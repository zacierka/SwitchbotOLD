import utility.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseTest {

    public static void main(String[] args) throws SQLException {
//        for (String p : DatabaseHelper.getOnlinePlayers())
//        {
//            System.out.println(p);
//        }

        for(Map.Entry<String, String> entry : DatabaseHelper.getPummelLeaderboards().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();


            System.out.println(key + " " + value);
            // do what you have to do here
            // In your case, another loop.
        }
    }
}
