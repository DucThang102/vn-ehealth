package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "emr_ck_phuong_phap_hoi_sinh")
public class EmrCkPhuongPhapHoiSinh {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    
    @Column public Boolean hutdich;
    
    @Column public Boolean xoaboptim;
    
    @Column public Boolean thooxy;
    
    @Column public Boolean noikhiquan;
    
    @Column public Boolean bopbongoxy;
    
    @Column public Boolean khac;

    @Column public String mota;
}
