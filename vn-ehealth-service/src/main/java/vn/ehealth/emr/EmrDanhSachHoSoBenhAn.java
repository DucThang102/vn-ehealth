package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.ehealth.emr.file.EmrFileDinhKemBenhAn;


@Entity
@Table(name = "emr_danh_sach_ho_so_benh_an")
public class EmrDanhSachHoSoBenhAn {

    @Id public int id;
    
    @Column public Integer idtrangthai;
    @Transient public EmrDm emrDmTrangthai;
    
    @Column public Integer idloaibenhan;
    @Transient public EmrDm emrDmLoaiBenhAn;
    
    @Column public int idbenhnhan;
    @Transient public EmrBenhNhan emrBenhNhan;
    
    @Column public Integer idnguondulieu;
    @Transient public EmrDm emrDmNguondulieu;
    
    @Transient public EmrCoSoKhamBenh emrCoSoKhamBenh;
    
    @Column public String mayte;
    @Column public String maluutru;
    @Column public Boolean daxoa;
    
    @Column public Date ngaytao;
    @Transient public Date ngaytiepnhan;
    
    @Column public Integer idnguoitao;    
    @Transient public String nguoitiepnhan;
    
    @Column public Date ngaysua;
    @Column public Integer idnguoisua;
    @Column public String giamdocbenhvien;
    @Column public String tenbenhvien;
    @Column public String donvichuquan;
    @Column public String truongphongth;
    
    @Transient public EmrQuanLyNguoiBenh emrQuanLyNguoiBenh;
    
    @Transient public EmrTongKetRaVien emrTongKetRaVien;
    
    @Transient public EmrBenhAn emrBenhAn;
    
    @Transient public EmrYhctBenhAn emrYhctBenhAn;
    
    @Transient public EmrChanDoan emrChanDoan;
    
    @Transient public EmrYhctChanDoan emrYhctChanDoan;
    
    @Transient public EmrTinhTrangRaVien emrTinhTrangRaVien;
    
    @Transient public EmrTongKetSanKhoa emrTongKetSanKhoa;
    // NoiPham add 21/07/2016
    
    @Transient public EmrYhctNhaBa emrYhctNhaBa;
    
    @Transient public List<EmrYhctNhaBaGhiChu> emrYhctNhaBaGhiChus = new ArrayList<>();
    // NoiPham add 21/07/2016
    
    @Transient public EmrVaoKhoa[] emrVaoKhoas = new EmrVaoKhoa[0];
    
    @Transient public List<EmrQuaTrinhSuDungThuoc> emrQuaTrinhSuDungThuocs = new ArrayList<>();
    
    @Transient public List<EmrHinhAnhTonThuong> emrHinhAnhTonThuongs = new ArrayList<>();
    
    @Transient public List<EmrGiaiPhauBenh> emrGiaiPhauBenhs = new ArrayList<>();
    
    @Transient public List<EmrThamDoChucNang> emrThamDoChucNangs = new ArrayList<>();
    
    @Transient public List<EmrPhauThuatThuThuat> _emrPhauThuatThuThuats = new ArrayList<>();
    
    @Transient public EmrPhauThuatThuThuat[] emrPhauThuatThuThuats = new EmrPhauThuatThuThuat[0];
    
    @Transient public List<EmrYeuCauTruyCapHsbaChiTiet> emrYeuCauTruyCapHsbaChiTiets = new ArrayList<>();
    
    @Transient public List<EmrChanDoanHinhAnh> emrChanDoanHinhAnhs = new ArrayList<>();
    
    @Transient public List<EmrDonThuoc> emrDonThuocs = new ArrayList<>();
    
    @Transient public List<EmrYhctDonThuoc> emrYhctDonThuocs = new ArrayList<>();
    
    @Transient public List<EmrXetNghiem> emrXetNghiems = new ArrayList<>();

    // NoiPD add 2016/03/18
    @Transient public List<EmrFileDinhKemBenhAn> emrFileDinhKems = new ArrayList<>();
    
    // For transfer HSBA from WS    
    @Column public String matraodoi;   
    
    @Transient public Boolean coPhauThuat;
    @Transient public Boolean coThuThuat;
    
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
    
    public List<EmrFileDinhKemBenhAn> getEmrQuanLyFileDinhKemBenhAn() {
        return emrFileDinhKems;
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
