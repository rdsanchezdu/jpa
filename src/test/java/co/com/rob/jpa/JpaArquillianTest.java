package co.com.rob.jpa;

import co.com.rob.jpa.entities.Expense;
import co.com.rob.jpa.extensions.ArquillianDBUnitExtension;
import com.github.database.rider.core.api.dataset.DataSet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import jakarta.annotation.Resource;

import javax.sql.DataSource; //TODO: intentar cambiarlo a jakarta y ver que pasa
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import com.github.database.rider.core.api.connection.ConnectionHolder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(ArquillianExtension.class)
@ExtendWith(ArquillianDBUnitExtension.class)
class JpaArquillianTest {

    @Resource(lookup = "java:/jdbc/personalBudgetDS")
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    private Connection connection;

    //este connecion holder es para databaserider
    //la cual llenará la base de datos con lo que pongamos en expenses.yml
    ConnectionHolder buildConnectionHolder() {
        return () -> {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
            return connection;
        };
    }

    @Deployment
    public static WebArchive createDeployment() {
        File[] dbRider = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("com.github.database-rider:rider-junit5")
                .withTransitivity()
                .asFile();

        return ShrinkWrap.create(WebArchive.class, "jpa-test.war")
                .addClass(Expense.class)
                .addAsWebInfResource(new File("src/test/resources/dbunit.yml"), "classes/dbunit.yml")
                .addAsResource(new FileAsset(new File("src/main/resources/META-INF/persistence.xml")),
                        "/META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addClass(ArquillianDBUnitExtension.class)
                .addAsWebInfResource(new File("src/test/resources/datasets/expenses.yml"), "classes/datasets/expenses.yml")
                .addAsWebInfResource(new File("src/test/resources/datasets/expenses_con_varios_registros.yml"), "classes/datasets/expenses_con_varios_registros.yml")
                .addAsLibraries(dbRider);
    }

    @Test
    @DataSet(value = "/datasets/expenses_con_varios_registros.yml")
    void testFindExpenseShort() throws SQLException {
        Expense expense = em.find(Expense.class, 1L);
        System.out.println("expense: " + expense.toString());
        System.out.println("******* expense amount: $" +expense.getAmount());

        assertNotNull(expense);

    }

//    @Test
//    @DataSet(value = "/datasets/expenses.yml")
//    void testFindExpense() throws SQLException {
//
//        Expense expense = em.find(Expense.class, 1L);
//        System.out.println("*********************sale a hacer el assert");
//        System.out.println(em);
//        System.out.println(expense);
//        System.out.println("***************TEST DEL TEST**************");
//        PreparedStatement ps =
//                connection.prepareStatement("SELECT id,amount, concept from expenses where id = ?");
//            ps.setLong(1, 1L);
//            ResultSet resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                System.out.println("result set: " + resultSet);
//                System.out.println("result set get string en 1 " + resultSet.getString(1));
//                System.out.println("ejecuta el query y trae: " + resultSet.getString("amount"));
//                System.out.println("ejecuta el query y trae: " + resultSet.getString("concept"));
//            } else {
//                // Manejar el caso en que no hay resultados
//                System.out.println("no reusltados");
//            }
//        assertNotNull(expense);
//    }
}