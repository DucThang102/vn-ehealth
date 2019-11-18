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
@Document(collection = "emr_phau_thuat_thu_thuat")
public class EmrPhauThuatThuThuat {

    @Id public ObjectId id;
        
    public ObjectId emrHoSoBenhAnId;    
        
    public EmrDmContent emrDmMaBenhChandoansau;
    public EmrDmContent emrDmMaBenhChandoantruoc;
    
    public List<EmrDmContent> emrDmMaBenhChandoansaus = new ArrayList<>();
    public List<EmrDmContent> emrDmMaBenhChandoantruocs = new ArrayList<>();
    
    public EmrDmContent emrDmPhauThuThuat;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaygiopttt;
    public String bacsithuchien;
    public String bacsygayme;
    public String chidinhptt;
    public String phuongphapvocam;
    public String luocdoptt;
    public String trinhtuptt;
    
    public String motachandoantruocpt;
    public String motachandoansaupt;
    
    public Boolean loaipttt;
    //public String loaimoChklist;
        
    public List<EmrThanhVienPttt> emrThanhVienPttts = new ArrayList<>();
    
    public List<EmrFileDinhKem> emrFileDinhKemPttts = new ArrayList<>();
    
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
