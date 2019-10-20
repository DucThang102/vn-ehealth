package vn.ehealth.emr.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.EmrDm;

public interface EmrDmRepository extends MongoRepository<EmrDm, ObjectId> {
    
    Optional<EmrDm> findByNhomIdAndMa(ObjectId nhomId, String ma);

}
