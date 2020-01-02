package test;

import java.io.IOException;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;


import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.utils.EmrUtils;

public class TestCDA {

    static ObjectMapper mapper = EmrUtils.createObjectMapper();
    
    static EmrHoSoBenhAn getHsba() throws IOException {
        var file = new ClassPathResource("static/json/hsba.json").getInputStream();
        var jsonSt = new String(file.readAllBytes());
        file.close();
        
        return mapper.readValue(jsonSt, EmrHoSoBenhAn.class);
    }
    
    public static void main(String[] args) throws Exception {
        
    }
}
