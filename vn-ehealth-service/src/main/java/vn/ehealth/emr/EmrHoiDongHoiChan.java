package vn.ehealth.emr;

import lombok.Data;

@Data
public class EmrHoiDongHoiChan {
    
    public int id;
    
    public Integer idhoichan;
    public EmrHoiChan emrHoiChan;
    
    public String bacsihoichan;
    public Integer idvaitro;
    
    public Boolean daxoa;

}
