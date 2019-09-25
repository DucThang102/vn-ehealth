package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

@Data
public class EmrChanDoanHinhAnh {

    public int id;
    
    public Integer idhsba;
    
    public Integer idloaichandoan;
    public EmrDm emrDmLoaiChanDoanHinhAnh;
    
    public Integer iddichvuchandoan;
    public EmrDm emrDmChanDoanHinhAnh;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public Date ngaythuchien;
    public String ketqua;
    public String ketluan;
    public String loidan;
    public String bacsichuyenkhoa;
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemCdha = new ArrayList<>(); 
}
