package util;

/**
 * Created by scott on 2017/4/26.
 */
public class LoginException extends RuntimeException {
    private String message;

    public LoginException() {
    }

    public LoginException(String message){
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
