package org.endertools.bungeeauth.util;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import lombok.NoArgsConstructor;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
@NoArgsConstructor
public class GoogleAuthUtil {

    public String createKey() {
        GoogleAuthenticator authenticator = new GoogleAuthenticator();
        final GoogleAuthenticatorKey authenticatorKey = authenticator.createCredentials();
        return authenticatorKey.getKey();
    }

    public boolean isValid(String secret, int code) {
        GoogleAuthenticator authenticator = new GoogleAuthenticator();
        return authenticator.authorize(secret, code);
    }

}
