package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrYhctChanDoan {

    public int idhsba;

    public Integer idbenhdanhyhctvaovien;
    public EmrDm emrDmYhctBenhdanhVaovien;
    
    public Integer idbenhdanhyhctravien;
    public EmrDm emrDmYhctBenhdanhRavien;
    
    public Integer idbenhdanh_vk;
    public EmrDm emrDmYhctBenhdanhVk;
    
    public String motabenhdanhyhctvaovien;
    public String motabenhdanhyhctravien;
    
    public String lstbatcuong;
    public String lstbatcuongTen;
    public String lsttangphu;
    public String lsttangphuTen;
    public String lstkinhmach;
    public String lstkinhmachTen;
    public String lstdinhvibenh;
    public String lstdinhvibenhTen;
    public String lstnguyennhanbenh;
    public String lstnguyennhanbenhTen;
    public Boolean daxoa;
    public Date ngaytao;
    public Integer idnguoitao;
    public Date ngaysua;
    public Integer idnguoisua;
    
    public String lstbatcuongVv;
    public String lstbatcuongVvTen;
    public String lsttangphuVv;
    public String lsttangphuVvTen;
    public String lstkinhmachVv;
    public String lstkinhmachVvTen;
    public String lstdinhvibenhVv;
    public String lstdinhvibenhVvTen;
    public String lstnguyennhanbenhVv;
    public String lstnguyennhanbenhVvTen;
    
    public String motabatcuong;
    public String motatangphu;
    public String motakinhmach;
    public String motanguyennhanbenh;
    public String motabenhdanhyhctvaokhoa;
    
    // Bo sung 02/04/2015 (hien thi tren report YHCT)
    public String lstbatcuongHienthi;
    public String lsttangphuHienthi;
    public String lstkinhmachHienthi;
    public String lstdinhvibenhHienthi;
    public String lstnguyennhanbenhHienthi;
    public String lstbatcuongVvHienthi;
    public String lsttangphuVvHienthi;
    public String lstkinhmachVvHienthi;
    public String lstdinhvibenhVvHienthi;
    public String lstnguyennhanbenhVvHienthi;
    
    // Bo sung 23/04/2015
    public String motabatcuongVv;
    public String motatangphuVv;
    public String motakinhmachVv;
    public String motanguyennhanbenhVv;
}
