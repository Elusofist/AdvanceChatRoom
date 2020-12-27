package DataBase;

public class SameUserNameExistsException extends Exception{
    SameUserNameExistsException() {
        super("Username is already caught. choose another one.");
    }
}
