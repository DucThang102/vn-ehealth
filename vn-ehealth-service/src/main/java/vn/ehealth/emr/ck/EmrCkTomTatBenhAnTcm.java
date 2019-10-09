package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tom_tat_benh_an_tcm")
public class EmrCkTomTatBenhAnTcm {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;    
    @Column public Integer songaybenh;
    @Column public Boolean suyhohap;
    @Column public Boolean soc;
    @Column public Boolean phuphoicap;
    @Column public Boolean roiloanhohap;
    @Column public Boolean machnhanh;
    @Column public Boolean tanghuyetap;
    @Column public Boolean gongchi;
    @Column public Boolean vamohoi;
    @Column public Boolean thatdieu;
    @Column public Boolean runggiatnhancau;
    @Column public Boolean yeuchi;
    @Column public Boolean lietthankinhso;
    @Column public Boolean giatminhluckham;
    @Column public Boolean benhsugiatminh;
    @Column public String bieuhienkhac;
}
