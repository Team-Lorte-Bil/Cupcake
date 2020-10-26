package domain.user;

public class UserNotFound extends Exception {
    public UserNotFound(String name){
        super("Could not find user: " + name);
    }
}
