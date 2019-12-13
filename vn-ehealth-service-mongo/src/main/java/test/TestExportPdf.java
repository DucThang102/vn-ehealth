package test;


import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.utils.ExportUtil;

public class TestExportPdf {
    
    //static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static ObjectMapper mapper = new ObjectMapper();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    
    static EmrHoSoBenhAn getHsba() throws IOException {
        mapper.setDateFormat(sdf);
        var file = new ClassPathResource("static/json/hsba.json").getInputStream();
        var jsonSt = new String(file.readAllBytes());
        file.close();
        
        return mapper.readValue(jsonSt, EmrHoSoBenhAn.class);
    }

    public static void main(String[] args) throws Exception {
        
        var hsba = getHsba();
        
        var bytes = ExportUtil.exportPdf(hsba, "http://localhost:8080");
        var f = new FileOutputStream("C:/tmp/output.pdf");
        f.write(bytes);
        f.close();
        
        System.out.println("Done");
    }
}
