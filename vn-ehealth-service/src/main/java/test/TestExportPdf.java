package test;


import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;

import org.springframework.core.io.ClassPathResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.ehealth.emr.EmrCoSoKhamBenh;
import vn.ehealth.emr.EmrDanhSachHoSoBenhAn;
import vn.ehealth.emr.utils.ExportUtil;

public class TestExportPdf {
    
    static Gson gson = new GsonBuilder()
            .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
    
    static EmrCoSoKhamBenh getCoSoKhamBenh() throws IOException {
        var file = new ClassPathResource("static/json/cosokhambenh.json").getInputStream();
        var jsonSt = new String(file.readAllBytes());
        file.close();
        
        return gson.fromJson(jsonSt, EmrCoSoKhamBenh.class);
    }
    
    static EmrDanhSachHoSoBenhAn getDsHsba() throws IOException {
        var file = new ClassPathResource("static/json/hsba.json").getInputStream();
        var jsonSt = new String(file.readAllBytes());
        file.close();
        
        return gson.fromJson(jsonSt, EmrDanhSachHoSoBenhAn.class);
    }

    public static void main(String[] args) throws Exception {
        var coSoKhamBenh = getCoSoKhamBenh();
        System.out.println(coSoKhamBenh.ten);
        
        var dsHsba = getDsHsba();
        System.out.println(dsHsba.emrBenhNhan.tendaydu);
        
        var bytes = ExportUtil.exportPdf(dsHsba, coSoKhamBenh, "", "http://localhost:8080");
        var f = new FileOutputStream("C:/tmp/output.pdf");
        f.write(bytes);
        f.close();
        
        System.out.println("Done");
    }
}
