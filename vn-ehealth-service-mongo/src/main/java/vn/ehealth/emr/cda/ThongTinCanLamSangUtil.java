package vn.ehealth.emr.cda;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.cda.AssignedEntity;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntry;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntryRelationship;
import org.springframework.util.StringUtils;

import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HSBAFactory;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaDocument;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaRelevantDiagnosticSection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaTestsResultOrganizer;
import vn.ehealth.emr.model.EmrChanDoanHinhAnh;
import vn.ehealth.emr.model.EmrFileDinhKem;
import vn.ehealth.emr.model.EmrGiaiPhauBenh;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.model.EmrThamDoChucNang;
import vn.ehealth.emr.model.EmrXetNghiem;
import vn.ehealth.emr.model.EmrXetNghiemKetQua;

public class ThongTinCanLamSangUtil {

    public static void addActCdCodeTag(Act act, String codeCda, String displayName){
        var codeTag = act.getCode();                         
        if(!StringUtils.isEmpty(codeCda)){
            codeTag.setCode(codeCda);
        }
        if(!StringUtils.isEmpty(displayName)){
            codeTag.setDisplayName(displayName);
        }           
    }
    
    public static void addActAssignedEntity(Act act, AssignedEntity assignedEntity, String assignPersonName){       
        var performer = CDAFactory.eINSTANCE.createPerformer2();
        act.getPerformers().add(performer);     
        performer.setAssignedEntity(assignedEntity);
        II assignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        assignedEntity.getIds().add(assignedEntityId);
        
        org.openhealthtools.mdht.uml.cda.Person requestPerson = CDAFactory.eINSTANCE.createPerson();
        var personName = DatatypesFactory.eINSTANCE.createPN();      
        if(!StringUtils.isEmpty(assignPersonName)){
            personName.addText(assignPersonName);
        }
        requestPerson.getNames().add(personName);
        assignedEntity.setAssignedPerson(requestPerson);      
    }
    
    public static void addActRequestService(Act act, Act requestServiceAct, String codeCdaRequestService, String requestServiceCodeSystem, String requestServiceName){
        var requestServiceEntryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();     
        act.getEntryRelationships().add(requestServiceEntryRelationship);
        requestServiceEntryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                
        requestServiceEntryRelationship.setAct(requestServiceAct);
        var codeTag = DatatypesFactory.eINSTANCE.createCD();
        requestServiceAct.setCode(codeTag);
        if(!StringUtils.isEmpty(codeCdaRequestService)){
            codeTag.setCode(codeCdaRequestService);
        }
        if(!StringUtils.isEmpty(requestServiceCodeSystem)){
            codeTag.setCodeSystem(requestServiceCodeSystem);
        }
        if(!StringUtils.isEmpty(requestServiceName)){
            codeTag.setDisplayName(requestServiceName);
        }       
    }
    
    public static void addTestResultObservation(HsbaTestsResultOrganizer testsResultOrganizer, EmrXetNghiemKetQua emrXetNghiemKetQua, Properties properties, Map<String, String> emrParameters){
        var emrDmChiSoXetNghiem = emrXetNghiemKetQua.emrDmChiSoXetNghiem;
        if(emrDmChiSoXetNghiem != null){
            var testResultObs = HSBAFactory.eINSTANCE.createHsbaTestsResultObservation().init();         
            testsResultOrganizer.addObservation(testResultObs);         
            
            //Mã chỉ số
            var codeTag = testResultObs.getCode();
            String codeCda = emrDmChiSoXetNghiem.maicd;
            String displayName = emrDmChiSoXetNghiem.ten;
            if(!StringUtils.isEmpty(codeCda)){
                codeTag.setCode(codeCda);                       
            }
            if(!StringUtils.isEmpty(displayName)){
                codeTag.setDisplayName(displayName);                        
            }
            
            //Giá trị đo
            Double giaTriChiSoDo = null;
            try {
                giaTriChiSoDo = Double.parseDouble(emrXetNghiemKetQua.giatrido);
            }catch(NumberFormatException e) {
                
            }
            
            if(giaTriChiSoDo != null){
                String unit = properties.getProperty("BA_CHISOXETNGHIEM_UNIT", "BA_CHISOXETNGHIEM_UNIT");
                var valueTag = DatatypesFactory.eINSTANCE.createPQ();                
                    valueTag.setValue(giaTriChiSoDo);
                    if(!StringUtils.isEmpty(unit)){
                        valueTag.setUnit(unit);
                    }
                    testResultObs.getValues().add(valueTag);                        
            }                               
            
            //Chỉ số bình thường ở nam và nữ
            var referenceRange = CDAFactory.eINSTANCE.createReferenceRange();            
            var obsRange = CDAFactory.eINSTANCE.createObservationRange();      
            referenceRange.setObservationRange(obsRange);                   
            StringBuilder moTaChiSo = new StringBuilder();
            var emrDmXetNghiem = emrXetNghiemKetQua.emrXetNghiemId;
            
            if(emrDmXetNghiem != null){
                String chiSoBtNam = "";//emrDmChiSoXetNghiem.chisobtnam;
                String chiSoBtNu = ""; // emrDmChiSoXetNghiem.chisobtnu();
                if(!StringUtils.isEmpty(chiSoBtNam)){
                    moTaChiSo.append(chiSoBtNam).append(properties.getProperty("CHISO_XETNGHIEM_SPLIT", "CHISO_XETNGHIEM_SPLIT"));                  
                }
                if(!StringUtils.isEmpty(chiSoBtNu)){
                    moTaChiSo.append(chiSoBtNu);
                }
                ED obsRangeText = DatatypesFactory.eINSTANCE.createED(moTaChiSo.toString());
                obsRange.setText(obsRangeText);
            }                       
            testResultObs.getReferenceRanges().add(referenceRange);
            
            //Thông dịch 
            var emrDmDichKetQuaXetNghiem = emrXetNghiemKetQua.emrDmDichKetQuaXetNghiem;       
            if(emrDmDichKetQuaXetNghiem != null){
                String interpretationCode = emrDmDichKetQuaXetNghiem.maicd;
                String interpretationCodeSystem = emrParameters.get("emr_dm_dich_ket_qua_xet_nghiem");
                String interpretationDisplayName = emrDmDichKetQuaXetNghiem.ten;
                var interpretationCodeTag = DatatypesFactory.eINSTANCE.createCE();
                if(interpretationCode != null && interpretationCodeSystem != null){
                    interpretationCodeTag.setCode(interpretationCode);
                    interpretationCodeTag.setCodeSystem(interpretationCodeSystem);
                }
                if(!StringUtils.isEmpty(interpretationDisplayName)){
                    interpretationCodeTag.setDisplayName(interpretationDisplayName);
                }
                testResultObs.getInterpretationCodes().add(interpretationCodeTag);  
            }
        }       
    }
    
    public static void addMedicalTests(HsbaRelevantDiagnosticSection relevantDiagnosticSection, EmrXetNghiem emrXetNghiem, Map<String, String> emrParameters, Properties properties, List<EmrFileDinhKem> lstXetNghiemEmrQuanLyFileDinhKems) throws IOException{
        var medicalTestsEntry = CDAFactory.eINSTANCE.createEntry();       
        relevantDiagnosticSection.getEntries().add(medicalTestsEntry);
        medicalTestsEntry.setTypeCode(x_ActRelationshipEntry.DRIV);
        
        var medicalTestsAct = HSBAFactory.eINSTANCE.createHsbaMedicalTestsAct().init();     
        medicalTestsEntry.setAct(medicalTestsAct);
        II medicalTestsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        medicalTestsAct.getIds().add(medicalTestsId);
        
        //Loại xét nghiệm
        var emrDmLoaiXetNghiem = emrXetNghiem.emrDmLoaiXetNghiem;
        if(emrDmLoaiXetNghiem != null){
            addActCdCodeTag(medicalTestsAct, emrDmLoaiXetNghiem.maicd, emrDmLoaiXetNghiem.ten);
        }
        
        //Thời điểm thực hiện xét nghiệm
        CDAExportUtil.addActEffectiveTime(medicalTestsAct, emrXetNghiem.ngaythuchien);      
        
        //Tài liệu đính kèm 
        if(lstXetNghiemEmrQuanLyFileDinhKems != null){
            for (var item : lstXetNghiemEmrQuanLyFileDinhKems) {                 
                CDAExportUtil.addActExternalDocument(medicalTestsAct, item.url);
            }
        }
        
        //Thông tin yêu cầu
        var requestEntryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
        medicalTestsAct.getEntryRelationships().add(requestEntryRelationship);
        requestEntryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
        requestEntryRelationship.setInversionInd(true);
        
        var procedureRequestAct = HSBAFactory.eINSTANCE.createHsbaProceduresRequestAct().init();
        II procedureRequestActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        procedureRequestAct.getIds().add(procedureRequestActId);
        requestEntryRelationship.setAct(procedureRequestAct);       
        //-- Thời điểm yêu cầu
        CDAExportUtil.addActEffectiveTime(procedureRequestAct, emrXetNghiem.ngayyeucau);
        
        //-- Bác sĩ yêu cầu 
        var requestDoctorAssingedEntity = HSBAFactory.eINSTANCE.createHsbaDischargeDoctorAssignedEntity().init();
        String bacSiYeuCau = emrXetNghiem.bacsiyeucau;     
        addActAssignedEntity(procedureRequestAct, requestDoctorAssingedEntity, bacSiYeuCau);
        
        //-- Dịch vụ yêu cầu cần làm
        var lstEmrXetNghiemDichVus = emrXetNghiem.getEmrXetNghiemDichVus();
        if(lstEmrXetNghiemDichVus != null){
            for (var emrXetNghiemDichVuItem : lstEmrXetNghiemDichVus) {
                var emrDmXetNghiem = emrXetNghiemDichVuItem.emrDmXetNghiem;
                if(emrDmXetNghiem != null){                 
                    var testsServicesRequestAct = HSBAFactory.eINSTANCE.createHsbaTestsServicesRequestAct().init();
                    II testsServicesRequestActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                    testsServicesRequestAct.getIds().add(testsServicesRequestActId);
                    
                    String codeCda = emrDmXetNghiem.maicd;
                    String codeSystem = emrParameters.get("emr_dm_xet_nghiem");
                    String displayName = emrDmXetNghiem.ten;
                    addActRequestService(procedureRequestAct, testsServicesRequestAct, codeCda, codeSystem, displayName);
                    
                    //Kết quả xét nghiệm
                    var lstEmrXetNghiemKetQuas = emrXetNghiemDichVuItem.getEmrXetNghiemKetQuas();
                    if(lstEmrXetNghiemKetQuas != null && lstEmrXetNghiemKetQuas.size() > 0){
                        for (var item : lstEmrXetNghiemKetQuas) {
                            var resultEntryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
                            testsServicesRequestAct.getEntryRelationships().add(resultEntryRelationship);
                            resultEntryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                            
                            var testResultOrganizer = HSBAFactory.eINSTANCE.createHsbaTestsResultOrganizer().init();
                            resultEntryRelationship.setOrganizer(testResultOrganizer);                      
                            addTestResultObservation(testResultOrganizer, item, properties, emrParameters);
                        }
                    }
                } 
            }
        }
        
        //Bác sĩ chuyên khoa thực hiện  
        var specializedAssignedEntity = HSBAFactory.eINSTANCE.createHsbaSpecializedPhysicianAssignedEntity().init();
        String bacSiChuyenKhoa = emrXetNghiem.bacsixetnghiem;
        addActAssignedEntity(medicalTestsAct, specializedAssignedEntity, bacSiChuyenKhoa);              
        
        //Nhận xét kết quả xét nghiệm       
        var annotationCmtEntryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
        medicalTestsAct.getEntryRelationships().add(annotationCmtEntryRelationship);
        annotationCmtEntryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        
        var annotationCmtAct = HSBAFactory.eINSTANCE.createHsbaAnnotationCommentAct().init();
        annotationCmtEntryRelationship.setAct(annotationCmtAct);        
        String nhanXetKqxn = emrXetNghiem.nhanxet;
        if(!StringUtils.isEmpty(nhanXetKqxn)){
            ED annotationCmtText = DatatypesFactory.eINSTANCE.createED(nhanXetKqxn);
            annotationCmtAct.setText(annotationCmtText);            
        }               
    }   
    
    public static void addChildActEntryRelationship(Act parentAct, Act childAct, String childActText){
        var entryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
        parentAct.getEntryRelationships().add(entryRelationship);
        entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        
        entryRelationship.setAct(childAct);
        if(!StringUtils.isEmpty(childActText)){
            ED childText = DatatypesFactory.eINSTANCE.createED(childActText);
            childAct.setText(childText);
        }       
    }
    

    /**
     * Thêm thông tin giải phẫu bệnh
     * @param relevantDiagnosticSection
     * @param emrGiaiPhauBenh
     * @param emrParameters
     * @throws IOException 
     */
    public static void addPathology(HsbaRelevantDiagnosticSection relevantDiagnosticSection, EmrGiaiPhauBenh emrGiaiPhauBenh, Map<String, String> emrParameters, List<EmrFileDinhKem> lstEmrQuanLyFileDinhKemGpbs) throws IOException{
        if(emrGiaiPhauBenh != null){
            var pathologyEntry = CDAFactory.eINSTANCE.createEntry();      
            relevantDiagnosticSection.getEntries().add(pathologyEntry);
            pathologyEntry.setTypeCode(x_ActRelationshipEntry.DRIV);
            
            var pathologyAct = HSBAFactory.eINSTANCE.createHsbaPathologyAct().init();
            pathologyEntry.setAct(pathologyAct);
            II pathologyActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            pathologyAct.getIds().add(pathologyActId);
            
            //Loại giải phẫu bệnh
            var emrDmLoaiGiaiPhauBenh = emrGiaiPhauBenh.emrDmLoaiGiaiPhauBenh;           
            if(emrDmLoaiGiaiPhauBenh != null){
                addActCdCodeTag(pathologyAct, emrDmLoaiGiaiPhauBenh.maicd, emrDmLoaiGiaiPhauBenh.ten);              
            }
            //Thời điểm thực hiện   
            CDAExportUtil.addActEffectiveTime(pathologyAct, emrGiaiPhauBenh.ngaythuchien);
            
            //Tài liệu đính kèm     
            if(lstEmrQuanLyFileDinhKemGpbs != null){
                for (var item : lstEmrQuanLyFileDinhKemGpbs) {
                    CDAExportUtil.addActExternalDocument(pathologyAct, item.url);
                }               
            }
            
            //Thông tin yêu cầu
            var requestEntryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
            pathologyAct.getEntryRelationships().add(requestEntryRelationship);
            requestEntryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
            requestEntryRelationship.setInversionInd(true);
            
            var procedureRequestAct = HSBAFactory.eINSTANCE.createHsbaProceduresRequestAct().init();
            II procedureRequestActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            procedureRequestAct.getIds().add(procedureRequestActId);
            requestEntryRelationship.setAct(procedureRequestAct);
            
            //-- Thời điểm yêu cầu  
            CDAExportUtil.addActEffectiveTime(procedureRequestAct, emrGiaiPhauBenh.ngayyeucau);
                        
            //-- Bác sĩ yêu cầu
            var requestDoctorAssingedEntity = HSBAFactory.eINSTANCE.createHsbaDischargeDoctorAssignedEntity().init();
            String bacSiYeuCau = emrGiaiPhauBenh.bacsiyeucau;      
            addActAssignedEntity(procedureRequestAct, requestDoctorAssingedEntity, bacSiYeuCau);
            
            //-- Dịch vụ yêu cầu cần làm 
            var pathologyActServicesRequestAct = HSBAFactory.eINSTANCE.createHsbaPathologyActServicesRequestAct().init();
            II pathologyActServicesRequestActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            pathologyActServicesRequestAct.getIds().add(pathologyActServicesRequestActId);
            
            var emrDmGiaiPhauBenh = emrGiaiPhauBenh.emrDmGiaiPhauBenh;
            if(emrDmGiaiPhauBenh!= null){
                String codeCda = emrDmGiaiPhauBenh.maicd;
                String codeSystem = emrParameters.get("emr_dm_giai_phau_benh");
                String displayName = emrDmGiaiPhauBenh.ten;
                addActRequestService(procedureRequestAct, pathologyActServicesRequestAct, codeCda, codeSystem, displayName);
            }
            
            //-- Mẫu sinh thiết
            var entryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
            pathologyAct.getEntryRelationships().add(entryRelationship);
            entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            
            var specimenCollectionProcedure = HSBAFactory.eINSTANCE.createHsbaSpecimenCollectionProcedure().init();
            entryRelationship.setProcedure(specimenCollectionProcedure);
            
            //------ Thời điểm lấy mẫu  
            var specimenCollectionEffectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
            specimenCollectionProcedure.setEffectiveTime(specimenCollectionEffectiveTime);
            var ngayLayMau = emrGiaiPhauBenh.ngaylaymausinhthiet;
            if(ngayLayMau != null){
                String thoiDiemLayMau = CDAExportUtil.getGMTDate(ngayLayMau);
                if(thoiDiemLayMau != null){
                    specimenCollectionEffectiveTime.setValue(thoiDiemLayMau);
                }
            }                       
            
            //------Vị trí lấy mẫu sinh thiết
            var emrDmViTriLayMau = emrGiaiPhauBenh.emrDmViTriLayMau;
            if(emrDmViTriLayMau != null){
                var targetSiteCode = DatatypesFactory.eINSTANCE.createCD();
                String code = emrDmViTriLayMau.maicd;
                String displayName = emrDmViTriLayMau.ten;
                String codeSystem = emrParameters.get("emr_dm_vi_tri_lay_mau");
                if(!StringUtils.isEmpty(code)){
                    targetSiteCode.setCode(code);               
                }
                if(!StringUtils.isEmpty(codeSystem)){
                    targetSiteCode.setCodeSystem(codeSystem);               
                }
                if(!StringUtils.isEmpty(displayName)){
                    targetSiteCode.setDisplayName(displayName);
                }
                specimenCollectionProcedure.getTargetSiteCodes().add(targetSiteCode);
            }
            
            //Bác sĩ đọc kết quả
            var specializedAssignedEntity = HSBAFactory.eINSTANCE.createHsbaSpecializedPhysicianAssignedEntity().init();
            String bacSiChuyenKhoa = emrGiaiPhauBenh.bacsichuyenkhoa;
            addActAssignedEntity(pathologyAct, specializedAssignedEntity, bacSiChuyenKhoa);
    
            //Nhận xét đại thể
            var pathologyReportGrossAct = HSBAFactory.eINSTANCE.createHsbaPathologyReportGrossAct().init();
            II pathologyReportGrossActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            pathologyReportGrossAct.getIds().add(pathologyReportGrossActId);
            addChildActEntryRelationship(pathologyAct, pathologyReportGrossAct, emrGiaiPhauBenh.nhanxetdaithe);            
                        
            //Nhận xét vi thể
            var pathologyReportMicroscopicAct = HSBAFactory.eINSTANCE.createHsbaPathologyReportMicroscopicAct().init();
            II pathologyReportMicroscopicActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            pathologyReportMicroscopicAct.getIds().add(pathologyReportMicroscopicActId);
            addChildActEntryRelationship(pathologyAct, pathologyReportMicroscopicAct, emrGiaiPhauBenh.nhanxetvithe);   
            
            //Mô tả chẩn đoán giải phẫu
            var pathologyReportDiagnosisAct = HSBAFactory.eINSTANCE.createHsbaPathologyReportDiagnosisAct().init();
            II pathologyReportDiagnosisActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            pathologyReportDiagnosisAct.getIds().add(pathologyReportDiagnosisActId);
            addChildActEntryRelationship(pathologyAct, pathologyReportDiagnosisAct, emrGiaiPhauBenh.motachandoangiaiphau);         
        }       
    }
    
    public static void addDiagnosisThums(HsbaRelevantDiagnosticSection relevantDiagnosticSection, EmrChanDoanHinhAnh emrChanDoanHinhAnh, Map<String, String> emrParameters, List<EmrFileDinhKem> lstEmrQuanLyFileDinhKemCdhas) throws IOException{
        if(emrChanDoanHinhAnh != null){
            var diagnosisThumbsEntry = CDAFactory.eINSTANCE.createEntry();        
            relevantDiagnosticSection.getEntries().add(diagnosisThumbsEntry);
            diagnosisThumbsEntry.setTypeCode(x_ActRelationshipEntry.DRIV);
            
            var diagnosisThumsAct = HSBAFactory.eINSTANCE.createHsbaDiagnosisThumbsAct().init();
            diagnosisThumbsEntry.setAct(diagnosisThumsAct);     
            II diagnosisThumbsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            diagnosisThumsAct.getIds().add(diagnosisThumbsId);
            
            //Loại chẩn đoán
            var emrDmLoaiChanDoanHinhAnh = emrChanDoanHinhAnh.emrDmLoaiChanDoanHinhAnh;           
            if(emrDmLoaiChanDoanHinhAnh != null){
                addActCdCodeTag(diagnosisThumsAct, emrDmLoaiChanDoanHinhAnh.maicd, emrDmLoaiChanDoanHinhAnh.ten);               
            }
            
            //Thời điểm thực hiện
            CDAExportUtil.addActEffectiveTime(diagnosisThumsAct, emrChanDoanHinhAnh.ngaythuchien);                  
                        
            //Tài liệu đính kèm
            if(lstEmrQuanLyFileDinhKemCdhas != null){
                for (var item : lstEmrQuanLyFileDinhKemCdhas) {
                    CDAExportUtil.addActExternalDocument(diagnosisThumsAct, item.url);
                }               
            }
            
            //Thông tin yêu cầu
            var requestEntryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
            diagnosisThumsAct.getEntryRelationships().add(requestEntryRelationship);
            requestEntryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
            requestEntryRelationship.setInversionInd(true);
            
            var procedureRequestAct = HSBAFactory.eINSTANCE.createHsbaProceduresRequestAct().init();
            II procedureRequestActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            procedureRequestAct.getIds().add(procedureRequestActId);
            requestEntryRelationship.setAct(procedureRequestAct);
            //-- Thời điểm yêu cầu
            CDAExportUtil.addActEffectiveTime(procedureRequestAct, emrChanDoanHinhAnh.ngayyeucau);
            
            //-- Bác sĩ yêu cầu 
            var requestDoctorAssingedEntity = HSBAFactory.eINSTANCE.createHsbaDischargeDoctorAssignedEntity().init();
            String bacSiYeuCau = emrChanDoanHinhAnh.bacsiyeucau;       
            addActAssignedEntity(procedureRequestAct, requestDoctorAssingedEntity, bacSiYeuCau);
            
            //-- Dịch vụ yêu cầu cần làm
            var diagnosisServiceRequestAct = HSBAFactory.eINSTANCE.createHsbaDiagnosisServicesRequestAct().init();
            II diagnosisServiceRequestActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            diagnosisServiceRequestAct.getIds().add(diagnosisServiceRequestActId);
            var emrDmChanDoanHinhAnh = emrChanDoanHinhAnh.emrDmChanDoanHinhAnh;
            if(emrDmChanDoanHinhAnh != null){
                String codeCda = emrDmChanDoanHinhAnh.maicd;
                String codeSystem = emrParameters.get("emr_dm_chan_doan_hinh_anh");
                String displayName = emrDmChanDoanHinhAnh.ten;
                addActRequestService(procedureRequestAct, diagnosisServiceRequestAct, codeCda, codeSystem, displayName);
            }
        
            //Bác sĩ chuyên khoa thực hiện  
            var specializedAssignedEntity = HSBAFactory.eINSTANCE.createHsbaSpecializedPhysicianAssignedEntity().init();
            String bacSiChuyenKhoa = emrChanDoanHinhAnh.bacsichuyenkhoa;
            addActAssignedEntity(diagnosisThumsAct, specializedAssignedEntity, bacSiChuyenKhoa);
    
            //Kết quả chẩn đoán     
            var diagnosisResultAct = HSBAFactory.eINSTANCE.createHsbaDiagnosisResultAct().init();
            II diagnosisResultActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            diagnosisResultAct.getIds().add(diagnosisResultActId);
            addChildActEntryRelationship(diagnosisThumsAct, diagnosisResultAct, emrChanDoanHinhAnh.ketqua);            
                        
            //Kết luận
            var conclusionsAct = HSBAFactory.eINSTANCE.createHsbaDiagnosisConclusionsAct().init();
            II conclusionsActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            conclusionsAct.getIds().add(conclusionsActId);
            addChildActEntryRelationship(diagnosisThumsAct, conclusionsAct, emrChanDoanHinhAnh.ketluan);   
            
            //Lời dặn
            var annotationCommentAct = HSBAFactory.eINSTANCE.createHsbaAnnotationCommentAct().init();
            II annotationCommentActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            annotationCommentAct.getIds().add(annotationCommentActId);
            addChildActEntryRelationship(diagnosisThumsAct, annotationCommentAct, emrChanDoanHinhAnh.loidan);
        }       
    }

    /**
     * Thêm thông tin Thăm dò chức năng
     * @param relevantDiagnosticSection
     * @param emrChanDoanHinhAnh
     * @param emrParameters
     * @throws IOException 
     */
    public static void addFunctionalProbe(HsbaRelevantDiagnosticSection relevantDiagnosticSection, EmrThamDoChucNang emrThamDoChucNang, Map<String, String> emrParameters, List<EmrFileDinhKem> lstEmrQuanLyFileDinhKemTdcns) throws IOException{
        if(emrThamDoChucNang != null){
            var functionalProbeEntry = CDAFactory.eINSTANCE.createEntry();        
            relevantDiagnosticSection.getEntries().add(functionalProbeEntry);
            functionalProbeEntry.setTypeCode(x_ActRelationshipEntry.DRIV);
            
            var functionalProbeAct = HSBAFactory.eINSTANCE.createHsbaFunctionalProbeAct().init();
            functionalProbeEntry.setAct(functionalProbeAct);
            II functionalProbeId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            functionalProbeAct.getIds().add(functionalProbeId);
            
            //Loại thăm dò chức năng
            var emrDmLoaiThamDoChucNang = emrThamDoChucNang.emrDmLoaiThamDoChucNang;           
            if(emrDmLoaiThamDoChucNang != null){
                addActCdCodeTag(functionalProbeAct, emrDmLoaiThamDoChucNang.maicd, emrDmLoaiThamDoChucNang.ten);                
            }
            //Thời điểm thực hiện   
            CDAExportUtil.addActEffectiveTime(functionalProbeAct, emrThamDoChucNang.ngaythuchien);
            
            //Tài liệu đính kèm     
            if(lstEmrQuanLyFileDinhKemTdcns != null){
                for (var item : lstEmrQuanLyFileDinhKemTdcns) {
                    CDAExportUtil.addActExternalDocument(functionalProbeAct, item.url);
                }               
            }
            
            //Thông tin yêu cầu
            var requestEntryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
            functionalProbeAct.getEntryRelationships().add(requestEntryRelationship);
            requestEntryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
            requestEntryRelationship.setInversionInd(true);
            
            var procedureRequestAct = HSBAFactory.eINSTANCE.createHsbaProceduresRequestAct().init();
            requestEntryRelationship.setAct(procedureRequestAct);
            
            //-- Thời điểm yêu cầu  
            CDAExportUtil.addActEffectiveTime(procedureRequestAct, emrThamDoChucNang.ngayyeucau);
            
            //-- Bác sĩ yêu cầu
            var requestDoctorAssingedEntity = HSBAFactory.eINSTANCE.createHsbaDischargeDoctorAssignedEntity().init();
            String bacSiYeuCau = emrThamDoChucNang.bacsiyeucau;        
            addActAssignedEntity(procedureRequestAct, requestDoctorAssingedEntity, bacSiYeuCau);
            
            //-- Dịch vụ yêu cầu cần làm
            var functionalProbeServicesRequestAct = HSBAFactory.eINSTANCE.createHsbaFunctionalProbeServicesRequestAct().init();
            var emrDmThamDoChucNang = emrThamDoChucNang.emrDmThamDoChucNang;
            if(emrDmThamDoChucNang!= null){
                String codeCda = emrDmThamDoChucNang.maicd;
                String codeSystem = emrParameters.get("emr_dm_tham_do_chuc_nang");
                String displayName = emrDmThamDoChucNang.ten;
                addActRequestService(procedureRequestAct, functionalProbeServicesRequestAct, codeCda, codeSystem, displayName);
            }
            
            //Bác sĩ chuyên khoa thực hiện  
            var specializedAssignedEntity = HSBAFactory.eINSTANCE.createHsbaSpecializedPhysicianAssignedEntity().init();
            String bacSiChuyenKhoa = emrThamDoChucNang.bacsichuyenkhoa;
            addActAssignedEntity(functionalProbeAct, specializedAssignedEntity, bacSiChuyenKhoa);
    
            //Kết quả thăm dò
            var diagnosisResultAct = HSBAFactory.eINSTANCE.createHsbaDiagnosisResultAct().init();
            addChildActEntryRelationship(functionalProbeAct, diagnosisResultAct, emrThamDoChucNang.ketqua);            
                        
            //Kết luận
            var conclusionsAct = HSBAFactory.eINSTANCE.createHsbaDiagnosisConclusionsAct().init();
            addChildActEntryRelationship(functionalProbeAct, conclusionsAct, emrThamDoChucNang.ketluan);   
            
            //Lời dặn
            var annotationCommentAct = HSBAFactory.eINSTANCE.createHsbaAnnotationCommentAct().init();
            addChildActEntryRelationship(functionalProbeAct, annotationCommentAct, emrThamDoChucNang.loidan);       
        }
    }
    
    
    public static void addRelevantDiagnostic(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters, Properties properties) throws IOException{  
        var lstEmrChanDoanHinhAnh = emrDanhSachHoSoBenhAn.getEmrChanDoanHinhAnhs();
        var lstEmrThamDoChucNangs = emrDanhSachHoSoBenhAn.getEmrThamDoChucNangs();
        var lstEmrGiaiPhauBenhs = emrDanhSachHoSoBenhAn.getEmrGiaiPhauBenhs();
        var lstEmrXetNghiems = emrDanhSachHoSoBenhAn.getEmrXetNghiems();
        var relevantDiagnosticSection = HSBAFactory.eINSTANCE.createHsbaRelevantDiagnosticSection().init();
        doc.addSection(relevantDiagnosticSection);
        II relevantDiagnosticSectionId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        relevantDiagnosticSection.setId(relevantDiagnosticSectionId);
        String relevantDiagnositcTitle = properties.getProperty("THONGTIN_CANLAMSANG_TITLE", "THONGTIN_CANLAMSANG_TITLE");
        CDAExportUtil.addSectionTitle(relevantDiagnosticSection, relevantDiagnositcTitle);               
        
        //Xét nghiệm
        if(lstEmrXetNghiems != null && lstEmrXetNghiems.size() > 0){
            for (var item : lstEmrXetNghiems) {
                addMedicalTests(relevantDiagnosticSection, item, emrParameters, properties, item.emrFileDinhKemXetNghiems);
            }
        }
        
        //Chẩn đoán hình ảnh
        if(lstEmrChanDoanHinhAnh != null && lstEmrChanDoanHinhAnh.size() > 0){
            for (var item : lstEmrChanDoanHinhAnh) {
                addDiagnosisThums(relevantDiagnosticSection, item, emrParameters, item.emrFileDinhKemCdhas);
            }
        }
        
        //Thăm dò chức năng
        if(lstEmrThamDoChucNangs != null && lstEmrThamDoChucNangs.size() > 0){
            for (var item : lstEmrThamDoChucNangs) {
                addFunctionalProbe(relevantDiagnosticSection, item, emrParameters, item.emrFileDinhKemTdcns);
            }
        }
        
        //Giải phẫu bệnh
        if(lstEmrGiaiPhauBenhs != null && lstEmrGiaiPhauBenhs.size() > 0){
            for (var item : lstEmrGiaiPhauBenhs) {
                addPathology(relevantDiagnosticSection, item, emrParameters, item.emrFileDinhKemGpbs);
            }
        }
            
        String cacXnCanLamSang = emrDanhSachHoSoBenhAn.emrBenhAn.xetnghiemcanlamsang;
        CDAExportUtil.setSectionData(relevantDiagnosticSection, cacXnCanLamSang);           
    }
}
