package cs.sbu.connection;

import java.sql.*;
import java.util.ArrayList;

public class SqlConnection {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/w";

    static final String USER = "root";
    static final String PASS = "0022949641";


    public SqlConnection(Connection conn) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Insert(ArrayList<String> words, Long id, ArrayList<Long> relations, Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        int in = stmt.executeUpdate("INSERT INTO `w`.`Words` (`id`, `relation_id`, `vocab`) VALUES ('"+id+"', '"+relations+"', '"+words+"');");
        stmt.execute("flush privileges;");
        stmt.close();
    }


    public void Update(String insert, Connection conn) throws SQLException {
        System.out.println("Creating statement...");
        Statement stmt = conn.createStatement();
        String sql;
        sql = "SELECT * FROM Words";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String age = rs.getString("relation_id");
            String first = rs.getString("vocab");

            System.out.print("ID: " + id);
            System.out.print(", relations : " + age);
            System.out.print(", vocab: " + first + "\n");
        }
        rs.close();
        int up = stmt.executeUpdate("UPDATE `w`.`Words` SET `vocab`='Morteza' WHERE `id`='3';");
        int in = stmt.executeUpdate("INSERT INTO `w`.`Words` (`id`, `relation_id`, `vocab`) VALUES ('8', 'asd', 'sad');");
        int del = stmt.executeUpdate("DELETE FROM `w`.`Words` WHERE `id` IN (3,6,7)");
        stmt.execute("flush privileges");
        stmt.close();
        conn.close();
    }

    public void Delete(String insert, Connection conn) throws SQLException {
        System.out.println("Creating statement...");
        Statement stmt = conn.createStatement();
        String sql;
        sql = "SELECT * FROM Words";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String age = rs.getString("relation_id");
            String first = rs.getString("vocab");

            System.out.print("ID: " + id);
            System.out.print(", relations : " + age);
            System.out.print(", vocab: " + first + "\n");
        }
        rs.close();
        int up = stmt.executeUpdate("UPDATE `w`.`Words` SET `vocab`='Morteza' WHERE `id`='3';");
        int in = stmt.executeUpdate("INSERT INTO `w`.`Words` (`id`, `relation_id`, `vocab`) VALUES ('8', 'asd', 'sad');");
        int del = stmt.executeUpdate("DELETE FROM `w`.`Words` WHERE `id` IN (3,6,7)");
        stmt.execute("flush privileges");
        stmt.close();
        conn.close();
    }
}