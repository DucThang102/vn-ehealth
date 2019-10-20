package vn.ehealth.emr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrChanDoan {

    public EmrDmContent emrDmMaBenhChandoandieutri;
        
    public EmrDmContent emrDmMaBenhChandoantruocpt;
    
    public EmrDmContent emrDmMaBenhChandoansaupt;
    
    public EmrDmContent emrDmMaBenhChandoankkb;
    
    public EmrDmContent emrDmLyDoTaiBienBienChung;
    
    public EmrDmContent emrDmMaBenhChandoannoiden;
    
    public EmrDmContent emrDmMaBenhChandoanraviennguyennhan;
    
    public EmrDmContent emrDmMaBenhChandoanravienkemtheo;
    
    public EmrDmContent emrDmMaBenhChandoanravienchinh;
    
    public String motachandoannoiden;

    public String motachandoankkb;

    public String motachandoandieutri;

    public String motachandoanravienchinh;

    public String motachandoanraviennguyennhan;
    
    public String motachandoanravienkemtheo;
    
    public String motachandoantruocpt;

    public String motachandoansaupt;

    public Boolean bitaibien;

    public Boolean bibienchung;

    public Integer tongsongaysaupt;

    public Integer tongsolanpt;

}
