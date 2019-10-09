package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

@Entity
@Table(name = "emr_chuc_nang_song")
public class EmrChucNangSong {
    
    @Id public int id;
    
    @Column public Integer idvaokhoa;
    
    @Column public String sophieu;
    
    @Transient public List<EmrChucNangSongChiTiet> emrChucNangSongChiTiets = new ArrayList<>();
    
    @Column public Boolean daxoa;
    
    @Transient public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemChucNangSongs = new ArrayList<>();
    
    // From EmrVaoKhoa
    @Transient public String tenKhoa;
    @Transient public String giuong;
    @Transient public String phong;
    
    public int getId() {
        return id;
    }
    
    public Integer getIdvaokhoa() {
        return idvaokhoa;
    }
    
    public String getSophieu() {
        return sophieu;
    }
    
    public List<EmrChucNangSongChiTiet> getEmrChucNangSongChiTiets() {
        return emrChucNangSongChiTiets;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }
    
    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemChucNangSongs() {
        return emrQuanLyFileDinhKemChucNangSongs;
    }
    
    public String getTenKhoa() {
        return tenKhoa;
    }
    
    public String getGiuong() {
        return giuong;
    }
    
    public String getPhong() {
        return phong;
    }
}
