package com.example.demo.utils;

import java.sql.*;

public class DB {
    private static String driver = "org.postgresql.Driver";
    private static Connection conn = null;
    private static String connStr = "jdbc:postgresql://localhost:5432/vaccinedb";
    public static void dbConnect() throws ClassNotFoundException, SQLException {
        try{
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection( connStr, "postgres", "355247phuc");
            System.out.println("Connect successfully");
        } catch (Exception e) {
            System.out.println( "Connect failed");
            e.printStackTrace();
            throw e;
        }


    }

    public static void dbDisconnect() throws SQLException {
        try{
            if( conn != null && !conn.isClosed() ){
                conn.close();
                System.out.println("Disconnect successfully");
            }
        } catch ( Exception e ){
            throw e;
        }
    }
    // DB execute query operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException , ClassNotFoundException{
        Statement stmt = null;
        ResultSet resultSet = null;
        try{
            dbConnect();// connect to db
            System.out.println("Select statement: " + queryStmt + "\n");
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery( queryStmt );
            return resultSet;
        } catch ( SQLException e){
            System.out.println("Problem occurred at executeQuery operation: " + e);
            throw e;
        }finally {
//
////            if( resultSet != null){
////                resultSet.close();
////            }
//            if( stmt != null){
//                stmt.close();
//            }
            dbDisconnect();
            return resultSet;
        }

    }
    // DB execute update
    public static int dbExecuteUpdate( String sqlStmt) throws SQLException, ClassNotFoundException{
        Statement stmt  = null;
        int a = 0;
        try{
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);

        } catch ( Exception e ){
            a = 1;
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {

            if( stmt != null){
                // close statement
                stmt.close();
            }
            dbDisconnect();
            System.out.println( "********************" + a+ "********************");
            return a;
        }

    }

}
