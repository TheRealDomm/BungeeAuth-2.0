package org.endertools.bungeeauth.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
@RequiredArgsConstructor
@Data
public class ProxyCheck {

    private static final String API_URL = "https://proxycheck.io/v2/";
    private final String API_KEY;

    public ProxyCheckLog createLog(ProxiedPlayer player) {
        ProxyCheckLog log = parseLog(player.getAddress().getAddress().getHostAddress());
        log.setName(player.getName());
        log.setUuid(player.getUniqueId());
        return log;
    }

    private String getApiUrl(String ip) {
        return API_URL + ip + "?key=" + API_KEY + "&vpn=1&asn=1";
    }

    private ProxyCheckLog parseLog(String ip) {
        ProxyCheckLog log = new ProxyCheckLog();
        String queryResult = query(ip);
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(queryResult);
            JSONObject subObject = (JSONObject) jsonObject.get(ip);
            log.setStatus((String) jsonObject.get("status"));
            log.setIp(ip);
            log.setAsn((String) subObject.get("asn"));
            log.setProvider((String) subObject.get("provider"));
            log.setContinent((String) subObject.get("continent"));
            log.setCountry((String) subObject.get("country"));
            log.setIsoCode((String) subObject.get("isocode"));
            log.setRegion((String) subObject.get("region"));
            log.setCity((String) subObject.get("city"));
            log.setLatitude((double) subObject.get("latitude"));
            log.setLongitude((double) subObject.get("longitude"));
            log.setProxy(subObject.get("proxy").toString().equals("no") ? false : true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return log;
    }

    private String query(String ip) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(getApiUrl(ip));
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("User-Agent", "BungeeAuth-2.0");
            connection.setRequestProperty("tag", "BungeeAuth-2.0");
            String line = "";
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
