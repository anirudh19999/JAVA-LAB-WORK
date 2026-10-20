import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {
    private static final Properties CONFIG = new Properties();

    static {
        try (InputStream in = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("db.properties not found in src folder.");
            }
            CONFIG.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DatabaseConnection() { }

    public static Connection getConnection() throws SQLException {
        String url = CONFIG.getProperty("db.url");
        String user = CONFIG.getProperty("db.user");
        String password = CONFIG.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
