package vn.ehealth.emr.model.ck;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import vn.ehealth.emr.model.EmrDm;

@JsonInclude(Include.NON_NULL)
public class EmrCkKhamSanKhoa {
    public String khamthaitai;
    public Integer tuoithai;
    public Boolean phonguonvan;
    public Integer muiuonvanthu;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date chuyendaluc;
    public String trieuchungbandau;
    public String dauhieusaudo;
    public Boolean seomocu;
    public Boolean dauvetmo;
    public String hinhdangtc;
    public String tuthetc;
    public Integer chieucaotc;
    public Integer vongbung;
    public String concotc;
    public Integer timthai;
    public String ngoithai;
    public String vu;
    public Integer dluonggai;
    public Integer dluongmao;
    public Integer dluongmau;
    public Integer dbodeloque;
    public Integer dluongungoi;
    public Integer khungchau;
    public String amho;
    public String amdao;
    public String tangsinhmon;
    public Boolean ctcdangxoa;
    public Boolean ctcdongkin;
    public Integer ctcmo;
    public Integer bishop;
    public Boolean utiendao;
    public String motau;
    public Integer dauoi;
    public Boolean bamoi;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date bamoiluc;
    public Integer mauoi;
    public String ngoi;
    public String the;
    public String kieuthe;
    //public Integer dolot;
    public EmrDm emrDmDolot;
    public Integer dnhohauve;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date kykinhcuoitungay;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date kykinhcuoidenngay;
    public String phanphu;
    public String nuocoi;
    
    //ngay 28/7/2015
    public String tencondukien;
    public String tenbo;
    public String tentrai;
    public String tengai;
}
