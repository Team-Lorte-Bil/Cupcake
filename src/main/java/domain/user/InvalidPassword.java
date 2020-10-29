package domain.user;

public class InvalidPassword extends Exception {
    public InvalidPassword() {
        super("Forkert kode! Prøv igen.");
    }
}
