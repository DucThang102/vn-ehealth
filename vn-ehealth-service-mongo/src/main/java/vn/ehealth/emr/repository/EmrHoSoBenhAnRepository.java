package vn.ehealth.emr.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.model.EmrHoSoBenhAn;

public interface EmrHoSoBenhAnRepository extends MongoRepository<EmrHoSoBenhAn, ObjectId> {

    List<EmrHoSoBenhAn> findByMayte(String mayte);
    Optional<EmrHoSoBenhAn> findByMayteAndIsLatest(String mayte, boolean isLatest);
    List<EmrHoSoBenhAn> findByTrangThaiAndIsLatest(int trangThai, boolean isLatest, Pageable pageable);
    long countByTrangThaiAndIsLatest(int trangThai, boolean isLatest);
    List<EmrHoSoBenhAn> findByEmrBenhNhanIdAndIsLatest(ObjectId emrBenhNhanId, boolean isLatest);
}
