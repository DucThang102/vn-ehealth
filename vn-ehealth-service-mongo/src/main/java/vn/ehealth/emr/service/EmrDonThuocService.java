package vn.ehealth.emr.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;

import javax.annotation.Nonnull;

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

import vn.ehealth.emr.model.EmrDonThuoc;
import vn.ehealth.emr.model.EmrXetNghiem;
import vn.ehealth.emr.object.CountAggregation;
import vn.ehealth.emr.repository.EmrDonThuocRepository;

@Service
public class EmrDonThuocService {

    @Autowired EmrDonThuocRepository emrDonThuocRepository;
    @Autowired EmrDonThuocChiTietService emrDonThuocChiTietService;
    @Autowired MongoTemplate mongoTemplate;
    
    public List<EmrDonThuoc> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrDonThuocRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public void deleteAllByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        for(var donthuoc : getByEmrHoSoBenhAnId(emrHoSoBenhAnId)) {
            emrDonThuocRepository.delete(donthuoc);
        }
    }
    
    public EmrDonThuoc createOrUpdate(@Nonnull EmrDonThuoc emrDonThuoc) {
        emrDonThuoc = emrDonThuocRepository.save(emrDonThuoc);
                
        emrDonThuocChiTietService.deleteAllByDonThuocId(emrDonThuoc.id);
        
        for(int i = 0; emrDonThuoc.emrDonThuocChiTiets != null && i < emrDonThuoc.emrDonThuocChiTiets.size(); i++) {
            var dtct = emrDonThuoc.emrDonThuocChiTiets.get(i);
            dtct.emrDonThuocId = emrDonThuoc.id;
            dtct.emrHoSoBenhAnId = emrDonThuoc.emrHoSoBenhAnId;
            dtct.emrBenhNhanId = emrDonThuoc.emrBenhNhanId;
            dtct.emrCoSoKhamBenhId = emrDonThuoc.emrCoSoKhamBenhId;
            dtct = emrDonThuocChiTietService.createOrUpdate(dtct);
            emrDonThuoc.emrDonThuocChiTiets.set(i, dtct);
        }
         
        return emrDonThuoc;
    }
    
    public void delete(ObjectId id) {
        emrDonThuocRepository.deleteById(id);
    }
    
    public List<EmrDonThuoc> getDsDonThuocByBenhNhan(ObjectId benhNhanId, int offset, int limit) {	
    	LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("emr_ho_so_benh_an")
                .localField("emrHoSoBenhAnId")
                .foreignField("_id")
                .as("emrHoSoBenhAn");
    	UnwindOperation unwindOperation = Aggregation.unwind("emrHoSoBenhAn");
		Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, Aggregation.match(Criteria.where("emrHoSoBenhAn.emrBenhNhanId").is(benhNhanId)), sort(Sort.Direction.DESC, "_id"), skip(offset), limit(limit));
		List<EmrDonThuoc> results = mongoTemplate.aggregate(aggregation, "emr_don_thuoc", EmrDonThuoc.class).getMappedResults();
		return results;
    }
    
    public long countDsDonThuocByBenhNhan(ObjectId benhNhanId) {	
    	LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("emr_ho_so_benh_an")
                .localField("emrHoSoBenhAnId")
                .foreignField("_id")
                .as("emrHoSoBenhAn");
    	UnwindOperation unwindOperation = Aggregation.unwind("emrHoSoBenhAn");
    	CountOperation count = Aggregation.count().as("total");
		Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, Aggregation.match(Criteria.where("emrHoSoBenhAn.emrBenhNhanId").is(benhNhanId)), sort(Sort.Direction.DESC, "_id"), count);
		List<CountAggregation> results = mongoTemplate.aggregate(aggregation, "emr_don_thuoc", CountAggregation.class).getMappedResults();
		return results.get(0).total;
    }
}
