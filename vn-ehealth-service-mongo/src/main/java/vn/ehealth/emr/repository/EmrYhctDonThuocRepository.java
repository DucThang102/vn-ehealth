package vn.ehealth.emr.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.EmrYhctDonThuoc;

public interface EmrYhctDonThuocRepository extends MongoRepository<EmrYhctDonThuoc, ObjectId> {

    public List<EmrYhctDonThuoc> findByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId);
}
