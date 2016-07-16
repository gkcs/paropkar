package paropkar.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import paropkar.model.Complaint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@RestController
public class ComplaintController {

    private static final String insertRecord = "INSERT INTO complaint (title,content,city,department,type,user_id," +
            "status) "
            + " VALUES (?,?,?,?,?,?,?)";
    private static final String mysqlDriver = "com.mysql.jdbc.Driver";


    @RequestMapping(value = "/addComplaint", method = RequestMethod.POST)
    public void addComplaint(@RequestBody final Complaint complaint) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(insertRecord);
            preparedStatement.setString(1, complaint.getTitle());
            preparedStatement.setString(2, complaint.getContent());
            preparedStatement.setString(3, complaint.getCity());
            preparedStatement.setString(4, complaint.getDepartment());
            preparedStatement.setString(5, complaint.getType());
            preparedStatement.setString(6, complaint.getUser_id());
            preparedStatement.setString(7, complaint.getStatus());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }

    private Connection getConnection() throws ClassNotFoundException,
            SQLException {
        Class.forName(mysqlDriver);
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/connect4", "root", "root");
    }

}