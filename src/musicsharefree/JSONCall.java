/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicsharefree;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
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
        JsonObject jsonObject = JsonObject.readFrom( json );
        String value = jsonObject.get("result").asObject().get("surl").asString();
        value = value.replace("30s-96", "full-192");
        
        return value;
    }
    
    public String getTitle(String json)
    {
        JsonObject jsonObject = JsonObject.readFrom( json );
        String value = jsonObject.get("result").asObject().get("name").asString();
        
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
    
    public ArrayList getSearch(String q) throws MalformedURLException
    {
        q = q.replace(" ", "%20"); // URL spaces.. 
        URL url = new URL("http://musicsharefree.com/php/search.php?q="+q);
        HttpRequest query = new HttpRequest(url, "GET");
        
        String json = query.body();

        ArrayList<String> ret = new ArrayList();
        String key, title;
        
        JsonObject jsonObject = JsonObject.readFrom( json );
        JsonArray results = jsonObject.get("result").asObject().get("results").asArray();
       
        for(JsonValue o : results)
        {
            key = o.asObject().get("key").asString();
            title = o.asObject().get("name").asString();
            System.out.println("["+key+"] - "+title);
            ret.add(key);
        }
              
        return ret;
    }
}
