import java.io.*;
import java.util.*;
import java.sql.*;

public class JDBCTest {

  public static void main(String[] args) throws Exception {

    String db_url  = "jdbc:postgresql://localhost/fsfiii";
    String db_user = "reader";
    String db_pass = "r32der";
    String sql = "select * from test_big_table limit 2000000";
    
    Class.forName("org.postgresql.Driver");
    
    Connection conn = DriverManager.getConnection(db_url, db_user, db_pass);
    conn.setAutoCommit(false);
    Statement stmt = conn.createStatement();
    stmt.setFetchSize(50);
    
    ResultSet rslt = stmt.executeQuery(sql);
    ResultSetMetaData meta = rslt.getMetaData();
    int cols = meta.getColumnCount();
    
    Calendar begin_cal = Calendar.getInstance();
    
    int n = 0;
    while (rslt.next()) {
      for (int i = 1; i <= cols; i++) {
        String s = rslt.getString(i);
      }
      n++;
    }
    
    Calendar end_cal = Calendar.getInstance();
    long elapsed = end_cal.getTimeInMillis() - begin_cal.getTimeInMillis();
    if (elapsed <= 0) {
      elapsed = 1;
    }
    elapsed /= 1000;
    float rate = n / elapsed;
    System.out.println(n + " rows processed in " + elapsed +
        " seconds (" + rate + " recs/s)");
    
  }
}
