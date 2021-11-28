import utility.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseTest {

    public static void main(String[] args) throws SQLException {
//        for (String p : DatabaseHelper.getOnlinePlayers())
//        {
//            System.out.println(p);
//        }
        
        Map<String, Integer> sortedlist = DatabaseHelper.getPummelLeaderboards()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer> comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        for (Map.Entry<String, Integer> en : sortedlist.entrySet()) {
            System.out.println("Key = " + en.getKey() +
                    ", Value = " + en.getValue());
        }
    }
}
