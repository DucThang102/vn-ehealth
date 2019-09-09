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
public class HsbaService {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	String adjustDsHosoSql(String rawSql, int trangthai, String maYte, List<Object> params) {
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
	
	public int countDsHoso(int trangthai, String maYte) {
		String sql = SQLUtil.getSQL("DS_HS");
		var params = new ArrayList<Object>();
		sql = adjustDsHosoSql(sql, trangthai, maYte, params);
		return SQLUtil.countRecords(jdbcTemplate, sql, params.toArray());
		
	}

	public List<Map<String, Object>> getDsHoso(int trangthai, String maYte, int start, int count) {
		String sql = SQLUtil.getSQL("DS_HS");
		var params = new ArrayList<Object>();
		sql = adjustDsHosoSql(sql, trangthai, maYte, params);
		return SQLUtil.getRecords(jdbcTemplate, sql, params.toArray(), start, count);
	}

}
