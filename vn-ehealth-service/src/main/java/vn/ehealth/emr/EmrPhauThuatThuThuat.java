package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.ehealth.emr.file.EmrFileDinhKem;

@Entity
@Table(name = "emr_phau_thuat_thu_thuat")
public class EmrPhauThuatThuThuat {

    @Id public int id;
    
    @Column public Integer idhsba;
        
    @Column public Integer idicdchandoansau;
    @Transient public EmrDm emrDmMaBenhChandoansau;
    
    @Column public Integer idicdchandoantruoc;
    @Transient public EmrDm emrDmMaBenhChandoantruoc;
    
    @Column public Integer idphauthuat;
    @Transient public EmrDm emrDmPhauThuThuat;
    
    @Column public Date ngaygiopttt;
    @Column public String bacsithuchien;
    @Column public String bacsygayme;
    @Column public String chidinhptt;
    @Column public String phuongphapvocam;
    @Column public String luocdoptt;
    @Column public String trinhtuptt;
    @Column public Boolean daxoa;
    @Column public Date ngaytao;
    @Column public Integer idnguoitao;
    @Column public Date ngaysua;
    @Column public Integer idnguoisua;
    
    @Column public String motachandoantruocpt;
    @Column public String motachandoansaupt;
    
    @Transient public List<EmrFileDinhKem> emrFileDinhKemPttts = new ArrayList<>();
    
    @Transient public List<EmrHoiDongPttt> emrHoiDongPttts = new ArrayList<EmrHoiDongPttt>(0);

    @Column public Boolean loaipttt;
    @Column(name = "loaimo_chklist") public String loaimoChklist;
    
    public int getId() {
        return id;
    }
    
    public Integer getIdhsba() {
        return idhsba;
    }
    
    public Integer getIdicdchandoansau() {
        return idicdchandoansau;
    }
    
    public EmrDm getEmrDmMaBenhChandoansau() {
        return emrDmMaBenhChandoansau;
    }
    
    public Integer getIdicdchandoantruoc() {
        return idicdchandoantruoc;
    }
    
    public EmrDm getEmrDmMaBenhChandoantruoc() {
        return emrDmMaBenhChandoantruoc;
    }
    
    public Integer getIdphauthuat() {
        return idphauthuat;
    }
    
    
    public EmrDm getEmrDmPhauThuThuat() {
        return emrDmPhauThuThuat;
    }
    
    public Date getNgaygiopttt() {
        return ngaygiopttt;
    }
    
    public String getBacsithuchien() {
        return bacsithuchien;
    }
    
    public String getBacsygayme() {
        return bacsygayme;
    }
    
    public String getChidinhptt() {
        return chidinhptt;
    }
    
    public String getPhuongphapvocam() {
        return phuongphapvocam;
    }
    
    public String getLuocdoptt() {
        return luocdoptt;
    }
    
    public String getTrinhtuptt() {
        return trinhtuptt;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }
    
    public Date getNgaytao() {
        return ngaytao;
    }
    
    public Integer getIdnguoitao() {
        return idnguoitao;
    }
    
    public Date getNgaysua() {
        return ngaysua;
    }
    
    public Integer getIdnguoisua() {
        return idnguoisua;
    }
    
    public String getMotachandoantruocpt() {
        return motachandoantruocpt;
    }
    
    public String getMotachandoansaupt() {
        return motachandoansaupt;
    }
    
    public List<EmrFileDinhKem> getEmrQuanLyFileDinhKemPttt() {
        return emrFileDinhKemPttts;
    }
    
    public List<EmrHoiDongPttt> getEmrHoiDongPttts() {
        return emrHoiDongPttts;
    }
    
    public Boolean getLoaipttt() {
        return loaipttt;
    }
    
    public String getLoaimoChklist() {
        return loaimoChklist;
    }
}
