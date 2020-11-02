package domain.user;

public interface UserFactory {
    User createUser(String name, String password, String email, int phoneno, double accountBalance, String role) throws UserExists;
}
