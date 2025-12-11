package model.DAO;

import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory FACTORY;
    private static final String PERSISTENCE_UNIT_NAME = "HotelPU";

    static {
        try {
            Dotenv dotenv = Dotenv.configure().directory("./").load();

            Map<String, String> properties = new HashMap<>();
            properties.put("jakarta.persistence.jdbc.url", dotenv.get("DB_URL") + "?verifyServerCertificate=false&useSSL=false&requireSSL=false&serverTimezone=UTC");
            properties.put("jakarta.persistence.jdbc.user", dotenv.get("DB_USER"));
            properties.put("jakarta.persistence.jdbc.password", dotenv.get("DB_PASS"));

            FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}
