package vn.ehealth.emr.file;

import lombok.Data;

@Data
public class EmrQuanLyFileDinhKem {

    public int id;
    public Integer iddk;
    public String tenfile;
    public String duongdan;
    
    public byte[] noiDungFile;
}
