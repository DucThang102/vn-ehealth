package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_phuong_phap_dieu_tri_ung_buou")
public class EmrCkPhuongPhapDieuTriUngBuou {

    @Id public int idhsba;
    
    @Column public Integer kieudieutri;
    
    @Column public Double tiaxatienphautaiu;

    @Column public Double tiaxatienphautaihach;

    @Column public Double tiaxadonthuantaiu;

    @Column public Double tiaxadonthuantaihach;

    @Column public String phauthuatu;

    @Column public Double tiaxahauphautaiu;

    @Column public Double tiaxahauphautaihach;

    @Column public String hoachat;

    @Column public Integer sodot;

    
    @Column public Integer dapung;

    @Column public String dieutrikhac; 
    
    @Column public Integer idchuyenkhoa;
}
