package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrChanDoan {

    public int idhsba;
    
    public Integer idicdChandoandieutri;
    public EmrDm emrDmMaBenhChandoandieutri;
        
    public Integer idicdChandoantruocpt;
    public EmrDm emrDmMaBenhChandoantruocpt;
    
    public Integer idicdChandoansaupt;
    public EmrDm emrDmMaBenhChandoansaupt;
    
    public Integer idicdChandoankkb;
    public EmrDm emrDmMaBenhChandoankkb;
    
    public Integer idlydotbbc;
    public EmrDm emrDmLyDoTaiBienBienChung; //LyDoTaiBienBienChung
    
    public Integer idicdChandoannoiden;
    public EmrDm emrDmMaBenhChandoannoiden;
    
    public Integer idicdChandoanraviennguyennhan;
    public EmrDm emrDmMaBenhChandoanraviennguyennhan;
    
    public Integer idicdChandoanravienkemtheo;
    public EmrDm emrDmMaBenhChandoanravienkemtheo;
    
    public Integer idicdChandoanravienchinh;
    public EmrDm emrDmMaBenhChandoanravienchinh;
    
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
    public Boolean daxoa;
    public Date ngaytao;
    public Integer idnguoitao;
    public Date ngaysua;
    public Integer idnguoisua;
}
