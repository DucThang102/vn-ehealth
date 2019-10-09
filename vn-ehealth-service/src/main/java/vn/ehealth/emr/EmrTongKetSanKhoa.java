package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_tong_ket_san_khoa")
public class EmrTongKetSanKhoa {
    
    @Id
    public int idhsba;
    
    @Column
    public Integer idtrangthaicotucung;
    
    @Transient
    public EmrDm emrDmTrangThaiCoTucung;
    
    @Column
    public Integer idtrangthaiamdao;
    
    @Transient
    public EmrDm emrDmTrangThaiAmdao;
    
    @Column
    public Integer idtrangthaisinhmon;
    
    @Transient
    public EmrDm emrDmTrangThaiSinhmon;
    
    @Column
    public Integer idcachde;
    
    @Transient
    public EmrDm emrDmCachDe;
    
    @Column
    public Date deluc;
    
    @Column
    public Integer apgar01;
    
    @Column
    public Integer apgar05;
    
    @Column
    public Integer apgar10;
    
    @Column
    public String sscannang;
    
    @Column
    public Integer sschieucao;
    
    @Column
    public Integer ssvongdau;
    
    @Column
    public Boolean ssdathai;
    
    @Column
    public Integer sssotrai;
    
    @Column
    public Integer sssogai;
    
    @Column
    public Boolean ssditathaumon;
    
    @Column
    public Boolean ssditatkhac;
    
    @Column
    public String ssmotaditat;
    
    @Column
    public String sstinhtrang;
    
    @Column
    public Boolean sshutdich;
    
    @Column
    public Boolean ssxoaboptim;
    
    @Column
    public Boolean ssthoo2;
    
    @Column
    public Boolean ssnoikhiquan;
    
    @Column
    public Boolean ssbopbongo2;
    
    @Column
    public Boolean sshoisinhkhac;
    
    @Column
    public Date rausoluc;
    
    @Column
    public Boolean somatmang;
    
    @Column
    public Boolean somatmui;
    
    @Column
    public Boolean raubongnon;
    
    @Column
    public Integer thoigiansorau;
    
    @Column
    public Integer chieudairau;
    
    @Column
    public Boolean raucuonso;
    
    @Column
    public Boolean kiemsoattucung;
    
    @Column
    public String lydokiemsoat;
    
    @Column
    public Boolean bocraunhantao;
    
    @Column
    public String lydobocrau;
    
    @Column
    public Boolean chaymausauso;
    
    @Column
    public Integer luongmaumat;
    
    @Column
    public String toantrang;
    
    @Column
    public Integer mach;
    
    @Column
    public Integer nhietdo;
    
    @Column
    public Integer huyetapthap;
    
    @Column
    public Integer huyetapcao;
    
    @Column
    public Integer nhiptho;
    
    @Column
    public String lydocanthiep;
    
    @Column
    public String canthiepkhac;
    
    // Add moi 13/04/2015
    @Column
    public String tennguoitheodoi;
    
    @Column
    public String chucdanhnguoitheodoi;
    
    @Column
    public String motachamsoctresosinh;
    
    @Column
    public Integer loaisorau;
    
    @Column
    public String motasorau;
    
    @Column
    public String motamatmang;
    
    @Column
    public String motamatmui;
    
    @Column
    public String motabanhrau;
    
    @Column
    public String cannangrau;
    
    @Column
    public String luongmaubimat;
    
    @Column
    public String xulysorau;
    
    @Column
    public String motatoantrang;
    
    @Column
    public String lydocanthiepkhac;
    
    @Column
    public String phuongphapkhauloaichi;
    
    @Column
    public Integer somuikhau;
    
    @Column
    public String tennguoimo;
    
    @Column
    public String tennguoidode;
    
    @Column
    public String thoigianchuyenda;
    
    @Column
    public String thoigiantheodoikhoade;
    
    
    @Column(name = "apgar_1p_tim")
    public Integer apgar1pTim;
    
    @Column(name = "apgar_1p_tho")
    public Integer apgar1pTho;
        
    @Column(name = "apgar_1p_mausacda")
    public Integer apgar1pMausacda;
    
    @Column(name = "apgar_1p_truonglucco")
    public Integer apgar1pTruonglucco;
    
    @Column(name = "apgar_1p_phanxa")
    public Integer apgar1pPhanxa;
    
    @Column(name = "apgar_5p_tim")
    public Integer apgar5pTim;
    
    @Column(name = "apgar_5p_tho")
    public Integer apgar5pTho;
    
    @Column(name = "apgar_5p_mausacda")
    public Integer apgar5pMausacda;
    
    @Column(name = "apgar_5p_truonglucco")
    public Integer apgar5pTruonglucco;
    
    @Column(name = "apgar_5p_phanxa")
    public Integer apgar5pPhanxa;
    
    //ngay27/7/2015
    @Column
    public Integer soluongsong;
    
    @Column
    public Integer soluongchet;

}
