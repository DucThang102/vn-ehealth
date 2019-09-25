package vn.ehealth.service.hsba;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.EmrBenhAn;
import vn.ehealth.emr.EmrBenhNhan;
import vn.ehealth.emr.EmrChamSoc;
import vn.ehealth.emr.EmrChanDoan;
import vn.ehealth.emr.EmrChanDoanHinhAnh;
import vn.ehealth.emr.EmrChucNangSong;
import vn.ehealth.emr.EmrChucNangSongChiTiet;
import vn.ehealth.emr.EmrCoSoKhamBenh;
import vn.ehealth.emr.EmrDanhSachHoSoBenhAn;
import vn.ehealth.emr.EmrDieuTri;
import vn.ehealth.emr.EmrDm;
import vn.ehealth.emr.EmrDonThuoc;
import vn.ehealth.emr.EmrDonThuocChiTiet;
import vn.ehealth.emr.EmrGiaiPhauBenh;
import vn.ehealth.emr.EmrHinhAnhTonThuong;
import vn.ehealth.emr.EmrHoiChan;
import vn.ehealth.emr.EmrHoiDongHoiChan;
import vn.ehealth.emr.EmrHoiDongPttt;
import vn.ehealth.emr.EmrPhauThuatThuThuat;
import vn.ehealth.emr.EmrQuaTrinhChamSoc;
import vn.ehealth.emr.EmrQuaTrinhDieuTri;
import vn.ehealth.emr.EmrQuaTrinhSuDungThuoc;
import vn.ehealth.emr.EmrQuanLyNguoiBenh;
import vn.ehealth.emr.EmrThamDoChucNang;
import vn.ehealth.emr.EmrTinhTrangRaVien;
import vn.ehealth.emr.EmrTongKetRaVien;
import vn.ehealth.emr.EmrTongKetSanKhoa;
import vn.ehealth.emr.EmrVaoKhoa;
import vn.ehealth.emr.EmrXetNghiem;
import vn.ehealth.emr.EmrXetNghiemDichVu;
import vn.ehealth.emr.EmrXetNghiemKetQua;
import vn.ehealth.emr.EmrYhctBenhAn;
import vn.ehealth.emr.EmrYhctBenhanThietChan;
import vn.ehealth.emr.EmrYhctBenhanVaanChan;
import vn.ehealth.emr.EmrYhctBenhanVawnChan;
import vn.ehealth.emr.EmrYhctBenhanVongChan;
import vn.ehealth.emr.EmrYhctChanDoan;
import vn.ehealth.emr.EmrYhctDonThuoc;
import vn.ehealth.emr.EmrYhctDonThuocChiTiet;
import vn.ehealth.emr.EmrYhctNhaBa;
import vn.ehealth.emr.EmrYhctNhaBaGhiChu;
import vn.ehealth.emr.ck.EmrCkChanTayMieng;
import vn.ehealth.emr.ck.EmrCkChucNangSinhHoat;
import vn.ehealth.emr.ck.EmrCkCoXuongKhop;
import vn.ehealth.emr.ck.EmrCkHoHap;
import vn.ehealth.emr.ck.EmrCkHuongDieuTriHuyetHoc;
import vn.ehealth.emr.ck.EmrCkHuongDieuTriTcm;
import vn.ehealth.emr.ck.EmrCkKhamPhuKhoa;
import vn.ehealth.emr.ck.EmrCkKhamSanKhoa;
import vn.ehealth.emr.ck.EmrCkKhamSoSinh;
import vn.ehealth.emr.ck.EmrCkMat;
import vn.ehealth.emr.ck.EmrCkMoiSinh;
import vn.ehealth.emr.ck.EmrCkPhuongPhapDieuTriUngBuou;
import vn.ehealth.emr.ck.EmrCkPhuongPhapHoiSinh;
import vn.ehealth.emr.ck.EmrCkQuaTrinhBenhLyTcm;
import vn.ehealth.emr.ck.EmrCkQuaTrinhSinhTruong;
import vn.ehealth.emr.ck.EmrCkSkTinhTrangSanPhu;
import vn.ehealth.emr.ck.EmrCkTamThan;
import vn.ehealth.emr.ck.EmrCkThanKinh;
import vn.ehealth.emr.ck.EmrCkTiemChung;
import vn.ehealth.emr.ck.EmrCkTienSuBanThanSanKhoa;
import vn.ehealth.emr.ck.EmrCkTienSuGiaDinh;
import vn.ehealth.emr.ck.EmrCkTienSuPhuKhoa;
import vn.ehealth.emr.ck.EmrCkTienSuSanKhoa;
import vn.ehealth.emr.ck.EmrCkTienSuSanKhoaChiTiet;
import vn.ehealth.emr.ck.EmrCkTieuHoa;
import vn.ehealth.emr.ck.EmrCkTinhTrangRaVienMat;
import vn.ehealth.emr.ck.EmrCkTinhTrangSanPhu;
import vn.ehealth.emr.ck.EmrCkTinhTrangSoSinh;
import vn.ehealth.emr.ck.EmrCkToanThan;
import vn.ehealth.emr.ck.EmrCkTomTatBenhAnTcm;
import vn.ehealth.emr.ck.EmrCkTuanHoan;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKemBenhAn;
import vn.ehealth.emr.utils.FieldUtil;

@Service
public class HsbaService {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
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
    
    <T> T getRecord(Class<T> cl, String table, String fieldName, Integer fieldValue, boolean checkDaXoa) {
        var records = getRecords(cl, table, fieldName, fieldValue, checkDaXoa);
        
        if(records.size() > 0) {
            return records.get(0);
        }else {
            return null;
        }
        
    }    
    
    <T> T getRecordById(Class<T> cl, String table, Integer id, boolean checkDaXoa) {        
        return getRecord(cl, table, "id", id, checkDaXoa);
    }
    
    public EmrDm getEmrDm(String table, Integer id) {
        return getRecordById(EmrDm.class, table, id, false);
    }    
    
    public EmrCoSoKhamBenh getCoSoKhamBenh() {
        var record = jdbcTemplate.queryForMap("SELECT * FROM emr_co_so_kham_benh LIMIT 1");
        var coSoKhamBenh = new EmrCoSoKhamBenh();
        FieldUtil.setFields(coSoKhamBenh, record);
        return coSoKhamBenh;        
    }
    
    public EmrBenhNhan getEmrBenhNhanById(Integer id) {
        var emrBenhNhan = getRecordById(EmrBenhNhan.class, "emr_benh_nhan", id, true);
        
        if(emrBenhNhan != null) {
            emrBenhNhan.emrDmGioiTinh = getEmrDm("emr_dm_gioi_tinh", emrBenhNhan.idgioitinh);
            emrBenhNhan.emrDmDanToc = getEmrDm("emr_dm_dan_toc", emrBenhNhan.iddantoc);
            emrBenhNhan.emrDmQuocGia = getEmrDm("emr_dm_quoc_gia", emrBenhNhan.idquocgia);
            emrBenhNhan.emrDmNgheNghiep = getEmrDm("emr_dm_nghe_nghiep", emrBenhNhan.idnghenghiep);
            emrBenhNhan.emrDmPhuongXa = getEmrDm("emr_dm_don_vi_hanh_chinh", emrBenhNhan.idphuongxa);
            emrBenhNhan.emrDmQuanHuyen = getEmrDm("emr_dm_don_vi_hanh_chinh", emrBenhNhan.idquanhuyen);
            emrBenhNhan.emrDmTinhThanh = getEmrDm("emr_dm_don_vi_hanh_chinh", emrBenhNhan.idtinhthanh);
            emrBenhNhan.emrDmNgheNghiepBo = getEmrDm("emr_dm_nghe_nghiep", emrBenhNhan.idnghebo);
            emrBenhNhan.emrDmNgheNghiepMe = getEmrDm("emr_dm_nghe_nghiep", emrBenhNhan.idngheme);
        }
                
        return emrBenhNhan;
    }    
    
    EmrQuanLyNguoiBenh getEmrQuanLyNguoiBenh(int idhsba) {
        var emrQuanLyNguoiBenh = getRecord(EmrQuanLyNguoiBenh.class, "emr_quan_ly_nguoi_benh", "idhsba", idhsba, true);
        
        if(emrQuanLyNguoiBenh != null) {
            emrQuanLyNguoiBenh.emrDmCoSoKhamBenh = getEmrDm("emr_dm_co_so_kham_benh", emrQuanLyNguoiBenh.idnoichuyenden);
            emrQuanLyNguoiBenh.emrDmLoaiChuyenVien = getEmrDm("emr_dm_loai_chuyen_vien", emrQuanLyNguoiBenh.idloaichuyenvien);
            emrQuanLyNguoiBenh.emrDmLoaiDoiTuongTaiChinh = getEmrDm("emr_dm_loai_doi_tuong_tai_chinh", emrQuanLyNguoiBenh.iddoituongtaichinh);
            emrQuanLyNguoiBenh.emrDmLoaiRaVien = getEmrDm("emr_dm_loai_ra_vien", emrQuanLyNguoiBenh.idloairavien);
            emrQuanLyNguoiBenh.emrDmLoaiVaoVien = getEmrDm("emr_dm_loai_vao_vien", emrQuanLyNguoiBenh.idloaivaovien);
            emrQuanLyNguoiBenh.emrDmNoiGioiThieu = getEmrDm("emr_dm_noi_gioi_thieu", emrQuanLyNguoiBenh.idnoigioithieu);
            emrQuanLyNguoiBenh.emrDmNoiTrucTiepVao = getEmrDm("emr_dm_noi_truc_tiep_vao", emrQuanLyNguoiBenh.idnoitructiepvao);            
        }
        
        return emrQuanLyNguoiBenh;
    }
    
    EmrTongKetRaVien getEmrTongKetRaVien(int idhsba) {
        
        var emrTongKetRaVien = getRecord(EmrTongKetRaVien.class, "emr_tong_ket_ra_vien", "idhsba", idhsba, true);
        
        if(emrTongKetRaVien != null) {
            emrTongKetRaVien.emrCkPhuongPhapDieuTriUngBuou = getRecord(EmrCkPhuongPhapDieuTriUngBuou.class, "emr_ck_phuong_phap_dieu_tri_ung_buou", "idhsba", idhsba, false);
            emrTongKetRaVien.emrCkTinhTrangRaVienMat = getRecord(EmrCkTinhTrangRaVienMat.class, "emr_ck_tinh_trang_ra_vien_mat", "idhsba", idhsba, false);            
        }
        
        return emrTongKetRaVien;
    }
    
    EmrChanDoan getEmrChanDoan(int idhsba) {
        
        var emrChanDoan = getRecord(EmrChanDoan.class, "emr_chan_doan", "idhsba", idhsba, true);
        
        if(emrChanDoan != null) {
            emrChanDoan.emrDmLyDoTaiBienBienChung =  getEmrDm("emr_dm_ly_do_tai_bien_bien_chung", emrChanDoan.idlydotbbc);
            emrChanDoan.emrDmMaBenhChandoandieutri =  getEmrDm("emr_dm_ma_benh", emrChanDoan.idicdChandoandieutri);
            emrChanDoan.emrDmMaBenhChandoankkb =  getEmrDm("emr_dm_ma_benh", emrChanDoan.idicdChandoankkb);
            emrChanDoan.emrDmMaBenhChandoannoiden =  getEmrDm("emr_dm_ma_benh", emrChanDoan.idicdChandoannoiden);
            emrChanDoan.emrDmMaBenhChandoanravienchinh =  getEmrDm("emr_dm_ma_benh", emrChanDoan.idicdChandoanravienchinh);
            emrChanDoan.emrDmMaBenhChandoanravienkemtheo =  getEmrDm("emr_dm_ma_benh", emrChanDoan.idicdChandoanravienkemtheo);
            emrChanDoan.emrDmMaBenhChandoanraviennguyennhan =  getEmrDm("emr_dm_ma_benh", emrChanDoan.idicdChandoanraviennguyennhan);
            emrChanDoan.emrDmMaBenhChandoansaupt =  getEmrDm("emr_dm_ma_benh", emrChanDoan.idicdChandoansaupt);
            emrChanDoan.emrDmMaBenhChandoantruocpt =  getEmrDm("emr_dm_ma_benh", emrChanDoan.idicdChandoantruocpt);            
        }
        
        return emrChanDoan;
    }
    
    EmrYhctChanDoan getEmrYhctChanDoan(int idhsba) {
        var emrYhctChanDoan = getRecord(EmrYhctChanDoan.class, "emr_yhct_chan_doan", "idhsba", idhsba, true);
        
        if(emrYhctChanDoan != null) {
            emrYhctChanDoan.emrDmYhctBenhdanhRavien = getEmrDm("emr_dm_yhct_benh_danh", emrYhctChanDoan.idbenhdanhyhctravien);
            emrYhctChanDoan.emrDmYhctBenhdanhVaovien= getEmrDm("emr_dm_yhct_benh_danh", emrYhctChanDoan.idbenhdanhyhctvaovien);
            emrYhctChanDoan.emrDmYhctBenhdanhVk = getEmrDm("emr_dm_yhct_benh_danh", emrYhctChanDoan.idbenhdanh_vk);            
        }
        
        return emrYhctChanDoan;
    }
    
    EmrTinhTrangRaVien getEmrTinhTrangRaVien(int idhsba) {
        
        EmrTinhTrangRaVien emrTinhTrangRaVien = getRecord(EmrTinhTrangRaVien.class, "emr_tinh_trang_ra_vien", "idhsba", idhsba, true);
        
        if(emrTinhTrangRaVien != null) {
            emrTinhTrangRaVien.emrDmKetQuaDieuTri = getEmrDm("emr_dm_ket_qua_dieu_tri", emrTinhTrangRaVien.idketquadieutri);
            emrTinhTrangRaVien.emrDmKetQuaGiaiPhauBenh = getEmrDm("emr_dm_ket_qua_giai_phau_benh", emrTinhTrangRaVien.idgiaiphaubenh);
            emrTinhTrangRaVien.emrDmLyDoTuVong = getEmrDm("emr_dm_ly_do_tu_vong", emrTinhTrangRaVien.idlydotuvong);
            emrTinhTrangRaVien.emrDmGiaiphaututhi = getEmrDm("emr_dm_ma_benh", emrTinhTrangRaVien.idicdGiaiphaututhi);
            emrTinhTrangRaVien.emrDmLyDoTuVong = getEmrDm("emr_dm_ly_do_tu_vong", emrTinhTrangRaVien.idlydotuvong);
            emrTinhTrangRaVien.emrDmNguyennhantuvong = getEmrDm("emr_dm_ma_benh", emrTinhTrangRaVien.idicdNguyennhantuvong);
            emrTinhTrangRaVien.emrDmThoiDiemTuVong = getEmrDm("emr_dm_thoi_diem_tu_vong", emrTinhTrangRaVien.idthoidiemtuvong);
            emrTinhTrangRaVien.emrDmYhctKetQuaDieuTri = getEmrDm("emr_dm_yhct_ket_qua_dieu_tri", emrTinhTrangRaVien.idyhctketquadieutri);
        }
        
        return emrTinhTrangRaVien;
    }
    
    EmrTongKetSanKhoa getEmrTongKetSanKhoa(int idhsba) {
        EmrTongKetSanKhoa emrTongKetSanKhoa = getRecord(EmrTongKetSanKhoa.class, "emr_tong_ket_san_khoa", "idhsba", idhsba, false);
        
        if(emrTongKetSanKhoa != null) {
            emrTongKetSanKhoa.emrDmCachDe = getEmrDm("emr_dm_cach_de", emrTongKetSanKhoa.idcachde);
            emrTongKetSanKhoa.emrDmTrangThaiAmdao = getEmrDm("emr_dm_trang_thai_mo_de", emrTongKetSanKhoa.idtrangthaiamdao);
            emrTongKetSanKhoa.emrDmTrangThaiCoTucung = getEmrDm("emr_dm_trang_thai_mo_de", emrTongKetSanKhoa.idtrangthaicotucung);
            emrTongKetSanKhoa.emrDmTrangThaiSinhmon = getEmrDm("emr_dm_trang_thai_mo_de", emrTongKetSanKhoa.idtrangthaisinhmon);            
        }
        
        return emrTongKetSanKhoa;
        
    }
        
    List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKems(String table, int iddk) {
        return getRecords(EmrQuanLyFileDinhKem.class, table, "iddk", iddk, false);
    }
    
    List<EmrChamSoc> getEmrChamSocs(EmrVaoKhoa emrVaoKhoa) {
        var lst = getRecords(EmrChamSoc.class, "emr_cham_soc", "idvaokhoa", emrVaoKhoa.id, true);
        
        for(var item : lst) {
            item.emrVaoKhoa = emrVaoKhoa;
            item.emrQuaTrinhChamSocs = getRecords(EmrQuaTrinhChamSoc.class, "emr_qua_trinh_cham_soc", "idchamsoc", item.id, true);
            item.emrQuanLyFileDinhKemChamSocs = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_cham_soc", item.id);
        }
        
        return lst;        
    }

    List<EmrDieuTri> getEmrDieuTris(EmrVaoKhoa emrVaoKhoa) {
        var lst = getRecords(EmrDieuTri.class, "emr_dieu_tri", "idvaokhoa", emrVaoKhoa.id, true);
        
        for(var item : lst) {
            item.emrVaoKhoa = emrVaoKhoa;
            item.emrQuaTrinhDieuTris = getRecords(EmrQuaTrinhDieuTri.class, "emr_qua_trinh_dieu_tri", "iddieutri", item.id, true);
            item.emrQuanLyFileDinhKemDieuTris = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_dieu_tri", item.id);
        }
        
        return lst;        
    }
  
    List<EmrChucNangSong> getEmrChucNangSongs(EmrVaoKhoa emrVaoKhoa) {
        var lst = getRecords(EmrChucNangSong.class, "emr_chuc_nang_song", "idvaokhoa", emrVaoKhoa.id, true);
        
        for(var item : lst) {
            item.emrVaoKhoa = emrVaoKhoa;
            item.emrChucNangSongChiTiets = getRecords(EmrChucNangSongChiTiet.class, "emr_chuc_nang_song_chi_tiet", "idchucnangsong", item.id, true);
            item.emrQuanLyFileDinhKemChucNangSongs = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_chuc_nang_song", item.id);
        }
        
        return lst;        
    }
    
    List<EmrHoiDongHoiChan> getEmrHoiDongHoiChans(EmrHoiChan emrHoiChan) {
        var lst = getRecords(EmrHoiDongHoiChan.class, "emr_hoi_dong_hoi_chan", "idhoichan", emrHoiChan.id, true);
        for(var item : lst) {
            item.emrHoiChan = emrHoiChan;
        }
        return lst;        
    }

    List<EmrHoiChan> getEmrHoiChans(EmrVaoKhoa emrVaoKhoa) {
        var lst = getRecords(EmrHoiChan.class, "emr_hoi_chan", "idvaokhoa", emrVaoKhoa.id, true);
        
        for(var item : lst) {
            item.emrVaoKhoa = emrVaoKhoa;
            item.emrHoiDongHoiChans = getEmrHoiDongHoiChans(item);
            item.emrQuanLyFileDinhKemHoiChans = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_hoi_chan", item.id);
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
    
    EmrCkTienSuSanKhoa getEmrCkTienSuSanKhoa(int idhsba) {
        var emrCkTienSuSanKhoa = getRecord(EmrCkTienSuSanKhoa.class, "emr_ck_tien_su_san_khoa", "idhsba", idhsba, false);
        emrCkTienSuSanKhoa.emrCkTienSuSanKhoaChiTiets = getRecords(EmrCkTienSuSanKhoaChiTiet.class, "emr_ck_tien_su_san_khoa_chi_tiet", "idhsba", idhsba, true);
        return emrCkTienSuSanKhoa;
    }
    
    public EmrYhctBenhAn getEmrYhctBenhAn(int idhsba) {
        var emrYhctBenhAn = getRecord(EmrYhctBenhAn.class, "emr_yhct_benh_an", "idhsba", idhsba, true);
        
        if(emrYhctBenhAn != null) {
            emrYhctBenhAn.emrDmYhctCheDoChamSoc = getEmrDm("emr_dm_yhct_che_do_cham_soc", emrYhctBenhAn.idchamsoc);
            emrYhctBenhAn.emrYhctBenhanThietChan = getRecord(EmrYhctBenhanThietChan.class, "emr_yhct_benhan_thiet_chan", "idhsba", idhsba, false);
            emrYhctBenhAn.emrYhctBenhanVaanChan = getRecord(EmrYhctBenhanVaanChan.class, "emr_yhct_benhan_vaan_chan", "idhsba", idhsba, false);
            emrYhctBenhAn.emrYhctBenhanVawnChan = getRecord(EmrYhctBenhanVawnChan.class, "emr_yhct_benhan_vawn_chan", "idhsba", idhsba, false);
            emrYhctBenhAn.emrYhctBenhanVongChan = getRecord(EmrYhctBenhanVongChan.class, "emr_yhct_benhan_vong_chan", "idhsba", idhsba, false);            
        }
        
        return emrYhctBenhAn;
    }
    
    public EmrBenhAn getEmrBenhAn(int idhsba) {
        var emrBenhAn = getRecord(EmrBenhAn.class, "emr_benh_an", "idhsba", idhsba, true);
        
        if(emrBenhAn != null) {
        
            emrBenhAn.emrCkChanTayMieng = getRecord(EmrCkChanTayMieng.class, "emr_ck_chan_tay_mieng", "idhsba", idhsba, false);
            emrBenhAn.emrCkChucNangSinhHoat = getRecord(EmrCkChucNangSinhHoat.class, "emr_ck_chuc_nang_sinh_hoat", "idhsba", idhsba, false);
            emrBenhAn.emrCkCoXuongKhop = getRecord(EmrCkCoXuongKhop.class, "emr_ck_co_xuong_khop", "idhsba", idhsba, false);
            emrBenhAn.emrCkHoHap = getRecord(EmrCkHoHap.class, "emr_ck_ho_hap", "idhsba", idhsba, false);
            emrBenhAn.emrCkHuongDieuTriHuyetHoc = getRecord(EmrCkHuongDieuTriHuyetHoc.class, "emr_ck_huong_dieu_tri_huyet_hoc", "idhsba", idhsba, false);
            emrBenhAn.emrCkHuongDieuTriTcm = getRecord(EmrCkHuongDieuTriTcm.class, "emr_ck_huong_dieu_tri_tcm", "idhsba", idhsba, false);
            emrBenhAn.emrCkKhamPhuKhoa = getRecord(EmrCkKhamPhuKhoa.class, "emr_ck_kham_phu_khoa", "idhsba", idhsba, false);
            emrBenhAn.emrCkKhamSanKhoa = getRecord(EmrCkKhamSanKhoa.class, "emr_ck_kham_san_khoa", "idhsba", idhsba, false);
            emrBenhAn.emrCkKhamSoSinh = getRecord(EmrCkKhamSoSinh.class, "emr_ck_kham_so_sinh", "idhsba", idhsba, false);
                    
            emrBenhAn.emrCkMat = getRecord(EmrCkMat.class, "emr_ck_mat", "idhsba", idhsba, false);
            emrBenhAn.emrCkMoiSinh = getRecord(EmrCkMoiSinh.class, "emr_ck_moi_sinh", "idhsba", idhsba, false);
            emrBenhAn.emrCkPhuongPhapHoiSinh = getRecord(EmrCkPhuongPhapHoiSinh.class, "emr_ck_phuong_phap_hoi_sinh", "idhsba", idhsba, false);                
            emrBenhAn.emrCkQuaTrinhBenhLyTcm = getRecord(EmrCkQuaTrinhBenhLyTcm.class, "emr_ck_qua_trinh_benh_ly_tcm", "idhsba", idhsba, false);
            emrBenhAn.emrCkQuaTrinhSinhTruong = getRecord(EmrCkQuaTrinhSinhTruong.class, "emr_ck_qua_trinh_sinh_truong", "idhsba", idhsba, false);
            emrBenhAn.emrCkSkTinhTrangSanPhu = getRecord(EmrCkSkTinhTrangSanPhu.class, "emr_ck_sk_tinh_trang_san_phu", "idhsba", idhsba, false);
            emrBenhAn.emrCkTamThan = getRecord(EmrCkTamThan.class, "emr_ck_tam_than", "idhsba", idhsba, false);
            emrBenhAn.emrCkThanKinh = getRecord(EmrCkThanKinh.class, "emr_ck_than_kinh", "idhsba", idhsba, false);
            emrBenhAn.emrCkTiemChung = getRecord(EmrCkTiemChung.class, "emr_ck_tiem_chung", "idhsba", idhsba, false);
            emrBenhAn.emrCkTienSuBanThanSanKhoa = getRecord(EmrCkTienSuBanThanSanKhoa.class, "emr_ck_tien_su_ban_than_san_khoa", "idhsba", idhsba, false);
            emrBenhAn.emrCkTienSuGiaDinh = getRecord(EmrCkTienSuGiaDinh.class, "emr_ck_tien_su_gia_dinh", "idhsba", idhsba, false);
            emrBenhAn.emrCkTienSuPhuKhoa = getRecord(EmrCkTienSuPhuKhoa.class, "emr_ck_tien_su_phu_khoa", "idhsba", idhsba, false);
            emrBenhAn.emrCkTienSuSanKhoa = getEmrCkTienSuSanKhoa(idhsba);
            emrBenhAn.emrCkTieuHoa = getRecord(EmrCkTieuHoa.class, "emr_ck_tieu_hoa", "idhsba", idhsba, false);
            emrBenhAn.emrCkTinhTrangSanPhu = getRecord(EmrCkTinhTrangSanPhu.class, "emr_ck_tinh_trang_san_phu", "idhsba", idhsba, false);
            emrBenhAn.emrCkTinhTrangSoSinh = getRecord(EmrCkTinhTrangSoSinh.class, "emr_ck_tinh_trang_so_sinh", "idhsba", idhsba, false);
            emrBenhAn.emrCkToanThan = getRecord(EmrCkToanThan.class, "emr_ck_toan_than", "idhsba", idhsba, false);
            emrBenhAn.emrCkTomTatBenhAnTcm = getRecord(EmrCkTomTatBenhAnTcm.class, "emr_ck_tom_tat_benh_an_tcm", "idhsba", idhsba, false);        
            emrBenhAn.emrCkTuanHoan = getRecord(EmrCkTuanHoan.class, "emr_ck_tuan_hoan", "idhsba", idhsba, false);
            
            emrBenhAn.emrDmMaBenhChandoanbenhchinh = getEmrDm("emr_dm_ma_benh", emrBenhAn.idicdChandoanbenhchinh);
            emrBenhAn.emrDmMaBenhChandoankemtheo = getEmrDm("emr_dm_ma_benh", emrBenhAn.idicdChandoankemtheo);
            emrBenhAn.emrDmMaBenhChandoanphanbiet = getEmrDm("emr_dm_ma_benh", emrBenhAn.idicdChandoanphanbiet);
        }        
        
        return emrBenhAn;        
    }
    
    List<EmrYhctDonThuoc> getEmrYhctDonThuocs(int idhsba) {
        var lst = (List<EmrYhctDonThuoc>) getRecords(EmrYhctDonThuoc.class, "emr_yhct_don_thuoc", "idhsba", idhsba, true);
        
        for(var item : lst) {
            item.emrYhctDonThuocChiTiets = getRecords(EmrYhctDonThuocChiTiet.class, "emr_yhct_don_thuoc_chi_tiet", "idyhctdonthuoc", item.id, true);
            item.emrQuanLyFileDinhKemDonThuocYhcts = getEmrQuanLyFileDinhKems("emr_quan_ly_file_dinh_kem_don_thuoc_yhct", item.id);
        }
        
        return lst;
    }
        
    public EmrDanhSachHoSoBenhAn getEmrDanhSachHoSoBenhAnById(Integer id) {
        
        var dshsba = getRecordById(EmrDanhSachHoSoBenhAn.class, "emr_danh_sach_ho_so_benh_an", id, true);
        
        if(dshsba != null) {
            dshsba.emrBenhAn = getEmrBenhAn(id);
            if(dshsba.emrBenhAn != null) {
                dshsba.emrBenhAn.emrDanhSachHoSoBenhAn = dshsba;
            }
            
            dshsba.emrBenhNhan = getEmrBenhNhanById(dshsba.idbenhnhan);
            dshsba.emrDmLoaiBenhAn = getEmrDm("emr_dm_loai_benh_an", dshsba.idloaibenhan);
            dshsba.emrDmNguondulieu = getEmrDm("emr_dm_tu_sinh", dshsba.idnguondulieu);
            dshsba.emrDmTrangthai = getEmrDm("emr_dm_tu_sinh", dshsba.idtrangthai);
            
            dshsba.emrQuanLyNguoiBenh = getEmrQuanLyNguoiBenh(id);
            dshsba.emrTongKetRaVien = getEmrTongKetRaVien(id);
            dshsba.emrChanDoan = getEmrChanDoan(id);
                        
            dshsba.emrTinhTrangRaVien = getEmrTinhTrangRaVien(id);
            dshsba.emrTongKetSanKhoa = getEmrTongKetSanKhoa(id);
            dshsba.emrVaoKhoas = getEmrVaoKhoas(id);
            dshsba.emrQuaTrinhSuDungThuocs = getEmrQuaTrinhSuDungThuocs(id);
            dshsba.emrHinhAnhTonThuongs = getEmrHinhAnhTonThuongs(id);
            dshsba.emrGiaiPhauBenhs = getEmrGiaiPhauBenhs(id);
            dshsba.emrThamDoChucNangs = getEmrThamDoChucNangs(id);
            dshsba.emrPhauThuatThuThuats = getEmrPhauThuatThuThuats(id);
            dshsba.emrChanDoanHinhAnhs = getEmrChanDoanHinhAnhs(id);
            dshsba.emrDonThuocs = getEmrDonThuocs(id);
            dshsba.emrXetNghiems = getEmrXetNghiems(id);
            dshsba.emrQuanLyFileDinhKemBenhAn = getRecords(EmrQuanLyFileDinhKemBenhAn.class, "emr_quan_ly_file_dinh_kem_benhan", "idhsba", id, false);
            
            dshsba.emrYhctBenhAn = getEmrYhctBenhAn(id);
            if(dshsba.emrYhctBenhAn != null) {
                dshsba.emrYhctBenhAn.emrDanhSachHoSoBenhAn = dshsba;
            }
            
            dshsba.emrYhctChanDoan = getEmrYhctChanDoan(id);
            dshsba.emrYhctDonThuocs = getEmrYhctDonThuocs(id);
            dshsba.emrYhctNhaBa = getRecord(EmrYhctNhaBa.class, "emr_yhct_nha_ba", "idhsba", id, true);
            dshsba.emrYhctNhaBaGhiChus = getRecords(EmrYhctNhaBaGhiChu.class, "emr_yhct_ghi_chu_nha_ba", "idhsba", id, true);
        }        
        
        return dshsba;
    }

}
