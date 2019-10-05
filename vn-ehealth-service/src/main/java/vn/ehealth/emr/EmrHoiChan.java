package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrHoiChan {

    public int id;
    
    public Integer idvaokhoa;
    public EmrVaoKhoa emrVaoKhoa;
    
    public Date ngaythuchien;
    public String tomtatdienbien;
    public String ketluanhoichan;
    public String huongdieutri;
    public Boolean daxoa;
    public List<EmrHoiDongHoiChan> emrHoiDongHoiChans = new ArrayList<>();
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemHoiChans = new ArrayList<>();

}
