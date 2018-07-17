package com.kiviliut;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class DBCon {

    private String dbUrl = "jdbc:mysql://127.0.0.1:3306?autoReconnect=true&useSSL=false";
    private String user = "root";
    private String password = "root";

    private static final int MYSQL_DUPLICATE_PK = 1062;

    /**
     * Closes a resource from SQL actions
     * handles exceptions and null exception
     * @param closeable variable which can be closed
     */
    private void CloseSQL(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets data from database
     * @param all if true select statement selects everything
     * @return vector of selected object from database
     */
    public Vector<Object[]> getInventory(boolean all) {
        // Vector returned data
        Vector<Object[]> data = new Vector<>();

        // SQL related variables
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT ID,Name,Item_count,Status FROM project_work.inventory";
            String sql2 = "SELECT * FROM project_work.inventory";

            connection = DriverManager.getConnection(this.dbUrl, this.user, this.password);
            statement = connection.createStatement();
            if (all) {
                resultSet = statement.executeQuery(sql2);
            }
            else {
                resultSet = statement.executeQuery(sql);
            }

            // Column count of the table to populate the vector of arrays
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                // Get row data
                Object[] row = new Object[columnCount];
                for (int i = 1; i < columnCount + 1; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                data.add(row);
            }
        }
        catch (SQLException e1){
            e1.printStackTrace();
        }
        finally {
            // Close all SQL variables
            CloseSQL(resultSet);
            CloseSQL(statement);
            CloseSQL(connection);
            //Notification.AddLogEntry("GetInventory DB connection closed");
        }

        return data;
    }

    public int getMinStock(String name) {
        // Return var
        int minStock = 0;

        // SQL related variables
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(this.dbUrl, this.user, this.password);
            preparedStatement = connection.prepareStatement("SELECT Min_stock FROM project_work.inventory WHERE Name = ?");
            preparedStatement.setString(1,name);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            minStock = resultSet.getInt("Min_stock");
        }
        catch (SQLException e1){
            e1.printStackTrace();
        }
        finally {
            // Close all SQL variables
            CloseSQL(resultSet);
            CloseSQL(preparedStatement);
            CloseSQL(connection);
        }

        return minStock;
    }

    /**
     * Updates database row with new data
     * @param name - name of item in database
     * @param status - status to be set in database
     * @param itemCount - item count to be set in database
     */
    public void DBUpdate(String name,String status, String itemCount){
        // SQL related variables
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(this.dbUrl, this.user, this.password);

            preparedStatement = connection.prepareStatement("UPDATE project_work.inventory SET Item_count = ?, Status = ? WHERE Name = ?");

            // Replaces the "?" in prepared statement with actual values, prevents SQL injection
            preparedStatement.setString(1,itemCount);
            preparedStatement.setString(2,status);
            preparedStatement.setString(3,name);

            // execute
            preparedStatement.executeUpdate();
        }
        catch (SQLException e1){
            e1.printStackTrace();
        }
        finally {
            // Close all SQL variables
            CloseSQL(preparedStatement);
            CloseSQL(connection);
        }
    }

    /**
     *
     * @param info ArrayList [0] Name [1] Item_count [2] Min_stock [3] Status [4] Sales
     * @return String return either "duplicate" or "success"
     */
    public String InsertToDB(ArrayList<String> info) {
        // SQL related variables
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(this.dbUrl, this.user, this.password);
            preparedStatement = connection.prepareStatement("INSERT INTO project_work.inventory (Name, Item_count, Min_stock, Status, Sales) VALUES (?,?,?,?,?)");

            // Replaces the "?" in prepared statement with actual values, prevents SQL injection
            preparedStatement.setString(1,info.get(0));
            preparedStatement.setInt(2,Integer.parseInt(info.get(1)));
            preparedStatement.setInt(3,Integer.parseInt(info.get(2)));
            preparedStatement.setString(4,info.get(3));
            preparedStatement.setInt(5,Integer.parseInt(info.get(4)));

            // execute
            preparedStatement.executeUpdate();
        }
        catch (SQLException e1){
            // Catches duplicate entry
            if(e1.getErrorCode() == MYSQL_DUPLICATE_PK ){
                System.out.println("Duplicate detected");
                return "duplicate";
            }
            else {
                e1.printStackTrace();
            }
        }
        finally {
            // Close all SQL variables
            CloseSQL(preparedStatement);
            CloseSQL(connection);
        }

        return "success";
    }
}
