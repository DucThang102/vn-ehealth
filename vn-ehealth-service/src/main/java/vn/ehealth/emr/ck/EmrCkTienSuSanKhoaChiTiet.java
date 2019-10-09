package vn.ehealth.emr.ck;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tien_su_san_khoa_chi_tiet")
public class EmrCkTienSuSanKhoaChiTiet {
    
    @Id public int id;
    @Column public Integer idhsba;
    
    @Column public int lancothai;
    @Column public Integer nam;
    @Column public Boolean duthang;
    @Column public Boolean denon;
    @Column public Boolean say;
    @Column public Boolean song;
    @Column public Boolean hut;
    @Column public Boolean nao;
    @Column public Boolean covac;
    @Column public Boolean chuangoaitucung;
    @Column public Boolean chuatrung;
    @Column public Boolean thaichetluu;
    @Column public Double cannang;
    @Column public String phuongphapde;
    @Column public String taibien;
    @Column public Boolean daxoa;
    
    //ngay 23/7/2015
    @Column public String dienbien;
    @Column public String hausan;
    @Column public Date ngayketthucthainghen;
    @Column public Integer tuoithai;
    
    public int getId() {
        return id;
    }
    
    public Integer getIdhsba() {
        return idhsba;
    }
    
    public int getLancothai() {
        return lancothai;
    }
    
    public Integer getNam() {
        return nam;
    }
    
    public Boolean getDuthang() {
        return duthang;
    }
    
    public Boolean getDenon() {
        return denon;
    }
    
    public Boolean getSay() {
        return say;
    }
    
    public Boolean getSong() {
        return song;
    }
    
    public Boolean getHut() {
        return hut;
    }
    
    public Boolean getNao() {
        return nao;
    }
    
    public Boolean getCovac() {
        return covac;
    }
    
    public Boolean getChuangoaitucung() {
        return chuangoaitucung;
    }
    
    public Boolean getChuatrung() {
        return chuatrung;
    }
    
    public Boolean getThaichetluu() {
        return thaichetluu;
    }
    
    public Double getCannang() {
        return cannang;
    }
    
    public String getPhuongphapde() {
        return phuongphapde;
    }
    
    public String getTaibien() {
        return taibien;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }
    
    public String getDienbien() {
        return dienbien;
    }
    
    public String getHausan() {
        return hausan;
    }
    
    public Date getNgayketthucthainghen() {
        return ngayketthucthainghen;
    }
    
    public Integer getTuoithai() {
        return tuoithai;
    }    
}
