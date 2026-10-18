package MyFirstTestAppSpringBoot.controller;

import MyFirstTestAppSpringBoot.exception.UnsupportedCodeException;
import MyFirstTestAppSpringBoot.exception.ValidationFailedException;
import MyFirstTestAppSpringBoot.model.Codes;
import MyFirstTestAppSpringBoot.model.ErrorCodes;
import MyFirstTestAppSpringBoot.model.ErrorMessages;
import MyFirstTestAppSpringBoot.model.Request;
import MyFirstTestAppSpringBoot.model.Response;
import MyFirstTestAppSpringBoot.service.AnnualBonusService;
import MyFirstTestAppSpringBoot.service.ModifyRequestService;
import MyFirstTestAppSpringBoot.service.ModifyResponseService;
import MyFirstTestAppSpringBoot.service.ValidationService;
import MyFirstTestAppSpringBoot.util.DateTimeUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {

    private final ValidationService validationService;
    @Qualifier("ModifySystemTimeResponseService")
    private final ModifyResponseService modifyResponseService;
    @Qualifier("ModifySourceRequestService")
    private final ModifyRequestService modifySourceRequestService;
    @Qualifier("ModifySystemNameRequestService")
    private final ModifyRequestService modifySystemNameRequestService;
    private final AnnualBonusService annualBonusService;

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("request: {}", request);

        request.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        log.info("request received by Service 1 at {}", request.getSystemTime());

        Response response = Response.success(request);
        log.info("response created: {}", response);

        try {
            validationService.isValid(bindingResult);
            validationService.isSupported(request);
            log.info("request uid={} passed validation", request.getUid());
            calculateBonuses(request, response);
        } catch (ValidationFailedException e) {
            log.error("ValidationFailedException: {}", e.getMessage());
            return failed(response, ErrorCodes.VALIDATION_EXCEPTION, ErrorMessages.VALIDATION, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("UnsupportedCodeException: {}", e.getMessage());
            return failed(response, ErrorCodes.UNSUPPORTED_EXCEPTION, ErrorMessages.UNSUPPORTED, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unknown exception", e);
            return failed(response, ErrorCodes.UNKNOWN_EXCEPTION, ErrorMessages.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response = modifyResponseService.modify(response);
        log.info("response to send: {}", response);

        modifySourceRequestService.modify(request);
        modifySystemNameRequestService.modify(request);

        return ResponseEntity.ok(response);
    }

    private void calculateBonuses(Request request, Response response) {
        response.setAnnualBonus(annualBonusService.calculate(request.getPosition(),
                request.getSalary(), request.getBonus(), request.getWorkDays()));
        response.setQuarterlyBonus(annualBonusService.calculateQuarterly(request.getPosition(),
                request.getSalary(), request.getBonus(), request.getWorkDays()));
        log.info("response after bonus calculation: {}", response);
    }

    private ResponseEntity<Response> failed(Response response, ErrorCodes errorCode,
                                            ErrorMessages errorMessage, HttpStatus status) {
        response.setCode(Codes.FAILED);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        log.info("response after error: {}", response);
        return new ResponseEntity<>(response, status);
    }
}
