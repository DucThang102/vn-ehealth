package vn.ehealth.emr.file;

public class EmrFileDinhKem {

    public int id;
    public Integer iddk;
    
    public String tenfile;

    public String url;
    public String duongdan;
    
    public byte[] noiDungFile;

    public int getId() {
        return id;
    }

    public Integer getIddk() {
        return iddk;
    }

    public String getTenfile() {
        return tenfile;
    }

    public String getDuongdan() {
        return duongdan;
    }

    public byte[] getNoiDungFile() {
        return noiDungFile;
    }
    
    
}
