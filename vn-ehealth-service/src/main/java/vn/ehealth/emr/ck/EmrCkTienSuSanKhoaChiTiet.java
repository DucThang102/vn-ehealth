package vn.ehealth.emr.ck;

import java.util.Date;

import lombok.Data;

@Data
public class EmrCkTienSuSanKhoaChiTiet {
    
    public int id;
    public Integer idhsba;
    
    public int lancothai;
    public Integer nam;
    public Boolean duthang;
    public Boolean denon;
    public Boolean say;
    public Boolean song;
    public Boolean hut;
    public Boolean nao;
    public Boolean covac;
    public Boolean chuangoaitucung;
    public Boolean chuatrung;
    public Boolean thaichetluu;
    public Double cannang;
    public String phuongphapde;
    public String taibien;
    public Boolean daxoa;
    
    //ngay 23/7/2015
    public String dienbien;
    public String hausan;
    public Date ngayketthucthainghen;
    public Integer tuoithai;

}
