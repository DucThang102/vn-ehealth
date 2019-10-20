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
@Document(collection = "emr_don_thuoc")
public class EmrDonThuoc {
    
    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }
    
    public ObjectId emrHoSoBenhAnId;
    
    public Date ngaykedon;
    public String bacsikedon;
    public String sodon;
    
    public List<EmrDonThuocChiTiet> emrDonThuocChiTiets = new ArrayList<>(); 
    
    public List<EmrFileDinhKem> emrFileDinhKemDonThuocs = new ArrayList<>();
}
