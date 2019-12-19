package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HSBAFactory;
import vn.ehealth.emr.cda.CDAExportUtil;
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
        var doc = HSBAFactory.eINSTANCE.createHsbaDocument().init();
        var hsba = getHsba();
        var emrParameters = new HashMap<String, String>();
        var emrDmChucNangs = new HashMap<String, Integer>();
        var properties = new Properties();
        CDAExportUtil.generateCDAHeader(doc, hsba, emrParameters, hsba.emrCoSoKhamBenh, properties);
        CDAExportUtil.generateCDABody(doc, hsba, emrParameters, emrDmChucNangs, properties, "");
        
        var fos = new FileOutputStream(new File("C:/tmp/cda.xml"));
        CDAExportUtil.save(doc, fos, "cda.xsl");
    }
}
