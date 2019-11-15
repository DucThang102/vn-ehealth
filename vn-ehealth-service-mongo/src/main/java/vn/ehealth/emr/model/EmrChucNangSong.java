package vn.ehealth.emr.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_chuc_nang_song")
public class EmrChucNangSong {
    
    @Id public ObjectId id;
        
    public ObjectId emrVaoKhoaId;
    
    @Transient public EmrVaoKhoa emrVaoKhoa;
    
    public String sophieu;
    
    public List<EmrChucNangSongChiTiet> emrChucNangSongChiTiets = new ArrayList<>();
    
    public List<EmrFileDinhKem> emrFileDinhKemChucNangSongs = new ArrayList<>();
    
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
    
    public String getEmrVaoKhoaId() {
        return emrVaoKhoaId != null? emrVaoKhoaId.toHexString(): null;        
    }
    
    public void setEmrVaoKhoaId(String emrVaoKhoaId) {
        if(emrVaoKhoaId != null) {
            this.emrVaoKhoaId = new ObjectId(emrVaoKhoaId);
        }else {
            this.emrVaoKhoaId = null;
        }
    }
}
