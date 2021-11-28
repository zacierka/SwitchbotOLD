package utility;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DatabaseHelper {

    private final static Logger databaseLogger = LoggerFactory.getLogger(DatabaseHelper.class);

    private static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = ClassHelpers.getProperty("db.url");
            String user = ClassHelpers.getProperty("db.user");
            String password = ClassHelpers.getProperty("db.password");

            // create a connection to the database
            String unicode="useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
            conn = DriverManager.getConnection(url + "?"+unicode, user, password);

        } catch (SQLException e) {
            databaseLogger.warn("[DatabaseHelper::connect] " + e.getMessage());
        } finally {
            try {
                if (conn == null)
                    conn.close();
            } catch (SQLException ex) {
                databaseLogger.warn("[DatabaseHelper::connect] " + ex.getMessage());
            }
        }
        return conn;
    }

    public static ResultSet executeQuery(String query) {
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            // loop through the result set
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static String getVersion() throws SQLException {
        ResultSet rs = executeQuery("SELECT VERSION() as version");
        String version = "";
        while(rs.next())
        {
            try {
                version = rs.getString("version");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        rs.close();
        return version;
    }

    public static List<String> getOnlinePlayers() throws SQLException {
        ResultSet rs = executeQuery("SELECT playername from minecraft_player_status WHERE `status` = 'ONLINE'");
        List<String> players = new LinkedList<>();
        if (rs != null) {
            while(rs.next())
            {
                try {
                    players.add(rs.getString("playername"));;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (rs != null) {
            rs.close();
        }

        return players;
    }

    public static HashMap<String, String> getPummelLeaderboards() throws SQLException {
        ResultSet rs = executeQuery("SELECT `user`, `score` FROM pummel_leaderboards ORDER BY `score` DESC;");
        HashMap<String, String> scoreboard = new HashMap<>();
        if (rs != null) {
            while(rs.next())
            {
                try {
                    scoreboard.put(rs.getString("user"), String.valueOf(rs.getInt("score")));;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (rs != null) {
            rs.close();
        }

        return scoreboard;
    }


}
