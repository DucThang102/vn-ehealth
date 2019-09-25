package vn.ehealth.emr.ck;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EmrCkTienSuSanKhoa {
    
    public int idhsba;
    public Integer idchuyenkhoa;
    public String thoigiandiadiem;
    public String tuoithai;
    public String dienbienthai;
    public String cachde;
    public String tresosinh;
    public String hausan;
    
    public String paraDuthang;
    public String paraDenon;
    public String paraSay;
    public String paraSong;
    public List<EmrCkTienSuSanKhoaChiTiet> emrCkTienSuSanKhoaChiTiets = new ArrayList<>();

    //add SonVT 08042016
    public Integer solancothai;

}
