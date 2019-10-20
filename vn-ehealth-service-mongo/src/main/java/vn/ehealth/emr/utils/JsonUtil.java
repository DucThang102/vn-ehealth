package vn.ehealth.emr.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
            
    private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    public static String dumpObject(Object obj) {
        mapper.setDateFormat(sdf);
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Cannot dump object to json ", e);
        }
        return "";
    }
}
