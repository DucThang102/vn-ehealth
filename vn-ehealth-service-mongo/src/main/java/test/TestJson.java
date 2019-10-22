package test;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ehealth.emr.EmrHoSoBenhAn;
import vn.ehealth.validate.ErrorMessage;
import vn.ehealth.validate.JsonParser;

public class TestJson {
    static String hsbaJsonSt;
    static String hsbaSchemaJsonSt;
    static JsonParser jsonParser = new JsonParser();
    
    static ObjectMapper mapper = new ObjectMapper();
    
    static {
        try {
            hsbaJsonSt = new String(new ClassPathResource("static/json/hsba/2155.json").getInputStream().readAllBytes());
            hsbaSchemaJsonSt = new String(new ClassPathResource("static/json/hsba_schema.json").getInputStream().readAllBytes()); 
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        
        try {
            var errors = new ArrayList<ErrorMessage>();
            var objMap = jsonParser.parseJson(hsbaJsonSt, hsbaSchemaJsonSt, errors);
            
            for(var error : errors) {
                System.out.println(error.message);
            }
            
            if(errors.size() == 0) {
                var hsba = mapper.convertValue(objMap, EmrHoSoBenhAn.class);
                System.out.println(hsba.mayte);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Done");
    }
}
