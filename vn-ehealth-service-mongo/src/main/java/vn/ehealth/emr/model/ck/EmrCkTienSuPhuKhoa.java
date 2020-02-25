package vn.ehealth.emr.model.ck;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrCkTienSuPhuKhoa {

    public Integer tuoicokinh;
    public Integer chukikinh;
    public Integer ngaythaykinh;
    public Integer tuoilaychong;
    public String benhphukhoa;
    public Boolean khoiubt;
    public Boolean uxotc;
    public Boolean didangsd;
    public Boolean ssd;
    public Boolean tsm;
    public Boolean dieutrictc;
    public Boolean catcutctc;
    public String tinhchatkinhnguyet;
    public String luongkinh;
    public Integer tuoihetkinh;
    
    // Add 10/04/2015
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaycokinh;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaykinhcuoi;
    public Boolean daubung;
    public Integer thoidiemdaubung; // (1. TrÆ°á»›c, 2. Trong, 3. Sau)
    public String phauthuatphukhoakhac;
}
