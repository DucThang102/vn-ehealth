package vn.ehealth.emr;

import lombok.Data;

@Data
public class EmrCoSoKhamBenh {

    public int id;
    public Integer idphuongxa;
    public Integer idquanhuyen;
    public Integer idtinhthanh;
    public Integer iddmcosokhambenh;
    public String ma;
    public String ten;
    public Short tuyen;
    public String diachi;
    public String donvichuquan;
    public String giamdoc;
    public String dienthoai;
    public String truongphongth;
}
