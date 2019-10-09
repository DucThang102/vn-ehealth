package vn.ehealth.emr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_yhct_benhan_thiet_chan")
public class EmrYhctBenhanThietChan {

    @Id public int idhsba;
    @Column public String lstxucchan;
    @Column public String lstxucchanTen;
    @Column public String lstconhuc;
    @Column public String lstconhucTen;
    @Column public String lstphucchan;
    @Column public String lstphucchanTen;
    @Column public String lstmachchantongquat;
    @Column public String lstmachchantongquatTen;
    @Column public String lstmachchanThonTaytrai;
    @Column public String lstmachchanThonTaytraiTen;
    @Column public String lstmachchanQuanTaytrai;
    @Column public String lstmachchanQuanTaytraiTen;
    @Column public String lstmachchanXichTaytrai;
    @Column public String lstmachchanXichTaytraiTen;
    @Column public String lstmachchanQuanTayphai;
    @Column public String lstmachchanQuanTayphaiTen;
    @Column public String lstmachchanThonTayphai;
    @Column public String lstmachchanThonTayphaiTen;
    @Column public String lstmachchanXichTayphai;
    @Column public String lstmachchanXichTayphaiTen;
    @Column public String motathietchan;
    
    @Column public String motamachchanTayphai;
    @Column public String motamachchanTaytrai;
    
    @Column public String motaxucchan;
    
    @Column public String lstxucchanHienthi;
    @Column public String lstconhucHienthi;
    @Column public String lstphucchanHienthi;
    @Column public String lstmachchantongquatHienthi;
    @Column public String lstmachchanThonTaytraiHienthi;
    @Column public String lstmachchanQuanTaytraiHienthi;
    @Column public String lstmachchanXichTaytraiHienthi;
    @Column public String lstmachchanQuanTayphaiHienthi;
    @Column public String lstmachchanThonTayphaiHienthi;
    @Column public String lstmachchanXichTayphaiHienthi;
    
    @Column public String lstxucchanmohoi;
    @Column public String lstxucchanmohoiTen;
    @Column public String lstxucchanmohoiHienthi;
}
