package vn.ehealth.emr.ck;

import lombok.Data;
import vn.ehealth.emr.EmrTongKetRaVien;

@Data
public class EmrCkPhuongPhapDieuTriUngBuou {

    public int idhsba;
    public EmrTongKetRaVien emrTongKetRaVien;  
    
    public Integer kieudieutri;
    
    public Double tiaxatienphautaiu;

    public Double tiaxatienphautaihach;

    public Double tiaxadonthuantaiu;

    public Double tiaxadonthuantaihach;

    public String phauthuatu;

    public Double tiaxahauphautaiu;

    public Double tiaxahauphautaihach;

    public String hoachat;

    public Integer sodot;

    
    public Integer dapung;

    public String dieutrikhac; 
    
    public Integer idchuyenkhoa;
}
