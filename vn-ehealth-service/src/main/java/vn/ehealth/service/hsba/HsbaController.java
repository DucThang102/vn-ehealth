package vn.ehealth.service.hsba;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.jasperreports.engine.JRException;
import vn.ehealth.emr.EmrDanhSachHoSoBenhAn;
import vn.ehealth.emr.utils.ExportUtil;
import vn.ehealth.emr.utils.JasperUtils;


@RestController
@RequestMapping("/api/hsba")
public class HsbaController {
	
	static Logger logger = LoggerFactory.getLogger(HsbaController.class);
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	JasperUtils jasperUtils = new JasperUtils();
	
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
	@Autowired RawHsbaService rawHsbaService;
	
	@Autowired HsbaService hsbaService;
	
	@GetMapping("/test")
	public String test() {
	    return "";	    
	}

	@GetMapping("/count_ds_hs")
	public int countHsba(@RequestParam int trangthai, @RequestParam("ma_yte")String maYte) {
		return rawHsbaService.countDsHoSo(trangthai, maYte);		
	}
	
	@GetMapping("/get_ds_hs")
	public ResponseEntity<?> getDsHsbaChuaxuly(@RequestParam int trangthai ,
												@RequestParam("ma_yte")String maYte,
												@RequestParam int start, 
												@RequestParam int count) {
		
		var result = rawHsbaService.getDsHoSo(trangthai, maYte, start, count);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get_hs")
    public ResponseEntity<?> getHs(@RequestParam("hoso_id") int hoSoId) {
        
        var result = hsbaService.getEmrDanhSachHoSoBenhAnById(hoSoId);
        
        result.ifPresent(x -> {
            var coSoKhamBenh = hsbaService.getCoSoKhamBenh();
            
            x.emrCoSoKhamBenh = coSoKhamBenh;
            x.emrBenhNhan.tuoi = jasperUtils.getTuoi(x);
            
            x.ngaytiepnhan = x.ngaytao;
            x.nguoitiepnhan = "";
            
            if(StringUtils.isEmpty(x.donvichuquan)) 
                x.donvichuquan = coSoKhamBenh.donvichuquan;
            
            if(StringUtils.isEmpty(x.tenbenhvien)) 
                x.tenbenhvien = coSoKhamBenh.ten;
            
            if(StringUtils.isEmpty(x.truongphongth)) 
                x.truongphongth = coSoKhamBenh.truongphongth;
        });        
        
        return ResponseEntity.of(result);
    }
	
	@GetMapping("/get_thongtin_hoso")
    public ResponseEntity<?> getThongTinHoSo(@RequestParam("hoso_id") int hoSoId) {
        var hsba = rawHsbaService.getHsba(hoSoId);        
        return ResponseEntity.ok(hsba);
	}
	
	@GetMapping("/get_thongtin_hanhchinh")
    public ResponseEntity<?> getThongTinHanhChinh(@RequestParam("hoso_id") int hoSoId) {
        var hsba = rawHsbaService.getHsba(hoSoId);
        int benhNhanId = (int) hsba.get("idbenhnhan");
        var thongTinHoSo = rawHsbaService.getThongTinHoSo(hoSoId);
        var thongTinhBenhNhan = rawHsbaService.getThongTinBenhNhan(benhNhanId);
        
        var result = Map.of(
                    "thongtin_hoso", thongTinHoSo,
                    "thongtin_benhnhan" , thongTinhBenhNhan
                );
        
        return ResponseEntity.ok(result);
    }
	
	@GetMapping("/get_thongtin_nguoibenh")
    public ResponseEntity<?> getThongTinNguoiBenh(@RequestParam("hoso_id") int hoSoId) {
	    var thongTinVaoVien = rawHsbaService.getThongTinVaoVien(hoSoId);
	    var thongTinVaoKhoa = rawHsbaService.getThongTinVaoKhoa(hoSoId);
	    var thongTinRaVien = rawHsbaService.getThongTinRaVien(hoSoId);
	    
	    var result = Map.of(
	            "thongtin_vaovien", thongTinVaoVien,
	            "thongtin_vaokhoa", thongTinVaoKhoa,
	            "thongtin_ravien", thongTinRaVien
	     );
	    
	    return ResponseEntity.ok(result);
	            
	}
	
	@GetMapping("/get_thongtin_chandoan")
    public ResponseEntity<?> getThongTinChanDoan(@RequestParam("hoso_id") int hoSoId) {
        var thongTinChanDoanVaoVien = rawHsbaService.getThongTinChanDoanVaoVien(hoSoId);
        var thongTinChanDoanVaoKhoa = rawHsbaService.getThongTinChanDoanVaoKhoa(hoSoId);
        var thongTinChanDoanRaVien = rawHsbaService.getThongTinChanDoanRaVien(hoSoId);
        
        var result = Map.of(
                "chandoan_vaovien", thongTinChanDoanVaoVien,
                "chandoan_vaokhoa", thongTinChanDoanVaoKhoa,
                "chandoan_ravien", thongTinChanDoanRaVien
         );
        
        return ResponseEntity.ok(result);
                
    }
	
	@GetMapping("/get_tinhtrang_ravien")
    public ResponseEntity<?> getTinhTrangRaVien(@RequestParam("hoso_id") int hoSoId) {
        var tinhTrangRaVien = rawHsbaService.getTinhTrangRaVien(hoSoId);
        var ttLanhDao = rawHsbaService.getThongTinLanhDao(hoSoId);
        
        var result = new HashMap<String, Object>();
        result.putAll(tinhTrangRaVien);
        result.putAll(ttLanhDao);
        
        return ResponseEntity.ok(result);                
    }	
	
	@GetMapping("/view_pdf")
	public ResponseEntity<?> viewPdf(@RequestParam("idhsba") int idhsba,
	                                @RequestParam(value="loai_report", required=false, defaultValue="") 
	                                String loaiReport) {
	    
	    var coSoKhamBenh = hsbaService.getCoSoKhamBenh();	    	    
	    var dsHsba = hsbaService.getEmrDanhSachHoSoBenhAnById(idhsba);
	    
	    System.out.println(coSoKhamBenh.ten);
	    
	    if(dsHsba.isPresent()) {
	        try {
	            var data = ExportUtil.exportPdf(dsHsba.get(), coSoKhamBenh, loaiReport, "");
	            var resource = new ByteArrayResource(data);
	            
	            return ResponseEntity.ok()
	                    .contentLength(data.length)
	                    .contentType(MediaType.parseMediaType("application/pdf"))
	                    .body(resource);
	        }catch(JRException | IOException  | NullPointerException e) {
	            logger.error("Error exporting pdf :", e);
	        }	        
	    }
	    
	    
	    return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/add_hoso")
	public String addHoSo(@RequestBody String jsonSt) {
	    var hsba = gson.fromJson(jsonSt, EmrDanhSachHoSoBenhAn.class);
	    System.out.println(hsba.mayte);
	    return "OK";
	}
}
