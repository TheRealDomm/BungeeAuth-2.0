package org.endertools.bungeeauth.mysql;

import org.endertools.bungeeauth.config.PluginConfiguration;
import org.endertools.database.TableManager;
import org.endertools.database.enumeration.ColumnType;
import org.endertools.database.utility.Column;
import org.endertools.database.utility.IDatabase;
import org.endertools.database.utility.RowResult;
import org.endertools.database.utility.RowResultList;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author TheRealDomm
 * @since 23.06.20
 */
public class ProxyCheckRepository {

    private final TableManager tableManager;

    public ProxyCheckRepository(IDatabase databaseManager) {
        this.tableManager = new TableManager(databaseManager, "auth_proxy_log");
        this.tableManager.setPrimaryColumn("id");
        this.tableManager.addColumn("request_time", ColumnType.STRING);
        this.tableManager.addColumn("request_count", ColumnType.LONG);
        this.tableManager.createTable();
    }

    public void addRequest() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        String sql = "SELECT request_count FROM auth_proxy_log WHERE request_time = ? LIMIT 1;";
        RowResultList rowList = tableManager.getRowResultList(sql, today);
        if (rowList.isEmpty()) {
            RowResult row = new RowResult(tableManager);
            row.set("request_time", today);
            row.set("request_count", 1);
            row.insert();
            return;
        }
        long requests = rowList.get(0).getLong("request_count");
        rowList.get(0).set("request_count", requests++);
        rowList.get(0).update();
    }

    public boolean isRequestLimit() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        String sql = "SELECT request_count FROM auth_proxy_log WHERE request_time = ? LIMIT 1;";
        RowResultList row = tableManager.getRowResultList(sql, today);
        if (row.isEmpty()) return false;
        return row.get(0).getLong("request_count") >= PluginConfiguration.SETTING_MAX_PROXY_API_REQUEST;
    }

}
