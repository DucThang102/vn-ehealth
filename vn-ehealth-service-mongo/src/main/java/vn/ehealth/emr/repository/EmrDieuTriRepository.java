package vn.ehealth.emr.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.model.EmrDieuTri;

public interface EmrDieuTriRepository extends MongoRepository<EmrDieuTri, ObjectId> {

    public List<EmrDieuTri> findByEmrHoSoBenhAnIdAndTrangThai(ObjectId emrHoSoBenhAnId, int trangThai);
    public List<EmrDieuTri> findByEmrBenhNhanIdAndTrangThai(ObjectId emrBenhNhanId, int trangThai);
}
