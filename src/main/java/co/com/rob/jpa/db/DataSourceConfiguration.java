package co.com.rob.jpa.db;


import jakarta.annotation.sql.DataSourceDefinition;

//Ojo para usar esto se tiene que tener el modulo com.mysql creado
//en el servidor que desplegará la app para que preste la clase
//MysqlConnectionPoolDatasource del JDBC que debe estar en el server.
@DataSourceDefinition(
        className = "com.mysql.cj.jdbc.MysqlConnectionPoolDataSource",
        name = "java:/jdbc/personalBudgetAnnotation",
        serverName = "localhost",
        databaseName = "personal_budget",
        portNumber = 3307,
        user = "budget",
        password = "budget",
        initialPoolSize = 1,
        minPoolSize = 1,
        maxPoolSize = 5,
        maxIdleTime = 300
)
public class DataSourceConfiguration {
}
