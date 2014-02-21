package com.vjr.meta;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class Constants {
    
    public static final String CHROME_USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
    
    public static final LinkedHashMap<String, String[]> META_TITLES = new LinkedHashMap<String, String[]>() {{
        put("name",new String[] { "title", "og:title", "twitter:title" });  
        put("itemprop",new String[] { "name" });  
        
    }};
    
    public static final LinkedHashMap<String, String[]> META_DESCRIPTIONS = new LinkedHashMap<String, String[]>() {{
        put("name",new String[] { "description", "og:description", "twitter:description" });  
        put("itemprop",new String[] { "description" });  
        
    }};
    
    public static final LinkedHashMap<String, String[]> META_IMAGES = new LinkedHashMap<String, String[]>() {{
        put("name",new String[] { "image", "og:image", "twitter:image:src" });  
        put("itemprop",new String[] { "image" });  
        
    }};
    
}
