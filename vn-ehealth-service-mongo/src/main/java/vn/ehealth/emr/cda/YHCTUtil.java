package vn.ehealth.emr.cda;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Organizer;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVXB_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.PQ;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.ActRelationshipHasComponent;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntry;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntryRelationship;
import org.openhealthtools.mdht.uml.hl7.vocab.x_DocumentSubstanceMood;
import org.springframework.util.StringUtils;

import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HSBAFactory;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaAdministrationOfSubstanceAct;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaBenhAnYHCTSection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaChanDoanYHCTSection;
import vn.dtt.openhealthtools.mdht.uml.cda.hsba.HsbaDocument;
import vn.ehealth.emr.model.EmrDmContent;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.model.EmrYhctBenhAn;
import vn.ehealth.emr.model.EmrYhctChanDoan;
import vn.ehealth.emr.model.EmrYhctDonThuocChiTiet;

public class YHCTUtil {

    public static void addChanDoanYhctBenhDanh(Observation obs, EmrDmContent emrDmYhctBenhDanh, String moTaBenhDanh, StringBuilder chanDoanYhctText, Properties properties, String contentDelimiter, String strSplit, String benhDanhTitle){
        var benhDanhER = CDAFactory.eINSTANCE.createEntryRelationship();
        benhDanhER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        obs.getEntryRelationships().add(benhDanhER);
        
        var benhDanhObs = HSBAFactory.eINSTANCE.createHsbaYhctBenhDanhObservation().init();
        benhDanhER.setObservation(benhDanhObs);
        II yhctBenhDanhVaoVienObsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        benhDanhObs.getIds().add(yhctBenhDanhVaoVienObsId);         
        
        CD codeTag = benhDanhObs.getCode(); 
        if(emrDmYhctBenhDanh != null){
            String code = emrDmYhctBenhDanh.maicd;
            if(!StringUtils.isEmpty(code)){
                codeTag.setCode(code);
            }
        }

        ED motaBenhDanhText = DatatypesFactory.eINSTANCE.createED();
        benhDanhObs.setText(motaBenhDanhText);
        if(!StringUtils.isEmpty(moTaBenhDanh)){
            motaBenhDanhText.addText(moTaBenhDanh);
            chanDoanYhctText.append(benhDanhTitle).append(contentDelimiter).append(moTaBenhDanh).append(strSplit);
        }   
    }
    
    public static void addLstTenForOrganizer(Organizer organizer, String lstTen){
        if(!StringUtils.isEmpty(lstTen)){                
            var component = CDAFactory.eINSTANCE.createComponent4();
            component.setTypeCode(ActRelationshipHasComponent.COMP);
            organizer.getComponents().add(component);
            
            var lstTenObs = HSBAFactory.eINSTANCE.createHsbaYhctLstTenObservation().init();
            ED lstTenED = DatatypesFactory.eINSTANCE.createED(lstTen);
            lstTenObs.setText(lstTenED);
            component.setObservation(lstTenObs);
        }       
    }
    
    public static void addOrganizerObservation(Organizer organizer, Observation obs, String codeCda, String codeSystem){
        var component = CDAFactory.eINSTANCE.createComponent4();
        component.setTypeCode(ActRelationshipHasComponent.COMP);
        organizer.getComponents().add(component);
                
        II obsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        obs.getIds().add(obsId);
        component.setObservation(obs);
                
        CDAExportUtil.addCdObservationValueTag(obs, codeCda, codeSystem, "");      
    }
    
    public static void addYhctBatCuongOrganizer(Observation obs, String lstBatCuong, String lstBatCuongTen, String motaBatCuong, Map<String, String> emrParameters, Properties properties, String strSplit){
        var batCuongER = CDAFactory.eINSTANCE.createEntryRelationship();
        batCuongER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        obs.getEntryRelationships().add(batCuongER);
        
        var batCuongOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctBatCuongOrganizer().init();
        II batCuongOrganizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        batCuongOrganizer.getIds().add(batCuongOrganizerId);
        batCuongER.setOrganizer(batCuongOrganizer);
        
        //add Text mô tả
        if(!StringUtils.isEmpty(motaBatCuong)){
            var motaBatCuongObs = HSBAFactory.eINSTANCE.createHsbaYhctTextMoTaObservation().init();
            motaBatCuongObs.setText(DatatypesFactory.eINSTANCE.createED(motaBatCuong));
            batCuongOrganizer.addObservation(motaBatCuongObs);
        }
        
        //add lstTen
        addLstTenForOrganizer(batCuongOrganizer, lstBatCuongTen);
        
        //add dm_yhct_bat_cuong by lstCodeCda
        if(!StringUtils.isEmpty(strSplit) && !StringUtils.isEmpty(lstBatCuong)){
            String[] arrCodeCdas = lstBatCuong.split(strSplit);
            for (String codeCdaItem : arrCodeCdas) {
                if(!StringUtils.isEmpty(codeCdaItem)){
                    String codeSystem = emrParameters.get("emr_dm_yhct_bat_cuong");
                    var batCuongObs = HSBAFactory.eINSTANCE.createHsbaYhctBatCuongObservation().init();
                    addOrganizerObservation(batCuongOrganizer, batCuongObs, codeCdaItem, codeSystem);
                }
            }
        }       
    }
    
    
    public static void addYhctTangPhuOrganizer(Observation obs, String lstTangPhu, String lstTangPhuTen, String motaTangPhu, Map<String, String> emrParameters, Properties properties, String strSplit){
        var tangPhuER = CDAFactory.eINSTANCE.createEntryRelationship();
        tangPhuER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        obs.getEntryRelationships().add(tangPhuER);
        
        var tangPhuOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctTangPhuOrganizer().init();
        II tangPhuOrganizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        tangPhuOrganizer.getIds().add(tangPhuOrganizerId);
        tangPhuER.setOrganizer(tangPhuOrganizer);
    
        //add Text mô tả
        if(!StringUtils.isEmpty(motaTangPhu)){
            var motaTangPhuObs = HSBAFactory.eINSTANCE.createHsbaYhctTextMoTaObservation().init();
            motaTangPhuObs.setText(DatatypesFactory.eINSTANCE.createED(motaTangPhu));
            tangPhuOrganizer.addObservation(motaTangPhuObs);
        }
        
        //add lstTen
        addLstTenForOrganizer(tangPhuOrganizer, lstTangPhuTen);
        
        //add dm_yhct_tang_phu          
        if(!StringUtils.isEmpty(strSplit) && !StringUtils.isEmpty(lstTangPhu)){
            String[] arrCodeCdas = lstTangPhu.split(strSplit);
            for (String codeCdaItem : arrCodeCdas) {
                if(!StringUtils.isEmpty(codeCdaItem)){
                    String codeSystem = emrParameters.get("emr_dm_yhct_tang_phu");
                    var tangPhuObs = HSBAFactory.eINSTANCE.createHsbaYhctTangPhuObservation().init();
                    addOrganizerObservation(tangPhuOrganizer, tangPhuObs, codeCdaItem, codeSystem);
                }
            }
        }   
    }
    
    public static void addYhctKinhMachOrganizer(Observation obs, String lstKinhMach, String lstKinhMachTen, String motaKinhMach, Map<String, String> emrParameters, Properties properties, String strSplit){
        var kinhMachER = CDAFactory.eINSTANCE.createEntryRelationship();
        kinhMachER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        obs.getEntryRelationships().add(kinhMachER);
        
        var kinhMachOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctKinhMachOrganizer().init();
        II kinhMachOrganizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        kinhMachOrganizer.getIds().add(kinhMachOrganizerId);
        kinhMachER.setOrganizer(kinhMachOrganizer);
        
        //add lstTen
        addLstTenForOrganizer(kinhMachOrganizer, lstKinhMachTen);
    
        if(!StringUtils.isEmpty(motaKinhMach)){
            var motaKinhMachObs = HSBAFactory.eINSTANCE.createHsbaYhctTextMoTaObservation().init();
            motaKinhMachObs.setText(DatatypesFactory.eINSTANCE.createED(motaKinhMach));
            kinhMachOrganizer.addObservation(motaKinhMachObs);
        }       
        
        if(!StringUtils.isEmpty(strSplit) && !StringUtils.isEmpty(lstKinhMach)){
            String[] arrCodeCdas = lstKinhMach.split(strSplit);
            for (String codeCdaItem : arrCodeCdas) {
                if(!StringUtils.isEmpty(codeCdaItem)){
                    String codeSystem = emrParameters.get("emr_dm_yhct_kinh_mach");
                    var kinhMachObs = HSBAFactory.eINSTANCE.createHsbaYhctKinhMachObservation().init();
                    addOrganizerObservation(kinhMachOrganizer, kinhMachObs, codeCdaItem, codeSystem);
                }
            }
        }
    }
    
    public static void addYhctDinhViBenhOrganizer(Observation obs, String lstDinhViBenh, String lstDinhViBenhTen, Map<String, String> emrParameters, Properties properties, String strSplit){
        var dinhViER = CDAFactory.eINSTANCE.createEntryRelationship();
        dinhViER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        obs.getEntryRelationships().add(dinhViER);
        
        var dinhViOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctDinhViBenhOrganizer().init();
        II dinhViOrganizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        dinhViOrganizer.getIds().add(dinhViOrganizerId);
        dinhViER.setOrganizer(dinhViOrganizer);
        
        //add lstTen
        addLstTenForOrganizer(dinhViOrganizer, lstDinhViBenhTen);
                
        if(!StringUtils.isEmpty(strSplit) && !StringUtils.isEmpty(lstDinhViBenh)){
            String[] arrCodeCdas = lstDinhViBenh.split(strSplit);
            for (String codeCdaItem : arrCodeCdas) {
                if(!StringUtils.isEmpty(codeCdaItem)){
                    String codeSystem = emrParameters.get("emr_dm_yhct_dinh_vi_benh");
                    var dinhViObs = HSBAFactory.eINSTANCE.createHsbaYhctDinhViBenhObservation().init();
                    addOrganizerObservation(dinhViOrganizer, dinhViObs, codeCdaItem, codeSystem);
                }
            }
        }   
    }
    
    public static void addYhctNguyenNhanOrganizer(Observation obs,  String lstNguyenNhan, String lstNguyenNhanTen, String motaNguyenNhan, Map<String, String> emrParameters, Properties properties, String strSplit){
        var nguyenNhanER = CDAFactory.eINSTANCE.createEntryRelationship();
        nguyenNhanER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        obs.getEntryRelationships().add(nguyenNhanER);
        
        var nguyenNhanOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctNguyenNhanOrganizer().init();
        II nguyenNhanOrganizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        nguyenNhanOrganizer.getIds().add(nguyenNhanOrganizerId);
        nguyenNhanER.setOrganizer(nguyenNhanOrganizer);
        
        //add lstTen
        addLstTenForOrganizer(nguyenNhanOrganizer, lstNguyenNhanTen);
        
        if(!StringUtils.isEmpty(motaNguyenNhan)){
            var motaNguyenNhanObs = HSBAFactory.eINSTANCE.createHsbaYhctTextMoTaObservation().init();
            motaNguyenNhanObs.setText(DatatypesFactory.eINSTANCE.createED(motaNguyenNhan));
            nguyenNhanOrganizer.addObservation(motaNguyenNhanObs);
        }       
        
        if(!StringUtils.isEmpty(strSplit) && !StringUtils.isEmpty(lstNguyenNhan)){
            String[] arrCodeCdas = lstNguyenNhan.split(strSplit);
            for (String codeCdaItem : arrCodeCdas) {
                if(!StringUtils.isEmpty(codeCdaItem)){
                    String codeSystem = emrParameters.get("emr_dm_yhct_nguyen_nhan_benh");
                    var nguyenNhanObs = HSBAFactory.eINSTANCE.createHsbaYhctNguyenNhanObservation().init();
                    addOrganizerObservation(nguyenNhanOrganizer, nguyenNhanObs, codeCdaItem, codeSystem);
                }
            }
        }       
    }
    
    public static void addYhctChanDoanVaoVien(HsbaChanDoanYHCTSection chanDoanYhctSection, EmrYhctChanDoan emrYhctChanDoan, Map<String, String> emrParameters, Properties properties, StringBuilder chanDoanYhctText, String strSplit){
        var yhctChanDoanVaoVienObs = HSBAFactory.eINSTANCE.createHsbaYhctChanDoanVaoVienObservation().init();
        chanDoanYhctSection.addObservation(yhctChanDoanVaoVienObs);
        II yhctChanDoanVaoVienObsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        yhctChanDoanVaoVienObs.getIds().add(yhctChanDoanVaoVienObsId);      
        String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");     
        String benhDanhVaoVienTitle = properties.getProperty("CD_YHCT_BENHDANH_VAOVIEN_TITLE", "CD_YHCT_BENHDANH_VAOVIEN_TITLE");       
        String motaBatCuong = emrYhctChanDoan.motabatcuongVv;
        String motaTangPhu = emrYhctChanDoan.motatangphuVv;
        String motaKinhMach = emrYhctChanDoan.motakinhmachVv;
        String motaNguyenNhan = emrYhctChanDoan.motanguyennhanbenhVv;
        
        //Bệnh danh 
        String moTaBenhDanh = emrYhctChanDoan.motabenhdanhyhctvaovien;
        addChanDoanYhctBenhDanh(yhctChanDoanVaoVienObs, emrYhctChanDoan.emrDmYhctBenhdanhVaovien, moTaBenhDanh,  chanDoanYhctText, properties, contentDelimiter, strSplit, benhDanhVaoVienTitle);
        
        //Bát cương
        //String lstBatCuong = emrYhctChanDoan.getLstbatcuongVv();
        //String lstBatCuongTen = emrYhctChanDoan.getLstbatcuongVvTen();
        addYhctBatCuongOrganizer(yhctChanDoanVaoVienObs, "", "", motaBatCuong, emrParameters, properties, strSplit);        
        
        //Tạng phủ
        //String lstTangPhu = emrYhctChanDoan.getLsttangphuVv();
        //String lstTangPhuTen = emrYhctChanDoan.getLsttangphuVvTen();
        addYhctTangPhuOrganizer(yhctChanDoanVaoVienObs, "", "", motaTangPhu, emrParameters, properties, strSplit);
        
        //Kinh mạch
        //String lstKinhMach = emrYhctChanDoan.getLstkinhmachVv();
        //String lstKinhMachTen = emrYhctChanDoan.getLstkinhmachVvTen();
        addYhctKinhMachOrganizer(yhctChanDoanVaoVienObs, "", "", motaKinhMach, emrParameters, properties, strSplit);
        
        //Định vị bệnh
        //String lstDinhViBenh = emrYhctChanDoan.getLstdinhvibenhVv();
        //String lstDinhViBenhTen = emrYhctChanDoan.getLstdinhvibenhVvTen();
        addYhctDinhViBenhOrganizer(yhctChanDoanVaoVienObs, "", "", emrParameters, properties, strSplit);
        
        //Nguyên nhân
        //String lstNguyenNhan = emrYhctChanDoan.getLstnguyennhanbenhVv();
        //String lstNguyenNhanTen = emrYhctChanDoan.getLstnguyennhanbenhVvTen();
        addYhctNguyenNhanOrganizer(yhctChanDoanVaoVienObs, "", "", motaNguyenNhan, emrParameters, properties, strSplit);       
    }
    
    
    public static void addYhctChanDoanVaoKhoaDieuTri(HsbaChanDoanYHCTSection chanDoanYhctSection, EmrYhctChanDoan emrYhctChanDoan, Map<String, String> emrParameters, Properties properties, StringBuilder chanDoanYhctText, String strSplit){
        var yhctChanDoanVaoKhoaDieuTriObs = HSBAFactory.eINSTANCE.createHsbaYhctChanDoanVaoKhoaDieuTriObservation().init();
        chanDoanYhctSection.addObservation(yhctChanDoanVaoKhoaDieuTriObs);
        II yhctChanDoanVaoKhoaDieuTriObsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        yhctChanDoanVaoKhoaDieuTriObs.getIds().add(yhctChanDoanVaoKhoaDieuTriObsId);        
        String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");     
        String benhDanhVaoKdtTitle = properties.getProperty("CD_YHCT_BENHDANH_VAOKDT_TITLE", "CD_YHCT_BENHDANH_VAOKDT_TITLE");
        String motaBatCuong = emrYhctChanDoan.motabatcuong;
        String motaTangPhu = emrYhctChanDoan.motatangphu;
        String motaKinhMach = emrYhctChanDoan.motakinhmach;
        String motaNguyenNhan = emrYhctChanDoan.motanguyennhanbenh;
        
        //Bệnh danh 
        String moTaBenhDanh = emrYhctChanDoan.motabenhdanhyhctvaokhoa;
        addChanDoanYhctBenhDanh(yhctChanDoanVaoKhoaDieuTriObs, emrYhctChanDoan.emrDmYhctBenhdanhVk, moTaBenhDanh, chanDoanYhctText, properties, contentDelimiter, strSplit, benhDanhVaoKdtTitle);
        
        //Bát cương
        String lstBatCuong = "";//emrYhctChanDoan.getLstbatcuong();
        String lstBatCuongTen = "";//emrYhctChanDoan.getLstbatcuongTen();
        addYhctBatCuongOrganizer(yhctChanDoanVaoKhoaDieuTriObs, lstBatCuong, lstBatCuongTen, motaBatCuong, emrParameters, properties, strSplit);
    
        //Tạng phủ
        String lstTangPhu = "";// emrYhctChanDoan.getLsttangphu();
        String lstTangPhuTen = "";//emrYhctChanDoan.getLsttangphuTen();
        addYhctTangPhuOrganizer(yhctChanDoanVaoKhoaDieuTriObs, lstTangPhu, lstTangPhuTen, motaTangPhu, emrParameters, properties, strSplit);
        
        //Kinh mạch
        String lstKinhMach = "";//emrYhctChanDoan.getLstkinhmach();
        String lstKinhMachTen = "";//emrYhctChanDoan.getLstkinhmachTen();
        addYhctKinhMachOrganizer(yhctChanDoanVaoKhoaDieuTriObs, lstKinhMach, lstKinhMachTen, motaKinhMach, emrParameters, properties, strSplit);
                    
        //Định vị bệnh
        String lstDinhViBenh = "";//emrYhctChanDoan.getLstdinhvibenh();
        String lstDinhViBenhTen = "";//emrYhctChanDoan.getLstdinhvibenhTen();
        addYhctDinhViBenhOrganizer(yhctChanDoanVaoKhoaDieuTriObs, lstDinhViBenh, lstDinhViBenhTen, emrParameters, properties, strSplit);
        
        //Nguyên nhân
        String lstNguyenNhan = "";//emrYhctChanDoan.getLstnguyennhanbenh();
        String lstNguyenNhanTen = "";//emrYhctChanDoan.getLstnguyennhanbenhTen();
        addYhctNguyenNhanOrganizer(yhctChanDoanVaoKhoaDieuTriObs, lstNguyenNhan, lstNguyenNhanTen, motaNguyenNhan, emrParameters, properties, strSplit);
    }
    
    public static void addYhctChanDoanRaVien(HsbaChanDoanYHCTSection chanDoanYhctSection, EmrYhctChanDoan emrYhctChanDoan, StringBuilder chanDoanYhctText, Properties properties, String strSplit){
        var yhctChanDoanRaVienObs = HSBAFactory.eINSTANCE.createHsbaYhctChanDoanRaVienObservation().init();
        chanDoanYhctSection.addObservation(yhctChanDoanRaVienObs);
        II yhctChanDoanRaVienObsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        yhctChanDoanRaVienObs.getIds().add(yhctChanDoanRaVienObsId);
        String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
        String benhDanhTitle = properties.getProperty("CD_YHCT_BENHDANH_RAVIEN_TITLE", "CD_YHCT_BENHDANH_RAVIEN_TITLE");        
        
        //Bệnh danh  
        var benhDanhObs = HSBAFactory.eINSTANCE.createHsbaYhctBenhDanhObservation().init();
        var benhDanhER = CDAFactory.eINSTANCE.createEntryRelationship();
        benhDanhER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
        yhctChanDoanRaVienObs.getEntryRelationships().add(benhDanhER);
        
        benhDanhER.setObservation(benhDanhObs);
        II yhctBenhDanhRaVienObsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        benhDanhObs.getIds().add(yhctBenhDanhRaVienObsId);
        
        CD codeTag = benhDanhObs.getCode();
        var emrDmYhctBenhDanh = emrYhctChanDoan.emrDmYhctBenhdanhRavien;
        if(emrDmYhctBenhDanh != null){
            String code = emrDmYhctBenhDanh.maicd;
            if(!StringUtils.isEmpty(code)){
                codeTag.setCode(code);
            }
        }
        String moTaBenhDanhRaVien = emrYhctChanDoan.motabenhdanhyhctravien;
        if(!StringUtils.isEmpty(moTaBenhDanhRaVien)){
            ED motaBenhDanhText = DatatypesFactory.eINSTANCE.createED(moTaBenhDanhRaVien);
            benhDanhObs.setText(motaBenhDanhText);
            chanDoanYhctText.append(benhDanhTitle).append(contentDelimiter).append(moTaBenhDanhRaVien);
        }
        if(chanDoanYhctText.length() > 0 && strSplit.equals(chanDoanYhctText.substring(chanDoanYhctText.length() - 1))){
            chanDoanYhctText.setLength(chanDoanYhctText.length() - 1);
        }   
    }
    
    public static void addChanDoanYHCT(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters, Properties properties){        
        var chanDoanYhctSection = HSBAFactory.eINSTANCE.createHsbaChanDoanYHCTSection().init();
        doc.addSection(chanDoanYhctSection);
        String chanDoanYhctTitle = properties.getProperty("CHANDOAN_YHCT_TITLE", "CHANDOAN_YHCT_TITLE");
        String strSplit = properties.getProperty("SPLIT_YHCT_CHANDOAN_VALUE", "SPLIT_YHCT_CHANDOAN_VALUE");
        CDAExportUtil.addSectionTitle(chanDoanYhctSection, chanDoanYhctTitle);
        II chanDoanYhctSectionId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        chanDoanYhctSection.setId(chanDoanYhctSectionId);
        StringBuilder chanDoanYhctText = new StringBuilder();
        
        var emrYhctChanDoan = emrDanhSachHoSoBenhAn.getEmrYhctChanDoan();
        if(emrYhctChanDoan != null){
            //Chẩn đoán vào viện 
            addYhctChanDoanVaoVien(chanDoanYhctSection, emrYhctChanDoan, emrParameters, properties, chanDoanYhctText, strSplit);    
            
            //Chẩn đoán vào khoa điều trị
            addYhctChanDoanVaoKhoaDieuTri(chanDoanYhctSection, emrYhctChanDoan, emrParameters, properties, chanDoanYhctText, strSplit);
            
            //Chẩn đoán ra viện
            addYhctChanDoanRaVien(chanDoanYhctSection, emrYhctChanDoan, chanDoanYhctText, properties, strSplit);    
        }
                
        CDAExportUtil.setSectionData(chanDoanYhctSection, chanDoanYhctText.toString());      
    }
    
    
    public static void addVongChanSection(HsbaBenhAnYHCTSection benhAnYhctSection, EmrYhctBenhAn emrYhctBenhAn, Map<String, String> emrParameters, Properties properties, String strSplit){
        var vongChanSection = HSBAFactory.eINSTANCE.createHsbaVongChanSection().init();
        benhAnYhctSection.addSection(vongChanSection);
        String vongChanTitle = properties.getProperty("YHCT_VONGCHAN_TITLE", "YHCT_VONGCHAN_TITLE");
        CDAExportUtil.addSectionTitle(vongChanSection, vongChanTitle);       
        StringBuilder vongChanSectionText = new StringBuilder();
        
        var emrYhctBenhanVongChan = emrYhctBenhAn.emrYhctBenhanVongChan;
        if(emrYhctBenhanVongChan != null){
            //Hình thái     
            String lstHinhThai = "";// emrYhctBenhanVongChan.getLsthinhthai();
            String lstHinhThaiTen = "";// emrYhctBenhanVongChan.getLsthinhthaiTen();
            String hinhThaiValueCodeSystem = "";// emrParameters.get("emr_dm_yhct_hinh_thai");
            var vcHinhThaiObs = HSBAFactory.eINSTANCE.createHsbaVongChanHinhThaiObservation().init();   
            String childHinhThaiObsTmpId = properties.getProperty("VONGCHAN_HINHTHAI_TEMPLATEID", "VONGCHAN_HINHTHAI_TEMPLATEID");              
            addYhctChildObservationInObservationElm(vongChanSection, strSplit, emrParameters, properties, lstHinhThai, lstHinhThaiTen, hinhThaiValueCodeSystem, vcHinhThaiObs, childHinhThaiObsTmpId);
            
            //Thần
            String lstThan = "";// emrYhctBenhanVongChan.getLstthan();
            String lstThanTen = ""; //emrYhctBenhanVongChan.getLstthanTen();
            String thanValueCodeSystem =  emrParameters.get("emr_dm_yhct_than");
            var vcThanObs = HSBAFactory.eINSTANCE.createHsbaVongChanThanObservation().init();   
            String childThanObsTmpId = properties.getProperty("VONGCHAN_THAN_TEMPLATEID", "VONGCHAN_THAN_TEMPLATEID");
            addYhctChildObservationInObservationElm(vongChanSection, strSplit, emrParameters, properties, lstThan, lstThanTen, thanValueCodeSystem, vcThanObs, childThanObsTmpId);
            
            //Sắc
            String lstSac = "";// emrYhctBenhanVongChan.getLstsac();
            String lstSacTen = "";//emrYhctBenhanVongChan.getLstsacTen();
            String sacValueCodeSystem = emrParameters.get("emr_dm_yhct_sac");
            var vcSacObs = HSBAFactory.eINSTANCE.createHsbaVongChanSacObservation().init();
            String childSacObsTmpId = properties.getProperty("VONGCHAN_SAC_TEMPLATEID", "VONGCHAN_SAC_TEMPLATEID");
            addYhctChildObservationInObservationElm(vongChanSection, strSplit, emrParameters, properties, lstSac, lstSacTen, sacValueCodeSystem, vcSacObs, childSacObsTmpId);
            
            //Trạch
            String lstTrach = "";//emrYhctBenhanVongChan.getLsttrach();
            String lstTrachTen = "";//emrYhctBenhanVongChan.getLsttrachTen();
            String trachValueCodeSystem = emrParameters.get("emr_dm_yhct_trach");
            var vcTrachObs = HSBAFactory.eINSTANCE.createHsbaVongChanTrachObservation().init();
            String childTrachObsTmpId = properties.getProperty("VONGCHAN_TRACH_TEMPLATEID", "VONGCHAN_TRACH_TEMPLATEID");
            addYhctChildObservationInObservationElm(vongChanSection, strSplit, emrParameters, properties, lstTrach, lstTrachTen, trachValueCodeSystem, vcTrachObs, childTrachObsTmpId);
            
            //Chất lưỡi
            String lstChatLuoi = "";//emrYhctBenhanVongChan.getLstchatluoi();
            String lstChatLuoiTen = "";//emrYhctBenhanVongChan.getLstchatluoiTen();
            String chatLuoiValueCodeSystem = emrParameters.get("emr_dm_yhct_chat_luoi");
            var vcChatLuoiObs = HSBAFactory.eINSTANCE.createHsbaVongChanChatLuoiObservation().init();
            String childChatLuoiObsTmpId = properties.getProperty("VONGCHAN_CHATLUOI_TEMPLATEID", "VONGCHAN_CHATLUOI_TEMPLATEID");
            addYhctChildObservationInObservationElm(vongChanSection, strSplit, emrParameters, properties, lstChatLuoi, lstChatLuoiTen, chatLuoiValueCodeSystem, vcChatLuoiObs, childChatLuoiObsTmpId);
            
            //Sắc lưỡi
            String lstSacLuoi = "";//emrYhctBenhanVongChan.getLstsacluoi();
            String lstSacLuoiTen = "";//emrYhctBenhanVongChan.getLstsacluoiTen();
            String sacLuoiValueCodeSystem = emrParameters.get("emr_dm_yhct_sac_luoi");
            var vcSacLuoiObs = HSBAFactory.eINSTANCE.createHsbaVongChanSacLuoiObservation().init();
            String childSacLuoiObsTmpId = properties.getProperty("VONGCHAN_SACLUOI_TEMPLATEID", "VONGCHAN_SACLUOI_TEMPLATEID");
            addYhctChildObservationInObservationElm(vongChanSection, strSplit, emrParameters, properties, lstSacLuoi, lstSacLuoiTen, sacLuoiValueCodeSystem, vcSacLuoiObs, childSacLuoiObsTmpId);
            
            //Rêu lưỡi
            String lstReuLuoi = "";//emrYhctBenhanVongChan.getLstreuluoi();
            String lstReuLuoiTen = "";//emrYhctBenhanVongChan.getLstreuluoiTen();
            String reuLuoiValueCodeSystem = emrParameters.get("emr_dm_yhct_reu_luoi");
            var vcReuLuoiObs = HSBAFactory.eINSTANCE.createHsbaVongChanReuLuoiObservation().init();
            String childReuLuoiObsTmpId = properties.getProperty("VONGCHAN_REULUOI_TEMPLATEID", "VONGCHAN_REULUOI_TEMPLATEID");
            addYhctChildObservationInObservationElm(vongChanSection, strSplit, emrParameters, properties, lstReuLuoi, lstReuLuoiTen, reuLuoiValueCodeSystem, vcReuLuoiObs, childReuLuoiObsTmpId);       
            
            //Mô tả vọng chẩn
            String moTaVongChan = emrYhctBenhanVongChan.motavongchan;
            if(!StringUtils.isEmpty(moTaVongChan)){
                var moTaVongChanAct = HSBAFactory.eINSTANCE.createHsbaMoTaVongChanAct().init();
                ED moTaVongChanText = DatatypesFactory.eINSTANCE.createED(moTaVongChan);
                moTaVongChanAct.setText(moTaVongChanText);
                vongChanSection.addAct(moTaVongChanAct);
                vongChanSectionText.append(moTaVongChan);
            }
        }
        
        CDAExportUtil.setSectionData(vongChanSection, vongChanSectionText.toString());
    }
    
    public static void addLstTenForObservation(Observation obs, String lstTen){
        if(!StringUtils.isEmpty(lstTen)){        
            var er = CDAFactory.eINSTANCE.createEntryRelationship();
            er.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            obs.getEntryRelationships().add(er);
            
            var lstTenObs = HSBAFactory.eINSTANCE.createHsbaYhctLstTenObservation().init();
            ED lstTenED = DatatypesFactory.eINSTANCE.createED(lstTen);
            lstTenObs.setText(lstTenED);
            er.setObservation(lstTenObs);
        }       
    }
    
    public static void addYhctChildObservationInObservationElm(Section section, String strSplit, Map<String, String> emrParameters, 
            Properties properties, String lstCodeCda, String lstTen, String valueCodeSystem, Observation parentObs, String childObsTemplateId){
        if(lstCodeCda != null){     
            II obsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            parentObs.getIds().add(obsId);
            //add lstTen
            addLstTenForObservation(parentObs, lstTen);
            section.addObservation(parentObs);              
            if(!StringUtils.isEmpty(strSplit)){
                String[] arrCodeCdas = lstCodeCda.split(strSplit);              
                for (String CodeCdaItem : arrCodeCdas) {                                
                    var childObsER = CDAFactory.eINSTANCE.createEntryRelationship();
                    childObsER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                    parentObs.getEntryRelationships().add(childObsER);
                    
                    Observation childObs = CDAFactory.eINSTANCE.createObservation();
                    II childTemplateId = DatatypesFactory.eINSTANCE.createII(childObsTemplateId);
                    childObs.getTemplateIds().add(childTemplateId);
                    childObs.setClassCode(ActClassObservation.OBS);
                    childObs.setMoodCode(x_ActMoodDocumentObservation.EVN);
                    String childCode = properties.getProperty("SNOMEDCT_FINDING_CODE", "SNOMEDCT_FINDING_CODE");
                    String childCodeSystemName = properties.getProperty("SNOMEDCT_CODESYSTEMNAME", "SNOMEDCT_CODESYSTEMNAME");
                    String childCodeSystem = properties.getProperty("SNOMEDCT_CODESYSTEM", "SNOMEDCT_CODESYSTEM"); 
                    String childDisplayName = properties.getProperty("SNOMEDCT_FINDING_DISPLAYNAME", "SNOMEDCT_FINDING_DISPLAYNAME"); 
                    CD childObsCode = DatatypesFactory.eINSTANCE.createCD(childCode, childCodeSystem, childCodeSystemName, childDisplayName);                                       
                    childObs.setCode(childObsCode);
                    II childObsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                    childObs.getIds().add(childObsId);
                    childObsER.setObservation(childObs);
                    
                    CDAExportUtil.addCdObservationValueTag(childObs, CodeCdaItem, valueCodeSystem, "");  
                }
            }               
        }
    }
    
    public static void addVawnChanSection(HsbaBenhAnYHCTSection benhAnYhctSection, EmrYhctBenhAn emrYhctBenhAn, Map<String, String> emrParameters, Properties properties, String strSplit){
        var vanChanSection = HSBAFactory.eINSTANCE.createHsbaVanChanSection().init();
        benhAnYhctSection.addSection(vanChanSection);
        String vanChanTitle = properties.getProperty("YHCT_VANCHAN_TITLE", "YHCT_VANCHAN_TITLE");
        CDAExportUtil.addSectionTitle(vanChanSection, vanChanTitle);
        StringBuilder vanChanSectionText = new StringBuilder();
        
        var emrYhctBenhanVawnChan = emrYhctBenhAn.emrYhctBenhanVawnChan;
        if(emrYhctBenhanVawnChan != null){
            //Tiếng nói
            String lstTiengNoi = "";//emrYhctBenhanVawnChan.getLsttiengnoi();
            String lstTiengNoiTen = "";//emrYhctBenhanVawnChan.getLsttiengnoiTen();
            String tiengNoiValueCodeSystem = emrParameters.get("emr_dm_yhct_tieng_noi");
            var vcTiengNoiObs = HSBAFactory.eINSTANCE.createHsbaVanChanTiengNoiObservation().init(); 
            String childTiengNoiObsTmpId = properties.getProperty("VAWNCHAN_TIENGNOI_TEMPLATEID", "VAWNCHAN_TIENGNOI_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChanSection, strSplit, emrParameters, properties, lstTiengNoi, lstTiengNoiTen, tiengNoiValueCodeSystem, vcTiengNoiObs, childTiengNoiObsTmpId);
            
            //Hơi thở
            String lstHoiTho = "";//emrYhctBenhanVawnChan.getLsthoitho();
            String lstHoiThoTen = "";//emrYhctBenhanVawnChan.getLsthoithoTen();
            String hoithoValueCodeSystem = emrParameters.get("emr_dm_yhct_hoi_tho");
            var vcHoiThoObs = HSBAFactory.eINSTANCE.createHsbaVanChanHoiThoObservation().init();   
            String childHoiThoObsTmpId = properties.getProperty("VAWNCHAN_HOITHO_TEMPLATEID", "VAWNCHAN_HOITHO_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChanSection, strSplit, emrParameters, properties, lstHoiTho, lstHoiThoTen, hoithoValueCodeSystem, vcHoiThoObs, childHoiThoObsTmpId);
            
            //Tiếng ho
            String lstTiengHo = "";// emrYhctBenhanVawnChan.getLsttiengho();
            String lstTiengHoTen = "";//emrYhctBenhanVawnChan.getLsttienghoTen();
            String tiengHoValueCodeSystem = emrParameters.get("emr_dm_yhct_tieng_ho");
            var vcTiengHoObs = HSBAFactory.eINSTANCE.createHsbaVanChanTiengHoObservation().init();
            String childTiengHoObsTmpId = properties.getProperty("VAWNCHAN_TIENGHO_TEMPLATEID", "VAWNCHAN_TIENGHO_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChanSection, strSplit, emrParameters, properties, lstTiengHo, lstTiengHoTen, tiengHoValueCodeSystem, vcTiengHoObs, childTiengHoObsTmpId);
            
            //Âm thanh Ợ            
            var amThanhO = emrYhctBenhanVawnChan.amthanho;     
            if(amThanhO != null){
                var amThanhOObs = HSBAFactory.eINSTANCE.createHsbaVanChanAmThanhOObservation().init();
                amThanhOObs.setText(DatatypesFactory.eINSTANCE.createED(amThanhO));
                vanChanSection.addObservation(amThanhOObs);
            }
            
            //Âm thanh Nấc      
            var amThanhNac = emrYhctBenhanVawnChan.amthanhnac;     
            if(amThanhNac != null){
                var amThanhNacObs = HSBAFactory.eINSTANCE.createHsbaVanChanAmThanhNacObservation().init();
                amThanhNacObs.setText(DatatypesFactory.eINSTANCE.createED(amThanhNac));
                vanChanSection.addObservation(amThanhNacObs);
            }
            
            //Mùi cơ thể
            String lstMuiCoThe = "";//emrYhctBenhanVawnChan.getLstmuicothe();
            String lstMuiCoTheTen = "";//emrYhctBenhanVawnChan.getLstmuicotheTen();
            String muiCoTheValueCodeSystem = emrParameters.get("emr_dm_yhct_mui_co_the");
            var vcMuiCoTheObs = HSBAFactory.eINSTANCE.createHsbaVanChanMuiCoTheObservation().init();
            String childMuiCoTheObsTmpId = properties.getProperty("VAWNCHAN_MUICOTHE_TEMPLATEID", "VAWNCHAN_MUICOTHE_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChanSection, strSplit, emrParameters, properties, lstMuiCoThe, lstMuiCoTheTen, muiCoTheValueCodeSystem, vcMuiCoTheObs, childMuiCoTheObsTmpId);
            
            //Chất thải bệnh lý
            String lstChatThaiBenhLy = "";//emrYhctBenhanVawnChan.getLstchatthaibenhly();
            String lstChatThaiBenhLyTen = "";//emrYhctBenhanVawnChan.getLstchatthaibenhlyTen();
            String chatThaiBenhLyValueCodeSystem = emrParameters.get("emr_dm_yhct_chat_luoi");
            var vcChatThaiBenhLyObs = HSBAFactory.eINSTANCE.createHsbaVanChanChatThaiBenhLiObservation().init();
            String childChatThaiBenhLyObsTmpId = properties.getProperty("VAWNCHAN_CHATTHAIBENHLY_TEMPLATEID", "VAWNCHAN_CHATTHAIBENHLY_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChanSection, strSplit, emrParameters, properties, lstChatThaiBenhLy, lstChatThaiBenhLyTen, chatThaiBenhLyValueCodeSystem, vcChatThaiBenhLyObs, childChatThaiBenhLyObsTmpId);
            
            //Mô tả văn chẩn
            String moTaVanChan = emrYhctBenhanVawnChan.motavawnchan;
            if(!StringUtils.isEmpty(moTaVanChan)){
                var moTaVawnChanAct = HSBAFactory.eINSTANCE.createHsbaMoTaVanChanAct().init();
                ED moTaVawnChanText = DatatypesFactory.eINSTANCE.createED(moTaVanChan);
                moTaVawnChanAct.setText(moTaVawnChanText);
                vanChanSection.addAct(moTaVawnChanAct);
                vanChanSectionText.append(moTaVanChan);
            }           
        }
        
        CDAExportUtil.setSectionData(vanChanSection, vanChanSectionText.toString());       
    }
    
    
    public static void addVaanChanSection(HsbaBenhAnYHCTSection benhAnYhctSection, EmrYhctBenhAn emrYhctBenhAn, Map<String, String> emrParameters, Properties properties, String strSplit){
        var vanChan1Section = HSBAFactory.eINSTANCE.createHsbaVanChan1Section().init();
        benhAnYhctSection.addSection(vanChan1Section);
        String vanChan1Title = properties.getProperty("YHCT_VANCHAN1_TITLE", "YHCT_VANCHAN1_TITLE");
        CDAExportUtil.addSectionTitle(vanChan1Section, vanChan1Title);
        StringBuilder vanChan1SectionText = new StringBuilder();
        
        var emrYhctBenhanVaanChan = emrYhctBenhAn.emrYhctBenhanVaanChan;
        if(emrYhctBenhanVaanChan != null){
            //Hàn nhiệt - Biểu hiện
            String lstHanNhietBieuHien = "";//emrYhctBenhanVaanChan.getLsthannhietbieuhien();
            String lstHanNhietBieuHienTen = "";//emrYhctBenhanVaanChan.getLsthannhietbieuhienTen();
            String hanNhietBieuHienValueCodeSystem = emrParameters.get("emr_dm_yhct_han_nhiet_bieu_hien");
            var vcHanNhietBieuHienObs = HSBAFactory.eINSTANCE.createHsbaVanChan1HanNhietBieuHienObservation().init();   
            String childHanNhietBieuHienObsTmpId = properties.getProperty("VAANCHAN_HANNHIET_BIEUHIEN_TEMPLATEID", "VAANCHAN_HANNHIET_BIEUHIEN_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstHanNhietBieuHien, lstHanNhietBieuHienTen, hanNhietBieuHienValueCodeSystem, vcHanNhietBieuHienObs, childHanNhietBieuHienObsTmpId);
            
            //Hàn nhiệt
            String lstHanNhiet = "";//emrYhctBenhanVaanChan.getLsthannhiet();
            String lstHanNHietTen = "";//emrYhctBenhanVaanChan.getLsthannhietTen();
            String hanNhietValueCodeSystem = emrParameters.get("emr_dm_yhct_han_nhiet");
            var vcHanNhietObs = HSBAFactory.eINSTANCE.createHsbaVanChan1HanNhietObservation().init();   
            String childHanNhietObsTmpId = properties.getProperty("VAANCHAN_HANNHIET_TEMPLATEID", "VAANCHAN_HANNHIET_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstHanNhiet, lstHanNHietTen, hanNhietValueCodeSystem, vcHanNhietObs, childHanNhietObsTmpId);
            
            //Mồ hôi
            String lstMoHoi = "";//emrYhctBenhanVaanChan.getLstmohoi();
            String lstMoHoiTen = "";//emrYhctBenhanVaanChan.getLstmohoiTen();
            String moHoiValueCodeSystem = emrParameters.get("emr_dm_yhct_mo_hoi");
            var vcMoHoiObs = HSBAFactory.eINSTANCE.createHsbaVanChan1MoHoiObservation().init();    
            String childMoHoiObsTmpId = properties.getProperty("VAANCHAN_MOHOI_TEMPLATEID", "VAANCHAN_MOHOI_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstMoHoi, lstMoHoiTen, moHoiValueCodeSystem, vcMoHoiObs, childMoHoiObsTmpId);
            
            //Đầu mặt cổ
            String lstDauMatCo = "";//emrYhctBenhanVaanChan.getLstdaumatco();
            String lstDauMatCoTen = "";//emrYhctBenhanVaanChan.getLstdaumatcoTen();
            String dauMatCoValueCodeSystem = emrParameters.get("emr_dm_yhct_dau_mat_co");
            var vcDauMatCoObs = HSBAFactory.eINSTANCE.createHsbaVanChan1DauMatCoObservation().init();
            String childDauMatCoObsTmpId = properties.getProperty("VAANCHAN_DAUMATCO_TEMPLATEID", "VAANCHAN_DAUMATCO_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstDauMatCo, lstDauMatCoTen, dauMatCoValueCodeSystem, vcDauMatCoObs, childDauMatCoObsTmpId);
            
            //Lưng
            String lstLung = "";//emrYhctBenhanVaanChan.getLstlung();
            String lstLungTen = "";//emrYhctBenhanVaanChan.getLstlungTen();
            String lungValueCodeSystem = emrParameters.get("emr_dm_yhct_lung");
            var vcLungObs = HSBAFactory.eINSTANCE.createHsbaVanChan1LungObservation().init();
            String childLungObsTmpId = properties.getProperty("VAANCHAN_LUNG_TEMPLATEID", "VAANCHAN_LUNG_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstLung, lstLungTen, lungValueCodeSystem, vcLungObs, childLungObsTmpId);
            
            //Bụng ngực
            String lstBungNguc = "";//emrYhctBenhanVaanChan.getLstbungnguc();
            String lstBungNgucTen = "";//emrYhctBenhanVaanChan.getLstbungngucTen();
            String bungNgucValueCodeSystem = emrParameters.get("emr_dm_yhct_bung_nguc");
            var vcBungNgucObs = HSBAFactory.eINSTANCE.createHsbaVanChan1BungNgucObservation().init();
            String childBungNgucObsTmpId = properties.getProperty("VAANCHAN_BUNGNGUC_TEMPLATEID", "VAANCHAN_BUNGNGUC_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstBungNguc, lstBungNgucTen, bungNgucValueCodeSystem, vcBungNgucObs, childBungNgucObsTmpId);
            
            //Chân tay
            String chanTay = emrYhctBenhanVaanChan.getLstchantay();            
            if(chanTay != null){
                var chanTayObs = HSBAFactory.eINSTANCE.createHsbaVanChan1ChanTayObservation().init();
                chanTayObs.setText(DatatypesFactory.eINSTANCE.createED(chanTay));
                vanChan1Section.addObservation(chanTayObs);     
            }
            
            //Ăn
            String lstAn = "";//emrYhctBenhanVaanChan.getLstan();
            String lstAnTen = "";//emrYhctBenhanVaanChan.getLstanTen();
            String anValueCodeSystem = emrParameters.get("emr_dm_yhct_an");
            var vcAnObs = HSBAFactory.eINSTANCE.createHsbaVanChan1AnObservation().init();
            String childAnObsTmpId = properties.getProperty("VAANCHAN_AN_TEMPLATEID", "VAANCHAN_AN_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstAn, lstAnTen, anValueCodeSystem, vcAnObs, childAnObsTmpId);
            
            //Uống
            String lstUong = "";//emrYhctBenhanVaanChan.getLstuong();
            String lstUongTen = "";//emrYhctBenhanVaanChan.getLstuongTen();
            String uongValueCodeSystem = emrParameters.get("emr_dm_yhct_uong");
            var vcUongObs = HSBAFactory.eINSTANCE.createHsbaVanChan1UongObservation().init();
            String childUongObsTmpId = properties.getProperty("VAANCHAN_UONG_TEMPLATEID", "VAANCHAN_UONG_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstUong, lstUongTen, uongValueCodeSystem, vcUongObs, childUongObsTmpId);
            
            //Đại tiện
            String lstDaiTien = "";//emrYhctBenhanVaanChan.getLstdaitien();
            String lstDaiTienTen = "";//emrYhctBenhanVaanChan.getLstdaitienTen();
            String daiTienValueCodeSystem = emrParameters.get("emr_dm_yhct_dai_tien");
            var vcDaiTienObs = HSBAFactory.eINSTANCE.createHsbaVanChan1DaiTienObservation().init();
            String childDaiTienObsTmpId = properties.getProperty("VAANCHAN_DAITIEN_TEMPLATEID", "VAANCHAN_DAITIEN_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstDaiTien, lstDaiTienTen, daiTienValueCodeSystem, vcDaiTienObs, childDaiTienObsTmpId);
            
            //Tiểu tiện
            String lstTieuTien = "";//emrYhctBenhanVaanChan.getLsttieutien();
            String lstTieuTienTen = "";//emrYhctBenhanVaanChan.getLsttieutienTen();
            String tieuTienValueCodeSystem = emrParameters.get("emr_dm_yhct_tieu_tien");
            var vcTieuTienObs = HSBAFactory.eINSTANCE.createHsbaVanChan1TieuTienObservation().init();
            String childTieuTienObsTmpId = properties.getProperty("VAANCHAN_DAITIEN_TEMPLATEID", "VAANCHAN_DAITIEN_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstTieuTien, lstTieuTienTen, tieuTienValueCodeSystem, vcTieuTienObs, childTieuTienObsTmpId);
            
            //Ngủ
            String lstNgu = "";//emrYhctBenhanVaanChan.getLstngu();
            String lstNguTen = "";//emrYhctBenhanVaanChan.getLstnguTen();
            String nguValueCodeSystem = emrParameters.get("emr_dm_yhct_ngu");
            var vcNguObs = HSBAFactory.eINSTANCE.createHsbaVanChan1NguObservation().init();
            String childNguObsTmpId = properties.getProperty("VAANCHAN_NGU_TEMPLATEID", "VAANCHAN_NGU_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstNgu, lstNguTen, nguValueCodeSystem, vcNguObs, childNguObsTmpId);
            
            //Kinh nguyệt
            String lstKinhNguyet = "";//emrYhctBenhanVaanChan.getLstkinhnguyet();
            String lstKinhNguyetTen = "";//emrYhctBenhanVaanChan.getLstkinhnguyetTen();
            String kinhNguyetValueCodeSystem = emrParameters.get("emr_dm_yhct_kinh_nguyet");
            var vcKinhNguyetObs = HSBAFactory.eINSTANCE.createHsbaVanChan1KinhNguyetObservation().init();
            String childKinhNguyetObsTmpId = properties.getProperty("VAANCHAN_KINHNGUYET_TEMPLATEID", "VAANCHAN_KINHNGUYET_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstKinhNguyet, lstKinhNguyetTen, kinhNguyetValueCodeSystem, vcKinhNguyetObs, childKinhNguyetObsTmpId);
            
            //Thống kinh
            String lstThongKinh = "";//emrYhctBenhanVaanChan.getLstthongkinh();
            String lstThongKinhTen = "";//emrYhctBenhanVaanChan.getLstthongkinhTen();
            String thongKinhValueCodeSystem = emrParameters.get("emr_dm_yhct_thong_kinh");
            var vcThongKinhObs = HSBAFactory.eINSTANCE.createHsbaVanChan1ThongKinhObservation().init();
            String childThongKinhObsTmpId = properties.getProperty("VAANCHAN_THONGKINH_TEMPLATEID", "VAANCHAN_THONGKINH_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstThongKinh, lstThongKinhTen, thongKinhValueCodeSystem, vcThongKinhObs, childThongKinhObsTmpId);
            
            //Đới hạ
            String lstDoiHa = "";//emrYhctBenhanVaanChan.getLstdoiha();
            String lstDoiHaTen = "";//emrYhctBenhanVaanChan.getLstdoihaTen();
            String doiHaValueCodeSystem = emrParameters.get("emr_dm_yhct_doi_ha");
            var vcDoiHaObs = HSBAFactory.eINSTANCE.createHsbaVanChan1DoiHaObservation().init();
            String childDoiHaObsTmpId = properties.getProperty("VAANCHAN_DOIHA_TEMPLATEID", "VAANCHAN_DOIHA_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstDoiHa, lstDoiHaTen, doiHaValueCodeSystem, vcDoiHaObs, childDoiHaObsTmpId);
            
            //Sinh dục Nam
            String lstSinhDuc = "";//emrYhctBenhanVaanChan.getLstsinhduc();
            String lstSinhDucTen = "";//emrYhctBenhanVaanChan.getLstsinhducTen();
            String sinhDucValueCodeSystem = emrParameters.get("emr_dm_yhct_sinh_duc");
            var vcSinhDucObs = HSBAFactory.eINSTANCE.createHsbaVanChan1SinhDucObservation().init();
            String childSinhDucObsTmpId = properties.getProperty("VAANCHAN_SINHDUC_TEMPLATEID", "VAANCHAN_SINHDUC_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstSinhDuc, lstSinhDucTen, sinhDucValueCodeSystem, vcSinhDucObs, childSinhDucObsTmpId);
            
            //Sinh dục Nữ
            String lstSinhDucNu = "";//emrYhctBenhanVaanChan.getLstsinhducnu();
            String lstSinhDucNuTen = "";// emrYhctBenhanVaanChan.getLstsinhducnuTen();
            String sinhDucNuValueCodeSystem = emrParameters.get("emr_dm_yhct_sinh_duc");
            var vcSinhDucNuObs = HSBAFactory.eINSTANCE.createHsbaVanChan1SinhDucNuObservation().init();
            String childSinhDucNuObsTmpId = properties.getProperty("VAANCHAN_SINHDUCNU_TEMPLATEID", "VAANCHAN_SINHDUCNU_TEMPLATEID");
            addYhctChildObservationInObservationElm(vanChan1Section, strSplit, emrParameters, properties, lstSinhDucNu, lstSinhDucNuTen, sinhDucNuValueCodeSystem, vcSinhDucNuObs, childSinhDucNuObsTmpId);
            
            //Mô tả vấn chẩn
            String moTaVaanChan = emrYhctBenhanVaanChan.motavaanchan;
            if(!StringUtils.isEmpty(moTaVaanChan)){
                var moTaVaanChanAct = HSBAFactory.eINSTANCE.createHsbaMoTaVanChan1Act().init();
                ED moTaVaanChanText = DatatypesFactory.eINSTANCE.createED(moTaVaanChan);
                moTaVaanChanAct.setText(moTaVaanChanText);
                vanChan1Section.addAct(moTaVaanChanAct);
                vanChan1SectionText.append(moTaVaanChan);
            }           
        }          
    }
    
    public static void addYhctChildObservationInOrganizerElm(Observation parentObs, String strSplit, Map<String, String> emrParameters,
                        Properties properties, String lstCodeCda, String lstTen, String valueCodeSystem, Organizer organizer, String childObsTemplateId){
        if(lstCodeCda != null){     
            II organizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            organizer.getIds().add(organizerId);
            
            //add lstTen
            addLstTenForOrganizer(organizer, lstTen);
            
            var organizerER = CDAFactory.eINSTANCE.createEntryRelationship();
            organizerER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            parentObs.getEntryRelationships().add(organizerER);         
            organizerER.setOrganizer(organizer);

            if(!StringUtils.isEmpty(strSplit)){
                String[] arrCodeCdas = lstCodeCda.split(strSplit);              
                for (String CodeCdaItem : arrCodeCdas) {                                
                    var component = CDAFactory.eINSTANCE.createComponent4();
                    component.setTypeCode(ActRelationshipHasComponent.COMP);
                    organizer.getComponents().add(component);
                    
                    Observation childObs = CDAFactory.eINSTANCE.createObservation();
                    II childTemplateId = DatatypesFactory.eINSTANCE.createII(childObsTemplateId);
                    childObs.getTemplateIds().add(childTemplateId);
                    childObs.setClassCode(ActClassObservation.OBS);
                    childObs.setMoodCode(x_ActMoodDocumentObservation.EVN);
                    String childCode = properties.getProperty("SNOMEDCT_FINDING_CODE", "SNOMEDCT_FINDING_CODE");
                    String childCodeSystemName = properties.getProperty("SNOMEDCT_CODESYSTEMNAME", "SNOMEDCT_CODESYSTEMNAME");
                    String childCodeSystem = properties.getProperty("SNOMEDCT_CODESYSTEM", "SNOMEDCT_CODESYSTEM"); 
                    String childDisplayName = properties.getProperty("SNOMEDCT_FINDING_DISPLAYNAME", "SNOMEDCT_FINDING_DISPLAYNAME"); 
                    CD childObsCode = DatatypesFactory.eINSTANCE.createCD(childCode, childCodeSystem, childCodeSystemName, childDisplayName);                                       
                    childObs.setCode(childObsCode);
                    II childObsId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                    childObs.getIds().add(childObsId);
                    component.setObservation(childObs);
                    
                    CDAExportUtil.addCdObservationValueTag(childObs, CodeCdaItem, valueCodeSystem, "");  
                }
            }               
        }
        
    }
    public static void addThietChanSection(HsbaBenhAnYHCTSection benhAnYhctSection, EmrYhctBenhAn emrYhctBenhAn, Map<String, String> emrParameters, Properties properties, String strSplit){
        var thietChanSection = HSBAFactory.eINSTANCE.createHsbaThietChanSection().init();
        benhAnYhctSection.addSection(thietChanSection);
        String thietChanTitle = properties.getProperty("YHCT_THIETCHAN_TITLE", "YHCT_THIETCHAN_TITLE");
        CDAExportUtil.addSectionTitle(thietChanSection, thietChanTitle);
        StringBuilder thietChanSectionText = new StringBuilder();
        
        var emrYhctBenhanThietChan = emrYhctBenhAn.emrYhctBenhanThietChan;
        if(emrYhctBenhanThietChan != null){
            //Xúc chẩn
            String strXucChan = emrYhctBenhanThietChan.motaxucchan;
            String lstXucChan = "";//emrYhctBenhanThietChan.getLstxucchan();
            String lstXucChanTen = "";//emrYhctBenhanThietChan.getLstxucchanTen();
            String xucChanValueCodeSystem = emrParameters.get("emr_dm_yhct_xuc_chan");
            var tcXucChanObs = HSBAFactory.eINSTANCE.createHsbaThietChanXucChanObservation().init();    
            //--- add text mô tả xúc chẩn
            if(!StringUtils.isEmpty(strXucChan)){
                tcXucChanObs.setText(DatatypesFactory.eINSTANCE.createED(strXucChan));
            }
            String childThietChanObsTmpId = properties.getProperty("THIETCHAN_XUCCHAN_TEMPLATEID", "THIETCHAN_XUCCHAN_TEMPLATEID");
            addYhctChildObservationInObservationElm(thietChanSection, strSplit, emrParameters, properties, lstXucChan, lstXucChanTen, xucChanValueCodeSystem, tcXucChanObs, childThietChanObsTmpId);
            
            //Cơ nhục
            String lstCoNhuc = "";//emrYhctBenhanThietChan.getLstconhuc();
            String lstCoNhucTen = "";//emrYhctBenhanThietChan.getLstconhucTen();
            String coNhucValueCodeSystem = emrParameters.get("emr_dm_yhct_co_nhuc");
            var tcCoNhucObs = HSBAFactory.eINSTANCE.createHsbaThietChanCoNhucObservation().init();   
            String childCoNhucObsTmpId = properties.getProperty("THIETCHAN_CONHUC_TEMPLATEID", "THIETCHAN_CONHUC_TEMPLATEID");
            addYhctChildObservationInObservationElm(thietChanSection, strSplit, emrParameters, properties, lstCoNhuc, lstCoNhucTen, coNhucValueCodeSystem, tcCoNhucObs, childCoNhucObsTmpId);
            
            //Xúc Chẩn - Mồ hôi
            String lstXucChanMoHoi = "";//emrYhctBenhanThietChan.getLstxucchanmohoi();
            String lstXucChanMoHoiTen = "";//emrYhctBenhanThietChan.getLstxucchanmohoiTen();
            String xucChanMoHoiValueCodeSystem = emrParameters.get("emr_dm_yhct_xuc_chan_mo_hoi");
            var tcxcMoHoiObs = HSBAFactory.eINSTANCE.createHsbaThietChanXucChanMoHoiObservation().init();  
            String childMoHoiObsTmpId = properties.getProperty("THIETCHAN_XUCCHAN_MOHOI_TEMPLATEID", "THIETCHAN_XUCCHAN_MOHOI_TEMPLATEID");
            addYhctChildObservationInObservationElm(thietChanSection, strSplit, emrParameters, properties, lstXucChanMoHoi, lstXucChanMoHoiTen, xucChanMoHoiValueCodeSystem, tcxcMoHoiObs, childMoHoiObsTmpId);
            
            //Phúc chẩn
            String lstPhucChan = "";//emrYhctBenhanThietChan.getLstphucchan();
            String lstPhucChanTen = "";//emrYhctBenhanThietChan.getLstphucchanTen();
            String phucChanValueCodeSystem = emrParameters.get("emr_dm_yhct_phuc_chan");
            var tcPhucChanObs = HSBAFactory.eINSTANCE.createHsbaThietChanPhucChanObservation().init(); 
            String childPhucChanObsTmpId = properties.getProperty("THIETCHAN_PHUCCHAN_TEMPLATEID", "THIETCHAN_PHUCCHAN_TEMPLATEID");
            addYhctChildObservationInObservationElm(thietChanSection, strSplit, emrParameters, properties, lstPhucChan, lstPhucChanTen, phucChanValueCodeSystem, tcPhucChanObs, childPhucChanObsTmpId);
            
            //Mạch chẩn         
            var tcMachChanObs = HSBAFactory.eINSTANCE.createHsbaThietChanMachChanObservation().init(); 
            thietChanSection.addObservation(tcMachChanObs);
            String machChanValueCodeSystem = emrParameters.get("emr_dm_yhct_mach_chan");
            String childMachChanObsTmpId = properties.getProperty("THIETCHAN_MACHCHAN_CHILDOBS_TEMPLATEID", "THIETCHAN_MACHCHAN_CHILDOBS_TEMPLATEID");
            
            //-- Mô tả Mạch chẩn tay phải
            String moTaMachChanTayPhai = emrYhctBenhanThietChan.motamachchanTayphai;               
            if(!StringUtils.isEmpty(moTaMachChanTayPhai)){
                var machChanTayPhaiER = CDAFactory.eINSTANCE.createEntryRelationship();
                machChanTayPhaiER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                tcMachChanObs.getEntryRelationships().add(machChanTayPhaiER);
                
                var machChanTayPhaiObs = HSBAFactory.eINSTANCE.createHsbaYhctMoTaMachChanTayPhaiObservation().init();
                ED machChanTayPhaiED = DatatypesFactory.eINSTANCE.createED(moTaMachChanTayPhai);
                machChanTayPhaiObs.setText(machChanTayPhaiED);
                machChanTayPhaiER.setObservation(machChanTayPhaiObs);               
            }
            //-- Mô tả Mạch chẩn tay trái
            String moTaMachChanTayTrai = emrYhctBenhanThietChan.motamachchanTaytrai;
            if(!StringUtils.isEmpty(moTaMachChanTayTrai)){
                var machChanTayTraiER = CDAFactory.eINSTANCE.createEntryRelationship();
                machChanTayTraiER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                tcMachChanObs.getEntryRelationships().add(machChanTayTraiER);
                
                var machChanTayTraiObs = HSBAFactory.eINSTANCE.createHsbaYhctMoTaMachChanTayTraiObservation().init();
                ED machChanTayTraiED = DatatypesFactory.eINSTANCE.createED(moTaMachChanTayTrai);
                machChanTayTraiObs.setText(machChanTayTraiED);
                machChanTayTraiER.setObservation(machChanTayTraiObs);
            }
            
            //--- Tổng khán         
            String lstTongKhan = "";//emrYhctBenhanThietChan.getLstmachchantongquat();   
            String lstMachChanTongQuatTen = "";//emrYhctBenhanThietChan.getLstmachchantongquatTen();
            var tongKhanOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctTongKhanOrganizer().init();   
            addYhctChildObservationInOrganizerElm(tcMachChanObs, strSplit, emrParameters, properties, lstTongKhan, lstMachChanTongQuatTen, machChanValueCodeSystem, tongKhanOrganizer, childMachChanObsTmpId);
            
            //--- Thốn tay trái
            String lstThonTayTrai = "";//emrYhctBenhanThietChan.getLstmachchanThonTaytrai(); 
            String lstThonTayTraiTen = "";//emrYhctBenhanThietChan.getLstmachchanThonTaytraiTen();
            var thonTayTraiOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctThonTayTraiOrganizer().init();
            addYhctChildObservationInOrganizerElm(tcMachChanObs, strSplit, emrParameters, properties, lstThonTayTrai, lstThonTayTraiTen, machChanValueCodeSystem, thonTayTraiOrganizer, childMachChanObsTmpId);
                
            //--- Quan tay trái
            String lstQuanTayTrai = "";//emrYhctBenhanThietChan.getLstmachchanQuanTaytrai();
            String lstQuanTayTraiTen = "";//emrYhctBenhanThietChan.getLstmachchanQuanTaytraiTen();
            var quanTayTraiOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctQuanTayTraiOrganizer().init();
            addYhctChildObservationInOrganizerElm(tcMachChanObs, strSplit, emrParameters, properties, lstQuanTayTrai, lstQuanTayTraiTen, machChanValueCodeSystem, quanTayTraiOrganizer, childMachChanObsTmpId);
                
            //--- Xích tay trái
            String lstXichTayTrai = "";//emrYhctBenhanThietChan.getLstmachchanXichTaytrai(); 
            String lstXichTayTraiTen = "";//emrYhctBenhanThietChan.getLstmachchanXichTaytraiTen();
            var xichTayTraiOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctXichTayTraiOrganizer().init();
            addYhctChildObservationInOrganizerElm(tcMachChanObs, strSplit, emrParameters, properties, lstXichTayTrai, lstXichTayTraiTen, machChanValueCodeSystem, xichTayTraiOrganizer, childMachChanObsTmpId);
                
            //--- Thốn tay phải
            String lstThonTayPhai = "";//emrYhctBenhanThietChan.getLstmachchanThonTayphai(); 
            String lstThonTayPhaiTen = "";//emrYhctBenhanThietChan.getLstmachchanThonTayphaiTen();
            var thonTayPhaiOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctThonTayPhaiOrganizer().init();
            addYhctChildObservationInOrganizerElm(tcMachChanObs, strSplit, emrParameters, properties, lstThonTayPhai, lstThonTayPhaiTen, machChanValueCodeSystem, thonTayPhaiOrganizer, childMachChanObsTmpId);
                
            //--- Quan tay phải
            String lstQuanTayPhai = "";//emrYhctBenhanThietChan.getLstmachchanQuanTayphai(); 
            String lstQuanTayPhaiTen = "";//emrYhctBenhanThietChan.getLstmachchanQuanTayphaiTen();
            var quanTayPhaiOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctQuanTayPhaiOrganizer().init();
            addYhctChildObservationInOrganizerElm(tcMachChanObs, strSplit, emrParameters, properties, lstQuanTayPhai, lstQuanTayPhaiTen, machChanValueCodeSystem, quanTayPhaiOrganizer, childMachChanObsTmpId);
                
            //--- Xích tay phải
            String lstXichTayPhai = "";//emrYhctBenhanThietChan.getLstmachchanXichTayphai(); 
            String lstXichTayPhaiTen = "";//emrYhctBenhanThietChan.getLstmachchanXichTayphaiTen();
            var xichTayPhaiOrganizer = HSBAFactory.eINSTANCE.createHsbaYhctXichTayPhaiOrganizer().init();
            addYhctChildObservationInOrganizerElm(tcMachChanObs, strSplit, emrParameters, properties, lstXichTayPhai, lstXichTayPhaiTen, machChanValueCodeSystem, xichTayPhaiOrganizer, childMachChanObsTmpId);
                
    
            //Mô tả thiết chẩn
            String moTaThietChan = emrYhctBenhanThietChan.motathietchan;
            if(!StringUtils.isEmpty(moTaThietChan)){
                var moTaThietChanAct = HSBAFactory.eINSTANCE.createHsbaMoTaThietChanAct().init();
                ED moTaThietChanText = DatatypesFactory.eINSTANCE.createED(moTaThietChan);
                moTaThietChanAct.setText(moTaThietChanText);
                thietChanSection.addAct(moTaThietChanAct);
                thietChanSectionText.append(moTaThietChan);
            }
        }
        
        CDAExportUtil.setSectionData(thietChanSection, thietChanSectionText.toString());   
    }
    
    
    public static void addTomTatTuChanSection(HsbaBenhAnYHCTSection benhAnYhctSection, EmrYhctBenhAn emrYhctBenhAn, Map<String, String> emrParameters, Properties properties){
        var tomTatTuChanSection = HSBAFactory.eINSTANCE.createHsbaTomTatTuChanSection().init();
        benhAnYhctSection.addSection(tomTatTuChanSection);
        String tomTatTuChanTitle = properties.getProperty("YHCT_TOMTATTUCHAN_TITLE", "YHCT_TOMTATTUCHAN_TITLE");
        CDAExportUtil.addSectionTitle(tomTatTuChanSection, tomTatTuChanTitle);
        StringBuilder  tomTatTuChanText = new StringBuilder();
        
        String tomTatTuChan = emrYhctBenhAn.tomtattuchan;
        if(!StringUtils.isEmpty(tomTatTuChan)){
            tomTatTuChanText.append(tomTatTuChan);
        }
        
        CDAExportUtil.setSectionData(tomTatTuChanSection, tomTatTuChanText.toString());       
    }
    
    public static void addBienChungLuanTriSection(HsbaBenhAnYHCTSection benhAnYhctSection, EmrYhctBenhAn emrYhctBenhAn, Map<String, String> emrParameters, Properties properties){
        var bienChungLuanTriSection = HSBAFactory.eINSTANCE.createHsbaBienChungLuanTriSection().init();
        benhAnYhctSection.addSection(bienChungLuanTriSection);
        String bienChungLuanTriTitle = properties.getProperty("YHCT_BIENCHUNGLUANTRI_TITLE", "YHCT_BIENCHUNGLUANTRI_TITLE");
        CDAExportUtil.addSectionTitle(bienChungLuanTriSection, bienChungLuanTriTitle);
        StringBuilder  bienChungLuanTriText = new StringBuilder();
        
        String bienChungLuanTri = emrYhctBenhAn.luantri;
        if(!StringUtils.isEmpty(bienChungLuanTri)){
            bienChungLuanTriText.append(bienChungLuanTri);
        }
        
        CDAExportUtil.setSectionData(bienChungLuanTriSection, bienChungLuanTriText.toString());       
    }
    
    public static void addYhctDieuTriAct(Section section, Act act, String strActText){
        II actId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        act.getIds().add(actId);            
        section.addAct(act);
        
        ED actText = DatatypesFactory.eINSTANCE.createED(strActText);
        act.setText(actText);  
    }
    
    public static void addOrganizerAct(Organizer organizer, Act act, String strActCode){
        if(!StringUtils.isEmpty(strActCode)){
            var component = CDAFactory.eINSTANCE.createComponent4();
            component.setTypeCode(ActRelationshipHasComponent.COMP);
            organizer.getComponents().add(component);
            
            II actId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            act.getIds().add(actId);
            component.setAct(act);

            ThongTinCanLamSangUtil.addActCdCodeTag(act, strActCode, "");
        }       
    }
    
    public static void addDieuTriYhctSection(HsbaBenhAnYHCTSection benhAnYhctSection,  EmrYhctBenhAn emrYhctBenhAn, Map<String, String> emrParameters, Properties properties, String strSplit){
        var dieuTriYhctSection = HSBAFactory.eINSTANCE.createHsbaDieuTriYHCTSection().init();
        benhAnYhctSection.addSection(dieuTriYhctSection);
        String dieuTriYhctTitle = properties.getProperty("YHCT_DIEUTRI_TITLE", "YHCT_DIEUTRI_TITLE");
        CDAExportUtil.addSectionTitle(dieuTriYhctSection, dieuTriYhctTitle);
        StringBuilder  dieuTriYhctText = new StringBuilder();
        String sectionDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
        String splitDelimiter = properties.getProperty("SPLIT_YHCT_CHANDOAN_VALUE", "SPLIT_YHCT_CHANDOAN_VALUE");
        
                
        //Pháp điều trị     
        String phapDieuTri = emrYhctBenhAn.phapdieutri;
        if(!StringUtils.isEmpty(phapDieuTri)){   
            String phapDieuTriTitle = properties.getProperty("YHCT_DIEUTRI_PHAPDIEUTRI_TITLE", "YHCT_DIEUTRI_PHUONGTHUOC_TITLE");
            dieuTriYhctText.append(phapDieuTriTitle).append(sectionDelimiter).append(phapDieuTri).append(splitDelimiter);
            var phapDieuTriAct = HSBAFactory.eINSTANCE.createHsbaPhapDieuTriAct().init();
            addYhctDieuTriAct(dieuTriYhctSection, phapDieuTriAct, phapDieuTri);                     
        }
        
        //Phương thuốc
        String phuongThuoc = emrYhctBenhAn.phuongthuoc;
        if(!StringUtils.isEmpty(phuongThuoc)){   
            String phuongThuocTitle = properties.getProperty("YHCT_DIEUTRI_PHUONGTHUOC_TITLE", "YHCT_DIEUTRI_PHUONGTHUOC_TITLE");
            dieuTriYhctText.append(phuongThuocTitle).append(sectionDelimiter).append(phuongThuoc).append(splitDelimiter);
            var phuongThuocAct = HSBAFactory.eINSTANCE.createHsbaPhuongThuocAct().init();
            addYhctDieuTriAct(dieuTriYhctSection, phuongThuocAct, phuongThuoc);                     
        }
        
        //Phương huyệt
        String phuongHuyet = emrYhctBenhAn.phuonghuyet;
        if(!StringUtils.isEmpty(phuongHuyet)){   
            String phuongHuyetTitle = properties.getProperty("YHCT_DIEUTRI_PHUONGHUYET_TITLE", "YHCT_DIEUTRI_PHUONGHUYET_TITLE");
            dieuTriYhctText.append(phuongHuyetTitle).append(sectionDelimiter).append(phuongHuyet).append(splitDelimiter);
            var phuongHuyetAct = HSBAFactory.eINSTANCE.createHsbaPhuongHuyetAct().init();
            addYhctDieuTriAct(dieuTriYhctSection, phuongHuyetAct, phuongHuyet);                     
        }
        
        //Xoa bóp bấm huyệt
        String xoaBopBamHuyet = emrYhctBenhAn.dieutriXoabopbamhuyet;
        if(!StringUtils.isEmpty(xoaBopBamHuyet)){    
            String xoaBopBamHuyetTitle = properties.getProperty("YHCT_DIEUTRI_XOABOPBAMHUYET_TITLE", "YHCT_DIEUTRI_XOABOPBAMHUYET_TITLE");
            dieuTriYhctText.append(xoaBopBamHuyetTitle).append(sectionDelimiter).append(xoaBopBamHuyet).append(splitDelimiter);
            var xoaBopBamHuyetAct = HSBAFactory.eINSTANCE.createHsbaXoaBopBamHuyetAct().init();
            addYhctDieuTriAct(dieuTriYhctSection, xoaBopBamHuyetAct, xoaBopBamHuyet);                       
        }
        
        //Phương pháp khác
        String phuongPhapKhac = emrYhctBenhAn.phuongphapkhac;
        if(!StringUtils.isEmpty(phuongPhapKhac)){    
            String phuongPhapKhacTitle = properties.getProperty("YHCT_DIEUTRI_PHUONGPHAPKHAC_TITLE", "YHCT_DIEUTRI_PHUONGPHAPKHAC_TITLE");
            dieuTriYhctText.append(phuongPhapKhacTitle).append(sectionDelimiter).append(phuongPhapKhac).append(splitDelimiter);
            var phuongPhapKhacAct = HSBAFactory.eINSTANCE.createHsbaPhuongPhapKhacAct().init();
            addYhctDieuTriAct(dieuTriYhctSection, phuongPhapKhacAct, phuongPhapKhac);                       
        }
        
        //Điều trị kết hợp YHHĐ
        String dieuTriKetHopYhhd = emrYhctBenhAn.dieutriKethopyhhd;
        if(!StringUtils.isEmpty(dieuTriKetHopYhhd)){
            String dieuTriKetHopYhhdTitle = properties.getProperty("YHCT_DIEUTRI_KETHOPYHHD_TITLE", "YHCT_DIEUTRI_KETHOPYHHD_TITLE");
            dieuTriYhctText.append(dieuTriKetHopYhhdTitle).append(sectionDelimiter).append(dieuTriKetHopYhhd).append(splitDelimiter);
            var dieuTriKetHopYhhdAct = HSBAFactory.eINSTANCE.createHsbaDieuTriKetHopVoiYhhdAct().init();
            addYhctDieuTriAct(dieuTriYhctSection, dieuTriKetHopYhhdAct, dieuTriKetHopYhhd);
        }
        
        //Chế độ dinh dưỡng
        String lstCheDoDinhDuong = "";//emrYhctBenhAn.getLstchedodinhduong();
        String lstCheDoDinhDuongTen = "";//emrYhctBenhAn.getLstchedodinhduongTen();
        if(!StringUtils.isEmpty(lstCheDoDinhDuong)){         
            var cheDoDinhDuongOrganizer = HSBAFactory.eINSTANCE.createHsbaCheDoDinhDuongOrganizer().init();
            II cheDoDinhDuongOrganizerId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            cheDoDinhDuongOrganizer.getIds().add(cheDoDinhDuongOrganizerId);
            dieuTriYhctSection.addOrganizer(cheDoDinhDuongOrganizer);           
            //add lstTen
            addLstTenForOrganizer(cheDoDinhDuongOrganizer, lstCheDoDinhDuongTen);
            
            if(!StringUtils.isEmpty(strSplit)){
                String[] arrActCodeCda = lstCheDoDinhDuong.split(strSplit);
                for (String actCodeCdaItem : arrActCodeCda) {
                    var cheDoDinhDuongAct = HSBAFactory.eINSTANCE.createHsbaCheDoDinhDuongAct().init();
                    addOrganizerAct(cheDoDinhDuongOrganizer, cheDoDinhDuongAct, actCodeCdaItem);
                }               
            }
        }
        //Mô tả chế độ dinh dưỡng
        String moTaCheDoDinhDuong = emrYhctBenhAn.motachedodinhduong;      
        if(!StringUtils.isEmpty(moTaCheDoDinhDuong)){
            String cheDoDinhDuongTitle = properties.getProperty("YHCT_DIEUTRI_CHEDODINHDUONG_TITLE", "YHCT_DIEUTRI_CHEDODINHDUONG_TITLE");
            dieuTriYhctText.append(cheDoDinhDuongTitle).append(sectionDelimiter).append(moTaCheDoDinhDuong).append(splitDelimiter);
            var moTaCheDoDinhDuongAct = HSBAFactory.eINSTANCE.createHsbaCheDoDinhDuongTextMotaAct().init();
            dieuTriYhctSection.addAct(moTaCheDoDinhDuongAct);
            II moTaCheDoDinhDuongActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            moTaCheDoDinhDuongAct.getIds().add(moTaCheDoDinhDuongActId);
            ED moTaText = DatatypesFactory.eINSTANCE.createED(moTaCheDoDinhDuong);
            moTaCheDoDinhDuongAct.setText(moTaText);            
        }
        
        //Chế độ chăm sóc
        var emrDmYhctChamSoc = emrYhctBenhAn.emrDmYhctCheDoChamSoc;
        if(emrDmYhctChamSoc != null){
            String code = emrDmYhctChamSoc.maicd;                    
            String displayName = emrDmYhctChamSoc.ten;         
            var cheDoChamSocAct = HSBAFactory.eINSTANCE.createHsbaCheDoChamSocAct().init();
            II cheDoChamSocActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            cheDoChamSocAct.getIds().add(cheDoChamSocActId);
            dieuTriYhctSection.addAct(cheDoChamSocAct);         
            ThongTinCanLamSangUtil.addActCdCodeTag(cheDoChamSocAct, code, displayName); 
        }
        
        if(dieuTriYhctText.length() > 0){
            dieuTriYhctText.setLength(dieuTriYhctText.length() - 1);
        }
        
        CDAExportUtil.setSectionData(dieuTriYhctSection, dieuTriYhctText.toString());   
    }
    
    public static void addDuHauSection(HsbaBenhAnYHCTSection benhAnYhctSection,  EmrYhctBenhAn emrYhctBenhAn, Map<String, String> emrParameters, Properties properties){
        var duHauSection = HSBAFactory.eINSTANCE.createHsbaDuHauSection().init();
        benhAnYhctSection.addSection(duHauSection);
        String duHauTitle = properties.getProperty("YHCT_DUHAU_TITLE", "YHCT_DUHAU_TITLE");
        CDAExportUtil.addSectionTitle(duHauSection, duHauTitle);
        StringBuilder  duHauText = new StringBuilder();
        
        String duHau = emrYhctBenhAn.tienluong;
        if(!StringUtils.isEmpty(duHau)){
            duHauText.append(duHau);
        }
        
        CDAExportUtil.setSectionData(duHauSection, duHauText.toString());   
    }
    
    public static void addBenhAnYHCT(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters, Properties properties){      
        var benhAnYhctSection = HSBAFactory.eINSTANCE.createHsbaBenhAnYHCTSection().init();
        doc.addSection(benhAnYhctSection);      
        String benhAnYhctTitle = properties.getProperty("BENHAN_YHCT_TITLE", "BENHAN_YHCT_TITLE");
        String strSplit = properties.getProperty("SPLIT_YHCT_CHANDOAN_VALUE", "SPLIT_YHCT_CHANDOAN_VALUE");
        CDAExportUtil.addSectionTitle(benhAnYhctSection, benhAnYhctTitle);
        StringBuilder benhAnYhctText = new StringBuilder();
        CDAExportUtil.setSectionData(benhAnYhctSection, benhAnYhctText.toString());
        
        var emrYhctBenhAn = emrDanhSachHoSoBenhAn.getEmrYhctBenhAn();     
        if(emrYhctBenhAn != null){      
            //Mô tả chăm sóc
            String moTaChamSoc = emrYhctBenhAn.motachamsoc;
            if(!StringUtils.isEmpty(moTaChamSoc)){
                var motaChamSocObs = HSBAFactory.eINSTANCE.createHsbaYhctTextMoTaObservation().init();
                ED textMotaChamSoc = DatatypesFactory.eINSTANCE.createED(moTaChamSoc);
                motaChamSocObs.setText(textMotaChamSoc);                
                benhAnYhctSection.addObservation(motaChamSocObs);               
            }
            
            //Vọng chẩn
            addVongChanSection(benhAnYhctSection, emrYhctBenhAn, emrParameters, properties, strSplit);
                    
            //Văn chẩn
            addVawnChanSection(benhAnYhctSection, emrYhctBenhAn, emrParameters, properties, strSplit);
                    
            //Vấn chẩn
            addVaanChanSection(benhAnYhctSection, emrYhctBenhAn, emrParameters, properties, strSplit);      
            
            //Thiết chẩn
            addThietChanSection(benhAnYhctSection, emrYhctBenhAn, emrParameters, properties, strSplit);
            
            //Tóm tắt tứ chẩn
            addTomTatTuChanSection(benhAnYhctSection, emrYhctBenhAn, emrParameters, properties);
            
            //Biện chứng luận trị
            addBienChungLuanTriSection(benhAnYhctSection, emrYhctBenhAn, emrParameters, properties);
            
            //Điều trị YHCT
            addDieuTriYhctSection(benhAnYhctSection, emrYhctBenhAn, emrParameters, properties, strSplit);
            
            //Dự hậu
            addDuHauSection(benhAnYhctSection, emrYhctBenhAn, emrParameters, properties);
        }   
    }
    
    public static void addEmrYhctDonThuocChiTiet(HsbaAdministrationOfSubstanceAct administrationOfSubstanceAct, EmrYhctDonThuocChiTiet chiTietDonThuocItem,  Map<String, String> emrParameters, StringBuilder yhctMedicationsSectionText, String medicationSplit){
        if(chiTietDonThuocItem != null){
            var substanceAdminER = CDAFactory.eINSTANCE.createEntryRelationship();
            substanceAdminER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
            administrationOfSubstanceAct.getEntryRelationships().add(substanceAdminER);
            
            var substanceAdministration = HSBAFactory.eINSTANCE.createHsbaSubstanceAdministration().init();
            substanceAdminER.setSubstanceAdministration(substanceAdministration);
            II substanceAdminId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
            substanceAdministration.getIds().add(substanceAdminId);                     
                            
            //-- Thông tin thuốc 
            var emrDmThuoc = chiTietDonThuocItem.emrDmYhctViThuoc;
            
            if(emrDmThuoc != null){         
                var manufacturedProduct = HSBAFactory.eINSTANCE.createHsbaManufacturedProduct().init();
                var consumable = CDAFactory.eINSTANCE.createConsumable();
                substanceAdministration.setConsumable(consumable);
                consumable.setManufacturedProduct(manufacturedProduct);                                                                     
                                                        
                var material = CDAFactory.eINSTANCE.createMaterial();
                manufacturedProduct.setManufacturedMaterial(material);
                CE codeTag = DatatypesFactory.eINSTANCE.createCE();
                material.setCode(codeTag);                  

                String codeCda = emrDmThuoc.maicd;
                String displayName = emrDmThuoc.ten;
                String codeSystem = emrParameters.get("emr_dm_yhct_vi_thuoc");
                if(!StringUtils.isEmpty(codeCda)){
                    codeTag.setCode(codeCda);                           
                }
                if(!StringUtils.isEmpty(displayName)){
                    codeTag.setDisplayName(displayName);
                    yhctMedicationsSectionText.append(displayName).append(medicationSplit);
                    ED originalText = DatatypesFactory.eINSTANCE.createED(displayName);
                    codeTag.setOriginalText(originalText);
                }
                if(!StringUtils.isEmpty(codeSystem))
                {
                    codeTag.setCodeSystem(codeSystem);
                }       
                
                //-- Liều lượng
                String lieuLuong = chiTietDonThuocItem.lieuluongdung;
                if(!StringUtils.isEmpty(lieuLuong)){
                    Double lieuDung = Double.parseDouble(lieuLuong);
                    if(lieuDung != null && lieuDung > 0){                           
                        material.setLotNumberText(DatatypesFactory.eINSTANCE.createST(String.valueOf(lieuDung)));
                    }
                }               
                
            }   
        }       
    }
    
    public static void addYhctMedications(HsbaDocument doc, EmrHoSoBenhAn emrDanhSachHoSoBenhAn, Map<String, String> emrParameters, Properties properties) throws IOException{     
        var yhctMedicationsSection = HSBAFactory.eINSTANCE.createHsbaYhctMedicationsSection().init();
        doc.addSection(yhctMedicationsSection);
        II yhctMedicationsSectionId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
        yhctMedicationsSection.setId(yhctMedicationsSectionId);
        String yhctMedicationsSectionTitle = properties.getProperty("DUNGTHUOC_TITLE", "DUNGTHUOC_TITLE");
        CDAExportUtil.addSectionTitle(yhctMedicationsSection, yhctMedicationsSectionTitle);
        StringBuilder yhctMedicationsSectionText = new StringBuilder(); 
        String ngayKeDonTitle = properties.getProperty("DUNGTHUOC_NGAYKEDON_TITLE", "DUNGTHUOC_NGAYKEDON_TITLE");
        String bacSiKeDonTitle = properties.getProperty("DUNGTHUOC_BACSIKEDON_TITLE", "DUNGTHUOC_BACSIKEDON_TITLE");
        String tenThuocTitle = properties.getProperty("DUNGTHUOC_TENTHUOC_TITLE", "DUNGTHUOC_TENTHUOC_TITLE");
        String contentDelimiter = properties.getProperty("SECTION_CONTENT_DELIMITER", "SECTION_CONTENT_DELIMITER");
        String strSplit = properties.getProperty("SPLIT_YHCT_CHANDOAN_SYMBOL", "SPLIT_YHCT_CHANDOAN_SYMBOL");
        String medicationSplit = properties.getProperty("HC_SPLIT_BACSIHOICHAN", "HC_SPLIT_BACSIHOICHAN");                          
        
        var lstEmrYhctDonThuocs = emrDanhSachHoSoBenhAn.getEmrYhctDonThuocs();        
        if(lstEmrYhctDonThuocs != null){            
            for (var yhctDonThuocItem : lstEmrYhctDonThuocs) {      
                var medicationsEntry = CDAFactory.eINSTANCE.createEntry();
                medicationsEntry.setTypeCode(x_ActRelationshipEntry.DRIV);
                yhctMedicationsSection.getEntries().add(medicationsEntry);
                
                var lstEmrQuanLyFileDinhKemDonThuocYhcts = yhctDonThuocItem.emrFileDinhKemYhctDonThuocs;

                //Thuốc sử dụng
                var lstEmrYhctDonThuocChiTiets = yhctDonThuocItem.emrYhctDonThuocChiTiets;
                if(lstEmrYhctDonThuocChiTiets != null){ 
                    var administrationOfSubstanceAct = HSBAFactory.eINSTANCE.createHsbaAdministrationOfSubstanceAct().init();
                    //Số đơn thuốc
                    II administrationOfSubstanceActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());                          
                    String sodon = yhctDonThuocItem.sodon;
                    if(!StringUtils.isEmpty(sodon)){
                        administrationOfSubstanceActId.setExtension(sodon);
                    }
                    administrationOfSubstanceAct.getIds().add(administrationOfSubstanceActId);
                    medicationsEntry.setAct(administrationOfSubstanceAct);                                                                                              
                    
                    //Ngày giờ kê đơn                   
                    Date ngayKeDon = yhctDonThuocItem.ngaykedon;
                    if(ngayKeDon != null){
                        String ngayKeDonThuoc = CDAExportUtil.getGMTDate(ngayKeDon);
                        if(!StringUtils.isEmpty(ngayKeDonThuoc)){
                            yhctMedicationsSectionText.append(ngayKeDonTitle).append(contentDelimiter).append(ngayKeDonThuoc).append(strSplit);
                        }
                    }
                    CDAExportUtil.addActEffectiveTime(administrationOfSubstanceAct, ngayKeDon);
                    
                    //Bác sĩ kê đơn 
                    var dischargeDoctorAssignedEntity = HSBAFactory.eINSTANCE.createHsbaDischargeDoctorAssignedEntity().init();
                    String bacSiKeDon = yhctDonThuocItem.bacsikedon;
                    if(!StringUtils.isEmpty(bacSiKeDon)){
                        yhctMedicationsSectionText.append(bacSiKeDonTitle).append(contentDelimiter).append(bacSiKeDon).append(strSplit);
                    }
                    ThongTinCanLamSangUtil.addActAssignedEntity(administrationOfSubstanceAct, dischargeDoctorAssignedEntity, bacSiKeDon);                                       
                    
                    //-- Số lượng thang thuốc   
                    var supplyER = CDAFactory.eINSTANCE.createEntryRelationship();
                    supplyER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                    administrationOfSubstanceAct.getEntryRelationships().add(supplyER);
                    
                    var supply = CDAFactory.eINSTANCE.createSupply();
                    supply.setMoodCode(x_DocumentSubstanceMood.EVN);
                    supplyER.setSupply(supply);                 

                    Integer soLuongThang = yhctDonThuocItem.soluongthang;
                    if(soLuongThang != null && soLuongThang > 0){
                        PQ doseQuantity = DatatypesFactory.eINSTANCE.createPQ();
                        doseQuantity.setValue(soLuongThang.doubleValue());
                        supply.setQuantity(doseQuantity);
                    }   
                    
                    //-- Ngày bắt đầu                       
                    IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
                    supply.getEffectiveTimes().add(effectiveTime);
                    IVXB_TS lowTime = DatatypesFactory.eINSTANCE.createIVXB_TS();
                    Date startDate = yhctDonThuocItem.ngaybatdau;
                    if(startDate != null){
                        String startTime = CDAExportUtil.getGMTDate(startDate);
                        lowTime.setValue(startTime);
                    }
                    effectiveTime.setLow(lowTime);
                    
                    //-- Ngày kết thúc
                    IVXB_TS highTime = DatatypesFactory.eINSTANCE.createIVXB_TS();
                    Date endDate = yhctDonThuocItem.ngayketthuc;
                    if(endDate != null){
                        String endTime = CDAExportUtil.getGMTDate(endDate);
                        highTime.setValue(endTime);
                    }
                    effectiveTime.setHigh(highTime);
                    
                    //-- Chỉ dẫn dùng thuốc 
                    var instructionsER = CDAFactory.eINSTANCE.createEntryRelationship();
                    instructionsER.setTypeCode(x_ActRelationshipEntryRelationship.COMP);
                    administrationOfSubstanceAct.getEntryRelationships().add(instructionsER);
                    
                    var instructionsAct = HSBAFactory.eINSTANCE.createHsbaInstructionsAct().init();
                    II instructionsActId = DatatypesFactory.eINSTANCE.createII(UUID.randomUUID().toString());
                    instructionsAct.getIds().add(instructionsActId);
                    instructionsER.setAct(instructionsAct);
                    
                    String chiDan = yhctDonThuocItem.chidan;
                    if(!StringUtils.isEmpty(chiDan)){
                        ED chiDanText = DatatypesFactory.eINSTANCE.createED(chiDan);
                        instructionsAct.setText(chiDanText);
                    }
                    
                    yhctMedicationsSectionText.append(tenThuocTitle).append(contentDelimiter);
                    
                    //Tài liệu đính kèm                                     
                    if(lstEmrQuanLyFileDinhKemDonThuocYhcts != null){
                        for (var item : lstEmrQuanLyFileDinhKemDonThuocYhcts) {
                            CDAExportUtil.addActExternalDocument(administrationOfSubstanceAct, item.url);
                        }                   
                    }
                    //Chi tiết đơn thuốc
                    for (var chiTietDonThuocItem : lstEmrYhctDonThuocChiTiets) {
                        if(chiTietDonThuocItem != null){
                            addEmrYhctDonThuocChiTiet(administrationOfSubstanceAct, chiTietDonThuocItem, emrParameters, yhctMedicationsSectionText, medicationSplit);
                        }
                    }   
                    if(yhctMedicationsSectionText.length() > 0 && medicationSplit.equals(yhctMedicationsSectionText.substring(yhctMedicationsSectionText.length() - 1))){
                        yhctMedicationsSectionText.setLength(yhctMedicationsSectionText.length() - 1);
                    }
                    yhctMedicationsSectionText.append(strSplit);
                } 
            }               
        }           
        CDAExportUtil.setSectionData(yhctMedicationsSection, yhctMedicationsSectionText.toString());
    }
}
