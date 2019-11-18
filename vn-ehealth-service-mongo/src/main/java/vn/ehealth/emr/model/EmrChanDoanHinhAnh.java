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
@Document(collection = "emr_chan_doan_hinh_anh")
public class EmrChanDoanHinhAnh {

    @Id public ObjectId id;
    
    public ObjectId emrHoSoBenhAnId;
    
    public EmrDmContent emrDmLoaiChanDoanHinhAnh;    
    public EmrDmContent emrDmChanDoanHinhAnh;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngayyeucau;
    
    public String bacsiyeucau;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaythuchien;
    
    public String bacsichuyenkhoa;
    public String ketqua;
    public String ketluan;
    public String loidan;
        
    public List<EmrFileDinhKem> emrFileDinhKemCdhas = new ArrayList<>();
    
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
