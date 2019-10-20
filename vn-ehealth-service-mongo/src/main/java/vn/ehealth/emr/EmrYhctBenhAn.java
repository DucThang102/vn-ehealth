package vn.ehealth.emr;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrYhctBenhAn {

    public EmrDmContent emrDmYhctCheDoChamSoc;
    
    public String tomtattuchan;
    
    public String luantri;
    
    public String phapdieutri;
    
    public String phuongthuoc;
    
    public String phuonghuyet;
    
    public String phuongphapkhac;
    
    public String tienluong;
    
    // public String lstchedodinhduong;
    // public String lstchedodinhduongTen;
    // public String lstchedodinhduongHienthi;
    
    public List<EmrDmContent> emrDmYhctCheDoDinhDuongs;
    
    public String motachedodinhduong;
    
    public String motachamsoc;
    
    public String dieutriXoabopbamhuyet;
    
    public String dieutriKethopyhhd;
    
    public EmrYhctBenhanVaanChan emrYhctBenhanVaanChan;
    
    public EmrYhctBenhanThietChan emrYhctBenhanThietChan;
    
    public EmrYhctBenhanVongChan emrYhctBenhanVongChan;
    
    public EmrYhctBenhanVawnChan emrYhctBenhanVawnChan;
        
    
}
