package vn.ehealth.emr.ck;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_ck_tien_su_san_khoa")
public class EmrCkTienSuSanKhoa {
    
    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public String thoigiandiadiem;
    @Column public String tuoithai;
    @Column public String dienbienthai;
    @Column public String cachde;
    @Column public String tresosinh;
    @Column public String hausan;
    
    @Column public String paraDuthang;
    @Column public String paraDenon;
    @Column public String paraSay;
    @Column public String paraSong;
    
    @Transient public List<EmrCkTienSuSanKhoaChiTiet> emrCkTienSuSanKhoaChiTiets = new ArrayList<>();

    //add SonVT 08042016
    @Column public Integer solancothai;

}
