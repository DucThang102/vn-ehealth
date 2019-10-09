package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tam_than")
public class EmrCkTamThan {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    @Column public String bieuhienchung;
    
    @Column public String dinhhuongkhonggian;
    
    @Column public String dinhhuongthoigian;
    
    @Column public String dinhhuongbanthan;
    
    @Column public String camxuc;
    
    @Column public String trigiac;
    
    @Column public String tuduyhinhthuc;
    
    @Column public String tuduynoidung;
    
    @Column public String hoatdongcoychi;
    
    @Column public String hoatdongbannang;
    
    @Column public String trinhomaymoc;
    
    @Column public String trinhothonghieu;
    
    @Column public String trinangphantich;
    
    @Column public String trinangtonghop;
    
    @Column public String khanangchuy; 
    @Column public String roiloanythuc;
    
    @Column public String hoichungroiloanythuc;
}
