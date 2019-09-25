package vn.ehealth.emr.ck;

import lombok.Data;

@Data
public class EmrCkMoiSinh {

    public int idhsba;
    public Integer idchuyenkhoa;
    public String khuvucbenhcaptinh;
    public String khuvucsong;
    public String thoigiansong;
    public String moisinh;
    
    // Add moi 15/04/2015
    public Boolean benhdichTruonghoc;
    public Boolean treCungnha;
    public Boolean treGannha;
    public Boolean treCungtruong;
}
