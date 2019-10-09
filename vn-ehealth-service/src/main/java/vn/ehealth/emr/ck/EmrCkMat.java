package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_mat")
public class EmrCkMat {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    
    @Column public String thiluckhongkinhtrai;
    
    @Column public String thiluckhongkinhphai;
    
    @Column public String thiluccokinhtrai;
    
    @Column public String thiluccokinhphai;
    
    @Column public String nhanaptrai;
    
    @Column public String nhanapphai;
    
    @Column public String thitruongtrai;
    
    @Column public String thitruongphai;
    
    @Column public Boolean choimattrai;
    
    @Column public Boolean choimatphai;
    
    @Column public Boolean chaynuocmattrai;
    
    @Column public Boolean chaynuocmatphai;
    
    @Column public Boolean sosangmattrai;
    
    @Column public Boolean sosangmatphai;
    
    @Column public Boolean momattrai;
    
    @Column public Boolean momatphai;
    
    @Column public Boolean rucmattrai;
    
    @Column public Boolean rucmatphai;
    
    
    
    @Column public String trieuchungmattrai;
    
    @Column public String trieuchungmatphai;
    //le dao
    @Column public String ledaotrai;   
    @Column public String ledaophai;
    //mi mat
    @Column public String mimattrai;   
    @Column public String mimatphai;
    
    //ket mac
    @Column public String ketmacmattrai;   
    @Column public String ketmacmatphai;
    
    //tinh hinh mat hoi
    @Column public String mathoitrai;  
    @Column public String mathoiphai;
    //giac mac
    @Column public String giaccungmacmattrai;  
    @Column public String giaccungmacmatphai;
    //cung  mac
    @Column public String cungmacmattrai;  
    @Column public String cungmacmatphai;
    
    //tien phong
    @Column public String tienphongmattrai;    
    @Column public String tienphongmatphai;
    
    //mong mat
    @Column public String mongmattrai; 
    @Column public String mongmatphai;
    
    //dong tu phan xa
    @Column public String dongtumattrai;   
    @Column public String dongtumatphai;
    
    //dich kinh(thuy tinh dich)
    @Column public String dichkinhmattrai; 
    @Column public String dichkinhmatphai;
    
    //thuy tinh the
    @Column public String thethuytinhmattrai;  
    @Column public String thethuytinhmatphai;
    
    //soi sang dong tu
    @Column public String soisangdongtumattrai;    
    @Column public String soisangdongtumatphai;
    //goc tien phong 
    @Column public String goctienphongmattrai; 
    @Column public String goctienphongmatphai; 
    
    //nhan cau 
    @Column public String nhancaumattrai;  
    @Column public String nhancaumatphai;
    
    //van nhan
    @Column public String vannhanmattrai;  
    @Column public String vannhanmatphai;
    
    //ho mat
    @Column public String homattrai;   
    @Column public String homatphai;   
    //day mat
    @Column public String daymattrai;  
    @Column public String daymatphai;  
}
