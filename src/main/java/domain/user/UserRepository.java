package domain.user;


public interface UserRepository extends UserFactory {
    User findUser(String name) throws UserNotFound;
    Iterable<User> findAllUsers();

}
