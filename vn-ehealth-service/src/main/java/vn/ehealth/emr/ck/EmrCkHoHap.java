package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_ho_hap")
public class EmrCkHoHap {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;

    @Column public Boolean conngungtho;
    @Column public Boolean thobung;

    @Column public Boolean thonong;

    @Column public Boolean thokhokhe;

    @Column public Boolean thoritphequan;

    @Column public Boolean thoritthanhquan;

    @Column public Boolean tholomnguc;

    @Column public Boolean thoranphoi;

    @Column public String thoranphoichitiet;

    @Column public String roiloan;

    @Column public Integer silverman;

    // Add moi 10/04/2015
    @Column public String longnguc;
    @Column public String thetichkhi;
    @Column public String tinhtrangbenhly;
}
