package vn.ehealth.service.hsba;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/hsba")
public class HsbaController {
	
	static Logger logger = LoggerFactory.getLogger(HsbaController.class);
		
	@Autowired HsbaService hsbaService;
	

	@GetMapping("/count_ds_hs")
	public int countHsba(@RequestParam int trangthai, @RequestParam("ma_yte")String maYte) {
		return hsbaService.countDsHoSo(trangthai, maYte);		
	}
	
	@GetMapping("/get_ds_hs")
	public ResponseEntity<?> getDsHsbaChuaxuly(@RequestParam int trangthai ,
												@RequestParam("ma_yte")String maYte,
												@RequestParam int start, 
												@RequestParam int count) {
		
		var result = hsbaService.getDsHoSo(trangthai, maYte, start, count);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get_thongtin_hanhchinh")
    public ResponseEntity<?> getThongTinHanhChinh(@RequestParam("hoso_id") int hoSoId) {
        var hsba = hsbaService.getHsba(hoSoId);
        int benhNhanId = (int) hsba.get("idbenhnhan");
        var thongTinHoSo = hsbaService.getThongTinHoSo(hoSoId);
        var thongTinhBenhNhan = hsbaService.getThongTinBenhNhan(benhNhanId);
        
        var result = Map.of(
                    "thongtin_hoso", thongTinHoSo,
                    "thongtin_benhnhan" , thongTinhBenhNhan
                );
        
        return ResponseEntity.ok(result);
    }
	
	@GetMapping("/get_thongtin_nguoibenh")
    public ResponseEntity<?> getThongTinNguoiBenh(@RequestParam("hoso_id") int hoSoId) {
	    var thongTinVaoVien = hsbaService.getThongTinVaoVien(hoSoId);
	    var thongTinVaoKhoa = hsbaService.getThongTinVaoKhoa(hoSoId);
	    var thongTinRaVien = hsbaService.getThongTinRaVien(hoSoId);
	    
	    var result = Map.of(
	            "thongtin_vaovien", thongTinVaoVien,
	            "thongtin_vaokhoa", thongTinVaoKhoa,
	            "thongtin_ravien", thongTinRaVien
	     );
	    
	    return ResponseEntity.ok(result);
	            
	}
	
	@GetMapping("/get_thongtin_chandoan")
    public ResponseEntity<?> getThongTinChanDoan(@RequestParam("hoso_id") int hoSoId) {
        var thongTinChanDoanVaoVien = hsbaService.getThongTinChanDoanVaoVien(hoSoId);
        var thongTinChanDoanVaoKhoa = hsbaService.getThongTinChanDoanVaoKhoa(hoSoId);
        var thongTinChanDoanRaVien = hsbaService.getThongTinChanDoanRaVien(hoSoId);
        
        var result = Map.of(
                "chandoan_vaovien", thongTinChanDoanVaoVien,
                "chandoan_vaokhoa", thongTinChanDoanVaoKhoa,
                "chandoan_ravien", thongTinChanDoanRaVien
         );
        
        return ResponseEntity.ok(result);
                
    }
	
	@GetMapping("/get_tinhtrang_ravien")
    public ResponseEntity<?> getTinhTrangRaVien(@RequestParam("hoso_id") int hoSoId) {
        var tinhTrangRaVien = hsbaService.getTinhTrangRaVien(hoSoId);
        var ttLanhDao = hsbaService.getThongTinLanhDao(hoSoId);
        
        var result = new HashMap<String, Object>();
        result.putAll(tinhTrangRaVien);
        result.putAll(ttLanhDao);
        
        return ResponseEntity.ok(result);
                
    }
}
