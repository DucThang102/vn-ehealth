package vn.ehealth.emr.model;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_dm")
public class EmrDm {

    @Id public ObjectId id;    
    
    public ObjectId emrNhomDmId;
    public ObjectId emrDmChaId;
    public String ma = "";
    public String ten = "";
    public int capdo;
    
    public Map<String, Object> extension;
    
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
    
    public String getEmrNhomDmId() {
        return emrNhomDmId != null? emrNhomDmId.toHexString() : null;
    }
    
    public String getEmrDmChaId() {
        return emrDmChaId != null? emrDmChaId.toHexString(): null;
    }
    /*
    public String kieu = "";
    
    public String value1;
    public String value2;
    public String value3;
    public String value4;
    
    public String tenta = "";
    public Integer idcha = 0;
    public Integer capdo = 0;
    public String codecda = "";
    public String donvi = "";
    public String maicd = "";
    public String chisobt_nu = "";
    public String chisobt_nam = "";*/
    
    /*    
    public Integer landau;
    public Integer lancuoi;
    public Boolean daxoa;
    
    public String truongkhoa;
    public Integer soguongbenh;
    
    public String maphannhom;
    
    public Boolean phantuyenA;
    public Boolean phantuyenB;
    public Boolean phantuyenC;
    public Boolean phantuyenD;
    
    public String dangbaoche;
    public String hamluong;
    public String duongdung;
    public String atccode;
    public String maduongdung;
    public String mahoatchat;
    public String tenhoatchat;
    
    public String mo;
    public String loaiptt;
    public String loai;
    public String giatri;
    public String reference;
    public String dichvu;
    public Boolean loaipttt;
    */
    
}
