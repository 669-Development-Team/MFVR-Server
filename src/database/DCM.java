package database;

// Java Imports

import javax.sql.DataSource;

/**
 * Database Connection Manager
 * The dcm class manages the database connection for executing queries. Uses the
 * GameDB class to create the connection.
 */
public final class DCM {

    // Singleton Instance
    private static DCM dcm;
    // dcm Variables
    private GameDB gameDB;
    private DataSource datasource;

    /**
     * Initialize the dcm and create the connection to the database.
     */
    private DCM() {
        gameDB = new GameDB();
        datasource = gameDB.getDataSource();
    }

    /**
     * Instantiates the dcm on first execution and return the instance.
     *
     * @return the instance of the dcm
     */
    public static DCM getInstance() {
        if (dcm == null) {
            dcm = new DCM();
        }

        return dcm;
    }

    /**
     * Retrieve the data source.
     *
     * @return the data source
     */
    public static DataSource getDataSource() {
        return dcm.datasource;
    }
}