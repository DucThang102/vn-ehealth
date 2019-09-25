package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrChucNangSongChiTiet {
    
    public int id;
    
    public Integer idchucnangsong;
    
    public Date ngaytheodoi;
    public Double mach;
    public Double nhietdo;
    public Integer huyetapthap;
    public Integer huyetapcao;
    public Integer nhiptho;
    public Integer cannang;
    public String ytatheodoi;
    public Boolean daxoa;

}
