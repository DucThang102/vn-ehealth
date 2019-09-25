package vn.ehealth.emr;

import java.util.Date;

import lombok.Data;

@Data
public class EmrCommentYeuCauTruyCapHsba {

    public int id;
    public User nguoiComment;
    public EmrYeuCauTruyCapHsba emrYeuCauTruyCapHsba;
    public Date ngayghichu;
    public String noidung;
    public Integer idnguoitao;
    public Date ngaytao;
    public Integer idnguoisua;
    public Date ngaysua;
    public Boolean daxoa;
}
