package vn.ehealth.emr;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrYhctChanDoan {

    public EmrDmContent emrDmYhctBenhdanhVaovien;
    
    public EmrDmContent emrDmYhctBenhdanhRavien;
    
    public EmrDmContent emrDmYhctBenhdanhVk;
    
    //public String lstbatcuong;
    //public String lstbatcuongTen;
    //public String lstbatcuongHienthi;
    public List<EmrDmContent> emrDmYhctBatCuongs;
    
    //public String lstbatcuongVv;    
    //public String lstbatcuongVvTen;
    //public String lstbatcuongVvHienthi;
    public List<EmrDmContent> emrDmYhctBatCuongVvs;
    
    //public String lsttangphu;    
    //public String lsttangphuTen;
    //public String lsttangphuHienthi;
    public List<EmrDmContent> emrDmYhctTangPhus;
    
    //public String lsttangphuVv;    
    //public String lsttangphuVvTen;
    //public String lsttangphuVvHienthi;
    public List<EmrDmContent> emrDmYhctTangPhuVvs;
    
    //public String lstkinhmach;    
    //public String lstkinhmachTen;
    //public String lstkinhmachHienthi;
    public List<EmrDmContent> emrDmYhctKinhMachs;
    
    //public String lstkinhmachVv;    
    //public String lstkinhmachVvTen;
    //public String lstkinhmachVvHienthi;
    public List<EmrDmContent> emrDmYhctKinhMachVvs;
    
    //public String lstdinhvibenh;    
    //public String lstdinhvibenhTen;
    //public String lstdinhvibenhHienthi;
    public List<EmrDmContent> emrDmYhctDinhViBenhs;
    
    //public String lstdinhvibenhVv;    
    //public String lstdinhvibenhVvTen;
    //public String lstdinhvibenhVvHienthi;
    public List<EmrDmContent> emrDmYhctDinhViBenhVvs;
    
    //public String lstnguyennhanbenh;    
    //public String lstnguyennhanbenhTen;
    //public String lstnguyennhanbenhHienthi;
    public List<EmrDmContent> emrDmYhctNguyenNhanBenhs;
        
    //public String lstnguyennhanbenhVv;    
    //public String lstnguyennhanbenhVvTen;
    //public String lstnguyennhanbenhVvHienthi;
    public List<EmrDmContent> emrDmYhctNguyenNhanBenhVvs;
    
    public String motabatcuong;
    public String motabatcuongVv;

    public String motatangphu;
    public String motatangphuVv;

    public String motakinhmach;
    public String motakinhmachVv;

    public String motanguyennhanbenh;
    public String motanguyennhanbenhVv;

    public String motabenhdanhyhctvaovien;
    public String motabenhdanhyhctvaokhoa;
    public String motabenhdanhyhctravien;        
}
