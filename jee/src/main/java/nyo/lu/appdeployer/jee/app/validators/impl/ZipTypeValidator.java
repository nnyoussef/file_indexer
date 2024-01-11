package nyo.lu.appdeployer.jee.app.validators.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nyo.lu.appdeployer.jee.app.validators.annotation.ZipTypeValidation;
import org.springframework.web.multipart.MultipartFile;

import static java.util.Objects.requireNonNull;


public class ZipTypeValidator implements ConstraintValidator<ZipTypeValidation, MultipartFile> {

    @Override
    public void initialize(ZipTypeValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext constraintValidatorContext) {
        return requireNonNull(multipartFile.getContentType()).contains("zip");
    }
}
