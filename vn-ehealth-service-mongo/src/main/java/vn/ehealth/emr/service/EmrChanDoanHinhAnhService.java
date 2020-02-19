package vn.ehealth.emr.service;

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

import vn.ehealth.emr.model.EmrChanDoanHinhAnh;
import vn.ehealth.emr.object.CountAggregation;
import vn.ehealth.emr.repository.EmrChanDoanHinhAnhRepository;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;

@Service
public class EmrChanDoanHinhAnhService {

    @Autowired EmrChanDoanHinhAnhRepository emrChanDoanHinhAnhRepository;
    @Autowired MongoTemplate mongoTemplate;
    
    public List<EmrChanDoanHinhAnh> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrChanDoanHinhAnhRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public void deleteAllByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        for(var cdha : getByEmrHoSoBenhAnId(emrHoSoBenhAnId)) {
            emrChanDoanHinhAnhRepository.delete(cdha);
        }
    }    
    
    public EmrChanDoanHinhAnh createOrUpdate(EmrChanDoanHinhAnh emrChanDoanHinhAnh) {
        return emrChanDoanHinhAnhRepository.save(emrChanDoanHinhAnh);
    }
    
    public void delete(ObjectId id) {
        emrChanDoanHinhAnhRepository.deleteById(id);
    }
    
    public List<EmrChanDoanHinhAnh> getDsChanDoanHinhAnh(ObjectId benhNhanId, int offset, int limit) {	
    	LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("emr_ho_so_benh_an")
                .localField("emrHoSoBenhAnId")
                .foreignField("_id")
                .as("emrHoSoBenhAn");
    	UnwindOperation unwindOperation = Aggregation.unwind("emrHoSoBenhAn");
		Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, Aggregation.match(Criteria.where("emrHoSoBenhAn.emrBenhNhanId").is(benhNhanId)), sort(Sort.Direction.DESC, "_id"), skip(offset), limit(limit));
		List<EmrChanDoanHinhAnh> results = mongoTemplate.aggregate(aggregation, "emr_chan_doan_hinh_anh", EmrChanDoanHinhAnh.class).getMappedResults();
		return results;
    }
    
    public long countDsChanDoanHinhAnh(ObjectId benhNhanId) {
    	LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("emr_ho_so_benh_an")
                .localField("emrHoSoBenhAnId")
                .foreignField("_id")
                .as("emrHoSoBenhAn");
    	UnwindOperation unwindOperation = Aggregation.unwind("emrHoSoBenhAn");
    	CountOperation count = Aggregation.count().as("total");
		Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, Aggregation.match(Criteria.where("emrHoSoBenhAn.emrBenhNhanId").is(benhNhanId)), sort(Sort.Direction.DESC, "_id"), count);
		List<CountAggregation> results = mongoTemplate.aggregate(aggregation, "emr_chan_doan_hinh_anh", CountAggregation.class).getMappedResults();
		return results.get(0).total;
    }
}




