package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrChucNangSong {
    
    public int id;
    
    public Integer idvaokhoa;
    public EmrVaoKhoa emrVaoKhoa;
    
    public String sophieu;
    public List<EmrChucNangSongChiTiet> emrChucNangSongChiTiets = new ArrayList<>();
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemChucNangSongs = new ArrayList<>();

}
