package vn.ehealth.emr.file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_quan_ly_file_dinh_kem_benhan")
public class EmrFileDinhKemBenhAn {

    @Id public int id;
    @Column public String tenfile;
    @Column public String duongdan;
    @Column public Integer idhsba;
    @Column public Boolean daxoa;
    
    @Transient public String url;
    
    @Transient public byte[] noiDungFile;

    public int getId() {
        return id;
    }

    public String getTenfile() {
        return tenfile;
    }

    public String getDuongdan() {
        return duongdan;
    }

    public Integer getIdhsba() {
        return idhsba;
    }

    public Boolean getDaxoa() {
        return daxoa;
    }

    public byte[] getNoiDungFile() {
        return noiDungFile;
    }
    
    
}
