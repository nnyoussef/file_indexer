package nyo.lu.appdeployer.jee.app.restcontroller;

import jakarta.servlet.http.HttpServletRequest;
import nyo.lu.appdeployer.jee.service.exceptions.DirectoryManagementException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static nyo.lu.appdeployer.jee.service.dto.builder.ProblemResponseEntityBuilder.create;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ErrorController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DirectoryManagementException.class)
    public ResponseEntity<ProblemDetail> getDirectoryMicromanagement(DirectoryManagementException directoryManagementException,
                                                                     HttpServletRequest httpServletRequest) {
        return create().setHttpRequest(httpServletRequest)
                .setTitle("Directory Management Issue")
                .addDetails(directoryManagementException.getDirectoryManagementErrors())
                .addThrowable(directoryManagementException)
                .build();
    }


}
