package vn.ehealth.service;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import vn.ehealth.service.utils.SQLUtil;

@SpringBootApplication
public class DbTest implements CommandLineRunner {

	public static void main(String args[]) {
        SpringApplication.run(DbTest.class, args);
    }
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {
		
		var dsHoso = SQLUtil.getRecords(jdbcTemplate, 
				"SELECT matraodoi, mayte, ngaytao FROM emr_danh_sach_ho_so_benh_an WHERE idtrangthai=?",
				new Object[] {1}, 0, 10);
		
		System.out.println(dsHoso);
		
		String sql = SQLUtil.getSQL("HS_BY_MA");
		var hoso = SQLUtil.getRecord(jdbcTemplate, sql, new Object[] {"180340024"});
		System.out.println(hoso);
		
		SQLUtil.updateRecord(jdbcTemplate, "emr_benh_an", "idhsba", 2155, 
						Map.of("ngaykybenhan", new Date()));
		
		SQLUtil.deleteRecord(jdbcTemplate, "account", "user_id", 2);
	}

}
