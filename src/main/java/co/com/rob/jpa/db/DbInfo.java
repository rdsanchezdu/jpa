package co.com.rob.jpa.db;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@ApplicationScoped
public class DbInfo {
    private static final Logger logger =
            LogManager.getLogger(DbInfo.class);

    @Resource(lookup = "java:/jdbc/personalBudgetDS")
    DataSource dataSource;

    @PostConstruct
    public void viendoQuePasa(){
        logger.debug("*******PROBANDO LOG4J in DbInfo");
        System.out.println("***********POST CONSTRUCT Dbinfo Class :justo antes de inyectarla");
        System.out.println("POST CONSTRUCT Dbinfo Class: datasource:"+dataSource);
    }

    public String getExpenseById(long id){
        System.out.println("***********entra el metodo getExpenseById");
        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * from expenses where id = ?");) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                System.out.println("result set: " + resultSet);
                System.out.println("result set get string en 1 " + resultSet.getString(1));
                System.out.println("ejecuta el query y trae: " + resultSet.getString(2));
                System.out.println("ejecuta el query y trae: " + resultSet.getString("concept"));

                return resultSet.getString(1);
            } else {
                // Manejar el caso en que no hay resultados
                return "No hay resultados";
            }
//            System.out.println("result set: " + resultSet);
//            System.out.println("result set get string en 1 " + resultSet.getString(1));
//            resultSet.next();
//            System.out.println("ejecuta el query y trae: "+resultSet.next());
//            System.out.println(resultSet.getString(1));
//            return resultSet.getString(1);
        } catch (SQLException ex) {
            return ex.getMessage();
        }

    }

    public String getVersion() {
        System.out.println("******Entra a getVersion() *******");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT VERSION()");) {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    //Brings the database owner
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