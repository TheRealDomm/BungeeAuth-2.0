package org.endertools.bungeeauth.mysql;

import org.endertools.bungeeauth.util.AuthUserInfo;
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
public class AuthRepository {

    private final TableManager tableManager;

    public AuthRepository(IDatabase databaseManager) {
        this.tableManager = new TableManager(databaseManager, "auth_users");
        this.tableManager.setPrimaryColumn("id");
        this.tableManager.addColumn("user_uuid", ColumnType.UUID);
        this.tableManager.addColumn("user_name", ColumnType.STRING);
        this.tableManager.addColumn("user_auth_key", ColumnType.STRING);
        this.tableManager.addColumn("user_logins", ColumnType.LONG);
        this.tableManager.addColumn("user_failed_logins", ColumnType.LONG);
        this.tableManager.addColumn("user_last_ip", ColumnType.STRING);
        this.tableManager.addColumn("user_last_login", ColumnType.LONG);
        this.tableManager.createTable();
    }

    public boolean isRegistered(UUID uuid) {
        String sql = "SELECT * FROM auth_users WHERE user_uuid = ? LIMIT 1;";
        RowResultList row = tableManager.getRowResultList(sql, uuid.toString());
        return !row.isEmpty();
    }

    public void addUser(AuthUserInfo authUser) {
        RowResult row = new RowResult(tableManager);
        row.set("user_uuid", authUser.getUuid().toString());
        row.set("user_name", authUser.getName());
        row.set("user_auth_key", authUser.getAuthKey());
        row.set("user_logins", authUser.getLogins());
        row.set("user_failed_logins", authUser.getFailedLogins());
        row.set("user_last_ip", authUser.getLastIp());
        row.set("user_last_login", authUser.getLastLogin());
        row.insert();
    }

    public AuthUserInfo getUser(UUID uuid) {
        if (!isRegistered(uuid)) return null;
        String sql = "SELECT * FROM auth_users WHERE user_uuid = ? LIMIT 1;";
        RowResult row = tableManager.getRowResult(sql, uuid.toString());
        UUID userUuid = row.getUuid("user_uuid");
        String authKey = row.getString("user_auth_key");
        AuthUserInfo userInfo = new AuthUserInfo(userUuid, authKey);
        userInfo.setName(row.getString("user_name"));
        userInfo.setLogins(row.getLong("user_logins"));
        userInfo.setFailedLogins(row.getLong("user_failed_logins"));
        userInfo.setLastIp(row.getString("user_last_ip"));
        userInfo.setLastLogin(row.getLong("user_last_login"));
        return userInfo;
    }

    public void updateUser(AuthUserInfo authUser) {
        String sql = "SELECT * FROM auth_users WHERE user_uuid = ? LIMIT 1;";
        RowResult row = tableManager.getRowResult(sql, authUser.getUuid().toString());
        row.set("user_name", authUser.getName());
        row.set("user_logins", authUser.getLogins());
        row.set("user_failed_logins", authUser.getFailedLogins());
        row.set("user_last_ip", authUser.getLastIp());
        row.set("user_last_login", authUser.getLastLogin());
        row.update();
    }

}
