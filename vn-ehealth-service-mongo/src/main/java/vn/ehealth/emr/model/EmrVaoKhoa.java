package vn.ehealth.emr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection="emr_vao_khoa")
public class EmrVaoKhoa {

    @Id public ObjectId id;
        
    public ObjectId emrHoSoBenhAnId;

    public EmrDmContent emrDmKhoaDieuTri;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm", timezone="Asia/Ho_Chi_Minh")
    public Date ngaygiovaokhoa;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm", timezone="Asia/Ho_Chi_Minh")
    public Date ngayketthucdieutri;

    public String tenkhoa;

    public String bacsidieutri;

    public String phong;

    public String giuong;

    public Integer songaydieutri;

    public String tentruongkhoa;
    
    @Transient public List<EmrHoiChan> emrHoiChans = new ArrayList<>();
    
    @Transient public List<EmrChamSoc> emrChamSocs = new ArrayList<>();
    
    @Transient public List<EmrDieuTri> emrDieuTris = new ArrayList<>();
    
    @Transient public List<EmrChucNangSong> emrChucNangSongs = new ArrayList<>();
    
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
