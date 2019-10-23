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
@Document(collection="emr_hoi_chan")
public class EmrHoiChan {

    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }
    
    public ObjectId emrVaoKhoaId;
    
    public String getEmrVaoKhoaId() { return emrVaoKhoaId.toHexString(); }

    public Date ngaythuchien;

    public String tomtatdienbien;

    public String ketluanhoichan;

    public String huongdieutri;
    
    public List<EmrThanhVienHoiChan> emrThanhVienHoiChans = new ArrayList<>();
    
    public List<EmrFileDinhKem> emrFileDinhKemHoiChans = new ArrayList<>();
    
}
