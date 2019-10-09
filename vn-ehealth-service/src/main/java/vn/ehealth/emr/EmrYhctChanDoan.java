package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_yhct_chan_doan")
public class EmrYhctChanDoan {

    @Id
    public int idhsba;

    @Column
    public Integer idbenhdanhyhctvaovien;
    
    @Transient
    public EmrDm emrDmYhctBenhdanhVaovien;
    
    @Column
    public Integer idbenhdanhyhctravien;
    
    @Transient
    public EmrDm emrDmYhctBenhdanhRavien;
    
    @Column
    public Integer idbenhdanh_vk;
    
    @Transient
    public EmrDm emrDmYhctBenhdanhVk;
    
    @Column
    public String motabenhdanhyhctvaovien;
    
    @Column
    public String motabenhdanhyhctravien;
    
    @Column
    public String lstbatcuong;
    
    @Column(name = "lstbatcuong_ten")
    public String lstbatcuongTen;
    
    @Column
    public String lsttangphu;
    
    @Column(name = "lsttangphu_ten")
    public String lsttangphuTen;
    
    @Column
    public String lstkinhmach;
    
    @Column(name = "lstkinhmach_ten")
    public String lstkinhmachTen;
    
    @Column
    public String lstdinhvibenh;
    
    @Column(name = "lstdinhvibenh_ten")
    public String lstdinhvibenhTen;
    
    @Column
    public String lstnguyennhanbenh;
    
    @Column(name = "lstnguyennhanbenh_ten")
    public String lstnguyennhanbenhTen;
    
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
    
    @Column(name = "lstbatcuong_vv")
    public String lstbatcuongVv;
    
    @Column(name = "lstbatcuong_ten_vv")
    public String lstbatcuongVvTen;
    
    @Column(name = "lsttangphu_vv")
    public String lsttangphuVv;
    
    @Column(name = "lsttangphu_ten_vv")
    public String lsttangphuVvTen;
    
    @Column(name = "lstkinhmach_vv")
    public String lstkinhmachVv;
    
    @Column(name = "lstkinhmach_ten_vv")
    public String lstkinhmachVvTen;
    
    @Column(name = "lstdinhvibenh_vv")
    public String lstdinhvibenhVv;
    
    @Column(name = "lstdinhvibenh_ten_vv")
    public String lstdinhvibenhVvTen;
    
    @Column(name = "lstnguyennhanbenh_vv")
    public String lstnguyennhanbenhVv;
    
    @Column(name = "lstnguyennhanbenh_ten_vv")
    public String lstnguyennhanbenhVvTen;
    
    @Column
    public String motabatcuong;
    
    @Column
    public String motatangphu;
    
    @Column
    public String motakinhmach;
    
    @Column
    public String motanguyennhanbenh;
    
    @Column
    public String motabenhdanhyhctvaokhoa;
    
    // Bo sung 02/04/2015 (hien thi tren report YHCT)
    
    @Column(name = "lstbatcuong_hienthi")
    public String lstbatcuongHienthi;
    
    @Column(name = "lsttangphu_hienthi")
    public String lsttangphuHienthi;
    
    @Column(name = "lstkinhmach_hienthi")
    public String lstkinhmachHienthi;
    
    @Column(name = "lstdinhvibenh_hienthi")
    public String lstdinhvibenhHienthi;
    
    @Column(name = "lstnguyennhanbenh_hienthi")
    public String lstnguyennhanbenhHienthi;
    
    @Column(name = "lstbatcuong_vv_hienthi")
    public String lstbatcuongVvHienthi;
    
    @Column(name = "lsttangphu_vv_hienthi")
    public String lsttangphuVvHienthi;
    
    @Column(name = "lstkinhmach_vv_hienthi")
    public String lstkinhmachVvHienthi;
    
    @Column(name = "lstdinhvibenh_vv_hienthi")
    public String lstdinhvibenhVvHienthi;
    
    @Column(name = "lstnguyennhanbenh_vv_hienthi")
    public String lstnguyennhanbenhVvHienthi;
    
    // Bo sung 23/04/2015
    @Column(name = "motabatcuong_vv")
    public String motabatcuongVv;
    
    @Column(name = "motatangphu_vv")
    public String motatangphuVv;
    
    @Column(name = "motakinhmach_vv")
    public String motakinhmachVv;
    
    @Column(name = "motanguyennhanbenh_vv")
    public String motanguyennhanbenhVv;
}
