package vn.ehealth.emr.ck;

import lombok.Data;

@Data
public class EmrCkTienSuGiaDinh {

    public int idhsba;
    public Integer idchuyenkhoa;
    public Boolean sinhdoi;
    public Boolean didang;
    public Boolean benhditruyen;
    public Boolean daiduong;
    public Boolean caohuyetap;
    public String benhkhac;
}
