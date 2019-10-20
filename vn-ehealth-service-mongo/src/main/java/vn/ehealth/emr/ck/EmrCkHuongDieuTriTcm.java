package vn.ehealth.emr.ck;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrCkHuongDieuTriTcm {

    public Boolean thooxy;
    public Boolean chongsoc;
    public Boolean dieutricaoHa;
    public Boolean anthan;
    public Boolean YGlobulin;
    public Boolean nhapIcu;
    public String dieutrikhac;
}
