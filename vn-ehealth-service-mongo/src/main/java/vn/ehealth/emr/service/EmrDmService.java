package vn.ehealth.emr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.EmrDmContent;
import vn.ehealth.emr.repository.EmrDmRepository;
import vn.ehealth.emr.repository.EmrNhomDmRepository;

@Service
public class EmrDmService {

    @Autowired EmrDmRepository emrDmRepository;
    @Autowired EmrNhomDmRepository emrNhomDmRepository;
    
    public EmrDmContent getEmrDmByNhom_Ma(String maNhom, String ma) {
        var emrDmContent = new EmrDmContent();
        emrDmContent.ma = emrDmContent.ten = "";
        
        var nhomId = emrNhomDmRepository.findByMa(maNhom).map(x -> x.id).orElse(null);
        if(nhomId != null) {
            var emrDm = emrDmRepository.findByNhomIdAndMa(nhomId, ma);            
            emrDmContent.ten = emrDm.map(x -> x.ten).orElse("");
            emrDmContent.ma = emrDm.map(x -> x.ma).orElse("");
        }
        
        return emrDmContent;
    }
}
