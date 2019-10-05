package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrYhctNhaBaGhiChu {

    public int id;
    public Integer idhsba;
    public Date ngayhgiohen;
    public String ghichu;
    public int stt;
    public Boolean daxoa;
}
