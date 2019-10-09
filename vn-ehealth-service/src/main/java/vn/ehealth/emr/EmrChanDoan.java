package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="emr_chan_doan")
public class EmrChanDoan {

    @Id
    public int idhsba;
    
    @Column(name="idicd_chandoandieutri")
    public Integer idicdChandoandieutri;
    
    @Transient
    public EmrDm emrDmMaBenhChandoandieutri;
        
    @Column(name = "idicd_chandoantruocpt")
    public Integer idicdChandoantruocpt;
    
    @Transient
    public EmrDm emrDmMaBenhChandoantruocpt;
    
    @Column(name = "idicd_chandoansaupt")
    public Integer idicdChandoansaupt;
    
    @Transient
    public EmrDm emrDmMaBenhChandoansaupt;
    
    @Column(name = "idicd_chandoankkb")
    public Integer idicdChandoankkb;
    
    @Transient
    public EmrDm emrDmMaBenhChandoankkb;
    
    @Column(name = "idlydotbbc")
    public Integer idlydotbbc;
    
    @Transient
    public EmrDm emrDmLyDoTaiBienBienChung; //LyDoTaiBienBienChung
    
    @Column(name = "idicd_chandoannoiden")
    public Integer idicdChandoannoiden;
    
    @Transient
    public EmrDm emrDmMaBenhChandoannoiden;
    
    @Column(name = "idicd_chandoanraviennguyennhan")
    public Integer idicdChandoanraviennguyennhan;
    
    @Transient
    public EmrDm emrDmMaBenhChandoanraviennguyennhan;
    
    @Column(name = "idicd_chandoanravienkemtheo")
    public Integer idicdChandoanravienkemtheo;
    
    @Transient
    public EmrDm emrDmMaBenhChandoanravienkemtheo;
    
    @Column(name = "idicd_chandoanravienchinh")
    public Integer idicdChandoanravienchinh;
    
    @Transient
    public EmrDm emrDmMaBenhChandoanravienchinh;
    
    @Column
    public String motachandoannoiden;
    
    @Column
    public String motachandoankkb;
    
    @Column
    public String motachandoandieutri;
    
    @Column
    public String motachandoanravienchinh;
    
    @Column
    public String motachandoanraviennguyennhan;
        
    @Column
    public String motachandoanravienkemtheo;
        
    @Column
    public String motachandoantruocpt;
    
    @Column
    public String motachandoansaupt;
    
    @Column
    public Boolean bitaibien;
    
    @Column
    public Boolean bibienchung;
    
    @Column
    public Integer tongsongaysaupt;
    
    @Column
    public Integer tongsolanpt;
    
    @Column
    public Boolean daxoa;
    
    @Column
    public Date ngaytao;
    
    @Column
    public Integer idnguoitao;
    
    @Column
    public Date ngaysua;    
    
    @Column
    public Integer idnguoisua;
}
