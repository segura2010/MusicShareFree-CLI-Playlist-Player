/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicsharefree;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author alberto
 */
public class Menu {
    private Database db = new Database();
    Scanner teclado = new Scanner(System.in);
    BasicPlayer playerB = new BasicPlayer();
    PlayerController player = null;
    
    private boolean isRandom = false;
    
    public Menu() throws ClassNotFoundException, SQLException
    {
        db.connect();
        player = new PlayerController(playerB);
    }
    
    public void exec(String cmd) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        try
        {
            (getClass().getMethod(cmd)).invoke(this, null);
        } catch (Exception e) {
            System.out.println(e.getCause());
            help();
        }
    }
    
    public void pls() throws SQLException
    {
        // List playlists
        ResultSet playlist = db.getPlaylist();
        System.out.println("Playlist: ");
        while(playlist.next())
        {
            System.out.println("["+playlist.getString(1)+"]- "+playlist.getString(2));
        }
    }
    
    public void sls() throws SQLException
    {
        System.out.print("Playlist ID: ");
        String idP = teclado.next();
        
        ResultSet playlist = db.getPlaylist(idP);
        System.out.println("Songs on PLaylist: ");
        while(playlist.next())
        {
            System.out.println("["+playlist.getString(2)+"]- "+playlist.getString(3));
        }
    }
    
    public void padd() throws SQLException
    {
        System.out.print("Nombre de playlist: ");
        String title = teclado.next();
        db.insert(title);
    }
    
    public void sadd() throws SQLException
    {
        JSONCall song = new JSONCall();
        System.out.print("Playlist ID: ");
        String idP = teclado.next();
        System.out.print("Song ID: ");
        String idS = teclado.next();
        String jsonS = null;
        try {
            jsonS = song.getSong(idS);
        } catch (Exception e) {
            System.out.println("Something bad.. \n"+e);
        }
        
        String url = song.getUrl(jsonS);
        String name = song.getTitle(jsonS);
        db.insert(idP, idS, name);
    }
    
    public void prm() throws SQLException
    {
        JSONCall song = new JSONCall();
        System.out.print("Playlist ID: ");
        String idP = teclado.next();
        
        db.remove(idP);
    }
    
    public void srm() throws SQLException
    {
        JSONCall song = new JSONCall();
        System.out.print("Playlist ID: ");
        String idP = teclado.next();
        System.out.print("Song ID: ");
        String idS = teclado.next();
        
        db.remove(idP, idS);
    }
    
    public void pplay() throws SQLException
    {
        player = new PlayerController(playerB);
        player.setRandom(isRandom);
        JSONCall song = new JSONCall();
        System.out.print("Playlist ID: ");
        String idP = teclado.next();
        
        ResultSet playlist = db.getPlaylist(idP);
        
        ArrayList<String> songs = new ArrayList();
        while(playlist.next())
        {
            songs.add(playlist.getString(2));
        }
        
        System.out.println(songs.size()+" songs loaded!");
        player.setPlaylist(songs);
        player.start();
    }
    
    public void play() throws BasicPlayerException
    {
        playerB.resume();
    }
    
    public void pause() throws BasicPlayerException
    {
        playerB.pause();
    }
    
    public void next() throws BasicPlayerException
    {
        playerB.stop();
    }
    
    public void random()
    {
        if(isRandom)
            isRandom = false;
        else
            isRandom = true;
        
        System.out.println("Random Mode selected to: "+isRandom);
    }
    
    public void q()
    {
        System.exit(0);
    }
    
    public void help()
    {
        System.out.println("Commands: "
                + "\nX can be: 'p' or 's' (without quotes)"
                + "\nXls - list playlist or songs."
                + "\nXadd - add playlist or song."
                + "\nXrm - remove playlist or song."
                + "\nsearch - search a song (You can use this to get song's ID and add to a playlist."
                + "\nrandom - on/off random mode."
                + "\npplay - plays a playlist."
                + "\npause - pause a song when is playing. (Have some bugs..)"
                + "\nplay - resume a song when is paused. (Have some bugs..)"
                + "\nnext - plays the next song of the playlist.");
    }
    
    public void search() throws MalformedURLException
    {
        System.out.print("What do you want search? ");
        String q = teclado.next();
        
        JSONCall search = new JSONCall();
        search.getSearch(q);
    }
}
