package vn.ehealth.emr;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Document(collection = "emr_benh_nhan")
public class EmrBenhNhan {
    
    @Id public ObjectId id;
    
    public String getId() { return id.toHexString(); }    
   
    public EmrDmContent emrDmGioiTinh;
    
    public EmrDmContent emrDmDanToc;
    
    public EmrDmContent emrDmQuocGia;
    
    public EmrDmContent emrDmNgheNghiep;
    
    public EmrDmContent emrDmPhuongXa;
    
    public EmrDmContent emrDmQuanHuyen;
    
    public EmrDmContent emrDmTinhThanh;
    
    public EmrDmContent emrDmNgheNghiepBo;
    
    public EmrDmContent emrDmNgheNghiepMe;
    
    public String iddinhdanhchinh;

    public String iddinhdanhphu;

    public String idhis;

    public String tendaydu;
    
    public Date ngaysinh;

    public String diachi;

    public String noilamviec;

    public String sobhyt;

    public Date ngayhethanthebhyt;

    public String hotenbo;

    public String hotenme;

    public String tennguoibaotin;

    public String diachinguoibaotin;

    public String sodienthoainguoibaotin;
    
    public Date ngaySinhCuaBo;
    
    public Date ngaySinhCuaMe;
    
    public String trinhDoVanHoaCuaBo;
    
    public String trinhDoVanHoaCuaMe;
    
    @Transient public String tuoi;
    
}
