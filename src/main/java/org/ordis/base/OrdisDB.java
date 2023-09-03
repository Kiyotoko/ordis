package org.ordis.base;

import com.google.common.hash.Hashing;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;
import java.sql.*;

public class OrdisDB {

    private static final @Nonnull Logger logger = LoggerFactory.getLogger(OrdisDB.class);

    private static final @Nonnull String DB_PATH = "jdbc:sqlite:ordis.db";

    private OrdisDB() {
        throw new UnsupportedOperationException();
    }

    static {
        try {
            logger.info("Loading data");
            Class.forName("com.mysql.cj.jdbc.Driver");
            create();
        } catch (ClassNotFoundException e) {
            logger.error("Failed to load driver", e);
        }
    }

    private static void create() {
        String sql = "CREATE TABLE IF NOT EXISTS user (\n"
                + "id text PRIMARY KEY,\n"
                + "user_name text NOT NULL,\n"
                + "global_name text NOT NULL\n"
                + ");";
        try (Connection connection = OrdisDB.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException ex) {
            logger.error("Can not create table", ex);
        }
    }

    public static void register(@Nonnull User user) {
        String sql = "INSERT INTO user(id,user_name,global_name) SELECT * FROM (SELECT ?,?,?) AS tmp " +
                "WHERE NOT EXISTS (SELECT id from user WHERE id = ?) LIMIT 1";
        try (Connection conn = OrdisDB.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            String hash = getHash(user);
            statement.setString(1, hash);
            statement.setString(2, user.getName());
            statement.setString(3, user.getGlobalName());
            statement.setString(4, hash);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Can not register user", ex);
        }
    }

    public static void select() {
        String sql = "SELECT id, user_name, global_name FROM user";
        try (Connection conn = DriverManager.getConnection(DB_PATH);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("\n{} | {} | {}", rs.getString("id"), rs.getString("user_name"),
                            rs.getString("global_name"));
                }
            }
        } catch (SQLException ex) {
            logger.error("Can not select data", ex);
        }
    }

    @Nonnull
    public static String getHash(@Nonnull User user) {
        return Hashing.sha256().hashString(user.getName(), Charset.defaultCharset()).toString();
    }

    @Nonnull
    public static Connection getConnection() throws SQLException {

       return  DriverManager.getConnection(DB_PATH);
    }
}
