package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection="emr_ho_so_benh_an")
public class EmrHoSoBenhAn {    
    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }
        
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
    
    public Date ngaytiepnhan;
    public String nguoitiepnhan;
    
    public String giamdocbenhvien;
    public String tenbenhvien;
    public String donvichuquan;
    public String truongphongth;
    
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
}
