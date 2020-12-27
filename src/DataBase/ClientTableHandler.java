package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientTableHandler {
    private static Statement stmt;
    private final static String INSERT_INTO;

    static {
        INSERT_INTO = "insert into";
    }

    public ClientTableHandler() {
        if (stmt == null) {
            ConnectionHandler connectionHandler = null;
            try {
                connectionHandler = new ConnectionHandler();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                stmt = connectionHandler.getConnection().createStatement();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void addRecord(String[] inputs) throws SQLException, SameUserNameExistsException {
        if (!exists(inputs[0])) {
            String queryStr = INSERT_INTO + " clients values('" +
                    inputs[0] + "', '" + inputs[1] + "')";
            stmt.executeUpdate(queryStr);
        } else throw new SameUserNameExistsException();
    }

    private static boolean exists(String username) {
        boolean exists = false;
        try {
            String queryStr = "select * from clients where username = '" + username + "';";
            ResultSet rs = stmt.executeQuery(queryStr);
            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return exists;
    }

    public static boolean check(String username, String password) throws SQLException
            , UserNotFoundException{
        try {
            String queryStr = "select password from clients where username = '" + username + "';";
            ResultSet rs = stmt.executeQuery(queryStr);
            boolean emptyDB = true;
            while (rs.next()) {
                emptyDB = false;
                if (rs.getString(2).equals(password)) {
                    System.out.println("Signed In Successfully");
                    return true;
                } else {
                    System.out.println("Wrong Password");
                    return false;
                }
            }
            if (emptyDB) throw new UserNotFoundException();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return false;
    }
}
