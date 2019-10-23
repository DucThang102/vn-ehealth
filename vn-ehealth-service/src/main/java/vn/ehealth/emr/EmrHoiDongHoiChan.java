package vn.ehealth.emr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_hoi_dong_hoi_chan")
public class EmrHoiDongHoiChan {
    
    @Id
    public int id;
    
    @Column
    public Integer idhoichan;
    
    @Column
    public String bacsihoichan;
    
    @Transient public String tenbacsi; 
    
    @Column
    public Integer idvaitro;
    
    @Transient public EmrDm emrDmVaiTro;
    
    @Column
    public Boolean daxoa;

    public int getId() {
        return id;
    }

    public Integer getIdhoichan() {
        return idhoichan;
    }

    public String getBacsihoichan() {
        return bacsihoichan;
    }

    public Integer getIdvaitro() {
        return idvaitro;
    }

    public Boolean getDaxoa() {
        return daxoa;
    }    
}
