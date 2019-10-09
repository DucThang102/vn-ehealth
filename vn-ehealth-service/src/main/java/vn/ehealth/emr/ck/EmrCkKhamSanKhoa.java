package vn.ehealth.emr.ck;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "emr_ck_kham_san_khoa")
public class EmrCkKhamSanKhoa {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public String khamthaitai;
    @Column public Integer tuoithai;
    @Column public Boolean phonguonvan;
    @Column public Integer muiuonvanthu;
    @Column public Date chuyendaluc;
    @Column public String trieuchungbandau;
    @Column public String dauhieusaudo;
    @Column public Boolean seomocu;
    @Column public Boolean dauvetmo;
    @Column public String hinhdangtc;
    @Column public String tuthetc;
    @Column public Integer chieucaotc;
    @Column public Integer vongbung;
    @Column public String concotc;
    @Column public Integer timthai;
    @Column public String ngoithai;
    @Column public String vu;
    @Column public Integer dluonggai;
    @Column public Integer dluongmao;
    @Column public Integer dluongmau;
    @Column public Integer dbodeloque;
    @Column public Integer dluongungoi;
    @Column public Integer khungchau;
    @Column public String amho;
    @Column public String amdao;
    @Column public String tangsinhmon;
    @Column public Boolean ctcdangxoa;
    @Column public Boolean ctcdongkin;
    @Column public Integer ctcmo;
    @Column public Integer bishop;
    @Column public Boolean utiendao;
    @Column public String motau;
    @Column public Integer dauoi;
    @Column public Boolean bamoi;
    @Column public Date bamoiluc;
    @Column public Integer mauoi;
    @Column public String ngoi;
    @Column public String the;
    @Column public String kieuthe;
    @Column public Integer dolot;
    @Column public Integer dnhohauve;
    @Column public Date kykinhcuoitungay;
    @Column public Date kykinhcuoidenngay;
    @Column public String phanphu;
    @Column public String nuocoi;
    
    //ngay 28/7/2015
    @Column public String tencondukien;
    @Column public String tenbo;
    @Column public String tentrai;
    @Column public String tengai;
}
