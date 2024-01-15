package co.com.rob.jpa.extensions;

import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;

//esta clase es usada en pruebas internas
//y el objetivo es que el datasource pueda encontrarse sin que se
//creen dos y creee un error de null pointer, entonces esto es una
//modificación directa a la extensión para poder inyectar el datasource
//en una clase de prueba.
public class ArquillianDBUnitExtension extends DBUnitExtension {

    private boolean isInside() {
        try {
            new InitialContext().lookup("java:comp/env");
            return true;
        } catch (NamingException ex) {
            return false;
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.beforeTestExecution(extensionContext);
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.afterTestExecution(extensionContext);
        }
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.beforeEach(extensionContext);
        }
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.afterEach(extensionContext);
        }
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.beforeAll(extensionContext);
        }
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if (isInside()) {
            super.afterAll(extensionContext);
        }
    }

}