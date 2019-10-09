package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_huong_dieu_tri_huyet_hoc")
public class EmrCkHuongDieuTriHuyetHoc {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;    
    @Column public Integer hongcauSolan;
    @Column public Double hongcauTongluong;
    @Column public Integer hongcauruaSolan;
    @Column public Double hongcauruaTongluong;
    @Column public Integer tieucauSolan;
    @Column public Double tieucauTongluong;
    @Column public Integer bachcauSolan;
    @Column public Double bachcauTongluong;
    @Column public Integer huyettuongSolan;
    @Column public Double huyettuongTongluong;
    @Column public Integer huyettuongtuoiSolan;
    @Column public Double huyettuongtuoiTongluong;
    @Column public Integer tualanhSolan;
    @Column public Double tualanhTongluong;
    @Column public Integer mautoanphanSolan;
    @Column public Double mautoanphanTongluong;
    @Column public Integer phanungtruyenmau;
}
