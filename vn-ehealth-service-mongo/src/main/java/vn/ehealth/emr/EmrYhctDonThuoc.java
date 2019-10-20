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
@Document(collection = "emr_yhct_don_thuoc")
public class EmrYhctDonThuoc {

    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }
    
    public ObjectId emrHoSoBenhAnId;
    
    public Date ngaykedon;
    public String bacsikedon;
    public String sodon;
    public Date ngaybatdau;
    public Date ngayketthuc;
    public Integer soluongthang;
    public String chidan;
    
    public List<EmrYhctDonThuocChiTiet> emrYhctDonThuocChiTiets = new ArrayList<>();    
    public List<EmrFileDinhKem> emrFileDinhKemYhctDonThuocs = new ArrayList<>();

}
