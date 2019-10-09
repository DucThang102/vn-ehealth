package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tuan_hoan")
public class EmrCkTuanHoan {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    
    @Column public Boolean timro;
    
    @Column public Boolean timmo;
    
    @Column public Boolean timgallop;
    
    @Column public Boolean timamthoi;
    
    @Column public String timchitiet;
    
    @Column public Boolean tinhmachconoi;
    
    @Column public Integer thoigiandaymaomach;
    
    @Column public Boolean vamohoi;
    
    @Column public Boolean danoibong;
    
    @Column public String roiloantim;
    
    // Add moi 13/04/2015
    @Column public String nhiptim;
}
