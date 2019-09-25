package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EmrBenhNhan {
    
    public int id;
    
    public Integer idgioitinh;
    public EmrDm emrDmGioiTinh;
    
    public Integer iddantoc;
    public EmrDm emrDmDanToc;
    
    public Integer idquocgia;
    public EmrDm emrDmQuocGia;
    
    public Integer idnghenghiep;
    public EmrDm emrDmNgheNghiep;
    
    public Integer idphuongxa;
    public EmrDm emrDmPhuongXa;
    
    public Integer idquanhuyen;
    public EmrDm emrDmQuanHuyen;
    
    public Integer idtinhthanh;
    public EmrDm emrDmTinhThanh;
    
    public Integer idnghebo;
    public EmrDm emrDmNgheNghiepBo;
    
    public Integer idngheme;
    public EmrDm emrDmNgheNghiepMe;
    
    public String iddinhdanhchinh;
    public String iddinhdanhphu;
    public String idhis;
    public String tendaydu;
    public Date ngaysinh;
    
    public String diachi;
    public String noilamviec;
    public String sobhyt;
    public Date ngayhethanthebhyt;
    public String hotenbo;
    public String hotenme;
    public String tennguoibaotin;
    public String diachinguoibaotin;
    public String sodienthoainguoibaotin;
    public Boolean daxoa;
    public Date ngaytao;
    public Integer idnguoitao;
    public Date ngaysua;
    public Integer idnguoisua; 
    public Date ngaySinhCuaBo;
    public Date ngaySinhCuaMe;
    public String trinhDoVanHoaCuaBo;
    public String trinhDoVanHoaCuaMe;
    public String tuoi;
    
    public List<EmrDanhSachHoSoBenhAn> emrDanhSachHoSoBenhAns = new ArrayList<>();

}
