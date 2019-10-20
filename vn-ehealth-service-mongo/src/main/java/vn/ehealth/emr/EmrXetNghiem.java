package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_xet_nghiem")
public class EmrXetNghiem {
    
    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }
    
    public ObjectId emrHoSoBenhAnId;
    
    public EmrDmContent emrDmXetNghiem;
    public EmrDmContent emrDmLoaiXetNghiem;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public String noidungyeucau;
    
    public Date ngaythuchien;
    public String bacsixetnghiem;    
    //public String tailieudinhkem;
    public String nhanxet;
   
    
    public List<EmrXetNghiemDichVu> emrXetNghiemDichVus = new ArrayList<>();
    
    public List<EmrFileDinhKem> emrFileDinhKemXetNghiems = new ArrayList<>();
    
}
