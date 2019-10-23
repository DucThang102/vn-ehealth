package vn.ehealth.emr.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.EmrHoSoBenhAn;

public interface EmrHoSoBenhAnRepository extends MongoRepository<EmrHoSoBenhAn, ObjectId> {

    List<EmrHoSoBenhAn> findByMayte(String mayte);
    List<EmrHoSoBenhAn> findByTrangThaiAndIsLatest(int trangThai, boolean isLatest);
    int countByTrangThaiAndIsLatest(int trangThai, boolean isLatest);
}
