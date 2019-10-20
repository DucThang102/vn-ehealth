package vn.ehealth.validate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonValidator {
    
    Logger logger = LoggerFactory.getLogger(JsonValidator.class);
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    ObjectMapper mapper = new ObjectMapper();
    
    void validateJsonElement(String field, Optional<JsonNode> jsonElement, @Nonnull JsonNode schemaInfo, List<ErrorMessage> errors) {
        String type = schemaInfo.get("type").asText();
        var requiredNode = Optional.ofNullable(schemaInfo.get("required"));
        boolean required = false;
        
        if(requiredNode.map(x -> x.isBoolean()).orElse(false)) {
            required = requiredNode.map(x -> x.asBoolean()).orElse(false);
        }
        
        boolean isNull = jsonElement.map(x -> x.isNull()).orElse(true);
        if(isNull) {
            if(required) {
                errors.add(new ErrorMessage(field, ErrorMessage.Code.MISSING_FIELD, String.format("Field not found : ", field)));
            }
            return;
        }
        
        var element = jsonElement.orElseThrow();
        boolean errorType = !element.isValueNode();
        
        switch(type) {
            case DataType.TYPE_INTEGER:
                errorType = !element.isInt();
                break;
                
            case DataType.TYPE_DOUBLE :
                errorType = !element.isDouble() && !element.isInt();
                break;
                
            case DataType.TYPE_BOOLEAN :
                errorType = !element.isBoolean();
                break;
                
            case DataType.TYPE_STRING :
                errorType = !element.isTextual();
                if(!errorType) {
                    String st= element.asText();
                    if(required && StringUtils.isEmpty(st)) {
                        //errors.add(new ErrorMessage(field, ErrorMessage.Code.MISSING_FIELD, String.format("Field \"%s\" cannot be blank", field)));
                    }
                }
                break;
                
            case DataType.TYPE_DATE :
                errorType = !element.isTextual();
                if(!errorType) {
                    String st= element.asText();
                    try {
                        sdf.parse(st);
                    }catch(Exception e) {
                        errorType = true;
                    }
                }
                break;
            
            default:
                throw new RuntimeException("Not allowed type : " + type);
        }
        
        if(errorType) {
            errors.add(new ErrorMessage(field, ErrorMessage.Code.WRONG_DATA_TYPE, 
                            String.format("Wrong data type for field \"%s\" : expected data type %s", field, type)));
        }
    }
    
    void validateJsonArray(String field, Optional<JsonNode> jsonArr, @Nonnull JsonNode elementSchemaInfo, List<ErrorMessage> errors) {
        jsonArr.ifPresent(arr -> {
            for(int i = 0; i < arr.size(); i++) {
                var fieldElement = String.format("%s[%d]", field, i);
                var element = Optional.ofNullable(arr.get(i));
                
                if(elementSchemaInfo.get("type") != null) {
                    validateJsonElement(fieldElement, element, elementSchemaInfo, errors);
                }else {
                    validateJsonNode(fieldElement, element, elementSchemaInfo, errors);
                }
            }
        });        
    }
    
    void validateJsonNode(String parentField, Optional<JsonNode> jsonNode, @Nonnull JsonNode schemaJsonNode, List<ErrorMessage> errors) {
        var keys = new HashSet<String>();
        schemaJsonNode.fieldNames().forEachRemaining(keys::add);
        
        var objKeys = new ArrayList<String>();
        
        jsonNode.ifPresent(x -> {
            x.fieldNames().forEachRemaining(objKeys::add);
        });
        
        for(var objKey : objKeys) {
            String field = !StringUtils.isEmpty(parentField)? parentField + "." + objKey : objKey;;
            if(!keys.contains(objKey)) {
                errors.add(new ErrorMessage(field, ErrorMessage.Code.NOT_ALLOW_FIELD, 
                                String.format("Not allowed fields : \"%s\"", field)));
            }
        }
        
        for(var key : keys) {
            String field = !StringUtils.isEmpty(parentField)? parentField + "." + key : key;
        
            var schemaInfo = schemaJsonNode.get(key);
            if(schemaInfo == null) {
                throw new RuntimeException("Missing type for field : " + key);
            }
            
            var element = jsonNode.map(x -> x.get(key));
            var typeNode = schemaInfo.get("type");
            
            if(typeNode != null) {
                var type = typeNode.asText("");
                
                if(!DataType.TYPE_ARRAY.equals(type)) {
                    validateJsonElement(field, element, schemaInfo, errors);
                }else {
                    var isArray = element.map(x -> x.isArray()).orElse(true);
                    
                    if(!isArray) {
                        errors.add(new ErrorMessage(field, ErrorMessage.Code.WRONG_DATA_TYPE, 
                                String.format("Wrong data type for field \"%s\" : expected data type JsonArray", field)));
                    }
                    
                    var elementSchemaInfo = schemaInfo.get("element");                    
                    if(elementSchemaInfo != null) {
                        validateJsonArray(field, element, elementSchemaInfo, errors);
                    }
                }
            }else {
                validateJsonNode(field, element, schemaInfo, errors);
            }
        }
    }
    
    public List<ErrorMessage> validate(String jsonSt, String jsonSchema) {
        var errors = new ArrayList<ErrorMessage>();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonSt);
        }catch(Exception e) {
            logger.error("Cannot parse json object", e);
            errors.add(new ErrorMessage("", ErrorMessage.Code.INVALID_JSON, "Invalid json content"));
        }
        
        JsonNode schemaJsonNode = null;
        try {
            schemaJsonNode = mapper.readTree(jsonSchema);
        }catch(Exception e) {
            throw new RuntimeException("Cannot parse json schema", e);
        }
        
        if(jsonNode != null && schemaJsonNode != null) {
            validateJsonNode("", Optional.of(jsonNode), schemaJsonNode, errors);
        }
        
        return errors;
    }
}
