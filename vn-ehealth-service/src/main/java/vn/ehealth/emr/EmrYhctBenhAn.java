package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_yhct_benh_an")
public class EmrYhctBenhAn {

    @Id public int idhsba;
    
    @Column public Integer idchamsoc;    
    @Transient public EmrDm emrDmYhctCheDoChamSoc;
    
    @Column public String tomtattuchan;
    
    @Column public String luantri;
    
    @Column public String phapdieutri;
    
    @Column public String phuongthuoc;
    
    @Column public String phuonghuyet;
    
    @Column public String phuongphapkhac;
    
    @Column public String tienluong;
    
    @Column public String lstchedodinhduong;    
    
    @Column public String lstchedodinhduongTen;
    
    @Column public Boolean daxoa;
    
    @Column public Date ngaytao;
    
    @Column public Integer idnguoitao;
    
    @Column public Date ngaysua;
    
    @Column public Integer idnguoisua;
    
    @Column public String motachedodinhduong;
    
    @Column public String motachamsoc;
    
    // Add new 03/04/2015
    
    @Column public String dieutriXoabopbamhuyet;
    
    @Column public String dieutriKethopyhhd;
    
    @Transient public EmrYhctBenhanVaanChan emrYhctBenhanVaanChan;
    
    @Transient public EmrYhctBenhanThietChan emrYhctBenhanThietChan;
    
    @Transient public EmrYhctBenhanVongChan emrYhctBenhanVongChan;
    
    @Transient public EmrYhctBenhanVawnChan emrYhctBenhanVawnChan;
        
    @Column public String lstchedodinhduongHienthi;
}
