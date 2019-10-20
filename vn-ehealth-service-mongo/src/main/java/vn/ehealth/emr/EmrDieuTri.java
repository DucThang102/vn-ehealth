package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_dieu_tri")
public class EmrDieuTri {
    
    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }
    
    public ObjectId emrVaoKhoaId;
    
    public String sotodieutri;
    
    public List<EmrQuaTrinhDieuTri> emrQuaTrinhDieuTris = new ArrayList<>();
    
    public List<EmrFileDinhKem> emrFileDinhKemDieuTris = new ArrayList<>();
    
}
