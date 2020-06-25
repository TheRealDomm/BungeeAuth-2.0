package org.endertools.bungeeauth.util;

import lombok.NoArgsConstructor;
import org.endertools.bungeeauth.config.PluginConfiguration;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
@NoArgsConstructor
public class HashUtil {

    private SecretKeySpec secretKeySpec() {
        try {
            String keyString = PluginConfiguration.SETTING_HASH_KEY;
            byte[] key = (keyString).getBytes("UTF-8");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            key = messageDigest.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            return secretKeySpec;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String serialize(String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec());
            byte[] bytes = cipher.doFinal(key.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String deserialize(String key) {
        try {
            byte[] bytes = Base64.getDecoder().decode(key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec());
            byte[] cipherBytes = cipher.doFinal(bytes);
            return new String(cipherBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
