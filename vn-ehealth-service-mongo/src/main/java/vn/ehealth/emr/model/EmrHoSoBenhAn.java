package vn.ehealth.emr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection="emr_ho_so_benh_an")
public class EmrHoSoBenhAn {    
    @Id public ObjectId id;
    
    public String getId() { return id != null? id.toHexString() : null; }
    
    public void setId(String id) { 
        if(id != null) {
            this.id = new ObjectId(id);
        }else {
            this.id = null;
        }
    }
        
    public EmrDmContent emrDmLoaiBenhAn;
    
    public transient ObjectId emrBenhNhanId;
    @Transient public EmrBenhNhan emrBenhNhan;
    
    public transient ObjectId emrCoSoKhamBenhId;
    @Transient public EmrCoSoKhamBenh emrCoSoKhamBenh;
        
    public int nguonDuLieu;    
    public int trangThai;
    public String mayte;
    public String maluutru;
    public String matraodoi;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaytiepnhan;
    
    public String nguoitiepnhan;
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngayluutru;
    
    public String nguoiluutru;
    
    public Date ngaytao;
    
    public boolean isLatest;
    
    public EmrQuanLyNguoiBenh emrQuanLyNguoiBenh;
    
    public EmrTongKetRaVien emrTongKetRaVien;
    
    public EmrTinhTrangRaVien emrTinhTrangRaVien;
    
    public EmrTongKetSanKhoa emrTongKetSanKhoa;  //??
    
    public EmrBenhAn emrBenhAn;
    
    public EmrYhctBenhAn emrYhctBenhAn;
    
    public EmrChanDoan emrChanDoan;
    
    public EmrYhctChanDoan emrYhctChanDoan;
    
    public EmrYhctNhaBa emrYhctNhaBa;
    
    public List<EmrYhctNhaBaGhiChu> emrYhctNhaBaGhiChus = new ArrayList<>();
    
    public List<EmrQuaTrinhSuDungThuoc> emrQuaTrinhSuDungThuocs = new ArrayList<>();
    
    public List<EmrFileDinhKem> emrFileDinhKems = new ArrayList<>();
        
    @Transient public List<EmrVaoKhoa> emrVaoKhoas = new ArrayList<>();
    
    @Transient public String khoaRaVien;
    
    @Transient public List<EmrHinhAnhTonThuong> emrHinhAnhTonThuongs = new ArrayList<>();
    
    @Transient public List<EmrGiaiPhauBenh> emrGiaiPhauBenhs = new ArrayList<>();
    
    @Transient public List<EmrThamDoChucNang> emrThamDoChucNangs = new ArrayList<>();
    
    @Transient public List<EmrPhauThuatThuThuat> emrPhauThuatThuThuats = new ArrayList<>();
    
    @Transient  public List<EmrChanDoanHinhAnh> emrChanDoanHinhAnhs = new ArrayList<>();
    
    @Transient  public List<EmrDonThuoc> emrDonThuocs = new ArrayList<>();
    
    @Transient  public List<EmrYhctDonThuoc> emrYhctDonThuocs = new ArrayList<>();
    
    @Transient  public List<EmrXetNghiem> emrXetNghiems = new ArrayList<>();
      
    
    @Transient public Boolean coPhauThuat;
    @Transient public Boolean coThuThuat;

    public EmrDmContent getEmrDmLoaiBenhAn() {
        return emrDmLoaiBenhAn;
    }
    
    public String getEmrBenhNhanId() {
        return emrBenhNhanId != null? emrBenhNhanId.toHexString(): null;
    }
    
    public void setEmrBenhNhanId(String emrBenhNhanId) {
        if(emrBenhNhanId != null) {
            this.emrBenhNhanId = new ObjectId(emrBenhNhanId);
        }else {
            this.emrBenhNhanId = null;
        }
    }
    
    public EmrBenhNhan getEmrBenhNhan() {
        return emrBenhNhan;
    }
    
    public String getEmrCoSoKhamBenhId() {
        return emrCoSoKhamBenhId != null? emrCoSoKhamBenhId.toHexString(): null;
    }
    
    public void setEmrCoSoKhamBenhId(String emrCoSoKhamBenhId) {
        if(emrCoSoKhamBenhId != null) {
            this.emrCoSoKhamBenhId = new ObjectId(emrCoSoKhamBenhId);
        }else {
            this.emrCoSoKhamBenhId = null;
        }
    }
    
    public EmrCoSoKhamBenh getEmrCoSoKhamBenh() {
        return emrCoSoKhamBenh;
    }
    
    public String getMayte() {
        return mayte;
    }
    
    public String getMaluutru() {
        return maluutru;
    }
    
    public String getMatraodoi() {
        return matraodoi;
    }
    
    public String getGiamdocbenhvien() {
        if(emrCoSoKhamBenh != null) {
            return emrCoSoKhamBenh.giamdoc;
        }
        return "";
    }
    
    public String getTenbenhvien() {
        if(emrCoSoKhamBenh != null) {
            return emrCoSoKhamBenh.ten;
        }
        return "";
    }
    
    public String getDonvichuquan() {
        if(emrCoSoKhamBenh != null) {
            return emrCoSoKhamBenh.donvichuquan;
        }
        return "";
    }
    
    public String getTruongphongth() {
        if(emrCoSoKhamBenh != null) {
            return emrCoSoKhamBenh.truongphongth;
        }
        return "";
    }
    
    public EmrQuanLyNguoiBenh getEmrQuanLyNguoiBenh() {
        return emrQuanLyNguoiBenh;
    }
    
    public EmrTongKetRaVien getEmrTongKetRaVien() {
        return emrTongKetRaVien;
    }
    
    public EmrTinhTrangRaVien getEmrTinhTrangRaVien() {
        return emrTinhTrangRaVien;
    }
    
    public EmrTongKetSanKhoa getEmrTongKetSanKhoa() {
        return emrTongKetSanKhoa;
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
    
    public EmrYhctNhaBa getEmrYhctNhaBa() {
        return emrYhctNhaBa;
    }
    
    public List<EmrYhctNhaBaGhiChu> getEmrYhctNhaBaGhiChus() {
        return emrYhctNhaBaGhiChus;
    }
    
    public List<EmrQuaTrinhSuDungThuoc> getEmrQuaTrinhSuDungThuocs() {
        return emrQuaTrinhSuDungThuocs;
    }
    
    public List<EmrFileDinhKem> getEmrFileDinhKems() {
        return emrFileDinhKems;
    }
    public EmrVaoKhoa[] getEmrVaoKhoas() {
        return emrVaoKhoas.toArray(new EmrVaoKhoa[0]);
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
    
    public List<EmrPhauThuatThuThuat> getEmrPhauThuatThuThuats() {
        return emrPhauThuatThuThuats;
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
    
    public Boolean getCoPhauThuat() {
        return coPhauThuat;
    }
    
    public Boolean getCoThuThuat() {
        return coThuThuat;
    }    
    
    public Boolean getDaxoa() {
        return false;
    }
}
