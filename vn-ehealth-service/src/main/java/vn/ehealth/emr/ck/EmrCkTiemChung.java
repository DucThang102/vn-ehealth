package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tiem_chung")
public class EmrCkTiemChung {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    
    @Column public Boolean tclao;
    
    @Column public Boolean tcbailiet;
    
    @Column public Boolean tcsoi;
    
    @Column public Boolean tchoga;
    
    @Column public Boolean tcuonvan;
    
    @Column public Boolean tcbachhau;
    
    @Column public Boolean tckhac;
    
    @Column public String tcmotakhac;
}
