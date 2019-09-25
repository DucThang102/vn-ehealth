package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

@Data
public class EmrThamDoChucNang {
    public int id;
    
    public Integer idhsba;
    
    public Integer idthamdochucnang;
    public EmrDm emrDmThamDoChucNang;
    
    public Integer idloaithamdochucnang;
    public EmrDm emrDmLoaiThamDoChucNang;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public String noidungyeucau;
    public Date ngaythuchien;
    public String ketqua;
    public String ketluan;
    public String loidan;
    public String bacsichuyenkhoa;
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemTdcns = new ArrayList<>();

}
