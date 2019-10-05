package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmrYeuCauTruyCapHsba {

    public int id;
    public String lydoyeucau;
    public String lydohuy;
    public Date ngayyeucau;
    public Date ngaycapphep;
    public Date thoihantruycap;
    public Date ngayhuyyeucau;
    public Integer idnguoitao;
    public Date ngaytao;
    public Integer idnguoisua;
    public Date ngaysua;
    public Boolean daxoa;
    public EmrDm trangThai;
    public List<EmrYeuCauTruyCapHsbaChiTiet> emrYeuCauTruyCapHsbaChiTiets = new ArrayList<>();
    public List<EmrCommentYeuCauTruyCapHsba> emrCommentYeuCauTruyCapHsbas = new ArrayList<>();
    
    //ngay 26/5/2015: trÆ°á»�ng thÃ´ng tin tracuuHSBAGiay (boolean)
    public Boolean hsbaGiay;
    // NoiPham update 2016/06/02 start
    public String lydophanhoi;
    // NoiPham update 2016/06/02 end
    
    // NoiPham update 2016/06/17 start
    public String sodienthoainguoiyeucau;
    public String noicongtac;
}
