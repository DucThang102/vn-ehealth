package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_hinh_anh_ton_thuong")
public class EmrHinhAnhTonThuong {
    
    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }
    
    public ObjectId emrHoSoBenhAnId;
        
    public String anhtonthuong;
    public String motatonthuong;
    public String dinhdanganh;
    
    public List<EmrFileDinhKem> emrFileDinhKemHatts = new ArrayList<>();
    
}
