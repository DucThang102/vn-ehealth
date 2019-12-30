package test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.eclipse.emf.common.util.Diagnostic;
import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.cda.Author;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Component3;
import org.openhealthtools.mdht.uml.cda.Custodian;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.EntryRelationship;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.PatientRole;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.ValidationResult;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.ON;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActClassDocumentEntryAct;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntryRelationship;
import org.openhealthtools.mdht.uml.hl7.vocab.x_DocumentActMood;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import test.TestCDA.Const.COMPONENT.DIAGNOSIS;
import test.TestCDA.Const.OID;
import vn.ehealth.emr.model.EmrBenhNhan;
import vn.ehealth.emr.model.EmrDmContent;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.utils.EmrUtils;

public class TestCDA {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    static class Const {
        public static final String DOC_TYPE_ID_ROOT = "2.16.840.1.113883.1.3";
        public static final String DOC_TYPE_ID_EXTENSION = "POCD_HD000040";
        
        public static final String DOC_SET_ID_EXTENSION = "180340024";
        
        public static class COMPONENT {
            public static class DIAGNOSIS {
                public static final String TEMPLATE_ID = "2.16.840.1.113883.3.6000.4.3";
                public static final String OBS_TEMPLATE_ID = "2.16.840.1.113883.3.6000.5.1.0.1";
                public static final String TITLE = "CHẨN ĐOÁN";
                public static final CE CODE = createCE("29308-4", OID.LOINC, "LOINC", "Diagnosis");
                public static final CE OBS_CODE = createCE("282291009", OID.SNOMED_CT, "SNOMED CT", "Diagnosis");
                
                public static class PRELIMINARY {
                    public static final String TEMPLATE_ID = "2.16.840.1.113883.3.6000.5.1.1";                    
                    public static final CE CODE = createCE("44833-2", OID.LOINC, "LOINC", "Preliminary diagnosis");                    
                }
                
                public static class ADMISSION {
                    public static final String TEMPLATE_ID = "2.16.840.1.113883.3.6000.5.1.2";                    
                    public static final CE CODE = createCE("8646-2", OID.LOINC, "LOINC", "Admission diagnosis");                    
                }
                
                public static class DISCHARGE {
                    public static final String TEMPLATE_ID = "2.16.840.1.113883.3.6000.5.1.4";                    
                    public static final CE CODE = createCE("8651-2", OID.LOINC, "LOINC", "Discharge diagnosis");                    
                }
                
                public static class PRE_OPERATIVE {
                    public static final String TEMPLATE_ID = "2.16.840.1.113883.3.6000.5.1.5";                    
                    public static final CE CODE = createCE("8720-5", OID.LOINC, "LOINC", "Preoperative diagnosis");                    
                }
                
                public static class POST_OPERATIVE {
                    public static final String TEMPLATE_ID = "2.16.840.1.113883.3.6000.5.1.6";                    
                    public static final CE CODE = createCE("8719-7", OID.LOINC, "LOINC", "Postoperative diagnosis");                    
                }
            }
        }
        
        public class OID {
            public static final String LOINC = "2.16.840.1.113883.6.1";
            public static final String SNOMED_CT = "2.16.840.1.113883.6.96";
            public static final String CONFIDENTIAL_CODE = "2.16.840.1.113883.5.25";
            public static final String HSBA = "2.16.840.1.113883.3.6000.3.1";
            public static final String LOAI_BENH_AN = "2.16.840.1.113883.3.6000.2.10";
            
            public static final String IDHIS = "2.16.840.1.113883.3.6000.1.9";        
            public static final String DINHDANHYTEQUOCGIA = "2.16.840.1.113883.3.6000.1.6";        
            public static final String CMND_HOCHIEU = "2.16.840.1.113883.3.6000.1.7";        
            public static final String DM_SO_BHYT = "2.16.840.1.113883.3.6000.1.8";
            
            public static final String DM_MA_BENH = "2.16.840.1.113883.3.6000.6.3";
        }        
        
    }
    static ObjectMapper mapper = EmrUtils.createObjectMapper();
    
    static EmrHoSoBenhAn getHsba() throws IOException {
        var file = new ClassPathResource("static/json/hsba.json").getInputStream();
        var jsonSt = new String(file.readAllBytes());
        file.close();
        
        return mapper.readValue(jsonSt, EmrHoSoBenhAn.class);
    }
    
    static String createUUID() {
        return UUID.randomUUID().toString();
    }
    
    static II createII(String root) {
        return DatatypesFactory.eINSTANCE.createII(root);
    }
    
    static II createII(String root, String extension) {
        if(StringUtils.isEmpty(extension)) {
            extension = null;
        }
        return DatatypesFactory.eINSTANCE.createII(root, extension);
    }
    
    static ST createST(String text) {
        return DatatypesFactory.eINSTANCE.createST(text);
    }
    
    static CE createCE(String code, String codeSystem) {
        return DatatypesFactory.eINSTANCE.createCE(code, codeSystem);        
    }
    
    static CE cloneCE(CE ce) {
        return DatatypesFactory.eINSTANCE.createCE(ce.getCode(), ce.getCodeSystem(), ce.getCodeSystemName(), ce.getDisplayName());
    }
    
    static CE createCE(String code, String codeSystem, String codeSystemName, String displayName) {
        if(StringUtils.isEmpty(codeSystemName))
            codeSystemName = null;
        
        if(StringUtils.isEmpty(displayName))
            displayName = null;
        
        return DatatypesFactory.eINSTANCE.createCE(code, codeSystem, codeSystemName, displayName);        
    }
    
    static CD createCD(String code, String codeSystem, String codeSystemName, String displayName) {
        if(StringUtils.isEmpty(codeSystemName))
            codeSystemName = null;
        
        if(StringUtils.isEmpty(displayName))
            displayName = null;
        
        
        return DatatypesFactory.eINSTANCE.createCD(code, codeSystem, codeSystemName, displayName);        
    }
    
    static TS createTS(Date date) {
        if(date != null) {
            return DatatypesFactory.eINSTANCE.createTS(sdf.format(date));
        }
        return null;
    }
    
    static CS createCS(String text) {
        return DatatypesFactory.eINSTANCE.createCS(text);
    }
 
    
    static PN createPN(String text) {
        var pn = DatatypesFactory.eINSTANCE.createPN();
        pn.addText(text);
        return pn;
    }
    
    static ON createON(String text) {
        var on = DatatypesFactory.eINSTANCE.createON();
        on.addText(text);
        return on;
    }
    
    static ED createED(String text) {
        var ed = DatatypesFactory.eINSTANCE.createED();
        ed.addText(text);
        return ed;        
    }
    
    static Observation createObs() {
        var obs = CDAFactory.eINSTANCE.createObservation();
        obs.setClassCode(ActClassObservation.OBS);
        obs.setMoodCode(x_ActMoodDocumentObservation.EVN);
        return obs;
    }
    
    static Act createAct() {
        var act = CDAFactory.eINSTANCE.createAct();
        act.setClassCode(x_ActClassDocumentEntryAct.ACT);
        act.setMoodCode(x_DocumentActMood.EVN);
        return act;        
    }
    
    static ClinicalDocument createHsbaDoc(EmrHoSoBenhAn hsba) {
        var doc =  CDAFactory.eINSTANCE.createClinicalDocument();
        var typeId = CDAFactory.eINSTANCE.createInfrastructureRootTypeId();
        typeId.setRoot(Const.DOC_TYPE_ID_ROOT);
        typeId.setExtension(Const.DOC_TYPE_ID_EXTENSION);
        
        doc.setTypeId(typeId);
        doc.getTemplateIds().add(createII(Const.OID.HSBA));
        
        doc.setId(createII(createUUID()));
        doc.setTitle(createST(hsba.emrDmLoaiBenhAn.ten));
        doc.setCode(createCE(hsba.emrDmLoaiBenhAn.ma, Const.OID.LOAI_BENH_AN));
        doc.setEffectiveTime(createTS(hsba.ngayluutru));
        doc.setLanguageCode(createCS("vi-VN"));
        doc.setSetId(createII(Const.DOC_TYPE_ID_ROOT, Const.DOC_SET_ID_EXTENSION));                
        doc.setConfidentialityCode(createCE("N", Const.OID.CONFIDENTIAL_CODE));
        
        var component = CDAFactory.eINSTANCE.createComponent2(); 
        doc.setComponent(component);
        
        var body = CDAFactory.eINSTANCE.createStructuredBody();
        component.setStructuredBody(body);
        
        return doc;
    }
    
    static PatientRole createPatientRole(EmrBenhNhan emrBenhNhan) {
        var patientRole = CDAFactory.eINSTANCE.createPatientRole();
        patientRole.getIds().add(createII(Const.OID.IDHIS, emrBenhNhan.idhis));
        patientRole.getIds().add(createII(Const.OID.DINHDANHYTEQUOCGIA, emrBenhNhan.iddinhdanhchinh));
        patientRole.getIds().add(createII(Const.OID.CMND_HOCHIEU, emrBenhNhan.iddinhdanhphu));
        patientRole.getIds().add(createII(Const.OID.DM_SO_BHYT, emrBenhNhan.sobhyt));
        
        return patientRole;
    }
    
    static Author createAuthor(EmrHoSoBenhAn hsba) {
        var author = CDAFactory.eINSTANCE.createAuthor();
        author.setTime(createTS(hsba.emrBenhAn.ngaykybenhan));
        var assignedAuthor = CDAFactory.eINSTANCE.createAssignedAuthor();
        assignedAuthor.getIds().add(createII(createUUID()));
        var assignPerson = CDAFactory.eINSTANCE.createPerson();
        assignPerson.getNames().add(createPN(hsba.emrBenhAn.bacsylambenhan));
        assignedAuthor.setAssignedPerson(assignPerson);
        author.setAssignedAuthor(assignedAuthor);
        return author;
                
    }
    
    static Custodian createCustodian(EmrHoSoBenhAn hsba) {
        var assignedCustodian = CDAFactory.eINSTANCE.createAssignedCustodian();
        var org = CDAFactory.eINSTANCE.createCustodianOrganization();
        org.getIds().add(createII(createUUID()));
        org.setName(createON(hsba.emrCoSoKhamBenh.ten));
       
        assignedCustodian.setRepresentedCustodianOrganization(org);        
        
        var custodian = CDAFactory.eINSTANCE.createCustodian();
        custodian.setAssignedCustodian(assignedCustodian);
        return custodian;
    }
    
    static EntryRelationship addChanDoanEntryRelationship(@Nonnull Entry entry, String text, EmrDmContent emrDmMaBenh) {
        return addChanDoanEntryRelationship(entry, text, List.of(emrDmMaBenh));
    }
    
    static EntryRelationship addChanDoanEntryRelationship(@Nonnull Entry entry, String text, List<EmrDmContent> emrDmMaBenhs) {
        var entryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
        entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        entry.getAct().getEntryRelationships().add(entryRelationship);
        
        var obs = createObs();
        entryRelationship.setObservation(obs);
        
        obs.getTemplateIds().add(createII(DIAGNOSIS.OBS_TEMPLATE_ID));
        obs.setCode(cloneCE(DIAGNOSIS.OBS_CODE));
        obs.setText(createED(text));
        for(var emrDmMaBenh : emrDmMaBenhs) {
            obs.getValues().add(createCD(emrDmMaBenh.ma, OID.DM_MA_BENH, null, emrDmMaBenh.ten));
        }
        return entryRelationship;
    }
    
    static Entry addChanDoanEntry(@Nonnull Section chandoanSection, String templateId, CE code) {
     
        var entry = CDAFactory.eINSTANCE.createEntry();
        var act = createAct();
        entry.setAct(act);
                        
        act.getTemplateIds().add(createII(templateId));
        act.setCode(code);         
        
        chandoanSection.getEntries().add(entry);
        return entry;
    }
    
    static Component3 createChanDoanComponent(EmrHoSoBenhAn hsba) {
        if(hsba.emrChanDoan != null) {
            var component = CDAFactory.eINSTANCE.createComponent3();
            var section = CDAFactory.eINSTANCE.createSection();
            component.setSection(section);
            
            section.getTemplateIds().add(createII(DIAGNOSIS.TEMPLATE_ID));
            section.setCode(cloneCE(DIAGNOSIS.CODE));
            section.setTitle(createST(DIAGNOSIS.TITLE));
            
            var entryChandoanNoiden = addChanDoanEntry(section, DIAGNOSIS.PRELIMINARY.TEMPLATE_ID, cloneCE(DIAGNOSIS.PRELIMINARY.CODE));
            addChanDoanEntryRelationship(entryChandoanNoiden, hsba.emrChanDoan.motachandoannoiden, hsba.emrChanDoan.emrDmMaBenhChandoannoiden);
            
            var entryChandoanKkb = addChanDoanEntry(section, DIAGNOSIS.ADMISSION.TEMPLATE_ID, cloneCE(DIAGNOSIS.ADMISSION.CODE));
            addChanDoanEntryRelationship(entryChandoanKkb, hsba.emrChanDoan.motachandoankkb, hsba.emrChanDoan.emrDmMaBenhChandoankkb);
            
            var entryChandoanRavien = addChanDoanEntry(section, DIAGNOSIS.DISCHARGE.TEMPLATE_ID, cloneCE(DIAGNOSIS.DISCHARGE.CODE));
            addChanDoanEntryRelationship(entryChandoanRavien, hsba.emrChanDoan.motachandoanravienchinh, hsba.emrChanDoan.emrDmMaBenhChandoanravienchinh);
            addChanDoanEntryRelationship(entryChandoanRavien, hsba.emrChanDoan.motachandoanravienkemtheo, hsba.emrChanDoan.emrDmMaBenhChandoanravienkemtheos);
            
            var entryChandoanTruocPttt = addChanDoanEntry(section, DIAGNOSIS.PRE_OPERATIVE.TEMPLATE_ID, cloneCE(DIAGNOSIS.PRE_OPERATIVE.CODE));
            addChanDoanEntryRelationship(entryChandoanTruocPttt, hsba.emrChanDoan.motachandoantruocpt, hsba.emrChanDoan.emrDmMaBenhChandoantruocpts);
            
            var entryChandoanSauPttt = addChanDoanEntry(section, DIAGNOSIS.POST_OPERATIVE.TEMPLATE_ID, cloneCE(DIAGNOSIS.POST_OPERATIVE.CODE));
            addChanDoanEntryRelationship(entryChandoanSauPttt, hsba.emrChanDoan.motachandoansaupt, hsba.emrChanDoan.emrDmMaBenhChandoansaupts);
            return component;
        }
        return null;
    }
    
    public static void main(String[] args) throws Exception {
        var hsba = getHsba();
        System.out.println(hsba.matraodoi);
        
        var doc = createHsbaDoc(hsba);
        
        var recordTarget = CDAFactory.eINSTANCE.createRecordTarget();
        doc.getRecordTargets().add(recordTarget);
        recordTarget.setPatientRole(createPatientRole(hsba.emrBenhNhan));
                
        doc.getAuthors().add(createAuthor(hsba));
        doc.setCustodian(createCustodian(hsba));
        
        var components= doc.getComponent().getStructuredBody().getComponents();
        components.add(createChanDoanComponent(hsba));
        
        var tongketBenhAn = CDAFactory.eINSTANCE.createComponent3();
        components.add(tongketBenhAn);
        
        var tkbaSection = CDAFactory.eINSTANCE.createSection();
        tongketBenhAn.setSection(tkbaSection);
        
        tkbaSection.getTemplateIds().add(DatatypesFactory.eINSTANCE.createII("2.16.840.1.113883.3.6000.4.6"));
        tkbaSection.setCode(DatatypesFactory.eINSTANCE.createCE("28655-9", "2.16.840.1.113883.6.1", "LOINC", "Physician attending Discharge summary"));
        tkbaSection.setTitle(DatatypesFactory.eINSTANCE.createST("Tổng kết bệnh án"));
        
        var text = CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("TKBA");
        tkbaSection.setText(text);
        
        var dienbienLamsang = CDAFactory.eINSTANCE.createComponent5();
        tkbaSection.getComponents().add(dienbienLamsang);
        
        var dienbienLamsangSection = CDAFactory.eINSTANCE.createSection();
        dienbienLamsang.setSection(dienbienLamsangSection);
        
        dienbienLamsangSection.getTemplateIds().add(DatatypesFactory.eINSTANCE.createII("2.16.840.1.113883.3.6000.4.6.1"));
        dienbienLamsangSection.setCode(DatatypesFactory.eINSTANCE.createCE("8648-8", "2.16.840.1.113883.6.1", "LOINC", "HOSPITAL COURSE"));
        dienbienLamsangSection.setTitle(DatatypesFactory.eINSTANCE.createST("Diễn biến lâm sàng"));
        var text2 = CDAFactory.eINSTANCE.createStrucDocText();
        text2.addText("Diễn biến lâm sàng .....");
        dienbienLamsangSection.setText(text2);
        
        var cdYHCT = CDAFactory.eINSTANCE.createComponent3();
        components.add(cdYHCT);
        
        var cdYHCTSection = CDAFactory.eINSTANCE.createSection();
        cdYHCT.setSection(cdYHCTSection);
        
        cdYHCTSection.getTemplateIds().add(DatatypesFactory.eINSTANCE.createII("2.16.840.1.113883.3.6000.4.14"));
        cdYHCTSection.setId(DatatypesFactory.eINSTANCE.createII("2530d8cd-a169-495a-9b7a-80e66e798fcc"));
        cdYHCTSection.setCode(DatatypesFactory.eINSTANCE.createCE("10000", "2.16.840.1.113883.3.6000.6", "YHCT", "Chẩn đoán YHCT"));
        cdYHCTSection.setTitle(DatatypesFactory.eINSTANCE.createST("Chẩn đoán YHCT"));
        var text3 = CDAFactory.eINSTANCE.createStrucDocText();
        text3.addText("");
        cdYHCTSection.setText(text3);
        
        var entry = CDAFactory.eINSTANCE.createEntry();
        cdYHCTSection.getEntries().add(entry);
        
        var obs =  CDAFactory.eINSTANCE.createObservation();
        entry.setObservation(obs);
        
        obs.setClassCode(ActClassObservation.OBS);
        obs.setMoodCode(x_ActMoodDocumentObservation.EVN);
        obs.getTemplateIds().add(DatatypesFactory.eINSTANCE.createII("2.16.840.1.113883.3.6000.5.49"));
        obs.getIds().add(DatatypesFactory.eINSTANCE.createII("76ba9305-2ab2-462d-8d27-116e3a63d8c8"));
        obs.setCode(DatatypesFactory.eINSTANCE.createCD("10100", "2.16.840.1.113883.3.6000.6", "YHCT", "Chẩn đoán vào viện"));
        
        var entryRelationship = CDAFactory.eINSTANCE.createEntryRelationship();
        obs.getEntryRelationships().add(entryRelationship);
        
        entryRelationship.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        var obs2 = CDAFactory.eINSTANCE.createObservation();
        entryRelationship.setObservation(obs2);
        
        obs2.setClassCode(ActClassObservation.OBS);
        obs2.setMoodCode(x_ActMoodDocumentObservation.EVN);
        obs2.getTemplateIds().add(DatatypesFactory.eINSTANCE.createII("2.16.840.1.113883.3.6000.5.52"));
        obs2.getIds().add(DatatypesFactory.eINSTANCE.createII("0539e062-21d2-4102-8b35-9bf7b39f7c61"));
        obs2.setCode(DatatypesFactory.eINSTANCE.createCD("282291009", "2.16.840.1.113883.6.96", "SNOMED-CT", "Diagnosis"));        
        obs2.setText(DatatypesFactory.eINSTANCE.createED(""));
                
        CDAUtil.save(doc, System.out);
        
        ValidationResult result = new ValidationResult();
        CDAUtil.validate(doc, result);
        
        for (Diagnostic diagnostic : result.getErrorDiagnostics()) {
            System.out.println("ERROR: " + diagnostic.getMessage());
        }
    }
}
