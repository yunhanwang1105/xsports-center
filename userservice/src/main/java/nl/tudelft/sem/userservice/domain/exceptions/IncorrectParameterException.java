package nl.tudelft.sem.userservice.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Incorrect parameter exception.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class IncorrectParameterException extends RuntimeException {

    /**
     * Instantiates a new Incorrect parameter exception.
     */
    public IncorrectParameterException() {

    }

    /**
     * Instantiates a new Incorrect parameter exception.
     *
     * @param message the message
     */
    public IncorrectParameterException(String message) {
        super(message);
    }

}
