package vn.ehealth.emr.file;

import lombok.Data;

@Data
public class EmrQuanLyFileDinhKemBenhAn {

    public int id;
    public String tenfile;
    public String duongdan;
    public Integer idhsba;
    public Boolean daxoa;
    
    public byte[] noiDungFile;
}
