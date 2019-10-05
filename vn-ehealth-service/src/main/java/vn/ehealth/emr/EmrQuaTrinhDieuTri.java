package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrQuaTrinhDieuTri {

    public int id;
    
    public Integer iddieutri;
    
    public Date ngaydieutri;
    public String dienbien;
    public String chamsoc;
    public String ylenh;
    public String bacsiraylenh;
    public Boolean daxoa;
}
