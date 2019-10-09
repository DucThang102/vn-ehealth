package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_moi_sinh")
public class EmrCkMoiSinh {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public String khuvucbenhcaptinh;
    @Column public String khuvucsong;
    @Column public String thoigiansong;
    @Column public String moisinh;
    
    // Add moi 15/04/2015
    @Column public Boolean benhdichTruonghoc;
    @Column public Boolean treCungnha;
    @Column public Boolean treGannha;
    @Column public Boolean treCungtruong;
}
