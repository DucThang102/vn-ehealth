package vn.ehealth.emr.ck;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.relational.core.mapping.Column;

@Entity
@Table(name = "emr_ck_huong_dieu_tri_tcm")
public class EmrCkHuongDieuTriTcm {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public Boolean thooxy;
    @Column public Boolean chongsoc;
    @Column public Boolean dieutricaoHa;
    @Column public Boolean anthan;
    @Column public Boolean YGlobulin;
    @Column public Boolean nhapIcu;
    @Column public String dieutrikhac;
}
