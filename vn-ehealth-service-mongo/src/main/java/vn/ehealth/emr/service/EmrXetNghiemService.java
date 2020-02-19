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

import vn.ehealth.emr.model.EmrChanDoanHinhAnh;
import vn.ehealth.emr.model.EmrXetNghiem;
import vn.ehealth.emr.object.CountAggregation;
import vn.ehealth.emr.repository.EmrXetNghiemRepository;

@Service
public class EmrXetNghiemService {

    @Autowired EmrXetNghiemRepository emrXetNghiemRepository;
    @Autowired EmrXetNghiemDichVuService emrXetNghiemDichVuService; 
    @Autowired MongoTemplate mongoTemplate;
    
    public List<EmrXetNghiem> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrXetNghiemRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public void deleteAllByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        for(var xn : getByEmrHoSoBenhAnId(emrHoSoBenhAnId)) {
            emrXetNghiemRepository.delete(xn);
        }
    }
    
    public EmrXetNghiem createOrUpdate(@Nonnull EmrXetNghiem xn) {
        xn = emrXetNghiemRepository.save(xn);
        
        emrXetNghiemDichVuService.deleteAllByXetNghiemId(xn.id);
        
        for(int i = 0; xn.emrXetNghiemDichVus != null && i < xn.emrXetNghiemDichVus.size(); i++) {
            var xndv = xn.emrXetNghiemDichVus.get(i);
            xndv.emrXetNghiemId = xn.id;
            xndv.emrHoSoBenhAnId = xn.emrHoSoBenhAnId;
            xndv.emrBenhNhanId = xn.emrBenhNhanId;
            xndv.emrCoSoKhamBenhId = xn.emrCoSoKhamBenhId;
            xndv = emrXetNghiemDichVuService.createOrUpdate(xndv);
            xn.emrXetNghiemDichVus.set(i, xndv);
        }
        
        return xn;
    }
    
    public void delete(ObjectId id) {
        emrXetNghiemRepository.deleteById(id);
    }
    
    public List<EmrXetNghiem> getDsXetNghiemByBenhNhan(ObjectId benhNhanId, int offset, int limit) {	
    	LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("emr_ho_so_benh_an")
                .localField("emrHoSoBenhAnId")
                .foreignField("_id")
                .as("emrHoSoBenhAn");
    	UnwindOperation unwindOperation = Aggregation.unwind("emrHoSoBenhAn");
		Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, Aggregation.match(Criteria.where("emrHoSoBenhAn.emrBenhNhanId").is(benhNhanId)), sort(Sort.Direction.DESC, "_id"), skip(offset), limit(limit));
		List<EmrXetNghiem> results = mongoTemplate.aggregate(aggregation, "emr_xet_nghiem", EmrXetNghiem.class).getMappedResults();
		return results;
    }
    
    public long countDsXetNghiemByBenhNhan(ObjectId benhNhanId) {	
    	LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("emr_ho_so_benh_an")
                .localField("emrHoSoBenhAnId")
                .foreignField("_id")
                .as("emrHoSoBenhAn");
    	UnwindOperation unwindOperation = Aggregation.unwind("emrHoSoBenhAn");
    	CountOperation count = Aggregation.count().as("total");
		Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, Aggregation.match(Criteria.where("emrHoSoBenhAn.emrBenhNhanId").is(benhNhanId)), sort(Sort.Direction.DESC, "_id"), count);
		List<CountAggregation> results = mongoTemplate.aggregate(aggregation, "emr_xet_nghiem", CountAggregation.class).getMappedResults();
		return results.get(0).total;
    }
}
