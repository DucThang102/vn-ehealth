package vn.ehealth.emr.ck;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "emr_ck_sk_tinh_trang_san_phu")
public class EmrCkSkTinhTrangSanPhu {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public Integer tinhthan;
    @Column public Boolean phu;
    @Column public String proteinnieu;
    @Column public Boolean seomocu;
    @Column public Boolean dauvetmo;
    @Column public Double chieucaotucung;
    @Column public Integer timthai;
    @Column public Integer tinhtrangtimthai;
    @Column public Integer cotucung;
    @Column public Integer domo;
    @Column public Integer chisobishop;
    @Column public Integer dauoi;
    @Column public Date thoidiemvooi;
    @Column public Integer maunuocoi;
    
    // Add moi 16/04/2015
    @Column public String nhietdo;
    @Column public String mach;
    @Column public String huyetap;
    @Column public String nhiptho;
    @Column public String vongbung;
    @Column public String ngoithai;
    @Column public String concotucung;
}
