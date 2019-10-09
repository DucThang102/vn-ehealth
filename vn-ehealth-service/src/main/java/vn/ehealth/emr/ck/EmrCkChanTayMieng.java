package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_chan_tay_mieng")
public class EmrCkChanTayMieng {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;    
    @Column public Boolean timtai;
    @Column public Integer spo2;
    @Column public Integer trigiac;
    @Column public Boolean loetmieng;
    @Column public Boolean phatban;
}
