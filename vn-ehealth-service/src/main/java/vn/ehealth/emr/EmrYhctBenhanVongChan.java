package vn.ehealth.emr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_yhct_benhan_vong_chan")
public class EmrYhctBenhanVongChan {

    @Id public int idhsba;
    @Column public String lsthinhthai;
    @Column public String lsthinhthaiTen;
    @Column public String lstthan;
    @Column public String lstthanTen;
    @Column public String lstsac;
    @Column public String lstsacTen;
    @Column public String lsttrach;
    @Column public String lsttrachTen;
    @Column public String lstchatluoi;
    @Column public String lstchatluoiTen;
    @Column public String lstsacluoi;
    @Column public String lstsacluoiTen;
    @Column public String lstreuluoi;
    @Column public String lstreuluoiTen;
    @Column public String motavongchan;
    
    @Column public String lsthinhthaiHienthi;
    @Column public String lstthanHienthi;
    @Column public String lstsacHienthi;
    @Column public String lsttrachHienthi;
    @Column public String lstchatluoiHienthi;
    @Column public String lstsacluoiHienthi;
    @Column public String lstreuluoiHienthi;
}
