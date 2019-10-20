package vn.ehealth.emr;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_co_so_kham_benh")
public class EmrCoSoKhamBenh {

    @Id    
    public transient ObjectId id;
    
    public String getId() { return id.toHexString(); }
    
    public EmrDmContent emrDmPhuongXa;
    public EmrDmContent emrDmQuanHuyen;
    public EmrDmContent emrDmTinhThanh;
    public EmrDmContent emrDmCoSoKhamBenh;
    
    public String ma;
    
    public String ten;
    
    public Short tuyen;
    
    public String diachi;
    
    public String donvichuquan;
    
    public String giamdoc;
    
    public String dienthoai;
    
    public String truongphongth;
}
