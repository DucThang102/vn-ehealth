package vn.ehealth.emr.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ehealth.emr.model.EmrHinhAnhTonThuong;

public interface EmrHinhAnhTonThuongRepository extends MongoRepository<EmrHinhAnhTonThuong, ObjectId> {

    public List<EmrHinhAnhTonThuong> findByEmrHoSoBenhAnIdAndTrangThai(ObjectId emrHoSoBenhAnId, int trangThai);
    public List<EmrHinhAnhTonThuong> findByEmrBenhNhanIdAndTrangThai(ObjectId emrBenhNhanId, int trangThai);
}
