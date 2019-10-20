package vn.ehealth.emr.repository;

import vn.ehealth.emr.EmrCoSoKhamBenh;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmrCoSoKhamBenhRepository extends MongoRepository<EmrCoSoKhamBenh, ObjectId> {

    public Optional<EmrCoSoKhamBenh> findByMa(String ma);
}
