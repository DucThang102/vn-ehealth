package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrXetNghiem {
    
    public int id;
    
    public Integer idloaixetnghiem;
    public EmrDm emrDmLoaiXetNghiem;
    
    public Integer idhsba;
    
    public Integer idxetnghiem;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public Date ngaythuchien;
    public String noidungyeucau;
    public String tailieudinhkem;
    public String nhanxet;
    public String bacsixetnghiem;
    
    public List<EmrXetNghiemDichVu> emrXetNghiemDichVus = new ArrayList<>();
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemXn = new ArrayList<>();
    
    public Boolean daxoa;

}
