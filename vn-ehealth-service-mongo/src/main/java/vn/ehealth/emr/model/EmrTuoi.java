package vn.ehealth.emr.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrTuoi {

    public String tuoi;
    /*
     * 1. Tuoi
     * 2. Thang tuoi
     * 3. Ngay tuoi
     * */
    public int loaiTuoi;
    
    /*
     * true: ton tai du lieu
     * flase: khong ton tai
     * 
     * */
    public boolean flagTuoi;
}
