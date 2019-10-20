package vn.ehealth.emr;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrChucNangSongChiTiet {
    
    public Date ngaytheodoi;
    public Integer mach;
    public Double nhietdo;
    public Integer huyetapthap;
    public Integer huyetapcao;
    public Integer nhiptho;
    public Double cannang;
    public String ytatheodoi;
}
