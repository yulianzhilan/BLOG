package util;

/**
 * Created by scott on 2017/4/26.
 */
public class LoginException extends RuntimeException {
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
