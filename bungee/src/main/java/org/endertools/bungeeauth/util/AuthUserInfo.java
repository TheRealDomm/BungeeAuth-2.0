package org.endertools.bungeeauth.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class AuthUserInfo {

    private final UUID uuid;
    private String name = "";
    private final String authKey;
    private long logins = 0;
    private long failedLogins = 0;
    private String lastIp = "";
    private long lastLogin = 0;

}
