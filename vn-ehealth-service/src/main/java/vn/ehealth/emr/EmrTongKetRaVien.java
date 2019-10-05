package vn.ehealth.emr;

import java.util.Date;

import vn.ehealth.emr.ck.EmrCkPhuongPhapDieuTriUngBuou;
import vn.ehealth.emr.ck.EmrCkTinhTrangRaVienMat;

public class EmrTongKetRaVien {

public int idhsba;
   
    /** The dienbienlamsang. */
    public String dienbienlamsang;
    
    /** The canlamsang. */
    public String canlamsang;
    
    /** The phuongphapdieutri. */
    public String phuongphapdieutri;
    
    /** The tinhtrangnguoibenh. */
    public String tinhtrangnguoibenh;
    
    /** The chidandieutri. */
    public String chidandieutri;
    
    /** The daxoa. */
    public Boolean daxoa;
    
    /** The ngaytao. */
    public Date ngaytao;
    
    /** The idnguoitao. */
    public Integer idnguoitao;
    
    /** The ngaysua. */
    public Date ngaysua;
    
    /** The idnguoisua. */
    public Integer idnguoisua;
    
    /** The nguoigiaohoso. */
    public String nguoigiaohoso;
    
    /** The ngaygiaohoso. */
    public Date ngaygiaohoso;
    
    /** The nguoinhanhoso. */
    public String nguoinhanhoso;
    
    /** The ngaynhanhoso. */
    public Date ngaynhanhoso;
    
    /** The bacsydieutri. */
    public String bacsydieutri;
    
    /** The ngaybacsydieutriky. */
    public Date ngaybacsydieutriky;
    
    public EmrCkPhuongPhapDieuTriUngBuou emrCkPhuongPhapDieuTriUngBuou;
    public EmrCkTinhTrangRaVienMat emrCkTinhTrangRaVienMat;

    //Cuongln 17/05
    public Integer so_to_x_quang;
    public Integer soToCTScanner;
    public Integer soToSieuAm;
    public Integer soToXetNghiem;
    public Integer soToKhac;
}
