package vn.ehealth.emr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.ehealth.emr.model.EmrDm;
import vn.ehealth.emr.model.EmrDmContent;
import vn.ehealth.emr.repository.EmrDmRepository;
import vn.ehealth.emr.repository.EmrNhomDmRepository;

@Service
public class EmrDmService {

    @Autowired MongoTemplate mongoTemplate;
    @Autowired EmrDmRepository emrDmRepository;
    @Autowired EmrNhomDmRepository emrNhomDmRepository;
    
    public EmrDmContent getEmrDmByNhom_Ma(String maNhom, String ma) {
        var emrDmContent = new EmrDmContent();
        emrDmContent.ma = emrDmContent.ten = "";
        
        var nhomId = emrNhomDmRepository.findByMa(maNhom).map(x -> x.id).orElse(null);
        if(nhomId != null) {
            var emrDm = emrDmRepository.findByEmrNhomDmIdAndMa(nhomId, ma);            
            emrDmContent.ten = emrDm.map(x -> x.ten).orElse("");
            emrDmContent.ma = emrDm.map(x -> x.ma).orElse("");
        }
        
        return emrDmContent;
    }
    
    public long countEmrDm(String maNhom, String ten, int capdo, String maCha) {
        var nhomId = emrNhomDmRepository.findByMa(maNhom).map(x -> x.id).orElse(null);
        if(nhomId != null) {
            var criteria = Criteria.where("emrNhomDmId").is(nhomId).and("ten").regex(ten);
            
            if(!StringUtils.isEmpty(maCha)) {
                var chaId = emrDmRepository.findByEmrNhomDmIdAndMa(nhomId, maCha).map(x -> x.id).orElse(null);
                criteria = criteria.and("emrDmChaId").is(chaId);
            }
            
            if(capdo > 0) {
                criteria = criteria.and("capdo").is(capdo);
            }
            return mongoTemplate.count(new Query(criteria), EmrDm.class);
        }
        return 0;
    }
    
    public List<EmrDm> getEmrDm(String maNhom, String ten, int capdo, String maCha, int offset, int limit) {
        var nhomId = emrNhomDmRepository.findByMa(maNhom).map(x -> x.id).orElse(null);
        if(nhomId != null) {
            var sort = new Sort(Sort.Direction.ASC, "id");
            var pageable = new OffsetBasedPageRequest(limit, offset, sort);
            
            var criteria = Criteria.where("emrNhomDmId").is(nhomId).and("ten").regex(ten);
            
            if(!StringUtils.isEmpty(maCha)) {
                var chaId = emrDmRepository.findByEmrNhomDmIdAndMa(nhomId, maCha).map(x -> x.id).orElse(null);
                criteria = criteria.and("emrDmChaId").is(chaId);
            }
            
            if(capdo > 0) {
                criteria = criteria.and("capdo").is(capdo);
            }
            return mongoTemplate.find(new Query(criteria).with(pageable), EmrDm.class);
        }
        return new ArrayList<>();
    }
}
