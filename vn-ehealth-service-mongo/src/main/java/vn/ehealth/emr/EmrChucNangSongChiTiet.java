
package vn.ehealth.emr;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrChucNangSongChiTiet {
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaytheodoi;
    public Double mach;
    public Double nhietdo;
    public Integer huyetapthap;
    public Integer huyetapcao;
    public Integer nhiptho;
    public Double cannang;
    public String ytatheodoi;
}
