package vn.ehealth.emr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_co_so_kham_benh")
public class EmrCoSoKhamBenh {

    @Id    
    public int id;
    
    @Column
    public Integer idphuongxa;
    
    @Column
    public Integer idquanhuyen;
    
    @Column
    public Integer idtinhthanh;
    
    @Column
    public Integer iddmcosokhambenh;
    
    @Column
    public String ma;
    
    @Column
    public String ten;
    
    @Column
    public Short tuyen;
    
    @Column
    public String diachi;
    
    @Column
    public String donvichuquan;
    
    @Column
    public String giamdoc;
    
    @Column
    public String dienthoai;
    
    @Column
    public String truongphongth;
}
