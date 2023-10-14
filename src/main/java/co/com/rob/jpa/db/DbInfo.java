package co.com.rob.jpa.db;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class DbInfo {


    @Resource(lookup = "java:/jdbc/personalBudgetDS")
    DataSource dataSource;

    @PostConstruct
    public void viendoQuePasa(){
        System.out.println("***********justo antes de inyectarla");
        System.out.println("datasource:"+dataSource);
    }

    public String getVersion() {
        System.out.println("******Entra *******");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT VERSION()");) {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    public String getUser(){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT USER()");) {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }
}