package vn.ehealth.emr.ck;

import lombok.Data;

@Data
public class EmrCkTieuHoa {

    public int idhsba;
    public Integer idchuyenkhoa;    
    
    public Boolean ganto;
    public Double gankichthuoc;
    public String gandacdiem;
    public String motakhac;
    public String tinhtrangbenhly;
    public String roiloanchucnang;
}
