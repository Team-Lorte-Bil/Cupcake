package domain.user;


public interface UserRepository extends UserFactory {
    User findUser(int id) throws UserNotFound;
    Iterable<User> findAllUsers();

}
