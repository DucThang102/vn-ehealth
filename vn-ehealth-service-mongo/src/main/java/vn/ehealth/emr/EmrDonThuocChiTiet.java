package vn.ehealth.emr;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrDonThuocChiTiet {

    public EmrDmContent emrDmThuoc;
    public EmrDmContent emrDmDuongDungThuoc;
    public EmrDmContent emrDmTanXuatDungThuoc;
    public EmrDmContent emrDmChiDanDungThuoc;
    
    public Date ngaybatdau;
    public Date ngayketthuc;
    public String lieuluongdung;    
    public String chidandungthuoc;
    public String bietduoc;
    
}
