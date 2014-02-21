package com.vjr.meta;


import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

public class MetaParser {

    private static final Logger          LOGGER      = LoggerFactory.getLogger(MetaParser.class);
    
    public static String fetch(String url) {
        boolean done = false;
        int numberOfRedirects = 0;
        try {
            while(!done) {
                ResponseEntity<String> response = RestTemplateUtil.getInstance().exchange(url, HttpMethod.GET, RestTemplateUtil.getHttpEntityWithUAHeader(), String.class);
                if(response.getStatusCode().toString().startsWith("20")) {
                    return response.getBody();
                }
                if(response.getStatusCode().toString().startsWith("30")) {
                    URI redirectLocation = response.getHeaders().getLocation();
                    url = redirectLocation.toString();
                    if(StringUtils.isBlank(url)) {
                        return null;
                    }
                    if(numberOfRedirects == 5) {
                        done = true;
                    }
                    numberOfRedirects++;
                }
            }
        }
        catch (Exception e) {
            LOGGER.warn("Exception in fetch : " + e.getMessage());
        }
        return null;
    }

    public static Map<String, Object> parse(String url) {
        Map<String, Object> result = getDefaultResult(url);
        String response = fetch(url);
        if(StringUtils.isNotBlank(response)) {
            Document doc = Jsoup.parse(response);
            result.put("title", getTitle(doc));
            result.put("description", getDescription(doc));
            result.put("images", preprocessImages(getImages(doc), url));
        }
        return result;
    }

    private static List<String> preprocessImages(Set<String> images, String url) {
        List<String> result = new ArrayList<String>();
        StringBuffer sb = new StringBuffer(url);
        if(url.endsWith("/")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        for(String image : images) {
            if(!image.startsWith("http")) {
                result.add(sb.append(image).toString());
            }
            else {
                result.add(image);
            }
        }
        return result;
    }

    private static Map<String, Object> getDefaultResult(String url) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("url", url);
        result.put("title", "");
        result.put("description", "");
        result.put("images", new ArrayList<String>());
        return result;
    }

    private static String getTitle(Document doc) {
        String title = renameThisMethod(doc, Constants.META_TITLES);
        try {
            if(StringUtils.isBlank(title)) {
                title = doc.title();
            }
        }
        catch (Exception e) {
            LOGGER.warn("Exception in getTitle : " + e.getMessage());
        }
        return title;
    }

    private static String getDescription(Document doc) {
        String description = renameThisMethod(doc, Constants.META_DESCRIPTIONS);
        try {
            if(StringUtils.isBlank(description)) {
                Elements paragraphs = doc.body().getElementsByTag("<p>");
                if(!CollectionUtils.isEmpty(paragraphs)) {
                    description = paragraphs.get(0).html();
                }
            }
        }
        catch (Exception e) {
            LOGGER.warn("Exception in getDescription : " + e.getMessage());
        }
        return description;
    }

    private static Set<String> getImages(Document doc) {
        Set<String> result = new LinkedHashSet<String>();
        String image = renameThisMethod(doc, Constants.META_IMAGES);
        try {
            if(StringUtils.isNotBlank(image)) {
                result.add(image);
            }
            Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            if(!CollectionUtils.isEmpty(images)) {
                for(Element img : images) {
                    result.add(img.attr("src"));
                }
            }
        }
        catch (Exception e) {
            System.out.println("Exception in getImages : " + e.getMessage());
        }
        return result;
    }

    private static String renameThisMethod(Document doc, Map<String, String[]> map) {
        String result = null;
        for(String key : map.keySet()) {
            for(String desc : map.get(key)) {
                try {
                    String description = doc.select("meta[" + key + "=" + desc + "]").get(0).attr("content");
                    if(StringUtils.isNotBlank(description)) {
                        result = description;
                        break;
                    }
                }
                catch (Exception e) {
                    LOGGER.warn("Exception finding key :- " + key + ", val :- " + desc + ", in doc :- " + e.getMessage());
                }
            }
        }

        return result;
    }
}
