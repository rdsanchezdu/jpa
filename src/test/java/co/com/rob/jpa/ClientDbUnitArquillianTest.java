//package co.com.rob.jpa;
//
//import co.com.rob.jpa.entities.Expense;
//import com.github.database.rider.core.api.connection.ConnectionHolder;
//import com.github.database.rider.core.api.dataset.DataSet;
//import com.github.database.rider.junit5.DBUnitExtension;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit5.ArquillianExtension;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.FileAsset;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import java.io.File;
//import java.sql.*;
//import java.util.Enumeration;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(ArquillianExtension.class)
//@ExtendWith(DBUnitExtension.class)
//public class ClientDbUnitArquillianTest {
//
//    // java:/jdbc/personalBudgetDS
//    ConnectionHolder buildConnectionHolder() {
//        System.out.println("***************** getting connection");
//        Connection connection = null;
//        try {
//            Enumeration<Driver> drivers = DriverManager.getDrivers();
//            while (drivers.hasMoreElements()) {
//
//                Driver driver = drivers.nextElement();
//                System.out.println("Driver: " + driver.getClass().getName());
//            }
//            connection = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3307/personal_budget?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"
//                    , "budget"
//                    , "budget");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(connection);
//
//
//        return () -> DriverManager.getConnection("jdbc:mysql://host.docker.internal:3307/personal_budget" +
//                        "?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"
//                , "budget"
//                , "budget");
//    }
//
//    @Deployment(testable = false)
//    public static WebArchive createDeployment() {
//        return ShrinkWrap.create(WebArchive.class, "jpa2-test.war")
//                .addClass(Expense.class)
//                .addAsResource(new FileAsset(new File("src/main/resources/META-INF/persistence.xml")),
//                        "/META-INF/persistence.xml");
//    }
//
//    @Test
//    @DataSet(value = "/datasets/expenses.yml")
//    void testFindExpense() throws SQLException {
//        System.out.println("**************Entra a hacer el test");
//        System.out.println("buildConnectionHolder: "+ buildConnectionHolder());
//        try (Connection connection = buildConnectionHolder().getConnection();
//             PreparedStatement ps = connection.prepareStatement("SELECT id,amount,comments,concept from expenses where id = ?");) {
//            ps.setLong(1, 1L);
//            ResultSet resultSet = ps.executeQuery();
//
//            assertTrue(resultSet.next());
//        }
//    }
//}
////    Bound non-transactional data source: java:/jdbc/personalBudgetDS
////WFLYJPA0002: Read persistence.xml for persistenceUnit-personalBudget
////        Http management interface listening on http://127.0.0.1:10090/management