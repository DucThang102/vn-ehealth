package vn.ehealth.emr.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.EmrHoSoBenhAn;

public interface EmrHoSoBenhAnRepository extends MongoRepository<EmrHoSoBenhAn, ObjectId> {

}
