package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                log.error("bindingResult error: field '{}', rejected value '{}', message: {}",
                        error.getField(), error.getRejectedValue(), error.getDefaultMessage());
            }
            String message = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            throw new ValidationFailedException(message);
        }
    }
}
