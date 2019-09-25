package vn.ehealth.emr.ck;

import lombok.Data;

@Data
public class EmrCkPhuongPhapHoiSinh {

    public int idhsba;
    public Integer idchuyenkhoa;
    
    public Boolean hutdich;
    
    public Boolean xoaboptim;
    
    public Boolean thooxy;
    
    public Boolean noikhiquan;
    
    public Boolean bopbongoxy;
    
    public Boolean khac;

    public String mota;
}
