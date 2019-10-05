package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrQuaTrinhChamSoc {

    public int id;
    
    public Integer idchamsoc;
    
    public Date ngaychamsoc;
    public String ytachamsoc;
    public String theodoidienbien;
    public String thuchienylenh;
    public Boolean daxoa;
}
