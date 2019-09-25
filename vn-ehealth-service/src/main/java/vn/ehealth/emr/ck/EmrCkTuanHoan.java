package vn.ehealth.emr.ck;

import lombok.Data;

@Data
public class EmrCkTuanHoan {

    public int idhsba;
    public Integer idchuyenkhoa;
    
    public Boolean timro;
    
    public Boolean timmo;
    
    public Boolean timgallop;
    
    public Boolean timamthoi;
    
    public String timchitiet;
    
    public Boolean tinhmachconoi;
    
    public Integer thoigiandaymaomach;
    
    public Boolean vamohoi;
    
    public Boolean danoibong;
    
    public String roiloantim;
    
    // Add moi 13/04/2015
    public String nhiptim;
}
