package vn.ehealth.emr;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrQuanLyNguoiBenh {

    public EmrDmContent emrDmNoiGioiThieu;

    public EmrDmContent emrDmLoaiDoiTuongTaiChinh;

    public EmrDmContent emrDmLoaiRaVien;

    public EmrDmContent emrDmNoiTrucTiepVao;

    public EmrDmContent emrDmLoaiChuyenVien;

    public EmrDmContent emrDmCoSoKhamBenh;

    public EmrDmContent emrDmLoaiVaoVien;
    
    public String sovaovien;

    public Date ngaygiovaovien;
    
    public Integer vaovienlanthu;
    
    public Date ngaygioravien;    

    public String tenbacsikham;    

    public Integer tongsongaydieutri;

    public String tenbacsichoravien;

    public String noichuyenden;
    
}
