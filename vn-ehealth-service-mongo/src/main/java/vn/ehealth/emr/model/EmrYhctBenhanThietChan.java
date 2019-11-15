package vn.ehealth.emr.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrYhctBenhanThietChan {

    //public String lstxucchan;
    //public String lstxucchanTen;
    //public String lstxucchanHienthi;
    public List<EmrDmContent> emrDmYhctXucChans;
    
    //public String lstconhuc;
    //public String lstconhucTen;
    //public String lstconhucHienthi;
    public List<EmrDmContent> emrDmYhctCoNhucs;
    
    //public String lstphucchan;
    //public String lstphucchanTen;
    //public String lstphucchanHienthi;
    public List<EmrDmContent> emrDmYhctPhucChans;
    
    //public String lstmachchantongquat;
    //public String lstmachchantongquatTen;
    //public String lstmachchantongquatHienthi;
    public List<EmrDmContent> emrDmYhctMachChanTongQuats;
    
    //public String lstmachchanThonTaytrai;
    //public String lstmachchanThonTaytraiTen;
    //public String lstmachchanThonTaytraiHienthi;
    public List<EmrDmContent> emrDmYhctMachChanThonTayTrais;
    
    //public String lstmachchanQuanTaytrai;
    //public String lstmachchanQuanTaytraiTen;
    //public String lstmachchanQuanTaytraiHienthi;
    public List<EmrDmContent> emrDmYhctMachChanQuanTayTrais;
    
    //public String lstmachchanXichTaytrai;
    //public String lstmachchanXichTaytraiTen;    
    //public String lstmachchanXichTaytraiHienthi;
    public List<EmrDmContent> emrDmYhctMachChanXichTayTrais;
    
    //public String lstmachchanThonTayphai;
    //public String lstmachchanThonTayphaiTen;
    //public String lstmachchanThonTayphaiHienthi;
    public List<EmrDmContent> emrDmYhctMachChanThonTayPhais;
    
    //public String lstmachchanQuanTayphai;
    //public String lstmachchanQuanTayphaiTen;
    //public String lstmachchanQuanTayphaiHienthi;
    public List<EmrDmContent> emrDmYhctMachChanQuanTayPhais;
    
    //public String lstmachchanXichTayphai;
    //public String lstmachchanXichTayphaiTen;
    //public String lstmachchanXichTayphaiHienthi;
    public List<EmrDmContent> emrDmYhctMachChanXichTayPhais;
            
    //public String lstxucchanmohoi;
    //public String lstxucchanmohoiTen;
    //public String lstxucchanmohoiHienthi;
    public List<EmrDmContent> emrDmYhctXucChanMoHois;
        
    public String motamachchanTaytrai;
    public String motamachchanTayphai;
    public String motaxucchan;    
    public String motathietchan;
    
}
