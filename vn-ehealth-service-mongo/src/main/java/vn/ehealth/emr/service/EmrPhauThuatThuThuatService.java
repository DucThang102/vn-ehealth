package vn.ehealth.emr.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.CountOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrPhauThuatThuThuat;
import vn.ehealth.emr.model.EmrXetNghiem;
import vn.ehealth.emr.object.CountAggregation;
import vn.ehealth.emr.repository.EmrPhauThuatThuThuatRepository;

@Service
public class EmrPhauThuatThuThuatService {

    @Autowired EmrPhauThuatThuThuatRepository emrPhauThuatThuThuatRepository;
    @Autowired MongoTemplate mongoTemplate;
    
    public List<EmrPhauThuatThuThuat> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId){
        return emrPhauThuatThuThuatRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public void deleteAllByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        for(var pttt : getByEmrHoSoBenhAnId(emrHoSoBenhAnId)) {
            emrPhauThuatThuThuatRepository.delete(pttt);
        }
    }
    
    public EmrPhauThuatThuThuat createOrUpdate(EmrPhauThuatThuThuat emrPhauThuatThuThuat) {
        return emrPhauThuatThuThuatRepository.save(emrPhauThuatThuThuat);                
    }
    
    public void delete(ObjectId id) {
        emrPhauThuatThuThuatRepository.deleteById(id);
    }
    
    public List<EmrPhauThuatThuThuat> getDsPhauThuatThuThuatByBenhNhan(ObjectId benhNhanId, int offset, int limit) {
    	LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("emr_ho_so_benh_an")
                .localField("emrHoSoBenhAnId")
                .foreignField("_id")
                .as("emrHoSoBenhAn");
    	UnwindOperation unwindOperation = Aggregation.unwind("emrHoSoBenhAn");
		Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, Aggregation.match(Criteria.where("emrHoSoBenhAn.emrBenhNhanId").is(benhNhanId)), sort(Sort.Direction.DESC, "_id"), skip(offset), limit(limit));
		List<EmrPhauThuatThuThuat> results = mongoTemplate.aggregate(aggregation, "emr_phau_thuat_thu_thuat", EmrPhauThuatThuThuat.class).getMappedResults();
		return results;
    }
    
    public long countDsPhauThuatThuThuatByBenhNhan(ObjectId benhNhanId) { 	
    	LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("emr_ho_so_benh_an")
                .localField("emrHoSoBenhAnId")
                .foreignField("_id")
                .as("emrHoSoBenhAn");
    	UnwindOperation unwindOperation = Aggregation.unwind("emrHoSoBenhAn");
    	CountOperation count = Aggregation.count().as("total");
		Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, Aggregation.match(Criteria.where("emrHoSoBenhAn.emrBenhNhanId").is(benhNhanId)), sort(Sort.Direction.DESC, "_id"), count);
		List<CountAggregation> results = mongoTemplate.aggregate(aggregation, "emr_phau_thuat_thu_thuat", CountAggregation.class).getMappedResults();
		return results.get(0).total;
    }
}
