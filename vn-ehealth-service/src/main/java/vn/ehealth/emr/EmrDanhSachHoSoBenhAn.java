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
    
    // For transfer HSBA from WS    
    public String matraodoi;   
    
    public Boolean coPhauThuat;
    public Boolean coThuThuat;
    public int getId() {
        return id;
    }
    public Integer getIdtrangthai() {
        return idtrangthai;
    }
    public EmrDm getEmrDmTrangthai() {
        return emrDmTrangthai;
    }
    public Integer getIdloaibenhan() {
        return idloaibenhan;
    }
    public EmrDm getEmrDmLoaiBenhAn() {
        return emrDmLoaiBenhAn;
    }
    public Integer getIdbenhnhan() {
        return idbenhnhan;
    }
    public EmrBenhNhan getEmrBenhNhan() {
        return emrBenhNhan;
    }
    public Integer getIdnguondulieu() {
        return idnguondulieu;
    }
    public EmrDm getEmrDmNguondulieu() {
        return emrDmNguondulieu;
    }
    public String getMayte() {
        return mayte;
    }
    public String getMaluutru() {
        return maluutru;
    }
    public Boolean getDaxoa() {
        return daxoa;
    }
    public Date getNgaytao() {
        return ngaytao;
    }
    public Integer getIdnguoitao() {
        return idnguoitao;
    }
    public Date getNgaysua() {
        return ngaysua;
    }
    public Integer getIdnguoisua() {
        return idnguoisua;
    }
    public String getGiamdocbenhvien() {
        return giamdocbenhvien;
    }
    public String getTenbenhvien() {
        return tenbenhvien;
    }
    public String getDonvichuquan() {
        return donvichuquan;
    }
    public String getTruongphongth() {
        return truongphongth;
    }
    public EmrQuanLyNguoiBenh getEmrQuanLyNguoiBenh() {
        return emrQuanLyNguoiBenh;
    }
    public EmrTongKetRaVien getEmrTongKetRaVien() {
        return emrTongKetRaVien;
    }
    public EmrBenhAn getEmrBenhAn() {
        return emrBenhAn;
    }
    public EmrYhctBenhAn getEmrYhctBenhAn() {
        return emrYhctBenhAn;
    }
    public EmrChanDoan getEmrChanDoan() {
        return emrChanDoan;
    }
    public EmrYhctChanDoan getEmrYhctChanDoan() {
        return emrYhctChanDoan;
    }
    public EmrTinhTrangRaVien getEmrTinhTrangRaVien() {
        return emrTinhTrangRaVien;
    }
    public EmrTongKetSanKhoa getEmrTongKetSanKhoa() {
        return emrTongKetSanKhoa;
    }
    public EmrYhctNhaBa getEmrYhctNhaBa() {
        return emrYhctNhaBa;
    }
    public List<EmrYhctNhaBaGhiChu> getEmrYhctNhaBaGhiChus() {
        return emrYhctNhaBaGhiChus;
    }
    public EmrVaoKhoa[] getEmrVaoKhoas() {
        return emrVaoKhoas;
    }
    public List<EmrQuaTrinhSuDungThuoc> getEmrQuaTrinhSuDungThuocs() {
        return emrQuaTrinhSuDungThuocs;
    }
    public List<EmrHinhAnhTonThuong> getEmrHinhAnhTonThuongs() {
        return emrHinhAnhTonThuongs;
    }
    public List<EmrGiaiPhauBenh> getEmrGiaiPhauBenhs() {
        return emrGiaiPhauBenhs;
    }
    public List<EmrThamDoChucNang> getEmrThamDoChucNangs() {
        return emrThamDoChucNangs;
    }
    public EmrPhauThuatThuThuat[] getEmrPhauThuatThuThuats() {
        return emrPhauThuatThuThuats;
    }
    public List<EmrYeuCauTruyCapHsbaChiTiet> getEmrYeuCauTruyCapHsbaChiTiets() {
        return emrYeuCauTruyCapHsbaChiTiets;
    }
    public List<EmrChanDoanHinhAnh> getEmrChanDoanHinhAnhs() {
        return emrChanDoanHinhAnhs;
    }
    public List<EmrDonThuoc> getEmrDonThuocs() {
        return emrDonThuocs;
    }
    public List<EmrYhctDonThuoc> getEmrYhctDonThuocs() {
        return emrYhctDonThuocs;
    }
    public List<EmrXetNghiem> getEmrXetNghiems() {
        return emrXetNghiems;
    }
    public List<EmrQuanLyFileDinhKemBenhAn> getEmrQuanLyFileDinhKemBenhAn() {
        return emrQuanLyFileDinhKemBenhAn;
    }
    public String getMatraodoi() {
        return matraodoi;
    }
    public Boolean getCoPhauThuat() {
        return coPhauThuat;
    }
    public Boolean getCoThuThuat() {
        return coThuThuat;
    }
    
    
    
}
