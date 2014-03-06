/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicsharefree;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alberto
 */
public class JSONCall {
    
    
    public String getUrl(String json)
    {
        int ini = json.indexOf("surl");
        String value = json.substring(ini);
        ini = value.indexOf("http://");
        int fin = value.indexOf(",");
        value = value.substring(ini, fin-1);
        value = value.replace("30s-96", "full-192");
        
        return value;
    }
    
    public String getTitle(String json)
    {
        int ini = json.indexOf("name");
        String value = json.substring(ini);
        int fin = value.indexOf(",");
        value = value.substring(8, fin-1);
        
        return value;
    }
    
    public String getSong(String id) throws MalformedURLException
    {
        URL url = new URL("http://musicsharefree.com/php/download.php?k="+id);
        HttpRequest g;
        g = new HttpRequest(url, "GET");
        String r = g.body();
        
        return r;
    }
    
    public void getSearch(String q) throws MalformedURLException
    {
        URL url = new URL("http://musicsharefree.com/php/search.php?q="+q);
        HttpRequest query = new HttpRequest(url, "GET");
        
        String json = query.body();
        //System.out.println(json);
        
        String rexk = "\"key\"\\: \"t[a-zA-Z0-9]*\",";
        String rexv = "\"name\"\\: \"[a-zA-Z0-9\\.\\:;, \\\\(\\)\\[\\]\\!\\¡\\?\\¿\\/áéíóú´`çñ\\-\\_\\{\\}\\$\\#\\@\\|\\=\\&%\\<\\>]*\",";
        //rex = "\\: \"[a-zA-Z0-9.\\(\\)\\[\\]]*\",";
        
        String string = "var1[value1], var2[value2], var3[value3]";
        Pattern patternk = Pattern.compile(rexk);
        Matcher matcherk = patternk.matcher(json);
        
        Pattern patternv = Pattern.compile(rexv);
        Matcher matcherv = patternv.matcher(json);

        List<String> songsk = new ArrayList<String>();
        List<String> songsv = new ArrayList<String>();

        while(matcherk.find())
        {
            songsk.add(matcherk.group(0));
        }
        while(matcherv.find())
        {
            songsv.add(matcherv.group(0));
        }

        String k;
        String v;
        
        /*
        for (String sv : songsv) {
            System.out.println(sv);
        }
        for (String sv : songsk) {
            System.out.println(sv);
        }*/
        
        System.out.println("Search Result: ");
        try {  
            for(int i=0;i<songsk.size();i++)
            {
                k = songsk.get(i).substring(8, songsk.get(i).length()-2);
                v = songsv.get(i).substring(8, songsv.get(i).length()-1);
                System.out.println("["+k+"]- "+v);
            }
        } catch (Exception e) {
            System.out.println("Error parsing result! Songs IDs could be wrong!");
        }
    }
}
