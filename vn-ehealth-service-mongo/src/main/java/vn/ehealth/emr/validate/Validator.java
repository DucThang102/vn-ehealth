package vn.ehealth.emr.validate;

import java.util.regex.Pattern;

import org.bson.types.ObjectId;

public class Validator {
	
    public static String isValidPassword(String password) {

	    String val = "";
	
	    Pattern[] passRegex = new Pattern[4];
	
	    {
	        passRegex[0] = Pattern.compile(".*[A-Z].*");
	        passRegex[1] = Pattern.compile(".*[a-z].*");
	        passRegex[2] = Pattern.compile(".*\\d.*");
	        passRegex[3] = Pattern.compile("[A-Za-z0-9]*");
	    }
	
	
        if(password.length() < 8){
        	val = "Password phải ít nhất 8 kí tự";
        }
        if(!passRegex[0].matcher(password).matches()){
        	val = "Mật khẩu phải chứa ít nhất một chữ in hoa";
        }
        if(!passRegex[1].matcher(password).matches()){
        	val = "Mật khẩu phải chứa ít nhất một chữ thường";
        }
        return val;	
    }
    
    public static boolean isCheckTyeObjectId(String input) {
    	boolean val = true;
    	if (!ObjectId.isValid(input)) {
    		val = false;
    	}
    	return val;
    }
    
    public static boolean isValidRequier(String input) {
    	boolean val = true;
    	if (input == null || input.equals("")) {
    		val = false;
    	}
    	return val;
    }

}
