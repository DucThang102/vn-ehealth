package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tien_su_ban_than_san_khoa")
public class EmrCkTienSuBanThanSanKhoa {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public Boolean caohuyetap;
    @Column public Boolean benhtim;
    @Column public Boolean laophoi;
    @Column public Boolean henphequan;
    @Column public Boolean benhthan;
    @Column public Boolean basedow;
    @Column public Boolean viemtactinhmach;
    @Column public Boolean dongkinh;
    @Column public Boolean moruotthua;
    @Column public Boolean diungthuoc;
    @Column public String thongtincuthe;
    @Column public String phauthuatobung;
}
