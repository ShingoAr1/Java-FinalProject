import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Mainmodel {
    
    Connection conn = null;
    private ObservableList<PersonData> personData;

    public Mainmodel(){
        this.conn = dbConnection.getConnection();

        if(this.conn == null){
            System.exit(0);
        }
    }

    public ObservableList<PersonData> getPersonData(){
        String query = "SELECT * FROM people_tbl";

        try {
            this.personData = FXCollections.observableArrayList();

            ResultSet resultSet = conn.createStatement().executeQuery(query);


            while(resultSet.next()){
                this.personData.add( new PersonData(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4), 
                    resultSet.getString(5),
                    resultSet.getString(7),
                    resultSet.getString(1),
                    resultSet.getString(6)
                ));
            }

            return personData;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
        
    }

    public void addPerson(String name, String category, String email, String phone, String notes, String address) {
        String query = "INSERT INTO people_tbl (name,category,email,phone,notes,address) VALUES(?,?,?,?,?,?)";
        PreparedStatement statement = null;

        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, category);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setString(5, notes);
            statement.setString(6,address);

            statement.execute();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }       
    }

    public void editPersonData(String name, String category, String email, String phone, String notes, String address, String id) {
        String query = "UPDATE people_tbl SET name = ?, category = ?, email = ?, phone = ?, notes = ?, address = ? WHERE id = ?" ;
    
        PreparedStatement statement = null;
        
        try {
          Connection conn = dbConnection.getConnection();
          statement = conn.prepareStatement(query);

  
          statement.setString(1, name);
          statement.setString(2, category);
          statement.setString(3, email);
          statement.setString(4, phone);
          statement.setString(5, notes);
         statement.setString(6, address);
          statement.setInt(7, Integer.parseInt(id));

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(String id) {
        String query = "DELETE FROM people_tbl WHERE id = ?";
        PreparedStatement statement = null;
        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(query);

            statement.setInt(1, Integer.parseInt(id));

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}