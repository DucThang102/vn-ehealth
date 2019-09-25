package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

@Data
public class EmrChucNangSong {
    
    public int id;
    
    public Integer idvaokhoa;
    public EmrVaoKhoa emrVaoKhoa;
    
    public String sophieu;
    public List<EmrChucNangSongChiTiet> emrChucNangSongChiTiets = new ArrayList<>();
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemChucNangSongs = new ArrayList<>();

}
