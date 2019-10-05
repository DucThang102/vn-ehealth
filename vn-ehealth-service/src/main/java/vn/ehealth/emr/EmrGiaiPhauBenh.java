package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

@Data
public class EmrGiaiPhauBenh {

    public int id;
    
    public Integer idhsba;
    
    public Integer idvitrilaymau;
    public EmrDm emrDmViTriLayMau;
    
    public Integer idloaigiaiphau;
    public EmrDm emrDmLoaiGiaiPhauBenh;
    public EmrDm emrDmKetQuaGiaiPhauBenh;
    
    public Integer iddichvugiaiphau;
    public EmrDm emrDmGiaiPhauBenh;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public String bacsichuyenkhoa;
    public Date ngaythuchien;
    public String nhanxetdaithe;
    public String nhanxetvithe;
    public String motachandoangiaiphau;
    public Date ngaylaymausinhthiet;
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemGpbs = new ArrayList<>();
    
}
