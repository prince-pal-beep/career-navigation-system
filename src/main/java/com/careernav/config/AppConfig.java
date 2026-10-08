package com.careernav.config;

import com.careernav.dao.UserDAO;
import com.careernav.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/** Application start-up hook + password hashing helpers (PBKDF2, no external library). */
@WebListener
public class AppConfig implements ServletContextListener {

    public static final String DEFAULT_ADMIN_EMAIL = "admin@careernav.com";
    public static final String DEFAULT_ADMIN_PASSWORD = "Admin@123";   // change after first login!

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            UserDAO dao = new UserDAO();
            if (!dao.emailExists(DEFAULT_ADMIN_EMAIL)) {
                User a = new User();
                a.setName("System Administrator");
                a.setEmail(DEFAULT_ADMIN_EMAIL);
                a.setPassword(hash(DEFAULT_ADMIN_PASSWORD));
                a.setRole("ADMIN");
                dao.register(a, null);
                sce.getServletContext().log("Default admin created: " + DEFAULT_ADMIN_EMAIL);
            }
        } catch (Exception e) {
            sce.getServletContext().log("Could not seed admin - did you run schema.sql? " + e.getMessage());
        }
    }

    public static String hash(String password) {
        try {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(pbkdf2(password, salt));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean verify(String password, String stored) {
        try {
            String[] parts = stored.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] expected = Base64.getDecoder().decode(parts[1]);
            return MessageDigest.isEqual(expected, pbkdf2(password, salt));
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] pbkdf2(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
    }
}
