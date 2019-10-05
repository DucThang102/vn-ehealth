package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

@Data
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
}
