package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_co_xuong_khop")
public class EmrCkCoXuongKhop {

    @Id public int idhsba;
    
    @Column public Integer idchuyenkhoa;
    
    @Column public String hinhthekhop;
    @Column public String tamhoatdongvaovien;

    @Column public String tamhoatdonravien;

    @Column public String ttbenhlyco;

    @Column public String roiloanco;

    @Column public String tencothu;

    @Column public Integer baccothu;

    @Column public String cotsongttbly;

    @Column public String cotsongschober;

    @Column public String cotsongstibor;

    @Column public String cotsongroiloancn;
}
