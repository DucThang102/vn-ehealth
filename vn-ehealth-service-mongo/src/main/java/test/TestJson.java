package test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ehealth.emr.EmrHoSoBenhAn;
import vn.ehealth.validate.JsonValidator;

public class TestJson {
    static String hsbaJsonSt;
    static String hsbaSchemaJsonSt;
    static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static JsonValidator validator = new JsonValidator();
    
    static ObjectMapper mapper = new ObjectMapper();
    static class Test {
        int val;
    }
    
    static {
        try {
            hsbaJsonSt = new String(new ClassPathResource("static/json/hsba2.json").getInputStream().readAllBytes());
            hsbaSchemaJsonSt = new String(new ClassPathResource("static/json/hsba_schema.json").getInputStream().readAllBytes()); 
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        mapper.setDateFormat(sdf);
        
        try {
            var errors = validator.validate(hsbaJsonSt, hsbaSchemaJsonSt);
            
            for(var error : errors) {
                System.out.println(error.message);
            }
            
            var hsba = mapper.readValue(hsbaJsonSt, EmrHoSoBenhAn.class);
            System.out.println(hsba.mayte);
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Done");
    }
}
