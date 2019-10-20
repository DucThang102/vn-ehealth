package vn.ehealth.emr;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_nhom_dm")
public class EmrNhomDm {
    
    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }
    
    public String ten;
    public String ma;
    public String mota;
}
