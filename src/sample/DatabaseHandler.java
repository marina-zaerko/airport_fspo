package sample;

import sample.Config;

import java.sql.*;
import java.lang.Class;


public class DatabaseHandler extends Config {


    Connection connect;

    public Connection getDbConnection()
        throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airport", "postgres", "1234");
        return connect;
    }


    public void signUpUser(String login, String password) throws ClassNotFoundException, SQLException {

        PreparedStatement pr;
        pr = getDbConnection().prepareStatement("INSERT INTO users (id_user, login, password, access) VALUES(?,?,?,?)");
        pr.setInt(1, setIdPlusUser("users"));
        pr.setString(2, login);
        pr.setString(3, password);
        pr.setString(4, "user");
        pr.executeUpdate();

    }

    public int setIdPlusUser(String table)
            throws ClassNotFoundException, SQLException {
            Statement set;
            set = getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
            String to_result = "select * from " + table;
            ResultSet resultset;
            resultset = set.executeQuery(to_result);
            int id;
            resultset.last();
            String id_ = resultset.getString(1);
            int ready_id = Integer.parseInt(id_);
            id = ready_id + 1;
            return id;
    }


    public boolean isCyrillic(String s) {
        boolean result = false;
        for (char a : s.toCharArray()) {
            if (Character.UnicodeBlock.of(a) == Character.UnicodeBlock.CYRILLIC) {
                result = !result;
                break;
            }
        }
        return result;
    }

    public static boolean isNumeric(String str)
    {
        try
        { double d = Double.parseDouble(str); }
        catch(NumberFormatException nfe)
        { return false; }
        return true;
    }

}
