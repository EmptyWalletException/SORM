package com.kingguanzhang.sorm.bean;
/*
* Packaging manage configuration information
*
* */
public class Configuration {
    private String driver;
    private String URL;
    private String user;
    private String pwd;
    private String usingDB;
    private String srcPath;
    private String poPackage;
    private int poolMinSize;
    private int poolMaxSize;


    public int getPoolMinSize() {
        return poolMinSize;
    }

    public void setPoolMinSize(int poolMinSize) {
        this.poolMinSize = poolMinSize;
    }

    public int getPoolMaxSize() {
        return poolMaxSize;
    }

    public void setPoolMaxSize(int poolMaxSize) {
        this.poolMaxSize = poolMaxSize;
    }




    public String getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(String queryClass) {
        this.queryClass = queryClass;
    }

    private String queryClass;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsingDB() {
        return usingDB;
    }

    public void setUsingDB(String usingDB) {
        this.usingDB = usingDB;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }

    public Configuration(String driver, String URL, String user, String pwd, String usingDB, String srcPath, String poPackage, int poolMinSize, int poolMaxSize, String queryClass) {
        this.driver = driver;
        this.URL = URL;
        this.user = user;
        this.pwd = pwd;
        this.usingDB = usingDB;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
        this.poolMinSize = poolMinSize;
        this.poolMaxSize = poolMaxSize;
        this.queryClass = queryClass;
    }

    public Configuration() {
    }
}
