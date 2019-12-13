package vn.ehealth.emr.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.model.EmrLog;

public interface EmrLogRepository extends MongoRepository<EmrLog, ObjectId> {
    
    List<EmrLog> findByObjectClassAndObjectIdAndHanhDongId(String objectClass, ObjectId objectId, ObjectId hanhDongId, Sort sort);
}
