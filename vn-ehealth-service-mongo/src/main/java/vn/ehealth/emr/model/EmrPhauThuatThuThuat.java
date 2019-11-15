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
    
    public String getId() { return id != null? id.toHexString() : null; }
    
    public ObjectId emrHoSoBenhAnId;    
        
    public EmrDmContent emrDmMaBenhChandoansau;
    public EmrDmContent emrDmMaBenhChandoantruoc;
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

    
}
