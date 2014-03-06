/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicsharefree;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import sun.misc.PerformanceLogger;

/**
 *
 * @author alberto
 */
public class PlayerController extends Thread {
    BasicPlayer player = null;
    final static int STOPPED = 2;
    
    private Handler handler;
    
    private ArrayList<String> playlist = null;
    private Random random = new Random();
    private boolean isRandom = false;
    
    PlayerController(BasicPlayer p)
    {
        player = p;
    }
    
    public void setRandom(boolean r)
    {
        isRandom = r;
    }
    
    public void run()
    {
        try {
            if(!isRandom)
                playInOrder();
            else
                playRandom();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setPlaylist(ArrayList p)
    {
        playlist = p;
    }
    
    public void playInOrder() throws SQLException, MalformedURLException, BasicPlayerException, InterruptedException, IOException
    {
        JSONCall song = new JSONCall();
        String url = null, json = null;
        while(!playlist.isEmpty())
        {
            json = song.getSong(playlist.get(0));
            url = song.getUrl(json);
            try {
                player.open(new URL(url));
                player.play();
            } catch (Exception e) {
                playInOrder();
            }
            System.out.println("Loading, please wait..");
            Thread.sleep(8000); // 8sec to prepare playing
            System.out.println("Im playing");
            while(player.getStatus() != STOPPED)
            {   // While song not end.
                Thread.sleep(2500);
            }
            // Song finished! Other song! This song can be deleted.
            playlist.remove(0);
        }
        
        System.out.println("Playlist end.");
        
    }
    
    public void playRandom() throws SQLException, MalformedURLException, BasicPlayerException, InterruptedException, IOException
    {
        int r;
        JSONCall song = new JSONCall();
        String url = null, json = null;
        while(!playlist.isEmpty())
        {
            r = random.nextInt(playlist.size());
            json = song.getSong(playlist.get(r));
            url = song.getUrl(json);
            try {
                player.open(new URL(url));
                player.play();
            } catch (Exception e) {
                playInOrder();
            }
            System.out.println("Loading, please wait..");
            Thread.sleep(8000); // 8sec to prepare playing
            System.out.println("Im playing");
            while(player.getStatus() != STOPPED)
            {   // While song not end.
                Thread.sleep(2500);
            }
            // Song finished! Other song! This song can be deleted.
            playlist.remove(r);
        }
        
        System.out.println("Playlist end.");
        
    }
}
