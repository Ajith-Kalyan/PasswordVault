package database.model;

public class Note  {

    public static final String TABLE_NAME = "data";

    //column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WEBSITE= "website";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String TIMESTAMP = "timestamp";


    private int id;
    private String website ="";
    private String username = "";
    private String password ="";
    private int timestamp ;

    //SQL Query ==> Create Table

 /*   public static final String CREATE_TABLE = " CREATE TABLE "+TABLE_NAME+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMN_USERNAME+" TEXT,"+COLUMN_PASSWORD+" TEXT,"+COLUMN_WEBSITE+" TEXT,"+TIMESTAMP+" DATETIME DEFAULT CURRENT_TIMESTAMP "+" )";*/

    public static final String CREATE_TABLE = " CREATE TABLE "+TABLE_NAME+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMN_USERNAME+" TEXT,"+COLUMN_PASSWORD+" TEXT,"+COLUMN_WEBSITE+" TEXT,"+TIMESTAMP+" INTEGER"+" )";

    public Note(int id, String username, String password, String website, int timestamp) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.website = website;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Note() {

    }


}
