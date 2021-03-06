package vn.ehealth.emr.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.model.EmrQuaTrinhChamSoc;

public interface EmrQuaTrinhChamSocRepository extends MongoRepository<EmrQuaTrinhChamSoc, ObjectId> {
    
    public List<EmrQuaTrinhChamSoc> findByEmrChamSocId(ObjectId emrChamSocId);

}
