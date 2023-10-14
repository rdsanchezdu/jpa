package co.com.rob.jpa;

import co.com.rob.jpa.db.DbInfo;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ArquillianExtension.class)
class DbInfoArquillianTest {

    @Inject
    private DbInfo dbInfo;

    @Deployment
    public static WebArchive createDeployment() {
        System.out.println("*************empieza a crear el war");
        System.out.println("DbInfo.class:"+DbInfo.class);
        return ShrinkWrap.create(WebArchive.class, "jpa-test.war")
                .addClass(DbInfo.class);
    }

    @Test
    void testVersion8() {
        System.out.println("*****ENTRA AL TEST*******");
        String test = "this is a test";
//        assertEq
        System.out.println("dbInfo.getVersion:"+dbInfo.getVersion());
        assertTrue(dbInfo.getVersion().startsWith("8"));
    }

}