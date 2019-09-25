package vn.ehealth.emr.ck;

import lombok.Data;

@Data
public class EmrCkTinhTrangSoSinh {

    public int idhsba;
    public Integer idchuyenkhoa;
    
    public Boolean sskhocngay;
    
    public Boolean ssngat;
    
    public Boolean sskhac;
    
    public String nguoidode;
    
    public Integer apgar01;
    
    public Integer apgar05;
    
    public Integer apgar10;
    
    public Double cannang;
    
    public String tinhtrangdinhduong;
    
    public String tennguoichuyensosinh;
    
    public Integer tuoi;
}
