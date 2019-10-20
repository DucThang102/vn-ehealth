package vn.ehealth.emr.ck;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrCkChucNangSinhHoat {

    public String anuong;
    
    public String chaitoc;
    
    public String danhrang;
    
    public String tam;
    
    public String macquanao;
    
    public String divesinh;
    
    public String namguaSap;
    
    public String namnguaNgoi;
    
    public String dungNgoi;
    
    public String dunglen;
    
    public String dichuyen;
    
    public String dungcu;
    public String sinhhoatkhac;
}
