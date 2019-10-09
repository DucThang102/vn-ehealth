package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_benh_nhan")
public class EmrBenhNhan {
    
    @Id
    public int id;
    
    @Column
    public Integer idgioitinh;
    
    @Transient
    public EmrDm emrDmGioiTinh;
    
    @Column
    public Integer iddantoc;
    
    @Transient
    public EmrDm emrDmDanToc;
    
    @Column
    public Integer idquocgia;
    
    @Transient
    public EmrDm emrDmQuocGia;
    
    @Column
    public Integer idnghenghiep;
    
    @Transient
    public EmrDm emrDmNgheNghiep;
    
    @Column
    public Integer idphuongxa;
    
    @Transient
    public EmrDm emrDmPhuongXa;
    
    @Column
    public Integer idquanhuyen;
    
    @Transient
    public EmrDm emrDmQuanHuyen;
    
    @Column
    public Integer idtinhthanh;
    
    @Transient
    public EmrDm emrDmTinhThanh;
    
    @Column
    public Integer idnghebo;
    
    @Transient
    public EmrDm emrDmNgheNghiepBo;
    
    @Column
    public Integer idngheme;
    
    @Transient
    public EmrDm emrDmNgheNghiepMe;
    
    @Column
    public String iddinhdanhchinh;
    
    @Column
    public String iddinhdanhphu;
    
    @Column
    public String idhis;
    
    @Column
    public String tendaydu;
        
    @Column
    public Date ngaysinh;
    
    @Column
    public String diachi;
    
    @Column
    public String noilamviec;
    
    @Column
    public String sobhyt;
    
    @Column
    public Date ngayhethanthebhyt;
    
    @Column
    public String hotenbo;
    
    
    @Column
    public String hotenme;
    
    @Column
    public String tennguoibaotin;
    
    @Column
    public String diachinguoibaotin;
    
    @Column
    public String sodienthoainguoibaotin;
    
    @Column
    public Boolean daxoa;
    
    @Column
    public Date ngaytao;
    
    @Column
    public Integer idnguoitao;
    
    @Column
    public Date ngaysua;
    
    @Column
    public Integer idnguoisua; 
    
    @Column(name = "ngay_sinh_cua_bo")
    public Date ngaySinhCuaBo;
    
    @Column(name = "ngay_sinh_cua_me")
    public Date ngaySinhCuaMe;
    
    @Column(name = "trinh_do_van_hoa_cua_bo")
    public String trinhDoVanHoaCuaBo;
    
    @Column(name = "trinh_do_van_hoa_cua_me")
    public String trinhDoVanHoaCuaMe;
    
    @Transient
    public String tuoi;

}
