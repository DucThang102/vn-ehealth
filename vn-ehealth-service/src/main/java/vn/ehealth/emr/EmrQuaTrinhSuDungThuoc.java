package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_qua_trinh_su_dung_thuoc")
public class EmrQuaTrinhSuDungThuoc {

    @Id
    public int id;
    
    @Column
    public Integer idhsba;
    
    @Column
    public Integer idthuoc;
    
    @Transient
    public EmrDm emrDmThuoc;
    
    @Column
    public String mathuoc;
    
    @Column
    public String phienbandmthuoc;
    
    @Column
    public String cachdung;
    
    @Column
    public Date ngaysudungthuoc;
    
    @Column
    public Integer soluong;
    
    @Column
    public Integer idnguoitao;
    
    @Column
    public Date ngaytao;
    
    @Column
    public Integer idnguoisua;
    
    @Column
    public Date ngaysua;
    
    @Column
    public Boolean daxoa;
}
