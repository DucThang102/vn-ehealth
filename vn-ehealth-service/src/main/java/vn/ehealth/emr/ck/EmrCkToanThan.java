package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_toan_than")
public class EmrCkToanThan {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public String tinhthan;
    @Column public String hinhdang;
    @Column public String thetrang;
    @Column public String da;
    @Column public String niemmac;
    @Column public Boolean biphu;
    @Column public Boolean phutoanthan;
    @Column public String trieuchungphu;
    @Column public String trieuchungxuathuyet;
    @Column public String longtocmong;
    @Column public String tuyengiap;
    @Column public String ganKichthuoc;
    @Column public String ganMatdo;
    @Column public String ganBogan;
    @Column public String ganDau;
    @Column public String ganThongtinbosung;
    @Column public String lachKichthuoc;
    @Column public String lachMatdo;
    @Column public String lachBolach;
    @Column public String lachMatlach;
    @Column public String lachDaulach;
    @Column public String lachThongtinbosung;
    @Column public String hachKichthuoc;
    @Column public String hachSoluong;
    @Column public String hachDodidong;
    @Column public String hachDodauhach;
    @Column public String hachThongtinbosung;
    @Column public String thanMota;
    @Column public String timMota;
    @Column public String phoiMota;
    @Column public String vuMota;
    
    @Column public String thieumau;
    
    @Column public String khuyetTatDacBiet;
    
    @Column public String matGan;
    @Column public String hachViTri;
    @Column public Integer tyletonthuongloai1;
    @Column public Integer tyletonthuongloai2;
    @Column public Integer tyletonthuongloai3;
    @Column public Integer tyletonthuongloai4;
    @Column public Integer tyletonthuongloai5;
}
