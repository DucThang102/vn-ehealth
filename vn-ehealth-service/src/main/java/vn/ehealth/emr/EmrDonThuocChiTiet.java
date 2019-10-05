package vn.ehealth.emr;

import java.util.Date;

public class EmrDonThuocChiTiet {

    public int id;
    
    public Integer iddonthuoc;    
    
    public Integer idloaiduongdung;
    public EmrDm emrDmDuongDungThuoc;
    
    public Integer idthuoc;
    public EmrDm emrDmThuoc;
    
    public Integer idtanxuatdung;
    public EmrDm emrDmTanXuatDungThuoc;
    
    public Date ngaybatdau;
    public Date ngayketthuc;
    public String lieuluongdung;
    
    public String chidandungthuoc;
    
    public Boolean daxoa;
    
    public String bietduoc;
    
    public Integer idchidandungthuoc;
}
