package co.com.rob.jpa;

import co.com.rob.jpa.db.DbInfo;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
/*
  this test class has a problem: do not show sql results, but it
  can query the database.
* */


@ExtendWith(ArquillianExtension.class)
class DbInfoArquillianTest {

    private static final Logger logger =
            LogManager.getLogger(DbInfoArquillianTest.class);

    @Inject
    private DbInfo dbInfo;

    @Deployment
    public static WebArchive createDeployment() {
        logger.debug("****PROBANDO LOG4J");
        System.out.println("*************empieza a crear el war");
        System.out.println("DbInfo.class:"+DbInfo.class);
        return ShrinkWrap.create(WebArchive.class, "jpa-test.war")
                .addClass(DbInfo.class);
    }

    @Test
    void testVersion8() {
        System.out.println("*****ENTRA AL TEST*******");
        System.out.println("dbInfo.getVersion: "+dbInfo.getVersion());
        System.out.println("dbInfo.getExpenseById: " + dbInfo.getExpenseById(1L));
        //System.out.println("TEST: dbInfo getUser: " + dbInfo.getUser());

        assertTrue(dbInfo.getVersion().startsWith("8"));
    }

}