package vn.ehealth.web.util;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PageUtil {
    
    static Logger logger = LoggerFactory.getLogger(PageUtil.class);
    
	public static List<Map> pages = new ArrayList<>();
    public static String defaultPage;
    
    static {
    	try {
            var file = new ClassPathResource("static/json/layout.json").getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            var layout = mapper.readValue(file, Map.class);
            defaultPage = (String) layout.get("default_page");
            pages = (List<Map>) layout.get("pages");            
           
        }catch(IOException e) {
            logger.error("Error:", e);
        }
    	
    }
    
    public static Map[] getPages(Set<String> roles) {
    	var result = new ArrayList<Map>();
        for(var page : pages) {
            var assignRoles = (List<String>) page.get("roles");
            if(assignRoles.stream().anyMatch(x -> roles.contains(x))) {
                
                if(page.containsKey("children")) {
                    var childPages = (List<Map>) page.get("children");
                    var filterChildPages = new ArrayList<Map>();
                    
                    for(Map childPage : childPages) {
                        var childAssignRoles = (List<String>) childPage.get("roles");
                        if(childAssignRoles.stream().anyMatch(x -> roles.contains(x))) {
                            filterChildPages.add(childPage);
                        }
                    }
                    
                    if(filterChildPages.size() > 0) {
                        page.put("children", filterChildPages);
                        result.add(page);
                    }
                }else {
                    result.add(page);
                }
            }
        }
        return result.toArray(new Map[0]);
    }
    
    public static String getHtmlURL(Map[] pages, String pageId) {
        for(var page : pages) {
            if(page.get("id").equals(pageId)) {
                return (String) page.get("url");
            }
            
            if(page.containsKey("children")) {
                for(var childPage : (List<Map>) page.get("children")) {
                    if(childPage.get("id").equals(pageId)) {
                        return (String) childPage.get("url");                                
                    }
                }
            }
        }
        return "";
    }
}