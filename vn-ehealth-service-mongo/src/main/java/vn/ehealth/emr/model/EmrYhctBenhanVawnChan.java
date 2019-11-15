package vn.ehealth.emr.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrYhctBenhanVawnChan {

    //public String lsttiengnoi;
    //public String lsttiengnoiTen;
    //public String lsttiengnoiHienthi;
    public List<EmrDmContent> emrDmYhctTiengNois;
    
    //public String lsthoitho;
    //public String lsthoithoTen;
    //public String lsthoithoHienthi;
    public List<EmrDmContent> emrDmYhctHoiThos;
    
    //public String lsttiengho;
    //public String lsttienghoTen;
    //public String lsttienghoHienthi;
    public List<EmrDmContent> emrDmYhctTiengHos;
    
    //public String lstmuicothe;
    //public String lstmuicotheTen;
    //public String lstmuicotheHienthi;
    public List<EmrDmContent> emrDmYhctMuiCoThes;
    
    //public String lstchatthaibenhly;
    //public String lstchatthaibenhlyTen;
    //public String lstchatthaibenhlyHienthi;
    public List<EmrDmContent> emrDmYhctChatThaiBenhLys;
    
    public String amthanho;
    public String amthanhnac;
    public String motavawnchan;
}
