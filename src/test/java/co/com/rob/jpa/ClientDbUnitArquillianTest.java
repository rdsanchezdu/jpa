package co.com.rob.jpa;

import co.com.rob.jpa.entities.Expense;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.github.database.rider.core.api.connection.ConnectionHolder;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(DBUnitExtension.class)
public class ClientDbUnitArquillianTest {

    ConnectionHolder buildConnectionHolder() throws ClassNotFoundException {
        //It is necessary to have installed mysql dependency in pom.xml file
        //if not this will not work.
        return () -> DriverManager.getConnection(
                  "jdbc:mysql://localhost:3307/personal_budget?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"
                , "budget"
                , "budget");
    }


    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "jpa2-test.war")
                .addClass(Expense.class)
                .addAsResource(
                        new FileAsset(
                                new File(
                                        "src/main/resources/META-INF/persistence.xml")),
                                        "/META-INF/persistence.xml");
    }

    @Test
    @DataSet(value = "/datasets/expenses.yml")
        void testFindExpense() throws SQLException {
        try (Connection connection = buildConnectionHolder().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * from expenses where id = ?");) {
            ps.setLong(1, 1L);
            ResultSet resultSet = ps.executeQuery();
            System.out.println(resultSet.next());
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(3));
            System.out.println(resultSet.getString(5));
            assertEquals("1",resultSet.getString(1) );
            //assertTrue(resultSet.next());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //este test usa un connection holder uqe está creado en una
    //clase interna y crea una base de datos con 4 valores.
    @Test
    @DataSet(value = "/datasets/expenses_con_varios_registros.yml")
    void testFindExpense2() throws SQLException {
        System.out.println("**************Entra a hacer el test");
//        System.out.println("buildConnectionHolder: "+ buildConnectionHolder());

        try {
            ConnectionHolderInternClass myConnectionInternClass = new ConnectionHolderInternClass();
            System.out.println("entra a llamar getConnection");
            Connection connection2 =  myConnectionInternClass.getConnection();
             PreparedStatement ps =
                        connection2.prepareStatement(
                                "SELECT id,amount,comments,concept " +
                                        "from expenses " +
                                        "where id = ?"
                        );
            ps.setLong(1, 1L);
            ResultSet resultSet = ps.executeQuery();
            System.out.println("*****antes del assert");
            System.out.println(resultSet.next());
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
            assertEquals("120.43", resultSet.getString(2));
            //assertTrue(resultSet.next());

        } catch (Exception e){
            System.out.println("exception");
            System.out.println(e);

        }
    }

}//fin de la clase publica.

class ConnectionHolderInternClass {

    private  ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        System.out.println("********entra buscando la conexión");
        Connection connection = connectionHolder.get();
        if (connection == null || connection.isClosed()) {
            System.out.println("la conexion es nula");
            //Crear nueva conexión si no hay una disponible o está cerrada
            connection = createNewConnection();
            connectionHolder.set(connection);
        }
        System.out.println("connection: "+connection);
        return connection;
    }

    public  void releaseConnection() {
        Connection connection = connectionHolder.get();
        if (connection != null) {
            // No cerrar la conexión aquí; solo marcarla como disponible para su reutilización
            connectionHolder.remove();
        }
    }

    private  Connection createNewConnection() throws SQLException, ClassNotFoundException {
        // Implementar la lógica para crear una nueva conexión aquí
//        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("*****entra al connection holder a crear una conexión");
        //IMPORTANT:
        return DriverManager.getConnection( "jdbc:mysql://localhost:3307/personal_budget?autoReconnect=true&amp",
                                            "budget",
                                            "budget");
    }

}

