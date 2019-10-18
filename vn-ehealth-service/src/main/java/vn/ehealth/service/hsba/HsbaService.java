package vn.ehealth.service.hsba;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.ehealth.emr.*;
import vn.ehealth.emr.ck.*;
import vn.ehealth.repository.*;
import vn.ehealth.service.Constants.TRANGTHAI_HOSO;
import vn.ehealth.emr.file.*;
import vn.ehealth.emr.utils.FieldUtil;
import vn.ehealth.emr.utils.JasperUtils;

@Service
public class HsbaService {
    
    JasperUtils jasperUtils = new JasperUtils();
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired EmrBenhNhanRepository emrBenhNhanRepository;
    @Autowired EmrQuanLyNguoiBenhRepository emrQuanLyNguoiBenhRepository;
    @Autowired EmrChanDoanRepository emrChanDoanRepository;
    @Autowired EmrYhctChanDoanRepository emrYhctChanDoanRepository;
    @Autowired EmrTinhTrangRaVienRepository emrTinhTrangRaVienRepository;
    @Autowired EmrTongKetRaVienRepository emrTongKetRaVienRepository;
    @Autowired EmrTongKetSanKhoaRepository emrTongKetSanKhoaRepository;
    @Autowired EmrCkTienSuSanKhoaRepository emrCkTienSuSanKhoaRepository;
    @Autowired EmrBenhAnRepository emrBenhAnRepository;
    @Autowired EmrVaoKhoaRespository emrVaoKhoaRespository;    
    @Autowired EmrChamSocRepository emrChamSocRepository;
    @Autowired EmrDieuTriRepository emrDieuTriRepository;
    @Autowired EmrChucNangSongRepository emrChucNangSongRepository;
    @Autowired EmrHoiChanRepository emrHoiChanRepository;
    @Autowired EmrYhctBenhAnRepository emrYhctBenhAnRepository;
    @Autowired EmrYhctBenhanVaanChanRepository emrYhctBenhanVaanChanRepository;
    @Autowired EmrYhctBenhanThietChanRepository emrYhctBenhanThietChanRepository;
    @Autowired EmrYhctBenhanVongChanRepository emrYhctBenhanVongChanRepository;
    @Autowired EmrYhctBenhanVawnChanRepository emrYhctBenhanVawnChanRepository;
    @Autowired EmrDanhSachHoSoBenhAnRepository emrDanhSachHoSoBenhAnRepository;    
    @Autowired EmrChanDoanHinhAnhRepository emrChanDoanHinhAnhRepository;
    @Autowired EmrDonThuocRepository emrDonThuocRepository;
    @Autowired EmrDonThuocChiTietRepository emrDonThuocChiTietRepository;
    @Autowired EmrYhctDonThuocRepository emrYhctDonThuocRepository;
    @Autowired EmrYhctDonThuocChiTietRepository emrYhctDonThuocChiTietRepository;
    @Autowired EmrHinhAnhTonThuongRepository emrHinhAnhTonThuongRepository;
    @Autowired EmrThamDoChucNangRepository emrThamDoChucNangRepository;
    @Autowired EmrYhctNhaBaRepository emrYhctNhaBaRepository;
    @Autowired EmrYhctNhaBaGhiChuRepository emrYhctNhaBaGhiChuRepository;
    
    
    static Logger logger = LoggerFactory.getLogger(HsbaService.class);
    
    <T> List<T> getRecords(Class<T> cl, String table, String fieldName, Integer fieldValue, boolean checkDaXoa) {
        var result = new ArrayList<T>();
        
        
        if(fieldValue != null) {
            var builder = new StringBuilder();
            builder.append("SELECT * FROM ").append(table).append(" WHERE ").append(fieldName).append("=?");
            if(checkDaXoa) {
                builder.append(" AND (daxoa IS NULL OR daxoa = FALSE)");
            }
            var sql = builder.toString();
            var records = jdbcTemplate.queryForList(sql, new Object[] {fieldValue});
            for(var record : records) {
                try {
                    var constructor = cl.getConstructor();
                    var obj = constructor.newInstance();
                    FieldUtil.setFields(obj, record);
                    result.add(obj);
                }catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    logger.error("Cannot set fields for object : ", e);
                }
            }
        }
        return result;
    }
    
    <T> Optional<T> getRecord(Class<T> cl, String table, String fieldName, Integer fieldValue, boolean checkDaXoa) {
        var records = getRecords(cl, table, fieldName, fieldValue, checkDaXoa);
        
        if(records.size() > 0) {
            return Optional.of(records.get(0));
        }else {
            return Optional.ofNullable(null);
        }
        
    }
    
    public @Nonnull EmrDm getEmrDm(String table, Integer id) {
        var emrDm = getRecord(EmrDm.class, table, "id", id, false);
        return emrDm.orElse(new EmrDm());
    }    
    
    public @Nonnull EmrCoSoKhamBenh getCoSoKhamBenh() {
        var record = jdbcTemplate.queryForMap("SELECT * FROM emr_co_so_kham_benh LIMIT 1");
        var coSoKhamBenh = new EmrCoSoKhamBenh();
        FieldUtil.setFields(coSoKhamBenh, record);
        return coSoKhamBenh;
    }
    
    public Optional<EmrBenhNhan> getEmrBenhNhanById(Integer id) {
        var emrBenhNhan = emrBenhNhanRepository.findById(id);
        
        emrBenhNhan.ifPresent(x -> {
            x.emrDmGioiTinh = getEmrDm("emr_dm_gioi_tinh", x.idgioitinh);
            x.emrDmDanToc = getEmrDm("emr_dm_dan_toc", x.iddantoc);
            x.emrDmQuocGia = getEmrDm("emr_dm_quoc_gia", x.idquocgia);
            x.emrDmNgheNghiep = getEmrDm("emr_dm_nghe_nghiep", x.idnghenghiep);
            x.emrDmPhuongXa = getEmrDm("emr_dm_don_vi_hanh_chinh", x.idphuongxa);
            x.emrDmQuanHuyen = getEmrDm("emr_dm_don_vi_hanh_chinh", x.idquanhuyen);
            x.emrDmTinhThanh = getEmrDm("emr_dm_don_vi_hanh_chinh", x.idtinhthanh);
            x.emrDmNgheNghiepBo = getEmrDm("emr_dm_nghe_nghiep", x.idnghebo);
            x.emrDmNgheNghiepMe = getEmrDm("emr_dm_nghe_nghiep", x.idngheme);
        });
                
        return emrBenhNhan;
    }    
    
    Optional<EmrQuanLyNguoiBenh> getEmrQuanLyNguoiBenh(int idhsba) {
        var emrQuanLyNguoiBenh = emrQuanLyNguoiBenhRepository.findById(idhsba);
        
        emrQuanLyNguoiBenh.ifPresent(x -> {
            x.emrDmCoSoKhamBenh = getEmrDm("emr_dm_co_so_kham_benh", x.idnoichuyenden);
            x.emrDmLoaiChuyenVien = getEmrDm("emr_dm_loai_chuyen_vien", x.idloaichuyenvien);
            x.emrDmLoaiDoiTuongTaiChinh = getEmrDm("emr_dm_loai_doi_tuong_tai_chinh", x.iddoituongtaichinh);
            x.emrDmLoaiRaVien = getEmrDm("emr_dm_loai_ra_vien", x.idloairavien);
            x.emrDmLoaiVaoVien = getEmrDm("emr_dm_loai_vao_vien", x.idloaivaovien);
            x.emrDmNoiGioiThieu = getEmrDm("emr_dm_noi_gioi_thieu", x.idnoigioithieu);
            x.emrDmNoiTrucTiepVao = getEmrDm("emr_dm_noi_truc_tiep_vao", x.idnoitructiepvao);            
        });
        
        return emrQuanLyNguoiBenh;
    }
    
    Optional<EmrChanDoan> getEmrChanDoan(int idhsba) {
        
        var emrChanDoan = emrChanDoanRepository.findById(idhsba);
        
        emrChanDoan.ifPresent(x -> {
            x.emrDmLyDoTaiBienBienChung =  getEmrDm("emr_dm_ly_do_tai_bien_bien_chung", x.idlydotbbc);
            x.emrDmMaBenhChandoandieutri =  getEmrDm("emr_dm_ma_benh", x.idicdChandoandieutri);
            x.emrDmMaBenhChandoankkb =  getEmrDm("emr_dm_ma_benh", x.idicdChandoankkb);
            x.emrDmMaBenhChandoannoiden =  getEmrDm("emr_dm_ma_benh", x.idicdChandoannoiden);
            x.emrDmMaBenhChandoanravienchinh =  getEmrDm("emr_dm_ma_benh", x.idicdChandoanravienchinh);
            x.emrDmMaBenhChandoanravienkemtheo =  getEmrDm("emr_dm_ma_benh", x.idicdChandoanravienkemtheo);
            x.emrDmMaBenhChandoanraviennguyennhan =  getEmrDm("emr_dm_ma_benh", x.idicdChandoanraviennguyennhan);
            x.emrDmMaBenhChandoansaupt =  getEmrDm("emr_dm_ma_benh", x.idicdChandoansaupt);
            x.emrDmMaBenhChandoantruocpt =  getEmrDm("emr_dm_ma_benh", x.idicdChandoantruocpt);
        });

        return emrChanDoan;
    }
    
    Optional<EmrYhctChanDoan> getEmrYhctChanDoan(int idhsba) {
        var emrYhctChanDoan = emrYhctChanDoanRepository.findById(idhsba);

        emrYhctChanDoan.ifPresent(x -> {
            x.emrDmYhctBenhdanhRavien = getEmrDm("emr_dm_yhct_benh_danh", x.idbenhdanhyhctravien);
            x.emrDmYhctBenhdanhVaovien= getEmrDm("emr_dm_yhct_benh_danh", x.idbenhdanhyhctvaovien);
            x.emrDmYhctBenhdanhVk = getEmrDm("emr_dm_yhct_benh_danh", x.idbenhdanh_vk);
        });
        
        return emrYhctChanDoan;
    }
    
    Optional<EmrTinhTrangRaVien> getEmrTinhTrangRaVien(int idhsba) {
        
        var emrTinhTrangRaVien = emrTinhTrangRaVienRepository.findById(idhsba);
        
        emrTinhTrangRaVien.ifPresent(x -> {
            x.emrDmKetQuaDieuTri = getEmrDm("emr_dm_ket_qua_dieu_tri", x.idketquadieutri);
            x.emrDmKetQuaGiaiPhauBenh = getEmrDm("emr_dm_ket_qua_giai_phau_benh", x.idgiaiphaubenh);
            x.emrDmLyDoTuVong = getEmrDm("emr_dm_ly_do_tu_vong", x.idlydotuvong);
            x.emrDmGiaiphaututhi = getEmrDm("emr_dm_ma_benh", x.idicdGiaiphaututhi);
            x.emrDmLyDoTuVong = getEmrDm("emr_dm_ly_do_tu_vong", x.idlydotuvong);
            x.emrDmNguyennhantuvong = getEmrDm("emr_dm_ma_benh", x.idicdNguyennhantuvong);
            x.emrDmThoiDiemTuVong = getEmrDm("emr_dm_thoi_diem_tu_vong", x.idthoidiemtuvong);
            x.emrDmYhctKetQuaDieuTri = getEmrDm("emr_dm_yhct_ket_qua_dieu_tri", x.idyhctketquadieutri);
        });
        
        return emrTinhTrangRaVien;
    }
    
    Optional<EmrTongKetSanKhoa> getEmrTongKetSanKhoa(int idhsba) {
        var emrTongKetSanKhoa = emrTongKetSanKhoaRepository.findById(idhsba);
        
        emrTongKetSanKhoa.ifPresent(x -> {
            x.emrDmCachDe = getEmrDm("emr_dm_cach_de", x.idcachde);
            x.emrDmTrangThaiAmdao = getEmrDm("emr_dm_trang_thai_mo_de", x.idtrangthaiamdao);
            x.emrDmTrangThaiCoTucung = getEmrDm("emr_dm_trang_thai_mo_de", x.idtrangthaicotucung);
            x.emrDmTrangThaiSinhmon = getEmrDm("emr_dm_trang_thai_mo_de", x.idtrangthaisinhmon);
        });
                
        return emrTongKetSanKhoa;
        
    }
        
    List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKems(String table, int iddk) {
        return getRecords(EmrQuanLyFileDinhKem.class, table, "iddk", iddk, false);
    }
    
    List<EmrChamSoc> getEmrChamSocs(@Nonnull EmrVaoKhoa emrVaoKhoa) {
        var lst = getRecords(EmrChamSoc.class, "emr_cham_soc", "idvaokhoa", emrVaoKhoa.id, true);
        
        for(var item : lst) {
            item.emrQuaTrinhChamSocs = getRecords(EmrQuaTrinhChamSoc.class, "emr_qua_trinh_cham_soc", "idchamsoc", item.id, true);
            item.emrQuanLyFileDinhKemChamSocs = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_cham_soc", item.id);
            item.tenKhoa = StringUtils.isEmpty(emrVaoKhoa.tenkhoa) ? emrVaoKhoa.emrDmKhoaDieuTri.ten : emrVaoKhoa.tenkhoa;
            item.phong = emrVaoKhoa.phong;
            item.giuong = emrVaoKhoa.giuong;
        }
        
        return lst;        
    }

    List<EmrDieuTri> getEmrDieuTris(@Nonnull EmrVaoKhoa emrVaoKhoa) {
        var lst = getRecords(EmrDieuTri.class, "emr_dieu_tri", "idvaokhoa", emrVaoKhoa.id, true);
        
        for(var item : lst) {            
            item.emrQuaTrinhDieuTris = getRecords(EmrQuaTrinhDieuTri.class, "emr_qua_trinh_dieu_tri", "iddieutri", item.id, true);
            item.emrQuanLyFileDinhKemDieuTris = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_dieu_tri", item.id);
            item.tenKhoa = StringUtils.isEmpty(emrVaoKhoa.tenkhoa) ? emrVaoKhoa.emrDmKhoaDieuTri.ten : emrVaoKhoa.tenkhoa;
            item.phong = emrVaoKhoa.phong;
            item.giuong = emrVaoKhoa.giuong;
        }
        
        return lst;        
    }
  
    List<EmrChucNangSong> getEmrChucNangSongs(@Nonnull EmrVaoKhoa emrVaoKhoa) {
        var lst = getRecords(EmrChucNangSong.class, "emr_chuc_nang_song", "idvaokhoa", emrVaoKhoa.id, true);
        
        for(var item : lst) {            
            item.emrChucNangSongChiTiets = getRecords(EmrChucNangSongChiTiet.class, "emr_chuc_nang_song_chi_tiet", "idchucnangsong", item.id, true);
            item.emrQuanLyFileDinhKemChucNangSongs = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_chuc_nang_song", item.id);
            item.tenKhoa = StringUtils.isEmpty(emrVaoKhoa.tenkhoa) ? emrVaoKhoa.emrDmKhoaDieuTri.ten : emrVaoKhoa.tenkhoa;
            item.phong = emrVaoKhoa.phong;
            item.giuong = emrVaoKhoa.giuong;
        }
        
        return lst;        
    }
    
    List<EmrHoiDongHoiChan> getEmrHoiDongHoiChans(@Nonnull EmrHoiChan emrHoiChan) {
        var lst = getRecords(EmrHoiDongHoiChan.class, "emr_hoi_dong_hoi_chan", "idhoichan", emrHoiChan.id, true);
        return lst;        
    }

    List<EmrHoiChan> getEmrHoiChans(@Nonnull EmrVaoKhoa emrVaoKhoa) {
        var lst = getRecords(EmrHoiChan.class, "emr_hoi_chan", "idvaokhoa", emrVaoKhoa.id, true);
        
        for(var item : lst) {            
            item.emrHoiDongHoiChans = getEmrHoiDongHoiChans(item);
            item.emrQuanLyFileDinhKemHoiChans = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_hoi_chan", item.id);
            item.tenKhoa = StringUtils.isEmpty(emrVaoKhoa.tenkhoa) ? emrVaoKhoa.emrDmKhoaDieuTri.ten : emrVaoKhoa.tenkhoa;
            item.phong = emrVaoKhoa.phong;
            item.giuong = emrVaoKhoa.giuong;
        }
        
        return lst;        
    }
    
    public List<EmrVaoKhoa> getEmrVaoKhoas(int idhsba) {        
        var lst = getRecords(EmrVaoKhoa.class, "emr_vao_khoa", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrDmKhoaDieuTri = getEmrDm("emr_dm_khoa_dieu_tri", item.idkhoadieutri);
            item.emrChamSocs = getEmrChamSocs(item);
            item.emrChucNangSongs = getEmrChucNangSongs(item);
            item.emrDieuTris = getEmrDieuTris(item);
            item.emrHoiChans = getEmrHoiChans(item);
        }
        
        Collections.sort(lst, new Comparator<EmrVaoKhoa>() {

            @Override
            public int compare(EmrVaoKhoa vk1, EmrVaoKhoa vk2) {
                return (int) (vk1.ngaygiovaokhoa.getTime() - vk2.ngaygiovaokhoa.getTime());
            }
        });
        
        return lst;
    }
    
    public List<EmrQuaTrinhSuDungThuoc> getEmrQuaTrinhSuDungThuocs(int idhsba) {
        var lst = getRecords(EmrQuaTrinhSuDungThuoc.class, "emr_qua_trinh_su_dung_thuoc", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrDmThuoc = getEmrDm("emr_dm_thuoc", item.idthuoc);
        }
        
        return lst;
    }    
    
    List<EmrHinhAnhTonThuong> getEmrHinhAnhTonThuongs(int idhsba) {
        var lst = getRecords(EmrHinhAnhTonThuong.class, "emr_hinh_anh_ton_thuong", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrQuanLyFileDinhKemHatts = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_hatt", item.id);
        }
        
        return lst;
    }

    List<EmrGiaiPhauBenh> getEmrGiaiPhauBenhs(int idhsba) {
        var lst = getRecords(EmrGiaiPhauBenh.class, "emr_giai_phau_benh", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrQuanLyFileDinhKemGpbs = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_gpb", item.id);
            item.emrDmGiaiPhauBenh = getEmrDm("emr_dm_giai_phau_benh", item.iddichvugiaiphau);
            item.emrDmKetQuaGiaiPhauBenh = getEmrDm("emr_dm_ket_qua_giai_phau_benh", item.idloaigiaiphau);
            item.emrDmLoaiGiaiPhauBenh = getEmrDm("emr_dm_loai_giai_phau_benh", item.idloaigiaiphau);
            item.emrDmViTriLayMau = getEmrDm("emr_dm_vi_tri_lay_mau", item.idvitrilaymau);
        }
        
        return lst;
    }
    
    List<EmrThamDoChucNang> getEmrThamDoChucNangs(int idhsba) {
        var lst = getRecords(EmrThamDoChucNang.class, "emr_tham_do_chuc_nang", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrDmLoaiThamDoChucNang = getEmrDm("emr_dm_loai_tham_do_chuc_nang", item.idloaithamdochucnang);
            item.emrDmThamDoChucNang = getEmrDm("emr_dm_tham_do_chuc_nang", item.idthamdochucnang);
            item.emrQuanLyFileDinhKemTdcns = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_tdcn", item.id);
        }
        
        return lst;
    }
    
    List<EmrPhauThuatThuThuat> getEmrPhauThuatThuThuats(int idhsba) {
        var lst = getRecords(EmrPhauThuatThuThuat.class, "emr_phau_thuat_thu_thuat", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrHoiDongPttts = getRecords(EmrHoiDongPttt.class, "emr_hoi_dong_pttt", "idpttt", item.id, true);
            item.emrHoiDongPttts.forEach(x -> {
                x.emrDmVaiTro = new EmrDm();
                if(x.idvaitro != null) {                    
                    x.emrDmVaiTro.ten = jasperUtils.getVaiTroPTTT(x.idvaitro);
                }
            });
            item.emrDmMaBenhChandoansau = getEmrDm("emr_dm_ma_benh", item.idicdchandoansau);
            item.emrDmMaBenhChandoantruoc = getEmrDm("emr_dm_ma_benh", item.idicdchandoantruoc);
            item.emrDmPhauThuThuat = getEmrDm("emr_dm_phau_thu_thuat", item.idphauthuat);
            item.emrQuanLyFileDinhKemPttt = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_pttt", item.id);
        }
        
        return lst;        
    }
        
    List<EmrChanDoanHinhAnh> getEmrChanDoanHinhAnhs(int idhsba) {
        var lst = getRecords(EmrChanDoanHinhAnh.class, "emr_chan_doan_hinh_anh", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrDmChanDoanHinhAnh = getEmrDm("emr_dm_chan_doan_hinh_anh", item.iddichvuchandoan);
            item.emrDmLoaiChanDoanHinhAnh = getEmrDm("emr_dm_loai_chan_doan_hinh_anh", item.idloaichandoan);
            item.emrQuanLyFileDinhKemCdha = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_cdha", item.id);
        }
        
        return lst;        
    }
    
    List<EmrDonThuocChiTiet> getEmrDonThuocChiTiets(int iddonthuoc) {
        var lst = getRecords(EmrDonThuocChiTiet.class, "emr_don_thuoc_chi_tiet", "iddonthuoc", iddonthuoc, true);
        
        for(var item : lst) {            
            item.emrDmDuongDungThuoc = getEmrDm("emr_dm_duong_dung_thuoc", item.idloaiduongdung);
            item.emrDmTanXuatDungThuoc = getEmrDm("emr_dm_tan_xuat_dung_thuoc", item.idtanxuatdung);
            item.emrDmChiDanDungThuoc =  getEmrDm("emr_dm_chi_dan_dung_thuoc", item.idchidandungthuoc);
            item.emrDmThuoc = getEmrDm("emr_dm_thuoc", item.idthuoc);
        }
        
        return lst;
    }
        
    List<EmrDonThuoc> getEmrDonThuocs(int idhsba) {
        var lst = getRecords(EmrDonThuoc.class, "emr_don_thuoc", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrDonThuocChiTiets = getEmrDonThuocChiTiets(item.id);
            item.emrQuanLyFileDinhKemDonThuocs = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_don_thuoc", item.id);            
        }
        
        return lst;        
    }
    
    
    List<EmrXetNghiemKetQua> getEmrXetNghiemKetQuas(int idxetnghiemdichvu) {
        
        var lst = getRecords(EmrXetNghiemKetQua.class, "emr_xet_nghiem_ket_qua", "iddichvuxetnghiem", idxetnghiemdichvu, true);
        
        for(var item : lst) {
            item.emrDmChiSoXetNghiem = getEmrDm("emr_dm_chi_so_xet_nghiem", item.idchisoxetnghiem);
            item.emrDmDichKetQuaXetNghiem = getEmrDm("emr_dm_dich_ket_qua_xet_nghiem", item.idthongdich);
            item.emrDmXetNghiem = getEmrDm("emr_dm_xet_nghiem", item.idxetnghiem);
        }
        
        return lst;
        
    }
    
    List<EmrXetNghiemDichVu> getEmrXetNghiemDichVus(int idxetnghiem) {
        var lst = getRecords(EmrXetNghiemDichVu.class, "emr_xet_nghiem_dich_vu", "idxetnghiem", idxetnghiem, true);
        for(var item : lst) {
            item.emrDmXetNghiem = getEmrDm("emr_dm_xet_nghiem", item.iddmxetnghiem);
            item.emrXetNghiemKetQuas = getEmrXetNghiemKetQuas(item.id);
        }
        return lst;
    }    
    
    List<EmrXetNghiem> getEmrXetNghiems(int idhsba) {
        var lst = getRecords(EmrXetNghiem.class, "emr_xet_nghiem", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrDmLoaiXetNghiem = getEmrDm("emr_dm_loai_xet_nghiem", item.idloaixetnghiem);
            item.emrQuanLyFileDinhKemXn = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_xn", item.id);
            item.emrXetNghiemDichVus = getEmrXetNghiemDichVus(item.id);
        }
        
        return lst;        
    }
    
    Optional<EmrCkTienSuSanKhoa> getEmrCkTienSuSanKhoa(int idhsba) {
        var emrCkTienSuSanKhoa = emrCkTienSuSanKhoaRepository.findById(idhsba);
        emrCkTienSuSanKhoa.ifPresent(x -> {
            x.emrCkTienSuSanKhoaChiTiets = getRecords(EmrCkTienSuSanKhoaChiTiet.class, "emr_ck_tien_su_san_khoa_chi_tiet", "idhsba", idhsba, true);
        });
        
        return emrCkTienSuSanKhoa;
    }
    
    public Optional<EmrYhctBenhAn> getEmrYhctBenhAn(int idhsba) {
        var emrYhctBenhAn = emrYhctBenhAnRepository.findById(idhsba);
        
        emrYhctBenhAn.ifPresent(x -> {
            x.emrDmYhctCheDoChamSoc = getEmrDm("emr_dm_yhct_che_do_cham_soc", x.idchamsoc);
            x.emrYhctBenhanThietChan = emrYhctBenhanThietChanRepository.findById(idhsba).orElse(null);
            x.emrYhctBenhanVaanChan = emrYhctBenhanVaanChanRepository.findById(idhsba).orElse(null);
            x.emrYhctBenhanVawnChan = emrYhctBenhanVawnChanRepository.findById(idhsba).orElse(null);
            x.emrYhctBenhanVongChan = emrYhctBenhanVongChanRepository.findById(idhsba).orElse(null);
        });
        
        return emrYhctBenhAn;
    }
    
    public Optional<EmrBenhAn> getEmrBenhAn(int idhsba) {
        var emrBenhAn = emrBenhAnRepository.findById(idhsba);
        
        emrBenhAn.ifPresent(x -> {
            x.emrCkChanTayMieng = getRecord(EmrCkChanTayMieng.class, "emr_ck_chan_tay_mieng", "idhsba", idhsba, false).orElse(null);
            x.emrCkChucNangSinhHoat = getRecord(EmrCkChucNangSinhHoat.class, "emr_ck_chuc_nang_sinh_hoat", "idhsba", idhsba, false).orElse(null);
            x.emrCkCoXuongKhop = getRecord(EmrCkCoXuongKhop.class, "emr_ck_co_xuong_khop", "idhsba", idhsba, false).orElse(null);
            x.emrCkHoHap = getRecord(EmrCkHoHap.class, "emr_ck_ho_hap", "idhsba", idhsba, false).orElse(null);
            x.emrCkHuongDieuTriHuyetHoc = getRecord(EmrCkHuongDieuTriHuyetHoc.class, "emr_ck_huong_dieu_tri_huyet_hoc", "idhsba", idhsba, false).orElse(null);
            x.emrCkHuongDieuTriTcm = getRecord(EmrCkHuongDieuTriTcm.class, "emr_ck_huong_dieu_tri_tcm", "idhsba", idhsba, false).orElse(null);
            x.emrCkKhamPhuKhoa = getRecord(EmrCkKhamPhuKhoa.class, "emr_ck_kham_phu_khoa", "idhsba", idhsba, false).orElse(null);
            x.emrCkKhamSanKhoa = getRecord(EmrCkKhamSanKhoa.class, "emr_ck_kham_san_khoa", "idhsba", idhsba, false).orElse(null);
            x.emrCkKhamSoSinh = getRecord(EmrCkKhamSoSinh.class, "emr_ck_kham_so_sinh", "idhsba", idhsba, false).orElse(null);
                    
            x.emrCkMat = getRecord(EmrCkMat.class, "emr_ck_mat", "idhsba", idhsba, false).orElse(null);
            x.emrCkMoiSinh = getRecord(EmrCkMoiSinh.class, "emr_ck_moi_sinh", "idhsba", idhsba, false).orElse(null);
            x.emrCkPhuongPhapHoiSinh = getRecord(EmrCkPhuongPhapHoiSinh.class, "emr_ck_phuong_phap_hoi_sinh", "idhsba", idhsba, false).orElse(null);                
            x.emrCkQuaTrinhBenhLyTcm = getRecord(EmrCkQuaTrinhBenhLyTcm.class, "emr_ck_qua_trinh_benh_ly_tcm", "idhsba", idhsba, false).orElse(null);
            x.emrCkQuaTrinhSinhTruong = getRecord(EmrCkQuaTrinhSinhTruong.class, "emr_ck_qua_trinh_sinh_truong", "idhsba", idhsba, false).orElse(null);
            x.emrCkSkTinhTrangSanPhu = getRecord(EmrCkSkTinhTrangSanPhu.class, "emr_ck_sk_tinh_trang_san_phu", "idhsba", idhsba, false).orElse(null);
            x.emrCkTamThan = getRecord(EmrCkTamThan.class, "emr_ck_tam_than", "idhsba", idhsba, false).orElse(null);
            x.emrCkThanKinh = getRecord(EmrCkThanKinh.class, "emr_ck_than_kinh", "idhsba", idhsba, false).orElse(null);
            x.emrCkTiemChung = getRecord(EmrCkTiemChung.class, "emr_ck_tiem_chung", "idhsba", idhsba, false).orElse(null);
            x.emrCkTienSuBanThanSanKhoa = getRecord(EmrCkTienSuBanThanSanKhoa.class, "emr_ck_tien_su_ban_than_san_khoa", "idhsba", idhsba, false).orElse(null);
            x.emrCkTienSuGiaDinh = getRecord(EmrCkTienSuGiaDinh.class, "emr_ck_tien_su_gia_dinh", "idhsba", idhsba, false).orElse(null);
            x.emrCkTienSuPhuKhoa = getRecord(EmrCkTienSuPhuKhoa.class, "emr_ck_tien_su_phu_khoa", "idhsba", idhsba, false).orElse(null);
            x.emrCkTienSuSanKhoa = getEmrCkTienSuSanKhoa(idhsba).orElse(null);
            x.emrCkTieuHoa = getRecord(EmrCkTieuHoa.class, "emr_ck_tieu_hoa", "idhsba", idhsba, false).orElse(null);
            x.emrCkTinhTrangSanPhu = getRecord(EmrCkTinhTrangSanPhu.class, "emr_ck_tinh_trang_san_phu", "idhsba", idhsba, false).orElse(null);
            x.emrCkTinhTrangSoSinh = getRecord(EmrCkTinhTrangSoSinh.class, "emr_ck_tinh_trang_so_sinh", "idhsba", idhsba, false).orElse(null);
            x.emrCkToanThan = getRecord(EmrCkToanThan.class, "emr_ck_toan_than", "idhsba", idhsba, false).orElse(null);
            x.emrCkTomTatBenhAnTcm = getRecord(EmrCkTomTatBenhAnTcm.class, "emr_ck_tom_tat_benh_an_tcm", "idhsba", idhsba, false).orElse(null);        
            x.emrCkTuanHoan = getRecord(EmrCkTuanHoan.class, "emr_ck_tuan_hoan", "idhsba", idhsba, false).orElse(null);
            x.emrCkPhuongPhapDieuTriUngBuou = getRecord(EmrCkPhuongPhapDieuTriUngBuou.class, "emr_ck_phuong_phap_dieu_tri_ung_buou", "idhsba", idhsba, false).orElse(null);
            x.emrCkTinhTrangRaVienMat = getRecord(EmrCkTinhTrangRaVienMat.class, "emr_ck_tinh_trang_ra_vien_mat", "idhsba", idhsba, false).orElse(null);
            x.emrDmMaBenhChandoanbenhchinh = getEmrDm("emr_dm_ma_benh", x.idicdChandoanbenhchinh);
            x.emrDmMaBenhChandoankemtheo = getEmrDm("emr_dm_ma_benh", x.idicdChandoankemtheo);
            x.emrDmMaBenhChandoanphanbiet = getEmrDm("emr_dm_ma_benh", x.idicdChandoanphanbiet);
        });
        
        return emrBenhAn;        
    }
    
    List<EmrYhctDonThuocChiTiet> getEmrYhctDonThuocChiTiets(int idyhctdonthuoc) {
        var lst = getRecords(EmrYhctDonThuocChiTiet.class, "emr_yhct_don_thuoc_chi_tiet", "idyhctdonthuoc", idyhctdonthuoc, true);
        
        for(var item : lst) {            
            item.emrDmDuongDungThuoc = getEmrDm("emr_dm_duong_dung_thuoc", item.idloaiduongdung);
            item.emrDmTanXuatDungThuoc = getEmrDm("emr_dm_tan_xuat_dung_thuoc", item.idtanxuatdung);
            item.emrDmChiDanDungThuoc =  getEmrDm("emr_dm_chi_dan_dung_thuoc", item.idchidandungthuoc);
            item.emrDmYhctViThuoc = getEmrDm("emr_dm_yhct_vi_thuoc", item.idvithuoc);
        }
        
        return lst;
    }
    
    List<EmrYhctDonThuoc> getEmrYhctDonThuocs(int idhsba) {
        var lst = (List<EmrYhctDonThuoc>) getRecords(EmrYhctDonThuoc.class, "emr_yhct_don_thuoc", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrYhctDonThuocChiTiets = getEmrYhctDonThuocChiTiets(item.id);
            item.emrQuanLyFileDinhKemDonThuocYhcts = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_don_thuoc_yhct", item.id);
        }
        
        return lst;
    }
        
    public Optional<EmrDanhSachHoSoBenhAn> getEmrDanhSachHoSoBenhAnById(int id) {
        
        var dshsba = emrDanhSachHoSoBenhAnRepository.findById(id);
        
        dshsba.ifPresent(x -> {
            x.emrBenhAn = getEmrBenhAn(id).orElse(null);
            x.emrBenhNhan = getEmrBenhNhanById(x.idbenhnhan).orElse(null);
            x.emrDmLoaiBenhAn = getEmrDm("emr_dm_loai_benh_an", x.idloaibenhan);
            x.emrDmNguondulieu = getEmrDm("emr_dm_tu_sinh", x.idnguondulieu);
            x.emrDmTrangthai = getEmrDm("emr_dm_tu_sinh", x.idtrangthai);
            
            x.emrQuanLyNguoiBenh = getEmrQuanLyNguoiBenh(id).orElse(null);
            x.emrTongKetRaVien = emrTongKetRaVienRepository.findById(id).orElse(null);
            x.emrChanDoan = getEmrChanDoan(id).orElse(null);
                        
            x.emrTinhTrangRaVien = getEmrTinhTrangRaVien(id).orElse(null);
            x.emrTongKetSanKhoa = getEmrTongKetSanKhoa(id).orElse(null);
            x.emrVaoKhoas = getEmrVaoKhoas(id).toArray(new EmrVaoKhoa[0]);
            x.emrQuaTrinhSuDungThuocs = getEmrQuaTrinhSuDungThuocs(id);
            x.emrHinhAnhTonThuongs = getEmrHinhAnhTonThuongs(id);
            x.emrGiaiPhauBenhs = getEmrGiaiPhauBenhs(id);
            x.emrThamDoChucNangs = getEmrThamDoChucNangs(id);
            x.emrPhauThuatThuThuats = getEmrPhauThuatThuThuats(id).toArray(new EmrPhauThuatThuThuat[0]);
            x.emrChanDoanHinhAnhs = getEmrChanDoanHinhAnhs(id);
            x.emrDonThuocs = getEmrDonThuocs(id);
            x.emrXetNghiems = getEmrXetNghiems(id);
            x.emrQuanLyFileDinhKemBenhAn = getRecords(EmrQuanLyFileDinhKemBenhAn.class, "emr_quan_ly_file_dinh_kem_benhan", "idhsba", id, false);
            x.emrYhctBenhAn = getEmrYhctBenhAn(id).orElse(null);
            x.emrYhctChanDoan = getEmrYhctChanDoan(id).orElse(null);
            x.emrYhctDonThuocs = getEmrYhctDonThuocs(id);
            x.emrYhctNhaBa = emrYhctNhaBaRepository.findById(id).orElse(null);
            x.emrYhctNhaBaGhiChus = getRecords(EmrYhctNhaBaGhiChu.class, "emr_yhct_ghi_chu_nha_ba", "idhsba", id, true);
            
        });        
        
        return dshsba;
    }
    
    int getDmIdByMa(String table, String ma) {
        var lst = jdbcTemplate.queryForList("SELECT id FROM " + table + " WHERE ma=?", new Object[] {ma}, Integer.class);
        if(lst.size() > 0) {
            return (int) lst.get(0);
        }
        return 0;
    }
    
    @Nonnull EmrBenhAn saveEmrBenhAn(int idhsba, @Nonnull EmrBenhAn emrBenhAn) {        
        emrBenhAn.idhsba = idhsba;
        
        var optEmrBenhAn = Optional.of(emrBenhAn);
        
        emrBenhAn.idicdChandoanbenhchinh = optEmrBenhAn.map(x -> x.emrDmMaBenhChandoanbenhchinh)
                                            .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrBenhAn.idicdChandoankemtheo = optEmrBenhAn.map(x -> x.emrDmMaBenhChandoankemtheo)
                                            .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrBenhAn.idicdChandoanphanbiet = optEmrBenhAn.map(x -> x.emrDmMaBenhChandoanphanbiet)
                                            .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        return emrBenhAnRepository.save(emrBenhAn);
    }
    
    @Nonnull EmrYhctBenhAn saveEmrYhctBenhAn(int idhsba, @Nonnull EmrYhctBenhAn emrYhctBenhAn) {
        emrYhctBenhAn.idhsba = idhsba;
        
        var optEmrYhctBenhAnh = Optional.of(emrYhctBenhAn);
        
        emrYhctBenhAn.idchamsoc = optEmrYhctBenhAnh.map(x -> x.emrDmYhctCheDoChamSoc)
                                                    .map(x -> getDmIdByMa("emr_dm_yhct_che_do_cham_soc", x.ma)).orElse(0);
        
        if(emrYhctBenhAn.emrYhctBenhanThietChan != null) {
            emrYhctBenhAn.emrYhctBenhanThietChan.idhsba = idhsba;
            emrYhctBenhAn.emrYhctBenhanThietChan = emrYhctBenhanThietChanRepository.save(emrYhctBenhAn.emrYhctBenhanThietChan);
        }
        
        if(emrYhctBenhAn.emrYhctBenhanVaanChan != null) {
            emrYhctBenhAn.emrYhctBenhanVaanChan.idhsba = idhsba;
            emrYhctBenhAn.emrYhctBenhanVaanChan = emrYhctBenhanVaanChanRepository.save(emrYhctBenhAn.emrYhctBenhanVaanChan);
        }
        
        if(emrYhctBenhAn.emrYhctBenhanVawnChan != null) {
            emrYhctBenhAn.emrYhctBenhanVawnChan.idhsba = idhsba;
            emrYhctBenhAn.emrYhctBenhanVawnChan = emrYhctBenhanVawnChanRepository.save(emrYhctBenhAn.emrYhctBenhanVawnChan);
        }
        
        if(emrYhctBenhAn.emrYhctBenhanVongChan != null) {
            emrYhctBenhAn.emrYhctBenhanVongChan.idhsba = idhsba;
            emrYhctBenhAn.emrYhctBenhanVongChan = emrYhctBenhanVongChanRepository.save(emrYhctBenhAn.emrYhctBenhanVongChan);
        }
        
        return emrYhctBenhAnRepository.save(emrYhctBenhAn);
    }
    
    
    @Nonnull EmrChanDoan saveEmrChanDoan(int idhsba, @Nonnull EmrChanDoan emrChanDoan) {
               
        emrChanDoan.idhsba = idhsba;
        
        var optEmrChanDoan = Optional.of(emrChanDoan);
        
        emrChanDoan.idlydotbbc = optEmrChanDoan.map(x -> x.emrDmLyDoTaiBienBienChung)
                                                .map(x -> getDmIdByMa("emr_dm_ly_do_tai_bien_bien_chung", x.ma)).orElse(0);
        
        emrChanDoan.idicdChandoandieutri = optEmrChanDoan.map(x -> x.emrDmMaBenhChandoandieutri)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrChanDoan.idicdChandoankkb = optEmrChanDoan.map(x -> x.emrDmMaBenhChandoankkb)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrChanDoan.idicdChandoannoiden = optEmrChanDoan.map(x -> x.emrDmMaBenhChandoannoiden)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrChanDoan.idicdChandoanravienchinh = optEmrChanDoan.map(x -> x.emrDmMaBenhChandoanravienchinh)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrChanDoan.idicdChandoanravienkemtheo = optEmrChanDoan.map(x -> x.emrDmMaBenhChandoanravienkemtheo)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrChanDoan.idicdChandoanraviennguyennhan = optEmrChanDoan.map(x -> x.emrDmMaBenhChandoanraviennguyennhan)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrChanDoan.idicdChandoansaupt = optEmrChanDoan.map(x -> x.emrDmMaBenhChandoansaupt)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrChanDoan.idicdChandoantruocpt = optEmrChanDoan.map(x -> x.emrDmMaBenhChandoantruocpt)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        
        return emrChanDoanRepository.save(emrChanDoan);
    }
    
    @Nonnull EmrYhctChanDoan saveEmrYhctChanDoan(int idhsba, @Nonnull EmrYhctChanDoan emrYhctChanDoan) {
        
        emrYhctChanDoan.idhsba = idhsba;
        
        var optEmrYhctChanDoan = Optional.of(emrYhctChanDoan);
        
        emrYhctChanDoan.idbenhdanh_vk = optEmrYhctChanDoan.map(x -> x.emrDmYhctBenhdanhVk)
                                            .map(x -> getDmIdByMa("emr_dm_yhct_benh_danh", x.ma)).orElse(0);
        
        emrYhctChanDoan.idbenhdanhyhctravien = optEmrYhctChanDoan.map(x -> x.emrDmYhctBenhdanhRavien)
                                            .map(x -> getDmIdByMa("emr_dm_yhct_benh_danh", x.ma)).orElse(0);
        
        emrYhctChanDoan.idbenhdanhyhctvaovien = optEmrYhctChanDoan.map(x -> x.emrDmYhctBenhdanhVaovien)
                                            .map(x -> getDmIdByMa("emr_dm_yhct_benh_danh", x.ma)).orElse(0);
        
        return emrYhctChanDoanRepository.save(emrYhctChanDoan);
    }
    
    void saveEmrChanDoanHinhAnhs(int idhsba, @Nonnull List<EmrChanDoanHinhAnh> emrChanDoanHinhAnhs) {
        for(int i = 0; i < emrChanDoanHinhAnhs.size(); i++) {
            var item = emrChanDoanHinhAnhs.get(i);
            
            item.idhsba = idhsba;
            
            var optItem = Optional.of(item);
            
            item.iddichvuchandoan = optItem.map(x -> x.emrDmChanDoanHinhAnh)
                                           .map(x -> getDmIdByMa("emr_dm_chan_doan_hinh_anh", x.ma)).orElse(0);
            
            item.idloaichandoan = optItem.map(x -> x.emrDmLoaiChanDoanHinhAnh)
                                           .map(x -> getDmIdByMa("emr_dm_loai_chan_doan_hinh_anh", x.ma)).orElse(0);
            
            emrChanDoanHinhAnhs.set(i, emrChanDoanHinhAnhRepository.save(item)); 
        }             
    }
    
    void saveEmrDonThuocChiTiets(int idhsba, @Nonnull List<EmrDonThuocChiTiet> emrDonThuocChiTiets) {
        for(int i = 0; i < emrDonThuocChiTiets.size(); i++) {
            var item = emrDonThuocChiTiets.get(i);
            item.id = idhsba;
            
            var optItem = Optional.of(item);
            
            item.idchidandungthuoc = optItem.map(x -> x.emrDmChiDanDungThuoc)
                                            .map(x -> getDmIdByMa("emr_dm_chi_dan_dung_thuoc", x.ma)).orElse(0);
            
            item.idloaiduongdung = optItem.map(x -> x.emrDmChiDanDungThuoc)
                                            .map(x -> getDmIdByMa("emr_dm_duong_dung_thuoc", x.ma)).orElse(0);
            
            item.idtanxuatdung = optItem.map(x -> x.emrDmChiDanDungThuoc)
                                            .map(x -> getDmIdByMa("emr_dm_tan_xuat_dung_thuoc", x.ma)).orElse(0);
            
            emrDonThuocChiTiets.set(i, emrDonThuocChiTietRepository.save(item));
        }
    }
    
    void saveEmrYhctDonThuocs(int idhsba, @Nonnull List<EmrYhctDonThuoc> emrYhctDonThuocs) {
        for(int i = 0; i < emrYhctDonThuocs.size(); i++) {
            var item = emrYhctDonThuocs.get(i);
            
            item.idhsba = idhsba;
            
            if(item.emrYhctDonThuocChiTiets != null) {
                saveEmrYhctDonThuocChiTiets(idhsba, item.emrYhctDonThuocChiTiets);                
            }
            
            emrYhctDonThuocs.set(i, emrYhctDonThuocRepository.save(item));
        }
    }
    
    void saveEmrYhctDonThuocChiTiets(int idhsba, @Nonnull List<EmrYhctDonThuocChiTiet> emrYhctDonThuocChiTiets) {
        for(int i = 0; i < emrYhctDonThuocChiTiets.size(); i++) {
            var item = emrYhctDonThuocChiTiets.get(i);
            
            item.id = idhsba;
            
            var optItem = Optional.of(item);
            
            item.idchidandungthuoc = optItem.map(x -> x.emrDmChiDanDungThuoc)
                                            .map(x -> getDmIdByMa("emr_dm_chi_dan_dung_thuoc", x.ma)).orElse(0);
            
            item.idloaiduongdung = optItem.map(x -> x.emrDmChiDanDungThuoc)
                                            .map(x -> getDmIdByMa("emr_dm_duong_dung_thuoc", x.ma)).orElse(0);
            
            item.idtanxuatdung = optItem.map(x -> x.emrDmChiDanDungThuoc)
                                            .map(x -> getDmIdByMa("emr_dm_tan_xuat_dung_thuoc", x.ma)).orElse(0);
            
            emrYhctDonThuocChiTiets.set(i, emrYhctDonThuocChiTietRepository.save(item));
        }
    }
    
    void saveEmrDonThuocs(int idhsba, @Nonnull List<EmrDonThuoc> emrDonThuocs) {
        for(int i = 0; i < emrDonThuocs.size(); i++) {
            var item = emrDonThuocs.get(i);
                    
            item.idhsba = idhsba;
            
            if(item.emrDonThuocChiTiets != null) {
                saveEmrDonThuocChiTiets(idhsba, item.emrDonThuocChiTiets);                
            }
            
            emrDonThuocs.set(i, emrDonThuocRepository.save(item));
        }
    }
    
    void saveEmrHinhAnhTonThuongs(int idhsba, @Nonnull List<EmrHinhAnhTonThuong> emrHinhAnhTonThuongs) {
        for(int i = 0; i < emrHinhAnhTonThuongs.size(); i++) {
            var item = emrHinhAnhTonThuongs.get(i);
            item.idhsba = idhsba;
            //TODO: save file
            
            emrHinhAnhTonThuongs.set(i, emrHinhAnhTonThuongRepository.save(item));
        }
    }
    
    @Nonnull EmrQuanLyNguoiBenh saveEmrQuanLyNguoiBenh(int idhsba, @Nonnull EmrQuanLyNguoiBenh emrQuanLyNguoiBenh) {
        emrQuanLyNguoiBenh.idhsba = idhsba;
        var optEmrQuanlyNguoiBenh = Optional.of(emrQuanLyNguoiBenh);
        
        emrQuanLyNguoiBenh.iddoituongtaichinh = optEmrQuanlyNguoiBenh.map(x -> x.emrDmLoaiDoiTuongTaiChinh)
                                                    .map(x -> getDmIdByMa("emr_dm_loai_doi_tuong_tai_chinh", x.ma)).orElse(0);
        
        emrQuanLyNguoiBenh.idloaichuyenvien = optEmrQuanlyNguoiBenh.map(x -> x.emrDmLoaiChuyenVien)
                                                    .map(x -> getDmIdByMa("emr_dm_loai_chuyen_vien", x.ma)).orElse(0);
        
        emrQuanLyNguoiBenh.idloairavien = optEmrQuanlyNguoiBenh.map(x -> x.emrDmLoaiRaVien)
                                                    .map(x -> getDmIdByMa("emr_dm_loai_ra_vien", x.ma)).orElse(0);
        
        emrQuanLyNguoiBenh.idloaivaovien = optEmrQuanlyNguoiBenh.map(x -> x.emrDmLoaiVaoVien)
                                                    .map(x -> getDmIdByMa("emr_dm_loai_vao_vien", x.ma)).orElse(0);
        
        emrQuanLyNguoiBenh.idnoichuyenden = optEmrQuanlyNguoiBenh.map(x -> x.emrDmCoSoKhamBenh)
                                                    .map(x -> getDmIdByMa("emr_dm_co_so_kham_benh", x.ma)).orElse(0);
        
        emrQuanLyNguoiBenh.idnoigioithieu = optEmrQuanlyNguoiBenh.map(x -> x.emrDmNoiGioiThieu)
                                                    .map(x -> getDmIdByMa("emr_dm_noi_gioi_thieu", x.ma)).orElse(0);
                
        emrQuanLyNguoiBenh.idnoitructiepvao = optEmrQuanlyNguoiBenh.map(x -> x.emrDmNoiTrucTiepVao)
                                                    .map(x -> getDmIdByMa("emr_dm_noi_truc_tiep_vao", x.ma)).orElse(0);
        
        return emrQuanLyNguoiBenhRepository.save(emrQuanLyNguoiBenh);
    }
    
    void saveEmrThamDoChucNangs(int idhsba, @Nonnull List<EmrThamDoChucNang> emrThamDoChucNangs) {
        for(int i = 0; i < emrThamDoChucNangs.size(); i++) {
            var item = emrThamDoChucNangs.get(i);
            
            item.idhsba = idhsba;
            var optItem = Optional.of(item);
            
            item.idloaithamdochucnang = optItem.map(x -> x.emrDmLoaiThamDoChucNang)
                                                .map(x -> getDmIdByMa("emr_dm_loai_tham_do_chuc_nang", x.ma)).orElse(0);
            
            item.idthamdochucnang = optItem.map(x -> x.emrDmThamDoChucNang)
                                                .map(x -> getDmIdByMa("emr_dm_tham_do_chuc_nang", x.ma)).orElse(0);
            
            
            emrThamDoChucNangs.set(i, emrThamDoChucNangRepository.save(item));
        }        
    }
    
    @Nonnull EmrTinhTrangRaVien saveEmrTinhTrangRaVien(int idhsba, @Nonnull EmrTinhTrangRaVien emrTinhTrangRaVien) {
        emrTinhTrangRaVien.idhsba = idhsba;                
        
        var optEmrTinhTrangRaVien = Optional.of(emrTinhTrangRaVien);
        
        emrTinhTrangRaVien.idgiaiphaubenh = optEmrTinhTrangRaVien.map(x -> x.emrDmKetQuaGiaiPhauBenh)
                                                .map(x -> getDmIdByMa("emr_dm_ket_qua_giai_phau_benh", x.ma)).orElse(0);
        
        emrTinhTrangRaVien.idicdGiaiphaututhi = optEmrTinhTrangRaVien.map(x -> x.emrDmGiaiphaututhi)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrTinhTrangRaVien.idicdNguyennhantuvong = optEmrTinhTrangRaVien.map(x -> x.emrDmNguyennhantuvong)
                                                .map(x -> getDmIdByMa("emr_dm_ma_benh", x.ma)).orElse(0);
        
        emrTinhTrangRaVien.idketquadieutri = optEmrTinhTrangRaVien.map(x -> x.emrDmKetQuaDieuTri)
                                                .map(x -> getDmIdByMa("emr_dm_ket_qua_dieu_tri", x.ma)).orElse(0);
        
        emrTinhTrangRaVien.idlydotuvong = optEmrTinhTrangRaVien.map(x -> x.emrDmLyDoTuVong)
                                                .map(x -> getDmIdByMa("emr_dm_ly_do_tu_vong", x.ma)).orElse(0);
        
        emrTinhTrangRaVien.idthoidiemtuvong = optEmrTinhTrangRaVien.map(x -> x.emrDmThoiDiemTuVong)
                                                .map(x -> getDmIdByMa("emr_dm_thoi_diem_tu_vong", x.ma)).orElse(0);
        
        emrTinhTrangRaVien.idyhctketquadieutri = optEmrTinhTrangRaVien.map(x -> x.emrDmYhctKetQuaDieuTri)
                                                .map(x -> getDmIdByMa("emr_dm_yhct_ket_qua_dieu_tri", x.ma)).orElse(0);
        
        return emrTinhTrangRaVienRepository.save(emrTinhTrangRaVien);
    }
    
    @Nonnull EmrTongKetRaVien saveEmrTongKetRaVien(int idhsba, @Nonnull EmrTongKetRaVien emrTongKetRaVien) {
        emrTongKetRaVien.idhsba = idhsba;
        return emrTongKetRaVienRepository.save(emrTongKetRaVien);
    }
    
    @Nonnull EmrTongKetSanKhoa saveEmrTongKetSanKhoa(int idhsba, @Nonnull EmrTongKetSanKhoa emrTongKetSanKhoa) {
        emrTongKetSanKhoa.idhsba = idhsba;
        
        var optEmrTongKetSanKhoa = Optional.of(emrTongKetSanKhoa);
        
        emrTongKetSanKhoa.idcachde = optEmrTongKetSanKhoa.map(x -> x.emrDmCachDe)
                                                 .map(x -> getDmIdByMa("emr_dm_cach_de", x.ma)).orElse(0);
        
        emrTongKetSanKhoa.idtrangthaiamdao = optEmrTongKetSanKhoa.map(x -> x.emrDmTrangThaiAmdao)
                                                .map(x -> getDmIdByMa("emr_dm_trang_thai_mo_de", x.ma)).orElse(0);
        
        emrTongKetSanKhoa.idtrangthaicotucung = optEmrTongKetSanKhoa.map(x -> x.emrDmTrangThaiCoTucung)
                                                .map(x -> getDmIdByMa("emr_dm_trang_thai_mo_de", x.ma)).orElse(0);
        
        emrTongKetSanKhoa.idtrangthaisinhmon = optEmrTongKetSanKhoa.map(x -> x.emrDmTrangThaiSinhmon)
                                                .map(x -> getDmIdByMa("emr_dm_trang_thai_mo_de", x.ma)).orElse(0);
        
        return emrTongKetSanKhoaRepository.save(emrTongKetSanKhoa);
    }
    
    void saveEmrChamSocs(int idvk, @Nonnull List<EmrChamSoc> emrChamSocs) {
        for(int i = 0; i < emrChamSocs.size(); i++) {
            var item  = emrChamSocs.get(i);            
            item.idvaokhoa =idvk;            
            emrChamSocs.set(i, emrChamSocRepository.save(item));
        }
        
    }
    
    void saveEmrDieuTris(int idvk, @Nonnull List<EmrDieuTri> emrDieuTris) {
        for(int i = 0; i < emrDieuTris.size(); i++) {
            var item  = emrDieuTris.get(i);            
            item.idvaokhoa =idvk;            
            emrDieuTris.set(i, emrDieuTriRepository.save(item));
        }
        
    }
    
    void saveEmrChucNangSongs(int idvk, @Nonnull List<EmrChucNangSong> emrChucNangSongs) {
        for(int i = 0; i < emrChucNangSongs.size(); i++) {
            var item  = emrChucNangSongs.get(i);            
            item.idvaokhoa =idvk;            
            emrChucNangSongs.set(i, emrChucNangSongRepository.save(item));
        }
        
    }
    
    void saveEmrHoiChans(int idvk, @Nonnull List<EmrHoiChan> emrHoiChans) {
        for(int i = 0; i < emrHoiChans.size(); i++) {
            var item  = emrHoiChans.get(i);            
            item.idvaokhoa =idvk;            
            emrHoiChans.set(i, emrHoiChanRepository.save(item));
        }
        
    }
    
    void saveEmrVaoKhoas(int idhsba, @Nonnull EmrVaoKhoa[] emrVaoKhoas) {
        for(int i = 0; i < emrVaoKhoas.length; i++) {
            var item = emrVaoKhoas[i];
            var optItem = Optional.of(item);
            
            item.idhsba = idhsba;
            item.idkhoadieutri = optItem.map(x -> x.emrDmKhoaDieuTri)
                                        .map(x -> getDmIdByMa("emr_dm_khoa_dieu_tri", x.ma)).orElse(0);
            
            if(item.emrChamSocs != null) {
                saveEmrChamSocs(item.id, item.emrChamSocs);
            }
            
            if(item.emrChucNangSongs != null) {
                saveEmrChucNangSongs(item.id, item.emrChucNangSongs);
            }
            
            if(item.emrDieuTris != null) {
                saveEmrDieuTris(item.id, item.emrDieuTris);
            }
            
            if(item.emrHoiChans != null) {
                saveEmrHoiChans(item.id, item.emrHoiChans);
            }
            
            emrVaoKhoas[i] = emrVaoKhoaRespository.save(item);            
        }
    }    
    
    @Nonnull EmrDanhSachHoSoBenhAn saveEmrDanhSachHoSoBenhAn(@Nonnull EmrDanhSachHoSoBenhAn hsba) {
        var optHsba = Optional.of(hsba);
        
        var benhNhanIdChinh = optHsba.map(x -> x.emrBenhNhan).map(x -> x.iddinhdanhchinh).orElse("");
        var emrBenhNhan = emrBenhNhanRepository.findByIddinhdanhchinh(benhNhanIdChinh);
        
        hsba.idbenhnhan = emrBenhNhan.map(x -> x.id).orElse(0);
        
        hsba.idloaibenhan = optHsba.map(x -> x.emrDmLoaiBenhAn)
                                    .map(x -> getDmIdByMa("emr_dm_loai_benh_an",x.ma)) .orElse(0);
        
        hsba.idtrangthai = TRANGTHAI_HOSO.CHUA_XULY;
        hsba = emrDanhSachHoSoBenhAnRepository.save(hsba);
        
        if(hsba.emrBenhAn != null) {
            saveEmrBenhAn(hsba.id, hsba.emrBenhAn);
        }
        
        if(hsba.emrChanDoan != null) {
            saveEmrChanDoan(hsba.id, hsba.emrChanDoan);
        }
        
        if(hsba.emrChanDoanHinhAnhs != null) {
            saveEmrChanDoanHinhAnhs(hsba.id, hsba.emrChanDoanHinhAnhs);
        }
        
        if(hsba.emrDonThuocs != null) {
            saveEmrDonThuocs(hsba.id, hsba.emrDonThuocs);
        }
        
        if(hsba.emrHinhAnhTonThuongs != null) {
            saveEmrHinhAnhTonThuongs(hsba.id, hsba.emrHinhAnhTonThuongs);
        }
        
        if(hsba.emrQuanLyNguoiBenh != null) {
            saveEmrQuanLyNguoiBenh(hsba.id, hsba.emrQuanLyNguoiBenh);
        }
        
        if(hsba.emrThamDoChucNangs != null) {
            saveEmrThamDoChucNangs(hsba.id, hsba.emrThamDoChucNangs);
        }
        
        if(hsba.emrTinhTrangRaVien != null) {
            saveEmrTinhTrangRaVien(hsba.id, hsba.emrTinhTrangRaVien);
        }
        
        if(hsba.emrTongKetRaVien != null) {
            saveEmrTongKetRaVien(hsba.id, hsba.emrTongKetRaVien);
        }
        
        if(hsba.emrTongKetSanKhoa != null) {
            saveEmrTongKetSanKhoa(hsba.id, hsba.emrTongKetSanKhoa);
        }
        
        if(hsba.emrYhctBenhAn != null) {
            saveEmrYhctBenhAn(hsba.id, hsba.emrYhctBenhAn);
        }
        
        if(hsba.emrYhctDonThuocs != null) {
            saveEmrYhctDonThuocs(hsba.id, hsba.emrYhctDonThuocs);
        }
        
        if(hsba.emrYhctChanDoan != null) {
            saveEmrYhctChanDoan(hsba.id, hsba.emrYhctChanDoan);
        }
        
        if(hsba.emrYhctNhaBa != null) {
            hsba.emrYhctNhaBa.idhsba = hsba.id;
            hsba.emrYhctNhaBa = emrYhctNhaBaRepository.save(hsba.emrYhctNhaBa);            
        }
        
        if(hsba.emrYhctNhaBaGhiChus != null) {
            for(int i = 0; i < hsba.emrYhctNhaBaGhiChus.size(); i++) {
                var item = hsba.emrYhctNhaBaGhiChus.get(i);
                item.idhsba = hsba.id;
                hsba.emrYhctNhaBaGhiChus.set(i, emrYhctNhaBaGhiChuRepository.save(item));
            }
        }
        
        if(hsba.emrVaoKhoas != null) {
            saveEmrVaoKhoas(hsba.id, hsba.emrVaoKhoas);
        }
        return hsba;
    }
}