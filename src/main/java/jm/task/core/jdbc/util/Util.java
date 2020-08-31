package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;


public class Util {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public Connection connectToDataBase() {
        Connection connection = null;
        MysqlDataSource dataSource = new MysqlDataSource();
        try {
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/testbase?useSSL=false&" +
                    "useLegacyDatetimeCode=false&serverTimezone=UTC");
            dataSource.setUser("user");
            dataSource.setPassword("user");
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static SessionFactory getSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );

            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
        return sessionFactory;
    }


    public static SessionFactory gettSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    public static void main(String[] args) {
        Util.getSessionFactory();
    }
}
