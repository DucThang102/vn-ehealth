package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmrDanhSachVetTraoDoi {

    public int id;
    public EmrDm emrDmTuSinhByIdtrangthai;
    public EmrDm emrDmTuSinhByIdloaitailieu;
    public String tendiemdi;
    public String tendiemden;
    public String madiemden;
    public String madiemdi;
    public String maphientraodoi;
    public Date ngaygui;
    public Date ngayhoanthanh;
    public String thongtinbosung;
    public String tenfile;
    public String duongdanfile;
    public EmrTraoDoiDanhMuc emrTraoDoiDanhMuc;
    public List<EmrLogTrangThaiTraoDoi> emrLogTrangThaiTraoDois = new ArrayList<>();
    public EmrTraoDoiHsba emrTraoDoiHsba;

    //them ngay 22/4/2015: chu ky so
    public Boolean chukyso;
    public Boolean trangthaiky;
    public String nguoiky;
    public String chucvunguoiky;
    public String tochucky;
    public String emailnguoiky;
}
