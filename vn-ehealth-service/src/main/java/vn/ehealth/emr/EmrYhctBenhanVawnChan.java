package vn.ehealth.emr;

import lombok.Data;

@Data
public class EmrYhctBenhanVawnChan {

    public int idhsba;
    public String lsttiengnoi;
    public String lsttiengnoiTen;
    public String lsthoitho;
    public String lsthoithoTen;
    public String lsttiengho;
    public String lsttienghoTen;
    public String lstmuicothe;
    public String lstmuicotheTen;
    public String lstchatthaibenhly;
    public String lstchatthaibenhlyTen;
    public String motavawnchan;
    
    // Add 02/04/2015 (for display in report YHCT)
    public String lsttiengnoiHienthi;
    public String lsthoithoHienthi;
    public String lsttienghoHienthi;
    public String lstmuicotheHienthi;
    public String lstchatthaibenhlyHienthi;
    
    public Boolean amthanho;
    public Boolean amthanhnac;
}
