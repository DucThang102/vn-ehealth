package vn.ehealth.emr.cda;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.vocab.ActRelationshipHasComponent;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntryRelationship;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HSBAFactory;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaDocument;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaHistoryAndPhysicalNoteSection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaInjuryIncidentDescriptionSection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaPhysicalFindingsSection;
import vn.ehealth.emr.model.EmrBenhAn;
import vn.ehealth.emr.model.EmrBenhNhan;
import vn.ehealth.emr.model.EmrHinhAnhTonThuong;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.utils.JsonUtil;

public class ThongTinBenhAnUtil {
    
    protected static Pattern pattern;
    protected static Matcher matcher;
    protected static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(/bmp|jpg|gif|png))$)";
    protected static ObjectMapper oMapper = new ObjectMapper(); 
    
    static{
        pattern = Pattern.compile(IMAGE_PATTERN);
        oMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS"));
    }
    
    public static void addHistoryAndPhysicalNote(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters, Properties properties, String srcLocalFolder){
        try {
            var emrBenhAn = emrDanhSachHoSoBenhAn.getEmrBenhAn();     
            var emrBenhNhan = emrDanhSachHoSoBenhAn.getEmrBenhNhan();
            
            var historyAndPhysicalNoteSection = HSBAFactory.eINSTANCE.createHsbaHistoryAndPhysicalNoteSection().init();
            doc.addSection(historyAndPhysicalNoteSection);              
            String benhAnTitle = properties.getProperty("BA_BENHAN_TITLE", "BA_BENHAN_TITLE");
            CDAExportUtil.addSectionTitle(historyAndPhysicalNoteSection, benhAnTitle);
            
            StringBuilder historyAndPhysicalText = new StringBuilder();
            String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
            String sectionContentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
            
            if(emrBenhAn != null){
                //Lý do vào viện
                var reasonForVisitSection = HSBAFactory.eINSTANCE.createHsbaReasonForVisitSection().init();
                historyAndPhysicalNoteSection.addSection(reasonForVisitSection);
                String lyDoVaoVienTitle = properties.getProperty("BA_LYDOVAOVIEN_TITLE", "BA_LYDOVAOVIEN_TITLE");
                CDAExportUtil.addSectionTitle(reasonForVisitSection, lyDoVaoVienTitle);              
                
                String lyDoVaoVien = emrBenhAn.lydovaovien;
                CDAExportUtil.setSectionData(reasonForVisitSection, lyDoVaoVien);
                
                //Vào ngày thứ ... của bệnh
                Integer vaoVienNgayThu = emrBenhAn.vaongaythu;
                if(vaoVienNgayThu != null){
                    var vaoVienNgayThuCuaBenhObs = HSBAFactory.eINSTANCE.createHsbaVaoVienNgayThuCuaBenhObservation().init();
                    ED vaoVienNgayThuED = DatatypesFactory.eINSTANCE.createED(vaoVienNgayThu.toString());
                    vaoVienNgayThuCuaBenhObs.setText(vaoVienNgayThuED);
                    
                    reasonForVisitSection.addObservation(vaoVienNgayThuCuaBenhObs);         
                }
                    
                //Qúa trình bệnh lý
                var historyOfPresentSection = HSBAFactory.eINSTANCE.createHsbaHistoryOfPresentIllnessSection().init();
                historyAndPhysicalNoteSection.addSection(historyOfPresentSection);
                String benhlyTitle = properties.getProperty("BA_QUATRINHBENHLY_TITLE", "BA_QUATRINHBENHLY_TITLE");      
                CDAExportUtil.addSectionTitle(historyOfPresentSection, benhlyTitle);     
                
                String quaTrinhBenhLy = emrBenhAn.quatrinhbenhly;
                CDAExportUtil.setSectionData(historyOfPresentSection, quaTrinhBenhLy);
                
                //Tiền sử bản thân
                var historyOfPastSection = HSBAFactory.eINSTANCE.createHsbaHistoryOfPastIllnessSection().init();
                historyAndPhysicalNoteSection.addSection(historyOfPastSection);
                String tienSuBanThanTitle = properties.getProperty("BA_TIENSUBANTHAN_TITLE", "BA_TIENSUBANTHAN_TITLE");
                CDAExportUtil.addSectionTitle(historyOfPastSection, tienSuBanThanTitle);     
                
                String tienSuBanThan = emrBenhAn.tiensubanthan;
                CDAExportUtil.setSectionData(historyOfPastSection, tienSuBanThan);
                
                //Tiền sử gia đình
                var familyHistorySection = HSBAFactory.eINSTANCE.createHsbaFamilyHistorySection().init();
                historyAndPhysicalNoteSection.addSection(familyHistorySection);
                String tienSuGiaDinh = emrBenhAn.tiensugiadinh;            
                String tienSuGiaDinhTitle = properties.getProperty("BA_TIENSUGIADINH_TITLE", "BA_TIENSUGIADINH_TITLE");
                CDAExportUtil.addSectionTitle(familyHistorySection, tienSuGiaDinhTitle); 
                
                CDAExportUtil.setSectionData(familyHistorySection, tienSuGiaDinh);

                
                //Đặc điểm liên quan bệnh
                addSocialHistorySection(historyAndPhysicalNoteSection, emrBenhAn, emrBenhNhan, emrParameters, properties, sectionContentDelimiter, splitDelimiter);
        
                //Dị ứng
                var allergiesSection = HSBAFactory.eINSTANCE.createHsbaAllergiesSection().init();
                historyAndPhysicalNoteSection.addSection(allergiesSection); 
                String diUngTitle = properties.getProperty("BA_DIUNG_TITLE", "BA_DIUNG_TITLE");
                CDAExportUtil.addSectionTitle(allergiesSection, diUngTitle); 
                
                String diUng = emrBenhAn.diung;
                CDAExportUtil.setSectionData(allergiesSection, diUng);
                
                //Tiêm chủng
                addTiemChung(historyAndPhysicalNoteSection, properties, emrBenhAn);
                        
                //Quá trình sinh trưởng
                addQuaTrinhSinhTruong(historyAndPhysicalNoteSection, properties, emrBenhAn);
        
                //Tình trạng sản phụ trong khi đẻ
                addTinhTrangSanPhu(historyAndPhysicalNoteSection, properties, emrBenhAn);

                //Tình trạng sơ sinh khi ra đời
                addTinhTrangSoSinh(historyAndPhysicalNoteSection, properties, emrBenhAn);
            
                //Phương pháp hồi sinh sau đẻ
                addPhuongPhapHoiSinh(historyAndPhysicalNoteSection, properties, emrBenhAn);
                
                //Dịch tễ
                addDichTe(historyAndPhysicalNoteSection, properties, emrBenhAn);
        
                //Chức năng sinh hoạt của người bệnh
                addChucNangSinhHoat(historyAndPhysicalNoteSection, properties, emrBenhAn);
                
                //Khám toàn thân
                addKhamToanThan(historyAndPhysicalNoteSection, properties, emrBenhAn);                              
                
                //Dấu hiệu sinh tồn
                addVitalSignsSection(historyAndPhysicalNoteSection, emrBenhAn, sectionContentDelimiter, splitDelimiter, properties);
                
                //Khám các bộ phận
                addPhysicalFindingsSection(historyAndPhysicalNoteSection, emrBenhAn, sectionContentDelimiter, splitDelimiter, properties);
                
                //Hình ảnh tổn thương 
                var injuryIncidentDescSection = HSBAFactory.eINSTANCE.createHsbaInjuryIncidentDescriptionSection().init();
                historyAndPhysicalNoteSection.addSection(injuryIncidentDescSection);
                String hinhAnhTonThuongTitle = properties.getProperty("BA_HINHANHTONTHUONG_TITLE", "BA_HINHANHTONTHUONG_TITLE");
                CDAExportUtil.addSectionTitle(injuryIncidentDescSection, hinhAnhTonThuongTitle);
                            
                StringBuilder injuryIncidentDescText = new StringBuilder();         
                var lstEmrHinhAnhTonThuong = emrDanhSachHoSoBenhAn.getEmrHinhAnhTonThuongs();
                if(lstEmrHinhAnhTonThuong != null && lstEmrHinhAnhTonThuong.size() > 0){
                    for (var item : lstEmrHinhAnhTonThuong) {
                        addInjuryObservationMedia(injuryIncidentDescSection, item, injuryIncidentDescText, properties, srcLocalFolder);
                    }               
                }
                CDAExportUtil.setSectionData(injuryIncidentDescSection, injuryIncidentDescText.toString());  
                    
                //Tóm tắt bệnh án
                var reviewOfSystemSection = HSBAFactory.eINSTANCE.createHsbaReviewOfSystemSection().init();
                historyAndPhysicalNoteSection.addSection(reviewOfSystemSection);
                String tomTatBenhAnTitle = properties.getProperty("BA_TOMTATBENHAN_TITLE", "BA_TOMTATBENHAN_TITLE");
                CDAExportUtil.addSectionTitle(reviewOfSystemSection, tomTatBenhAnTitle);
                
                String tomtat = emrBenhAn.tomtat;
                CDAExportUtil.setSectionData(reviewOfSystemSection, tomtat);
                
                //Chẩn đoán khi vào khoa điều trị
                var diagnosisNarrativeSection = HSBAFactory.eINSTANCE.createHsbaDiagnosisNarrativeSection().init();
                historyAndPhysicalNoteSection.addSection(diagnosisNarrativeSection);
                String cdVaoKhoaDieuTriTitle = properties.getProperty("BA_CHANDOANVAOKHOADIEUTRI_TITLE", "BA_CHANDOANVAOKHOADIEUTRI_TITLE");
                CDAExportUtil.addSectionTitle(diagnosisNarrativeSection, cdVaoKhoaDieuTriTitle);
                        
                addDiagnosisObservations(diagnosisNarrativeSection, emrBenhAn, emrParameters, splitDelimiter, properties);

                //Tiên lượng
                var assessmentsSection = HSBAFactory.eINSTANCE.createHsbaAssessmentsSection().init();
                historyAndPhysicalNoteSection.addSection(assessmentsSection);
                String tienLuongTitle = properties.getProperty("BA_TIENLUONG_TITLE", "BA_TIENLUONG_TITLE");
                CDAExportUtil.addSectionTitle(assessmentsSection, tienLuongTitle);
                        
                String tienLuong = emrBenhAn.tienluong;
                CDAExportUtil.setSectionData(assessmentsSection, tienLuong);
        
                //Hướng điều trị
                var planOfCareSection = HSBAFactory.eINSTANCE.createHsbaPlanOfCareSection().init();
                historyAndPhysicalNoteSection.addSection(planOfCareSection);
                String huongDieuTriTitle = properties.getProperty("BA_HUONGDIEUTRI_TITLE", "BA_HUONGDIEUTRI_TITLE");
                CDAExportUtil.addSectionTitle(planOfCareSection, huongDieuTriTitle);
            
                String huongDieuTri = emrBenhAn.huongdieutri;
                CDAExportUtil.setSectionData(planOfCareSection, huongDieuTri);
            }
            CDAExportUtil.setSectionData(historyAndPhysicalNoteSection, historyAndPhysicalText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    
    public static void addSocialHistoryObservationContent(Observation obs, String orginiralText, String valueText){
        try {
            II obsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            obs.getIds().add(obsId);        
            CD obsCode = obs.getCode();             
            if(!StringUtils.isEmpty(orginiralText)){
                ED originiralValue = DatatypesFactory.eINSTANCE.createED(orginiralText);
                obsCode.setOriginalText(originiralValue);
            }
            
            if(!StringUtils.isEmpty(valueText)){
                ST valueTag = DatatypesFactory.eINSTANCE.createST(valueText);
                obs.getValues().add(valueTag);          
            }
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addSocialHistorySection(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, EmrBenhAn emrBenhAn, EmrBenhNhan emrBenhNhan, Map<String, String> emrParameters, Properties properties, String sectionContentDelimiter, String splitDelimiter){
      //Đặc điểm liên quan bệnh
        var socialHistorySection = HSBAFactory.eINSTANCE.createHsbaSocialHistorySection().init();
        historyAndPhysicalNoteSection.addSection(socialHistorySection);
        String dacDiemLienQuanTitle = properties.getProperty("BA_DACDIEMLIENQUANBENH_TITLE", "BA_DACDIEMLIENQUANBENH_TITLE");
        CDAExportUtil.addSectionTitle(socialHistorySection, dacDiemLienQuanTitle);
        
        StringBuilder socialHistoryText = new StringBuilder();
        
        //Thuốc lá
        var tobaccoObs = HSBAFactory.eINSTANCE.createHsbaTobaccoObservation().init();
        socialHistorySection.addObservation(tobaccoObs);
        String tobaccoOrgText = properties.getProperty("BA_THUOCLA_ORGTEXT", "BA_THUOCLA_ORGTEXT");
        String tobaccoTextValue = emrBenhAn.thuocla;
        addSocialHistoryObservationContent(tobaccoObs, tobaccoOrgText, tobaccoTextValue);
        if(!StringUtils.isEmpty(tobaccoTextValue)){
            socialHistoryText.append(tobaccoOrgText).append(sectionContentDelimiter).append(tobaccoTextValue).append(splitDelimiter);
        }   
        
        //Ma túy
        var drugMisuseObs = HSBAFactory.eINSTANCE.createHsbaDrugMisuseObservation().init();
        socialHistorySection.addObservation(drugMisuseObs);
        String matuyOrgText = properties.getProperty("BA_MATUY_ORGTEXT", "BA_MATUY_ORGTEXT");
        String matuyTextValue = emrBenhAn.matuy;
        addSocialHistoryObservationContent(drugMisuseObs, matuyOrgText, matuyTextValue);
        if(!StringUtils.isEmpty(matuyTextValue)){
            socialHistoryText.append(matuyOrgText).append(sectionContentDelimiter).append(matuyTextValue).append(splitDelimiter);
        }
        
        //Rượu bia
        var alcoholObs = HSBAFactory.eINSTANCE.createHsbaAlcoholObservation().init();
        socialHistorySection.addObservation(alcoholObs);
        String ruouBiaOrgText = properties.getProperty("BA_RUOUBIA_ORGTEXT", "BA_RUOUBIA_ORGTEXT");
        String ruouBiaTextValue = emrBenhAn.ruoubia;
        addSocialHistoryObservationContent(alcoholObs, ruouBiaOrgText, ruouBiaTextValue);
        if(!StringUtils.isEmpty(ruouBiaTextValue)){
            socialHistoryText.append(ruouBiaOrgText).append(sectionContentDelimiter).append(ruouBiaTextValue).append(splitDelimiter);
        }   
        //Đặc điểm khác
        var ortherObs = HSBAFactory.eINSTANCE.createHsbaOtherSocialHistoryObservation().init();
        socialHistorySection.addObservation(ortherObs);
        String otherText = properties.getProperty("BA_KHAC_ORGTEXT", "BA_KHAC_ORGTEXT");
        String otherTextValue = emrBenhAn.dacdiemkhac;
        addSocialHistoryObservationContent(ortherObs, otherText, otherTextValue);
        if(!StringUtils.isEmpty(otherTextValue)){
            socialHistoryText.append(otherText).append(sectionContentDelimiter).append(otherTextValue).append(splitDelimiter);
        }   
        
        //Nguồn lây
        String nguonlay = emrBenhAn.nguonlay;
        if(!StringUtils.isEmpty(nguonlay)){
            var nguonlayObs = HSBAFactory.eINSTANCE.createHsbaNguonLayObservation().init();
            nguonlayObs.setText(DatatypesFactory.eINSTANCE.createED(nguonlay));
            socialHistorySection.addObservation(nguonlayObs);
        }
        
        //Nghề nghiệp
        var emrDmNgheNghiep = emrBenhNhan.emrDmNgheNghiep;
        var employmentObs = HSBAFactory.eINSTANCE.createHsbaEmploymentObservation().init();
        socialHistorySection.addObservation(employmentObs);
        String ngheNghiepOrgText = properties.getProperty("BA_NGHENGHIEP_ORGTEXT", "BA_NGHENGHIEP_ORGTEXT");
        CD obsCode = employmentObs.getCode();               
        if(!StringUtils.isEmpty(ngheNghiepOrgText)){
            ED originiralValue = DatatypesFactory.eINSTANCE.createED(ngheNghiepOrgText);
            obsCode.setOriginalText(originiralValue);
        }
        
        if(emrDmNgheNghiep != null){            
            String code = emrDmNgheNghiep.maicd;
            String codeSystem = emrParameters.get("emr_dm_nghe_nghiep");
            String displayName = emrDmNgheNghiep.ten;
            
            CDAExportUtil.addCdObservationValueTag(employmentObs, code, codeSystem, displayName);
        }
        if(socialHistoryText.length() > 0){
            socialHistoryText.setLength(socialHistoryText.length() - 1);
        }
        
        CDAExportUtil.setSectionData(socialHistorySection, socialHistoryText.toString());          
    }
    
    public static void addTiemChung(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var immunizationsSection = HSBAFactory.eINSTANCE.createHsbaImmunizationsSection().init();
            historyAndPhysicalNoteSection.addSection(immunizationsSection);
            String tiemChungTitle = properties.getProperty("BA_TIEMCHUNG_TITLE", "BA_TIEMCHUNG_TITLE");
            CDAExportUtil.addSectionTitle(immunizationsSection, tiemChungTitle); 
            
            var emrCkTiemChung = emrBenhAn.emrCkTiemChung;
            if(emrCkTiemChung != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTiemChung); //HSBAUtil.convertObjectToJson(EmrCkTiemChung.class, emrCkTiemChung);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    immunizationsSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String tiemChung = emrBenhAn.tiemchung;
            CDAExportUtil.setSectionData(immunizationsSection, tiemChung);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addQuaTrinhSinhTruong(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var growthDevelopmentSection = HSBAFactory.eINSTANCE.createHsbaHistoryOfGrowthDevelopmentSection().init();
            historyAndPhysicalNoteSection.addSection(growthDevelopmentSection);
            String qtSinhTruongTitle = properties.getProperty("BA_QUATRINHSINHTRUONG_TITLE", "BA_QUATRINHSINHTRUONG_TITLE");
            CDAExportUtil.addSectionTitle(growthDevelopmentSection, qtSinhTruongTitle);
            
            var emrCkQuaTrinhSinhTruong = emrBenhAn.emrCkQuaTrinhSinhTruong;
            if(emrCkQuaTrinhSinhTruong != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkQuaTrinhSinhTruong); //HSBAUtil.convertObjectToJson(EmrCkQuaTrinhSinhTruong.class, emrCkQuaTrinhSinhTruong);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    growthDevelopmentSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String quaTringSinhTruong = emrBenhAn.quatrinhsinhtruong;
            CDAExportUtil.setSectionData(growthDevelopmentSection, quaTringSinhTruong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

    
    public static void addTinhTrangSanPhu(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var healthWomenStatusSection = HSBAFactory.eINSTANCE.createHsbaHealthWomenStatusSection().init();
            historyAndPhysicalNoteSection.addSection(healthWomenStatusSection);
            String ttSanPhuTitle = properties.getProperty("BA_TINHTRANGSANPHU_TITLE", "BA_TINHTRANGSANPHU_TITLE");
            CDAExportUtil.addSectionTitle(healthWomenStatusSection, ttSanPhuTitle);

            var emrCkTinhTrangSanPhu = emrBenhAn.emrCkTinhTrangSanPhu;
            if(emrCkTinhTrangSanPhu != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTinhTrangSanPhu); //HSBAUtil.convertObjectToJson(EmrCkTinhTrangSanPhu.class, emrCkTinhTrangSanPhu);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    healthWomenStatusSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String tinhTrangSanPhu = emrBenhAn.tinhtrangsanphu;
            CDAExportUtil.setSectionData(healthWomenStatusSection, tinhTrangSanPhu);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addTinhTrangSoSinh(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var statusOfInfantsSection = HSBAFactory.eINSTANCE.createHsbaStatusOfInfantsSection().init();
            historyAndPhysicalNoteSection.addSection(statusOfInfantsSection);
            String ttSoSinhTitle = properties.getProperty("BA_TINHTRANGSOSINH_TITLE", "BA_TINHTRANGSOSINH_TITLE");
            CDAExportUtil.addSectionTitle(statusOfInfantsSection, ttSoSinhTitle);    
            
            var emrCkTinhTrangSoSinh = emrBenhAn.emrCkTinhTrangSoSinh;
            if(emrCkTinhTrangSoSinh != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTinhTrangSoSinh);//HSBAUtil.convertObjectToJson(EmrCkTinhTrangSoSinh.class, emrCkTinhTrangSoSinh);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    statusOfInfantsSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String tinhTrangSoSinh = emrBenhAn.tinhtrangsosinh;
            CDAExportUtil.setSectionData(statusOfInfantsSection, tinhTrangSoSinh);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addPhuongPhapHoiSinh(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var resuscitationAfterCalvingSection = HSBAFactory.eINSTANCE.createHsbaResuscitationAfterCalvingSection().init();
            historyAndPhysicalNoteSection.addSection(resuscitationAfterCalvingSection);
            String hoiSinhTitle = properties.getProperty("BA_HOISINHSAUDE_TITLE", "BA_HOISINHSAUDE_TITLE");
            CDAExportUtil.addSectionTitle(resuscitationAfterCalvingSection, hoiSinhTitle);   

            var emrCkPhuongPhapHoiSinh = emrBenhAn.emrCkPhuongPhapHoiSinh;
            if(emrCkPhuongPhapHoiSinh != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkPhuongPhapHoiSinh);//HSBAUtil.convertObjectToJson(EmrCkPhuongPhapHoiSinh.class, emrCkPhuongPhapHoiSinh);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    resuscitationAfterCalvingSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String ppHoiSinh = emrBenhAn.phuongphaphoisinh;
            CDAExportUtil.setSectionData(resuscitationAfterCalvingSection, ppHoiSinh);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addDichTe(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var healthRelatedConditionsSection = HSBAFactory.eINSTANCE.createHsbaHealthRelatedConditionsSection().init();
            historyAndPhysicalNoteSection.addSection(healthRelatedConditionsSection);
            String dichTeTitle = properties.getProperty("BA_DICHTE_TITLE", "BA_DICHTE_TITLE");
            CDAExportUtil.addSectionTitle(healthRelatedConditionsSection, dichTeTitle);
            
            var emrCkMoiSinh = emrBenhAn.emrCkMoiSinh;
            if(emrCkMoiSinh != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkMoiSinh);//HSBAUtil.convertObjectToJson(EmrCkMoiSinh.class, emrCkMoiSinh);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    healthRelatedConditionsSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String dichTe = emrBenhAn.dichte;
            CDAExportUtil.setSectionData(healthRelatedConditionsSection, dichTe);
        } catch (Exception e) {
            e.printStackTrace();
        }               
    }
    
    public static void addChucNangSinhHoat(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var historyOfFunctionalSection = HSBAFactory.eINSTANCE.createHsbaHistoryOfFunctionalStatusSection().init();
            historyAndPhysicalNoteSection.addSection(historyOfFunctionalSection);
            String chucNangSinhHoatTitle = properties.getProperty("BA_CHUCNANGSINHHOAT_TITLE", "BA_CHUCNANGSINHHOAT_TITLE");
            CDAExportUtil.addSectionTitle(historyOfFunctionalSection, chucNangSinhHoatTitle);

            var emrCkChucNangSinhHoat = emrBenhAn.emrCkChucNangSinhHoat;
            if(emrCkChucNangSinhHoat != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkChucNangSinhHoat);//HSBAUtil.convertObjectToJson(EmrCkChucNangSinhHoat.class, emrCkChucNangSinhHoat);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    historyOfFunctionalSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String chucNangSinhHoat = emrBenhAn.chucnangsinhhoat;
            CDAExportUtil.setSectionData(historyOfFunctionalSection, chucNangSinhHoat);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addKhamToanThan(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var generalStatusSection = HSBAFactory.eINSTANCE.createHsbaGeneralStatusSection().init();
            historyAndPhysicalNoteSection.addSection(generalStatusSection);
            String khamToanThanTitle = properties.getProperty("BA_KHAMTOANTHAN_TITLE", "BA_KHAMTOANTHAN_TITLE");
            CDAExportUtil.addSectionTitle(generalStatusSection, khamToanThanTitle);                  
                        
            var emrCkToanThan = emrBenhAn.emrCkToanThan;
            if(emrCkToanThan != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkToanThan);//HSBAUtil.convertObjectToJson(EmrCkToanThan.class, emrCkToanThan);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    generalStatusSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String khamToanThan = emrBenhAn.toanthan;
            CDAExportUtil.setSectionData(generalStatusSection, khamToanThan);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addPqObservationValueTag(Observation obs, String unit, Integer value){
        try {
            var valueTag = DatatypesFactory.eINSTANCE.createPQ();
            
            if(value != null){  
                valueTag.setValue((double)value);
                if(!StringUtils.isEmpty(unit)){
                    valueTag.setUnit(unit);
                }
                obs.getValues().add(valueTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addVitalSignsSection(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, EmrBenhAn emrBenhAn, String sectionContentDelimiter, String splitDelimiter, Properties properties){
        try {
            var vitalSignsSection = HSBAFactory.eINSTANCE.createHsbaVitalSignsSection().init();
            historyAndPhysicalNoteSection.addSection(vitalSignsSection);    
            String dauHieuSinhTonTitle = properties.getProperty("BA_DAUHIEUSINHTON_TITLE", "BA_DAUHIEUSINHTON_TITLE");
            CDAExportUtil.addSectionTitle(vitalSignsSection, dauHieuSinhTonTitle);               
            StringBuilder vitalSignsText = new StringBuilder();
            
            var vitalSingsOrganizer = HSBAFactory.eINSTANCE.createHsbaVitalSignsOrganizer().init();
            vitalSignsSection.addOrganizer(vitalSingsOrganizer);
            II vitalSignsOrganizerid = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            vitalSingsOrganizer.getIds().add(vitalSignsOrganizerid);
                
            //Mạch
            var hearRateComponent = CDAFactory.eINSTANCE.createComponent4();
            hearRateComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            vitalSingsOrganizer.getComponents().add(hearRateComponent);
            
            var heartRateObs = HSBAFactory.eINSTANCE.createHsbaHeartRateObservation().init();
            hearRateComponent.setObservation(heartRateObs);     
            String machUnit = properties.getProperty("BA_DHST_MACH_UNIT", "BA_DHST_MACH_UNIT");
            Integer machValue = (int) (double) emrBenhAn.mach;
            addPqObservationValueTag(heartRateObs, machUnit, machValue);
            if(machValue != null){
                vitalSignsText.append(properties.getProperty("BA_DHST_MACH_TITLE", "BA_DHST_MACH_TITLE")).append(sectionContentDelimiter).append(machValue).append(machUnit).append(splitDelimiter);
            }
            
            //Nhiệt độ
            var bodyTemperatureComponent = CDAFactory.eINSTANCE.createComponent4();
            bodyTemperatureComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            vitalSingsOrganizer.getComponents().add(bodyTemperatureComponent);
            
            var bodyTemperatureObs = HSBAFactory.eINSTANCE.createHsbaBodyTemperatureObservation().init();
            bodyTemperatureComponent.setObservation(bodyTemperatureObs);
            String nhietDoUnit = properties.getProperty("BA_DHST_NHIETDO_UNIT", "BA_DHST_NHIETDO_UNIT");
            Integer nhietDoValue = (int) (double) emrBenhAn.nhietdo;
            addPqObservationValueTag(bodyTemperatureObs, nhietDoUnit, nhietDoValue);
            if(nhietDoValue != null){
                vitalSignsText.append(properties.getProperty("BA_DHST_NHIETDO_TITLE", "BA_DHST_NHIETDO_TITLE")).append(sectionContentDelimiter).append(nhietDoValue).append(nhietDoUnit).append(splitDelimiter);
            }
            
            //Huyết áp trị cao
            var bpSystolicComponent = CDAFactory.eINSTANCE.createComponent4();
            bpSystolicComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            vitalSingsOrganizer.getComponents().add(bpSystolicComponent);
            
            var bpSystolicObs = HSBAFactory.eINSTANCE.createHsbaBPSystolicObservation().init();
            bpSystolicComponent.setObservation(bpSystolicObs);
            String huyetApUnit = properties.getProperty("BA_DHST_HUYETAP_UNIT", "BA_DHST_HUYETAP_UNIT");
            Integer huyetApCao = emrBenhAn.huyetapcao;
            addPqObservationValueTag(bpSystolicObs, huyetApUnit, huyetApCao);
            
            //Huyết áp trị thấp
            var bpDiastolicComponent = CDAFactory.eINSTANCE.createComponent4();
            bpDiastolicComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            vitalSingsOrganizer.getComponents().add(bpDiastolicComponent);
            
            var bpDiastolicObs = HSBAFactory.eINSTANCE.createHsbaBPDiastolicObservation().init();
            bpDiastolicComponent.setObservation(bpDiastolicObs);
            Integer huyetApThap = emrBenhAn.huyetapthap;
            addPqObservationValueTag(bpDiastolicObs, huyetApUnit, huyetApThap);
            if(huyetApCao != null && huyetApThap != null){
                vitalSignsText.append(properties.getProperty("BA_DHST_HUYETAP_TITLE", "BA_DHST_HUYETAP_TITLE")).append(sectionContentDelimiter).append(huyetApCao)
                          .append(properties.getProperty("VITALSIGNS_BP_SPLIT", "VITALSIGNS_BP_SPLIT")).append(huyetApThap).append(huyetApUnit).append(splitDelimiter);
            }
            
            //Nhịp thở
            var respiratoryRateComponent = CDAFactory.eINSTANCE.createComponent4();
            respiratoryRateComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            vitalSingsOrganizer.getComponents().add(respiratoryRateComponent);
            
            var respiratoryRateObs = HSBAFactory.eINSTANCE.createHsbaRespiratoryRateObservation().init();
            respiratoryRateComponent.setObservation(respiratoryRateObs);
            String nhipThoUnit = properties.getProperty("BA_DHST_NHIPTHO_UNIT", "BA_DHST_NHIPTHO_UNIT");
            Integer nhipTho = emrBenhAn.nhiptho;
            addPqObservationValueTag(respiratoryRateObs, nhipThoUnit, nhipTho);
            if(nhipTho != null){
                vitalSignsText.append(properties.getProperty("BA_DHST_NHIPTHO_TITLE", "BA_DHST_NHIPTHO_TITLE")).append(sectionContentDelimiter).append(nhipTho).append(nhipThoUnit).append(splitDelimiter);
            }
            
            //Cân nặng
            var weightMeasuredComponent = CDAFactory.eINSTANCE.createComponent4();
            weightMeasuredComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            vitalSingsOrganizer.getComponents().add(weightMeasuredComponent);
            
            var weightMeasuredObs = HSBAFactory.eINSTANCE.createHsbaWeightMeasuredObservation().init();
            weightMeasuredComponent.setObservation(weightMeasuredObs);
            String canNangUnit = properties.getProperty("BA_DHST_CANNANG_UNIT", "BA_DHST_CANNANG_UNIT");
            Integer canNang = (int)(double) emrBenhAn.cannang;
            addPqObservationValueTag(weightMeasuredObs, canNangUnit, canNang);
            if(canNang != null){
                vitalSignsText.append(properties.getProperty("BA_DHST_CANNANG_TITLE", "BA_DHST_CANNANG_TITLE")).append(sectionContentDelimiter).append(canNang).append(canNangUnit).append(splitDelimiter);
            }
            
            //Chu vi đầu
            var headCircumferenceComponent = CDAFactory.eINSTANCE.createComponent4();
            weightMeasuredComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            vitalSingsOrganizer.getComponents().add(headCircumferenceComponent);
            
            var headCircumferenceObs = HSBAFactory.eINSTANCE.createHsbaHeadCircumferenceObservation().init();
            headCircumferenceComponent.setObservation(headCircumferenceObs);
            String chuViDauUnit = properties.getProperty("BA_DHST_CHUVIDAU_UNIT", "BA_DHST_CHUVIDAU_UNIT");
            Integer chuViDau = emrBenhAn.vongdau;
            addPqObservationValueTag(headCircumferenceObs, chuViDauUnit, chuViDau);
            if(chuViDau != null){
                vitalSignsText.append(properties.getProperty("BA_DHST_CHUVIDAU_TITLE", "BA_DHST_CHUVIDAU_TITLE")).append(sectionContentDelimiter).append(chuViDau).append(chuViDauUnit).append(splitDelimiter);
            }
            
            //Chiều cao
            var heightComponent = CDAFactory.eINSTANCE.createComponent4();
            heightComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            vitalSingsOrganizer.getComponents().add(heightComponent);
            
            var heightObs = HSBAFactory.eINSTANCE.createHsbaHeightObservation().init();
            heightComponent.setObservation(heightObs);
            String chieucaoUnit = properties.getProperty("BA_DHST_CHIEUCAO_UNIT", "BA_DHST_CHIEUCAO_UNIT");
            Integer chieuCao = emrBenhAn.chieucao;
            addPqObservationValueTag(heightObs, chieucaoUnit, chieuCao);
            if(chieuCao != null){
                vitalSignsText.append(properties.getProperty("BA_DHST_CHIEUCAO_TITLE", "BA_DHST_CHIEUCAO_TITLE")).append(sectionContentDelimiter).append(chieuCao).append(chieucaoUnit).append(splitDelimiter);
            }
            
            //Vòng ngực
            var breastsComponent = CDAFactory.eINSTANCE.createComponent4();
            breastsComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            vitalSingsOrganizer.getComponents().add(breastsComponent);
            
            var breastsObs = HSBAFactory.eINSTANCE.createHsbaBreastsCircumferenceObservation().init();
            breastsComponent.setObservation(breastsObs);
            String vongngucUnit = properties.getProperty("BA_DHST_VONGNGUC_UNIT", "BA_DHST_VONGNGUC_UNIT");
            Integer vongNguc = emrBenhAn.vongnguc;
            addPqObservationValueTag(breastsObs, vongngucUnit, vongNguc);
            if(vongNguc != null){
                vitalSignsText.append(properties.getProperty("BA_DHST_VONGNGUC_TITLE", "BA_DHST_VONGNGUC_TITLE")).append(sectionContentDelimiter).append(vongNguc).append(vongngucUnit).append(splitDelimiter);
            }
                
            if(vitalSignsText.length() > 0){
                vitalSignsText.setLength(vitalSignsText.length() - 1);
            }
            CDAExportUtil.setSectionData(vitalSignsSection, vitalSignsText.toString());  
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addInjuryObservationMedia(HsbaInjuryIncidentDescriptionSection injurySection, EmrHinhAnhTonThuong hinhAnh, StringBuilder injuryIncidentDescText, Properties properties, String srcLocalFolder){
        try {
            var lstEmrQuanLyFileDinhKemHatt = hinhAnh.emrFileDinhKemHatts;
            
            var injuryIncidentAct = HSBAFactory.eINSTANCE.createHsbaInjuryIncidentAct().init();
            II injyryIncidentActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            injuryIncidentAct.getIds().add(injyryIncidentActId);
            injurySection.addAct(injuryIncidentAct);

            if(lstEmrQuanLyFileDinhKemHatt != null && lstEmrQuanLyFileDinhKemHatt.size() > 0){
                for (var hattFile : lstEmrQuanLyFileDinhKemHatt) {
                    String fileUrl = hattFile.url;
                    String tenAnh = hinhAnh.anhtonthuong;
                    String motaAnh = hinhAnh.motatonthuong;
                    
                    // tên ảnh tổn thương
                    if(!StringUtils.isEmpty(tenAnh)){                            
                        injuryIncidentAct.getCode().setDisplayName(tenAnh.replaceAll("(\\r\\n|\\n)", " "));
                    }
                    
                    // mô tả ảnh tổn thương
                    if(!StringUtils.isEmpty(motaAnh)){
                        injuryIncidentAct.setText(DatatypesFactory.eINSTANCE.createED(motaAnh));
                    }
                    
                    //add file đính kèm
                    CDAExportUtil.addActExternalDocument(injuryIncidentAct, fileUrl);
                    
                    /*String extension = "";
                    StringBuilder mediaType = new StringBuilder();
                    StringBuilder filePath = new StringBuilder();
                    
                    if(fileUrl != null){
                        int i = fileUrl.lastIndexOf(".");
                        if(i > 0){
                            extension = fileUrl.substring(i+1);
                        }
                        boolean isImage = validateImg(fileUrl);
                        //add observation media
                        if(isImage){
                            filePath.append(srcLocalFolder).append(File.separator).append(fileUrl);
                            File file = new File(filePath.toString());
                            if(file != null){
                                FileInputStream fis = new FileInputStream(file);
                                byte fileData[] = new byte[(int) file.length()];
                                fis.read(fileData);
                             
                                String base64Text = new BASE64Encoder().encode(fileData);
                                if(!StringUtils.isEmpty(base64Text)){
                                    EntryRelationship injuryIncidentER = CDAFactory.eINSTANCE.createEntryRelationship();
                                    injuryIncidentER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                                    injuryIncidentAct.getEntryRelationships().add(injuryIncidentER);
                                    
                                    HsbaInjuryObservationMedia injuryObservationMedia = HSBAFactory.eINSTANCE.createHsbaInjuryObservationMedia().init();
                                    injuryIncidentER.setObservationMedia(injuryObservationMedia);
                                    ED valueTag = DatatypesFactory.eINSTANCE.createED();
                                    String prefixMediaType = properties.getProperty("IMG_MEDIA_TYPE", "IMG_MEDIA_TYPE");
                                    mediaType.append(prefixMediaType).append(extension);
                                    valueTag.setMediaType(mediaType.toString());
                                    valueTag.setRepresentation(BinaryDataEncoding.B64);
                
                                    valueTag.addText(base64Text);
                                    injuryObservationMedia.setValue(valueTag);                                                              
                                }
                            }
                        }
                    }*/
                }           
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addDiagnosisObservations(Section section, EmrBenhAn emrBenhAn, Map<String, String> emrParameters, String splitDelimiter, Properties properties){     
        try {
            StringBuilder diagnosisNarrativeText = new StringBuilder();     
            String benhchinhTitle = properties.getProperty("CD_RAVIEN_BENHCHINH_TITLE", "CD_RAVIEN_BENHCHINH_TITLE");
            String benhkemtheoTitle = properties.getProperty("CD_RAVIEN_BENHKEMTHEO_TITLE", "CD_RAVIEN_BENHKEMTHEO_TITLE");
            String phanbietTitle = properties.getProperty("CD_PHANBIET_TITLE", "CD_PHANBIET_TITLE");        
            String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
            String icd10CodeSystem = emrParameters.get("emr_dm_ma_benh");               
            
            var inputDepartmentObs = HSBAFactory.eINSTANCE.createHsbaInputDepartmentDiagnosisObservation().init();
            section.addObservation(inputDepartmentObs);     
            
            //Bệnh chính    
            var priminaryER = CDAFactory.eINSTANCE.createEntryRelationship();
            priminaryER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            inputDepartmentObs.getEntryRelationships().add(priminaryER);
            
            var primaryDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init(); 
            priminaryER.setObservation(primaryDiagnosisObs);        
            CDAExportUtil.addDiagnosisObservationContent(primaryDiagnosisObs, benhchinhTitle, contentDelimiter, splitDelimiter, emrBenhAn.motachandoanbenhchinh, emrBenhAn.emrDmMaBenhChandoanbenhchinh, icd10CodeSystem, emrParameters, diagnosisNarrativeText);
            
            //Bệnh kèm theo
            var secondaryER = CDAFactory.eINSTANCE.createEntryRelationship();
            secondaryER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            inputDepartmentObs.getEntryRelationships().add(secondaryER);
            
            var secondaryDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaSecondaryDiagnosisObservation().init();   
            secondaryER.setObservation(secondaryDiagnosisObs);      
            CDAExportUtil.addDiagnosisObservationContent(secondaryDiagnosisObs, benhkemtheoTitle, contentDelimiter, splitDelimiter, emrBenhAn.motachandoankemtheo, emrBenhAn.emrDmMaBenhChandoankemtheo, icd10CodeSystem, emrParameters, diagnosisNarrativeText);
            
            //Phân biệt
            var complaintER = CDAFactory.eINSTANCE.createEntryRelationship();
            complaintER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            inputDepartmentObs.getEntryRelationships().add(complaintER);
            
            var complaintDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaComplaintDiagnosisObservation().init();
            complaintER.setObservation(complaintDiagnosisObs);
            CDAExportUtil.addDiagnosisObservationContent(complaintDiagnosisObs, phanbietTitle, contentDelimiter, splitDelimiter, emrBenhAn.motachandoanphanbiet, emrBenhAn.emrDmMaBenhChandoanphanbiet, icd10CodeSystem, emrParameters, diagnosisNarrativeText);                        
            
            if(diagnosisNarrativeText.length() > 0){
                diagnosisNarrativeText.setLength(diagnosisNarrativeText.length() - 1);
            }
            CDAExportUtil.setSectionData(section, diagnosisNarrativeText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }               
    }
    
    
    public static void addCkTuanHoan(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var cardiovascularSystemSection = HSBAFactory.eINSTANCE.createHsbaCardiovascularSystemSection().init();
            physicalFindingsSection.addSection(cardiovascularSystemSection);
            String tuanHoanTitle = properties.getProperty("BA_TUANHOAN_TITLE", "BA_TUANHOAN_TITLE");
            CDAExportUtil.addSectionTitle(cardiovascularSystemSection, tuanHoanTitle);

            var emrCkTuanHoan = emrBenhAn.emrCkTuanHoan;
            if(emrCkTuanHoan != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTuanHoan); //HSBAUtil.convertObjectToJson(EmrCkTuanHoan.class, emrCkTuanHoan);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    cardiovascularSystemSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String tuanHoan = emrBenhAn.tuanhoan;
            CDAExportUtil.setSectionData(cardiovascularSystemSection, tuanHoan);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addCkHoHap(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var respiratorySystemSection = HSBAFactory.eINSTANCE.createHsbaRespiratorySystemSection().init();
            physicalFindingsSection.addSection(respiratorySystemSection);
            String hoHapTitle = properties.getProperty("BA_HOHAP_TITLE", "BA_HOHAP_TITLE");
            CDAExportUtil.addSectionTitle(respiratorySystemSection, hoHapTitle);
            
            var emrCkHoHap = emrBenhAn.emrCkHoHap;
            if(emrCkHoHap != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkHoHap); //HSBAUtil.convertObjectToJson(EmrCkHoHap.class, emrCkHoHap);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    respiratorySystemSection.addObservation(hsbaJsonObs);
                }   
            }       

            String hoHap = emrBenhAn.hohap;
            CDAExportUtil.setSectionData(respiratorySystemSection, hoHap);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addCkTieuHoa(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var gastrointestinalSection = HSBAFactory.eINSTANCE.createHsbaGastrointestinalSystemSection().init();
            physicalFindingsSection.addSection(gastrointestinalSection);
            String tieHoaTitle = properties.getProperty("BA_TIEUHOA_TITLE", "BA_TIEUHOA_TITLE");
            CDAExportUtil.addSectionTitle(gastrointestinalSection, tieHoaTitle);

            var emrCkTieuHoa = emrBenhAn.emrCkTieuHoa;
            if(emrCkTieuHoa != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTieuHoa);//HSBAUtil.convertObjectToJson(EmrCkTieuHoa.class, emrCkTieuHoa);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    gastrointestinalSection.addObservation(hsbaJsonObs);
                }   
            }   
            
            String tieuHoa = emrBenhAn.tieuhoa;
            CDAExportUtil.setSectionData(gastrointestinalSection, tieuHoa);      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
    
    public static void addCkTietNieuSinhDuc(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var genitourinarySection = HSBAFactory.eINSTANCE.createHsbaGenitourinaryTractSection().init();
            physicalFindingsSection.addSection(genitourinarySection);
            String tietNieuSDTitle = properties.getProperty("BA_TIETNIEUSINHDUC_TITLE", "BA_TIETNIEUSINHDUC_TITLE");
            CDAExportUtil.addSectionTitle(genitourinarySection, tietNieuSDTitle);

            String tietNieuSd = emrBenhAn.tietnieusinhduc;
            CDAExportUtil.setSectionData(genitourinarySection, tietNieuSd);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addCkThanKinh(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var neutrologicSection = HSBAFactory.eINSTANCE.createHsbaNeurologicSystemSection().init();
            physicalFindingsSection.addSection(neutrologicSection);
            String thanKinhTitle = properties.getProperty("BA_THANKINH_TITLE", "BA_THANKINH_TITLE");
            CDAExportUtil.addSectionTitle(neutrologicSection, thanKinhTitle);
            
            var emrCkThanKinh = emrBenhAn.emrCkThanKinh;
            if(emrCkThanKinh != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkThanKinh);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    neutrologicSection.addObservation(hsbaJsonObs);
                }   
            }   

            String thanKinh = emrBenhAn.thankinh;
            CDAExportUtil.setSectionData(neutrologicSection, thanKinh);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addCkCoXuongKhop(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var musculoskeletalSection = HSBAFactory.eINSTANCE.createHsbaMusculoskeletalSystemSection().init();
            physicalFindingsSection.addSection(musculoskeletalSection);
            String coXuongKhopTitle = properties.getProperty("BA_COXUONGKHOP_TITLE", "BA_COXUONGKHOP_TITLE");
            CDAExportUtil.addSectionTitle(musculoskeletalSection, coXuongKhopTitle);

            var emrCkCoXuongKhop = emrBenhAn.emrCkCoXuongKhop;
            if(emrCkCoXuongKhop != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkCoXuongKhop);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    musculoskeletalSection.addObservation(hsbaJsonObs);
                }   
            }   
            
            String coXuongKhop = emrBenhAn.coxuongkhop;
            CDAExportUtil.setSectionData(musculoskeletalSection, coXuongKhop);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addCkTaiMuiHong(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var earsNoseThroatSection = HSBAFactory.eINSTANCE.createHsbaEearsNoseThroatSection().init();
            physicalFindingsSection.addSection(earsNoseThroatSection);
            String taiMuiHongTitle = properties.getProperty("BA_TAIMUIHONG_TITLE", "BA_TAIMUIHONG_TITLE");
            CDAExportUtil.addSectionTitle(earsNoseThroatSection, taiMuiHongTitle);

            String taiMuiHong = emrBenhAn.taimuihong;
            CDAExportUtil.setSectionData(earsNoseThroatSection, taiMuiHong);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    /**
     * Thêm thông tin chuyên khoa Răng hàm mặt
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkRangHamMat(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var mouthThroatTeethSection = HSBAFactory.eINSTANCE.createHsbaMouthThroatTeethSection().init();
            physicalFindingsSection.addSection(mouthThroatTeethSection);
            String rangHamMatTitle = properties.getProperty("BA_RANGHAMMAT_TITLE", "BA_RANGHAMMAT_TITLE");
            CDAExportUtil.addSectionTitle(mouthThroatTeethSection, rangHamMatTitle);

            String rangHamMat = emrBenhAn.ranghammat;
            CDAExportUtil.setSectionData(mouthThroatTeethSection, rangHamMat);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    /**
     * Thêm thông tin chuyên khoa Nội tiết dinh dưỡng
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkNoiTietDinhDuong(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var endocrineSection = HSBAFactory.eINSTANCE.createHsbaEndocrineSection().init();
            physicalFindingsSection.addSection(endocrineSection);
            String noiTietDinhDuongTitle = properties.getProperty("BA_NOITIETDINHDUONG_TITLE", "BA_NOITIETDINHDUONG_TITLE");
            CDAExportUtil.addSectionTitle(endocrineSection, noiTietDinhDuongTitle);

            String noiTietDinhDuong = emrBenhAn.noitietdinhduong;
            CDAExportUtil.setSectionData(endocrineSection, noiTietDinhDuong);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }

    /**
     * Thêm thông tin chuyên khoa Da liễu
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkDaLieu(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var skinSection = HSBAFactory.eINSTANCE.createHsbaSkinSection().init();
            physicalFindingsSection.addSection(skinSection);
            String daLieuTitle = properties.getProperty("BA_DALIEU_TITLE", "BA_DALIEU_TITLE");
            CDAExportUtil.addSectionTitle(skinSection, daLieuTitle);
            
            String daLieu = emrBenhAn.dalieu;
            CDAExportUtil.setSectionData(skinSection, daLieu);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    /**
     * Thêm thông tin chuyên khoa Mắt
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkMat(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var eyeSection = HSBAFactory.eINSTANCE.createHsbaEyeSection().init();
            physicalFindingsSection.addSection(eyeSection);
            String eyeTitle = properties.getProperty("BA_MAT_TITLE", "BA_MAT_TITLE");
            CDAExportUtil.addSectionTitle(eyeSection, eyeTitle);

            var emrCkMat = emrBenhAn.emrCkMat;
            if(emrCkMat != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkMat);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    eyeSection.addObservation(hsbaJsonObs);
                }   
            }   
            
            String mat = emrBenhAn.mat;
            CDAExportUtil.setSectionData(eyeSection, mat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thêm thông tin chuyên khoa Tâm thần
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkTamThan(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var psychiatricSection = HSBAFactory.eINSTANCE.createHsbaPsychiatricSection().init();
            physicalFindingsSection.addSection(psychiatricSection);
            String tamThanTitle = properties.getProperty("BA_TAMTHAN_TITLE", "BA_TAMTHAN_TITLE");
            CDAExportUtil.addSectionTitle(psychiatricSection, tamThanTitle);

            var emrCkTamThan = emrBenhAn.emrCkTamThan;
            if(emrCkTamThan != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTamThan);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    psychiatricSection.addObservation(hsbaJsonObs);
                }   
            }   
            
            String tamThan = emrBenhAn.tamthan;
            CDAExportUtil.setSectionData(psychiatricSection, tamThan);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    /**
     * Thêm thông tin chuyên khoa Huyết học
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkHuyetHoc(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var hematologicSection = HSBAFactory.eINSTANCE.createHsbaHematologicSection().init();
            physicalFindingsSection.addSection(hematologicSection);
            String huyetHocTitle = properties.getProperty("BA_HUYETHOC_TITLE", "BA_HUYETHOC_TITLE");
            CDAExportUtil.addSectionTitle(hematologicSection, huyetHocTitle);
            
            var emrCkHuyetHoc = emrBenhAn.emrCkHuongDieuTriHuyetHoc;
            if(emrCkHuyetHoc != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkHuyetHoc);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    hematologicSection.addObservation(hsbaJsonObs);
                }   
            }   
            
            String huyetHoc = emrBenhAn.huyethoc;
            CDAExportUtil.setSectionData(hematologicSection, huyetHoc);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addCkTienSuPhuKhoa(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var tienSuPhuKhoaSection = HSBAFactory.eINSTANCE.createHsbaCkTienSuPhuKhoaSection().init();
            physicalFindingsSection.addSection(tienSuPhuKhoaSection);
            String tienSuPhuKhoaTitle = properties.getProperty("BA_TIENSUPHUKHOA_TITLE", "BA_TIENSUPHUKHOA_TITLE");
            CDAExportUtil.addSectionTitle(tienSuPhuKhoaSection, tienSuPhuKhoaTitle);
            
            var emrCkTienSuPhuKhoa = emrBenhAn.emrCkTienSuPhuKhoa;
            if(emrCkTienSuPhuKhoa != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTienSuPhuKhoa);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    tienSuPhuKhoaSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String tienSuPhuKhoa = emrBenhAn.tiensuphukhoa;
            CDAExportUtil.setSectionData(tienSuPhuKhoaSection, tienSuPhuKhoa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Thêm thông tin chuyên khoa Tiền sử sản khoa
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkTienSuSanKhoa(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var tienSuSanKhoaSection = HSBAFactory.eINSTANCE.createHsbaCkTienSuSanKhoaSection().init();
            physicalFindingsSection.addSection(tienSuSanKhoaSection);
            String tienSuSanKhoaTitle = properties.getProperty("BA_TIENSUSANKHOA_TITLE", "BA_TIENSUSANKHOA_TITLE");
            CDAExportUtil.addSectionTitle(tienSuSanKhoaSection, tienSuSanKhoaTitle);
            
            var emrCkTienSuSanKhoa = emrBenhAn.emrCkTienSuSanKhoa;
            if(emrCkTienSuSanKhoa != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTienSuSanKhoa);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    tienSuSanKhoaSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String tienSuSanKhoa = emrBenhAn.tiensusankhoa;
            CDAExportUtil.setSectionData(tienSuSanKhoaSection, tienSuSanKhoa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Thêm thông tin chuyên khoa Khám sản khoa
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkKhamSanKhoa(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var khamSanKhoaSection = HSBAFactory.eINSTANCE.createHsbaCkKhamSanKhoaSection().init();
            physicalFindingsSection.addSection(khamSanKhoaSection);
            String khamSanKhoaTitle = properties.getProperty("BA_KHAMSANKHOA_TITLE", "BA_KHAMSANKHOA_TITLE");
            CDAExportUtil.addSectionTitle(khamSanKhoaSection, khamSanKhoaTitle);
            
            var emrCkKhamSanKhoa = emrBenhAn.emrCkKhamSanKhoa;
            if(emrCkKhamSanKhoa != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkKhamSanKhoa);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    khamSanKhoaSection.addObservation(hsbaJsonObs);
                }   
            }
                    
            String khamSanKhoa = emrBenhAn.khamsankhoa;
            CDAExportUtil.setSectionData(khamSanKhoaSection, khamSanKhoa);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    /**
     * Thêm thông tin chuyên khoa Tiền sử bản thân sản khoa
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkTienSuBanThanSanKhoa(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var tienSuBanThanSanKhoaSection = HSBAFactory.eINSTANCE.createHsbaCkTienSuBanThanSanKhoaSection().init();
            physicalFindingsSection.addSection(tienSuBanThanSanKhoaSection);
            String tienSuBanThanSanKhoaTitle = properties.getProperty("BA_TIENSUBANTHANSANKHOA_TITLE", "BA_TIENSUBANTHANSANKHOA_TITLE");
            CDAExportUtil.addSectionTitle(tienSuBanThanSanKhoaSection, tienSuBanThanSanKhoaTitle);
            
            var emrCkTienSuBanThanSanKhoa = emrBenhAn.emrCkTienSuBanThanSanKhoa;
            if(emrCkTienSuBanThanSanKhoa != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTienSuBanThanSanKhoa);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    tienSuBanThanSanKhoaSection.addObservation(hsbaJsonObs);
                }   
            }
                    
            String tienSuBanThanSanKhoa = emrBenhAn.tiensubanthan;
            CDAExportUtil.setSectionData(tienSuBanThanSanKhoaSection, tienSuBanThanSanKhoa);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    /**
     * Thêm thông tin chuyên khoa Tiền sử Gia đình
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkTienSuGiaDinh(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var ckTienSuGiaDinhSection = HSBAFactory.eINSTANCE.createHsbaCkTienSuGiaDinhSection().init();
            physicalFindingsSection.addSection(ckTienSuGiaDinhSection);
            String tienSuGiaDinhTitle = properties.getProperty("BA_CKTIENSUGIADINH_TITLE", "BA_CKTIENSUGIADINH_TITLE");
            CDAExportUtil.addSectionTitle(ckTienSuGiaDinhSection, tienSuGiaDinhTitle);
            
            var emrCkTienSuGiaDinh = emrBenhAn.emrCkTienSuGiaDinh;
            if(emrCkTienSuGiaDinh != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTienSuGiaDinh);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    ckTienSuGiaDinhSection.addObservation(hsbaJsonObs);
                }   
            }
                    
            String tienSuGiaDinh = emrBenhAn.tiensugiadinh;
            CDAExportUtil.setSectionData(ckTienSuGiaDinhSection, tienSuGiaDinh);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    /**
     * Thêm thông tin chuyên khoa Tình trạng sản phụ (Pstw)
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkSkTinhTrangSanPhu(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var ckSkTinhTrangSanPhuSection = HSBAFactory.eINSTANCE.createHsbaCkSkTinhTrangSanPhuSection().init();
            physicalFindingsSection.addSection(ckSkTinhTrangSanPhuSection);
            String skTinhTrangSanPhuTitle = properties.getProperty("BA_CKSK_TINHTRANGSANPHU_TITLE", "BA_CKSK_TINHTRANGSANPHU_TITLE");
            CDAExportUtil.addSectionTitle(ckSkTinhTrangSanPhuSection, skTinhTrangSanPhuTitle);
            
            var emrCkSkTinhTrangSanPhu = emrBenhAn.emrCkSkTinhTrangSanPhu;
            if(emrCkSkTinhTrangSanPhu != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkSkTinhTrangSanPhu);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    ckSkTinhTrangSanPhuSection.addObservation(hsbaJsonObs);
                }   
            }
                    
            String skTinhTrangSanPhu = emrBenhAn.tinhtrangsanphu;
            CDAExportUtil.setSectionData(ckSkTinhTrangSanPhuSection, skTinhTrangSanPhu);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    /**
     * Thêm thông tin chuyên khoa Khám sơ sinh
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkKhamSoSinh(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var khamSoSinhSection = HSBAFactory.eINSTANCE.createHsbaCkKhamSoSinhSection().init();
            physicalFindingsSection.addSection(khamSoSinhSection);
            String khamSoSinhTitle = properties.getProperty("BA_KHAMSOSINH_TITLE", "BA_KHAMSOSINH_TITLE");
            CDAExportUtil.addSectionTitle(khamSoSinhSection, khamSoSinhTitle);
            
            var emrCkKhamSoSinh = emrBenhAn.emrCkKhamSoSinh;;
            if(emrCkKhamSoSinh != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkKhamSoSinh);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    khamSoSinhSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String khamSoSinh = emrBenhAn.khamsosinh;
            CDAExportUtil.setSectionData(khamSoSinhSection, khamSoSinh);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    /**
     * Thêm thông tin chuyên khoa Khám phụ khoa
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkKhamPhuKhoa(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var khamPhuKhoaSection = HSBAFactory.eINSTANCE.createHsbaCkKhamPhuKhoaSection().init();
            physicalFindingsSection.addSection(khamPhuKhoaSection);
            String khamPhuKhoaTitle = properties.getProperty("BA_KHAMPHUKHOA_TITLE", "BA_KHAMPHUKHOA_TITLE");
            CDAExportUtil.addSectionTitle(khamPhuKhoaSection, khamPhuKhoaTitle);
                
            var emrCkKhamPhuKhoa = emrBenhAn.emrCkKhamPhuKhoa;
            if(emrCkKhamPhuKhoa != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkKhamPhuKhoa);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    khamPhuKhoaSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String khamPhuKhoa = emrBenhAn.khamphukhoa;
            CDAExportUtil.setSectionData(khamPhuKhoaSection, khamPhuKhoa);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    /**
     * Thêm thông tin chuyên khoa Quá trình bệnh lý - Tay chân miệng
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkQuaTrinhBenhLyTcm(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
        var quaTrinhBenhLyTcmSection = HSBAFactory.eINSTANCE.createHsbaCkQuaTrinhBenhLyTcmSection().init();
            physicalFindingsSection.addSection(quaTrinhBenhLyTcmSection);
            String qtblTcmTitle = properties.getProperty("BA_QUATRINHBENHLY_TCM_TITLE", "BA_QUATRINHBENHLY_TCM_TITLE");
            CDAExportUtil.addSectionTitle(quaTrinhBenhLyTcmSection, qtblTcmTitle);
                
            var emrCkquaTrinhBenhLyTcm = emrBenhAn.emrCkQuaTrinhBenhLyTcm;
            if(emrCkquaTrinhBenhLyTcm != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkquaTrinhBenhLyTcm);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    quaTrinhBenhLyTcmSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String qtblTayChanMieng = emrBenhAn.quatrinhbenhly;
            CDAExportUtil.setSectionData(quaTrinhBenhLyTcmSection, qtblTayChanMieng);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }   
    
    /**
     * Thêm thông tin chuyên khoa Chân tay miệng
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkChanTayMieng(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
        var chanTayMiengSection = HSBAFactory.eINSTANCE.createHsbaCkChanTayMiengSection().init();
            physicalFindingsSection.addSection(chanTayMiengSection);
            String chanTayMiengTitle = properties.getProperty("BA_CHANTAYMIENG_TITLE", "BA_CHANTAYMIENG_TITLE");
            CDAExportUtil.addSectionTitle(chanTayMiengSection, chanTayMiengTitle);
                
            var emrCkChanTayMieng = emrBenhAn.emrCkChanTayMieng;
            if(emrCkChanTayMieng != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkChanTayMieng);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    chanTayMiengSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String chanTayMieng = emrBenhAn.taychanmieng;
            CDAExportUtil.setSectionData(chanTayMiengSection, chanTayMieng);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    /**
     * Thêm thông tin chuyên khoa Hướng điều trị (Tay chân miệng)
     * @param physicalFindingsSection
     * @param properties
     * @param emrBenhAn
     */
    public static void addCkHuongDieuTriTcm(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var huongDieuTriTcmSection = HSBAFactory.eINSTANCE.createHsbaCkHuongDieuTriTcmSection().init();
            physicalFindingsSection.addSection(huongDieuTriTcmSection);
            String huongDieuTriTcmTitle = properties.getProperty("BA_HUONGDIEUTRI_TCM_TITLE", "BA_HUONGDIEUTRI_TCM_TITLE");
            CDAExportUtil.addSectionTitle(huongDieuTriTcmSection, huongDieuTriTcmTitle);
                
            var emrCkHuongDieuTriTcm = emrBenhAn.emrCkHuongDieuTriTcm;
            if(emrCkHuongDieuTriTcm != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkHuongDieuTriTcm);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    huongDieuTriTcmSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String huongDieuTriTcm = emrBenhAn.huongdieutri;
            CDAExportUtil.setSectionData(huongDieuTriTcmSection, huongDieuTriTcm);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addCkTomTatBenhAnTcm(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var tomTatBenhAnTcmSection = HSBAFactory.eINSTANCE.createHsbaCkTomTatBenhAnTcmSection().init();
            physicalFindingsSection.addSection(tomTatBenhAnTcmSection);
            String tomTatBaTcmTitle = properties.getProperty("BA_TOMTATBENHAN_TCM_TITLE", "BA_TOMTATBENHAN_TCM_TITLE");
            CDAExportUtil.addSectionTitle(tomTatBenhAnTcmSection, tomTatBaTcmTitle);
                
            var emrCkTomTatBenhAnTcm = emrBenhAn.emrCkTomTatBenhAnTcm;
            if(emrCkTomTatBenhAnTcm != null){
                var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
                ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
                String jsonText = JsonUtil.dumpObject(emrCkTomTatBenhAnTcm);
                if(!StringUtils.isEmpty(jsonText)){
                    jsonObsText.addText(jsonText);
                    hsbaJsonObs.setText(jsonObsText);
                    tomTatBenhAnTcmSection.addObservation(hsbaJsonObs);
                }   
            }
            
            String tomTatBenhAnTcm = emrBenhAn.tomtat;
            CDAExportUtil.setSectionData(tomTatBenhAnTcmSection, tomTatBenhAnTcm);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addCoQuanKhac(HsbaPhysicalFindingsSection physicalFindingsSection, Properties properties, EmrBenhAn emrBenhAn){
        try {
            var coQuanKhacSection = HSBAFactory.eINSTANCE.createHsbaCkCoQuanKhacSection().init();
            physicalFindingsSection.addSection(coQuanKhacSection);
            String coQuanKhacTitle = properties.getProperty("BA_COQUANKHAC_TITLE", "BA_COQUANKHAC_TITLE");
            CDAExportUtil.addSectionTitle(coQuanKhacSection, coQuanKhacTitle);
                
            String coQuanKhac = emrBenhAn.coquankhac;
                    
            CDAExportUtil.setSectionData(coQuanKhacSection, coQuanKhac);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
        
    public static void addPhysicalFindingsSection(HsbaHistoryAndPhysicalNoteSection historyAndPhysicalNoteSection, EmrBenhAn emrBenhAn, String sectionContentDelimiter, String splitDelimiter, Properties properties){
        var physicalFindingsSection = HSBAFactory.eINSTANCE.createHsbaPhysicalFindingsSection().init();
        historyAndPhysicalNoteSection.addSection(physicalFindingsSection);
        String khamBoPhanTitle = properties.getProperty("BA_KHAMCACBOPHAN_TITLE", "BA_KHAMCACBOPHAN_TITLE");
        CDAExportUtil.addSectionTitle(physicalFindingsSection, khamBoPhanTitle);
        
        StringBuilder physicalFindingsText = new StringBuilder();
        
        //Tuần hoàn
        addCkTuanHoan(physicalFindingsSection, properties, emrBenhAn);
        
        //Hô hấp
        addCkHoHap(physicalFindingsSection, properties, emrBenhAn);
    
        //Tiêu hóa
        addCkTieuHoa(physicalFindingsSection, properties, emrBenhAn);       
        
        //Tiết niệu - sinh dục  
        addCkTietNieuSinhDuc(physicalFindingsSection, properties, emrBenhAn);       

        //Thần kinh
        addCkThanKinh(physicalFindingsSection, properties, emrBenhAn);
        
        //Cơ xương khớp
        addCkCoXuongKhop(physicalFindingsSection, properties, emrBenhAn);
        
        //Tai mũi họng
        addCkTaiMuiHong(physicalFindingsSection, properties, emrBenhAn);
        
        //Răng hàm mặt
        addCkRangHamMat(physicalFindingsSection, properties, emrBenhAn);
        
        //Nội tiết, dinh dưỡng
        addCkNoiTietDinhDuong(physicalFindingsSection, properties, emrBenhAn);
        
        //Da liễu
        addCkDaLieu(physicalFindingsSection, properties, emrBenhAn);
        
        //Mắt
        addCkMat(physicalFindingsSection, properties, emrBenhAn);
    
        //Tâm thần
        addCkTamThan(physicalFindingsSection, properties, emrBenhAn);
        
        //Huyết học
        addCkHuyetHoc(physicalFindingsSection, properties, emrBenhAn);      
        
        //Tiền sử phụ khoa
        addCkTienSuPhuKhoa(physicalFindingsSection, properties, emrBenhAn);
        
        //Tiền sử sản khoa
        addCkTienSuSanKhoa(physicalFindingsSection, properties, emrBenhAn);
        
        //Khám sản khoa
        addCkKhamSanKhoa(physicalFindingsSection, properties, emrBenhAn);
        
        //Tiền sử bản thân sản khoa
        addCkTienSuBanThanSanKhoa(physicalFindingsSection, properties, emrBenhAn);
        
        //Tiền sử Gia đình
        addCkTienSuGiaDinh(physicalFindingsSection, properties, emrBenhAn);
        
        //Tình trạng sản phụ (Pstw)
        addCkSkTinhTrangSanPhu(physicalFindingsSection, properties, emrBenhAn);
        
        //Khám sơ sinh
        addCkKhamSoSinh(physicalFindingsSection, properties, emrBenhAn);
        
        //Khám phụ khoa
        addCkKhamPhuKhoa(physicalFindingsSection, properties, emrBenhAn);
        
        //Quá trình bệnh lý - Tay chân miệng
        addCkQuaTrinhBenhLyTcm(physicalFindingsSection, properties, emrBenhAn);
        
        //Chân tay miệng
        addCkChanTayMieng(physicalFindingsSection, properties, emrBenhAn);
        
        //Tóm tắt bệnh án - Tay chân miệng
        addCkTomTatBenhAnTcm(physicalFindingsSection, properties, emrBenhAn);
        
        //Hướng điều trị - Tay chân miệng
        addCkHuongDieuTriTcm(physicalFindingsSection, properties, emrBenhAn);
        
        //Các cơ quan khác
        addCoQuanKhac(physicalFindingsSection, properties, emrBenhAn);
        
        if(physicalFindingsText.length() > 0){
            physicalFindingsText.setLength(physicalFindingsText.length() - 1);
        }

        CDAExportUtil.setSectionData(physicalFindingsSection, physicalFindingsText.toString());   
    }
    
}
