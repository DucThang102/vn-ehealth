package vn.ehealth.emr.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_log")
public class EmrLog {
    
    @Id public ObjectId id;
    
    public String getId() { return id != null? id.toHexString() : null; }
    
    public ObjectId nguoiThucHienId;
    public ObjectId hanhDongId;
    
    public Date ngayThucHien;
    public String noiDung;
    public String ghiChu;
    
    public String objectClass;
    public ObjectId objectId;
}
