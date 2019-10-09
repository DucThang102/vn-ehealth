package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_qua_trinh_benh_ly_tcm")
public class EmrCkQuaTrinhBenhLyTcm {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;    
    @Column public Boolean sot;
    @Column public Boolean phatban;
    @Column public Boolean loetmieng;
    @Column public Boolean giatminh;
    @Column public Integer giatminh24h;
    @Column public Boolean nonoi;
    @Column public Boolean cogiat;
    @Column public Boolean runchi;
    @Column public String dauhieukhac;
    @Column public String dieutrituyentruoc;
    @Column public String motakhac;
}
