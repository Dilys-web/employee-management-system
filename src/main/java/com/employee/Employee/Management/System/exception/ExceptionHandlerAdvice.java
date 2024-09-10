package com.employee.Employee.Management.System.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final String TIMESTAMP = LocalDateTime.now().toString();

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), TIMESTAMP);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), TIMESTAMP);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        String errorMessage = "Provided arguments are invalid: " + String.join(", ", errors);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, TIMESTAMP);
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(Exception ex) {
        String message = "Username or password is incorrect";
        if (ex instanceof UsernameNotFoundException) {
            message = "Username not found";
        } else if (ex instanceof BadCredentialsException bcEx && (bcEx.getMessage().contains("Invalid Credentials") || bcEx.getMessage().contains("Bad Credentials"))) {
            message = "Username or password cannot be empty";
        }
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, message, TIMESTAMP);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, "Login credentials are missing", TIMESTAMP);
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAccountStatusException(AccountStatusException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, "User account is abnormal", TIMESTAMP);
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidBearerTokenException(InvalidBearerTokenException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, "The access token provided is expired, revoked, malformed, or invalid for other reasons", TIMESTAMP);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "No permission", TIMESTAMP);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "This API endpoint is not found.", TIMESTAMP);
    }

    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    public ResponseEntity<ErrorResponse> handleRestClientException(HttpStatusCodeException ex) throws JsonProcessingException {
        JsonNode rootNode = getJsonNode(ex);
        String formattedExceptionMessage = rootNode.path("error").path("message").asText();
        ErrorResponse errorResponse = new ErrorResponse((HttpStatus) ex.getStatusCode(), "A rest client error occurs, see data for details.", TIMESTAMP);
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    private static JsonNode getJsonNode(HttpStatusCodeException ex) throws JsonProcessingException {
        String exceptionMessage = ex.getMessage();
        exceptionMessage = exceptionMessage.replace("<EOL>", "\n");
        String jsonPart = exceptionMessage.substring(exceptionMessage.indexOf("{"), exceptionMessage.lastIndexOf("}") + 1);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(jsonPart);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred", TIMESTAMP);
    }
}