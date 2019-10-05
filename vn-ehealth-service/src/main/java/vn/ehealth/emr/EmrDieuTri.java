package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrDieuTri {
    
    public int id;
    
    public Integer idvaokhoa;
    public EmrVaoKhoa emrVaoKhoa;
    
    public String sotodieutri;
    public List<EmrQuaTrinhDieuTri> emrQuaTrinhDieuTris = new ArrayList<>();
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemDieuTris = new ArrayList<>();


}
