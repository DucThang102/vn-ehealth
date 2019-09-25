package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EmrVaoKhoa {

    public int id;
    
    public Integer idhsba;
    
    public Integer idkhoadieutri;
    public EmrDm emrDmKhoaDieuTri;
    
    public Date ngaygiovaokhoa;
    public Date ngayketthucdieutri;
    public String tenkhoa;
    public String bacsidieutri;
    public String phong;
    public String giuong;
    public Integer songaydieutri;
    public String tentruongkhoa;
    public Boolean daxoa;
    public Date ngaytao;
    public Integer idnguoitao;
    public Date ngaysua;
    public Integer idnguoisua;
    
    public List<EmrHoiChan> emrHoiChans = new ArrayList<>();
    public List<EmrChamSoc> emrChamSocs = new ArrayList<>();
    public List<EmrDieuTri> emrDieuTris = new ArrayList<>();
    public List<EmrChucNangSong> emrChucNangSongs = new ArrayList<>();

    public Integer stt;
}
