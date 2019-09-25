package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrYeuCauTruyCapHsbaChiTiet {
    
    public int id;
    public EmrDanhSachHoSoBenhAn emrDanhSachHoSoBenhAn;
    public EmrYeuCauTruyCapHsba emrYeuCauTruyCapHsba;
    public Integer idnguoitao;
    public Date ngaytao;
    public Integer idnguoisua;
    public Date ngaysua;
    public Boolean daxoa;

}
