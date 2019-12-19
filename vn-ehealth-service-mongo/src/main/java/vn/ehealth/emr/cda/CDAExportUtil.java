package vn.ehealth.emr.cda;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.UUID;

import javax.annotation.Nonnull;

import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HSBAFactory;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaAdministrationOfSubstanceAct;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaConsultationEncounter;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaDocument;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaGeneralMedicineProgressSection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaHospitalConsultationsSection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaInOutHospitalEncounter;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaInputDepartmentEncounter;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaNurseProgressNoteSection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaPhysicianAttendingDischargeSummarySection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaProceduresProcedure;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaProceduresSection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaVitalSignsFunctionalSection;
import vn.ehealth.emr.model.EmrBenhAn;
import vn.ehealth.emr.model.EmrBenhNhan;
import vn.ehealth.emr.model.EmrChamSoc;
import vn.ehealth.emr.model.EmrChucNangSong;
import vn.ehealth.emr.model.EmrChucNangSongChiTiet;
import vn.ehealth.emr.model.EmrCoSoKhamBenh;
import vn.ehealth.emr.model.EmrDieuTri;
import vn.ehealth.emr.model.EmrDmContent;
import vn.ehealth.emr.model.EmrDonThuocChiTiet;
import vn.ehealth.emr.model.EmrFileDinhKem;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.model.EmrHoiChan;
import vn.ehealth.emr.model.EmrPhauThuatThuThuat;
import vn.ehealth.emr.model.EmrQuaTrinhChamSoc;
import vn.ehealth.emr.model.EmrQuaTrinhDieuTri;
import vn.ehealth.emr.model.EmrQuanLyNguoiBenh;
import vn.ehealth.emr.model.EmrVaoKhoa;
import vn.ehealth.emr.utils.JasperUtils;
import vn.ehealth.emr.utils.JsonUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.cda.AssignedEntity;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.CDAPackage;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Component1;
import org.openhealthtools.mdht.uml.cda.DocumentRoot;
import org.openhealthtools.mdht.uml.cda.Organization;
import org.openhealthtools.mdht.uml.cda.OrganizationPartOf;
import org.openhealthtools.mdht.uml.cda.Organizer;
import org.openhealthtools.mdht.uml.cda.Patient;
import org.openhealthtools.mdht.uml.cda.PatientRole;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.consol.Encounter;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.EncompassingEncounter;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVXB_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ON;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openhealthtools.mdht.uml.hl7.vocab.ActRelationshipHasComponent;
import org.openhealthtools.mdht.uml.hl7.vocab.EntityClassRoot;
import org.openhealthtools.mdht.uml.hl7.vocab.ParticipationType;
import org.openhealthtools.mdht.uml.hl7.vocab.SetOperator;
import org.openhealthtools.mdht.uml.hl7.vocab.TimingEvent;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntry;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntryRelationship;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipExternalReference;
import org.openhealthtools.mdht.uml.hl7.vocab.x_DocumentSubstanceMood;
import org.springframework.util.StringUtils;

public class CDAExportUtil {
    
    
    protected static Map<String, EmrVaoKhoa> referenceDepartments = new HashMap<String, EmrVaoKhoa>();
    public static final String EMR_MA_LOAI_DOI_TUONG_BHYT = "01";
    
    protected static Map<String, EmrVaoKhoa> tempEmrVaoKhoas = new HashMap<String, EmrVaoKhoa>();
    protected static final String STR_NA_VALUE = "N/a";
    protected static final String PT_TYPE = "PT";
    protected static final String TT_TYPE = "TT";
    protected static final String TB_TYPE = "TB";
    protected static final String BC_TYPE = "BC";
    protected static final int TV_TRC_24H = 1;
    protected static final int TV_SAU_24H = 2;
    protected static final String TONGSONGAY_DTR_TYPE = "TSNDT";
    protected static final String TONGSONGAY_DTR_SAUPT_TYPE = "TSNDT_SAUPT";
    protected static final String TONGSOLAN_PT_TYPE = "TSL_PT";
    
    public static final String LOAI_TANSUAT_DUNGTHUOC_TS = "TS";
    public static final String LOAI_TANSUAT_DUNGTHUOC_EIVL_TS = "EIVL_TS";
    public static final String LOAI_TANSUAT_DUNGTHUOC_PIVL_TS = "PIVL_TS";
    
    //protected static boolean isContainsPhauThuat = false;   
    //protected static boolean isContainsThuThuat = false;
    
    public static void generateCDAHeader(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters, EmrCoSoKhamBenh emrCoSoKhamBenh, Properties properties) throws Exception{
        var emrDmLoaiBenhAn = emrDanhSachHoSoBenhAn.getEmrDmLoaiBenhAn();
        var emrQuanLyNguoiBenh = emrDanhSachHoSoBenhAn.getEmrQuanLyNguoiBenh();          
        var emrBenhNhan = emrDanhSachHoSoBenhAn.getEmrBenhNhan();       
        var  emrBenhAn = emrDanhSachHoSoBenhAn.getEmrBenhAn();        
        var lstEmrVaoKhoa = emrDanhSachHoSoBenhAn.emrVaoKhoas;
        
        var custodian = CDAFactory.eINSTANCE.createCustodian();
        var assignedCustodian = CDAFactory.eINSTANCE.createAssignedCustodian();
        custodian.setAssignedCustodian(assignedCustodian);
        var custodianOrganization = CDAFactory.eINSTANCE.createCustodianOrganization();
        assignedCustodian.setRepresentedCustodianOrganization(custodianOrganization);
        II custodianOrganizationId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString()); 
        custodianOrganization.getIds().add(custodianOrganizationId);
        ON custodianName = DatatypesFactory.eINSTANCE.createON();
        custodianOrganization.setName(custodianName);
        String tenBenhVien = emrCoSoKhamBenh.ten;
        
        if(!StringUtils.isEmpty(tenBenhVien)){
            custodianName.addText(tenBenhVien);
        }   
        doc.setCustodian(custodian);
        
        if(emrDmLoaiBenhAn != null){
            doc.getCode().setCode(emrDmLoaiBenhAn.maicd);
            ST hsbaDocTitle = DatatypesFactory.eINSTANCE.createST(emrDmLoaiBenhAn.ten);        
            doc.setTitle(hsbaDocTitle);
        }
        
        II docId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        doc.setId(docId);
        
        String soLuuTru = emrDanhSachHoSoBenhAn.getMaluutru();
        if(!StringUtils.isEmpty(soLuuTru)){
            II docSetId = DatatypesFactory.eINSTANCE.createII();
            docSetId.setExtension(soLuuTru);
            docSetId.setRoot(doc.getTemplateIds().get(0).getRoot());
            doc.setSetId(docSetId);
        }
        
        var recordTarget = CDAFactory.eINSTANCE.createRecordTarget();
        doc.getRecordTargets().add(recordTarget);
        
        var patientRole = CDAFactory.eINSTANCE.createPatientRole();
        recordTarget.setPatientRole(patientRole);
        
        String strIdHis = emrBenhNhan.idhis;
        if(!StringUtils.isEmpty(strIdHis)){
            II idHis = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            idHis.setRoot(properties.getProperty("OID_IDHIS", "OID_IDHIS"));
            idHis.setExtension(strIdHis);
            patientRole.getIds().add(idHis);
        }
        
        var patient = CDAFactory.eINSTANCE.createPatient();
        patientRole.setPatient(patient);
                
        var emrDmLoaiDoiTuongTaiChinh = emrQuanLyNguoiBenh.emrDmLoaiDoiTuongTaiChinh;
        if(emrDmLoaiDoiTuongTaiChinh != null){
            String dttcCodeCda = emrDmLoaiDoiTuongTaiChinh.maicd;
            CE dttcCodeTag = DatatypesFactory.eINSTANCE.createCE();
            if(!StringUtils.isEmpty(dttcCodeCda)){
                dttcCodeTag.setCode(dttcCodeCda);
                
                String dttcDisplayName = emrDmLoaiDoiTuongTaiChinh.ten;
                if(!StringUtils.isEmpty(dttcDisplayName)){
                    dttcCodeTag.setDisplayName(dttcDisplayName);
                }
            }               
            patient.setRaceCode(dttcCodeTag);
        }
        
        if(emrBenhNhan != null){
            //I.HÀNH CHÍNH - 1 + 2 + 3 + 5 + 7 + 10. Tên bệnh nhân + Sinh ngày + Giới tính + Dân tộc + Địa chỉ + Đối tượng BHYT, Số BHYT
            addPatientInfor(patient, patientRole, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //I.HÀNH CHÍNH - 11. Họ tên người báo tin + sđt người báo tin       
            addGuardianPerson(patient, emrBenhNhan);    
                        
            //Thông tin cha mẹ bệnh nhân 
            addPatientParents(doc, emrDanhSachHoSoBenhAn);
            
            //Nơi làm việc          
            addEmployer(doc, emrBenhNhan);          
        }
        
        //Đơn vị chủ quản và Trưởng phòng kế hoạch tổng hợp
        addDvcq_Tpkhth(doc, emrCoSoKhamBenh, emrDanhSachHoSoBenhAn);
        
        //15. Cơ sở khám chữa bệnh
        Component1 componentOf = CDAFactory.eINSTANCE.createComponent1();
        doc.setComponentOf(componentOf);
        
        
        //create encompassing encounter
        var ecpEncounter = CDAFactory.eINSTANCE.createEncompassingEncounter();
        componentOf.setEncompassingEncounter(ecpEncounter);
        
        //24. Mã Y tế
        String maYte = emrDanhSachHoSoBenhAn.getMayte();
        if(!StringUtils.isEmpty(maYte)){
            II ecpEncounterId = DatatypesFactory.eINSTANCE.createII();
            ecpEncounterId.setExtension(maYte);
            ecpEncounterId.setRoot(properties.getProperty("OID_DINHDANHYTEQUOCGIA", "OID_DINHDANHYTEQUOCGIA"));
            ecpEncounter.getIds().add(ecpEncounterId);
        }           
        if(emrQuanLyNguoiBenh != null){
            //II.QUẢN LÝ NGƯỜI BỆNH - 12+18. Ngày vào viện/Ngày ra viện
            input_OutputHospital(ecpEncounter, emrDanhSachHoSoBenhAn, emrParameters, emrCoSoKhamBenh);
        }
                
        if(emrBenhAn != null){
            //16 + 17. Ngày làm bệnh án + Bác sĩ làm bệnh án
            addPersonCreateDocument(doc, emrBenhAn);
        }

        if(lstEmrVaoKhoa.size() > 0){
            var emrVaoKhoa = lstEmrVaoKhoa.get(0);
            //20+21. Ngày ký bệnh án + Bác sĩ trưởng khoa 
            addDean(doc, emrVaoKhoa, properties);       
        }
        
        //22. Giám đốc bệnh viện 
        addHospitalDirector(doc, emrCoSoKhamBenh, emrDanhSachHoSoBenhAn, properties);
    }
    
    public static void addTongKetSanKhoa(HsbaPhysicianAttendingDischargeSummarySection physicianAttendingSection, Properties properties, EmrHoSoBenhAn emrDanhSachHoSoBenhAn){
        var tongKetSanKhoaSection = HSBAFactory.eINSTANCE.createHsbaTongKetSanKhoaSection().init();
        physicianAttendingSection.addSection(tongKetSanKhoaSection);
        String tongKetSanKhoaTitle = properties.getProperty("TKBA_TONGKETSANKHOA_TITLE", "TKBA_TONGKETSANKHOA_TITLE");
        addSectionTitle(tongKetSanKhoaSection, tongKetSanKhoaTitle);                        
        
        CE tkskCodeTag = tongKetSanKhoaSection.getCode();
        String tkskCodeCda = properties.getProperty("TKBA_TONGKETSANKHOA_CODECDA", "TKBA_TONGKETSANKHOA_CODECDA");
        if(tkskCodeTag != null){
            tkskCodeTag.setCode(tkskCodeCda);
        }
        //add HsbaJson
        var emrTongKetSanKhoa = emrDanhSachHoSoBenhAn.emrTongKetSanKhoa;
        if(emrTongKetSanKhoa != null){
            var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
            ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
            String jsonText = JsonUtil.dumpObject(emrTongKetSanKhoa);
            if(!StringUtils.isEmpty(jsonText)){
                jsonObsText.addText(jsonText);
                hsbaJsonObs.setText(jsonObsText);
                tongKetSanKhoaSection.addObservation(hsbaJsonObs);
            }               
        }
                    
        setSectionData(tongKetSanKhoaSection, emrDanhSachHoSoBenhAn.getTongketsankhoaCda());    
    }
    
    public static void addPhuongPhapDieuTriUngBuou(HsbaPhysicianAttendingDischargeSummarySection physicianAttendingSection, Properties properties, EmrHoSoBenhAn hsba){
        var ppDieuTriUngBuouSection = HSBAFactory.eINSTANCE.createHsbaCkPhuongPhapDieuTriUngBuouSection().init();
        physicianAttendingSection.addSection(ppDieuTriUngBuouSection);
        String tongKetSanKhoaTitle = properties.getProperty("TKBA_PPDIEUTRIUNGBUOU_TITLE", "TKBA_PPDIEUTRIUNGBUOU_TITLE");
        addSectionTitle(ppDieuTriUngBuouSection, tongKetSanKhoaTitle);                      
                
        //add HsbaJson
        var emrPpDieuTriUngBuou = hsba.emrBenhAn.emrCkPhuongPhapDieuTriUngBuou;
        if(emrPpDieuTriUngBuou != null){
            var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
            ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
            String jsonText = JsonUtil.dumpObject(emrPpDieuTriUngBuou);
            if(!StringUtils.isEmpty(jsonText)){
                jsonObsText.addText(jsonText);
                hsbaJsonObs.setText(jsonObsText);
                ppDieuTriUngBuouSection.addObservation(hsbaJsonObs);
            }               
        }
                    
        setSectionData(ppDieuTriUngBuouSection, hsba.emrTongKetRaVien.phuongphapdieutri);   
    }
    
    public static void addTinhTrangRaVienMat(HsbaPhysicianAttendingDischargeSummarySection physicianAttendingSection, Properties properties, EmrHoSoBenhAn hsba){
        var tinhTrangRaVienMatSection = HSBAFactory.eINSTANCE.createHsbaCkTinhTrangRaVienMatSection().init();
        physicianAttendingSection.addSection(tinhTrangRaVienMatSection);
        String tongKetSanKhoaTitle = properties.getProperty("TKBA_TINHTRANGRAVIENMAT_TITLE", "TKBA_TINHTRANGRAVIENMAT_TITLE");
        addSectionTitle(tinhTrangRaVienMatSection, tongKetSanKhoaTitle);                        
                
        //add HsbaJson
        var emrTinhTrangRaVienMat = hsba.emrBenhAn.emrCkTinhTrangRaVienMat;
        if(emrTinhTrangRaVienMat != null){
            var hsbaJsonObs = HSBAFactory.eINSTANCE.createHsbaJsonObservation().init();         
            ED jsonObsText = DatatypesFactory.eINSTANCE.createED();
            String jsonText = JsonUtil.dumpObject(emrTinhTrangRaVienMat);
            if(!StringUtils.isEmpty(jsonText)){
                jsonObsText.addText(jsonText);
                hsbaJsonObs.setText(jsonObsText);
                tinhTrangRaVienMatSection.addObservation(hsbaJsonObs);
            }               
        }
                    
        setSectionData(tinhTrangRaVienMatSection, hsba.emrTongKetRaVien.tinhtrangnguoibenh);    
    }
    
    public static void addPhysicianAttendingDischargeSummarySection(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters, Properties properties){
        try {
            var emrTongKetRaVien = emrDanhSachHoSoBenhAn.emrTongKetRaVien;
            var physicianAttendingSection = HSBAFactory.eINSTANCE.createHsbaPhysicianAttendingDischargeSummarySection().init();
            doc.addSection(physicianAttendingSection);
            String tkbaTitle = properties.getProperty("TONGKETBENHAN_TITLE", "TONGKETBENHAN_TITLE");
            addSectionTitle(physicianAttendingSection, tkbaTitle);
            
            StringBuilder physicianAttendingText = new StringBuilder();
            
            
            if(emrTongKetRaVien != null){
                //Quá trình bệnh lý và diễn biến lâm sàng
                var hospitalCourseSection = HSBAFactory.eINSTANCE.createHsbaHospitalCourseSection().init();
                physicianAttendingSection.addSection(hospitalCourseSection);
                String qtBenhLyDienBenLamSangTitle = properties.getProperty("TKBA_DIENBIENLAMSANG_TITLE", "TKBA_DIENBIENLAMSANG_TITLE");
                addSectionTitle(hospitalCourseSection, qtBenhLyDienBenLamSangTitle);
                
                String hospitalCourseText = emrTongKetRaVien.dienbienlamsang;
                setSectionData(hospitalCourseSection, hospitalCourseText);
                
                //Kết quả cận lâm sàng
                var dischargeStudiesSection = HSBAFactory.eINSTANCE.createHsbaHospitalDischargeStudiesSummarySection().init();
                physicianAttendingSection.addSection(dischargeStudiesSection);
                String kqCanLamSangTitle = properties.getProperty("TKBA_XETNGHIEMCANLAMSANG_TITLE", "TKBA_XETNGHIEMCANLAMSANG_TITLE");
                addSectionTitle(dischargeStudiesSection, kqCanLamSangTitle);
                
                String dischargeStudiesText = emrTongKetRaVien.canlamsang;
                setSectionData(dischargeStudiesSection, dischargeStudiesText);
                
                //Phương pháp điều trị
                var planOfCareNoteSection = HSBAFactory.eINSTANCE.createHsbaPlanOfCareNoteSection().init();
                physicianAttendingSection.addSection(planOfCareNoteSection);
                String ppDieuTriTitle = properties.getProperty("TKBA_PHUONGPHAPDIEUTRI_TITLE", "TKBA_PHUONGPHAPDIEUTRI_TITLE");
                addSectionTitle(planOfCareNoteSection, ppDieuTriTitle);
                
                String planOfCareNoteText = emrTongKetRaVien.phuongphapdieutri;
                setSectionData(planOfCareNoteSection, planOfCareNoteText);                  
                
                //Tình trạng người bệnh ra viện
                var dischargePhysicalSection = HSBAFactory.eINSTANCE.createHsbaHospitalDischargePhysicalSection().init();
                physicianAttendingSection.addSection(dischargePhysicalSection);
                String tinhTrangRaVienTitle = properties.getProperty("TKBA_TINHTRANGNGUOIBENHRAVIEN_TITLE", "TKBA_TINHTRANGNGUOIBENHRAVIEN_TITLE");
                addSectionTitle(dischargePhysicalSection, tinhTrangRaVienTitle);
                
                String dischargePhysicalText = emrTongKetRaVien.tinhtrangnguoibenh;
                setSectionData(dischargePhysicalSection, dischargePhysicalText);
                
                //Hướng điều trị và các chế độ tiếp theo
                var dischargeInstructionsSection = HSBAFactory.eINSTANCE.createHsbaHospitalDischargeInstructionsSection().init();
                physicianAttendingSection.addSection(dischargeInstructionsSection);
                String huongDieuTriVaCheDoTitle = properties.getProperty("TKBA_HUONGDIEUTRI_CHEDO_TITLE", "TKBA_HUONGDIEUTRI_CHEDO_TITLE");
                addSectionTitle(dischargeInstructionsSection, huongDieuTriVaCheDoTitle);
                String dischargeInstructionsText = emrTongKetRaVien.chidandieutri;
                setSectionData(dischargeInstructionsSection, dischargeInstructionsText);
                
                //Tổng kết sản khoa
                addTongKetSanKhoa(physicianAttendingSection, properties, emrDanhSachHoSoBenhAn);    
                
                //Phương pháp điều trị ung bướu
                addPhuongPhapDieuTriUngBuou(physicianAttendingSection, properties, emrDanhSachHoSoBenhAn);   
                
                //Tình trạng ra viện Mắt
                addTinhTrangRaVienMat(physicianAttendingSection, properties, emrDanhSachHoSoBenhAn);
            }
            
            setSectionData(physicianAttendingSection, physicianAttendingText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    
    public static void addProcedureCodeTag(HsbaProceduresProcedure procedure, String code, String codeSystem, String displayName){
        try {
            CD procedureCode = DatatypesFactory.eINSTANCE.createCD();       
            if(!StringUtils.isEmpty(code)){
                procedureCode.setCode(code);
            }
            if(!StringUtils.isEmpty(codeSystem)){
                procedureCode.setCodeSystem(codeSystem);
            }
            if(!StringUtils.isEmpty(displayName)){
                procedureCode.setDisplayName(displayName);
            }                   
            procedure.setCode(procedureCode);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addProcedureAssignedEntity(HsbaProceduresProcedure proc, AssignedEntity assignedEntity, String assignPersonName){
        try {
            II assignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            assignedEntity.getIds().add(assignedEntityId);
            var performer = CDAFactory.eINSTANCE.createPerformer2();
            proc.getPerformers().add(performer);                    
            performer.setAssignedEntity(assignedEntity);
            
            
            org.openhealthtools.mdht.uml.cda.Person requestPerson = CDAFactory.eINSTANCE.createPerson();
            PN personName = DatatypesFactory.eINSTANCE.createPN();      
            if(!StringUtils.isEmpty(assignPersonName)){
                personName.addText(assignPersonName);
            }
            requestPerson.getNames().add(personName);
            assignedEntity.setAssignedPerson(requestPerson);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addProcedureObservationContent(Observation obs, String obsTitle, String obsText, EmrDmContent emrDmMaBenh, String codeSystem, Map<String, String> emrParameters){
        try {
            II obsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            obs.getIds().add(obsId);
            ED preliminaryObsText = DatatypesFactory.eINSTANCE.createED();
            String convertObsText = STR_NA_VALUE;
            if(obsText != null){
                convertObsText = obsText;
            }
            preliminaryObsText.addText(convertObsText);
            obs.setText(preliminaryObsText);
            
            if(emrDmMaBenh != null){
                addCdObservationValueTag(obs, emrDmMaBenh.maicd, codeSystem, emrDmMaBenh.ten);      
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addProcedureExternalDocument(HsbaProceduresProcedure procedure, String url) throws IOException{   
        if(!StringUtils.isEmpty(url)){    
            String encodedExternalDocumentUrl = URLEncoder.encode(url, "UTF-8");
            
            var reference = CDAFactory.eINSTANCE.createReference();
            reference.setTypeCode(x_ActRelationshipExternalReference.REFR);
            procedure.getReferences().add(reference);
            
            var externalDocument = HSBAFactory.eINSTANCE.createHsbaExternalDocument().init();
            reference.setExternalDocument(externalDocument);        
            TEL referenceValue = DatatypesFactory.eINSTANCE.createTEL();                
                                
            referenceValue.setValue(encodedExternalDocumentUrl);
            ED externalDocumentText = DatatypesFactory.eINSTANCE.createED();
            externalDocumentText.setReference(referenceValue);
            externalDocument.setText(externalDocumentText); 
        }                
    }
    
    public static void addEmrPhauThuatThuThuat(HsbaProceduresSection proceduresSection, EmrPhauThuatThuThuat emrPhauThuatThuThuat, Map<String, String> emrParameters, List<EmrFileDinhKem> lstEmrQuanLyFileDinhKemPttts, Properties properties, StringBuilder procedureSectionText) throws IOException{
        if(emrPhauThuatThuThuat != null){
            var procedure = HSBAFactory.eINSTANCE.createHsbaProceduresProcedure().init();
            proceduresSection.addProcedure(procedure);
            II procedureId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            procedure.getIds().add(procedureId);                
            
            String splitSymbol  = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
            String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
            
            var emrDmPtt = emrPhauThuatThuThuat.emrDmPhauThuThuat;   
            if(emrDmPtt != null){   
                String code = emrDmPtt.maicd;
                String codeSystem = emrParameters.get("emr_dm_phau_thu_thuat");     
                String displayName = emrDmPtt.ten;
                addProcedureCodeTag(procedure, code, codeSystem, displayName);      
                
                //Phương pháp phẫu thủ thuật                                
                String phuongPhapPtt = emrDmPtt.ten;
                ED phuongPhap = DatatypesFactory.eINSTANCE.createED();
                if(!StringUtils.isEmpty(phuongPhapPtt)){
                    phuongPhap.addText(phuongPhapPtt);
                    String phuongPhapPttTitle = properties.getProperty("PTT_PHUONGPHAP_TITLE", "PTT_PHUONGPHAP_TITLE");
                    procedureSectionText.append(phuongPhapPttTitle).append(contentDelimiter).append(phuongPhapPtt).append(splitSymbol);
                }
                procedure.setText(phuongPhap);                  
                
                            
            }
            //Ngày giờ phẫu thuật
            Date ptttDate = emrPhauThuatThuThuat.ngaygiopttt;
            IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
            if(ptttDate != null){               
                String effectiveDate = getGMTDate(ptttDate);
                if(!StringUtils.isEmpty(effectiveDate)){
                    effectiveTime.setValue(effectiveDate);
                    String ngayGioPttTitle = properties.getProperty("PTT_NGAYGIO_TITLE", "PTT_NGAYGIO_TITLE");
                    procedureSectionText.append(ngayGioPttTitle).append(contentDelimiter).append(effectiveDate).append(splitSymbol);
                }
                procedure.setEffectiveTime(effectiveTime);              
            }
            
            
            //Bác sĩ thực hiện phẫu thủ thuật
            var surgeonAssignedEntity = HSBAFactory.eINSTANCE.createHsbaSurgeonAssignedEntity().init();   
            String surgeonPersonName = emrPhauThuatThuThuat.bacsithuchien;
            if(!StringUtils.isEmpty(surgeonPersonName)){
                addProcedureAssignedEntity(procedure, surgeonAssignedEntity, surgeonPersonName);
                String bacsiThucHienTitle = properties.getProperty("PTT_BACSITHUCHIEN_TITLE", "PTT_BACSITHUCHIEN_TITLE");
                procedureSectionText.append(bacsiThucHienTitle).append(contentDelimiter).append(surgeonPersonName).append(splitSymbol);
            }

            //Bác sĩ gây mê
            var anesthesiologistEntity = HSBAFactory.eINSTANCE.createHsbaAnesthesiologistAssignedEntity().init();
            String anesthesologistPersonName = emrPhauThuatThuThuat.bacsygayme;
            if(!StringUtils.isEmpty(anesthesologistPersonName)){
                addProcedureAssignedEntity(procedure, anesthesiologistEntity, anesthesologistPersonName);
                String bacsiGayMeTitle = properties.getProperty("PTT_BACSIGAYME_TITLE", "PTT_BACSIGAYME_TITLE");
                procedureSectionText.append(bacsiGayMeTitle).append(contentDelimiter).append(anesthesologistPersonName).append(splitSymbol);
            }       
            if(procedureSectionText.length() > 0){
                procedureSectionText.setLength(procedureSectionText.length() - 1);
            }
            
            //Hội đồng phẫu thủ thuật
            var lstEmrHoiDongPttts = emrPhauThuatThuThuat.emrThanhVienPttts;                
            if(lstEmrHoiDongPttts != null){
                String hoidongBacsiTitle = properties.getProperty("PT_CACBACSITRONGHOIDONG_TITLE", "PT_CACBACSITRONGHOIDONG_TITLE");    
                String splitBacSi = properties.getProperty("HC_SPLIT_BACSIHOICHAN", "HC_SPLIT_BACSIHOICHAN");
                procedureSectionText.append(hoidongBacsiTitle).append(contentDelimiter);
                for (var emrHoiDongPtttItem : lstEmrHoiDongPttts) {
                    String bacSiHoiDongPtt = emrHoiDongPtttItem.tenbacsi;
                    if(!StringUtils.isEmpty(bacSiHoiDongPtt)){                                
                        var consultantAssignedEntity = HSBAFactory.eINSTANCE.createHsbaConsultantPhysicianAssignedEntity().init();                                
                        //vai trò
                        var vaitro = emrHoiDongPtttItem.emrDmVaiTro;
                        if(vaitro != null){
                            CE codeTag = consultantAssignedEntity.getCode();
                            if(codeTag != null){
                                ED vaitroED = DatatypesFactory.eINSTANCE.createED(vaitro.ma);
                                codeTag.setOriginalText(vaitroED);
                            }
                        }
                        
                        addProcedureAssignedEntity(procedure, consultantAssignedEntity, bacSiHoiDongPtt);
                        procedureSectionText.append(bacSiHoiDongPtt).append(splitBacSi);
                    }
                }
            }
            
            //Chẩn đoán trước phẫu thuật    
            var preoperativeER = CDAFactory.eINSTANCE.createEntryRelationship();
            preoperativeER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            procedure.getEntryRelationships().add(preoperativeER);  
            
            var preoperativeDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaPreoperativeDiagnosisObservation().init();
            preoperativeER.setObservation(preoperativeDiagnosisObs);
            String preoperativeTitle = properties.getProperty("CD_RAVIEN_CDTRUOCPT_TITLE", "CD_RAVIEN_CDTRUOCPT_TITLE");
            String icd10CodeSystem = emrParameters.get("emr_dm_ma_benh");
            
            var preoperativePrimaryER = CDAFactory.eINSTANCE.createEntryRelationship();
            preoperativePrimaryER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            preoperativeDiagnosisObs.getEntryRelationships().add(preoperativePrimaryER);        
            
            var preoperativePrimaryObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init();  
            preoperativePrimaryER.setObservation(preoperativePrimaryObs);                           
            addProcedureObservationContent(preoperativePrimaryObs,preoperativeTitle , emrPhauThuatThuThuat.motachandoantruocpt, emrPhauThuatThuThuat.emrDmMaBenhChandoantruoc, icd10CodeSystem, emrParameters);
        
            //Chẩn đoán sau phẫu thuật
            var postoperativeER = CDAFactory.eINSTANCE.createEntryRelationship();
            postoperativeER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            procedure.getEntryRelationships().add(postoperativeER);
            
            var postoperativeDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaPostoperativeDiagnosisObservation().init();
            postoperativeER.setObservation(postoperativeDiagnosisObs);
            String posoperativeTitle = properties.getProperty("CD_RAVIEN_CDSAUPT_TITLE", "CD_RAVIEN_CDSAUPT_TITLE");    
            
            var postoperativePrimaryER = CDAFactory.eINSTANCE.createEntryRelationship();
            postoperativePrimaryER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            postoperativeDiagnosisObs.getEntryRelationships().add(postoperativePrimaryER);
            
            var postoperativePrimaryObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init(); 
            postoperativePrimaryER.setObservation(postoperativePrimaryObs);     
            addProcedureObservationContent(postoperativePrimaryObs, posoperativeTitle, emrPhauThuatThuThuat.motachandoansaupt, emrPhauThuatThuThuat.emrDmMaBenhChandoansau, icd10CodeSystem, emrParameters);       
            
            //Chỉ định phẫu thuật
            var procedureIndicationsER = CDAFactory.eINSTANCE.createEntryRelationship();
            procedureIndicationsER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            procedure.getEntryRelationships().add(procedureIndicationsER);
            
            var procedureIndicationsAct = HSBAFactory.eINSTANCE.createHsbaProcedureIndicationsAct().init();
            II procedureIndicationsActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            procedureIndicationsAct.getIds().add(procedureIndicationsActId);
            procedureIndicationsER.setAct(procedureIndicationsAct);
            String chiDinhPt = emrPhauThuatThuThuat.chidinhptt;
            if(!StringUtils.isEmpty(chiDinhPt)){
                ED chiDinhPtText = DatatypesFactory.eINSTANCE.createED(chiDinhPt);
                procedureIndicationsAct.setText(chiDinhPtText);
            }
            
            //Phương pháp vô cảm
            var anesthesiaER = CDAFactory.eINSTANCE.createEntryRelationship();
            anesthesiaER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            procedure.getEntryRelationships().add(anesthesiaER);
            
            var anesthesiaAct = HSBAFactory.eINSTANCE.createHsbaAnesthesiaAct().init();
            II anesthesiaActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            anesthesiaAct.getIds().add(anesthesiaActId);
            anesthesiaER.setAct(anesthesiaAct);
            String phuongPhapVoCam = emrPhauThuatThuThuat.phuongphapvocam;
            if(!StringUtils.isEmpty(phuongPhapVoCam)){
                ED phuongPhapVoCamText = DatatypesFactory.eINSTANCE.createED(phuongPhapVoCam);
                anesthesiaAct.setText(phuongPhapVoCamText);
            }
            
            //Lược đồ phẫu thủ thuật                
            String luocDoPtt = emrPhauThuatThuThuat.luocdoptt;
            if(!StringUtils.isEmpty(luocDoPtt)){
                var procedureChartER = CDAFactory.eINSTANCE.createEntryRelationship();
                procedureChartER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                procedure.getEntryRelationships().add(procedureChartER);
                var procedureChartAct = HSBAFactory.eINSTANCE.createHsbaProcedureChartAct().init();
                procedureChartER.setAct(procedureChartAct);
                
                procedureChartAct.setText(DatatypesFactory.eINSTANCE.createED(luocDoPtt));                      
            }
                
            //Trình tự (diễn tiến) phẫu thủ thuật
            var procedureDescER = CDAFactory.eINSTANCE.createEntryRelationship();
            procedureDescER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            procedure.getEntryRelationships().add(procedureDescER);
            
            var procedureDescAct = HSBAFactory.eINSTANCE.createHsbaProcedureDescriptionAct().init();
            II procedureDescActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            procedureDescAct.getIds().add(procedureDescActId);
            procedureDescER.setAct(procedureDescAct);           
            String trinhTuPtt = emrPhauThuatThuThuat.trinhtuptt;
            if(!StringUtils.isEmpty(trinhTuPtt)){
                procedureDescAct.setText(DatatypesFactory.eINSTANCE.createED(trinhTuPtt));
            }
            
            //Tài liệu đính kèm 
            if(lstEmrQuanLyFileDinhKemPttts != null){
                for (var item : lstEmrQuanLyFileDinhKemPttts) {
                    addProcedureExternalDocument(procedure, item.url);        
                }
            }
        }       
    }
    
    public static void addProcedures(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters,  Properties properties){
        try {
            var lstEmrPhauThuatThuThuat = emrDanhSachHoSoBenhAn.getEmrPhauThuatThuThuats();          
            var proceduresSection = HSBAFactory.eINSTANCE.createHsbaProceduresSection().init();
            doc.addSection(proceduresSection);      
            II proceduresSectionId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            proceduresSection.setId(proceduresSectionId);
            String ptttTitle = properties.getProperty("TKBA_DS_PHAUTHUATTHUTHUAT_TITLE", "TKBA_DS_PHAUTHUATTHUTHUAT_TITLE");
            String endLine = properties.getProperty("ENDLINE_DELIMITER", "ENDLINE_DELIMITER");;
            addSectionTitle(proceduresSection, ptttTitle);
                        
            StringBuilder proceduresText = new StringBuilder();

            if(lstEmrPhauThuatThuThuat != null && lstEmrPhauThuatThuThuat.size() > 0){          
                for (var ptttItem : lstEmrPhauThuatThuThuat) {
                    var lstEmrQuanLyFileDinhKemPttts = ptttItem.emrFileDinhKemPttts;           
                    addEmrPhauThuatThuThuat(proceduresSection, ptttItem, emrParameters, lstEmrQuanLyFileDinhKemPttts, properties, proceduresText);
                    if(proceduresText.length() > 0){
                        proceduresText.append(endLine);
                    }
                }   
                
                //Loại phẫu thuật/thủ thuật
                var ptSupply = CDAFactory.eINSTANCE.createSupply();
                ptSupply.setCode(DatatypesFactory.eINSTANCE.createCS(PT_TYPE));
                ptSupply.setMoodCode(x_DocumentSubstanceMood.EVN);              
                ptSupply.setText(DatatypesFactory.eINSTANCE.createED(String.valueOf(true)));
                proceduresSection.addSupply(ptSupply);
                
                var ttSupply = CDAFactory.eINSTANCE.createSupply();
                ttSupply.setCode(DatatypesFactory.eINSTANCE.createCS(TT_TYPE));             
                ttSupply.setMoodCode(x_DocumentSubstanceMood.EVN);              
                ttSupply.setText(DatatypesFactory.eINSTANCE.createED(String.valueOf(true)));
                proceduresSection.addSupply(ttSupply);
            }       

            setSectionData(proceduresSection, proceduresText.toString());   
        } catch (Exception e) {
            e.printStackTrace();
        }                       
    }
    
    public static void addEncounterAssignedEntity(HsbaConsultationEncounter consultationEncounter, AssignedEntity assignedEntity, String assignPersonName){
        try {
            var performer = CDAFactory.eINSTANCE.createPerformer2();
            consultationEncounter.getPerformers().add(performer);       
            performer.setAssignedEntity(assignedEntity);
            
            org.openhealthtools.mdht.uml.cda.Person requestPerson = CDAFactory.eINSTANCE.createPerson();
            PN personName = DatatypesFactory.eINSTANCE.createPN();      
            if(!StringUtils.isEmpty(assignPersonName)){
                personName.addText(assignPersonName);
            }
            requestPerson.getNames().add(personName);
            assignedEntity.setAssignedPerson(requestPerson);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addEncounterExternalDocument(HsbaConsultationEncounter consultationEncounter, String externalDocumentUrl) throws IOException{   
        if(!StringUtils.isEmpty(externalDocumentUrl)){    
            String encodedExternalDocumentUrl = URLEncoder.encode(externalDocumentUrl, "UTF-8");
            var reference = CDAFactory.eINSTANCE.createReference();
            reference.setTypeCode(x_ActRelationshipExternalReference.REFR);
            consultationEncounter.getReferences().add(reference);
            
            var externalDocument = HSBAFactory.eINSTANCE.createHsbaExternalDocument().init();
            reference.setExternalDocument(externalDocument);        
            TEL referenceValue = DatatypesFactory.eINSTANCE.createTEL();                        
                                
            referenceValue.setValue(encodedExternalDocumentUrl);
            ED externalDocumentText = DatatypesFactory.eINSTANCE.createED();
            externalDocumentText.setReference(referenceValue);
            externalDocument.setText(externalDocumentText); 
        }
    }
    
    public static void addEmrHoiChan(HsbaHospitalConsultationsSection hospitalConsultationsSection, EmrHoiChan hoiChanItem, StringBuilder hospitalConsultationsText, String contentDelimiter, String splitDelimiter, String endLineDelimiter, List<EmrFileDinhKem> lstEmrQuanLyFileDinhKemHcs,  Properties properties) throws IOException{
        if(hoiChanItem != null){
            var consultationEncounter = HSBAFactory.eINSTANCE.createHsbaConsultationEncounter().init();
            hospitalConsultationsSection.addEncounter(consultationEncounter);
            
            //Ngày hội chẩn
            IVL_TS consultationEffectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
            consultationEncounter.setEffectiveTime(consultationEffectiveTime);
            Date ngayHoiChan = hoiChanItem.ngaythuchien;
            String thoiDiemHoiChan = "";
            if(ngayHoiChan != null){
                thoiDiemHoiChan = CDAExportUtil.getGMTDate(ngayHoiChan);
                if(thoiDiemHoiChan != null){
                    consultationEffectiveTime.setValue(thoiDiemHoiChan);
                    String ngayHoiChanTitle = properties.getProperty("HC_NGAYHOICHAN_TITLE", "HC_NGAYHOICHAN_TITLE");               
                    hospitalConsultationsText.append(ngayHoiChanTitle).append(contentDelimiter).append(thoiDiemHoiChan).append(splitDelimiter);
                }
            }   
            
            //Tham chiếu khoa điều trị
            EmrVaoKhoa emrVaoKhoa = hoiChanItem.emrVaoKhoa;
            if(emrVaoKhoa != null){         
                var emrDmKhoaDieuTri = emrVaoKhoa.emrDmKhoaDieuTri;
                if(emrDmKhoaDieuTri != null){
                    var reference = CDAFactory.eINSTANCE.createReference();
                    reference.setTypeCode(x_ActRelationshipExternalReference.REFR);
                    consultationEncounter.getReferences().add(reference);
                    
                    II referenceDepartmentId = DatatypesFactory.eINSTANCE.createII();
                    for (java.util.Map.Entry<String, EmrVaoKhoa> referenceDepartmentItem : referenceDepartments.entrySet()) {
                        EmrVaoKhoa refEmrVaoKhoa = referenceDepartmentItem.getValue();
                        if(emrVaoKhoa.equals(refEmrVaoKhoa)){
                            referenceDepartmentId.setRoot(referenceDepartmentItem.getKey());
                            referenceDepartmentId.setExtension(String.valueOf(referenceDepartmentItem.getValue().getId()));
                        }
                    }
                    
                    var admissionToDepartmentExternalAct = HSBAFactory.eINSTANCE.createHsbaAdmissionToDepartmentExternalAct().init();
                    II admissionToDepartmentExternalActId = referenceDepartmentId;
                    admissionToDepartmentExternalAct.getIds().add(admissionToDepartmentExternalActId);
                    reference.setExternalAct(admissionToDepartmentExternalAct);
                    
                    String codeCda = emrDmKhoaDieuTri.maicd;
                    if(!StringUtils.isEmpty(codeCda)){
                        CD codeTag = admissionToDepartmentExternalAct.getCode();
                        codeTag.setCode(codeCda);
                    }
                }
                
            }
        
            //Các bác sĩ tham gia hội chẩn
            var lstEmrHoiDongHoiChans = hoiChanItem.emrThanhVienHoiChans;
            if(lstEmrHoiDongHoiChans != null){
                String ngayHoiChanTitle = properties.getProperty("HC_CACBACSIHOICHAN_TITLE", "HC_CACBACSIHOICHAN_TITLE");   
                String splitBacSi = properties.getProperty("HC_SPLIT_BACSIHOICHAN", "HC_SPLIT_BACSIHOICHAN");
                hospitalConsultationsText.append(ngayHoiChanTitle).append(contentDelimiter);
                for (var hoiDongHoiChanItem : lstEmrHoiDongHoiChans) {
                    String bacSiHoiChan = hoiDongHoiChanItem.tenbacsi;
                    if(!StringUtils.isEmpty(bacSiHoiChan)){
                        var consultantAssignedEntity = HSBAFactory.eINSTANCE.createHsbaConsultantPhysicianAssignedEntity().init();
                        II consultantAssignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                        consultantAssignedEntity.getIds().add(consultantAssignedEntityId);
                        addEncounterAssignedEntity(consultationEncounter, consultantAssignedEntity, bacSiHoiChan);
                        hospitalConsultationsText.append(bacSiHoiChan).append(splitBacSi);
                        
                        //vai trò
                        var vaitro = hoiDongHoiChanItem.emrDmVaiTro;
                        if(vaitro != null){
                            CE codeTag = consultantAssignedEntity.getCode();
                            if(codeTag != null){
                                ED vaitroED = DatatypesFactory.eINSTANCE.createED(vaitro.ma);
                                codeTag.setOriginalText(vaitroED);
                            }
                        }
                    }
                }
                //remove end split doctor 
                if(splitBacSi.equals(hospitalConsultationsText.substring(hospitalConsultationsText.length()-1))){
                    hospitalConsultationsText.setLength(hospitalConsultationsText.length() - 1);
                }
                hospitalConsultationsText.append(splitDelimiter);
            }
            //Tóm tắt diễn biến hội chẩn
            var consultationReviewER = CDAFactory.eINSTANCE.createEntryRelationship();
            consultationReviewER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            consultationEncounter.getEntryRelationships().add(consultationReviewER);
            
            var reviewOfSystemObs = HSBAFactory.eINSTANCE.createHsbaReviewOfSystemObservation().init();
            consultationReviewER.setObservation(reviewOfSystemObs);
            String tomTatDienBien = hoiChanItem.tomtatdienbien;
            if(!StringUtils.isEmpty(tomTatDienBien)){
                ED tomTatDienBienText = DatatypesFactory.eINSTANCE.createED(tomTatDienBien);
                reviewOfSystemObs.setText(tomTatDienBienText);
                String tomTatDienBienTitle = properties.getProperty("HC_TOMTATDIENBIEN_TITLE", "HC_TOMTATDIENBIEN_TITLE");              
                hospitalConsultationsText.append(tomTatDienBienTitle).append(contentDelimiter).append(tomTatDienBien).append(splitDelimiter);
            }
            
            //Kết luận
            var consultationDiagnosisER = CDAFactory.eINSTANCE.createEntryRelationship();
            consultationDiagnosisER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            consultationEncounter.getEntryRelationships().add(consultationDiagnosisER);
            
            var conclusionDiagnosisAct = HSBAFactory.eINSTANCE.createHsbaDiagnosisConclusionsAct().init();
            consultationDiagnosisER.setAct(conclusionDiagnosisAct);
            String ketLuan = hoiChanItem.ketluanhoichan;
            if(!StringUtils.isEmpty(ketLuan)){
                ED ketLuanText = DatatypesFactory.eINSTANCE.createED(ketLuan);
                conclusionDiagnosisAct.setText(ketLuanText);
                String ketLuanTitle = properties.getProperty("HC_KETLUAN_TITLE", "HC_KETLUAN_TITLE");               
                hospitalConsultationsText.append(ketLuanTitle).append(contentDelimiter).append(ketLuan).append(splitDelimiter);
            }
            
            //Hướng điều trị
            var planOfCareER = CDAFactory.eINSTANCE.createEntryRelationship();
            planOfCareER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            consultationEncounter.getEntryRelationships().add(planOfCareER);
            
            var planOfCareAct = HSBAFactory.eINSTANCE.createHsbaPlanOfCareAct().init();
            planOfCareER.setAct(planOfCareAct);
            String huongDieuTri = hoiChanItem.huongdieutri;
            if(!StringUtils.isEmpty(huongDieuTri)){
                ED huongDieuTriText = DatatypesFactory.eINSTANCE.createED(huongDieuTri);
                planOfCareAct.setText(huongDieuTriText);
                String huongDieuTriTitle = properties.getProperty("HC_HUONGDIEUTRI_TITLE", "HC_HUONGDIEUTRI_TITLE");                
                hospitalConsultationsText.append(huongDieuTriTitle).append(contentDelimiter).append(huongDieuTri).append(splitDelimiter);
            }           
            
            //Tài liệu đính kèm     
            if(lstEmrQuanLyFileDinhKemHcs != null){
                for (var item : lstEmrQuanLyFileDinhKemHcs) {
                    addEncounterExternalDocument(consultationEncounter, item.url);
                }           
            }
            
            if(hospitalConsultationsText.length() > 0){
                if(splitDelimiter.equals(hospitalConsultationsText.substring(hospitalConsultationsText.length() - 1))){
                    hospitalConsultationsText.setLength(hospitalConsultationsText.length() - 1);
                }
            }
            hospitalConsultationsText.append(endLineDelimiter);
        }   
    }
    
    public static void addHospitalConsultations(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters,  Properties properties){              
        try {
            var hospitalConsultationsSection = HSBAFactory.eINSTANCE.createHsbaHospitalConsultationsSection().init();
            doc.addSection(hospitalConsultationsSection);
            II hospitalConsultationsSectionId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            hospitalConsultationsSection.setId(hospitalConsultationsSectionId);
            String hospitalConsultationsTitle = properties.getProperty("THONGTIN_HOICHAN_TITLE", "THONGTIN_HOICHAN_TITLE");
            String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
            String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
            String endLineDelimiter = properties.getProperty("ENDLINE_DELIMITER", "ENDLINE_DELIMITER");
            addSectionTitle(hospitalConsultationsSection, hospitalConsultationsTitle);
            StringBuilder hospitalConsultationsText = new StringBuilder();      

            var lstEmrVaoKhoas = emrDanhSachHoSoBenhAn.emrVaoKhoas;
            if(lstEmrVaoKhoas != null){
                for (var vaoKhoaItem : lstEmrVaoKhoas) {
                    var lstHoiChans = vaoKhoaItem.emrHoiChans;
                    if(lstHoiChans != null){
                        for (var item : lstHoiChans) {    
                            addEmrHoiChan(hospitalConsultationsSection, item, hospitalConsultationsText, contentDelimiter, splitDelimiter, endLineDelimiter, item.emrFileDinhKemHoiChans, properties);
                        }
                    }
                }
            }       
            if(hospitalConsultationsText.length() > 0){
                hospitalConsultationsText.setLength(hospitalConsultationsText.length() - 1);
            }
            setSectionData(hospitalConsultationsSection, hospitalConsultationsText.toString());     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addOrganizerAssignedEntity(Organizer organizer, AssignedEntity assignedEntity, String assignPersonName){
        try {
            var performer = CDAFactory.eINSTANCE.createPerformer2();
            organizer.getPerformers().add(performer);       
            performer.setAssignedEntity(assignedEntity);
            II assignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            assignedEntity.getIds().add(assignedEntityId);
            
            org.openhealthtools.mdht.uml.cda.Person requestPerson = CDAFactory.eINSTANCE.createPerson();
            PN personName = DatatypesFactory.eINSTANCE.createPN();      
            if(!StringUtils.isEmpty(assignPersonName)){
                personName.addText(assignPersonName);
            }
            requestPerson.getNames().add(personName);
            assignedEntity.setAssignedPerson(requestPerson);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addEmrQuaTrinhDieuTri(Act act, EmrQuaTrinhDieuTri qtDieuTriItem, StringBuilder generalMedicineProgressText,  Properties properties){
        if(qtDieuTriItem != null){
            var physicalMedicineER = CDAFactory.eINSTANCE.createEntryRelationship();
            physicalMedicineER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            act.getEntryRelationships().add(physicalMedicineER);
        
            String ngayGioDieuTriTitle = properties.getProperty("DT_NGAYGIODIEUTRI_TITLE", "DT_NGAYGIODIEUTRI_TITLE");
            String dienBienTitle = properties.getProperty("DT_DIENBIENNGUOIBENH_TITLE", "DT_DIENBIENNGUOIBENH_TITLE");
            String yLenhTitle = properties.getProperty("DT_YLENHBACSI_TITLE", "DT_YLENHBACSI_TITLE");
            String bacSiRaYlenhTitle = properties.getProperty("DT_BACSIDIEUTRI_TITLE", "DT_BACSIDIEUTRI_TITLE");
            String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
            String endLineDelimiter = properties.getProperty("ENDLINE_DELIMITER", "ENDLINE_DELIMITER") ;
            String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
        
            var physicalMedicineOrganizer = HSBAFactory.eINSTANCE.createHsbaPhysicalMedicineOrganizer().init();
            physicalMedicineER.setOrganizer(physicalMedicineOrganizer);
            II physicalMedicineOrganizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            physicalMedicineOrganizer.getIds().add(physicalMedicineOrganizerId);
            
            //Ngày giờ điều trị
            IVL_TS phycicalMedicineEffectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
            physicalMedicineOrganizer.setEffectiveTime(phycicalMedicineEffectiveTime);
                    
            Date ngayDieuTri = qtDieuTriItem.ngaydieutri;
            if(ngayDieuTri != null){
                String thoiDiemDieuTri = CDAExportUtil.getGMTDate(ngayDieuTri);          
                if(thoiDiemDieuTri != null){
                    generalMedicineProgressText.append(ngayGioDieuTriTitle).append(contentDelimiter).append(thoiDiemDieuTri).append(splitDelimiter);
                    phycicalMedicineEffectiveTime.setValue(thoiDiemDieuTri);
                }
            }   
            //Diễn biến người bệnh  
            var reviewOfSystemComponent = CDAFactory.eINSTANCE.createComponent4();
            reviewOfSystemComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            physicalMedicineOrganizer.getComponents().add(reviewOfSystemComponent);
            
            var reviewOfSystemObs = HSBAFactory.eINSTANCE.createHsbaReviewOfSystemObservation().init();
            reviewOfSystemComponent.setObservation(reviewOfSystemObs);
            String dienBien = qtDieuTriItem.dienbien;
            if(!StringUtils.isEmpty(dienBien)){
                generalMedicineProgressText.append(dienBienTitle).append(contentDelimiter).append(dienBien).append(splitDelimiter);
                ED dienBienText = DatatypesFactory.eINSTANCE.createED(dienBien);
                reviewOfSystemObs.setText(dienBienText);
            }
            //Y lệnh bác sĩ     
            var planOfTreatmentComponent = CDAFactory.eINSTANCE.createComponent4();
            planOfTreatmentComponent.setTypeCode(ActRelationshipHasComponent.COMP);
            physicalMedicineOrganizer.getComponents().add(planOfTreatmentComponent);
            
            var planOfTreatmentAct = HSBAFactory.eINSTANCE.createHsbaPlanOfTreatmentAct().init();
            II planOfTreatmentActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            planOfTreatmentAct.getIds().add(planOfTreatmentActId);
            planOfTreatmentComponent.setAct(planOfTreatmentAct);
            String yLenh = qtDieuTriItem.ylenh;
            if(!StringUtils.isEmpty(yLenh)){
                generalMedicineProgressText.append(yLenhTitle).append(contentDelimiter).append(yLenh).append(splitDelimiter);
                ED yLenhText = DatatypesFactory.eINSTANCE.createED(yLenh);
                planOfTreatmentAct.setText(yLenhText);
            }
            //Bác sĩ ra y lệnh      
            var dischargeDoctorAssignedEntity = HSBAFactory.eINSTANCE.createHsbaDischargeDoctorAssignedEntity().init();
            String bacSiRaYlenh = qtDieuTriItem.bacsiraylenh;
            if(!StringUtils.isEmpty(bacSiRaYlenh)){
                generalMedicineProgressText.append(bacSiRaYlenhTitle).append(contentDelimiter).append(bacSiRaYlenh).append(splitDelimiter);
                addOrganizerAssignedEntity(physicalMedicineOrganizer, dischargeDoctorAssignedEntity, bacSiRaYlenh);
            }
            
            if(generalMedicineProgressText.length() > 0){
                generalMedicineProgressText.setLength(generalMedicineProgressText.length() - 1);
                generalMedicineProgressText.append(endLineDelimiter);
            }
        }   
    }
    
    
    public static void addEmrDieuTri(HsbaGeneralMedicineProgressSection generalMedicineSection, EmrVaoKhoa emrVaoKhoa, EmrDieuTri dieuTriItem, StringBuilder generalMedicineProgressText, List<EmrFileDinhKem> lstEmrQuanLyFileDinhKemDieuTris,  Properties properties) throws IOException{
        if(dieuTriItem != null){
            var generalMedicineEntry = CDAFactory.eINSTANCE.createEntry();        
            generalMedicineSection.getEntries().add(generalMedicineEntry);
            generalMedicineEntry.setTypeCode(x_ActRelationshipEntry.DRIV);
            
            var physicalMedicineAct = HSBAFactory.eINSTANCE.createHsbaPhysicalMedicineAct().init();
            generalMedicineEntry.setAct(physicalMedicineAct);       
            
            //Số tờ điều trị
            String sotoDtri = dieuTriItem.sotodieutri;
            if(!StringUtils.isEmpty(sotoDtri)){
                II physicalMedicineActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                physicalMedicineActId.setExtension(sotoDtri);
                physicalMedicineAct.getIds().add(physicalMedicineActId);
            }
                            
            //Tham chiếu vào khoa điều trị
            var emrDmKhoaDieuTri = emrVaoKhoa.emrDmKhoaDieuTri;
            if(emrDmKhoaDieuTri != null){
                var reference = CDAFactory.eINSTANCE.createReference();
                reference.setTypeCode(x_ActRelationshipExternalReference.REFR);
                physicalMedicineAct.getReferences().add(reference);             
                
                II referenceDepartmentId = DatatypesFactory.eINSTANCE.createII();
                for (java.util.Map.Entry<String, EmrVaoKhoa> referenceDepartmentItem : referenceDepartments.entrySet()) {
                    EmrVaoKhoa refEmrVaoKhoa = referenceDepartmentItem.getValue();
                    if(emrVaoKhoa.equals(refEmrVaoKhoa)){       
                        referenceDepartmentId.setRoot(referenceDepartmentItem.getKey());
                        referenceDepartmentId.setExtension(String.valueOf(referenceDepartmentItem.getValue().getId()));
                    }
                }
                
                var admissionToDepartmentExternalAct = HSBAFactory.eINSTANCE.createHsbaAdmissionToDepartmentExternalAct().init();
                II admissionToDepartmentExternalActId = referenceDepartmentId;
                admissionToDepartmentExternalAct.getIds().add(admissionToDepartmentExternalActId);
                reference.setExternalAct(admissionToDepartmentExternalAct);
                
                String codeCda = emrDmKhoaDieuTri.maicd;
                if(!StringUtils.isEmpty(codeCda)){
                    CD codeTag = admissionToDepartmentExternalAct.getCode();
                    codeTag.setCode(codeCda);
                }
            }
        
            //Nội dung điều trị
            var lstEmrQuaTrinhDieuTris = dieuTriItem.getEmrQuaTrinhDieuTris();
            if(lstEmrQuaTrinhDieuTris != null){
                for (var quaTrinhDtItem : lstEmrQuaTrinhDieuTris) {
                    addEmrQuaTrinhDieuTri(physicalMedicineAct, quaTrinhDtItem, generalMedicineProgressText, properties);
                }
            }
            
            //Tài liệu đính kèm 
            if(lstEmrQuanLyFileDinhKemDieuTris != null){
                for (var item : lstEmrQuanLyFileDinhKemDieuTris) {
                    addActExternalDocument(physicalMedicineAct, item.url);
                }           
            }
        }
    }
    
    public static void addGeneralMedicineProgress(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters,  Properties properties){
        try {
            var generalMedicineSection = HSBAFactory.eINSTANCE.createHsbaGeneralMedicineProgressSection().init();
            doc.addSection(generalMedicineSection);
            II generalMedicineSectionId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            generalMedicineSection.setId(generalMedicineSectionId);
            String generalMedicineTitle = properties.getProperty("THONGTIN_DIEUTRI_TITLE", "THONGTIN_DIEUTRI_TITLE");
            addSectionTitle(generalMedicineSection, generalMedicineTitle);
            
            StringBuilder generalMedicineProgressText = new StringBuilder();
                    
            var lstEmrVaoKhoas = emrDanhSachHoSoBenhAn.getEmrVaoKhoas();
            if(lstEmrVaoKhoas != null){
                for (var vaoKhoaItem : lstEmrVaoKhoas) {
                    var lstDieuTris = vaoKhoaItem.emrDieuTris;
                    if(lstDieuTris != null){
                        for (var item : lstDieuTris) {    
                            addEmrDieuTri(generalMedicineSection, vaoKhoaItem, item, generalMedicineProgressText, item.emrFileDinhKemDieuTris, properties);
                        }
                    }
                }
            }               
                
            setSectionData(generalMedicineSection, generalMedicineProgressText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addEmrQuaTrinhChamSoc(Act act, EmrQuaTrinhChamSoc qtChamSocItem, StringBuilder nurseProgressNoteText,  Properties properties){
        try {
            if(qtChamSocItem != null){
                var nurseProgressNoteER = CDAFactory.eINSTANCE.createEntryRelationship();
                nurseProgressNoteER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                act.getEntryRelationships().add(nurseProgressNoteER);
            
                var nurseProgressNoteOrganizer = HSBAFactory.eINSTANCE.createHsbaNurseProgressNoteOrganizer().init();
                nurseProgressNoteER.setOrganizer(nurseProgressNoteOrganizer);
                II nurseProgressNoteOrganizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                nurseProgressNoteOrganizer.getIds().add(nurseProgressNoteOrganizerId);
                
                String ngayGioChamSocTitle = properties.getProperty("CS_NGAYGIOCHAMSOC_TITLE", "CS_NGAYGIOCHAMSOC_TITLE");
                String dienBienTitle = properties.getProperty("CS_DIENBIENNGUOIBENH_TITLE", "CS_DIENBIENNGUOIBENH_TITLE");
                String thucHienYlenhTitle = properties.getProperty("CS_THUCHIENYLENH_TITLE", "CS_THUCHIENYLENH_TITLE");
                String yTaChamSocTitle = properties.getProperty("CS_YTACHAMSOC_TITLE", "CS_YTACHAMSOC_TITLE");
                String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
                String endLineDelimiter = properties.getProperty("ENDLINE_DELIMITER", "ENDLINE_DELIMITER") ;
                String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
                
                //Ngày giờ chăm sóc
                IVL_TS nurseProgressNoteEffectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
                nurseProgressNoteOrganizer.setEffectiveTime(nurseProgressNoteEffectiveTime);
                        
                Date ngayChamSoc = qtChamSocItem.ngaychamsoc;
                if(ngayChamSoc != null){
                    String thoiDiemChamSoc = CDAExportUtil.getGMTDate(ngayChamSoc);
                    if(thoiDiemChamSoc != null){
                        nurseProgressNoteText.append(ngayGioChamSocTitle).append(contentDelimiter).append(thoiDiemChamSoc).append(splitDelimiter);
                        nurseProgressNoteEffectiveTime.setValue(thoiDiemChamSoc);
                    }
                }   
                //Diễn biến người bệnh      
                var reviewOfSystemComponent = CDAFactory.eINSTANCE.createComponent4();
                reviewOfSystemComponent.setTypeCode(ActRelationshipHasComponent.COMP);
                nurseProgressNoteOrganizer.getComponents().add(reviewOfSystemComponent);
                
                var reviewOfSystemObs = HSBAFactory.eINSTANCE.createHsbaReviewOfSystemObservation().init();
                reviewOfSystemComponent.setObservation(reviewOfSystemObs);
                String dienBien = qtChamSocItem.theodoidienbien;
                if(!StringUtils.isEmpty(dienBien)){
                    nurseProgressNoteText.append(dienBienTitle).append(contentDelimiter).append(dienBien).append(splitDelimiter);
                    ED dienBienText = DatatypesFactory.eINSTANCE.createED(dienBien);
                    reviewOfSystemObs.setText(dienBienText);
                }
                //Thực hiện y lệnh      
                var treatmentInformationComponent = CDAFactory.eINSTANCE.createComponent4();
                treatmentInformationComponent.setTypeCode(ActRelationshipHasComponent.COMP);
                nurseProgressNoteOrganizer.getComponents().add(treatmentInformationComponent);
                
                var treatmentInformationAct = HSBAFactory.eINSTANCE.createHsbaTreatmentInformationAct().init();
                II treatmentInformationActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                treatmentInformationAct.getIds().add(treatmentInformationActId);
                treatmentInformationComponent.setAct(treatmentInformationAct);
                String yLenh = qtChamSocItem.thuchienylenh;
                if(!StringUtils.isEmpty(yLenh)){
                    nurseProgressNoteText.append(thucHienYlenhTitle).append(contentDelimiter).append(yLenh).append(splitDelimiter);
                    ED yLenhText = DatatypesFactory.eINSTANCE.createED(yLenh);
                    treatmentInformationAct.setText(yLenhText);
                }
                //Y tá chăm sóc
                var nursingOccupationAssignedEntity = HSBAFactory.eINSTANCE.createHsbaNursingOccupationAssignedEntity().init();
                String yTaChamSoc = qtChamSocItem.ytachamsoc;
                if(!StringUtils.isEmpty(yTaChamSoc)){
                    nurseProgressNoteText.append(yTaChamSocTitle).append(contentDelimiter).append(yTaChamSoc).append(splitDelimiter);
                    addOrganizerAssignedEntity(nurseProgressNoteOrganizer, nursingOccupationAssignedEntity, yTaChamSoc);
                }
                if(nurseProgressNoteText.length() > 0){
                    nurseProgressNoteText.setLength(nurseProgressNoteText.length() - 1);
                    nurseProgressNoteText.append(endLineDelimiter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addEmrChamSoc(HsbaNurseProgressNoteSection nurseProgressNoteSection, EmrVaoKhoa emrVaoKhoa, EmrChamSoc chamSocItem, StringBuilder nurseProgressNoteText, List<EmrFileDinhKem> lstEmrQuanLyFileDinhKemChamSocs,  Properties properties) throws IOException{
        if(chamSocItem != null){
            var nurseProgressNoteEntry = CDAFactory.eINSTANCE.createEntry();      
            nurseProgressNoteSection.getEntries().add(nurseProgressNoteEntry);
            nurseProgressNoteEntry.setTypeCode(x_ActRelationshipEntry.DRIV);
            
            var nursingProcedureAct = HSBAFactory.eINSTANCE.createHsbaNursingProcedureAct().init();
            nurseProgressNoteEntry.setAct(nursingProcedureAct); 
            
            //Số phiếu chăm sóc
            String sophieu = chamSocItem.sotochamsoc;
            if(!StringUtils.isEmpty(sophieu)){
                II nursingProcedureActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                nursingProcedureActId.setExtension(sophieu);
                nursingProcedureAct.getIds().add(nursingProcedureActId);
            }
            
            var emrDmKhoaDieuTri = emrVaoKhoa.emrDmKhoaDieuTri;
            if(emrDmKhoaDieuTri != null){
                var reference = CDAFactory.eINSTANCE.createReference();
                reference.setTypeCode(x_ActRelationshipExternalReference.REFR);
                nursingProcedureAct.getReferences().add(reference);             
                
                II referenceDepartmentId = DatatypesFactory.eINSTANCE.createII();
                for (java.util.Map.Entry<String, EmrVaoKhoa> referenceDepartmentItem : referenceDepartments.entrySet()) {
                    EmrVaoKhoa refEmrVaoKhoa = referenceDepartmentItem.getValue();
                    if(emrVaoKhoa.equals(refEmrVaoKhoa)){
                        referenceDepartmentId.setRoot(referenceDepartmentItem.getKey());
                        referenceDepartmentId.setExtension(String.valueOf(referenceDepartmentItem.getValue().getId()));
                    }
                }
                
                var admissionToDepartmentExternalAct = HSBAFactory.eINSTANCE.createHsbaAdmissionToDepartmentExternalAct().init();
                II admissionToDepartmentExternalActId = referenceDepartmentId;
                admissionToDepartmentExternalAct.getIds().add(admissionToDepartmentExternalActId);
                reference.setExternalAct(admissionToDepartmentExternalAct);             
                
                String codeCda = emrDmKhoaDieuTri.maicd;
                if(!StringUtils.isEmpty(codeCda)){
                    CD codeTag = admissionToDepartmentExternalAct.getCode();
                    codeTag.setCode(codeCda);
                }
            }
        
            //Nội dung chăm sóc
            var lstEmrQuaTrinhChamSocs = chamSocItem.getEmrQuaTrinhChamSocs();
            if(lstEmrQuaTrinhChamSocs != null){
                for (var quaTrinhCsItem : lstEmrQuaTrinhChamSocs) {  
                    addEmrQuaTrinhChamSoc(nursingProcedureAct, quaTrinhCsItem, nurseProgressNoteText, properties);
                }
            }
            
            //Tài liệu đính kèm 
            if(lstEmrQuanLyFileDinhKemChamSocs != null){
                for (var item : lstEmrQuanLyFileDinhKemChamSocs) {
                    addActExternalDocument(nursingProcedureAct, item.url);
                }           
            }
        }
    }
    
    
    public static void addHsbaNurseProgressNote(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters,  Properties properties){      
        try {
            var nurseProgressNoteSection = HSBAFactory.eINSTANCE.createHsbaNurseProgressNoteSection().init();
            doc.addSection(nurseProgressNoteSection);
            II nurseProgressNoteSectionId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            nurseProgressNoteSection.setId(nurseProgressNoteSectionId);
            String nurseProgressNoteTitle = properties.getProperty("THONGTIN_CHAMSOC_TITLE", "THONGTIN_CHAMSOC_TITLE");
            addSectionTitle(nurseProgressNoteSection, nurseProgressNoteTitle);
            StringBuilder nurseProgressNoteText = new StringBuilder();

            List<EmrVaoKhoa> lstEmrVaoKhoas = emrDanhSachHoSoBenhAn.emrVaoKhoas;
            if(lstEmrVaoKhoas != null){
                for (EmrVaoKhoa vaoKhoaItem : lstEmrVaoKhoas) { 
                    var lstEmrChamSocs = vaoKhoaItem.emrChamSocs;
                    if(lstEmrChamSocs != null){
                        for (var item : lstEmrChamSocs) { 
                            addEmrChamSoc(nurseProgressNoteSection, vaoKhoaItem, item, nurseProgressNoteText, item.emrFileDinhKemChamSocs, properties);
                        }
                    }
                }
            }                           
            setSectionData(nurseProgressNoteSection, nurseProgressNoteText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addOrganizerVitalSignObservation(Organizer organizer, Observation obs, Double value, String unit){
        try {
            var component = CDAFactory.eINSTANCE.createComponent4();
            component.setTypeCode(ActRelationshipHasComponent.COMP);
            organizer.getComponents().add(component);
            
            component.setObservation(obs);
            II obsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            obs.getIds().add(obsId);
            
            if(value != null && unit != null){
                var valueTag = DatatypesFactory.eINSTANCE.createPQ(value, unit);
                obs.getValues().add(valueTag);
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }           
    }
    
    public static void addEmrChucNangSongChiTiet(Act act, EmrChucNangSongChiTiet chucNangSongCtItem, Properties properties, StringBuilder vitalSignsFunctionalText){
        try {
            if(chucNangSongCtItem != null){
                var vitalSignsFunctionalER = CDAFactory.eINSTANCE.createEntryRelationship();
                vitalSignsFunctionalER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                act.getEntryRelationships().add(vitalSignsFunctionalER);
            
                var vitalSignsOrganizer = HSBAFactory.eINSTANCE.createHsbaVitalSignsOrganizer().init();
                vitalSignsFunctionalER.setOrganizer(vitalSignsOrganizer);
                II vitalSignsOrganizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                vitalSignsOrganizer.getIds().add(vitalSignsOrganizerId);                
                
                //Ngày giờ theo dõi
                IVL_TS vitalSignsFunctionalEffectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
                vitalSignsOrganizer.setEffectiveTime(vitalSignsFunctionalEffectiveTime);
                        
                Date ngayTheoDoi = chucNangSongCtItem.ngaytheodoi;
                if(ngayTheoDoi != null){
                    String thoiDiemTheoDoi = CDAExportUtil.getGMTDate(ngayTheoDoi);
                    if(thoiDiemTheoDoi != null){
                        vitalSignsFunctionalEffectiveTime.setValue(thoiDiemTheoDoi);
                    }
                }   
                
                //Chi tiết thông tin sinh hiệu
                String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
                String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
                //--- Mạch      
                var hearRateObs = HSBAFactory.eINSTANCE.createHsbaHeartRateObservation().init();
                Double mach = chucNangSongCtItem.mach;
                String machUnit = properties.getProperty("BA_DHST_MACH_UNIT", "BA_DHST_MACH_UNIT");
                if(mach != null){           
                    addOrganizerVitalSignObservation(vitalSignsOrganizer, hearRateObs, mach, machUnit);
                    vitalSignsFunctionalText.append(properties.getProperty("BA_DHST_MACH_TITLE", "BA_DHST_MACH_TITLE")).append(contentDelimiter)
                                            .append(mach).append(properties.getProperty("BA_DHST_MACH_UNIT", "BA_DHST_MACH_UNIT")).append(splitDelimiter);
                }
                //--- Nhiệt độ
                var bodyTemperatureObs = HSBAFactory.eINSTANCE.createHsbaBodyTemperatureObservation().init();
                Double nhietDo = chucNangSongCtItem.nhietdo;
                String nhietDoUnit = properties.getProperty("BA_DHST_NHIETDO_UNIT", "BA_DHST_NHIETDO_UNIT");
                if(nhietDo != null){            
                    addOrganizerVitalSignObservation(vitalSignsOrganizer, bodyTemperatureObs, nhietDo, nhietDoUnit);
                    vitalSignsFunctionalText.append(properties.getProperty("BA_DHST_NHIETDO_TITLE", "BA_DHST_NHIETDO_TITLE")).append(contentDelimiter)
                                            .append(nhietDo).append(properties.getProperty("BA_DHST_NHIETDO_UNIT", "BA_DHST_NHIETDO_UNIT")).append(splitDelimiter);
                }
                //--- Huyết áp tâm thu
                var bpSystolicObs = HSBAFactory.eINSTANCE.createHsbaBPSystolicObservation().init();
                Integer haTamThu = chucNangSongCtItem.huyetapcao;
                String haTamThuUnit = properties.getProperty("BA_DHST_HUYETAP_UNIT", "BA_DHST_HUYETAP_UNIT");
                if(haTamThu != null){
                    Double haTamThuValue = (double)haTamThu;
                    addOrganizerVitalSignObservation(vitalSignsOrganizer, bpSystolicObs, haTamThuValue, haTamThuUnit);
                    vitalSignsFunctionalText.append(properties.getProperty("BA_DHST_HUYETAPTAMTHU_TITLE", "BA_DHST_HUYETAPTAMTHU_TITLE")).append(contentDelimiter)
                                            .append(haTamThuValue).append(properties.getProperty("BA_DHST_HUYETAP_UNIT", "BA_DHST_HUYETAP_UNIT")).append(splitDelimiter);
                }
                //--- Huyết áp tâm trương 
                var bpDiastolicObs = HSBAFactory.eINSTANCE.createHsbaBPDiastolicObservation().init();
                Integer haTamTruong = chucNangSongCtItem.huyetapthap;
                String haTamTruongUnit = properties.getProperty("BA_DHST_HUYETAP_UNIT", "BA_DHST_HUYETAP_UNIT");
                if(haTamThu != null){
                    Double haTamTruongValue = (double)haTamTruong;
                    addOrganizerVitalSignObservation(vitalSignsOrganizer, bpDiastolicObs, haTamTruongValue, haTamTruongUnit);
                    vitalSignsFunctionalText.append(properties.getProperty("BA_DHST_HUYETAPTAMTRUONG_TITLE", "BA_DHST_HUYETAPTAMTRUONG_TITLE")).append(contentDelimiter)
                                            .append(haTamTruongValue).append(properties.getProperty("BA_DHST_HUYETAP_UNIT", "BA_DHST_HUYETAP_UNIT")).append(splitDelimiter);
                }
                
                //--- Nhịp thở
                var respiratoryRateObs = HSBAFactory.eINSTANCE.createHsbaRespiratoryRateObservation().init();
                Integer nhipTho = chucNangSongCtItem.nhiptho;
                String nhipThoUnit = properties.getProperty("BA_DHST_NHIPTHO_UNIT", "BA_DHST_NHIPTHO_UNIT");
                if(nhipTho != null){
                    Double nhipThoValue = (double)nhipTho;
                    addOrganizerVitalSignObservation(vitalSignsOrganizer, respiratoryRateObs, nhipThoValue, nhipThoUnit);
                    vitalSignsFunctionalText.append(properties.getProperty("BA_DHST_NHIPTHO_TITLE", "BA_DHST_NHIPTHO_TITLE")).append(contentDelimiter)
                                            .append(nhipThoValue).append(properties.getProperty("BA_DHST_NHIPTHO_UNIT", "BA_DHST_NHIPTHO_UNIT")).append(splitDelimiter);
                }
                
                //--- Cân nặng
                var weightMeasuredObs = HSBAFactory.eINSTANCE.createHsbaWeightMeasuredObservation().init();
                Integer canNang = (int)(double) chucNangSongCtItem.cannang;
                String canNangUnit = properties.getProperty("BA_DHST_CANNANG_UNIT", "BA_DHST_CANNANG_UNIT");
                if(canNang != null){
                    Double canNangValue = (double)canNang;
                    addOrganizerVitalSignObservation(vitalSignsOrganizer, weightMeasuredObs, canNangValue, canNangUnit);
                    vitalSignsFunctionalText.append(properties.getProperty("BA_DHST_CANNANG_TITLE", "BA_DHST_CANNANG_TITLE")).append(contentDelimiter)
                                            .append(canNangValue).append(properties.getProperty("BA_DHST_CANNANG_UNIT", "BA_DHST_CANNANG_UNIT")).append(splitDelimiter);
                }       
                vitalSignsFunctionalText.setLength(vitalSignsFunctionalText.length() - 1);  
                
                //Y tá theo dõi
                var nursingOccupationAssignedEntity = HSBAFactory.eINSTANCE.createHsbaNursingOccupationAssignedEntity().init();
                String yTaTheoDoi = chucNangSongCtItem.ytatheodoi;
                if(!StringUtils.isEmpty(yTaTheoDoi)){
                    addOrganizerAssignedEntity(vitalSignsOrganizer, nursingOccupationAssignedEntity, yTaTheoDoi);
                }   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addEmrChucNangSong(HsbaVitalSignsFunctionalSection vitalSignsFunctionalSection, EmrVaoKhoa emrVaoKhoa, EmrChucNangSong chucNangSongItem, StringBuilder vitalSignsFunctionalText, List<EmrFileDinhKem> lstEmrQuanLyFileDinhKemChucNangSongs,  Properties properties) throws IOException{
        if(chucNangSongItem != null){
            var vitalSignsFunctionalEntry = CDAFactory.eINSTANCE.createEntry();       
            vitalSignsFunctionalSection.getEntries().add(vitalSignsFunctionalEntry);
            vitalSignsFunctionalEntry.setTypeCode(x_ActRelationshipEntry.DRIV);
            
            var vitalSignsFunctionalAct = HSBAFactory.eINSTANCE.createHsbaVitalSignsFunctionalAct().init();

            //Số phiếu theo dõi
            II vitalSignsFunctionalActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            String sophieu = chucNangSongItem.sophieu;
            if(!StringUtils.isEmpty(sophieu)){
                vitalSignsFunctionalActId.setExtension(sophieu);
            }
            vitalSignsFunctionalAct.getIds().add(vitalSignsFunctionalActId);
            vitalSignsFunctionalEntry.setAct(vitalSignsFunctionalAct);                      
            
            var emrDmKhoaDieuTri = emrVaoKhoa.emrDmKhoaDieuTri;
            if(emrDmKhoaDieuTri != null){                   
                var reference = CDAFactory.eINSTANCE.createReference();
                reference.setTypeCode(x_ActRelationshipExternalReference.REFR);
                vitalSignsFunctionalAct.getReferences().add(reference);             
                
                II referenceDepartmentId = DatatypesFactory.eINSTANCE.createII();
                for (java.util.Map.Entry<String, EmrVaoKhoa> referenceDepartmentItem : referenceDepartments.entrySet()) {
                    EmrVaoKhoa refEmrVaoKhoa = referenceDepartmentItem.getValue();
                    if(emrVaoKhoa.equals(refEmrVaoKhoa)){
                        referenceDepartmentId.setRoot(referenceDepartmentItem.getKey());
                        referenceDepartmentId.setExtension(String.valueOf(referenceDepartmentItem.getValue().getId()));
                    }
                }
                
                var admissionToDepartmentExternalAct = HSBAFactory.eINSTANCE.createHsbaAdmissionToDepartmentExternalAct().init();
                II admissionToDepartmentExternalActId = referenceDepartmentId;
                admissionToDepartmentExternalAct.getIds().add(admissionToDepartmentExternalActId);
                reference.setExternalAct(admissionToDepartmentExternalAct);
                
                String codeCda = emrDmKhoaDieuTri.maicd;
                if(!StringUtils.isEmpty(codeCda)){
                    CD codeTag = admissionToDepartmentExternalAct.getCode();
                    codeTag.setCode(codeCda);
                }
            }
    
            //Sinh hiệu
            var lstEmrChucNangSongChiTiets = chucNangSongItem.getEmrChucNangSongChiTiets();
            if(lstEmrChucNangSongChiTiets != null){
                for (var chucNangSongCtItem : lstEmrChucNangSongChiTiets) {
                    addEmrChucNangSongChiTiet(vitalSignsFunctionalAct, chucNangSongCtItem, properties, vitalSignsFunctionalText);
                }
            }
            
            //Tài liệu đính kèm
            if(lstEmrQuanLyFileDinhKemChucNangSongs != null){
                for (var item : lstEmrQuanLyFileDinhKemChucNangSongs) {
                    addActExternalDocument(vitalSignsFunctionalAct, item.url);
                }           
            }
        }       
    }
    
    
    public static void addHsbaVitalSignsFunctional(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters,  Properties properties){
        try {
            var vitalSignsFunctionalSection = HSBAFactory.eINSTANCE.createHsbaVitalSignsFunctionalSection().init();
            doc.addSection(vitalSignsFunctionalSection);
            II vitalSignsFunctionalSectionId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            vitalSignsFunctionalSection.setId(vitalSignsFunctionalSectionId);
            String vitalSignsFunctionalTitle = properties.getProperty("THEODOI_CHUCNANGSONG_TITLE", "THEODOI_CHUCNANGSONG_TITLE");
            addSectionTitle(vitalSignsFunctionalSection, vitalSignsFunctionalTitle);
            StringBuilder vitalSignsFunctionalText = new StringBuilder();

            var lstEmrVaoKhoas = emrDanhSachHoSoBenhAn.emrVaoKhoas;
            for (EmrVaoKhoa vaoKhoaItem : lstEmrVaoKhoas) {
                var lstEmrChucNangSongs = vaoKhoaItem.emrChucNangSongs;
                if(lstEmrChucNangSongs != null){
                    for (var item : lstEmrChucNangSongs) {  
                        addEmrChucNangSong(vitalSignsFunctionalSection, vaoKhoaItem, item, vitalSignsFunctionalText, item.emrFileDinhKemChucNangSongs, properties);
                    }
                }
            }   
            
            setSectionData(vitalSignsFunctionalSection, vitalSignsFunctionalText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addEmrDonThuocChiTiet(HsbaAdministrationOfSubstanceAct administrationOfSubstanceAct, EmrDonThuocChiTiet chiTietDonThuocItem,  Map<String, String> emrParameters, StringBuilder medicationsSectionText, String medicationSplit){
        if(chiTietDonThuocItem != null){
            var substanceAdministration = HSBAFactory.eINSTANCE.createHsbaSubstanceAdministration().init();             
            II substanceAdminId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            substanceAdministration.getIds().add(substanceAdminId);                     
            
            //-- Ngày bắt đầu                       
            IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
            substanceAdministration.getEffectiveTimes().add(effectiveTime);
            IVXB_TS lowTime = DatatypesFactory.eINSTANCE.createIVXB_TS();
            Date startDate = chiTietDonThuocItem.ngaybatdau;
            if(startDate != null){
                String startTime = CDAExportUtil.getGMTDate(startDate);
                lowTime.setValue(startTime);
            }
            effectiveTime.setLow(lowTime);
            
            //-- Ngày kết thúc
            IVXB_TS highTime = DatatypesFactory.eINSTANCE.createIVXB_TS();
            Date endDate = chiTietDonThuocItem.ngayketthuc;
            if(endDate != null){
                String endTime = CDAExportUtil.getGMTDate(endDate);
                highTime.setValue(endTime);
            }
            effectiveTime.setHigh(highTime);
                        
            //-- Thông tin thuốc 
            var emrDmThuoc = chiTietDonThuocItem.emrDmThuoc;
            //String unit = "";
            if(emrDmThuoc != null){
                var substanceAdminER = CDAFactory.eINSTANCE.createEntryRelationship();
                substanceAdminER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                administrationOfSubstanceAct.getEntryRelationships().add(substanceAdminER);
                substanceAdminER.setSubstanceAdministration(substanceAdministration);
                
                var manufacturedProduct = HSBAFactory.eINSTANCE.createHsbaManufacturedProduct().init();
                var consumable = CDAFactory.eINSTANCE.createConsumable();
                substanceAdministration.setConsumable(consumable);
                consumable.setManufacturedProduct(manufacturedProduct);                             
                
                //unit = emrDmThuoc.getDangbaoche();
                var material = CDAFactory.eINSTANCE.createMaterial();
                manufacturedProduct.setManufacturedMaterial(material);
                CE codeTag = DatatypesFactory.eINSTANCE.createCE();
                material.setCode(codeTag);
                
                String codeCda = emrDmThuoc.maicd;
                String displayName = emrDmThuoc.ten;
                String codeSystem = emrParameters.get("emr_dm_thuoc");
                if(!StringUtils.isEmpty(codeCda)){
                    codeTag.setCode(codeCda);                           
                }
                if(!StringUtils.isEmpty(displayName)){
                    codeTag.setDisplayName(displayName);
                    medicationsSectionText.append(displayName).append(medicationSplit);
                    ED originalText = DatatypesFactory.eINSTANCE.createED(displayName);
                    codeTag.setOriginalText(originalText);
                }
                if(!StringUtils.isEmpty(codeSystem))
                {
                    codeTag.setCodeSystem(codeSystem);
                }
                if(medicationsSectionText.length() > 0 && medicationSplit.equals(medicationsSectionText.substring(medicationsSectionText.length() - 1))){
                    medicationsSectionText.setLength(medicationsSectionText.length() - 1);
                }
                
                //-- Tần suất sử dụng
                var emrDmTanXuatDungThuoc = chiTietDonThuocItem.emrDmTanXuatDungThuoc;
                if(emrDmTanXuatDungThuoc != null){
                    String effectiveType = "";//emrDmTanXuatDungThuoc.loai;
                    String effectiveValue = "";//emrDmTanXuatDungThuoc.giatri;
                    String tanxuatCodeCda = emrDmTanXuatDungThuoc.maicd;     
                    String tanxuatTen = emrDmTanXuatDungThuoc.ten; 
                    
                    //dữ liệu Danh mục
                    if(!StringUtils.isEmpty(tanxuatCodeCda)){
                        CD tanxuatCodeTag = DatatypesFactory.eINSTANCE.createCD();
                        tanxuatCodeTag.setCode(tanxuatCodeCda);
                        tanxuatCodeTag.setDisplayName(tanxuatTen);
                        substanceAdministration.setCode(tanxuatCodeTag);
                    }
                    // dữ liệu Giá trị
                    if(LOAI_TANSUAT_DUNGTHUOC_PIVL_TS.equals(effectiveType)){
                        if(effectiveValue != null){
                            var tanSuatEffectiveTime = DatatypesFactory.eINSTANCE.createPIVL_TS();
                            substanceAdministration.getEffectiveTimes().add(tanSuatEffectiveTime);
                            
                            tanSuatEffectiveTime.setOperator(SetOperator.A);
                            var tanSuatPeriod = DatatypesFactory.eINSTANCE.createPQ();
                            Double value = Double.parseDouble(effectiveValue);
                            if(value != null){
                                tanSuatPeriod.setValue(value);                      
                                tanSuatEffectiveTime.setPeriod(tanSuatPeriod);
                            }           
                        }
                    }else if(LOAI_TANSUAT_DUNGTHUOC_TS.equals(effectiveType)){                                            
                        if(!StringUtils.isEmpty(effectiveValue)){
                            effectiveTime.setLow(null);
                            effectiveTime.setHigh(null);
                            effectiveTime.setValue(effectiveValue);                                 
                        }
                    }else if(LOAI_TANSUAT_DUNGTHUOC_EIVL_TS.equals(effectiveType)){
                        var tanSuatEffectiveTime = DatatypesFactory.eINSTANCE.createEIVL_TS();
                        substanceAdministration.getEffectiveTimes().add(tanSuatEffectiveTime);
                        if(!StringUtils.isEmpty(effectiveValue)){             
                            tanSuatEffectiveTime.setOperator(SetOperator.A);                
                            var event = DatatypesFactory.eINSTANCE.createEIVL_event();                               
                            event.setCode(effectiveValue);  
                                                            
                            var timingEvent = TimingEvent.getByName(effectiveValue);
                            if(timingEvent != null){
                                String timingEventCodeValue = timingEvent.getLiteral();
                                event.setCode(timingEventCodeValue);
                            }               
                            tanSuatEffectiveTime.setEvent(event);                                                               
                        }
                    }
                }
                                        
                //-- Đường dùng                     
                CE routeCode = DatatypesFactory.eINSTANCE.createCE();                       
                var emrDmDuongDungThuoc = chiTietDonThuocItem.emrDmDuongDungThuoc;
                if(emrDmDuongDungThuoc != null){
                    String routeCodeCda = emrDmDuongDungThuoc.maicd;
                    if(!StringUtils.isEmpty(routeCodeCda)){
                        routeCode.setCode(routeCodeCda);
                    }
                    String routeCodeSystem = emrParameters.get("emr_dm_duong_dung_thuoc");
                    if(!StringUtils.isEmpty(routeCodeSystem)){
                        routeCode.setCodeSystem(routeCodeSystem);                               
                    }
                    String routeDisplayName = emrDmDuongDungThuoc.ten;
                    if(!StringUtils.isEmpty(routeDisplayName)){
                        routeCode.setDisplayName(routeDisplayName);
                    }
                }
                substanceAdministration.setRouteCode(routeCode);
                
                //-- Liều lượng 
                var doseQuantity = DatatypesFactory.eINSTANCE.createIVL_PQ();
                String lieuLuong = chiTietDonThuocItem.lieuluongdung;      
                if(lieuLuong != null){
                    Double lieuDung = Double.parseDouble(lieuLuong);
                    if(lieuDung != null){
                        doseQuantity.setValue(lieuDung);
                    }
                }       
                
                substanceAdministration.setDoseQuantity(doseQuantity);
                
                //-- Chỉ dẫn dùng thuốc 
                var instructionsER = CDAFactory.eINSTANCE.createEntryRelationship();
                instructionsER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                substanceAdministration.getEntryRelationships().add(instructionsER);
                
                var instructionsAct = HSBAFactory.eINSTANCE.createHsbaInstructionsAct().init();
                II instructionsActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                instructionsAct.getIds().add(instructionsActId);
                instructionsER.setAct(instructionsAct);
                
                String chiDan = chiTietDonThuocItem.chidandungthuoc;
                if(!StringUtils.isEmpty(chiDan)){
                    ED chiDanText = DatatypesFactory.eINSTANCE.createED(chiDan);
                    instructionsAct.setText(chiDanText);
                }
            }       
        }   
    }
    
    public static void addMedications(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters,  Properties properties){        
        try {
            var medicationsSection = HSBAFactory.eINSTANCE.createHsbaMedicationsSection().init();
            doc.addSection(medicationsSection);
            II medicationsSectionId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            medicationsSection.setId(medicationsSectionId);
            String medicationsSectionTitle = properties.getProperty("DUNGTHUOC_TITLE", "DUNGTHUOC_TITLE");
            addSectionTitle(medicationsSection, medicationsSectionTitle);
            StringBuilder medicationsSectionText = new StringBuilder(); 
            String ngayKeDonTitle = properties.getProperty("DUNGTHUOC_NGAYKEDON_TITLE", "DUNGTHUOC_NGAYKEDON_TITLE");
            String bacSiKeDonTitle = properties.getProperty("DUNGTHUOC_BACSIKEDON_TITLE", "DUNGTHUOC_BACSIKEDON_TITLE");
            String tenThuocTitle = properties.getProperty("DUNGTHUOC_TENTHUOC_TITLE", "DUNGTHUOC_TENTHUOC_TITLE");
            String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
            String strSplit = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
            String medicationSplit = properties.getProperty("HC_SPLIT_BACSIHOICHAN", "HC_SPLIT_BACSIHOICHAN");                          
            
            var lstEmrDonThuocs = emrDanhSachHoSoBenhAn.getEmrDonThuocs();        
            if(lstEmrDonThuocs != null){            
                for (var donThuocItem : lstEmrDonThuocs) {
                    var medicationsEntry = CDAFactory.eINSTANCE.createEntry();
                    medicationsEntry.setTypeCode(x_ActRelationshipEntry.DRIV);
                    medicationsSection.getEntries().add(medicationsEntry);
                    
                    var lstEmrQuanLyFileDinhKemDonThuocs = donThuocItem.emrFileDinhKemDonThuocs;

                    //Thuốc sử dụng
                    var lstEmrDonThuocChiTiets = donThuocItem.emrDonThuocChiTiets;
                    if(lstEmrDonThuocChiTiets != null){ 
                        var administrationOfSubstanceAct = HSBAFactory.eINSTANCE.createHsbaAdministrationOfSubstanceAct().init();
                        //Số đơn thuốc
                        II administrationOfSubstanceActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                        String sodonthuoc = donThuocItem.sodon;
                        if(!StringUtils.isEmpty(sodonthuoc)){
                            administrationOfSubstanceActId.setExtension(sodonthuoc);
                        }
                        administrationOfSubstanceAct.getIds().add(administrationOfSubstanceActId);
                        medicationsEntry.setAct(administrationOfSubstanceAct);              
                        
                        //Ngày giờ kê đơn                   
                        Date ngayKeDon = donThuocItem.ngaykedon;
                        if(ngayKeDon != null){
                            String ngayKeDonThuoc = CDAExportUtil.getGMTDate(ngayKeDon);
                            if(!StringUtils.isEmpty(ngayKeDonThuoc)){
                                medicationsSectionText.append(ngayKeDonTitle).append(contentDelimiter).append(ngayKeDonThuoc).append(strSplit);
                            }
                        }
                        addActEffectiveTime(administrationOfSubstanceAct, ngayKeDon);
                        
                        //Bác sĩ kê đơn 
                        var dischargeDoctorAssignedEntity = HSBAFactory.eINSTANCE.createHsbaDischargeDoctorAssignedEntity().init();
                        String bacSiKeDon = donThuocItem.bacsikedon;
                        if(!StringUtils.isEmpty(bacSiKeDon)){
                            medicationsSectionText.append(bacSiKeDonTitle).append(contentDelimiter).append(bacSiKeDon).append(strSplit);
                        }
                        ThongTinCanLamSangUtil.addActAssignedEntity(administrationOfSubstanceAct, dischargeDoctorAssignedEntity, bacSiKeDon);
                        medicationsSectionText.append(tenThuocTitle).append(contentDelimiter);
                        
                        for (var item : lstEmrDonThuocChiTiets) {
                            addEmrDonThuocChiTiet(administrationOfSubstanceAct, item, emrParameters, medicationsSectionText, medicationSplit);
                        }   
                        
                        //Tài liệu đính kèm                                     
                        if(lstEmrQuanLyFileDinhKemDonThuocs != null){
                            for (var item : lstEmrQuanLyFileDinhKemDonThuocs) {                            
                                addActExternalDocument(administrationOfSubstanceAct, item.url);
                            }                   
                        }
                    }  
                }
            }
            setSectionData(medicationsSection, medicationsSectionText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    
    public static void generateCDABody(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters, Map<String, Integer> emrDmChucNangs, Properties properties, String srcLocalFolder){
        try {
            //II. QUẢN LÝ NGƯỜI BỆNH
            addPatientManagement(doc, emrDanhSachHoSoBenhAn, properties, emrParameters);
            
            //III.CHẨN ĐOÁN
            addDiagnosis(doc, emrDanhSachHoSoBenhAn, properties, emrParameters);

            //IV.TÌNH TRẠNG RA VIỆN
            addDischargeSummary(doc, emrDanhSachHoSoBenhAn, properties, emrParameters);
        
            //A.BỆNH ÁN
            ThongTinBenhAnUtil.addHistoryAndPhysicalNote(doc, emrDanhSachHoSoBenhAn, emrParameters, properties, srcLocalFolder);
            
            //B.TỔNG KẾT BỆNH ÁN
            addPhysicianAttendingDischargeSummarySection(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Thông tin cận lâm sàng
            ThongTinCanLamSangUtil.addRelevantDiagnostic(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Phẫu thủ thuật  
            addProcedures(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Thông tin hội chẩn
            addHospitalConsultations(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Thông tin điều trị
            addGeneralMedicineProgress(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Thông tin chăm sóc
            addHsbaNurseProgressNote(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Theo dõi chức năng sống
            addHsbaVitalSignsFunctional(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Dùng thuốc
            addMedications(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Chẩn đoán YHCT
            YHCTUtil.addChanDoanYHCT(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Bệnh án YHCT
            YHCTUtil.addBenhAnYHCT(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
            
            //Dùng thuốc YHCT
            YHCTUtil.addYhctMedications(doc, emrDanhSachHoSoBenhAn, emrParameters, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addActExternalDocument(Act act, String externalDocumentUrl) throws IOException{     
        if(!StringUtils.isEmpty(externalDocumentUrl)){
            var reference = CDAFactory.eINSTANCE.createReference();
            reference.setTypeCode(x_ActRelationshipExternalReference.REFR);
            act.getReferences().add(reference);
            
            String encodedExternalDocumentUrl = URLEncoder.encode(externalDocumentUrl, "UTF-8");
            
            var externalDocument = HSBAFactory.eINSTANCE.createHsbaExternalDocument().init();
            
            reference.setExternalDocument(externalDocument);        
            TEL referenceValue = DatatypesFactory.eINSTANCE.createTEL();                    
                                        
            referenceValue.setValue(encodedExternalDocumentUrl);
            ED externalDocumentText = DatatypesFactory.eINSTANCE.createED();
            externalDocumentText.setReference(referenceValue);
            externalDocument.setText(externalDocumentText); 
        }
            
    }
    
    public static void addActEffectiveTime(Act act, Date ngayYeuCau){   
        try {
            IVL_TS effectiveTimeRequest = DatatypesFactory.eINSTANCE.createIVL_TS();
            act.setEffectiveTime(effectiveTimeRequest);
            if(ngayYeuCau != null){             
                String thoiDiemYeuCau = CDAExportUtil.getGMTDate(ngayYeuCau);
                if(thoiDiemYeuCau != null){
                    effectiveTimeRequest.setValue(thoiDiemYeuCau);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
     
    public static String getGMTDate(Date date){
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");     
        String nowAsISO = df.format(date);
        
        return nowAsISO;
    }
    
    public static String convertDateToString(Date date){    
        String result;
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            result = df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
    
    public static void addPatientInfor(Patient patient, PatientRole patientRole, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters,  Properties properties){
        try {
            var emrBenhNhan = emrDanhSachHoSoBenhAn.getEmrBenhNhan();
            //Mã y tế quốc gia(nếu có)
            II maYteQuocGiaId = DatatypesFactory.eINSTANCE.createII(properties.getProperty("OID_DINHDANHYTEQUOCGIA", "OID_DINHDANHYTEQUOCGIA"));
            patientRole.getIds().add(maYteQuocGiaId);
            String maYteQuocGia = emrBenhNhan.iddinhdanhchinh;
            if(!StringUtils.isEmpty(maYteQuocGia)){
                maYteQuocGiaId.setExtension(maYteQuocGia);
            }
            
            //Mã ID thay thế(CMT, Hộ chiếu)
            II maCmndHoChieuId = DatatypesFactory.eINSTANCE.createII(properties.getProperty("OID_CMND_HOCHIEU", "OID_CMND_HOCHIEU"));
            patientRole.getIds().add(maCmndHoChieuId);
            String maCmndHoChieu = emrBenhNhan.iddinhdanhphu;
            if(!StringUtils.isEmpty(maCmndHoChieu)){
                maCmndHoChieuId.setExtension(maCmndHoChieu);
            }
            
            //2. Số bhyt + Tên bệnh nhân                
            String soBHYT = emrBenhNhan.sobhyt;
            if(!StringUtils.isEmpty(soBHYT)){         
                //12. Số thẻ BHYT
                II insuranceId = DatatypesFactory.eINSTANCE.createII(properties.getProperty("OID_DM_SO_BHYT", "OID_DM_SO_BHYT"), soBHYT);               
                patientRole.getIds().add(insuranceId);  
                
                //Ngày hết hạn BHYT
                Date insuranceDate = emrBenhNhan.ngayhethanthebhyt;
                if(insuranceDate != null){
                    String ngayHetHanBh = convertDateToString(insuranceDate);
                    if(!StringUtils.isEmpty(ngayHetHanBh)){
                        Organization po = CDAFactory.eINSTANCE.createOrganization();
                        OrganizationPartOf opo = CDAFactory.eINSTANCE.createOrganizationPartOf();
                        opo.setEffectiveTime(DatatypesFactory.eINSTANCE.createIVL_TS(ngayHetHanBh));
                        
                        po.setAsOrganizationPartOf(opo);
                        patientRole.setProviderOrganization(po);
                    }
                }
            }
            // Họ tên bệnh nhân
            String tenBenhNhan = emrBenhNhan.tendaydu;
            if(!StringUtils.isEmpty(tenBenhNhan)){            
                PN name = DatatypesFactory.eINSTANCE.createPN();
                name.addText(tenBenhNhan);
                patient.getNames().add(name);   
            }
            
            //3. Sinh ngày
            Date ngaySinh = emrBenhNhan.ngaysinh;
            if(ngaySinh != null){
                TS birthTime = DatatypesFactory.eINSTANCE.createTS();
                birthTime.setValue(convertDateToString(ngaySinh));
                patient.setBirthTime(birthTime);
                
                //Tính tuổi
                String age = JasperUtils.getTuoi(emrDanhSachHoSoBenhAn);
                if(!StringUtils.isEmpty(age)){
                    II patientAgeId = DatatypesFactory.eINSTANCE.createII();
                    patientAgeId.setRoot(properties.getProperty("DOCUMENT_OID", "DOCUMENT_OID"));
                    patient.setId(patientAgeId);
                    patientAgeId.setExtension(String.valueOf(age));
                }
            }

            //4. Giới tính      
            CE gender;
            var emrDmGioiTinh = emrBenhNhan.emrDmGioiTinh;
            if(emrDmGioiTinh != null){
                String gioiTinhCodeCDA = emrDmGioiTinh.maicd;                        
                if(!StringUtils.isEmpty(gioiTinhCodeCDA)){
                    if( "M".equals(gioiTinhCodeCDA)){//Male
                        gender = DatatypesFactory.eINSTANCE.createCE(gioiTinhCodeCDA, emrParameters.get("emr_dm_gioi_tinh"));           
                    }else{//Female
                        gender = DatatypesFactory.eINSTANCE.createCE(gioiTinhCodeCDA, emrParameters.get("emr_dm_gioi_tinh"));
                    }   
                    patient.setAdministrativeGenderCode(gender);
                }
            }

            //6. Dân tộc
            var emrDmDanToc = emrBenhNhan.emrDmDanToc;
            if(emrDmDanToc != null){
                CE ethnicGC = DatatypesFactory.eINSTANCE.createCE();
                if(!StringUtils.isEmpty(emrDmDanToc.maicd)){
                    ethnicGC.setCode(emrDmDanToc.maicd);
                }
                String codeSystem = emrParameters.get("emr_dm_dan_toc");
                if(!StringUtils.isEmpty(codeSystem)){
                    ethnicGC.setCodeSystem(codeSystem);
                }                       
                if(!StringUtils.isEmpty(emrDmDanToc.ten)){
                    ethnicGC.setDisplayName(emrDmDanToc.ten);
                }
                patient.setEthnicGroupCode(ethnicGC);
            }
                        
            //8. Địa chỉ
            AD address = DatatypesFactory.eINSTANCE.createAD();     
            String addressDetails = emrBenhNhan.diachi;
            if(!StringUtils.isEmpty(addressDetails)){         
                address.addStreetAddressLine(addressDetails);
            }   
            StringBuilder strAddress = new StringBuilder(); 
            
            var emrDonViHcXaPhuong = emrBenhNhan.emrDmPhuongXa;
            if(emrDonViHcXaPhuong != null){
                String xaPhuongCodeCda = emrDonViHcXaPhuong.maicd;   
                String xaPhuongTen = emrDonViHcXaPhuong.ten;
                if(!StringUtils.isEmpty(xaPhuongCodeCda)){
                    address.addPrecinct(xaPhuongCodeCda);
                }           
                if(!StringUtils.isEmpty(xaPhuongTen)){                    
                    strAddress.append(xaPhuongTen);
                }
            }
            
            var emrDonViHcQuanHuyen = emrBenhNhan.emrDmQuanHuyen;
            if(emrDonViHcQuanHuyen != null){
                String quanHuyenCodeCda = emrDonViHcQuanHuyen.maicd;
                String quanHuyenTen = emrDonViHcQuanHuyen.ten;
                if(!StringUtils.isEmpty(quanHuyenCodeCda)){
                    address.addCity(quanHuyenCodeCda);
                }
                if(!StringUtils.isEmpty(quanHuyenTen)){
                    strAddress.append(properties.getProperty("SPLIT_DELIMITER", "SPLIT_DELIMITER")).append(quanHuyenTen);
                }
            }
            var emrDonViHcTinhThanh = emrBenhNhan.emrDmTinhThanh;
            if(emrDonViHcTinhThanh != null){
                String tinhThanhCodeCda = emrDonViHcTinhThanh.maicd;
                String tinhThanhTen = emrDonViHcTinhThanh.ten;
                if(!StringUtils.isEmpty(tinhThanhCodeCda)){
                    address.addState(tinhThanhCodeCda);
                }
                if(!StringUtils.isEmpty(tinhThanhTen)){
                    strAddress.append(properties.getProperty("SPLIT_DELIMITER", "SPLIT_DELIMITER")).append(tinhThanhTen);
                }
            }
            if(strAddress.toString().length() > 0){
                address.addDirection(strAddress.toString());
            }
            
            patientRole.getAddrs().add(address);
        } catch (Exception e) {
            e.printStackTrace();
        }               
    }
    
    public static void addGuardianPerson(Patient patient, EmrBenhNhan emrBenhNhan){
        try {
            var guardian = CDAFactory.eINSTANCE.createGuardian();      
            org.openhealthtools.mdht.uml.cda.Person guardianPerson = CDAFactory.eINSTANCE.createPerson();
            guardian.setGuardianPerson(guardianPerson); 
                        
            //add guardian person name
            String nguoiBaoTin = emrBenhNhan.tennguoibaotin;
            if(!StringUtils.isEmpty(nguoiBaoTin)){    
                PN guardianPersonName = DatatypesFactory.eINSTANCE.createPN();
                guardianPerson.getNames().add(guardianPersonName);
                guardianPersonName.addText(nguoiBaoTin);                                
            }
            
            //add guardian person address       
            String diaChiNguoiBaoTin = emrBenhNhan.diachinguoibaotin;
            if(!StringUtils.isEmpty(diaChiNguoiBaoTin)){  
                AD guardianAddress = DatatypesFactory.eINSTANCE.createAD();
                guardian.getAddrs().add(guardianAddress);
                guardianAddress.addText(diaChiNguoiBaoTin);         
            }
                    
            //add guardian phone        
            String sdtNguoiBaoTin = emrBenhNhan.sodienthoainguoibaotin;
            if(!StringUtils.isEmpty(sdtNguoiBaoTin)){
                TEL telecom = DatatypesFactory.eINSTANCE.createTEL();
                guardian.getTelecoms().add(telecom);
                telecom.setValue(sdtNguoiBaoTin);       
            }               
            
            //add guardian to patient
            patient.getGuardians().add(guardian);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addPatientParents(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn){
        try {
            EmrBenhNhan emrBenhNhan = emrDanhSachHoSoBenhAn.getEmrBenhNhan();
            var fParticipant = CDAFactory.eINSTANCE.createParticipant1();
            fParticipant.setTypeCode(ParticipationType.IND);
                        
            doc.getParticipants().add(fParticipant);
            
            var fAssociateEntity = HSBAFactory.eINSTANCE.createHsbaFatherAssociatedEntity().init();
            fParticipant.setAssociatedEntity(fAssociateEntity);
            org.openhealthtools.mdht.uml.cda.Person fAssignedPerson = CDAFactory.eINSTANCE.createPerson();
            fAssociateEntity.setAssociatedPerson(fAssignedPerson);
            PN fatherPN = DatatypesFactory.eINSTANCE.createPN();
            fAssignedPerson.getNames().add(fatherPN);
            
            String dad = emrBenhNhan.hotenbo; 
            if(!StringUtils.isEmpty(dad)){
                fatherPN.addText(dad);
            }
            
            Organization orgDad = CDAFactory.eINSTANCE.createOrganization();
            //Trình độ văn hóa
            String trinhdoVhbo = emrBenhNhan.trinhDoVanHoaCuaBo;
            if(!StringUtils.isEmpty(trinhdoVhbo)){                
                ON onTrinhdovhbo = DatatypesFactory.eINSTANCE.createON();           
                onTrinhdovhbo.addText(trinhdoVhbo);
                orgDad.getNames().add(onTrinhdovhbo);
            }

            //Ngày sinh
            OrganizationPartOf opoDad = CDAFactory.eINSTANCE.createOrganizationPartOf();
            Date ngaysinhbo = emrBenhNhan.ngaySinhCuaBo;
            if(ngaysinhbo != null){
                String dadBirthTime = convertDateToString(ngaysinhbo);
                if(!StringUtils.isEmpty(dadBirthTime)){
                    IVL_TS ngaysinhboIvlTs = DatatypesFactory.eINSTANCE.createIVL_TS(dadBirthTime);
                    opoDad.setEffectiveTime(ngaysinhboIvlTs);
                    //Tính tuổi bố
                    Integer tuoibo = JasperUtils.getTuoi(emrDanhSachHoSoBenhAn, ngaysinhbo);
                    if(tuoibo != null && tuoibo > 0){
                        ngaysinhboIvlTs.setCenter(DatatypesFactory.eINSTANCE.createTS(String.valueOf(tuoibo)));
                    }
                }               
            }
            
            //Nghề nghiệp
            CE dadCode = DatatypesFactory.eINSTANCE.createCE();
            var dmNgheBo = emrBenhNhan.emrDmNgheNghiepBo;
            if(dmNgheBo != null){
                String ngheboCodeCda = dmNgheBo.maicd;
                String ngheboTen = dmNgheBo.ten;
                if(!StringUtils.isEmpty(ngheboCodeCda)){
                    dadCode.setCode(ngheboCodeCda);
                    if(!StringUtils.isEmpty(ngheboTen)){
                        dadCode.setDisplayName(ngheboTen);
                    }
                }
            }
            opoDad.setCode(dadCode);            
            orgDad.setAsOrganizationPartOf(opoDad);
            fAssociateEntity.setScopingOrganization(orgDad);                            
                        
            
            //Thông tin mẹ bệnh nhân 
            var mParticipant = CDAFactory.eINSTANCE.createParticipant1();
            mParticipant.setTypeCode(ParticipationType.IND);
            doc.getParticipants().add(mParticipant);
            
            var mAssociateEntity = HSBAFactory.eINSTANCE.createHsbaMotherAssociatedEntity().init();
            mParticipant.setAssociatedEntity(mAssociateEntity);
            org.openhealthtools.mdht.uml.cda.Person mAssignedPerson = CDAFactory.eINSTANCE.createPerson();
            mAssociateEntity.setAssociatedPerson(mAssignedPerson);
            PN mumPN = DatatypesFactory.eINSTANCE.createPN();
            mAssignedPerson.getNames().add(mumPN);                  
            
            String mum = emrBenhNhan.hotenme; 
            if(!StringUtils.isEmpty(mum)){
                mumPN.addText(mum);
            }
            
            Organization orgMum = CDAFactory.eINSTANCE.createOrganization();
            //Trình độ văn hóa
            String trinhdoVhme = emrBenhNhan.trinhDoVanHoaCuaMe;
            if(!StringUtils.isEmpty(trinhdoVhme)){                
                ON onTrinhdovhme = DatatypesFactory.eINSTANCE.createON();           
                onTrinhdovhme.addText(trinhdoVhme);
                orgMum.getNames().add(onTrinhdovhme);
            }
            
            //Ngày sinh
            OrganizationPartOf opoMum = CDAFactory.eINSTANCE.createOrganizationPartOf();
            Date ngaysinhme = emrBenhNhan.ngaySinhCuaMe;
            if(ngaysinhme != null){
                String mumBirthTime = convertDateToString(ngaysinhme);
                if(!StringUtils.isEmpty(mumBirthTime)){
                    IVL_TS ngaysinhmeIvlTs = DatatypesFactory.eINSTANCE.createIVL_TS(mumBirthTime);
                    opoMum.setEffectiveTime(ngaysinhmeIvlTs);
                    //Tính tuổi mẹ
                    Integer tuoime = JasperUtils.getTuoi(emrDanhSachHoSoBenhAn, ngaysinhme);
                    if(tuoime != null && tuoime > 0){
                        ngaysinhmeIvlTs.setCenter(DatatypesFactory.eINSTANCE.createTS(String.valueOf(tuoime)));
                    }
                }               
            }
            
            //Nghề nghiệp
            CE mumCode = DatatypesFactory.eINSTANCE.createCE();
            var dmNgheMe = emrBenhNhan.emrDmNgheNghiepMe;
            if(dmNgheMe != null){
                String nghemeCodeCda = dmNgheMe.maicd;
                String nghemeTen = dmNgheMe.ten;
                if(!StringUtils.isEmpty(nghemeCodeCda)){
                    mumCode.setCode(nghemeCodeCda);
                    if(!StringUtils.isEmpty(nghemeTen)){
                        mumCode.setDisplayName(nghemeTen);
                    }
                }
            }
            opoMum.setCode(mumCode);            
            orgMum.setAsOrganizationPartOf(opoMum);
            mAssociateEntity.setScopingOrganization(orgMum);
            
            //Đẻ lần mấy
            /*ED delanmay = DatatypesFactory.eINSTANCE.createED("1");
            mumCode.setOriginalText(delanmay);*/
            
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addEmployer(HsbaDocument doc, EmrBenhNhan emrBenhNhan){
        try {
            var eParticipant = CDAFactory.eINSTANCE.createParticipant1();
            eParticipant.setTypeCode(ParticipationType.IND);
            doc.getParticipants().add(eParticipant);
            
            var eAssociateEntity = HSBAFactory.eINSTANCE.createHsbaEmployerAssociatedEntity().init();
            eParticipant.setAssociatedEntity(eAssociateEntity);
            org.openhealthtools.mdht.uml.cda.Person eAssignedPerson = CDAFactory.eINSTANCE.createPerson();
            eAssociateEntity.setAssociatedPerson(eAssignedPerson);
            PN employerPN = DatatypesFactory.eINSTANCE.createPN();
            eAssignedPerson.getNames().add(employerPN);
            
            String employer = emrBenhNhan.noilamviec; 
            if(!StringUtils.isEmpty(employer)){
                employerPN.addText(employer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public static void addDvcq_Tpkhth(HsbaDocument doc, EmrCoSoKhamBenh emrCoSoKhamBenh, EmrHoSoBenhAn emrDanhSachHoSoBenhAn){
        try {
            var dvcqParticipant = CDAFactory.eINSTANCE.createParticipant1();           
            dvcqParticipant.setTypeCode(ParticipationType.IND);
            doc.getParticipants().add(dvcqParticipant);

            var associatedEntity = HSBAFactory.eINSTANCE.createHsbaDvcqVaTpkhthAssociatedEntity().init();
            dvcqParticipant.setAssociatedEntity(associatedEntity);
            org.openhealthtools.mdht.uml.cda.Person eAssignedPerson = CDAFactory.eINSTANCE.createPerson();
            associatedEntity.setAssociatedPerson(eAssignedPerson);
            PN tpkhthPN = DatatypesFactory.eINSTANCE.createPN();
            eAssignedPerson.getNames().add(tpkhthPN);
            
            String tpkhth = emrDanhSachHoSoBenhAn.getTruongphongth();
            
            if(tpkhth == null || tpkhth.isEmpty()){
                tpkhth = emrCoSoKhamBenh.truongphongth;
                if(tpkhth == null){
                    tpkhth = "";
                }
                tpkhthPN.addText(tpkhth);
            }
            
            Organization org = CDAFactory.eINSTANCE.createOrganization();
            ON dvcqON = DatatypesFactory.eINSTANCE.createON();
            org.getNames().add(dvcqON);
            associatedEntity.setScopingOrganization(org);
            
            String dvcq = emrDanhSachHoSoBenhAn.getDonvichuquan(); 
            if(dvcq == null || dvcq.isEmpty()){ 
                dvcq = emrCoSoKhamBenh.donvichuquan;
                if(dvcq == null){
                    dvcq = "";
                }
                dvcqON.addText(dvcq);               
            }           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void input_OutputHospital(EncompassingEncounter ecpEncounter, EmrHoSoBenhAn hsba, Map<String, String> emrParameters, EmrCoSoKhamBenh emrCoSoKhamBenh ){
        var emrQuanLyNguoiBenh =  hsba.emrQuanLyNguoiBenh;
        try {
            IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
            ecpEncounter.setEffectiveTime(effectiveTime);
            var lowTime = DatatypesFactory.eINSTANCE.createIVXB_TS();               
            
            //Ngay vao vien
            Date inputDate = emrQuanLyNguoiBenh.ngaygiovaovien;
            if(inputDate != null){
                lowTime.setValue(getGMTDate(inputDate));                            
            }
            effectiveTime.setLow(lowTime);
            
            //Ngày ra viện
            var highTime = DatatypesFactory.eINSTANCE.createIVXB_TS();  
            Date outputDate = emrQuanLyNguoiBenh.ngaygioravien;
            if(outputDate != null){
                highTime.setValue(getGMTDate(outputDate));
            }
            effectiveTime.setHigh(highTime);    
            
            //Loại vào viện
            var emrDmLoaiVaoVien = emrQuanLyNguoiBenh.emrDmLoaiVaoVien;
            if(emrDmLoaiVaoVien != null){
                CE ecpEncounterCode = DatatypesFactory.eINSTANCE.createCE();
                if(!StringUtils.isEmpty(emrDmLoaiVaoVien.maicd)){
                    ecpEncounterCode.setCode(emrDmLoaiVaoVien.maicd);
                }
                String codeSystem = emrParameters.get("emr_dm_loai_vao_vien");
                if(!StringUtils.isEmpty(codeSystem)){
                    ecpEncounterCode.setCodeSystem(codeSystem);
                }
                String loaivaovienDisplayName = emrDmLoaiVaoVien.ten;
                if(!StringUtils.isEmpty(loaivaovienDisplayName)){
                    ecpEncounterCode.setDisplayName(loaivaovienDisplayName);
                }
                ecpEncounter.setCode(ecpEncounterCode);
            }
            
            //Cơ sở y tế
            var responsibleParty = CDAFactory.eINSTANCE.createResponsibleParty();
            ecpEncounter.setResponsibleParty(responsibleParty);
            
            var assignedEntity = CDAFactory.eINSTANCE.createAssignedEntity();
            responsibleParty.setAssignedEntity(assignedEntity);
            II assignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            assignedEntity.getIds().add(assignedEntityId);
            
            Organization organization = CDAFactory.eINSTANCE.createOrganization();      
            assignedEntity.getRepresentedOrganizations().add(organization);
            
            ON tencosoyte = DatatypesFactory.eINSTANCE.createON();      
            organization.getNames().add(tencosoyte);
            String tenCoSoYte = hsba.getTenbenhvien();
            tencosoyte.addText(tenCoSoYte);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addPersonCreateDocument(HsbaDocument doc,  EmrBenhAn emrBenhAn){
        try {
            var author = CDAFactory.eINSTANCE.createAuthor();
            doc.getAuthors().add(author);
            TS authorTime = DatatypesFactory.eINSTANCE.createTS();
            author.setTime(authorTime);
            TS createTime = DatatypesFactory.eINSTANCE.createTS();
            doc.setEffectiveTime(createTime);
                    
            //set document create time      
            Date aprroveDate = emrBenhAn.ngaykybenhan;     
            if(aprroveDate != null){        
                String ngayKyBenhAn = getGMTDate(aprroveDate);
                if(ngayKyBenhAn != null){
                    authorTime.setValue(ngayKyBenhAn);                                                  
                }                       
            }       
            Date createDate = new Date();
            String ngayTao = getGMTDate(createDate);
            if(!StringUtils.isEmpty(ngayTao)){
                createTime.setValue(ngayTao);
            }       
            
            //set assignedAuthor
            var assignAuthor = CDAFactory.eINSTANCE.createAssignedAuthor();
            author.setAssignedAuthor(assignAuthor);     
            II assignAuthorId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            assignAuthor.getIds().add(assignAuthorId);
        
            org.openhealthtools.mdht.uml.cda.Person authAssignPerson = CDAFactory.eINSTANCE.createPerson();     
            assignAuthor.setAssignedPerson(authAssignPerson);
            
            PN authAssignPersonName = DatatypesFactory.eINSTANCE.createPN();
            authAssignPerson.getNames().add(authAssignPersonName);
            
            String bacsylambenhan = emrBenhAn.bacsylambenhan;
            if(!StringUtils.isEmpty(bacsylambenhan)){
                authAssignPersonName.addText(bacsylambenhan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addDean(HsbaDocument doc, EmrVaoKhoa emrVaoKhoa,  Properties properties){
        try {
            var authenticator = CDAFactory.eINSTANCE.createAuthenticator();       
            doc.getAuthenticators().add(authenticator);
            CS authenticatorSignatureCode = DatatypesFactory.eINSTANCE.createCS();
            authenticator.setSignatureCode(authenticatorSignatureCode);
            TS authenticatorTime = DatatypesFactory.eINSTANCE.createTS();
            authenticator.setTime(authenticatorTime);
            //add authenticator time
            if(emrVaoKhoa.ngaygiovaokhoa != null){
                authenticatorTime.setValue(getGMTDate(emrVaoKhoa.ngaygiovaokhoa));
                authenticator.setTime(authenticatorTime);
            }
            
            //add assigned entity for authenticator 
            var authenAssignedEntity = CDAFactory.eINSTANCE.createAssignedEntity();
            authenticator.setAssignedEntity(authenAssignedEntity);
            II authenAssignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            authenAssignedEntity.getIds().add(authenAssignedEntityId);
                    
            //add authenAssignedPerson
            org.openhealthtools.mdht.uml.cda.Person authenAssignedPerson = CDAFactory.eINSTANCE.createPerson();
            authenAssignedEntity.setAssignedPerson(authenAssignedPerson);
            
            PN authenAssignedPersonName = DatatypesFactory.eINSTANCE.createPN();
            authenAssignedPerson.getNames().add(authenAssignedPersonName);  
            
            String truongkhoa = emrVaoKhoa.tentruongkhoa;
            if(!StringUtils.isEmpty(truongkhoa)){
                authenAssignedPersonName.addText(truongkhoa);           
                authenticatorSignatureCode.setCode(properties.getProperty("CDA_SIGNATURE_CODE", "CDA_SIGNATURE_CODE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addHospitalDirector(HsbaDocument doc, EmrCoSoKhamBenh emrCoSoKhamBenh, EmrHoSoBenhAn emrDanhSachHoSoBenhAn,  Properties properties){
        try {
            var legalAuthenticator = CDAFactory.eINSTANCE.createLegalAuthenticator();
            doc.setLegalAuthenticator(legalAuthenticator);
            CS legalSignatureCode = DatatypesFactory.eINSTANCE.createCS();
            legalAuthenticator.setSignatureCode(legalSignatureCode);
            TS legalAuthenticatorTime = DatatypesFactory.eINSTANCE.createTS();
            legalAuthenticator.setTime(legalAuthenticatorTime);
            
            //add assigned entity for legalAuthenticator 
            var legalAssignedEntity = CDAFactory.eINSTANCE.createAssignedEntity();
            legalAuthenticator.setAssignedEntity(legalAssignedEntity);
            II legalAssignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            legalAssignedEntity.getIds().add(legalAssignedEntityId);
                    
            
            //add legalAssignedPerson
            org.openhealthtools.mdht.uml.cda.Person legalAssignedPerson = CDAFactory.eINSTANCE.createPerson();
            legalAssignedEntity.setAssignedPerson(legalAssignedPerson);
            
            PN legalAssignedPersonName = DatatypesFactory.eINSTANCE.createPN();     
            legalAssignedPerson.getNames().add(legalAssignedPersonName);    

            String directorName = emrDanhSachHoSoBenhAn.getGiamdocbenhvien();
            if(directorName == null || directorName.isEmpty()){             
                directorName = emrCoSoKhamBenh.giamdoc;    
                if(directorName == null){
                    directorName = "";
                }
                legalAssignedPersonName.addText(directorName);
                legalSignatureCode.setCode(properties.getProperty("CDA_SIGNATURE_CODE", "CDA_SIGNATURE_CODE"));     
            }
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void addPatientManagement(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Properties properties, Map<String, String> emrParameters){      
        try {
            var emrQuanLyNguoiBenh = emrDanhSachHoSoBenhAn.getEmrQuanLyNguoiBenh();
            
            var historyOfEncountersSection = HSBAFactory.eINSTANCE.createHsbaHistoryOfEncountersSection().init();
            doc.addSection(historyOfEncountersSection);
            String quanLyNguoiBenhTitle = properties.getProperty("QUANLYNGUOIBENH_TITLE", "QUANLYNGUOIBENH_TITLE");
            addSectionTitle(historyOfEncountersSection, quanLyNguoiBenhTitle);
                
            StringBuilder historyEncounterText = new StringBuilder();
            
            //Thông tin vào ra viện
            var inOutHospitalEncounter = HSBAFactory.eINSTANCE.createHsbaInOutHospitalEncounter().init();
            historyOfEncountersSection.addEncounter(inOutHospitalEncounter);    
            
            //I.HÀNH CHÍNH - Ngoại kiều 
            addNgoaiKieu(inOutHospitalEncounter, emrDanhSachHoSoBenhAn);    
        
            if(emrQuanLyNguoiBenh != null){
                //II.QUẢN LÝ NGƯỜI BỆNH - 12,18. Ngày vào ra viện
                addPatientInputOutputDate(inOutHospitalEncounter, emrDanhSachHoSoBenhAn, emrParameters, historyEncounterText, properties);
                
                //II.QUẢN LÝ NGƯỜI BỆNH - 13. Trực tiếp vào
                addDirectlyInTo(inOutHospitalEncounter, emrQuanLyNguoiBenh, emrParameters, properties, historyEncounterText);
                
                //II.QUẢN LÝ NGƯỜI BỆNH - 14. Nơi giới thiệu
                addReferredBy(inOutHospitalEncounter, emrQuanLyNguoiBenh, emrParameters, properties, historyEncounterText);
                
                //II.QUẢN LÝ NGƯỜI BỆNH - Vào viện do bệnh này lần thứ
                addVaoVienLanThu(inOutHospitalEncounter, emrQuanLyNguoiBenh);
                
                //II.QUẢN LÝ NGƯỜI BỆNH - 17. Nơi chuyển viện
                addReferredTo(inOutHospitalEncounter, emrQuanLyNguoiBenh, emrParameters, properties, historyEncounterText);
        
            }
            //II.QUẢN LÝ NGƯỜI BỆNH - 15,16. Vào khoa/chuyển khoa
            var lstVaoKhoa = emrDanhSachHoSoBenhAn.emrVaoKhoas;
            if(lstVaoKhoa != null && lstVaoKhoa.size() > 0){
                historyEncounterText.append(properties.getProperty("VAOKHOA_CHUYENKHOA_TITLE", "VAOKHOA_CHUYENKHOA_TITLE"));
                for (EmrVaoKhoa item : lstVaoKhoa) {
                    addInput_SwitchDepartment(historyOfEncountersSection, item, historyEncounterText, emrQuanLyNguoiBenh, properties);
                }
            }
            
            if(historyEncounterText.length() > 0){
                historyEncounterText.setLength(historyEncounterText.length() - 1);
            }
            
            setSectionData(historyOfEncountersSection, historyEncounterText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }               
    }
    
    public static void setSectionData(Section section, String sectionText){ 
        try {
            var haDiagnosisText = CDAFactory.eINSTANCE.createStrucDocText();
            StringBuilder builder = new StringBuilder();

            section.setText(haDiagnosisText);
            if(!StringUtils.isEmpty(sectionText)){
                builder.append(sectionText);    
            }else{  
                builder.append("");
            }
            haDiagnosisText.addText(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    private static void addReferredTo(HsbaInOutHospitalEncounter inOutHospitalEncounter,
            EmrQuanLyNguoiBenh emrQuanLyNguoiBenh, Map<String, String> emrParameters, Properties properties2,
            StringBuilder historyEncounterText) {
        // TODO Auto-generated method stub
        
    }

    public static void addSectionTitle(Section section, String title){
        ST historyAndPhysicalNoteTitle = DatatypesFactory.eINSTANCE.createST();
        if(title != null){
            historyAndPhysicalNoteTitle.addText(title);
        }else{
            historyAndPhysicalNoteTitle.addText("");
        }
        section.setTitle(historyAndPhysicalNoteTitle);       
    }
    
    public static void addNgoaiKieu(HsbaInOutHospitalEncounter inOutHospitalEncounter, EmrHoSoBenhAn emrDanhSachHoSoBenhAn){
        EmrBenhNhan emrBenhNhan = emrDanhSachHoSoBenhAn.getEmrBenhNhan();
        if(emrBenhNhan != null){            
            var emrDmQuocGia = emrBenhNhan.emrDmQuocGia;
            if(emrDmQuocGia != null){
                var er = CDAFactory.eINSTANCE.createEntryRelationship();
                er.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                inOutHospitalEncounter.getEntryRelationships().add(er);
                
                var ngoaiKieuObs = HSBAFactory.eINSTANCE.createHsbaNgoaiKieuObservation().init();
                //add codecda
                String ngoaiKieuCodeCda = emrDmQuocGia.maicd;
                if(!StringUtils.isEmpty(ngoaiKieuCodeCda)){           
                    CD ngoaiKieuCode = ngoaiKieuObs.getCode();
                    if(ngoaiKieuCode != null){
                        ngoaiKieuCode.setCode(ngoaiKieuCodeCda);
                    }else{
                        ngoaiKieuCode = DatatypesFactory.eINSTANCE.createCD();
                        ngoaiKieuCode.setCode(ngoaiKieuCodeCda);
                    }
                }
                //add ten
                String ngoaiKieu = emrDmQuocGia.ten;
                if(!StringUtils.isEmpty(ngoaiKieu)){
                    ED ngoaiKieuText = DatatypesFactory.eINSTANCE.createED(ngoaiKieu);
                    ngoaiKieuObs.setText(ngoaiKieuText);
                }                               
                er.setObservation(ngoaiKieuObs);
            }           
        }       
    }
    
    static Date getNgayThangBiBong(EmrHoSoBenhAn danhSachHSBA) {
        Date result = null;
        Integer iNgayVaoVienThu = null;
        
        // lay ngay vao vien thu
        iNgayVaoVienThu = danhSachHSBA.getEmrBenhAn().vaongaythu;
        
        // lay ngay gio vao vien
        Date dNgayVaoVien = danhSachHSBA.getEmrQuanLyNguoiBenh().ngaygiovaovien;
        
        if ( iNgayVaoVienThu == null ) {
            return result;
        } else{
            result = new Date(dNgayVaoVien.getTime() - iNgayVaoVienThu.intValue() * 24 * 3600 * 1000l );
        }
        
        return result;
        
    }
    
    public static void addPatientInputOutputDate(HsbaInOutHospitalEncounter inOutHospitalEncounter, EmrHoSoBenhAn hsba, Map<String, String> emrParameters, StringBuilder ecpEncounterText,  Properties properties){
        var emrQuanLyNguoiBenh = hsba.getEmrQuanLyNguoiBenh();
        
        IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
        inOutHospitalEncounter.setEffectiveTime(effectiveTime);
        var lowTime = DatatypesFactory.eINSTANCE.createIVXB_TS();   
        String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
        String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
        //II.QUẢN LÝ NGƯỜI BỆNH - 12. Ngày vào viện
        Date inputDate = emrQuanLyNguoiBenh.ngaygiovaovien;
        if(inputDate != null){
            lowTime.setValue(getGMTDate(inputDate));
            ecpEncounterText.append(properties.getProperty("NGAYVAOVIEN_TITLE", "NGAYVAOVIEN_TITLE")).append(contentDelimiter);
            ecpEncounterText.append(inputDate.toString()).append(splitDelimiter);
            
            //Thời gian bỏng
            Date tgianBong = getNgayThangBiBong(hsba);
            if(tgianBong != null){
                String strTgianbong = convertDateToString(tgianBong);
                if(!StringUtils.isEmpty(strTgianbong)){
                    var tgianBongER = CDAFactory.eINSTANCE.createEntryRelationship();
                    tgianBongER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                    inOutHospitalEncounter.getEntryRelationships().add(tgianBongER);
                    
                    var tgianBongObs = HSBAFactory.eINSTANCE.createHsbaThoiGianBongObservation().init();
                    tgianBongObs.setText(DatatypesFactory.eINSTANCE.createED(strTgianbong));
                    tgianBongER.setObservation(tgianBongObs);
                }
            }               
        }               
        effectiveTime.setLow(lowTime);
                    
        //II.QUẢN LÝ NGƯỜI BỆNH - 18. Ngày ra viện
        var highTime = DatatypesFactory.eINSTANCE.createIVXB_TS();  
        Date outputDate = emrQuanLyNguoiBenh.ngaygioravien;
        if(outputDate != null){
            highTime.setValue(getGMTDate(outputDate));
            ecpEncounterText.append(properties.getProperty("NGAYRAVIEN_TITLE", "NGAYRAVIEN_TITLE")).append(contentDelimiter);
            ecpEncounterText.append(outputDate.toString()).append(splitDelimiter);
        }       
        effectiveTime.setHigh(highTime);    
                
        //Loại vào viện
        var emrDmLoaiVaoVien = emrQuanLyNguoiBenh.emrDmLoaiVaoVien;
        if(emrDmLoaiVaoVien != null){           
            CD ecpEncounterCode = DatatypesFactory.eINSTANCE.createCD();
            if(!StringUtils.isEmpty(emrDmLoaiVaoVien.maicd)){
                ecpEncounterCode.setCode(emrDmLoaiVaoVien.maicd);
            }
            String codeSystem = emrParameters.get("emr_dm_loai_vao_vien");
            if(!StringUtils.isEmpty(codeSystem)){
                ecpEncounterCode.setCodeSystem(codeSystem);
            }                   
            String loaivaovienDisplayName = emrDmLoaiVaoVien.ten;
            if(!StringUtils.isEmpty(loaivaovienDisplayName)){
                ecpEncounterCode.setDisplayName(loaivaovienDisplayName);
            }
            inOutHospitalEncounter.setCode(ecpEncounterCode);
        }   
        
        //Loại hình ra viện
        var emrDmLoaiRaVien = emrQuanLyNguoiBenh.emrDmLoaiRaVien;
        var er = CDAFactory.eINSTANCE.createEntryRelationship();
        er.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        inOutHospitalEncounter.getEntryRelationships().add(er);
        var dischargeHospitalAct = HSBAFactory.eINSTANCE.createHsbaDischargeHospitalAct().init();
        II dischargeHospitalActid = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        dischargeHospitalAct.getIds().add(dischargeHospitalActid);
        er.setAct(dischargeHospitalAct);
    
        if(emrDmLoaiRaVien != null){
            CD dischargeDispositionCode = DatatypesFactory.eINSTANCE.createCD();
            
            String ravienCode = emrDmLoaiRaVien.maicd;
            if(!StringUtils.isEmpty(ravienCode)){
                dischargeDispositionCode.setCode(ravienCode);
            }       
            String codeSystem = emrParameters.get("emr_dm_loai_ra_vien");
            if(!StringUtils.isEmpty(codeSystem)){
                dischargeDispositionCode.setCodeSystem(codeSystem);
            }   
            String ravienDisplayName = emrDmLoaiRaVien.ten;
            if(!StringUtils.isEmpty(ravienDisplayName)){
                dischargeDispositionCode.setDisplayName(ravienDisplayName);
            }
            dischargeHospitalAct.setCode(dischargeDispositionCode);
        }
        
        //Bác sĩ tiếp nhận
        var admittingPerformer = CDAFactory.eINSTANCE.createPerformer2();
        inOutHospitalEncounter.getPerformers().add(admittingPerformer);
        
        var admittingAssignedEntity = HSBAFactory.eINSTANCE.createHsbaReceiveDoctorAssignedEntity().init(); 
        admittingPerformer.setAssignedEntity(admittingAssignedEntity);  
        II admittingAssignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        admittingAssignedEntity.getIds().add(admittingAssignedEntityId);
        
        org.openhealthtools.mdht.uml.cda.Person admittingPerson = CDAFactory.eINSTANCE.createPerson();
        PN admittingPhysician = DatatypesFactory.eINSTANCE.createPN();
        String bacsitiepnhan = emrQuanLyNguoiBenh.tenbacsikham;
        if(!StringUtils.isEmpty(bacsitiepnhan)){
            admittingPhysician.addText(bacsitiepnhan);
        }
        admittingPerson.getNames().add(admittingPhysician);
        admittingAssignedEntity.setAssignedPerson(admittingPerson);     
        
        //Bác sĩ cho ra viện
        var attendingPerformer = CDAFactory.eINSTANCE.createPerformer2();
        inOutHospitalEncounter.getPerformers().add(attendingPerformer);
        var attendingAssignedEntity = HSBAFactory.eINSTANCE.createHsbaDischargeDoctorAssignedEntity().init();
        attendingPerformer.setAssignedEntity(attendingAssignedEntity);  
        II attendingAssignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        attendingAssignedEntity.getIds().add(attendingAssignedEntityId);
        
        org.openhealthtools.mdht.uml.cda.Person attendingPerson = CDAFactory.eINSTANCE.createPerson();
        PN attendingPhysician = DatatypesFactory.eINSTANCE.createPN();
        String bacsichoravien = emrQuanLyNguoiBenh.tenbacsichoravien;
        if(!StringUtils.isEmpty(bacsichoravien)){
            attendingPhysician.addText(bacsichoravien);
        }
        attendingPerson.getNames().add(attendingPhysician);
        attendingAssignedEntity.setAssignedPerson(attendingPerson); 
        
        //Tổng số ngày điều trị
        var tsndtER = CDAFactory.eINSTANCE.createEntryRelationship();
        tsndtER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        
        var tsndtSupply = CDAFactory.eINSTANCE.createSupply();
        tsndtSupply.setMoodCode(x_DocumentSubstanceMood.EVN);
        tsndtER.setSupply(tsndtSupply);
        tsndtSupply.setCode(DatatypesFactory.eINSTANCE.createCS(TONGSONGAY_DTR_TYPE));
        inOutHospitalEncounter.getEntryRelationships().add(tsndtER);
        
        Integer tongsongaydtr = tinhTongSoNgayDieuTri(emrQuanLyNguoiBenh);
        if(tongsongaydtr != null){
            tsndtSupply.setText(DatatypesFactory.eINSTANCE.createED(String.valueOf(tongsongaydtr)));
        }  
    }
        
    public static void addDirectlyInTo(HsbaInOutHospitalEncounter inOutHospitalEncounter, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh, Map<String, String> emrParameters, Properties properties, StringBuilder encounterText){  
        var directlyInToParticipant = CDAFactory.eINSTANCE.createParticipant2();
        directlyInToParticipant.setTypeCode(ParticipationType.LOC);
        inOutHospitalEncounter.getParticipants().add(directlyInToParticipant);
        String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
        String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
        
        var directlyInToParticipantRole = HSBAFactory.eINSTANCE.createHsbaDirectlyInToParticipantRole().init();
        directlyInToParticipant.setParticipantRole(directlyInToParticipantRole);
        
        var emrDmNoiTrucTiepVao = emrQuanLyNguoiBenh.emrDmNoiTrucTiepVao;
        if(emrDmNoiTrucTiepVao != null){
            CE directlyIntoCode = DatatypesFactory.eINSTANCE.createCE();    
            
            String code = emrDmNoiTrucTiepVao.maicd;
            if(!StringUtils.isEmpty(code)){
                directlyIntoCode.setCode(code);
            }       
            String codeSystem = emrParameters.get("emr_dm_noi_truc_tiep_vao");
            if(!StringUtils.isEmpty(codeSystem)){
                directlyIntoCode.setCodeSystem(codeSystem);
            }                   
            String displayName = emrDmNoiTrucTiepVao.ten;
            if(!StringUtils.isEmpty(displayName)){
                directlyIntoCode.setDisplayName(displayName);
                encounterText.append(properties.getProperty("TRUCTIEPVAO_TITLE", "TRUCTIEPVAO_TITLE")).append(contentDelimiter).append(displayName).append(splitDelimiter);
            }
            directlyInToParticipantRole.setCode(directlyIntoCode);
        }   
    }

    public static Integer tinhTongSoNgayDieuTri(EmrQuanLyNguoiBenh emrQuanLyNguoiBenh) {
        if (emrQuanLyNguoiBenh == null) return null;
        return JasperUtils.tinhSoNgayDieuTri(emrQuanLyNguoiBenh.ngaygiovaovien, emrQuanLyNguoiBenh.ngaygioravien);
    }
    
    public static void addReferredBy(HsbaInOutHospitalEncounter inOutHospitalEncounter, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh, Map<String, String> emrParameters, Properties properties, StringBuilder encounterText){
        var referredByParticipant = CDAFactory.eINSTANCE.createParticipant2();
        referredByParticipant.setTypeCode(ParticipationType.REFB);
        inOutHospitalEncounter.getParticipants().add(referredByParticipant);
        
        var referredByParticipantRole = HSBAFactory.eINSTANCE.createHsbaReferredByParticipantRole().init();
        referredByParticipant.setParticipantRole(referredByParticipantRole);
        String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
        String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
        
        var emrDmNoiGioiThieu = emrQuanLyNguoiBenh.emrDmNoiGioiThieu;
        if(emrDmNoiGioiThieu != null){
            CE referredByCode = DatatypesFactory.eINSTANCE.createCE();  
            
            String code = emrDmNoiGioiThieu.maicd;
            if(!StringUtils.isEmpty(code)){
                referredByCode.setCode(code);
            }       
            String codeSystem = emrParameters.get("emr_dm_noi_gioi_thieu");
            if(!StringUtils.isEmpty(codeSystem)){
                referredByCode.setCodeSystem(codeSystem);
            }
            String displayName = emrDmNoiGioiThieu.ten;
            if(!StringUtils.isEmpty(displayName)){
                referredByCode.setDisplayName(displayName);
                encounterText.append(properties.getProperty("NOIGIOITHIEU_TITLE", "NOIGIOITHIEU_TITLE")).append(contentDelimiter).append(displayName).append(splitDelimiter);
            }
            referredByParticipantRole.setCode(referredByCode);
        }                       
    }
    
    public static void addVaoVienLanThu(HsbaInOutHospitalEncounter inOutHospitalEncounter, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh){      
        Integer vaoVienLanThu = emrQuanLyNguoiBenh.vaovienlanthu;
        
        if(vaoVienLanThu != null && vaoVienLanThu >= 0){    
            var er = CDAFactory.eINSTANCE.createEntryRelationship();
            er.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            inOutHospitalEncounter.getEntryRelationships().add(er);
            
            var vaoVienLanThuObs = HSBAFactory.eINSTANCE.createHsbaVaoVienDoBenhLanThuObservation().init();          
            //add ten
            String vaoVienLanThuObsText = String.valueOf(vaoVienLanThu);
            if(!StringUtils.isEmpty(vaoVienLanThuObsText)){
                ED ngoaiKieuText = DatatypesFactory.eINSTANCE.createED(vaoVienLanThuObsText);
                vaoVienLanThuObs.setText(ngoaiKieuText);
            }                               
            er.setObservation(vaoVienLanThuObs);                
        }  
    }
    
    public static Integer getSoNgayDieuTri(EmrVaoKhoa emrVaoKhoa, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh) {      
        
        if (emrVaoKhoa == null || emrVaoKhoa.ngayketthucdieutri == null || emrVaoKhoa.ngaygiovaokhoa == null)
            return null;
        
        return JasperUtils.tinhSoNgayDieuTri(emrVaoKhoa.ngaygiovaokhoa, emrVaoKhoa.ngayketthucdieutri);
    }
    
    public static void addInput_SwitchDepartment(Section section, EmrVaoKhoa emrVaoKhoa, StringBuilder historyEncounterText, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh,  Properties properties){
        var ecpEncounter = HSBAFactory.eINSTANCE.createHsbaInputDepartmentEncounter().init();      
        String emrVaoKhoaId = String.valueOf(emrVaoKhoa.getId()); 
        II ecpEncounterId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString(), emrVaoKhoaId);
        ecpEncounter.getIds().add(ecpEncounterId);
        section.addEncounter(ecpEncounter);
        
        //add encounter participant time
        IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
        ecpEncounter.setEffectiveTime(effectiveTime);
        
        //Ngày vào khoa
        Date inputDate = emrVaoKhoa.ngaygiovaokhoa;
        var departPaticipantInputTime = DatatypesFactory.eINSTANCE.createIVXB_TS();
        if(inputDate != null){          
            departPaticipantInputTime.setValue(getGMTDate(inputDate));
            effectiveTime.setLow(departPaticipantInputTime);    
            
            //Ngày xuất khoa
            Date outputDate = emrVaoKhoa.ngayketthucdieutri;
            if(outputDate != null){                             
                var departPaticipantOutputTime = DatatypesFactory.eINSTANCE.createIVXB_TS();
                String ngayKetThucDieuTri = getGMTDate(outputDate);
                if(!StringUtils.isEmpty(ngayKetThucDieuTri)){
                    departPaticipantOutputTime.setValue(ngayKetThucDieuTri);
                    effectiveTime.setHigh(departPaticipantOutputTime);
                }               
            }
        }           
        
        //Số phòng, số giường, Số ngày điều trị
        addSoNgayDieuTri(ecpEncounter, emrVaoKhoa, emrQuanLyNguoiBenh);
        
        var performer = CDAFactory.eINSTANCE.createPerformer2();
        ecpEncounter.getPerformers().add(performer);
        
        //add bác sĩ điều trị
        var departAssignedEntity = CDAFactory.eINSTANCE.createAssignedEntity();      
        performer.setAssignedEntity(departAssignedEntity);
        II departAssignedEntityId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        departAssignedEntity.getIds().add(departAssignedEntityId);
        
        org.openhealthtools.mdht.uml.cda.Person assignedPerson = CDAFactory.eINSTANCE.createPerson();
        departAssignedEntity.setAssignedPerson(assignedPerson);
        PN assignedPersonName = DatatypesFactory.eINSTANCE.createPN();
        String bacsidieutri = emrVaoKhoa.bacsidieutri;
        if(!StringUtils.isEmpty(bacsidieutri)){
            assignedPersonName.addText(bacsidieutri);
        }
        assignedPerson.getNames().add(assignedPersonName);
        
        //Thông tin khoa
        var departPaticipant = CDAFactory.eINSTANCE.createParticipant2();
        ecpEncounter.getParticipants().add(departPaticipant);       
        departPaticipant.setTypeCode(ParticipationType.LOC);
        
        //add assigned entity
        var participantRole = HSBAFactory.eINSTANCE.createHsbaDepartmentParticipantRole().init();
        departPaticipant.setParticipantRole(participantRole);
        
        var playingEntity = CDAFactory.eINSTANCE.createPlayingEntity();
        participantRole.setPlayingEntity(playingEntity);
        playingEntity.setClassCode(EntityClassRoot.PLC);

        //Số thứ tự
        CE sttCode = DatatypesFactory.eINSTANCE.createCE();
        sttCode.setCode(emrVaoKhoa.emrDmKhoaDieuTri.maicd);
        playingEntity.setCode(sttCode);
        
        PN department = DatatypesFactory.eINSTANCE.createPN();              
        String departmentName = emrVaoKhoa.tenkhoa;
        if(departmentName == null  || departmentName.isEmpty()){                                    
            var emrDmKhoaDieuTri = emrVaoKhoa.emrDmKhoaDieuTri;
            if(emrDmKhoaDieuTri != null){                   
                //Tên khoa
                departmentName = emrDmKhoaDieuTri.ten;                                         
            }                               
        }   
        if(departmentName == null){
            departmentName = "";
        }
        department.addText(departmentName);
        
        historyEncounterText.append(properties.getProperty("SPLIT_DELIMITER", "SPLIT_DELIMITER")).append(departmentName);
        playingEntity.getNames().add(department);       
        
        referenceDepartments.put(ecpEncounterId.getRoot(), emrVaoKhoa);
    }
    
    public static void addSoNgayDieuTri(HsbaInputDepartmentEncounter ecpEncounter, EmrVaoKhoa emrVaoKhoa, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh){     
        if(emrVaoKhoa != null){ 
            //Số phòng, số giường
            String phong = emrVaoKhoa.phong;
            String giuong = emrVaoKhoa.giuong;
            if(!StringUtils.isEmpty(phong)){
                var pgEr = CDAFactory.eINSTANCE.createEntryRelationship();
                pgEr.setTypeCode(x_ActRelationshipEntryRelationship.COMP);

                var soBuongGiuongObs = HSBAFactory.eINSTANCE.createHsbaSoBuongGiuongObservation().init();
                soBuongGiuongObs.setText(DatatypesFactory.eINSTANCE.createED(phong));
                pgEr.setObservation(soBuongGiuongObs);
                
                if(!StringUtils.isEmpty(giuong)){
                    ST soGiuong = DatatypesFactory.eINSTANCE.createST(giuong);
                    soBuongGiuongObs.getValues().add(soGiuong);
                }
                ecpEncounter.getEntryRelationships().add(pgEr);
            }
            
            //add số ngày điều trị
            Integer songaydieutri = getSoNgayDieuTri(emrVaoKhoa, emrQuanLyNguoiBenh);              
            if(songaydieutri != null){
                var er = CDAFactory.eINSTANCE.createEntryRelationship();
                er.setTypeCode(x_ActRelationshipEntryRelationship.COMP);        
                
                var soNgayDieuTriObs = HSBAFactory.eINSTANCE.createHsbaSoNgayDieuTriObservation().init();
                ED soNgayDieuTriText = DatatypesFactory.eINSTANCE.createED(String.valueOf(songaydieutri));                  
                soNgayDieuTriObs.setText(soNgayDieuTriText);                        
                er.setObservation(soNgayDieuTriObs);
                
                ecpEncounter.getEntryRelationships().add(er);
            }
        }   
    }
    
    public static void addSoNgayDieuTri(Encounter encounter, EmrVaoKhoa emrVaoKhoa, EmrQuanLyNguoiBenh emrQuanLyNguoiBenh){     
        if(emrVaoKhoa != null){ 
            //Số phòng, số giường
            String phong = emrVaoKhoa.phong;
            String giuong = emrVaoKhoa.giuong;
            if(!StringUtils.isEmpty(phong)){
                var pgEr = CDAFactory.eINSTANCE.createEntryRelationship();
                pgEr.setTypeCode(x_ActRelationshipEntryRelationship.COMP);

                var soBuongGiuongObs = HSBAFactory.eINSTANCE.createHsbaSoBuongGiuongObservation().init();
                soBuongGiuongObs.setText(DatatypesFactory.eINSTANCE.createED(phong));
                pgEr.setObservation(soBuongGiuongObs);
                
                if(!StringUtils.isEmpty(giuong)){
                    ST soGiuong = DatatypesFactory.eINSTANCE.createST(giuong);
                    soBuongGiuongObs.getValues().add(soGiuong);
                }
                encounter.getEntryRelationships().add(pgEr);
            }
            
            //add số ngày điều trị
            Integer songaydieutri = getSoNgayDieuTri(emrVaoKhoa, emrQuanLyNguoiBenh);              
            if(songaydieutri != null){
                var er = CDAFactory.eINSTANCE.createEntryRelationship();
                er.setTypeCode(x_ActRelationshipEntryRelationship.COMP);        
                
                var soNgayDieuTriObs = HSBAFactory.eINSTANCE.createHsbaSoNgayDieuTriObservation().init();
                ED soNgayDieuTriText = DatatypesFactory.eINSTANCE.createED(String.valueOf(songaydieutri));                  
                soNgayDieuTriObs.setText(soNgayDieuTriText);                        
                er.setObservation(soNgayDieuTriObs);
                
                encounter.getEntryRelationships().add(er);
            }
        }   
    }
    
    public static void addCdObservationValueTag(Observation obs, String code, String codeSystem, String displayName){
        CD valueTag = DatatypesFactory.eINSTANCE.createCD();
        
        if(!StringUtils.isEmpty(code)){
            valueTag.setCode(code);
        }
        if(!StringUtils.isEmpty(codeSystem)){
            valueTag.setCodeSystem(codeSystem);
        }
        if(!StringUtils.isEmpty(displayName)){
            valueTag.setDisplayName(displayName);
        }
        obs.getValues().add(valueTag);
    }
    
    public static void addDiagnosisObservationContent(Observation obs, String obsTitle, String contentDelimiter, String splitDelimiter, String obsText, EmrDmContent emrDmMaBenh, String codeSystem, Map<String, String> emrParameters, StringBuilder sectionTextBuilder){
        II obsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        obs.getIds().add(obsId);
        ED preliminaryObsText = DatatypesFactory.eINSTANCE.createED();
        String convertObsText = STR_NA_VALUE;
        
        if(!StringUtils.isEmpty(obsText)){
            convertObsText = obsText;
        }
        preliminaryObsText.addText(convertObsText);
        obs.setText(preliminaryObsText);
        sectionTextBuilder.append(obsTitle).append(contentDelimiter).append(convertObsText).append(splitDelimiter);
        
        if(emrDmMaBenh != null ){
            addCdObservationValueTag(obs, emrDmMaBenh.maicd, codeSystem, emrDmMaBenh.ten);      
        }   
    }
    
    public static void addDiagnosis(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Properties properties, Map<String, String> emrParameters){
        var emrChanDoan = emrDanhSachHoSoBenhAn.getEmrChanDoan();       
        
        var diagnosisSection = HSBAFactory.eINSTANCE.createHsbaDiagnosisSection().init();
        doc.addSection(diagnosisSection);   
        String chanDoanTitle = properties.getProperty("CHANDOAN_TITLE", "CHANDOAN_TITLE");
        String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
        String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
        addSectionTitle(diagnosisSection, chanDoanTitle);       
    
        StringBuilder diagnosisText = new StringBuilder();      
        String icd10CodeSystem = emrParameters.get("emr_dm_ma_benh");       
        
        if(emrChanDoan != null){            
            //III. CHẨN ĐOÁN - 20. Nơi chuyển đến
            var preliminaryDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaPreliminaryDiagnosisObservation().init();
            diagnosisSection.addObservation(preliminaryDiagnosisObs);   
            
            String preliminaryTitle = properties.getProperty("CD_NOICHUYENDEN_TITLE", "CD_NOICHUYENDEN_TITLE");
            
            var preliminaryER = CDAFactory.eINSTANCE.createEntryRelationship();
            preliminaryER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            preliminaryDiagnosisObs.getEntryRelationships().add(preliminaryER);     
            
            var prelPrimaryObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init();  
            preliminaryER.setObservation(prelPrimaryObs);
            addDiagnosisObservationContent(prelPrimaryObs, preliminaryTitle, contentDelimiter, splitDelimiter, emrChanDoan.motachandoannoiden, emrChanDoan.emrDmMaBenhChandoannoiden, icd10CodeSystem, emrParameters, diagnosisText);          
            
            //III. CHẨN ĐOÁN - 21. KKB, Cấp cứu
            var admissionDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaAdmissionDiagnosisObservation().init();
            diagnosisSection.addObservation(admissionDiagnosisObs);     
            String admittingTitle = properties.getProperty("CD_KKBCAPCUU_TITLE", "CD_KKBCAPCUU_TITLE");
            
            var admissionER = CDAFactory.eINSTANCE.createEntryRelationship();
            admissionER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            admissionDiagnosisObs.getEntryRelationships().add(admissionER);
            
            var admissionPrimaryObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init(); 
            admissionER.setObservation(admissionPrimaryObs);            
            addDiagnosisObservationContent(admissionPrimaryObs, admittingTitle, contentDelimiter, splitDelimiter, emrChanDoan.motachandoankkb, emrChanDoan.emrDmMaBenhChandoankkb, icd10CodeSystem, emrParameters, diagnosisText);                         
                
            //III. CHẨN ĐOÁN - 25.Ra viện
            diagnosisText.append(properties.getProperty("CD_RAVIEN_TITLE", "CD_RAVIEN_TITLE")).append(contentDelimiter);
            var dischargeDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaDischargeDiagnosisObservation().init();
            diagnosisSection.addObservation(dischargeDiagnosisObs);
            
            //Bệnh chính
            String primaryObsTitle = properties.getProperty("CD_RAVIEN_BENHCHINH_TITLE", "CD_RAVIEN_BENHCHINH_TITLE");
            var primaryER = CDAFactory.eINSTANCE.createEntryRelationship();
            primaryER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            dischargeDiagnosisObs.getEntryRelationships().add(primaryER);           
            
            var primaryPrimaryObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init();   
            primaryER.setObservation(primaryPrimaryObs);                
            addDiagnosisObservationContent(primaryPrimaryObs, primaryObsTitle, contentDelimiter, splitDelimiter, emrChanDoan.motachandoanravienchinh, emrChanDoan.emrDmMaBenhChandoanravienchinh, icd10CodeSystem, emrParameters, diagnosisText);
            //Nguyên nhân
            String complaintObsTitle = properties.getProperty("CD_RAVIEN_NGUYENNHAN_TITLE", "CD_RAVIEN_NGUYENNHAN_TITLE");
            var complaintER = CDAFactory.eINSTANCE.createEntryRelationship();
            complaintER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            dischargeDiagnosisObs.getEntryRelationships().add(complaintER);
            
            var  complaintPrimaryObs = HSBAFactory.eINSTANCE.createHsbaComplaintDiagnosisObservation().init();    
            complaintER.setObservation(complaintPrimaryObs);                
            addDiagnosisObservationContent(complaintPrimaryObs, complaintObsTitle, contentDelimiter, splitDelimiter, emrChanDoan.motachandoanraviennguyennhan, emrChanDoan.emrDmMaBenhChandoanraviennguyennhan, icd10CodeSystem, emrParameters, diagnosisText);            
            
            //Bệnh kèm theo
            String secondaryObsTitle = properties.getProperty("CD_RAVIEN_BENHKEMTHEO_TITLE", "CD_RAVIEN_BENHKEMTHEO_TITLE");
            var secondaryER = CDAFactory.eINSTANCE.createEntryRelationship();
            secondaryER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            dischargeDiagnosisObs.getEntryRelationships().add(secondaryER);
            
            var primarySecondaryObs = HSBAFactory.eINSTANCE.createHsbaSecondaryDiagnosisObservation().init(); 
            secondaryER.setObservation(primarySecondaryObs);            
            addDiagnosisObservationContent(primarySecondaryObs, secondaryObsTitle, contentDelimiter, splitDelimiter, emrChanDoan.motachandoanravienkemtheo, emrChanDoan.emrDmMaBenhChandoanravienkemtheo, icd10CodeSystem, emrParameters, diagnosisText);          
            
            //Chẩn đoán trước phẫu thuật                    
            var preoperativeDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaPreoperativeDiagnosisObservation().init();
            diagnosisSection.addObservation(preoperativeDiagnosisObs);
            String preoperativeTitle = properties.getProperty("CD_RAVIEN_CDTRUOCPT_TITLE", "CD_RAVIEN_CDTRUOCPT_TITLE");
            var preoperativeER = CDAFactory.eINSTANCE.createEntryRelationship();
            preoperativeER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            preoperativeDiagnosisObs.getEntryRelationships().add(preoperativeER);
            
            var preoperativePrimaryObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init();  
            preoperativeER.setObservation(preoperativePrimaryObs);                          
            addDiagnosisObservationContent(preoperativePrimaryObs, preoperativeTitle , contentDelimiter, splitDelimiter, emrChanDoan.motachandoantruocpt, emrChanDoan.emrDmMaBenhChandoantruocpt, icd10CodeSystem, emrParameters, diagnosisText);
        
            //Chẩn đoán sau phẫu thuật
            var postoperativeDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaPostoperativeDiagnosisObservation().init();
            diagnosisSection.addObservation(postoperativeDiagnosisObs);
            String posoperativeTitle = properties.getProperty("CD_RAVIEN_CDSAUPT_TITLE", "CD_RAVIEN_CDSAUPT_TITLE");            
            var postoperativeER = CDAFactory.eINSTANCE.createEntryRelationship();
            postoperativeER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            postoperativeDiagnosisObs.getEntryRelationships().add(postoperativeER);
            
            var postoperativePrimaryObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init(); 
            postoperativeER.setObservation(postoperativePrimaryObs);        
            addDiagnosisObservationContent(postoperativePrimaryObs, posoperativeTitle, contentDelimiter, splitDelimiter, emrChanDoan.motachandoansaupt, emrChanDoan.emrDmMaBenhChandoansaupt, icd10CodeSystem, emrParameters, diagnosisText);
    
            //Tai biến, biến chứng
            var complicationObs = HSBAFactory.eINSTANCE.createHsbaComplicationDiagnosisObservation().init();
            diagnosisSection.addObservation(complicationObs);
            II admittingDiagnosisId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            complicationObs.getIds().add(admittingDiagnosisId); 
            
            StringBuilder title = new StringBuilder();      
            //---Tai biến
            var taibienER = CDAFactory.eINSTANCE.createEntryRelationship();
            taibienER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            var taibienSupply = CDAFactory.eINSTANCE.createSupply();
            taibienSupply.setMoodCode(x_DocumentSubstanceMood.EVN);
            CD taibienCode = DatatypesFactory.eINSTANCE.createCD();
            taibienCode.setCode(TB_TYPE);
            taibienSupply.setCode(taibienCode);
            taibienER.setSupply(taibienSupply);
            complicationObs.getEntryRelationships().add(taibienER);
            
            
            if(emrChanDoan.bitaibien != null && emrChanDoan.bitaibien){
                title.append(properties.getProperty("CD_TAIBIEN_TITLE", "CD_TAIBIEN_TITLE")).append(",");                   
                taibienSupply.setText(DatatypesFactory.eINSTANCE.createED(emrChanDoan.bitaibien.toString()));
            }else{
                taibienSupply.setText(DatatypesFactory.eINSTANCE.createED(String.valueOf(false)));
            }
            
            //---Biến chứng
            var bienchungER = CDAFactory.eINSTANCE.createEntryRelationship();
            bienchungER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            var bienchungSupply = CDAFactory.eINSTANCE.createSupply();
            bienchungSupply.setMoodCode(x_DocumentSubstanceMood.EVN);
            CD bienchungCode = DatatypesFactory.eINSTANCE.createCD();
            bienchungCode.setCode(BC_TYPE);
            bienchungSupply.setCode(bienchungCode); 
            bienchungER.setSupply(bienchungSupply);
            complicationObs.getEntryRelationships().add(bienchungER);
            
            if(emrChanDoan.bibienchung != null && emrChanDoan.bibienchung){
                title.append(properties.getProperty("CD_BIENCHUNG_TITLE", "CD_BIENCHUNG_TITLE")).append(",");                   
                bienchungSupply.setText(DatatypesFactory.eINSTANCE.createED(emrChanDoan.bibienchung.toString()));
            }else{                  
                bienchungSupply.setText(DatatypesFactory.eINSTANCE.createED(String.valueOf(false)));
            }
            if(title.length() > 0){
                title.setLength(title.length() - 1);
            }
            
            //Nguyên nhân tai biến/biến chứng           
            var emrLyDoTaiBienBienChung = emrChanDoan.emrDmLyDoTaiBienBienChung;
            if(emrLyDoTaiBienBienChung != null){
                var causeOfComplicationER = CDAFactory.eINSTANCE.createEntryRelationship();
                causeOfComplicationER.setTypeCode(x_ActRelationshipEntryRelationship.CAUS);
                complicationObs.getEntryRelationships().add(causeOfComplicationER);
                
                var causeOfComplicationAct = HSBAFactory.eINSTANCE.createHsbaCauseOfComplicationAct().init();
                II causeOfComplicationActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                causeOfComplicationAct.getIds().add(causeOfComplicationActId);
                causeOfComplicationER.setAct(causeOfComplicationAct);
                                
                CD causeOfComplicationCode = DatatypesFactory.eINSTANCE.createCD();
                String code = emrLyDoTaiBienBienChung.maicd;
                if(!StringUtils.isEmpty(code)){
                    causeOfComplicationCode.setCode(code);
                }
                String codeSystem = emrParameters.get("emr_dm_ly_do_tai_bien_bien_chung");
                if(!StringUtils.isEmpty(codeSystem)){
                    causeOfComplicationCode.setCodeSystem(codeSystem);
                }                   
                String displayName = emrLyDoTaiBienBienChung.ten;
                if(!StringUtils.isEmpty(displayName)){
                    causeOfComplicationCode.setDisplayName(displayName);
                    diagnosisText.append(title.toString()).append(contentDelimiter).append(displayName).append(splitDelimiter);                 
                }               
                causeOfComplicationAct.setCode(causeOfComplicationCode);
            }
            
            if(diagnosisText.length() > 0){
                diagnosisText.setLength(diagnosisText.length() - 1);
            }

            //Tổng số ngày điều trị sau phẫu thuật
            var tsndtsptSupply = CDAFactory.eINSTANCE.createSupply();
            tsndtsptSupply.setMoodCode(x_DocumentSubstanceMood.EVN);
            tsndtsptSupply.setCode(DatatypesFactory.eINSTANCE.createCS(TONGSONGAY_DTR_SAUPT_TYPE));
            diagnosisSection.addSupply(tsndtsptSupply);
            Integer tongsongaydtrSaupt = emrChanDoan.tongsongaysaupt;
            if(tongsongaydtrSaupt != null){
                tsndtsptSupply.setText(DatatypesFactory.eINSTANCE.createED(String.valueOf(tongsongaydtrSaupt)));
            }
            
            //Tổng số ngày lần phẫu thuật
            var tslptSupply = CDAFactory.eINSTANCE.createSupply();
            tslptSupply.setMoodCode(x_DocumentSubstanceMood.EVN);
            tslptSupply.setCode(DatatypesFactory.eINSTANCE.createCS(TONGSOLAN_PT_TYPE));
            diagnosisSection.addSupply(tslptSupply);
            Integer tongsolanpt = emrChanDoan.tongsolanpt;
            if(tongsolanpt != null){
                tslptSupply.setText(DatatypesFactory.eINSTANCE.createED(String.valueOf(tongsolanpt)));
            }
            
            setSectionData(diagnosisSection, diagnosisText.toString());
        }               
    }
    
    public static void addDischargeSummary(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Properties properties, Map<String, String> emrParameters){       
        try {
            var emrTinhTrangRaVien = emrDanhSachHoSoBenhAn.getEmrTinhTrangRaVien();
            
            var dischargeSummarySection = HSBAFactory.eINSTANCE.createHsbaDischargeSummarySection().init();
            doc.addSection(dischargeSummarySection);
            String tinhTrangRaVienTitle = properties.getProperty("TINHTRANGRAVIEN_TITLE", "TINHTRANGRAVIEN_TITLE");
            addSectionTitle(dischargeSummarySection, tinhTrangRaVienTitle);
            
            StringBuilder dischargeSummaryText = new StringBuilder();
            String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
            String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");     
        
            if(emrTinhTrangRaVien != null){ 
                //Kết quả điều trị
                var healthStatusObs = HSBAFactory.eINSTANCE.createHsbaHealthStatusObservation().init();
                dischargeSummarySection.addObservation(healthStatusObs);        
                II healthStatusId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                healthStatusObs.getIds().add(healthStatusId);       
                var emrDmKetQuaDieuTri = emrTinhTrangRaVien.emrDmKetQuaDieuTri;
                if(emrDmKetQuaDieuTri != null){
                    String healthStatusCthStatusCode = emrDmKetQuaDieuTri.maicd;
                    String healthStatusCodeSystem = emrParameters.get("emr_dm_ket_qua_dieu_tri");
                    String healthStatusDisplayName = emrDmKetQuaDieuTri.ten;
                    dischargeSummaryText.append(properties.getProperty("TTRV_KETQUADIEUTRI_TITLE", "TTRV_KETQUADIEUTRI_TITLE")).append(contentDelimiter).append(healthStatusDisplayName).append(splitDelimiter);
                    addCdObservationValueTag(healthStatusObs, healthStatusCthStatusCode, healthStatusCodeSystem, healthStatusDisplayName);
                }
                
                //Giải phẫu bệnh
                var pathologyDiagnosisObs = HSBAFactory.eINSTANCE.createHsbaPathologyDiagnosisObservation().init();
                dischargeSummarySection.addObservation(pathologyDiagnosisObs);      
                II pathologyDiagnosisId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                pathologyDiagnosisObs.getIds().add(pathologyDiagnosisId);           
                
                var emrDmKetQuaGiaiPhauBenh = emrTinhTrangRaVien.emrDmKetQuaGiaiPhauBenh;          
                if(emrDmKetQuaGiaiPhauBenh != null){
                    String pathologyCode = emrDmKetQuaGiaiPhauBenh.maicd;
                    String pathologyCodeSystem = emrParameters.get("emr_dm_giai_phau_benh");        
                    String pathologyDisplayName = emrDmKetQuaGiaiPhauBenh.ten;
                    dischargeSummaryText.append(properties.getProperty("TTRV_GIAIPHAUBENH_TITLE", "TTRV_GIAIPHAUBENH_TITLE")).append(contentDelimiter).append(pathologyDisplayName).append(splitDelimiter);
                    addCdObservationValueTag(pathologyDiagnosisObs, pathologyCode, pathologyCodeSystem, pathologyDisplayName);
                }
                
                //Tình hình tử vong
                var deadObs = HSBAFactory.eINSTANCE.createHsbaDeadObservation().init();
                dischargeSummarySection.addObservation(deadObs);
                II deadObsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                deadObs.getIds().add(deadObsId);                
                IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
                deadObs.setEffectiveTime(effectiveTime);
                var lowTime = DatatypesFactory.eINSTANCE.createIVXB_TS();
                Date deadTime = emrTinhTrangRaVien.ngaygiotuvong;
                
                if(deadTime != null){
                    lowTime.setValue(getGMTDate(deadTime).toString());
                    //--Loại thời điểm tử vong
                    Integer thoidiemTuvong = JasperUtils.getLoaiThoiGianTuVong(emrDanhSachHoSoBenhAn);
                    if(thoidiemTuvong != null){
                        deadObs.setText(DatatypesFactory.eINSTANCE.createED(String.valueOf(thoidiemTuvong)));
                    }
                }
                effectiveTime.setLow(lowTime);
                
                //Lý do tử vong
                var emrDmLyDoTuVong = emrTinhTrangRaVien.emrDmLyDoTuVong;
                if(emrDmLyDoTuVong != null){
                    var causeOfDeathER = CDAFactory.eINSTANCE.createEntryRelationship();
                    causeOfDeathER.setTypeCode(x_ActRelationshipEntryRelationship.CAUS);
                    deadObs.getEntryRelationships().add(causeOfDeathER);                
                    var causeOfDeathAct = HSBAFactory.eINSTANCE.createHsbaCauseOfDeathAct().init();
                    II causeOfDeathActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                    causeOfDeathAct.getIds().add(causeOfDeathActId);
                    causeOfDeathER.setAct(causeOfDeathAct);
                    CD causeOfDeathCode = DatatypesFactory.eINSTANCE.createCD();                    
                    
                    String code = emrDmLyDoTuVong.maicd; 
                    if(!StringUtils.isEmpty(code)){
                        causeOfDeathCode.setCode(code);                 
                    }
                    String codeSystem = emrParameters.get("emr_dm_ly_do_tu_vong");
                    causeOfDeathCode.setCodeSystem(codeSystem);
            
                    String displayName = emrDmLyDoTuVong.ten;
                    if(!StringUtils.isEmpty(displayName)){
                        causeOfDeathCode.setDisplayName(displayName);
                    }
                    causeOfDeathAct.setCode(causeOfDeathCode);
                }                                   
                        
                //Chẩn đoán nguyên nhân tử vong 
                var diagnosisCauseOfDeathER = CDAFactory.eINSTANCE.createEntryRelationship();
                diagnosisCauseOfDeathER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                deadObs.getEntryRelationships().add(diagnosisCauseOfDeathER);           
                
                var diagnosisCauseOfDeathObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init();
                diagnosisCauseOfDeathER.setObservation(diagnosisCauseOfDeathObs);
                
                II diagnosisCauseOfDeathId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                diagnosisCauseOfDeathObs.getIds().add(diagnosisCauseOfDeathId); 
                //-- mô tả nguyên nhân tử vong
                String nguyennhan = emrTinhTrangRaVien.motanguyennhantuvong;
                if(!StringUtils.isEmpty(nguyennhan)){
                    ED deathDiagnosis = DatatypesFactory.eINSTANCE.createED(nguyennhan);
                    diagnosisCauseOfDeathObs.setText(deathDiagnosis);
                }
                var emrNguyenNhanTuVong = emrTinhTrangRaVien.emrDmNguyennhantuvong;
                if(emrNguyenNhanTuVong != null){
                    String code = emrNguyenNhanTuVong.maicd;
                    String codeSystem = emrParameters.get("emr_dm_ma_benh");            
                    String displayName = emrNguyenNhanTuVong.ten;
                    dischargeSummaryText.append(properties.getProperty("TTRV_GIAIPHAUTUTHI_TITLE", "TTRV_GIAIPHAUTUTHI_TITLE")).append(contentDelimiter).append(displayName).append(splitDelimiter);
                    addCdObservationValueTag(diagnosisCauseOfDeathObs, code, codeSystem, displayName);
                }               
                    
                var autopsyExaminationER = CDAFactory.eINSTANCE.createEntryRelationship();
                autopsyExaminationER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                deadObs.getEntryRelationships().add(autopsyExaminationER);
                
                //Khám nghiệm giải phẫu tử thi              
                Boolean isAutospy = emrTinhTrangRaVien.khamnghiemtuthi;        
                var autospyExaminationProc = HSBAFactory.eINSTANCE.createHsbaAutopsyExaminationProcedure().init();
                autopsyExaminationER.setProcedure(autospyExaminationProc);
                II autospyExaminationProcId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                autospyExaminationProc.getIds().add(autospyExaminationProcId);
                if(isAutospy != null && isAutospy){
                    autospyExaminationProc.setText(DatatypesFactory.eINSTANCE.createED("true"));                    
                }else{
                    autospyExaminationProc.setText(DatatypesFactory.eINSTANCE.createED("false"));
                }
                
                //Chẩn đoán giải phẫu tử thi
                var autopsyDiagnosisER = CDAFactory.eINSTANCE.createEntryRelationship();
                autopsyDiagnosisER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                autospyExaminationProc.getEntryRelationships().add(autopsyDiagnosisER);
                
                var autopsyDiagnosisCauseOfDeathObs = HSBAFactory.eINSTANCE.createHsbaPrimaryDiagnosisObservation().init();
                autopsyDiagnosisER.setObservation(autopsyDiagnosisCauseOfDeathObs);
                
                II autopsyDiagnosisCauseOfDeathId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                autopsyDiagnosisCauseOfDeathObs.getIds().add(autopsyDiagnosisCauseOfDeathId);       
                String autospyCause = emrTinhTrangRaVien.motagiaiphaututhi;
                //-- mô tả chẩn đoán giải phẫu tử thi
                if(!StringUtils.isEmpty(autospyCause)){
                    ED autospyCauseDiagnosisText = DatatypesFactory.eINSTANCE.createED(autospyCause);
                    autopsyDiagnosisCauseOfDeathObs.setText(autospyCauseDiagnosisText);
                }           
                var emrGiaiPhauTuThi = emrTinhTrangRaVien.emrDmGiaiphaututhi;
                if(emrGiaiPhauTuThi != null){
                    String code = emrGiaiPhauTuThi.maicd;
                    String codeSystem = emrParameters.get("emr_dm_ma_benh");            
                    String displayName = emrGiaiPhauTuThi.ten;
                    dischargeSummaryText.append(properties.getProperty("TTRV_GIAIPHAUTUTHI_TITLE", "TTRV_GIAIPHAUTUTHI_TITLE")).append(contentDelimiter).append(displayName).append(splitDelimiter);
                    addCdObservationValueTag(autopsyDiagnosisCauseOfDeathObs, code, codeSystem, displayName);
                }       
                
            }
            
            if(dischargeSummaryText.length() > 0){
                dischargeSummaryText.setLength(dischargeSummaryText.length() - 1);
            }
            
            setSectionData(dischargeSummarySection, dischargeSummaryText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
        
    @SuppressWarnings("unchecked")
    private static void handleDefaults(EObject root) {
        Queue<EObject> queue = new LinkedList<EObject>();
        queue.add(root); // root
        while (!queue.isEmpty()) {
            EObject eObject = queue.remove();
            EClass eClass = eObject.eClass();
            for (EAttribute attribute : eClass.getEAllAttributes()) { // visit
                if (!eObject.eIsSet(attribute) && attribute.getLowerBound() > 0 &&
                        attribute.getDefaultValueLiteral() != null) {
                    if (attribute.isMany()) {
                        List<Object> list = (List<Object>) eObject.eGet(attribute);
                        list.add(attribute.getDefaultValue());
                    } else {
                        eObject.eSet(attribute, attribute.getDefaultValue());
                    }
                }
            }
            for (EReference reference : eClass.getEAllReferences()) { // process successors
                Object value = eObject.eGet(reference);
                if (value != null) {
                    if (reference.isMany()) {
                        queue.addAll((List<EObject>) value);
                    } else {
                        queue.add((EObject) value);
                    }
                }
            }
        }
    }
    
    private static XMLResource prepare(ClinicalDocument clinicalDocument, boolean defaults, String xslFile) {
        if (defaults) {
            handleDefaults(clinicalDocument);
        }
        XMLResource resource = (XMLResource) clinicalDocument.eResource();
        if (resource == null) {
            resource = (XMLResource) CDAUtil.createResourceSet().createResource(URI.createURI(CDAPackage.eNS_URI)); 

            DocumentRoot root = CDAFactory.eINSTANCE.createDocumentRoot();
            root.setClinicalDocument(clinicalDocument);
            root.getXMLNSPrefixMap().put("", CDAPackage.eNS_URI);
            root.getXSISchemaLocation().put(CDAPackage.eNS_URI, "cda.xsd");                                 
            
            //add cda stylesheet
            FeatureMapUtil.addProcessingInstruction(root.getMixed(), 0, "xml-stylesheet", "type=\"text/xsl\"  href=\"" + xslFile + "\"");           
            resource.getContents().add(root);
        } else {
            DocumentRoot root = (DocumentRoot) clinicalDocument.eContainer();
            List<String> keys = new ArrayList<String>();
            for (Map.Entry<String, String> entry : root.getXMLNSPrefixMap().entrySet()) {
                if (EPackage.Registry.INSTANCE.keySet().contains(entry.getValue())) {
                    keys.add(entry.getKey());
                }
            }
            for (String key : keys) {
                root.getXMLNSPrefixMap().removeKey(key);
            }
            root.getXMLNSPrefixMap().put("", CDAPackage.eNS_URI);
        }
        return resource;
    }
    
    public static void save(ClinicalDocument clinicalDocument, OutputStream out, String xslFile) throws Exception {
        save(clinicalDocument, out, true, xslFile);
    }
    
    public static void save(ClinicalDocument clinicalDocument, OutputStream out, boolean defaults, String xslFile) throws Exception {
        XMLResource resource = prepare(clinicalDocument, defaults, xslFile);
        resource.save(out, null);
    }
    
    public static byte[] exportCDA(@Nonnull EmrHoSoBenhAn hsba) throws Exception {
        var doc = HSBAFactory.eINSTANCE.createHsbaDocument().init();
        var emrParameters = new HashMap<String, String>();
        var emrDmChucNangs = new HashMap<String, Integer>();
        var properties = new Properties();
        generateCDAHeader(doc, hsba, emrParameters, hsba.emrCoSoKhamBenh, properties);
        generateCDABody(doc, hsba, emrParameters, emrDmChucNangs, properties, "");
        XMLResource resource = prepare(doc, true, "cda.xsl");
        
        var bos = new ByteArrayOutputStream();
        resource.save(bos, null);
        return bos.toByteArray();
    }
    
}
