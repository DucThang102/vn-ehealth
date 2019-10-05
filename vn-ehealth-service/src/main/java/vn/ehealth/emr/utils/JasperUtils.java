package vn.ehealth.emr.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import vn.ehealth.emr.EmrBenhAn;
import vn.ehealth.emr.EmrChanDoan;
import vn.ehealth.emr.EmrDanhSachHoSoBenhAn;
import vn.ehealth.emr.EmrDm;
import vn.ehealth.emr.EmrHoiDongHoiChan;
import vn.ehealth.emr.EmrHoiDongPttt;
import vn.ehealth.emr.EmrPhauThuatThuThuat;
import vn.ehealth.emr.EmrQuanLyNguoiBenh;
import vn.ehealth.emr.EmrTinhTrangRaVien;
import vn.ehealth.emr.EmrTuoi;
import vn.ehealth.emr.EmrVaoKhoa;
import vn.ehealth.emr.EmrYhctBenhanVaanChan;
import vn.ehealth.emr.ck.EmrCkTinhTrangSanPhu;
import vn.ehealth.emr.utils.Constants.LoaiThoiDiemTuVong;
import vn.ehealth.emr.utils.Constants.VaiTroHoiChan;
import vn.ehealth.emr.utils.Constants.VaiTroPTTT;

@Component
public class JasperUtils extends JRDefaultScriptlet  {
    
    final String STR_TUOI = " tuổi";
    final String STR_THANG = " tháng";
    final String STR_NGAY = " ngày";
    final String EMR_REPORT_DATE_FORMAT = "yyyyMMddHHmmss";
    
    final String reportDateFormat_DDMMYYYY = "'Ngày' dd 'tháng' MM 'năm' yyyy";
    SimpleDateFormat reportDf_DDMMYYYY = new SimpleDateFormat(reportDateFormat_DDMMYYYY);
    final String STRING_NULL_VALUE_DateFormat_DDMMYYYY = "ngày ... tháng ... năm ...";
    
    final String reportDateFormat_HHmmDDMMYYYY = "HH 'giờ' mm 'phút, ngày' dd/MM/yyyy";
    final String STRING_NULL_VALUE_DateFormat_HHmmDDMMYYYY = ".. giờ ..phút, ngày ../../....";
    SimpleDateFormat reportDf_HHmmDDMMYYYY = new SimpleDateFormat(reportDateFormat_HHmmDDMMYYYY);
    
    final String reportDateFormat_PTTT = "HH 'giờ,'dd/MM/yyyy";
    SimpleDateFormat reportDf_PTTT = new SimpleDateFormat(reportDateFormat_PTTT);
            
    final String reportDateFormat_VK= "HH':'mm dd/MM/yyyy";
    SimpleDateFormat reportDf_VK = new SimpleDateFormat(reportDateFormat_VK);
    
    final String reportDateFormatShort = "dd/MM/yyyy";
    SimpleDateFormat reportDfShort = new SimpleDateFormat(reportDateFormatShort);    
    
    final String reportDateFormat_HHmmDDMMYYYY_Full = "HH 'giờ' mm 'ngày' dd 'tháng' MM 'năm' yyyy";
    final String STRING_NULL_VALUE_DateFormat_HHmmDDMMYYYY_Full = "... giờ ... ngày ... tháng ... năm ....";
    SimpleDateFormat reportDf_HHmmDDMMYYYY_Full = new SimpleDateFormat(reportDateFormat_HHmmDDMMYYYY_Full);
    
    final String reportDateFormat_HHmmDDMMYYYY_Full2 = "HH 'Giờ' mm 'Phút' ... 'Ngày' dd 'Tháng' MM 'Năm' yyyy";
    final String STRING_NULL_VALUE_DateFormat_HHmmDDMMYYYY_Full2 = ".....Giờ ....Phút......Ngày......Tháng.....Năm....";
    SimpleDateFormat reportDf_HHmmDDMMYYYY_Full2 = new SimpleDateFormat(reportDateFormat_HHmmDDMMYYYY_Full2);
    
    final String reportDateFormat_DDMMYYYY_1 = "'ngày' dd 'tháng' MM 'năm' yyyy";
    SimpleDateFormat reportDf_DDMMYYYY_1 = new SimpleDateFormat(reportDateFormat_DDMMYYYY_1);
    
    final String reportDateFormat_HHmmDDMMYYYY_1 = "HH 'giờ' mm 'ph ngày' dd/MM/yyyy";
    final String reportDateFormat_HHmmDDMMYYYY_2 = "HH 'giờ' mm 'phút' dd/MM/yyyy";
    final String STRING_NULL_VALUE_DateFormat_HHmmDDMMYYYY_1 = ".. giờ ...phút.../../....";
    SimpleDateFormat reportDf_HHmmDDMMYYYY_1 = new SimpleDateFormat(reportDateFormat_HHmmDDMMYYYY_1);
    SimpleDateFormat reportDf_HHmmDDMMYYYY_2 = new SimpleDateFormat(reportDateFormat_HHmmDDMMYYYY_2);
    
    public String getThongTinChuyeNKhoa(String inputCk) {
        if (inputCk == null) return "";
        else return inputCk.replaceAll("[\r\n]+", "; ");
    }
    
    public String getTime_VK(Date inputDate) {
        if (inputDate == null) return "";
        
        else return reportDf_VK.format(inputDate);
    }
    
    public String getTextChanDoanYhhd(String thongTinMoTa, EmrDm maBenh) {
        if (StringUtils.isEmpty(thongTinMoTa))
            return maBenh == null ? "":maBenh.ten;
        else return thongTinMoTa;
    }
    
    public String getTime_DDMMYYYY(Date inputDate) {
        if (inputDate == null) return STRING_NULL_VALUE_DateFormat_DDMMYYYY;
        
        else return reportDf_DDMMYYYY.format(inputDate);
    }
    
    public String getTime_HHmmDDMMYYYY(Date inputDate) {
        if (inputDate == null) return STRING_NULL_VALUE_DateFormat_HHmmDDMMYYYY;
        
        else return reportDf_HHmmDDMMYYYY.format(inputDate);
    }
    
    
    public String getTuoi(EmrDanhSachHoSoBenhAn danhSachHSBA) {
        if(danhSachHSBA == null 
                || danhSachHSBA.emrBenhNhan == null 
                || danhSachHSBA.emrBenhNhan.ngaysinh == null
                || danhSachHSBA.emrQuanLyNguoiBenh == null 
                || danhSachHSBA.emrQuanLyNguoiBenh.ngaygioravien == null) {
            return "";
        }
        
        var d1 = new java.sql.Date(danhSachHSBA.emrBenhNhan.ngaysinh.getTime()).toLocalDate();
        var d2 = new java.sql.Date(danhSachHSBA.emrQuanLyNguoiBenh.ngaygioravien.getTime()).toLocalDate();
        
        var m1 = YearMonth.from(d1);
        var m2 = YearMonth.from(d2);
        
        var y1 = Year.from(d1);
        var y2 = Year.from(d2);
        
        int days = (int) d1.until(d2, ChronoUnit.DAYS);
        int months = (int) m1.until(m2, ChronoUnit.MONTHS);
        int years = (int) y1.until(y2, ChronoUnit.YEARS);
        
        if(d1.getDayOfMonth() > d2.getDayOfMonth()) 
            months -= 1;
        
        if(d1.getMonthValue() > d2.getMonthValue() || (d1.getMonthValue() == d2.getMonthValue() && d1.getDayOfMonth() > d2.getDayOfMonth())) 
            years -= 1;
        
        if(months < 1) {
            return days + STR_NGAY;
        }else if(months < 36) {
            return months + STR_THANG;
        }else {            
            return years + STR_TUOI;
        }
    }
    
    public String getTenKhoaDieuTri(EmrVaoKhoa[] vaoKhoas, int index){
        if(vaoKhoas != null && index < vaoKhoas.length){
            EmrVaoKhoa object = vaoKhoas[index];
            if(object != null){
                if (!StringUtils.isEmpty(object.tenkhoa)){
                    return object.tenkhoa;
                }else{
                    EmrDm dmKhoa = object.emrDmKhoaDieuTri;
                    if(dmKhoa != null){
                        return dmKhoa.ten;
                    }
                }
            }
        }
        return "";
    }
    
    int tinhSoNgayDieuTri(Date startDate, Date endDate) {
        var d1 = startDate.toInstant();
        var d2 = endDate.toInstant();
        return 1 + (int) d1.until(d2, ChronoUnit.DAYS);
    }
    
    public String getSoNgayDieuTri(EmrVaoKhoa emrVaoKhoa, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh) {      
        
        if (emrVaoKhoa == null || emrVaoKhoa.ngayketthucdieutri == null || emrVaoKhoa.ngaygiovaokhoa == null)
            return "";
        
        return String.valueOf(tinhSoNgayDieuTri(emrVaoKhoa.ngaygiovaokhoa, emrVaoKhoa.ngayketthucdieutri));
    }
    
    public String getSoNgayDieuTri(int vaoKhoaThu,EmrVaoKhoa emrVaoKhoa, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh){
        
        return getSoNgayDieuTri(emrVaoKhoa, emrQuanLyNguoiBenh, vaoKhoaThu);
        
    }
    
    public String getSoNgayDieuTri(EmrVaoKhoa emrVaoKhoa, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh, int index) {       

        String ngayDT = getSoNgayDieuTri(emrVaoKhoa, emrQuanLyNguoiBenh);
            
        if(ngayDT != null){
            int length = ngayDT.length();               
            if(index <= 2 && index >=0){
                if(index == 0 || index == 1){
                    if(length == 1){
                        return "0";
                    }else{
                        return ngayDT.charAt(0) + "";
                    }
                }else{
                    if(length == 1){
                        return ngayDT;
                    }else{
                        return ngayDT.charAt(1) + "";
                    }
                }
            }
        }
                
        return "";
    }
    
    public String getTongSoNgayDieuTri(EmrQuanLyNguoiBenh emrQuanLyNguoiBenh) {        
        
        if (emrQuanLyNguoiBenh == null 
                || emrQuanLyNguoiBenh.ngaygiovaovien == null 
                || emrQuanLyNguoiBenh.ngaygioravien == null) 
            return "";
        
        return String.valueOf(tinhSoNgayDieuTri(emrQuanLyNguoiBenh.ngaygioravien, emrQuanLyNguoiBenh.ngaygioravien));
    }
    
    public int getLoaiThoiGianTuVong(EmrDanhSachHoSoBenhAn danhSachHSBA) {
        
        EmrQuanLyNguoiBenh qlnb = danhSachHSBA.emrQuanLyNguoiBenh;
        EmrTinhTrangRaVien ttRaVien = danhSachHSBA.emrTinhTrangRaVien;
        
        if (qlnb == null || qlnb.ngaygiovaovien == null) 
            return LoaiThoiDiemTuVong.KHONG_XAC_DINH;
        
        if (ttRaVien == null || ttRaVien.ngaygiotuvong == null) 
            return LoaiThoiDiemTuVong.KHONG;
        
        var d1 = qlnb.ngaygiovaovien.toInstant();
        var d2 = ttRaVien.ngaygiotuvong.toInstant();                
        
        long elapsedHours = d1.until(d2, ChronoUnit.HOURS);
        
        if (elapsedHours <= 24) return LoaiThoiDiemTuVong._24H;
        else if (elapsedHours <= 48) return LoaiThoiDiemTuVong._48H;
        else if (elapsedHours <= 72) return LoaiThoiDiemTuVong._72H;
        else return LoaiThoiDiemTuVong.OTHER;        
    }
    
    public String getBacSiDieuTri(EmrDanhSachHoSoBenhAn emrDanhSachHoSoBenhAn) {
        
        if (emrDanhSachHoSoBenhAn == null) return "";
        var vaoKhoas = emrDanhSachHoSoBenhAn.getEmrVaoKhoas();
        
        // Tim ra doi tuong khoa ra vien
        EmrVaoKhoa emrKhoaRaVien = null;
        Date ngayKetThucDieuTri = new Date(0);
        for (EmrVaoKhoa emrVaoKhoa : vaoKhoas) {
            
            if (emrVaoKhoa.getNgayketthucdieutri() == null || (emrVaoKhoa.getDaxoa() != null && emrVaoKhoa.getDaxoa() == true)) continue;
            
            if (emrVaoKhoa.getNgayketthucdieutri().after(ngayKetThucDieuTri)) {
                ngayKetThucDieuTri = emrVaoKhoa.getNgayketthucdieutri();
                emrKhoaRaVien = emrVaoKhoa;
            }
        }
        
        if(!emrDanhSachHoSoBenhAn.getEmrQuanLyNguoiBenh().getTenbacsichoravien().equals("")){
            return emrDanhSachHoSoBenhAn.getEmrQuanLyNguoiBenh().getTenbacsichoravien();
        }else{
            if (emrKhoaRaVien == null) return "";
            else return emrKhoaRaVien.getBacsidieutri();
        }
    }
    
    EmrTuoi getEmrTuoi(EmrDanhSachHoSoBenhAn danhSachHSBA) {
        var result = new EmrTuoi();
        result.flagTuoi = false;
        
        if(danhSachHSBA == null 
                || danhSachHSBA.emrBenhNhan == null 
                || danhSachHSBA.emrBenhNhan.ngaysinh == null
                || danhSachHSBA.emrQuanLyNguoiBenh == null 
                || danhSachHSBA.emrQuanLyNguoiBenh.ngaygioravien == null) {
            return result;
        }
        
        var d1 = new java.sql.Date(danhSachHSBA.emrBenhNhan.ngaysinh.getTime()).toLocalDate();
        var d2 = new java.sql.Date(danhSachHSBA.emrQuanLyNguoiBenh.ngaygioravien.getTime()).toLocalDate();
        
        var m1 = YearMonth.from(d1);
        var m2 = YearMonth.from(d2);
        
        var y1 = Year.from(d1);
        var y2 = Year.from(d2);
        
        int years = (int) y1.until(y2, ChronoUnit.YEARS);
        int months = (int) m1.until(m2, ChronoUnit.MONTHS);
        int days = (int) d1.until(d2, ChronoUnit.DAYS);
        
        if(d1.getDayOfMonth() > d2.getDayOfMonth()) 
            months -= 1;
        
        if(d1.getMonthValue() > d2.getMonthValue() || (d1.getMonthValue() == d2.getMonthValue() && d1.getDayOfMonth() > d2.getDayOfMonth())) 
            years -= 1;
        
        result.flagTuoi = true;
        
        if(months < 1) {
            result.tuoi = String.valueOf(days);
            result.loaiTuoi = 3;
        }else if(months < 36) {
            result.tuoi =  String.valueOf(months);
            result.loaiTuoi = 2;
        }else {            
            result.tuoi = String.valueOf(years);
            result.loaiTuoi = 1;
        }
        return result;
    }
    
    public String getTuoi_yhct(EmrDanhSachHoSoBenhAn danhSachHSBA, int index) { 
        String tuoi = "";
        if(danhSachHSBA != null){
            EmrTuoi object = getEmrTuoi(danhSachHSBA);
            if (object != null && object.isFlagTuoi()){
                String vTuoi = object.getTuoi();
                int length = vTuoi.length();                
                if(index <= 2){
                    if(index == 0 || index == 1){
                        if(length == 1){
                            tuoi = "0";
                        }else{
                            tuoi = vTuoi.charAt(0) + "";
                        }
                    }else{
                        if(length == 1){
                            tuoi = vTuoi;
                        }else{
                            tuoi = vTuoi.charAt(1) + "";
                        }
                    }
                }
            }
        }
         
        return tuoi;
        
    }
    
    Integer convertStringToInteger(String s) {
        try {
            return Integer.parseInt(s);
        }catch(NumberFormatException e) {
            return null;
        }
    }
    
    
    public String getDeLanMay(EmrCkTinhTrangSanPhu ttsp){
        String str = "";
        if(ttsp != null){
            int vDenon      = 0;
            int vDuThang    = 0;
            Integer denon   = convertStringToInteger(ttsp.getParaDenon());
            if(denon != null){
                vDenon = denon.intValue();
            }
            Integer duthang = convertStringToInteger(ttsp.getParaDuthang());
            if(duthang != null){
                vDuThang = duthang.intValue();
            }
            int tong = vDenon + vDuThang;
            str = String.valueOf(tong);
        }
        
        return str;
    }
    
    public String getIndexDateTime(Date sDate, int index){
        String str = "";
        if(sDate != null){
            String strDate = new SimpleDateFormat(EMR_REPORT_DATE_FORMAT).format(sDate);
            int length = strDate.length();
            if(index <= length){
                str = "" + strDate.charAt(index);
            }
        }
        return str;
    }
    
    public String getNhanTuoi_YHCT(EmrDanhSachHoSoBenhAn danhSachHSBA) {    
        String tuoi = "Tuổi";
        if(danhSachHSBA != null){
            EmrTuoi object = getEmrTuoi(danhSachHSBA);
            if (object != null && object.isFlagTuoi()){
                int loai = object.getLoaiTuoi();
                if(loai == 1){
                    tuoi = "Tuổi";
                }else if(loai == 2){
                    tuoi = "Tháng tuổi";
                }else if(loai == 3){
                    tuoi = "Ngày tuổi";
                }
                
            }
        }
         
        return tuoi;
        
    }
    
    public String encodeUrl(String rawUrl) {
        
        try {
            return URLEncoder.encode(rawUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            
            e.printStackTrace();
            return "";
        }
    }
    
    public String getVaiTroPTTT(int ivaitro){
        if(ivaitro == VaiTroPTTT.PHAU_THUAT_VIEN_CHINH){
            return ExportUtil.getMessage("vaitro.pttt.ptv.chinh");
        }else if(ivaitro == VaiTroPTTT.PHAU_THUAT_VIEN_PHU){
            return ExportUtil.getMessage("vaitro.pttt.ptv.phu");
        }else if(ivaitro == VaiTroPTTT.THU_THUAT_VIEN_CHINH){
            return ExportUtil.getMessage("vaitro.pttt.ttv.chinh");
        }else if(ivaitro == VaiTroPTTT.THU_THUAT_VIEN_PHU){
            return ExportUtil.getMessage("vaitro.pttt.ttv.phu");
        }else if(ivaitro == VaiTroPTTT.GAY_ME_CHINH){
            return ExportUtil.getMessage("vaitro.pttt.gm.chinh");
        }else if(ivaitro == VaiTroPTTT.GAY_ME_PHU){
            return ExportUtil.getMessage("vaitro.pttt.gm.phu");
        }else if(ivaitro == VaiTroPTTT.THANH_VIEN){
            return ExportUtil.getMessage("vaitro.pttt.tv");
        }
        return "";
    }
    
    public String getBacSyThuKy(List<EmrHoiDongHoiChan> emrHoiDongHoiChanList) {
        return emrHoiDongHoiChanList
                     .stream()
                     .filter(x -> x.idvaitro == VaiTroHoiChan.THU_KY)
                     .findAny()
                     .map(x -> x.bacsihoichan)
                     .orElse("");
    }
    
    public String getBacSyChuToa(List<EmrHoiDongHoiChan> emrHoiDongHoiChanList) {
        return emrHoiDongHoiChanList
                     .stream()
                     .filter(x -> x.idvaitro == VaiTroHoiChan.BAC_SY_CHU_TOA)
                     .findAny()
                     .map(x -> x.bacsihoichan)
                     .orElse("");
    }
    
    public String getBacSyGayMeChinh(List<EmrHoiDongPttt> emrHoiDongPttts) {
        return emrHoiDongPttts
                .stream()
                .filter(x -> x.idvaitro == VaiTroPTTT.GAY_ME_CHINH)
                .findAny()
                .map(x -> x.tenbacsi)
                .orElse("");
    }
    
    public String getBacSyPhauThuat(List<EmrHoiDongPttt> emrHoiDongPttts) { 
        return emrHoiDongPttts
                .stream()
                .filter(x -> x.idvaitro == VaiTroPTTT.PHAU_THUAT_VIEN_CHINH || x.idvaitro == VaiTroPTTT.THU_THUAT_VIEN_CHINH)
                .findAny()
                .map(x -> x.tenbacsi)
                .orElse("");
    }
    
    final String[] ARR_MA_LOAI_BA_YHCT = {"27","28","33","34"};
    
    public boolean checkLoaiBAYHCT(String ma){
        boolean rs = false;
        for(int i = 0; i < ARR_MA_LOAI_BA_YHCT.length; i++){
            if(ARR_MA_LOAI_BA_YHCT[i].equalsIgnoreCase(ma)){
                rs = true;
                break;
            }
        }
        return rs;
    }
    
    String getText_ICD10(String maICD, int index) {
        String kiTu = "";
        
        if(maICD != null){
            if(index == 1){
                kiTu = maICD.charAt(0) + "";
            }else if(index == 2){
                kiTu = maICD.charAt(1) + "";
            }else if(index == 3){
                kiTu = maICD.charAt(2) + "";
            }else if(index == 4){
                int length = maICD.length();
                switch (length) {
                    case 3:
                        break;
                    case 4:
                        kiTu = maICD.charAt(3) + "";
                        break;
                    case 5:
                        kiTu = maICD.charAt(4) + "";
                        break;
                    case 6:
                        kiTu = maICD.substring(3) + "";
                        break;
                    default:
                        break;
                }
            }
        }
        
        return kiTu;        
    }
    
    public String getTextICD10(EmrChanDoan emrChanDoan, int index){
        if(emrChanDoan != null && emrChanDoan.emrDmMaBenhChandoannoiden != null) {
            return getText_ICD10(emrChanDoan.emrDmMaBenhChandoannoiden.ma, index);
        }
        
        return "";
    }
    
    public String getTextICD10FromKKB(EmrChanDoan emrChanDoan, int index){
        if(emrChanDoan != null  && emrChanDoan.emrDmMaBenhChandoankkb != null) {
            return getText_ICD10(emrChanDoan.emrDmMaBenhChandoankkb.ma, index);
        }
        
        return "";
    }
    
    
    public String getTextICD10BenhChinhFromBenhAN(EmrBenhAn emrBenhAn, int index){
        if(emrBenhAn != null && emrBenhAn.emrDmMaBenhChandoanbenhchinh != null) {
            return getText_ICD10(emrBenhAn.emrDmMaBenhChandoanbenhchinh.ma, index);
        }
        
        return "";
    }
    
    public String getTextICD10RaVienFromChanDoan(EmrChanDoan emrChanDoan, int index){
        if(emrChanDoan != null && emrChanDoan.emrDmMaBenhChandoanravienchinh!= null){
            return getText_ICD10(emrChanDoan.emrDmMaBenhChandoanravienchinh.ma, index);
            
        }
        
        return "";
    }
    
    
    public String getTextICD10TuVongFromTTRaVien(EmrTinhTrangRaVien emrTinhTrangRaVien, int index){
        if(emrTinhTrangRaVien != null && emrTinhTrangRaVien.emrDmNguyennhantuvong != null) {
            return getText_ICD10(emrTinhTrangRaVien.emrDmNguyennhantuvong.ma, index);
        }
        
        return "";        
    }
    
    public String getTextICD10GiaiPhauFromTTRaVien(EmrTinhTrangRaVien emrTinhTrangRaVien, int index){
        if(emrTinhTrangRaVien != null && emrTinhTrangRaVien.emrDmGiaiphaututhi != null) {
            return getText_ICD10(emrTinhTrangRaVien.emrDmGiaiphaututhi.ma, index);
        }
        
        return "";        
    }
    
    public String getTextICD10TruocPhauThuatFromChanDoan(EmrChanDoan emrChanDoan, int index){
        if(emrChanDoan != null && emrChanDoan.emrDmMaBenhChandoantruocpt != null) {
            return getText_ICD10(emrChanDoan.emrDmMaBenhChandoantruocpt.ma, index);
        }
        
        return "";        
    }    
   
    public String getTextICD10SauPhauThuatFromChanDoan(EmrChanDoan emrChanDoan, int index){
        if(emrChanDoan != null && emrChanDoan.emrDmMaBenhChandoansaupt != null) {
            return getText_ICD10(emrChanDoan.emrDmMaBenhChandoansaupt.ma, index);
        }
        
        return "";
    }
    
    public String getTextICD10NNRaVienFromChanDoan(EmrChanDoan emrChanDoan, int index){
        if(emrChanDoan != null && emrChanDoan.emrDmMaBenhChandoanraviennguyennhan != null) {
            return getText_ICD10(emrChanDoan.emrDmMaBenhChandoanraviennguyennhan.ma, index);
        }
        
        return "";
    }
    
    public String getTextICD10KemTheoFromChanDoan(EmrChanDoan emrChanDoan, int index){
        if(emrChanDoan != null && emrChanDoan.emrDmMaBenhChandoanravienkemtheo != null) {
            return getText_ICD10(emrChanDoan.emrDmMaBenhChandoanravienkemtheo.ma, index);
            
        }

        return "";
    }  
    
    
    public String getTime_PTTT(Date inputDate) {
        if (inputDate == null) return "";
        
        else return reportDf_PTTT.format(inputDate);
    }
    
    public String htNgaySauPT(int songay, int index){
        if(songay >= 0){
            String strSoNgay = "" + songay;
            int strlengt = strSoNgay.length();
            switch (index) {
            case 1:if(strlengt >= 4){
                return "" + strSoNgay.charAt(strlengt-4);
            }else{
                return "0";
            }
            case 2:if(strlengt >= 3){
                return "" + strSoNgay.charAt(strlengt-3);
            }else{
                return "0";
            }
            case 3:if(strlengt >= 2){
                return "" + strSoNgay.charAt(strlengt-2);
            }else{
                return "0";
            }
            case 4:if(strlengt >= 1){
                return "" + strSoNgay.charAt(strlengt-1);
            }else{
                return "0";
            }
            default:
                return "0";
            }
        }else {
            return "0";
        }
    }
    
    public String htSoLanPhauThuat(int solanphauthuat,int index){
        if(solanphauthuat > 0){
            String strSoLanPhauThuat = "" + solanphauthuat;
            int strlengt = strSoLanPhauThuat.length();
            switch (index) {
            case 1:if(strlengt >= 2){
                return "" + strSoLanPhauThuat.charAt(strlengt-2);
            }else{
                return "0";
            }
            case 2:if(strlengt >= 1){
                return "" + strSoLanPhauThuat.charAt(strlengt-1);
            }else{
                return "0";
            }
            default:
                return "0";
            }
        }else{
            return "0";
        }
    }
    
    public String getChuandoanSauPTTT(EmrPhauThuatThuThuat[] emrPTTTs){
        if(emrPTTTs != null && emrPTTTs.length > 0){
            var emrPTTT = emrPTTTs[emrPTTTs.length - 1];
            return getTextChanDoanYhhd(emrPTTT.motachandoansaupt, emrPTTT.emrDmMaBenhChandoansau);
        }
        return "";
    }
    
    public String getChuandoanTruocPTTT(EmrPhauThuatThuThuat[] emrPTTTs){
        if(emrPTTTs != null && emrPTTTs.length > 0){
            var emrPTTT = emrPTTTs[0];
            return getTextChanDoanYhhd(emrPTTT.motachandoantruocpt, emrPTTT.emrDmMaBenhChandoantruoc);
        }
        return "";
    }
    
    Date getNgayThangBiBong(EmrDanhSachHoSoBenhAn danhSachHSBA) {
        Date result = null;
        Integer iNgayVaoVienThu = null;
        
        // lay ngay vao vien thu
        iNgayVaoVienThu = danhSachHSBA.getEmrBenhAn().getVaongaythu();
        
        // lay ngay gio vao vien
        Date dNgayVaoVien = danhSachHSBA.getEmrQuanLyNguoiBenh().getNgaygiovaovien();
        
        if ( iNgayVaoVienThu == null ) {
            return result;
        } else{
            result = new Date(dNgayVaoVien.getTime() - iNgayVaoVienThu.intValue() * 24 * 3600 * 1000l );
        }
        
        return result;
        
    }
    
    public String getThoiGianBiBong(EmrDanhSachHoSoBenhAn danhSachHSBA){
        var date = getNgayThangBiBong(danhSachHSBA); 
        if (date == null) {
            return "";
        }else {
            return reportDf_HHmmDDMMYYYY.format(date);
        }
    }
    
    public String getDateStringShort(Date inputDate) {
        if (inputDate == null) return "";
        else {
            return reportDfShort.format(inputDate);
        }
    }
    
    public String getTime_HHmmDDMMYYYY_Full(Date inputDate) {
        if (inputDate == null) return STRING_NULL_VALUE_DateFormat_HHmmDDMMYYYY_Full;
        
        else return reportDf_HHmmDDMMYYYY_Full.format(inputDate);
    }
    
    public String getTime_HHmmDDMMYYYY_Full2(Date inputDate) {
        if (inputDate == null) return STRING_NULL_VALUE_DateFormat_HHmmDDMMYYYY_Full2;
        
        else return reportDf_HHmmDDMMYYYY_Full2.format(inputDate);
    }
    
    public String getTime_DDMMYYYY_1(Date inputDate) {
        if (inputDate == null) return STRING_NULL_VALUE_DateFormat_DDMMYYYY;
        
        else return reportDf_DDMMYYYY_1.format(inputDate);
    }
    
    public String getTime_HHmmDDMMYYYY_2(Date inputDate) {
        if (inputDate == null) return STRING_NULL_VALUE_DateFormat_HHmmDDMMYYYY_1;
        
        else return reportDf_HHmmDDMMYYYY_2.format(inputDate);
    }
    
    public String getTime_HHmmDDMMYYYY_1(Date inputDate) {
        if (inputDate == null) return STRING_NULL_VALUE_DateFormat_HHmmDDMMYYYY_1;
        
        else return reportDf_HHmmDDMMYYYY_1.format(inputDate);
    }
    
    public String getStringMaxLength(String str, int maxlength){
        if(str != null && !str.equals("")){
            String[] arr = str.split(" ");
            str = "";
            for(int i = 0; i < arr.length; i++){
                if(str.length() + arr[i].length() >= maxlength){
                    str += "...";
                    break;
                } else {
                    str += arr[i] + " ";
                }
            }
        }
        return str;
    }
    
    public String [] yhctListArray (String input){
        String [] dataArr = null;
        if ( input != null){
            dataArr =  input.split(",");
            for(int i = 0; i<dataArr.length;i++){
                if(dataArr[i] != null && !dataArr[i].trim().equals("")){
                    dataArr[i] = String.valueOf(Integer.parseInt(dataArr[i]));
                }
            }
        }
        return dataArr;
    }
    
    public String getMaChanDoanYHCT(EmrChanDoan chanDoan) {
        StringBuilder result = new StringBuilder();
        
        if (chanDoan.emrDmMaBenhChandoanravienchinh != null) {
            result.append(chanDoan.emrDmMaBenhChandoanravienchinh.ma);
        }
        
        if (chanDoan.emrDmMaBenhChandoanravienkemtheo != null) {
            if (result.length() > 0) result.append(", ");
            result.append(chanDoan.emrDmMaBenhChandoanravienkemtheo.ma);
        }
        
        if (chanDoan.emrDmMaBenhChandoanraviennguyennhan != null) {
            if (result.length() > 0) result.append(", ");
            result.append(chanDoan.emrDmMaBenhChandoanraviennguyennhan.ma);
        }
        
        return result.toString();
    }
    
    public String[] getDacDiemLienQuanBenhYHCTNoiTru(EmrBenhAn emrBenhAn) {
        
        StringBuilder sb = new StringBuilder();
        
        int count = 0;
        if ("on".equals(emrBenhAn.getThuocla())||"1".equals(emrBenhAn.getThuocla())||"TRUE".equals(emrBenhAn.getThuocla().toLowerCase())) {
            sb.append("1,");
            count++;
        }
        
        if ("on".equals(emrBenhAn.getRuoubia())||"1".equals(emrBenhAn.getRuoubia())||"TRUE".equals(emrBenhAn.getRuoubia().toLowerCase())) {
            sb.append("2,");
            count++;
        }
        
        if ("on".equals(emrBenhAn.getMatuy())||"1".equals(emrBenhAn.getMatuy())||"TRUE".equals(emrBenhAn.getMatuy().toLowerCase())) {
            sb.append("3,");
            count++;
        }
        
        if ("on".equals(emrBenhAn.getDiung())||"1".equals(emrBenhAn.getDiung())||"TRUE".equals(emrBenhAn.getDiung().toLowerCase())) {
            sb.append("4,");
            count++;
        }
        
        if ("on".equals(emrBenhAn.getDacdiemkhac())||"1".equals(emrBenhAn.getDacdiemkhac())||"TRUE".equals(emrBenhAn.getDacdiemkhac().toLowerCase())) {
            sb.append("5,");
            count++;
        }
        
        if (count < 5) {
            for (int i = 0; i < 5 - count; i++)
                sb.append(" ,");
        }
        
        String[] result = sb.toString().split(",");     
        
        return result;
    }
    
    public String getTextChanDoanYhct(String thongTinMoTa, EmrDm benhDanh) {
        if (StringUtils.isEmpty(thongTinMoTa))
            return benhDanh == null ? "":benhDanh.getTen();
        else return thongTinMoTa;
    }
    
    public String StringtypeforInt(int i){
        if(i < 0) return "";
        
        return String.valueOf(i);
    }
    
    public String sxChuoiFromHISTangdan(String lstdaumatcoHienthi,int input){
        if(lstdaumatcoHienthi != null){
            String [] dataArr = this.yhctListArray(lstdaumatcoHienthi);
            if(dataArr.length == 1){
                return input == 0? dataArr[0] : "";                    
            }else if(dataArr.length >= 2){
                int [] daytangdan = new int[7];
                
                for(int i = 0; i<dataArr.length ;i++){
                    daytangdan[i] = -1;
                    if(!dataArr[i].equals("")){
                        try {
                            daytangdan[i] = Integer.parseInt(dataArr[i]);
                        } catch (Exception e) {
                            
                        }
                    }
                }
                
                for(int i = 0; i<dataArr.length ;i++){
                    int trunggian;
                    for(int j = i + 1; j < dataArr.length; j++ ){
                        if(daytangdan[i] > daytangdan[j] ){
                            trunggian = daytangdan[i];
                            daytangdan[i] = daytangdan[j];
                            daytangdan[j] = trunggian;
                        }
                    }
                }

                for(int i = 0; i<dataArr.length ;i++){
                    int trunggian;
                    for(int j = i + 1; j < dataArr.length; j++ ){
                        if(daytangdan[i] == -1 && daytangdan[j]!= -1){
                            trunggian = daytangdan[i];
                            daytangdan[i] = daytangdan[j];
                            daytangdan[j] = trunggian;
                        }
                    }
                }
                
                switch (input) {
                    case 0: return StringtypeforInt(daytangdan[0]);
                    case 1: return StringtypeforInt(daytangdan[1]);
                    case 2: return dataArr.length <= 2? "" :  StringtypeforInt(daytangdan[2]);
                    case 3: return dataArr.length <= 3? "" :  StringtypeforInt(daytangdan[3]);
                    case 4: return dataArr.length <= 4? "" :  StringtypeforInt(daytangdan[4]);
                    case 5: return dataArr.length <= 5? "" :  StringtypeforInt(daytangdan[5]);
                    default:
                        break;
                }
            }
            
        }
        
        return "";
    }
    
    public String convertBooleanTo1OR2(Boolean flag){
        
        String str = "2";
        if(flag != null && flag == true){
            str = "1";
        }       
        return str;     
    }
    
    public String checkDaiTieuTienCuaVaanChan(EmrYhctBenhanVaanChan object){
        
        if(object != null && (!StringUtils.isEmpty(object.lsttieutienHienthi) || 
                !StringUtils.isEmpty(object.lstdaitienHienthi)))
        {
            return "1";
        }
        
        return "2";
    }
    
    public String checkSinhDucCuaVaanChan(EmrYhctBenhanVaanChan object){
        
        if(object != null && (!StringUtils.isEmpty(object.lstsinhducHienthi) || 
                !StringUtils.isEmpty(object.lstsinhducnuHienthi)))
        {
            return "1";
        }
        
        return "2";
    }
}
