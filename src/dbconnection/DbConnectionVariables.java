package dbconnection;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 *@author: DABAGIRE Valens
 * @description : Save DB connection variables in a properties file
 * */

public class DbConnectionVariables {
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private String databasePort;
    private Long port;

    public DbConnectionVariables(String dbUrl, String dbUsername, String dbPassword, String dbPort, Long port) {
        this.databaseUrl = dbUrl;
        this.databaseUsername = dbUsername;
        this.databasePassword = dbPassword;
        this.databasePort = dbPort;
        this.port = port;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public void saveDbConnectionVariablesInFile() throws IOException {

        Properties properties = new Properties();
        properties.setProperty("dbUrl", getDatabaseUrl());
        properties.setProperty("dbUsername", getDatabaseUsername());
        properties.setProperty("dbPassword", getDatabasePassword());
        properties.setProperty("dbPort", getDatabasePort().toString());
        properties.setProperty("serverPort", getPort().toString());

        properties.store(new FileWriter("dbConfig.properties"), null);
    }
}
