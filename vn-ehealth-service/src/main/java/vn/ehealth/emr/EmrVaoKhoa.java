package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_vao_khoa")
public class EmrVaoKhoa {

    @Id
    public int id;
    
    @Column
    public Integer idhsba;
    
    @Column
    public Integer idkhoadieutri;
    
    @Transient
    public EmrDm emrDmKhoaDieuTri;
    
    @Column
    public Date ngaygiovaokhoa;
    
    @Column
    public Date ngayketthucdieutri;
    
    @Column
    public String tenkhoa;
    
    @Column
    public String bacsidieutri;
    
    @Column
    public String phong;
    
    @Column
    public String giuong;
    
    @Column
    public Integer songaydieutri;
    
    @Column
    public String tentruongkhoa;
    
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
    
    @Transient public List<EmrHoiChan> emrHoiChans = new ArrayList<>();
    
    @Transient public List<EmrChamSoc> emrChamSocs = new ArrayList<>();
    
    @Transient public List<EmrDieuTri> emrDieuTris = new ArrayList<>();
    
    @Transient public List<EmrChucNangSong> emrChucNangSongs = new ArrayList<>();

    @Column
    public Integer stt;
}
