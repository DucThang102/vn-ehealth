package vn.ehealth.emr.ck;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "emr_ck_tien_su_phu_khoa")
public class EmrCkTienSuPhuKhoa {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public Integer tuoicokinh;
    @Column public Integer chukikinh;
    @Column public Integer ngaythaykinh;
    @Column public Integer tuoilaychong;
    @Column public String benhphukhoa;
    @Column public Boolean khoiubt;
    @Column public Boolean uxotc;
    @Column public Boolean didangsd;
    @Column public Boolean ssd;
    @Column public Boolean tsm;
    @Column public Boolean dieutrictc;
    @Column public Boolean catcutctc;
    @Column public String tinhchatkinhnguyet;
    @Column public String luongkinh;
    @Column public Integer tuoihetkinh;
    
    // Add 10/04/2015
    @Column public Date ngaycokinh;
    @Column public Date ngaykinhcuoi;
    @Column public Boolean daubung;
    @Column public Integer thoidiemdaubung; // (1. TrÆ°á»›c, 2. Trong, 3. Sau)
    @Column public String phauthuatphukhoakhac;
}
