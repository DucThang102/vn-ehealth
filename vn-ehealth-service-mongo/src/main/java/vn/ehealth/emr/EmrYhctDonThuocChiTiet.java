package vn.ehealth.emr;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrYhctDonThuocChiTiet {
    
    public EmrDmContent emrDmYhctViThuoc;    
    public EmrDmContent emrDmDuongDungThuoc;
    public EmrDmContent emrDmChiDanDungThuoc;
    public EmrDmContent emrDmTanXuatDungThuoc;
    
    public Date ngaybatdau;
    public Date ngayketthuc;
    public String lieuluongdung;
    public String chidandungthuoc;    
}
