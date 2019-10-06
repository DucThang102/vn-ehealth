package vn.ehealth.emr;

public class EmrXetNghiemKetQua {

    public int id;
    
    public Integer idthongdich;
    public EmrDm emrDmDichKetQuaXetNghiem;
    
    public Integer idchisoxetnghiem;
    public EmrDm emrDmChiSoXetNghiem;
    
    public Integer iddmxetnghiem;
    public EmrDm emrDmXetNghiem;
    
    public Integer iddichvuxetnghiem;
    
    public Integer idxetnghiem;
    
    public String giatrido;
    public Boolean daxoa;
    
    public int getId() {
        return id;
    }
    public Integer getIdthongdich() {
        return idthongdich;
    }
    public EmrDm getEmrDmDichKetQuaXetNghiem() {
        return emrDmDichKetQuaXetNghiem;
    }
    public Integer getIdchisoxetnghiem() {
        return idchisoxetnghiem;
    }
    public EmrDm getEmrDmChiSoXetNghiem() {
        return emrDmChiSoXetNghiem;
    }
    public Integer getIddmxetnghiem() {
        return iddmxetnghiem;
    }
    public EmrDm getEmrDmXetNghiem() {
        return emrDmXetNghiem;
    }
    public Integer getIddichvuxetnghiem() {
        return iddichvuxetnghiem;
    }
    public Integer getIdxetnghiem() {
        return idxetnghiem;
    }
    public String getGiatrido() {
        return giatrido;
    }
    public Boolean getDaxoa() {
        return daxoa;
    }    
    
}
