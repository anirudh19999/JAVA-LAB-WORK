# ShopSphere

ShopSphere is an Advanced Java learning project built around a small online store. The main application uses Jakarta Servlets and JSP on Tomcat, JDBC with MySQL, and a service/DAO structure. Separate examples cover Swing, sockets, RMI, serialization, JNDI, internationalization, URL handlers, and legacy applet concepts.

## Requirements

- JDK 17 or newer
- Maven 3.9+
- MySQL 8+
- Apache Tomcat 10.1+

## Run the web application

1. Create a MySQL database and load the schema and sample products:

   ```sql
   SOURCE database/shopsphere.sql;
   ```

2. Set database connection environment variables. Defaults are shown below; change the password to your local MySQL password.

   ```text
   SHOPSPHERE_DB_URL=jdbc:mysql://localhost:3306/shopsphere?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   SHOPSPHERE_DB_USER=root
   SHOPSPHERE_DB_PASSWORD=your-password
   ```

   On PowerShell, set them for the current terminal with `$env:SHOPSPHERE_DB_URL='...'`, `$env:SHOPSPHERE_DB_USER='root'`, and `$env:SHOPSPHERE_DB_PASSWORD='...'`.

3. Build the WAR:

   ```bash
   mvn clean package
   ```

4. Copy `target/shopsphere.war` into Tomcat's `webapps` directory and start Tomcat. Visit `http://localhost:8080/shopsphere/`.

5. Register an account from the web page. To use admin product management, set the account role in MySQL after registration:

   ```sql
   UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';
   ```

The checkout flow uses a clearly labeled mock payment: no real payment provider or card data is involved. Order totals and inventory changes are calculated on the server inside a database transaction.

### Quick local demo without MySQL

For a first look, set `SHOPSPHERE_DEMO_MODE=true` in the environment used to start Tomcat. In PowerShell, run `$env:SHOPSPHERE_DEMO_MODE='true'` before starting Tomcat. The app then uses an in-memory catalog and account store; all demo accounts, stock changes, and orders reset when Tomcat stops. The seeded demo admin is `admin@shopsphere.local` with password `DemoAdmin123!`. Use this account only for the local classroom demo. When demo mode is off, ShopSphere uses the MySQL settings above.

## Optional syllabus demonstrations

Build the project first, then run Java entry points using your IDE or `scripts/run-demo.ps1` on Windows. `com.shopsphere.network.InventoryServer` and `InventoryClient` demonstrate a line-based socket protocol; `com.shopsphere.rmi.InventoryServerMain` and `InventoryClientMain` demonstrate RMI lookup and remote calls; `com.shopsphere.swing.AdminDemo` demonstrates Swing MVC and common controls. `SerializationDemo`, `JndiDemo`, `UrlConnectionDemo`, and `I18nDemo` are standalone examples. The networking and RMI examples do not access the production database.

Applets are included only as a historical syllabus note: browser plug-in support was removed from modern Java/browser environments. Their concepts are documented in `docs/syllabus-map.md`, while the current app runs as a normal web application.

## Project map

- `src/main/java`: servlet controllers, service, JDBC DAO, models, filters, listener and syllabus demonstrations
- `src/main/webapp/WEB-INF/views`: JSP views, inaccessible directly so requests pass through servlets
- `database/shopsphere.sql`: tables and sample catalog data
- `docs/`: syllabus mapping, architecture notes, and manual verification steps

## GitHub

Create a repository, then from this folder run `git init`, `git add .`, `git commit -m "Add ShopSphere Advanced Java project"`, and add/push to your GitHub remote. Do not commit local database passwords.
