package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

@Data
public class EmrChamSoc {

    public int id;
    
    public Integer idvaokhoa;
    public EmrVaoKhoa emrVaoKhoa;
    
    public String sotochamsoc;
    public List<EmrQuaTrinhChamSoc> emrQuaTrinhChamSocs = new ArrayList<>();
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemChamSocs = new ArrayList<>();

}
