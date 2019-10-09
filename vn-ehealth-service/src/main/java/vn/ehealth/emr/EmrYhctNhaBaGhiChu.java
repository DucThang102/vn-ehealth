package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_yhct_ghi_chu_nha_ba")
public class EmrYhctNhaBaGhiChu {

    @Id public int id;
    @Column public Integer idhsba;
    @Column public Date ngayhgiohen;
    @Column public String ghichu;
    @Column public int stt;
    @Column public Boolean daxoa;
        
    public int getId() {
        return id;
    }
    
    public Integer getIdhsba() {
        return idhsba;
    }
    
    public Date getNgayhgiohen() {
        return ngayhgiohen;
    }
    
    public String getGhichu() {
        return ghichu;
    }
    
    public int getStt() {
        return stt;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }    
}
