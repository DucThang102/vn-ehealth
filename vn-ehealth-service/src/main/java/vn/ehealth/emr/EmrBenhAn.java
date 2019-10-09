package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.ehealth.emr.ck.EmrCkChanTayMieng;
import vn.ehealth.emr.ck.EmrCkChucNangSinhHoat;
import vn.ehealth.emr.ck.EmrCkCoXuongKhop;
import vn.ehealth.emr.ck.EmrCkHoHap;
import vn.ehealth.emr.ck.EmrCkHuongDieuTriHuyetHoc;
import vn.ehealth.emr.ck.EmrCkHuongDieuTriTcm;
import vn.ehealth.emr.ck.EmrCkKhamPhuKhoa;
import vn.ehealth.emr.ck.EmrCkKhamSanKhoa;
import vn.ehealth.emr.ck.EmrCkKhamSoSinh;
import vn.ehealth.emr.ck.EmrCkMat;
import vn.ehealth.emr.ck.EmrCkMoiSinh;
import vn.ehealth.emr.ck.EmrCkPhuongPhapDieuTriUngBuou;
import vn.ehealth.emr.ck.EmrCkPhuongPhapHoiSinh;
import vn.ehealth.emr.ck.EmrCkQuaTrinhBenhLyTcm;
import vn.ehealth.emr.ck.EmrCkQuaTrinhSinhTruong;
import vn.ehealth.emr.ck.EmrCkSkTinhTrangSanPhu;
import vn.ehealth.emr.ck.EmrCkTamThan;
import vn.ehealth.emr.ck.EmrCkThanKinh;
import vn.ehealth.emr.ck.EmrCkTiemChung;
import vn.ehealth.emr.ck.EmrCkTienSuBanThanSanKhoa;
import vn.ehealth.emr.ck.EmrCkTienSuGiaDinh;
import vn.ehealth.emr.ck.EmrCkTienSuPhuKhoa;
import vn.ehealth.emr.ck.EmrCkTienSuSanKhoa;
import vn.ehealth.emr.ck.EmrCkTieuHoa;
import vn.ehealth.emr.ck.EmrCkTinhTrangRaVienMat;
import vn.ehealth.emr.ck.EmrCkTinhTrangSanPhu;
import vn.ehealth.emr.ck.EmrCkTinhTrangSoSinh;
import vn.ehealth.emr.ck.EmrCkToanThan;
import vn.ehealth.emr.ck.EmrCkTomTatBenhAnTcm;
import vn.ehealth.emr.ck.EmrCkTuanHoan;

@Entity
@Table(name = "emr_benh_an")
public class EmrBenhAn {

    @Id
    public int idhsba;
    
    @Column(name = "idicd_chandoanphanbiet")
    public Integer idicdChandoanphanbiet;
    
    @Transient
    public EmrDm emrDmMaBenhChandoanphanbiet;
    
    @Column(name = "idicd_chandoankemtheo")
    public Integer idicdChandoankemtheo;
    
    @Transient
    public EmrDm emrDmMaBenhChandoankemtheo;
    
    @Column(name = "idicd_chandoanbenhchinh")
    public Integer idicdChandoanbenhchinh;
    
    @Transient
    public EmrDm emrDmMaBenhChandoanbenhchinh;
    
    @Column public String lydovaovien;
    @Column public Integer vaongaythu;
    @Column public String quatrinhbenhly;
    @Column public String tiensubanthan;
    @Column public String tiemchung;
    @Column public String diung;
    @Column public String matuy;
    @Column public String ruoubia;
    @Column public String thuocla;
    @Column public String thuoclao;
    @Column public String dacdiemkhac;
    @Column public String tiensugiadinh;
    @Column public String quatrinhsinhtruong;
    @Column public String tinhtrangsanphu;
    @Column public String tinhtrangsosinh;
    @Column public String phuongphaphoisinh;
    @Column public String dichte;
    @Column public String chucnangsinhhoat;
    @Column public String toanthan;
    @Column public Integer mach;
    @Column public Double nhietdo;
    @Column public Integer huyetapcao;
    @Column public Integer huyetapthap;
    @Column public Integer nhiptho;
    @Column public Double cannang;
    @Column public Integer chieucao;
    @Column public Integer vongnguc;
    @Column public Integer vongdau;
    @Column public String tuanhoan;
    @Column public String hohap;
    @Column public String tieuhoa;
    @Column public String tietnieusinhduc;
    @Column public String thankinh;
    @Column public String coxuongkhop;
    @Column public String taimuihong;
    @Column public String ranghammat;
    @Column public String noitietdinhduong;
    @Column public String dalieu;
    @Column public String mat;
    @Column public String coquankhac;
    @Column public String xetnghiemcanlamsang;
    @Column public String tomtat;
    @Column public String motachandoanbenhchinh;
    @Column public String motachandoankemtheo;
    @Column public String motachandoanphanbiet;
    @Column public String tienluong;
    @Column public String huongdieutri;
    @Column public Date ngaykybenhan;
    @Column public String bacsylambenhan;
    @Column public Boolean daxoa;
    @Column public Date ngaytao;
    @Column public Integer idnguoitao;
    @Column public Date ngaysua;
    @Column public Integer idnguoisua;
    
    @Column public String nguonlay;
    @Column public String tamthan;
    
    @Column public String tiensuphukhoa;
    @Column public String tiensusankhoa;
    @Column public String khamsankhoa;
    
    @Column public String khamphukhoa;
    @Column public String khamsosinh;
    @Column public String huyethoc;
    
    // add moi 15/04/2015
    @Column public String taychanmieng;    
    
    @Column public String moisinh;
    
    
    @Transient public EmrCkTinhTrangSanPhu emrCkTinhTrangSanPhu;    
    
    @Transient public EmrCkTinhTrangSoSinh emrCkTinhTrangSoSinh;    
    
    @Transient public EmrCkPhuongPhapHoiSinh emrCkPhuongPhapHoiSinh;    
    
    @Transient public EmrCkChucNangSinhHoat emrCkChucNangSinhHoat;    
    
    @Transient public EmrCkTuanHoan emrCkTuanHoan;    
    
    @Transient public EmrCkHoHap emrCkHoHap;      
    
    @Transient public EmrCkThanKinh emrCkThanKinh;
    
    @Transient public EmrCkCoXuongKhop emrCkCoXuongKhop;
    
    @Transient public EmrCkTamThan emrCkTamThan;    
    
    @Transient public EmrCkMat emrCkMat;      
    
    @Transient public EmrCkQuaTrinhSinhTruong emrCkQuaTrinhSinhTruong;    
    
    @Transient public EmrCkTiemChung emrCkTiemChung;    
    
    @Transient public EmrCkTienSuPhuKhoa emrCkTienSuPhuKhoa;    
    
    @Transient public EmrCkTienSuSanKhoa emrCkTienSuSanKhoa;    
    
    @Transient public EmrCkKhamSanKhoa emrCkKhamSanKhoa;    
    
    @Transient public EmrCkTienSuBanThanSanKhoa emrCkTienSuBanThanSanKhoa;    
    
    @Transient public EmrCkKhamPhuKhoa emrCkKhamPhuKhoa;    
    
    @Transient public EmrCkTienSuGiaDinh emrCkTienSuGiaDinh;    
    
    @Transient public EmrCkKhamSoSinh emrCkKhamSoSinh;    
    
    @Transient public EmrCkSkTinhTrangSanPhu emrCkSkTinhTrangSanPhu;
    
    // Add moi 02/04/2015
    
    @Transient public EmrCkMoiSinh emrCkMoiSinh;  
    
    // Add moi 10/04/2015
    
    @Transient public EmrCkToanThan emrCkToanThan;    
    
    @Transient public EmrCkTieuHoa emrCkTieuHoa;    
    
    @Transient public EmrCkHuongDieuTriHuyetHoc emrCkHuongDieuTriHuyetHoc;
    
    // Add moi 15/04/2015
    
    @Transient public EmrCkQuaTrinhBenhLyTcm emrCkQuaTrinhBenhLyTcm;    
    
    @Transient public EmrCkChanTayMieng emrCkChanTayMieng;    
    
    @Transient public EmrCkTomTatBenhAnTcm emrCkTomTatBenhAnTcm;
    
    // Add moi 16/04/2015
    
    @Transient public EmrCkHuongDieuTriTcm emrCkHuongDieuTriTcm;    
    
    @Transient public EmrCkPhuongPhapDieuTriUngBuou emrCkPhuongPhapDieuTriUngBuou;    
    
    @Transient public EmrCkTinhTrangRaVienMat emrCkTinhTrangRaVienMat;
    
    //Add moi 16/03/2016
    //List<EmrQuanLyFileDinhKemBenhAn> emrQuanLyFileDinhKemBenhAn = new ArrayList<EmrQuanLyFileDinhKemBenhAn>();
    
    // Add new by SonVu 05/09/2016
    @Column(name = "tylebaohiem")
    public String tyLeBaoHiem;
    
    @Column(name = "dungtuyen")
    public Boolean dungTuyen;
    
    @Column public Integer tyletonthuongloai1;
    
    @Column public Integer tyletonthuongloai2;
    
    @Column public Integer tyletonthuongloai3;
    
    @Column public Integer tyletonthuongloai4;
    
    @Column public Integer tyletonthuongloai5;
    
    @Column public String sinhduc;
    
    @Column public String nhommau;
    
    @Transient public String emrCkJson;
}
