package nl.tudelft.sem.lessonservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Not acceptable.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)

public class NotAcceptable extends RuntimeException {

    /**
     * Instantiates a new Not acceptable.
     */
    public NotAcceptable() {

    }

    /**
     * Instantiates a new Not acceptable.
     *
     * @param message the message
     */
    public NotAcceptable(String message) {
        super(message);
    }
}
