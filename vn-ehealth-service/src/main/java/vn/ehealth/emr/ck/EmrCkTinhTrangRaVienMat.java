package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tinh_trang_ra_vien_mat")
public class EmrCkTinhTrangRaVienMat {

    @Id public int idhsba;
    @Column public String thiluccokinhphai;
    @Column public String thiluccokinhtrai;
    @Column public String thiluckhongkinhphai;
    @Column public String thiluckhongkinhtrai;
    @Column public String nhanapphai;
    @Column public String nhanaptrai;
}
