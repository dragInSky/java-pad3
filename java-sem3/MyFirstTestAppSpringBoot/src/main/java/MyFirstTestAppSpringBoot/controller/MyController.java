package MyFirstTestAppSpringBoot.controller;

import MyFirstTestAppSpringBoot.exception.UnsupportedCodeException;
import MyFirstTestAppSpringBoot.exception.ValidationFailedException;
import MyFirstTestAppSpringBoot.model.Codes;
import MyFirstTestAppSpringBoot.model.ErrorCodes;
import MyFirstTestAppSpringBoot.model.ErrorMessages;
import MyFirstTestAppSpringBoot.model.Request;
import MyFirstTestAppSpringBoot.model.Response;
import MyFirstTestAppSpringBoot.service.ModifyResponseService;
import MyFirstTestAppSpringBoot.service.ValidationService;
import MyFirstTestAppSpringBoot.util.DateTimeUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("response created: {}", response);

        try {
            validationService.isValid(bindingResult);
            log.info("request uid={} passed validation", request.getUid());
            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("uid 123 не поддерживается");
            }
        } catch (ValidationFailedException e) {
            log.error("ValidationFailedException: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("response after validation error: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("UnsupportedCodeException: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("response after unsupported code error: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unknown exception", e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("response after unknown error: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response = modifyResponseService.modify(response);
        log.info("response to send: {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
