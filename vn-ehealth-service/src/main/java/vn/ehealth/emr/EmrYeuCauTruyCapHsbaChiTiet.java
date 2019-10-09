package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_yeu_cau_truy_cap_hsba_chi_tiet")
public class EmrYeuCauTruyCapHsbaChiTiet {
    
    @Id public int id;
    //public EmrDanhSachHoSoBenhAn emrDanhSachHoSoBenhAn;
    //public EmrYeuCauTruyCapHsba emrYeuCauTruyCapHsba;
    @Column public Integer idnguoitao;
    @Column public Date ngaytao;
    @Column public Integer idnguoisua;
    @Column public Date ngaysua;
    @Column public Boolean daxoa;

}
