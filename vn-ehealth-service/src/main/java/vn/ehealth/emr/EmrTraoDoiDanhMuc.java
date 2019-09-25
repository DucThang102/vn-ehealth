package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrTraoDoiDanhMuc {

    public int idvettraodoi;
    public EmrDanhSachVetTraoDoi emrDanhSachVetTraoDoi;
    public int iddanhmuc;
    public Date ngayxuatban;
    public Date ngaycohieuluc;
    public Date ngayhethieuluc;
    public String phienban;
}
