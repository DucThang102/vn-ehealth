package vn.ehealth.service.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SQLUtil {

	static final Logger logger = LoggerFactory.getLogger(SQLUtil.class);
	
	public static Map<String, String> SQLQueries = new HashMap<>();
	
	static {
		try {
			var file = new ClassPathResource("static/sql/query.xml").getInputStream();
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("item");			
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					var name = eElement.getElementsByTagName("name").item(0).getTextContent();
					var sql = eElement.getElementsByTagName("sql").item(0).getTextContent();
					SQLQueries.put(name, sql);
				}
			}
		}catch(IOException | SAXException | ParserConfigurationException e) {
			logger.error("Cannot load query set", e);
		}
	}
	
	public static String getSQL(String name) {
		return SQLQueries.getOrDefault(name, "");
	}
	
	public static int countRecords(JdbcTemplate jdbcTemplate, String queryStr, Object[] params) {
		if(queryStr != null) {
			int pos = queryStr.toUpperCase().indexOf("FROM");
			if(pos > 0) {
				queryStr = "SELECT COUNT(1) " + queryStr.substring(pos);
				return jdbcTemplate.queryForObject(queryStr, params, Integer.class);
			}			
		}
		return 0;		
	}
	
	private static void formatDateField(Map<String, Object> record) {
		for(String key : record.keySet()) {
			Object value = record.get(key);
			if(value instanceof Date) {
				value = DateUtil.parseDateToString((Date) value, DateUtil.FORMAT_DD_MM_YYYY_HH_MM);
				record.put(key, value);
			}
		}
	}
	
	public static List<Map<String, Object>> getRecords(JdbcTemplate jdbcTemplate, String queryStr, Object[] params, int start, int count) {
		if(!StringUtils.isEmpty(queryStr)) {
			queryStr = queryStr + String.format(" OFFSET %d LIMIT %d", start, count);
			var result = jdbcTemplate.queryForList(queryStr, params);
			
			for(var record : result) {
				formatDateField(record);
			}
			return result;
		}		
		return new ArrayList<>();
	}
	
	public static Map<String, Object> getRecord(JdbcTemplate jdbcTemplate, String queryStr, Object[] params) {
		if(!StringUtils.isEmpty(queryStr)) {
			var records = jdbcTemplate.queryForList(queryStr, params);
			if(records.size() > 0) {
				var record = records.get(0);
				formatDateField(record);
				return record;
			}			
		}
		return new HashMap<>();
	}
	
	public static void insertRecord(JdbcTemplate jdbcTemplate, String table, Map<String, Object> values) {
		var params = new ArrayList<Object>();
		var builder = new StringBuilder();
		builder.append("INSERT INTO ")
				.append(table)
				.append("(");
		
		int i = 0;
		for(String key : values.keySet()) {
			builder.append(key);
			if(i < values.size() - 1) {
				builder.append(",");				
			}
			params.add(values.get(key));
			i++;
		}
		
		builder.append(") VALUES(");
		for(i = 0; i < params.size(); i++) {
			builder.append("?");
			if(i < params.size() - 1) {
				builder.append(",");
			}
		}
		builder.append(")");
		
		String sql = builder.toString();
		jdbcTemplate.update(sql, params.toArray());		
	}
	
	public static void updateRecord(JdbcTemplate jdbcTemplate, String table, String idField, int id, Map<String, Object> values) {
		
		var builder = new StringBuilder();
		builder.append("UPDATE ")
				.append(table)
				.append(" SET ");
		
		int i = 0;
		var params = new ArrayList<Object>();
		
		for(String key : values.keySet()) {
			builder.append(key);
			builder.append("=?");
			if(i < values.size() - 1) {
				builder.append(",");				
			}			
			params.add(values.get(key));
			i++;
		}
		
		builder.append(" WHERE ").append(idField).append("=?");
		params.add(id);
		
		String sql = builder.toString();
		jdbcTemplate.update(sql, params.toArray());		
	}
	
	public static void deleteRecord(JdbcTemplate jdbcTemplate, String table, String idField, int id) {
		var builder = new StringBuilder();
		builder.append("DELETE FROM ")
				.append(table)
				.append(" WHERE ")
				.append(idField)
				.append("=?");
		
		String sql = builder.toString();
		jdbcTemplate.update(sql, new Object[] {id});
	}
}
