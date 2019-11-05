package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "emr_chuc_nang_song_chi_tiet")
public class EmrChucNangSongChiTiet {
    
    @Id public int id;
    
    @Column public Integer idchucnangsong;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    @Column public Date ngaytheodoi;
    
    @Column public Double mach;
    @Column public Double nhietdo;
    @Column public Integer huyetapthap;
    @Column public Integer huyetapcao;
    @Column public Integer nhiptho;
    @Column public Integer cannang;
    @Column public String ytatheodoi;
    @Column public Boolean daxoa;
    
    public int getId() {
        return id;
    }
    
    public Integer getIdchucnangsong() {
        return idchucnangsong;
    }
    
    public Date getNgaytheodoi() {
        return ngaytheodoi;
    }
    
    public Double getMach() {
        return mach;
    }
    
    public Double getNhietdo() {
        return nhietdo;
    }
    
    public Integer getHuyetapthap() {
        return huyetapthap;
    }
    
    public Integer getHuyetapcao() {
        return huyetapcao;
    }    
    
    public Integer getNhiptho() {
        return nhiptho;
    }
    
    public Integer getCannang() {
        return cannang;
    }
    
    public String getYtatheodoi() {
        return ytatheodoi;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }
}
