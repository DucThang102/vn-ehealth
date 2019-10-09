package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_than_kinh")
public class EmrCkThanKinh {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    
    @Column public String daythankinhsonao;
    
    @Column public String daymat;
    
    @Column public String vandong;
    
    @Column public String truonglucco;
    
    @Column public String camgiac;
    
    @Column public String phanxa;
    
    @Column public String roiloanchucnang; 
    
    // Add moi 15/04/2015
    @Column public Integer kichthuocDongtu;
    @Column public String phanxaAnhsang;
    @Column public Boolean coguong;
    @Column public Boolean giatminhluckham;
    @Column public Boolean le;
    @Column public Boolean yeuchi;
    @Column public Boolean thatdieu;
    @Column public Boolean runggiatnhancau;
    @Column public Boolean lietthankinhso;
    @Column public Boolean nguga;
}
