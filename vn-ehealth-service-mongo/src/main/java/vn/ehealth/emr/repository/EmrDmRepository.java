package vn.ehealth.emr.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.model.EmrDm;

public interface EmrDmRepository extends MongoRepository<EmrDm, ObjectId> {
    
    Optional<EmrDm> findByEmrNhomDmIdAndMa(ObjectId emrNhomDmId, String ma);
}
