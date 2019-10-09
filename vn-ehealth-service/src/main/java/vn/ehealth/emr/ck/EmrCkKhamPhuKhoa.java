package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_kham_phu_khoa")
public class EmrCkKhamPhuKhoa {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public String dauhieusinhducthuphat;
    @Column public String moilon;
    @Column public String moibe;
    @Column public String amvat;
    @Column public String amho;
    @Column public String mangtrinh;
    @Column public String tangsinhmon;
    @Column public String amdao;
    @Column public String cotucung;
    @Column public String thantucung;
    @Column public String phanphu;
    @Column public String cactuicung;
    @Column public String thongtinkhac;
}
