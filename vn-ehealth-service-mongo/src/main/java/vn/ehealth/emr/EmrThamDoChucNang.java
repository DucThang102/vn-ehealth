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
@Document(collection = "emr_tham_do_chuc_nang")
public class EmrThamDoChucNang {
    
    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }
    
    public ObjectId emrHoSoBenhAnId;
    
    public EmrDmContent emrDmThamDoChucNang;
    public EmrDmContent emrDmLoaiThamDoChucNang;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public String noidungyeucau;
    public Date ngaythuchien;
    public String ketqua;
    public String ketluan;
    public String loidan;
    public String bacsichuyenkhoa;
    
    public List<EmrFileDinhKem> emrFileDinhKemTdcns = new ArrayList<>();

}
