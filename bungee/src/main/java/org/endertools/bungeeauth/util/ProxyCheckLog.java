package org.endertools.bungeeauth.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProxyCheckLog {

    private UUID uuid;
    private String name;
    private String ip;
    private String asn;
    private String provider;
    private String continent;
    private String country;
    private String isoCode;
    private String region;
    private String city;
    private double latitude;
    private double longitude;
    private boolean proxy;
    private String status;

}
