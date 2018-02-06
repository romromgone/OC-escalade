package com.ocp3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DaoFactory {
	private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
        	throw new DaoConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }

        DaoFactory instance = new DaoFactory("jdbc:postgresql://localhost:5432/escalade", "postgres", "admin");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Récupération du Dao
    public UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDaoImpl(this);
    }
    public TopoDao getTopoDao() {
        return new TopoDaoImpl(this);
    }
    public ReservationDao getReservationDao() {
        return new ReservationDaoImpl(this);
    }
    public CommentaireTopoDao getCommentaireTopoDao() {
        return new CommentaireTopoDaoImpl(this);
    }
}
