package domain.user;

public class InvalidPassword extends Exception {
    public InvalidPassword(String msg) {
        super(msg);
    }
}
