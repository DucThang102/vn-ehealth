package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrLogTrangThaiTraoDoi {
    
    public int id;
    public EmrDm trangThaiTraoDoi;
    public EmrDanhSachVetTraoDoi emrDanhSachVetTraoDoi;
    public Date thoigianthuchien;
    public Integer idnguoitao;
    public Date ngaytao;
    public Integer idnguoisua;
    public Date ngaysua;
    public Boolean daxoa;
    public String errorMessage;

}
