package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_chuc_nang_sinh_hoat")
public class EmrCkChucNangSinhHoat {

    @Id public int idhsba;    
    @Column public Integer idchuyenkhoa;    
    @Column public Integer anuong;
    
    @Column public Integer chaitoc;
    
    @Column public Integer danhrang;
    
    @Column public Integer tam;
    
    @Column public Integer macquanao;
    
    @Column public Integer divesinh;
    
    @Column public Integer namguaSap;
    
    @Column public Integer namnguaNgoi;
    
    @Column public Integer dungNgoi;
    
    @Column public Integer dunglen;
    
    @Column public Integer dichuyen;
    
    @Column public Integer dungcu;
    @Column public String sinhhoatkhac;
}
