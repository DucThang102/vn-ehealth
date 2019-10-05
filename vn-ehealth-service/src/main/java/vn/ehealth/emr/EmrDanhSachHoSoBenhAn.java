package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKemBenhAn;

public class EmrDanhSachHoSoBenhAn {

    public int id;
    
    public Integer idtrangthai;
    public EmrDm emrDmTrangthai;
    
    public Integer idloaibenhan;
    public EmrDm emrDmLoaiBenhAn;
    
    public Integer idbenhnhan;
    public EmrBenhNhan emrBenhNhan;
    
    public Integer idnguondulieu;
    public EmrDm emrDmNguondulieu;
    
    public String mayte;
    public String maluutru;
    public Boolean daxoa;
    
    // Them 25/05/2015
    public Boolean isLocked;
    
    public Date ngaytao;
    public Integer idnguoitao;
    public Date ngaysua;
    public Integer idnguoisua;
    public String giamdocbenhvien;
    public String tenbenhvien;
    public String donvichuquan;
    public String truongphongth;
    
    public EmrQuanLyNguoiBenh emrQuanLyNguoiBenh;
    public EmrTongKetRaVien emrTongKetRaVien;
    public EmrBenhAn emrBenhAn;
    public EmrYhctBenhAn emrYhctBenhAn;
    public EmrChanDoan emrChanDoan;
    public EmrYhctChanDoan emrYhctChanDoan;
    public EmrTinhTrangRaVien emrTinhTrangRaVien;
    public EmrTongKetSanKhoa emrTongKetSanKhoa;
    // NoiPham add 21/07/2016
    public EmrYhctNhaBa emrYhctNhaBa;
    public List<EmrYhctNhaBaGhiChu> emrYhctNhaBaGhiChus = new ArrayList<>();
    // NoiPham add 21/07/2016
    public EmrVaoKhoa[] emrVaoKhoas = new EmrVaoKhoa[0];
    public List<EmrQuaTrinhSuDungThuoc> emrQuaTrinhSuDungThuocs = new ArrayList<>();
    public List<EmrHinhAnhTonThuong> emrHinhAnhTonThuongs = new ArrayList<>();
    public List<EmrGiaiPhauBenh> emrGiaiPhauBenhs = new ArrayList<>();
    public List<EmrThamDoChucNang> emrThamDoChucNangs = new ArrayList<>();
    public EmrPhauThuatThuThuat[] emrPhauThuatThuThuats = new EmrPhauThuatThuThuat[0];
    public List<EmrYeuCauTruyCapHsbaChiTiet> emrYeuCauTruyCapHsbaChiTiets = new ArrayList<>();
    public List<EmrChanDoanHinhAnh> emrChanDoanHinhAnhs = new ArrayList<>();
    public List<EmrDonThuoc> emrDonThuocs = new ArrayList<>();  
    public List<EmrYhctDonThuoc> emrYhctDonThuocs = new ArrayList<>();  
    public List<EmrXetNghiem> emrXetNghiems = new ArrayList<>();
    // NoiPD add 2016/03/18
    public List<EmrQuanLyFileDinhKemBenhAn> emrQuanLyFileDinhKemBenhAn = new ArrayList<>();
    
    // For validate CDA
    public String validateCDAResult;
    
    // For storing cda text of tong ket san khoa
    public String tongketsankhoaCda;
    
    // For transfer HSBA from WS    
    public String matraodoi;   
    public String transferUserName;    
    public String transferPassword;
    
    public Boolean coPhauThuat;
    public Boolean coThuThuat;
    
    //ngay 29/6/2015
    public Boolean guimotphan;
    public Integer phangui;
    
    // Add new by SonVu 05/09/2016
    public String maLienQuan;
}
