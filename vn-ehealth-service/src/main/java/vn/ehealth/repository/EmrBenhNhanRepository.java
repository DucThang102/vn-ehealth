package vn.ehealth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import vn.ehealth.emr.EmrBenhNhan;

public interface EmrBenhNhanRepository extends CrudRepository<EmrBenhNhan, Integer> {

    public Optional<EmrBenhNhan> findByIddinhdanhchinh(String iddinhdanhchinh);
}
