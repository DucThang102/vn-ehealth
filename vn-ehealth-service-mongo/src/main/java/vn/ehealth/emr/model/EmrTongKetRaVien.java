package vn.ehealth.emr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrTongKetRaVien {

    @JsonInclude(Include.NON_NULL)
    
    public static class EmrToDieuTri {
        public String ma;
        public String ten;
        public int soluong;
    }
    
    public String dienbienlamsang;

    public String canlamsang;

    public String phuongphapdieutri;

    public String tinhtrangnguoibenh;

    public String chidandieutri;

    public String nguoigiaohoso;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date ngaygiaohoso;

    public String nguoinhanhoso;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date ngaynhanhoso;

    public EmrYSy bacsydieutri;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date ngaybacsydieutriky;

    public List<EmrToDieuTri> sotodieutri = new ArrayList<>();
}
