## Wildfly configurations
### Mysql module with JDBC Controller
Nota: todos los archivos acá mencionados estarán
en la carpeta **wildfly-config**
para hacer esto primero toca descargar el JDBC de
la página de la base de datos que se
vaya a usar. 
##### mysql: https://dev.mysql.com/downloads/connector/j/
Luego de tenerlo ya descargado se descomprime y se guarda 
el **.jar** en la ruta del modulo que se va a crear:
##### {WILDFLY}/modules/system/layers/base/com/mysql/main
Entonces se pasa este archivo a la carpeta main 
y allí dentro se crea el archivo .xml del modulo llamado 
module.xml
### standalone.xml
Luego se debe registrar en el archivo **standalone.xml**
en el subsystem de "..datasources" tanto  el driver que 
representa el modulo anteriormente creado como el datasource que
enlaza al servidor de aplicaciones con la unidad de persistencia 
del proyecto java. 

### logs
También se agregan ciertos logger para poder ver a 
mas detalle los comando sql que está interpretando
hibernate, para esto se modifica **standalone.xml**
en la parte de logging y se agregan los correspondientes
modulos a los que se le tomarán los logs. 












