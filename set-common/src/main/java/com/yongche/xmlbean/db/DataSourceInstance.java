package com.yongche.xmlbean.db;

import org.simpleframework.xml.Attribute;

/**
* @author ash
*/
public class DataSourceInstance {

    @Attribute(required = true)
    private String host;

    @Attribute(required = false)
    private int port;

    @Attribute(required = true)
    private String db;

    @Attribute(required = true)
    private String username;

    @Attribute(required = true)
    private String password;

    @Attribute(required = false)
    private int connectionTimeout;

    @Attribute(required = false)
    private int maximumPoolSize;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }
}
