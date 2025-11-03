package nl.tudelft.sem.lessonservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class NotFound extends RuntimeException {
    /**
     * Instantiates a new Not found.
     */
    public NotFound() {

    }

    /**
     * Instantiates a new Not found.
     *
     * @param message the message
     */
    public NotFound(String message) {
        super(message);
    }
}
