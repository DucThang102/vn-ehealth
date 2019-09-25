package vn.ehealth.emr;

import lombok.Data;

@Data
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
