package vn.ehealth.emr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_don_thuoc")
public class EmrDonThuoc {
    
    @Id public ObjectId id;
    
    public ObjectId emrHoSoBenhAnId;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaykedon;
    public String bacsikedon;
    public String sodon;
    
    public List<EmrDonThuocChiTiet> emrDonThuocChiTiets = new ArrayList<>(); 
    
    public List<EmrFileDinhKem> emrFileDinhKemDonThuocs = new ArrayList<>();
    
    public String getId() { 
        return id != null? id.toHexString() : null; 
    }
    
    public void setId(String id) {
        if(id != null) {
            this.id = new ObjectId(id);
        }else {
            this.id = null;
        }
    }
    
    public String getEmrHoSoBenhAnId() {
        return emrHoSoBenhAnId != null? emrHoSoBenhAnId.toHexString(): null;
    }
    
    public void setEmrHoSoBenhAnId(String emrHoSoBenhAnId) {
        if(emrHoSoBenhAnId != null) {
            this.emrHoSoBenhAnId = new ObjectId(emrHoSoBenhAnId);
        }else {
            this.emrHoSoBenhAnId = null;
        }            
    }
}
