package vn.ehealth.emr;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrYhctBenhanVongChan {

    //public String lsthinhthai;
    //public String lsthinhthaiTen;
    //public String lsthinhthaiHienthi;
    public List<EmrDmContent> emrDmYhctHinhThais;
    
    //public String lstthan;
    //public String lstthanTen;
    //public String lstthanHienthi;
    public List<EmrDmContent> emrDmYhctThans;
    
    //public String lstsac;
    //public String lstsacTen;
    //public String lstsacHienthi;
    public List<EmrDmContent> emrDmYhctSacs;
    
    //public String lsttrach;
    //public String lsttrachTen;
    //public String lsttrachHienthi;
    public List<EmrDmContent> emrDmYhctTrachs;
    
    //public String lstchatluoi;
    //public String lstchatluoiTen;
    //public String lstchatluoiHienthi;
    public List<EmrDmContent> emrDmYhctChatLuois;
    
    //public String lstsacluoi;
    //public String lstsacluoiTen;
    //public String lstsacluoiHienthi;
    public List<EmrDmContent> emrDmYhctSacLuois;
    
    //public String lstreuluoi;
    //public String lstreuluoiTen;
    //public String lstreuluoiHienthi;
    public List<EmrDmContent> emrDmYhctReuLuois;
    
    public String motavongchan;    
    
}
