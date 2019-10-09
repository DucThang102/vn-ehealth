package vn.ehealth.emr.ck;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "emr_ck_tinh_trang_san_phu")
public class EmrCkTinhTrangSanPhu {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    
    @Column public Date ngayvooi;
    
    @Column public String mauoi;
    
    @Column public Boolean dethuong;
    
    @Column public Boolean canthiep;
    
    @Column public Date ngaycanthiep;
    
    @Column public String lydo;
    
    @Column public Date thoidiemvaode;
    @Column public Date thoidiemmode;
    @Column public String ngoithai;
    @Column public Boolean kiemsoattucung;
    @Column public String motakiemsoattucung;
    @Column public String nhommau;
    @Column public String paraDuthang;
    @Column public String paraDenon;
    @Column public String paraSay;
    @Column public String paraSong;
    
    // Add moi 16/04/2015
    @Column public String benhcuame;
    @Column public String dieutribenhcuame;
    @Column public String toantrang;
    @Column public Integer mach;
    @Column public String huyetap;
    @Column public String nhietdo;
    @Column public String motacachde;
    @Column public String socon;
    @Column public String sobenhanme;
    @Column public String meNoinam;
    @Column public String meSogiuong;
}
