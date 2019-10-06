package vn.ehealth.emr.file;

public class EmrQuanLyFileDinhKemBenhAn {

    public int id;
    public String tenfile;
    public String duongdan;
    public Integer idhsba;
    public Boolean daxoa;
    
    public byte[] noiDungFile;

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
