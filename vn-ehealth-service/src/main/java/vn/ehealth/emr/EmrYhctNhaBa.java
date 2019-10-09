package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_yhct_nha_ba")
public class EmrYhctNhaBa {
    
    @Id public int idhsba;
    @Column public Date ngaybatdauchua_nhaba;
    @Column public String tenbacsichua_nhaba;
    @Column public String chandoan_nhaba;
    @Column public Boolean daxoa;
    @Column public Date ngaytao;
    @Column public Integer idnguoitao;
    @Column public Date ngaysua;
    @Column public Integer idnguoisua;

}
