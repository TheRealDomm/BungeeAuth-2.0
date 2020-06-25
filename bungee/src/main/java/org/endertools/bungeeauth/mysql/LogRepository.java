package org.endertools.bungeeauth.mysql;

import org.endertools.bungeeauth.util.ProxyCheckLog;
import org.endertools.database.TableManager;
import org.endertools.database.enumeration.ColumnType;
import org.endertools.database.utility.Column;
import org.endertools.database.utility.IDatabase;
import org.endertools.database.utility.RowResult;
import org.endertools.database.utility.RowResultList;

import java.util.UUID;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
public class LogRepository {

    private final TableManager tableManager;

    public LogRepository(IDatabase databaseManager) {
        this.tableManager = new TableManager(databaseManager, "auth_logs");
        this.tableManager.setPrimaryColumn("id");
        this.tableManager.addColumn("user_uuid", ColumnType.UUID);
        this.tableManager.addColumn("user_name", ColumnType.STRING);
        this.tableManager.addColumn("user_request_time", ColumnType.LONG);
        this.tableManager.addColumn("user_ip", ColumnType.STRING);
        this.tableManager.addColumn("user_asn", ColumnType.STRING);
        this.tableManager.addColumn("user_provider", ColumnType.STRING);
        this.tableManager.addColumn("user_continent", ColumnType.STRING);
        this.tableManager.addColumn("user_country", ColumnType.STRING);
        this.tableManager.addColumn("user_iso_code", ColumnType.STRING);
        this.tableManager.addColumn("user_region", ColumnType.STRING);
        this.tableManager.addColumn("user_city", ColumnType.STRING);
        this.tableManager.addColumn("user_latitude", ColumnType.DOUBLE);
        this.tableManager.addColumn("user_longitude", ColumnType.DOUBLE);
        this.tableManager.addColumn("user_proxy_check", ColumnType.BOOLEAN);
        this.tableManager.addColumn("user_status", ColumnType.STRING);
        this.tableManager.createTable();
    }

    public void addLog(ProxyCheckLog log) {
        RowResult databaseRow = new RowResult(tableManager);
        databaseRow.set("user_uuid", log.getUuid().toString());
        databaseRow.set("user_name", log.getName());
        databaseRow.set("user_request_time", System.currentTimeMillis());
        databaseRow.set("user_ip", log.getIp());
        databaseRow.set("user_asn", log.getAsn());
        databaseRow.set("user_provider", log.getProvider());
        databaseRow.set("user_continent", log.getContinent());
        databaseRow.set("user_country", log.getCountry());
        databaseRow.set("user_iso_code", log.getIsoCode());
        databaseRow.set("user_region", log.getRegion());
        databaseRow.set("user_city", log.getCity());
        databaseRow.set("user_latitude", log.getLatitude());
        databaseRow.set("user_longitude", log.getLongitude());
        databaseRow.set("user_proxy_check", log.isProxy() ? 1 : 0);
        databaseRow.set("user_status", log.getStatus());
        databaseRow.insert();
    }

    public ProxyCheckLog getLog(UUID uuid) {
        String sql = "SELECT * FROM auth_logs WHERE user_uuid = ? LIMIT 1;";
        RowResultList row = tableManager.getRowResultList(sql, uuid.toString());
        if (row.isEmpty()) return null;
        return getProxyCheckLog(row.get(0));
    }

    public ProxyCheckLog getLog(String name) {
        String sql = "SELECT * FROM auth_logs WHERE user_name = ? LIMIT 1;";
        RowResultList row = tableManager.getRowResultList(sql, name);
        if (row.isEmpty()) return null;
        return getProxyCheckLog(row.get(0));
    }

    private ProxyCheckLog getProxyCheckLog(RowResult row) {
        if (row.isEmpty()) return null;
        ProxyCheckLog log = new ProxyCheckLog();
        log.setUuid(row.getUuid("user_uuid"));
        log.setName(row.getString("user_name"));
        log.setIp(row.getString("user_ip"));
        log.setAsn(row.getString("user_asn"));
        log.setProvider(row.getString("user_provider"));
        log.setContinent(row.getString("user_continent"));
        log.setCountry(row.getString("user_country"));
        log.setIsoCode(row.getString("user_iso_code"));
        log.setRegion(row.getString("user_region"));
        log.setCity(row.getString("user_city"));
        log.setLatitude(row.getDouble("user_latitude"));
        log.setLongitude(row.getDouble("user_longitude"));
        log.setProxy(switchInt(row.getInt("user_proxy_check")));
        log.setStatus(row.getString("user_status"));
        return log;
    }

    private boolean switchInt(int i) {
        switch (i) {
            case 1:
                return true;
            default:
                return false;
        }
    }

}
