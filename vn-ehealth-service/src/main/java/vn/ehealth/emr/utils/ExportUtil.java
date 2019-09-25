package vn.ehealth.emr.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import vn.ehealth.emr.EmrCoSoKhamBenh;
import vn.ehealth.emr.EmrDanhSachHoSoBenhAn;

public class ExportUtil {
    
    static String getRealPath(String path) {
        return "src/main/resources/" + path;
    }
    
    static boolean hasPrivilege(String permission) {
        return true;
    }
       
    static Map<String, String> messages = new HashMap<>();
    
    static {
        try {
            var file = new ClassPathResource("message.properties").getInputStream();
            var reader = new BufferedReader(new InputStreamReader(file));
            String line;
            while((line = reader.readLine()) != null) {
                String[] arr = line.split("=");
                if(arr.length == 2) {
                    messages.put(arr[0].trim(), arr[1].trim());
                }
            }            
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    static String getMessage(String key) {
        return messages.getOrDefault(key, key);
    }
    
    public static byte[] exportPdf(@NonNull EmrDanhSachHoSoBenhAn danhSachHSBA, @NonNull EmrCoSoKhamBenh coSoKhamBenh, 
                                    @NonNull String loaiReport, 
                                    @NonNull String serverBaseURL) throws JRException, IOException {        
        
        String maCoSoKhamBenh = coSoKhamBenh.ma;
        
        String jrxmlFile;
        InputStream jasperIs;
        
        JasperPrint jasperPrint     = null;
        Map<String, Object> params  = new HashMap<>();
       
        if(danhSachHSBA != null){
            /*EmrLogTruyCapHsba emrLogTruyCapHsba = new EmrLogTruyCapHsba();
            emrLogTruyCapHsba.setEmrDanhSachHoSoBenhAn(danhSachHSBA);
            emrLogTruyCapHsba.setNgaytao(new Date());
            emrLogTruyCapHsba.setNguoiTruyCap(getAuthenticatedUser());
            emrLogTruyCapHsba.setThoidiemtruycap(new Date());*/
            
            if(StringUtils.isEmpty(danhSachHSBA.donvichuquan)) 
                danhSachHSBA.donvichuquan = coSoKhamBenh.donvichuquan;
            
            if(StringUtils.isEmpty(danhSachHSBA.tenbenhvien)) 
                danhSachHSBA.tenbenhvien = coSoKhamBenh.ten;
            
            if(StringUtils.isEmpty(danhSachHSBA.truongphongth)) 
                danhSachHSBA.truongphongth = coSoKhamBenh.truongphongth;
            
            params.put("danhSachHSBA", danhSachHSBA);
            
            String headerPath;
            headerPath = getRealPath("report/Header");
            params.put("headerPath", headerPath);
            
            
            String duongDanFileDinhKem, duongDanFileDinhKemOnline ;
            duongDanFileDinhKem = serverBaseURL + "/file";
            params.put("duongDanFileDinhKem", duongDanFileDinhKem);
            
            duongDanFileDinhKemOnline = serverBaseURL + "/dtt/hoSoBenhAn/xembaocao/xemFile.htm?id=";
            params.put("duongDanFileDinhKemOnline", duongDanFileDinhKemOnline); 
            
            params.put("duongDanHost", serverBaseURL);
            
            params.put("SUBREPORT_DAUHIEUSINHTON_DIR", getRealPath("report/BenhAn/DauHieuSinhTon")); 
            
            params.put("IMG_DIR", getRealPath("report/images"));
            
            List<EmrDanhSachHoSoBenhAn> dsHsba = new ArrayList<>();
            dsHsba.add(danhSachHSBA);
            
            //Hien thi report
            if("giaychuyenvien".equalsIgnoreCase(loaiReport)) {
                //emrLogTruyCapHsba.setNoidung(getMessage("report.noidungtruycap.giaychuyenvien"));
                //params.put("emrDanhSachVetTraoDoi", emrDanhSachVetTraoDoi);
                params.put("emrDanhSachVetTraoDoi", null);
                jrxmlFile = getRealPath("report/BenhAn/GCV/GiayChuyenVien.jasper");
                jasperIs = new FileInputStream(new File(jrxmlFile));
                 jasperPrint = JasperFillManager.fillReport(jasperIs, params, new JRBeanCollectionDataSource(dsHsba));
             } else if("giaiphaubenh".equalsIgnoreCase(loaiReport)) {
                if(hasPrivilege("EMR_REPORT_GIAIPHAUBENH")) {
                    //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.giaiPhauBenh"));
                    
                    params.put("tenThongTin", getMessage("report.tieuDe.tenGiaiPhauBenh"));
                    params.put("SUBREPORT_DIR", getRealPath("report/GiaiPhauBenh"));
                    jrxmlFile = getRealPath("report/GiaiPhauBenh/GiaiPhauBenh.jasper");
                    jasperIs = new FileInputStream(new File(jrxmlFile));        
                     jasperPrint = JasperFillManager.fillReport(jasperIs,
                        params, new JRBeanCollectionDataSource(danhSachHSBA.emrGiaiPhauBenhs));
                }                
             } else if ("hinhanhtonthuong".equalsIgnoreCase(loaiReport)){
                 if(hasPrivilege("EMR_REPORT_HINHANHTONTHUONG")) {

                    //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.hinhAnhTonThuong"));
                    
                    params.put("tenThongTin", getMessage("report.tieuDe.tenHinhAnhTonThuong"));
                    params.put("SUBREPORT_DIR", getRealPath("report/HinhAnhTonThuong"));
                    jrxmlFile = getRealPath("report/HinhAnhTonThuong/HinhAnhTonThuong.jasper");
                    jasperIs = new FileInputStream(new File(jrxmlFile));
                
                     jasperPrint = JasperFillManager.fillReport(jasperIs,
                        params, new JRBeanCollectionDataSource(danhSachHSBA.emrHinhAnhTonThuongs));
                }
             }else if ("phauthuatthuthuat".equalsIgnoreCase(loaiReport)){
                 if(hasPrivilege("EMR_REPORT_PTTT")) {
                    //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.phauThuatThuThuat"));            
                    params.put("tenThongTin", getMessage("report.tieuDe.tenPhauThuatThuThuat"));
                    params.put("SUBREPORT_DIR", getRealPath("report/PhauThuatThuThuat"));
                    jrxmlFile = getRealPath("report/PhauThuatThuThuat/PhauThuatThuThuat.jasper");
                    jasperIs = new FileInputStream(new File(jrxmlFile));
                    
                     jasperPrint = JasperFillManager.fillReport(jasperIs,
                        params, new JRBeanCollectionDataSource(danhSachHSBA.emrPhauThuatThuThuats));
                }
             }else if ("chandoanhinhanh".equalsIgnoreCase(loaiReport)){
                 if(hasPrivilege("EMR_REPORT_CHANDOANHINHANH")) {
                    //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.chanDoanHinhAnh"));
                        
                    params.put("tenThongTin", getMessage("report.tieuDe.tenChanDoanHinhAnh"));
                    Integer idChucNang = 60; // getIdTuSinh(EmrDmTuSinhKieu.CHUC_NANG_HSBA, EmrDmTuSinhMa.CHUC_NANG_HSBA_CHANDOANHINHANH);
                    
                    params.put("idChucNang",idChucNang);
                    params.put("SUBREPORT_DIR", getRealPath("report/ChanDoanHinhAnh"));
                    jrxmlFile = getRealPath("report/ChanDoanHinhAnh/ChanDoanHinhAnh.jasper");
                    jasperIs = new FileInputStream(new File(jrxmlFile));
                
                    jasperPrint = JasperFillManager.fillReport(jasperIs,
                                        params, new JRBeanCollectionDataSource(danhSachHSBA.emrChanDoanHinhAnhs));
                }                
             }else if ("thamdochucnang".equalsIgnoreCase(loaiReport)){
                 if(hasPrivilege("EMR_REPORT_THAMDOCHUCNANG")) {
                    //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.thamDoChucNang"));
                        
                    params.put("tenThongTin", getMessage("report.tieuDe.tenThamDoChucNang"));
                    params.put("SUBREPORT_DIR", getRealPath("report/ThamDoChucNang"));
                    
                    jrxmlFile = getRealPath("report/ThamDoChucNang/ThamDoChucNang.jasper");
                    jasperIs = new FileInputStream(new File(jrxmlFile));
                
                     jasperPrint = JasperFillManager.fillReport(jasperIs,
                        params, new JRBeanCollectionDataSource(danhSachHSBA.emrThamDoChucNangs));
                }
             }else if("xetnghiem".equalsIgnoreCase(loaiReport)) {
                                  
               //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.xetNghiem"));
             
                 params.put("tenThongTin", getMessage("report.tieuDe.tenXetNghiem"));
                 params.put("SUBREPORT_DIR", getRealPath("report/XetNghiem"));
                 jrxmlFile = getRealPath("report/XetNghiem/XetNghiem.jasper");
                 jasperIs = new FileInputStream(new File(jrxmlFile));
                 
                 jasperPrint = JasperFillManager.fillReport(jasperIs,
                     params, new JRBeanCollectionDataSource(danhSachHSBA.emrXetNghiems));
                 
             }else if ("hoichan".equalsIgnoreCase(loaiReport)){
                 if(hasPrivilege("EMR_REPORT_HOICHAN")) {
                     //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.hoiChan"));
                        
                        params.put("tenThongTin", getMessage("report.tieuDe.tenHoiChan"));
                        params.put("SUBREPORT_DIR", getRealPath("report/HoiChan"));
                        if(maCoSoKhamBenh.equals("45")){
                            jrxmlFile = getRealPath("report/HoiChan/YHCT_HoiChan.jasper");
                        }else{
                            jrxmlFile = getRealPath("report/HoiChan/HoiChan.jasper");
                        }
                        jasperIs = new FileInputStream(new File(jrxmlFile));
                        
                        var emrHoiChans = danhSachHSBA.emrVaoKhoas.stream()
                                                .flatMap(x -> x.emrHoiChans.stream())
                                                .collect(Collectors.toList());
                                                
                    
                        jasperPrint = JasperFillManager.fillReport(jasperIs,
                            params, new JRBeanCollectionDataSource(emrHoiChans));
                }                
             }else if ("dieutri".equalsIgnoreCase(loaiReport)){
                if(hasPrivilege("EMR_REPORT_DIEUTRI")) {
                    //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.dieuTri"));
                    
                    params.put("tenThongTin", getMessage("report.tieuDe.tenDieuTri"));
                    params.put("SUBREPORT_DIR", getRealPath("report/DieuTri"));
                    jrxmlFile = getRealPath("report/DieuTri/DieuTri.jasper");
                    
                    var emrDieuTris = danhSachHSBA.emrVaoKhoas.stream()
                                                   .flatMap(x -> x.emrDieuTris.stream())
                                                   .collect(Collectors.toList());
                    
                                                   
                    
                    jasperIs = new FileInputStream(new File(jrxmlFile));
                    jasperPrint = JasperFillManager.fillReport(jasperIs, params, new JRBeanCollectionDataSource(emrDieuTris));
                }                
             }else if ("chucnangsong".equalsIgnoreCase(loaiReport)){
                if(hasPrivilege("EMR_REPORT_CHUCNANGSONG")) {
                    ///emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.theoDoiChucNangSong"));
                    
                    params.put("tenThongTin", getMessage("report.tieuDe.tenTheoDoiChucNangSong"));
                    params.put("SUBREPORT_DIR", getRealPath("report/ChucNangSong"));
                    jrxmlFile = getRealPath("report/ChucNangSong/ChucNangSong.jasper");
                    jasperIs = new FileInputStream(new File(jrxmlFile));
                    
                    var emrChucNangSongs = danhSachHSBA.emrVaoKhoas.stream()
                                                        .flatMap(x -> x.emrChucNangSongs.stream())
                                                        .collect(Collectors.toList());
                
                    jasperPrint = JasperFillManager.fillReport(jasperIs,
                        params, new JRBeanCollectionDataSource(emrChucNangSongs));
                }
             }else if ("cacloaigiaytokhac".equalsIgnoreCase(loaiReport)){
                // NoiPD add 2016/03/21
                if(hasPrivilege("EMR_REPORT_CACGIAYTODIKEMKHAC")) {
                    //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.cacGiayToDiKemKhac"));
                      
                      params.put("tenThongTin", getMessage("report.tieuDe.cacGiayToDiKemKhac"));
                      params.put("SUBREPORT_DIR", getRealPath("report/CacGiayToDiKemKhac"));
                      jrxmlFile = getRealPath("report/CacGiayToDiKemKhac/CacGiayToDiKemKhac.jasper");
                      jasperIs = new FileInputStream(new File(jrxmlFile));
                  
                       jasperPrint = JasperFillManager.fillReport(jasperIs,
                          params, new JRBeanCollectionDataSource(dsHsba));
                    }
            
             }else if ("donthuoc".equalsIgnoreCase(loaiReport)){
                if(hasPrivilege("EMR_REPORT_DONTHUOC")) {
                    //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.donThuoc"));
                    
                    params.put("tenThongTin", getMessage("report.tieuDe.tenDonThuoc"));
                    params.put("SUBREPORT_DIR", getRealPath("report/DonThuoc"));
                    jrxmlFile = getRealPath("report/DonThuoc/DonThuoc.jasper");
                    jasperIs = new FileInputStream(new File(jrxmlFile));
                    
                     jasperPrint = JasperFillManager.fillReport(jasperIs, params, new JRBeanCollectionDataSource(danhSachHSBA.emrDonThuocs));
                }
                
            }   else if ("donthuocyhct".equalsIgnoreCase(loaiReport)){
                if(hasPrivilege("EMR_REPORT_DONTHUOC_YHCT")) {
                    //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.donThuocYHCT"));
                    
                    params.put("tenThongTin", getMessage("report.tieuDe.tenDonThuocYHCT"));
                    params.put("SUBREPORT_DIR", getRealPath("report/DonThuocYHCT"));
                    jrxmlFile = getRealPath("report/DonThuocYHCT/DonThuocYHCT.jasper");
                    jasperIs = new FileInputStream(new File(jrxmlFile));
                    
                     jasperPrint = JasperFillManager.fillReport(jasperIs,
                        params, new JRBeanCollectionDataSource(danhSachHSBA.emrYhctDonThuocs));
                }
                
             }else if ("chamsoc".equalsIgnoreCase(loaiReport)){
                 if(hasPrivilege("EMR_REPORT_CHAMSOC")) {
                     //emrLogTruyCapHsba.setNoidung(getMessage("formThemMoiHoSoBenhAnVaoForm.tieuDe.chamSoc"));
                        
                    params.put("tenThongTin", getMessage("report.tieuDe.tenChamSoc"));
                    params.put("SUBREPORT_DIR", getRealPath("report/ChamSoc"));
                    jrxmlFile = getRealPath("report/ChamSoc/ChamSoc.jasper");
                    
                    jasperIs = new FileInputStream(new File(jrxmlFile));
                    
                    var emrChamSocs = danhSachHSBA.emrVaoKhoas.stream()
                            .flatMap(x -> x.emrChamSocs.stream())
                            .collect(Collectors.toList());
                
                     jasperPrint = JasperFillManager.fillReport(jasperIs,
                                             params, new JRBeanCollectionDataSource(emrChamSocs));
                }
             } else { // Truong hop xem to benh an
                //emrLogTruyCapHsba.setNoidung(getMessage("emr.report.navigation.tobenhan"));
                
                String maLoaiBenhAn = danhSachHSBA.emrDmLoaiBenhAn != null? danhSachHSBA.emrDmLoaiBenhAn.ma : "";
                
                params.put("SUBREPORT_DIR", getRealPath("report/BenhAn/ToBAFooter"));
                
                // Check loai ho so benh an
                if (Constants.EMR_MA_LOAI_BENH_AN_NOI_KHOA.equals(maLoaiBenhAn)) {
                    jrxmlFile = getRealPath("report/BenhAn/NOIKHOA/BenhAn-NOIKHOA.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_NHI_KHOA.equals(maLoaiBenhAn)) {
                    jrxmlFile = getRealPath("report/BenhAn/NHIKHOA/BenhAn-NHIKHOA.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_TRUYEN_NHIEM.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/TRUYENNHIEM/BenhAnTruyenNhiem.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_PHU_KHOA.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/PHUKHOA/BenhAnPhuKhoa.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_SAN_KHOA.equals(maLoaiBenhAn)) { 
                    
                    params.put("SUBREPORT_DIR_TIEN_SU", getRealPath("report/BenhAn/San-Khoa"));
                    jrxmlFile = getRealPath("report/BenhAn/San-Khoa/BenhAnSanKhoa.jasper");                    
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_SO_SINH.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/SOSINH/BenhAn-SOSINH.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_TAM_THAN.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/TAMTHAN/BenhAn-TAMTHAN.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_DA_LIEU.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/DALIEU/BenhAn-DALIEU.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_DIEU_DUONG_PHUC_HOI_CHUC_NANG.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/PHCN/BenhAn-PHCN.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_HUYET_HOC_TRUYEN_MAU.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/HHTM/BenhAn-HHTM.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_NGOAI_KHOA.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/NGOAIKHOA/BenhAn-NGOAIKHOA.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_BONG.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/BONG/BenhAn-BONG.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_UNG_BUOU.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/UNGBUOU/BenhAn-UngBuou.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_RANG_HAM_MAT.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/RHM/BenhAn-RHM.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_TAI_MUI_HONG.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/TMH/BenhAn-TMH.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_MAT.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/MAT/BenhAn-Mat.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_LAC_VAN_NHAN.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/MAT/Benh-An-Mat-Lac.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_MAT_TRE_EM.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/MAT/BenhAnMatTE.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_MAT_GLOCOM.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/MAT/Benh-an-Mat-Glocom.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_LAO.equals(maLoaiBenhAn)) {
                    jrxmlFile = getRealPath("report/BenhAn/LAO/BenhAn-LAO.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_TAY_CHAN_MIENG.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/TAYCHANMIENG/BenhAnTayChanMieng.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_TIM_MACH.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/TIMMACH/BenhAn-TimMach.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_NGOAI_TRU.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/NGOAITRU/Benh-an-Ngoai-tru.jasper");
                    
                }else if (Constants.EMR_MA_LOAI_BENH_AN_NGOAI_TRU_TAI_MUI_HONG.equals(maLoaiBenhAn)) {
                    jrxmlFile = getRealPath("report/BenhAn/TMH/BenhAn-TMH-NgoaiTru.jasper");
                    
                }else if (Constants.EMR_MA_LOAI_BENH_AN_NGOAI_TRU_MAT.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/NGOAITRU-MAT/BenhAn-NgoaiTru_Mat.jasper");
                    
                }else if (Constants.EMR_MA_LOAI_BENH_AN_NGOAI_TRU_RANG_HAM_MAT.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/RHM/BenhAn-RHM-NgoaiTru.jasper");
                    
                }else if (Constants.EMR_MA_LOAI_BENH_AN_YHCT_NOI_TRU.equals(maLoaiBenhAn)) {
                    jrxmlFile = getRealPath("report/BenhAn/YHCTNoiTru/Benh-an-YHCT-Noi-tru.jasper");
                    
                }else if (Constants.EMR_MA_LOAI_BENH_AN_YHCT_NGOAI_TRU.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/YHCTNgoaiTru/Benh-an-YHCT-Ngoai-tru.jasper");
                    
                }else if (Constants.EMR_MA_LOAI_BENH_AN_YHCT_NOI_TRU_BAN_NGAY.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/YHCTNoiTruBanNgay/Benh-an-YHCT-Noi-Tru-BN.jasper");
                    
                }else if (Constants.EMR_MA_LOAI_BENH_AN_YHCT_NHA_BA.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/NhaBaYHCT/Nha-ba-YHCT.jasper");
                    
                }else if (Constants.EMR_MA_LOAI_BENH_AN_YHCT_XA_PHUONG.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/BAXP/BenhAn-BAXP.jasper");
                    
                }/*                 
                     else if (Constants.EMR_MA_LOAI_BENH_AN_SO_SINH_PSTW.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/SOSINH/BenhAnSoSinh_PSTW.jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_PHU_PSTW.equals(maLoaiBenhAn)) { 
                    jrxmlFile = getRealPath("report/BenhAn/PHUKHOA-PHUSANTW/BenhAnPhuKhoa(PSTW).jasper");
                    
                } else if (Constants.EMR_MA_LOAI_BENH_AN_SAN_PSTW.equals(maLoaiBenhAn)) { 
                    params.put("SUBREPORT_DIR", getRealPath("report/BenhAn/SANKHOA_PSTW"));  
                    var emrCkTienSuSanKhoaChiTiets = danhSachHSBA.emrBenhAn.emrCkTienSuSanKhoa.emrCkTienSuSanKhoaChiTiets;
                    
                  params.put("TTCTTIENSUSANKHOA", emrCkTienSuSanKhoaChiTiets);
                  jrxmlFile = getRealPath("report/BenhAn/SANKHOA_PSTW/BenhAnSanKhoa_PSTW.jasper");
              }*/else {
                  
                  jrxmlFile = getRealPath("report/BenhAn/GEN/BenhAn.jasper");
              }
                
              jasperIs = new FileInputStream(new File(jrxmlFile));
              
              jasperPrint = JasperFillManager.fillReport(jasperIs,
                  params, new JRBeanCollectionDataSource(dsHsba));
             }
            
            //if (!"1".equals(fromCsdlTg)) {
                //getEmrLogTruyCapHsbaService().luuEmrLogTruyCapHsba(emrLogTruyCapHsba);  
            //} 
        }
        
        var ouputStream = new ByteArrayOutputStream();

        var exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(ouputStream));

        exporter.exportReport();
        ouputStream.close();
        return ouputStream.toByteArray();
    }
}
