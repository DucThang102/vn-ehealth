package vn.ehealth.emr;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrYhctBenhanVaanChan {
   
    //public String lsthannhiet;
    //public String lsthannhietTen;
    //public String lsthannhietHienthi;
    public List<EmrDmContent> emrDmYhctThanNhiets;
    
    //public String lstmohoi;
    //public String lstmohoiTen;
    //public String lstmohoiHienthi;
    public List<EmrDmContent> emrDmYhctMoHois;
    
    //public String lstdaumatco;    
    //public String lstdaumatcoTen;
    //public String lstdaumatcoHienthi;
    public List<EmrDmContent> emrDmYhctDauMatCos;
    
    //public String lstchantay;
    //public String lstchantayTen;
    //public String lstchantayHienthi;
    public List<EmrDmContent> emrDmYhctChanTays;
    
    //public String lstlung;
    //public String lstlungTen;
    //public String lstlungHienthi;
    public List<EmrDmContent> emrDmYhctLungs;
    
    //public String lstbungnguc;
    //public String lstbungngucTen;
    //public String lstbungngucHienthi;
    public List<EmrDmContent> emrDmYhctBungNgucs;
    
    //public String lstan;
    //public String lstanTen;
    //public String lstanHienthi;
    public List<EmrDmContent> emrDmYhctTans;
    
    //public String lstuong;
    //public String lstuongTen;
    //public String lstuongHienthi;
    public List<EmrDmContent> emrDmYhctTuongs;
    
    //public String lstdaitien;
    //public String lstdaitienTen;
    //public String lstdaitienHienthi;
    public List<EmrDmContent> emrDmYhctDaiTiens;
    
    //public String lsttieutien;
    //public String lsttieutienTen;
    //public String lsttieutienHienthi;
    public List<EmrDmContent> emrDmYhctTieuTiens;
    
    //public String lstngu;
    //public String lstnguTen;
    //public String lstnguHienthi;
    public List<EmrDmContent> emrDmYhctNgus;
    
    //public String lstkinhnguyet;
    //public String lstkinhnguyetTen;
    //public String lstkinhnguyetHienthi;
    public List<EmrDmContent> emrDmYhctKinhNguyets;
    
    //public String lstthongkinh;
    //public String lstthongkinhTen;
    //public String lstthongkinhHienthi;
    public List<EmrDmContent> emrDmYhctThongKinhs;
    
    //public String lstdoiha;
    //public String lstdoihaTen;
    //public String lstdoihaHienthi;
    public List<EmrDmContent> emrDmYhctDoiHas;
    
    //public String lstsinhduc;
    //public String lstsinhducTen;
    //public String lstsinhducHienthi;
    public List<EmrDmContent> emrDmYhctSinhDucs;
    
    //public String lstsinhducnu;
    //public String lstsinhducnuTen;
    //public String lstsinhducnuHienthi;
    public List<EmrDmContent> emrDmYhctSinhDucNus;
        
    //public String lsthannhietbieuhien;
    //public String lsthannhietbieuhienTen;
    //public String lsthannhietbieuhienHienthi;
    public List<EmrDmContent> emrDmYhctThanNhietBieuHiens;
    
    public String motavaanchan;
    public Boolean dkxuatvien;
}
