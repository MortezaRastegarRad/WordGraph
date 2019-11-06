package cs.sbu.connection;
//STEP 1. Import required packages

import java.sql.*;

class SqlConnection {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/w";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "0022949641";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {

//            STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

//            STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM Words";
            ResultSet rs = stmt.executeQuery(sql);

//            int in = stmt.executeUpdate("INSERT INTO `w`.`Words` (`id`, `relation_id`, `vocab`) VALUES ('7', 'asd', 'sad');");

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String age = rs.getString("relation_id");
                String first = rs.getString("vocab");
//                String last = rs.getString("last");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", relations : " + age);
                System.out.print(", vocab: " + first + "\n");
//                System.out.println(", Last: " + last);
            }
//           STEP 6: Clean-up environment
            rs.close();
            int up = stmt.executeUpdate("UPDATE `w`.`Words` SET `vocab`='Morteza' WHERE `id`='3';");
//            int in = stmt.executeUpdate("INSERT INTO `w`.`Words` (`id`, `relation_id`, `vocab`) VALUES ('8', 'asd', 'sad');");
            int del = stmt.executeUpdate("DELETE FROM `w`.`Words` WHERE `id` IN (3,6,7)");
            stmt.execute("flush privileges");
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("\nGoodbye!");
    }//end main
}//end FirstExample