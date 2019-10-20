package vn.ehealth.emr;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrBenhAn {

    public EmrDmContent emrDmMaBenhChandoanphanbiet;
    
    public EmrDmContent emrDmMaBenhChandoankemtheo;
    
    public EmrDmContent emrDmMaBenhChandoanbenhchinh;
    
    public String lydovaovien;
    public Integer vaongaythu;              //??
    public String quatrinhbenhly;
    public String tiensubanthan;
    public String tiemchung;
    public String diung;
    public String matuy;
    public String ruoubia;
    public String thuocla;
    public String thuoclao;
    public String dacdiemkhac;
    public String tiensugiadinh;
    public String quatrinhsinhtruong;
    public String tinhtrangsanphu;
    public String tinhtrangsosinh;
    public String phuongphaphoisinh;
    public String dichte;
    public String chucnangsinhhoat;
    public String toanthan;
    public Integer mach;
    public Double nhietdo;
    public Integer huyetapcao;
    public Integer huyetapthap;
    public Integer nhiptho;
    public Double cannang;
    public Integer chieucao;
    public Integer vongnguc;
    public Integer vongdau;
    public String tuanhoan;
    public String hohap;
    public String tieuhoa;
    public String tietnieusinhduc;
    public String thankinh;
    public String coxuongkhop;
    public String taimuihong;
    public String ranghammat;
    public String noitietdinhduong;
    public String dalieu;
    public String mat;
    public String coquankhac;
    public String xetnghiemcanlamsang;
    public String tomtat;
    public String motachandoanbenhchinh;
    public String motachandoankemtheo;
    public String motachandoanphanbiet;    
    public String tamthan;
    public String tiensuphukhoa;
    public String tiensusankhoa;
    public String khamsankhoa;    
    public String khamphukhoa;
    public String khamsosinh;
    public String huyethoc;    
    public String taychanmieng;    
    public String sinhduc;    
    public String nhommau;    
    public String tienluong;
    public String huongdieutri;
    public Date ngaykybenhan;
    public String bacsylambenhan;
    
    //public String moisinh;
    
    public String nguonlay;     
    
    public Map<String, Object> emrCkTinhTrangSanPhu;    
    
    public Map<String, Object> emrCkTinhTrangSoSinh;    
    
    public Map<String, Object> emrCkPhuongPhapHoiSinh;    
    
    public Map<String, Object> emrCkChucNangSinhHoat;    
    
    public Map<String, Object> emrCkTuanHoan;    
    
    public Map<String, Object> emrCkHoHap;      
    
    public Map<String, Object> emrCkThanKinh;
    
    public Map<String, Object> emrCkCoXuongKhop;
    
    public Map<String, Object> emrCkTamThan;    
    
    public Map<String, Object> emrCkMat;      
    
    public Map<String, Object> emrCkQuaTrinhSinhTruong;    
    
    public Map<String, Object> emrCkTiemChung;    
    
    public Map<String, Object> emrCkTienSuPhuKhoa;    
    
    public Map<String, Object> emrCkTienSuSanKhoa;    
    
    public Map<String, Object> emrCkKhamSanKhoa;    
    
    public Map<String, Object> emrCkTienSuBanThanSanKhoa;    
    
    public Map<String, Object> emrCkKhamPhuKhoa;    
    
    public Map<String, Object> emrCkTienSuGiaDinh;    
    
    public Map<String, Object> emrCkKhamSoSinh;    
    
    public Map<String, Object> emrCkSkTinhTrangSanPhu;
    
    // Add moi 02/04/2015
    
    public Map<String, Object> emrCkMoiSinh;  
    
    // Add moi 10/04/2015
    
    public Map<String, Object> emrCkToanThan;    
    
    public Map<String, Object> emrCkTieuHoa;    
    
    public Map<String, Object> emrCkHuongDieuTriHuyetHoc;
    
    // Add moi 15/04/2015
    
    public Map<String, Object> emrCkQuaTrinhBenhLyTcm;    
    
    public Map<String, Object> emrCkChanTayMieng;    
    
    public Map<String, Object> emrCkTomTatBenhAnTcm;
    
    // Add moi 16/04/2015
    
    public Map<String, Object> emrCkHuongDieuTriTcm;    
    
    public Map<String, Object> emrCkPhuongPhapDieuTriUngBuou;    
    
    public Map<String, Object> emrCkTinhTrangRaVienMat;
    
    public String tyLeBaoHiem; //??
    
    public Boolean dungTuyen;  //??
    
    public Integer tyletonthuongloai1;  //??
    
    public Integer tyletonthuongloai2;  //??
    
    public Integer tyletonthuongloai3;  //??
    
    public Integer tyletonthuongloai4;  //??
    
    public Integer tyletonthuongloai5;  //??
        
}
