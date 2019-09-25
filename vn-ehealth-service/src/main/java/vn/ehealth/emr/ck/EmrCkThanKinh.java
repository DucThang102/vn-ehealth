package vn.ehealth.emr.ck;

import lombok.Data;

@Data
public class EmrCkThanKinh {

    public int idhsba;
    public Integer idchuyenkhoa;
    
    public String daythankinhsonao;
    
    public String daymat;
    
    public String vandong;
    
    public String truonglucco;
    
    public String camgiac;
    
    public String phanxa;
    
    public String roiloanchucnang; 
    
    // Add moi 15/04/2015
    public Integer kichthuocDongtu;
    public String phanxaAnhsang;
    public Boolean coguong;
    public Boolean giatminhluckham;
    public Boolean le;
    public Boolean yeuchi;
    public Boolean thatdieu;
    public Boolean runggiatnhancau;
    public Boolean lietthankinhso;
    public Boolean nguga;
}
