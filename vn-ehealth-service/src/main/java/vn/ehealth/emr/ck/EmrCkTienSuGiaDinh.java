package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tien_su_gia_dinh")
public class EmrCkTienSuGiaDinh {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public Boolean sinhdoi;
    @Column public Boolean didang;
    @Column public Boolean benhditruyen;
    @Column public Boolean daiduong;
    @Column public Boolean caohuyetap;
    @Column public String benhkhac;
}
