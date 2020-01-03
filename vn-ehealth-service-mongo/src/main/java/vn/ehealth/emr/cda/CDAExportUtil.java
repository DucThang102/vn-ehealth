package vn.ehealth.emr.cda;

import java.util.UUID;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.springframework.util.StringUtils;

import vn.ehealth.emr.model.EmrHoSoBenhAn;

public class CDAExportUtil {

    public static void generateCDAHeader(ClinicalDocument doc, EmrHoSoBenhAn hsba) throws Exception{
        var emrDmLoaiBenhAn = hsba.getEmrDmLoaiBenhAn();
        var emrQuanLyNguoiBenh = hsba.getEmrQuanLyNguoiBenh();          
        var emrBenhNhan = hsba.getEmrBenhNhan();       
        var  emrBenhAn = hsba.getEmrBenhAn();        
        var lstEmrVaoKhoa = hsba.emrVaoKhoas;
        
        var custodian = CDAFactory.eINSTANCE.createCustodian();
        var assignedCustodian = CDAFactory.eINSTANCE.createAssignedCustodian();
        custodian.setAssignedCustodian(assignedCustodian);
        var custodianOrganization = CDAFactory.eINSTANCE.createCustodianOrganization();
        assignedCustodian.setRepresentedCustodianOrganization(custodianOrganization);
        var custodianOrganizationId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString()); 
        custodianOrganization.getIds().add(custodianOrganizationId);
        var custodianName = DatatypesFactory.eINSTANCE.createON();
        custodianOrganization.setName(custodianName);
        String tenBenhVien = hsba.emrCoSoKhamBenh.ten;
        
        if(!StringUtils.isEmpty(tenBenhVien)){
            custodianName.addText(tenBenhVien);
        }   
        doc.setCustodian(custodian);
        
        if(emrDmLoaiBenhAn != null){
            doc.getCode().setCode(emrDmLoaiBenhAn.maicd);
           var hsbaDocTitle = DatatypesFactory.eINSTANCE.createST(emrDmLoaiBenhAn.ten);        
            doc.setTitle(hsbaDocTitle);
        }
        
        var docId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        doc.setId(docId);
        
        String soLuuTru = hsba.getMaluutru();
        if(!StringUtils.isEmpty(soLuuTru)){
            var docSetId = DatatypesFactory.eINSTANCE.createII();
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
            var idHis = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            //idHis.setRoot(properties.getProperty("OID_IDHIS", "OID_IDHIS"));
            idHis.setExtension(strIdHis);
            patientRole.getIds().add(idHis);
        }
        
        var patient = CDAFactory.eINSTANCE.createPatient();
        patientRole.setPatient(patient);
                
        var emrDmLoaiDoiTuongTaiChinh = emrQuanLyNguoiBenh.emrDmLoaiDoiTuongTaiChinh;
        if(emrDmLoaiDoiTuongTaiChinh != null){
            String dttcCodeCda = emrDmLoaiDoiTuongTaiChinh.maicd;
            var dttcCodeTag = DatatypesFactory.eINSTANCE.createCE();
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
            //addPatientInfor(patient, patientRole, hsba, emrParameters, properties);
            
            //I.HÀNH CHÍNH - 11. Họ tên người báo tin + sđt người báo tin       
            //addGuardianPerson(patient, emrBenhNhan);    
                        
            //Thông tin cha mẹ bệnh nhân 
            ///addPatientParents(doc, hsba);
            
            //Nơi làm việc          
            //addEmployer(doc, emrBenhNhan);          
        }
        
        //Đơn vị chủ quản và Trưởng phòng kế hoạch tổng hợp
        //addDvcq_Tpkhth(doc, hsba.emrCoSoKhamBenh, hsba);
        
        //15. Cơ sở khám chữa bệnh
        var componentOf = CDAFactory.eINSTANCE.createComponent1();
        doc.setComponentOf(componentOf);
        
        
        //create encompassing encounter
        var ecpEncounter = CDAFactory.eINSTANCE.createEncompassingEncounter();
        componentOf.setEncompassingEncounter(ecpEncounter);
        
        //24. Mã Y tế
        String maYte = hsba.getMayte();
        if(!StringUtils.isEmpty(maYte)){
            var ecpEncounterId = DatatypesFactory.eINSTANCE.createII();
            ecpEncounterId.setExtension(maYte);
            //ecpEncounterId.setRoot(properties.getProperty("OID_DINHDANHYTEQUOCGIA", "OID_DINHDANHYTEQUOCGIA"));
            ecpEncounter.getIds().add(ecpEncounterId);
        }           
        if(emrQuanLyNguoiBenh != null){
            //II.QUẢN LÝ NGƯỜI BỆNH - 12+18. Ngày vào viện/Ngày ra viện
            //input_OutputHospital(ecpEncounter, hsba, emrParameters, emrCoSoKhamBenh);
        }
                
        if(emrBenhAn != null){
            //16 + 17. Ngày làm bệnh án + Bác sĩ làm bệnh án
            //addPersonCreateDocument(doc, emrBenhAn);
        }

        if(lstEmrVaoKhoa.size() > 0){
            var emrVaoKhoa = lstEmrVaoKhoa.get(0);
            //20+21. Ngày ký bệnh án + Bác sĩ trưởng khoa 
            //addDean(doc, emrVaoKhoa, properties);       
        }
        
        //22. Giám đốc bệnh viện 
        //addHospitalDirector(doc, emrCoSoKhamBenh, hsba, properties);
    }
}
