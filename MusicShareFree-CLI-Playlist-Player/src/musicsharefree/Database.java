/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicsharefree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author alberto
 */
public class Database {
    private Connection c;
    private Statement query;
    
    public void connect() throws ClassNotFoundException, SQLException
    {
        String data = "data.db";
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:"+data);
        query = c.createStatement();
        
        query.executeUpdate("CREATE TABLE IF NOT EXISTS playlist (id INTEGER PRIMARY KEY AUTOINCREMENT, title varchar(30))");
        query.executeUpdate("CREATE TABLE IF NOT EXISTS playlistSongs (playlist PRIMEARY KEY references playlist(id), songId varchar(20) not null, songName varchar(50))");
    }
    
    public void insert(String tP) throws SQLException
    {
        try {
            query.executeUpdate("INSERT INTO playlist(title) VALUES('"+tP+"')");
        } catch (Exception e) {
            System.out.println("Something bad.. \n"+e);
        }
        
        
    }
    
    public void insert(String idP, String idS, String tSong) throws SQLException
    {
        try
        {
            query.executeUpdate("INSERT INTO playlistSongs VALUES('"+idP+"', '"+idS+"', '"+tSong+"')");
        } catch (Exception e) {
            System.out.println("Something bad.. \n"+e);
        }
    }
    
    public ResultSet getPlaylist() throws SQLException
    {
        return query.executeQuery("SELECT * FROM playlist");
    }
    
    public ResultSet getPlaylist(String idP) throws SQLException
    {
        return query.executeQuery("SELECT * FROM playlistSongs WHERE playlist='"+idP+"'");
    }
    
    public void remove(String idP, String idS) throws SQLException
    {
        try
        {
            query.executeUpdate("DELETE FROM playlistSongs WHERE playlist='"+idP+"' AND songId='"+idS+"'");
        } catch (Exception e) {
            System.out.println("Something bad.. \n"+e);
        }
    }
    
    public void remove(String idP) throws SQLException
    {
        try
        {
            query.executeUpdate("DELETE FROM playlistSongs WHERE playlist='"+idP+"' ");
            query.executeUpdate("DELETE FROM playlist WHERE id='"+idP+"' ");
        } catch (Exception e) {
            System.out.println("Something bad.. \n"+e);
        }
    }
    
}
