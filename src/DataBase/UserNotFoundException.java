package DataBase;

public class UserNotFoundException extends Exception{
    UserNotFoundException() {
        super("There is no one with such username.");
    }
}
