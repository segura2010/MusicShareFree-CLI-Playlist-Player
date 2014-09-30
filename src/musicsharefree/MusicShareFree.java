/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicsharefree;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;
import javax.print.attribute.standard.Media;
import javax.rmi.CORBA.Util;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import sun.net.www.http.HttpClient;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 *
 * @author alberto
 */
public class MusicShareFree {

    /**
     * @param args the command line arguments
     * @throws javazoom.jlgui.basicplayer.BasicPlayerException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws BasicPlayerException, MalformedURLException, IOException, ClassNotFoundException, SQLException, NoSuchMethodException {
        BasicPlayer test = new BasicPlayer();
        Menu m = new Menu();
        
        Scanner teclado=new Scanner(System.in);
        String te;
        
        Logger.getLogger(BasicPlayer.class.getName()).setLevel(Level.OFF);
        
        while(true)
        {
            System.out.print("[MSF CLI Private]> ");
            te = teclado.next();
            try
            {
                m.exec(te);
            } catch (Exception e) {
                System.out.println("Something bad ocurred.. \n"+e);
            }
        }
                
        
        
    }
}
