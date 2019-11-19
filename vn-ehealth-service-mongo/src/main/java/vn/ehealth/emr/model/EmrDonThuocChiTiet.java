package vn.ehealth.emr.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrDonThuocChiTiet {

    public EmrDmContent emrDmThuoc;
    public EmrDmContent emrDmDuongDungThuoc;
    public EmrDmContent emrDmTanSuatDungThuoc;
    public EmrDmContent emrDmChiDanDungThuoc;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaybatdau;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngayketthuc;
    
    public String lieuluongdung;    
    public String chidandungthuoc;
    public String bietduoc;
    
}
