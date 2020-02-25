package vn.ehealth.emr.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrTongKetRaVien {

    public String dienbienlamsang;

    public String canlamsang;

    public String phuongphapdieutri;

    public String tinhtrangnguoibenh;

    public String chidandieutri;

    public String nguoigiaohoso;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaygiaohoso;

    public String nguoinhanhoso;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaynhanhoso;

    public String bacsydieutri;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaybacsydieutriky;

    public Integer soToXQuang;
    
    public Integer soToCTScanner;
    
    public Integer soToSieuAm;
    
    public Integer soToXetNghiem;
    
    public Integer soToKhac;
}
