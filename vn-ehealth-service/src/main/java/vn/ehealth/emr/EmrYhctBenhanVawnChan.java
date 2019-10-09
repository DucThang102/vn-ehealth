package vn.ehealth.emr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_yhct_benhan_vawn_chan")
public class EmrYhctBenhanVawnChan {

    @Id public int idhsba;
    @Column public String lsttiengnoi;
    @Column public String lsttiengnoiTen;
    @Column public String lsthoitho;
    @Column public String lsthoithoTen;
    @Column public String lsttiengho;
    @Column public String lsttienghoTen;
    @Column public String lstmuicothe;
    @Column public String lstmuicotheTen;
    @Column public String lstchatthaibenhly;
    @Column public String lstchatthaibenhlyTen;
    @Column public String motavawnchan;
    
    // Add 02/04/2015 (for display in report YHCT)
    @Column public String lsttiengnoiHienthi;
    @Column public String lsthoithoHienthi;
    @Column public String lsttienghoHienthi;
    @Column public String lstmuicotheHienthi;
    @Column public String lstchatthaibenhlyHienthi;
    
    @Column public Boolean amthanho;
    @Column public Boolean amthanhnac;
}
