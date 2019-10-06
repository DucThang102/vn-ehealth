package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrPhauThuatThuThuat {

    public int id;
    
    public Integer idhsba;
        
    public Integer idicdchandoansau;
    public EmrDm emrDmMaBenhChandoansau;
    
    public Integer idicdchandoantruoc;
    public EmrDm emrDmMaBenhChandoantruoc;
    
    public Integer idphauthuat;
    public EmrDm emrDmPhauThuThuat;
    
    public Date ngaygiopttt;
    public String bacsithuchien;
    public String bacsygayme;
    public String chidinhptt;
    public String phuongphapvocam;
    public String luocdoptt;
    public String trinhtuptt;
    public Boolean daxoa;
    public Date ngaytao;
    public Integer idnguoitao;
    public Date ngaysua;
    public Integer idnguoisua;
    
    public String motachandoantruocpt;
    public String motachandoansaupt;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemPttt = new ArrayList<>();
    public List<EmrHoiDongPttt> emrHoiDongPttts = new ArrayList<EmrHoiDongPttt>(0);

    public Boolean loaipttt;
    public String loaimoChklist;
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
    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemPttt() {
        return emrQuanLyFileDinhKemPttt;
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
