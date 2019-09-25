package vn.ehealth.emr.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FieldUtil {

    static String camelToSnake(String s) {
        return s.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }
    
    public static void setFields(Object obj, Map<String, Object> record) {
        var fields = obj.getClass().getFields();
        var mapFields = new HashMap<String, Field>();
        
        for(var field : fields) {
            mapFields.put(field.getName(), field);
            mapFields.put(field.getName().toLowerCase(), field);
            mapFields.put(camelToSnake(field.getName()), field);
        }
        
        for(String param : record.keySet()) {
            Object value = record.get(param);
            try {
                Field field = mapFields.get(param);
                
                if(field == null) {
                    field = mapFields.get(param.replace("_", ""));
                }
                
                if(field != null) {
                    if(value instanceof BigDecimal) {
                        field.set(obj, Double.valueOf(String.valueOf(value)));
                    }else {
                        field.set(obj, value);
                    }
                }else {
                    System.out.println(String.format("Field %s not found for class %s", param, obj.getClass().getName()));
                }
            } catch (SecurityException | IllegalAccessException  e) {
                e.printStackTrace();
            }
        }
    }
}
