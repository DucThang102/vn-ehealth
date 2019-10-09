package vn.ehealth.emr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_yhct_benhan_vaan_chan")
public class EmrYhctBenhanVaanChan {

    @Id public int idhsba;
    @Column public String lsthannhiet;
    @Column public String lsthannhietTen;
    @Column public String lstmohoi;
    @Column public String lstmohoiTen;
    @Column public String lstdaumatco;
    
    @Column(name="lstentdaumatco_ten") public String lstdaumatcoTen;
    
    @Column public String lstchantayTen;
    @Column public String lstlung;
    @Column public String lstlungTen;
    @Column public String lstbungnguc;
    @Column public String lstbungngucTen;
    @Column public String lstan;
    @Column public String lstanTen;
    @Column public String lstuong;
    @Column public String lstuongTen;
    @Column public String lstdaitien;
    @Column public String lstdaitienTen;
    @Column public String lsttieutien;
    @Column public String lsttieutienTen;
    @Column public String lstngu;
    @Column public String lstnguTen;
    @Column public String lstkinhnguyet;
    @Column public String lstkinhnguyetTen;
    @Column public String lstthongkinh;
    @Column public String lstthongkinhTen;
    @Column public String lstdoiha;
    @Column public String lstdoihaTen;
    @Column public String lstsinhduc;
    @Column public String lstsinhducTen;
    @Column public String motavaanchan;
    
    // Add 02/04/2015 (for display in report YHCT)
    @Column public String lsthannhietHienthi;
    @Column public String lstmohoiHienthi;
    
    @Column(name="lstentdaumatco_hienthi") 
    public String lstdaumatcoHienthi;
    
    @Column public String lstchantayHienthi;
    @Column public String lstlungHienthi;
    @Column public String lstbungngucHienthi;
    @Column public String lstanHienthi;
    @Column public String lstuongHienthi;
    @Column public String lstdaitienHienthi;
    @Column public String lsttieutienHienthi;
    @Column public String lstnguHienthi;
    @Column public String lstkinhnguyetHienthi;
    @Column public String lstthongkinhHienthi;
    @Column public String lstdoihaHienthi;
    @Column public String lstsinhducHienthi;
    @Column public String lstsinhducnu;
    @Column public String lstsinhducnuTen;
    @Column public String lstsinhducnuHienthi;
    @Column public Boolean lstchantay;
    
    @Column public String lsthannhietbieuhien;
    @Column public String lsthannhietbieuhienTen;
    @Column public String lsthannhietbieuhienHienthi;
    
    //ngay 24/8/2015
    @Column public Boolean dkxuatvien;
}
