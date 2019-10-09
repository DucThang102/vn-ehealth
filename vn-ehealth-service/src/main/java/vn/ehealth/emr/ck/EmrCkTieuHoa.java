package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tieu_hoa")
public class EmrCkTieuHoa {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;    
    
    @Column public Boolean ganto;
    @Column public Double gankichthuoc;
    @Column public String gandacdiem;
    @Column public String motakhac;
    @Column public String tinhtrangbenhly;
    @Column public String roiloanchucnang;
}
