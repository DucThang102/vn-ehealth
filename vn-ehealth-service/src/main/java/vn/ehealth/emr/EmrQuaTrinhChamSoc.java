package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "emr_qua_trinh_cham_soc")
public class EmrQuaTrinhChamSoc {

    @Id
    public int id;
    
    @Column
    public Integer idchamsoc;
    
    @Column
    public Date ngaychamsoc;
    
    @Column
    public String ytachamsoc;
    
    @Column
    public String theodoidienbien;
    
    @Column
    public String thuchienylenh;
    
    @Column
    public Boolean daxoa;
    
    public int getId() {
        return id;
    }
    
    public Integer getIdchamsoc() {
        return idchamsoc;
    }
    
    public Date getNgaychamsoc() {
        return ngaychamsoc;
    }
    
    public String getYtachamsoc() {
        return ytachamsoc;
    }
    
    public String getTheodoidienbien() {
        return theodoidienbien;
    }
    
    public String getThuchienylenh() {
        return thuchienylenh;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }    
}
