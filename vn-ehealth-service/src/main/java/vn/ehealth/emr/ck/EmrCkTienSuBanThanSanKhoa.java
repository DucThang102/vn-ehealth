package vn.ehealth.emr.ck;

import lombok.Data;

@Data
public class EmrCkTienSuBanThanSanKhoa {

    public int idhsba;
    public Integer idchuyenkhoa;
    public Boolean caohuyetap;
    public Boolean benhtim;
    public Boolean laophoi;
    public Boolean henphequan;
    public Boolean benhthan;
    public Boolean basedow;
    public Boolean viemtactinhmach;
    public Boolean dongkinh;
    public Boolean moruotthua;
    public Boolean diungthuoc;
    public String thongtincuthe;
    public String phauthuatobung;
}
