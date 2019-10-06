package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrChucNangSong {
    
    public int id;
    
    public Integer idvaokhoa;
    
    public String sophieu;
    public List<EmrChucNangSongChiTiet> emrChucNangSongChiTiets = new ArrayList<>();
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemChucNangSongs = new ArrayList<>();
    
    // From EmrVaoKhoa
    public String tenKhoa;
    public String giuong;
    public String phong;
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
