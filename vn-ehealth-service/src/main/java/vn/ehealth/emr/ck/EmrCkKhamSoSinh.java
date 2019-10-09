package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_kham_so_sinh")
public class EmrCkKhamSoSinh {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public Boolean ditatbamsinh;
    @Column public Boolean ditathaumon;
    @Column public String motaditat;
    @Column public String tinhhinhsosinhvaokhoa;
    @Column public String tinhtrangtoanthan;
    @Column public Integer mausacda;
    @Column public String tinhtranghohap;
    @Column public Integer nhiptho;
    @Column public Integer silverman;
    @Column public Integer nhiptim;
}
