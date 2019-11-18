package vn.ehealth.emr.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrChanDoan {

    public EmrDmContent emrDmMaBenhChandoandieutri;
    
    public EmrDmContent emrDmMaBenhChandoantruocpt;
    
    public EmrDmContent emrDmMaBenhChandoansaupt;
    
    public EmrDmContent emrDmMaBenhChandoankkb;
    
    public List<EmrDmContent> emrDmMaBenhChandoandieutris = new ArrayList<>();
        
    public List<EmrDmContent> emrDmMaBenhChandoantruocpts = new ArrayList<>();
    
    public List<EmrDmContent> emrDmMaBenhChandoansaupts = new ArrayList<>();
    
    public List<EmrDmContent> emrDmMaBenhChandoankkbs = new ArrayList<>();
    
    public EmrDmContent emrDmLyDoTaiBienBienChung;
    
    public EmrDmContent emrDmMaBenhChandoannoiden;
    
    public EmrDmContent emrDmMaBenhChandoanraviennguyennhan;
    
    public EmrDmContent emrDmMaBenhChandoanravienkemtheo;
    
    public EmrDmContent emrDmMaBenhChandoanravienchinh;    
    
    public List<EmrDmContent> emrDmMaBenhChandoannoidens = new ArrayList<>();
    
    public List<EmrDmContent> emrDmMaBenhChandoanraviennguyennhans = new ArrayList<>();
    
    public List<EmrDmContent> emrDmMaBenhChandoanravienkemtheos = new ArrayList<>();
    
    public List<EmrDmContent> emrDmMaBenhChandoanravienchinhs = new ArrayList<>();
    
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
