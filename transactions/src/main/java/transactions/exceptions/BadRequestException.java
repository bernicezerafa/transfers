package transactions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(final String message) {
        super(message);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
