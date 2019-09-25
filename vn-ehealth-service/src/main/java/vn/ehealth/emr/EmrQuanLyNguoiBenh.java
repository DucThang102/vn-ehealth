package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrQuanLyNguoiBenh {

    public int idhsba;
    
    public Integer idnoigioithieu;
    public EmrDm emrDmNoiGioiThieu;
    
    public Integer iddoituongtaichinh;
    public EmrDm emrDmLoaiDoiTuongTaiChinh;
    
    public Integer idloairavien;
    public EmrDm emrDmLoaiRaVien;
    
    public Integer idnoitructiepvao; 
    public EmrDm emrDmNoiTrucTiepVao;
    
    public Integer idloaichuyenvien;
    public EmrDm emrDmLoaiChuyenVien;
    
    public EmrDm emrDmCoSoKhamBenh;
    
    public Integer idloaivaovien;
    public EmrDm emrDmLoaiVaoVien;
    
    public Integer idnoichuyenden;
    public Integer idloaidieutri;
    
    public String sovaovien;
    public Date ngaygiovaovien;
    public Integer vaovienlanthu;
    public String tenbacsikham;
    public Date ngaygioravien;
    public Integer tongsongaydieutri;
    public String tenbacsichoravien;
    public String noichuyenden;
    public Boolean daxoa;
    public Date ngaytao;
    public Integer idnguoitao;
    public Date ngaysua;
    public Integer idnguoisua; 
    
    
}
