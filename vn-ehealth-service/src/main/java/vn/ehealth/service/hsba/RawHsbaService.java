package vn.ehealth.service.hsba;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.ehealth.service.utils.SQLUtil;

@Service
public class RawHsbaService {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	String adjustDsHoSoSql(String rawSql, int trangthai, String maYte, List<Object> params) {
		params.add(trangthai);
		
		String sql = rawSql;
		if(StringUtils.isEmpty(maYte)) {
			sql = sql.replace("[MA_YTE_CHECK]", "");			
		}else {
			sql = sql.replace("[MA_YTE_CHECK]", "AND ba.mayte LIKE ?");
			params.add("%" + maYte + "%");
		}
		
		return sql;
	}
	
	public int countDsHoSo(int trangthai, String maYte) {
		String sql = SQLUtil.getSQL("COUNT_HS");
		var params = new ArrayList<Object>();
		sql = adjustDsHoSoSql(sql, trangthai, maYte, params);
		return jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
		
	}

	public List<Map<String, Object>> getDsHoSo(int trangthai, String maYte, int start, int count) {
		String sql = SQLUtil.getSQL("DS_HS");
		var params = new ArrayList<Object>();
		sql = adjustDsHoSoSql(sql, trangthai, maYte, params);
		return SQLUtil.getRecords(jdbcTemplate, sql, params.toArray(), start, count);
	}
	
	public Map<String, Object> getThongTinHoSo(int hoSoId) {
	    String sql = SQLUtil.getSQL("THONGTIN_HOSO");
	    return SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {hoSoId});
	}
	
	public Map<String, Object> getThongTinBenhNhan(int benhNhanId) {
	    String sql = SQLUtil.getSQL("THONGTIN_BENHNHAN");
	    return SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {benhNhanId});
	}
	
	public Map<String, Object> getHsba(int hoSoId) {
	    return SQLUtil.getRecord(jdbcTemplate, "SELECT * FROM emr_danh_sach_ho_so_benh_an WHERE id=?", 
	                    new Object[] {hoSoId});
	}
	
	public Map<String, Object> getThongTinVaoVien(int hoSoId) {
	    String sql = SQLUtil.getSQL("THONGTIN_VAOVIEN");
	    return SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {hoSoId});	    
	}
	
	public List<Map<String, Object>> getThongTinVaoKhoa(int hoSoId) {
	    String sql = SQLUtil.getSQL("THONGTIN_VAOKHOA");
	    return SQLUtil.getRecords(jdbcTemplate, sql, new Object[] {hoSoId}, -1, -1);
	}
	
	public Map<String, Object> getThongTinRaVien(int hoSoId) {
        String sql = SQLUtil.getSQL("THONGTIN_RAVIEN");
        return SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {hoSoId});     
    }
	
	public Map<String, Object> getThongTinChanDoanVaoVien(int hoSoId) {
	    String sql = SQLUtil.getSQL("CHANDOAN_VAOVIEN");
	    return SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {hoSoId});
	}
	
	public Map<String, Object> getThongTinChanDoanVaoKhoa(int hoSoId) {
	    String sql = SQLUtil.getSQL("CHANDOAN_VAOKHOA");
	    return SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {hoSoId});
        
    }

	public Map<String, Object> getThongTinChanDoanRaVien(int hoSoId) {
	    String sql = SQLUtil.getSQL("CHANDOAN_RAVIEN");
	    return SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {hoSoId});
	}
	
	public Map<String, Object> getTinhTrangRaVien(int hoSoId) {
        String sql = SQLUtil.getSQL("TINHTRANG_RAVIEN");
        return SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {hoSoId});
    }
	
	public Map<String, Object> getThongTinLanhDao(int hoSoId) {
        String sql = SQLUtil.getSQL("THONGTIN_LANHDAO");
        return SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {hoSoId});
    }
}
