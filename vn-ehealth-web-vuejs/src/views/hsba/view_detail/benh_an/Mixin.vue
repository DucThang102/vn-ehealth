<script>
export default {
  data: function() {
    return {
      ptttList: []
    };
  },

  created: async function() {
    this.ptttList = await this.get("/api/pttt/get_ds_pttt", {
      hsba_id: this.hsba.id
    });
  },

  props: ["hsba"],

  computed: {
    // Thong tin co so kham benh
    donViChuQuan() {
      return attr(this.hsba, "emrCoSoKhamBenh.donvichuquan");
    },

    coSoKhamBenh() {
      return attr(this.hsba, "emrCoSoKhamBenh.ten");
    },

    // Thong tin benh nhan
    tenBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.tendaydu") || "";
    },

    ngaySinhBenhNhan() {
      return this.formatDate(attr(this.hsba, "emrBenhNhan.ngaysinh")) || "";
    },

    tuoiBenhNhan() {
      var tuoi = (this.hsba.tuoiBenhNhan.tuoi || "") + "";
      if (tuoi.length == 1) {
        return "0" + tuoi;
      }
      return tuoi;
    },

    gioiTinhBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.emrDmGioiTinh") || {};
    },

    ngheNghiepBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.emrDmNgheNghiep") || {};
    },

    danTocBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.emrDmDanToc") || {};
    },

    quocTichBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.emrDmQuocGia") || {};
    },

    diaChiXaBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.emrDmPhuongXa") || {};
    },

    diaChiBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.diachi");
    },

    diaChiHuyenBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.emrDmQuanHuyen") || {};
    },

    diaChiTinhBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.emrDmTinhThanh") || {};
    },

    noiLamViecBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.noilamviec");
    },

    doiTuongTaiChinh() {
      return attr(this.hsba, "emrBenhNhan.emrDmLoaiDoiTuongTaiChinh") || {};
    },

    soTheBHYT() {
      return attr(this.hsba, "emrBenhNhan.sobhyt") || "";
    },

    ngayHetHanBHYT() {
      return attr(this.hsba, "emrBenhNhan.ngayhethanthebhyt") || "";
    },

    nguoiBaoTin() {
      return attr(this.hsba, "emrBenhNhan.tennguoibaotin") || "";
    },

    soDienThoaiNguoiBaoTin() {
      return attr(this.hsba, "emrBenhNhan.sodienthoainguoibaotin") || "";
    },

    diaChiNguoiBaoTin() {
      return attr(this.hsba, "emrBenhNhan.diachinguoibaotin") || "";
    },

    // Quan ly nguoi benh
    ngayGioVaoVien() {
      return attr(this.hsba, "emrQuanLyNguoiBenh.ngaygiovaovien") || "";
    },

    noiTrucTiepVao() {
      return attr(this.hsba, "emrQuanLyNguoiBenh.emrDmNoiTrucTiepVao") || {};
    },

    noiGioiThieu() {
      return attr(this.hsba, "emrQuanLyNguoiBenh.emrDmNoiGioiThieu") || {};
    },

    lanVaoVien() {
      return attr(this.hsba, "emrQuanLyNguoiBenh.vaovienlanthu");
    },

    loaiChuyenVien() {
      return attr(this.hsba, "emrQuanLyNguoiBenh.emrDmLoaiChuyenVien") || {};
    },

    noiChuyenDen() {
      return attr(this.hsba, "emrQuanLyNguoiBenh.noichuyenden");
    },

    ngayGioRaVien() {
      return attr(this.hsba, "emrQuanLyNguoiBenh.ngaygioravien") || "";
    },

    loaiRaVien() {
      return attr(this.hsba, "emrQuanLyNguoiBenh.emrDmLoaiRaVien") || {};
    },

    tongSoNgayDieuTri() {
      return attr(this.hsba, "emrQuanLyNguoiBenh.tongsongaydieutri");
    },

    // Chan doan
    chanDoanNoiDen() {
      var chanDoan =
        attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoannoiden") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp("motachandoannoiden")
      };
    },

    chanDoanKKB() {
      var chanDoan =
        attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoankkb") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp("motachandoankkb")
      };
    },

    chanDoanDieuTri() {
      var chanDoan =
        attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoandieutrichinh") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp("motachandoandieutrichinh")
      };
    },

    chanDoanDieuTriKemTheo() {
      var chanDoanKemTheoList = attr(
        this.hsba,
        "emrChanDoan.emrDmMaBenhChandoandieutrikemtheos"
      );
      var chanDoan = {};
      if (chanDoanKemTheoList && chanDoanKemTheoList.length > 0) {
        chanDoan = chanDoanKemTheoList[0];
      }
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp("motachandoandieutrikemtheo")
      };
    },

    chandoanDieuTriPhanBiet() {
      var chanDoan =
        attr(this.hsba, "emrBenhAn.emrDmMaBenhChandoandieutriphanbiet") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp("motachandoandieutriphanbiet")
      };
    },

    chanDoanCoPhauThuat() {
      return attr(this.hsba, "emrChanDoan.dieutriphauthuat");
    },

    chanDoanCoThuThuat() {
      return attr(this.hsba, "emrChanDoan.dieutrithuthuat");
    },

    chanDoanRaVienChinh() {
      var chanDoan =
        attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoanravienchinh") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp("motachandoanravienchinh")
      };
    },

    chanDoanRaVienKemTheo() {
      var chanDoanKemTheoList = attr(
        this.hsba,
        "emrChanDoan.emrDmMaBenhChandoanravienkemtheos"
      );
      var chanDoan = {};
      if (chanDoanKemTheoList && chanDoanKemTheoList.length > 0) {
        chanDoan = chanDoanKemTheoList[0];
      }
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp("motachandoanravienkemtheo")
      };
    },

    chanDoanRaVienNguyenNhan() {
      var chanDoan =
        attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoanraviennguyennhan") ||
        {};
      return {
        ma: chanDoan.ma,
        ten:
          chanDoan.ten || this.getChanDoanProp("motachandoanraviennguyennhan")
      };
    },

    chanDoanTruocPt() {
      var chanDoanTruocPtList = attr(
        this.hsba,
        "emrChanDoan.emrDmMaBenhChandoantruocpts"
      );
      var chanDoan = {};
      if (chanDoanTruocPtList && chanDoanTruocPtList.length > 0) {
        chanDoan = chanDoanTruocPtList[0];
      }
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp("motachandoantruocpt")
      };
    },

    chanDoanSauPt() {
      var chanDoanSauPtList = attr(
        this.hsba,
        "emrChanDoan.emrDmMaBenhChandoansaupts"
      );
      var chanDoan = {};
      if (chanDoanSauPtList && chanDoanSauPtList.length > 0) {
        chanDoan = chanDoanSauPtList[0];
      }
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp("motachandoansaupt")
      };
    },

    chanDoanBiTaiBien() {
      return attr(this.hsba, "emrChanDoan.bitaibien");
    },

    chanDoanBiBienChung() {
      return attr(this.hsba, "emrChanDoan.bibienchung");
    },

    // Tinh trang ra vien

    ketQuaDieuTri() {
      return attr(this.hsba, "emrTinhTrangRaVien.emrDmKetQuaDieuTri") || {};
    },

    giaiPhauBenh() {
      return (
        attr(this.hsba, "emrTinhTrangRaVien.emrDmKetQuaGiaiPhauBenh") || {}
      );
    },

    ngayGioTuVong() {
      return attr(this.hsba, "emrTinhTrangRaVien.ngaygiotuvong") || "";
    },

    lyDoTuVuong() {
      return attr(this.hsba, "emrTinhTrangRaVien.emrDmLyDoTuVong") || {};
    },

    thoiDiemTuVong() {
      return attr(this.hsba, "emrTinhTrangRaVien.emrDmThoiDiemTuVong") || {};
    },

    nguyenNhanTuVong() {
      return (
        attr(this.hsba, "emrTinhTrangRaVien.emrDmMaBenhNguyennhantuvong") || {}
      );
    },

    khamNghiemTuThi() {
      return attr(this.hsba, "emrTinhTrangRaVien.khamnghiemtuthi");
    },

    giaiPhauTuThi() {
      return (
        attr(this.hsba, "emrTinhTrangRaVien.emrDmMaBenhGiaiphaututhi") || {}
      );
    },

    // Benh an
    tienThai() {
      return (
        attr(this.hsba, "emrBenhAn.hoibenh.quatrinhsinhtruong.tienthai") || {}
      );
    },

    tinhTrangKhiSinh() {
      return (
        attr(
          this.hsba,
          "emrBenhAn.hoibenh.quatrinhsinhtruong.emrDmTinhTrangKhiSinh"
        ) || {}
      );
    },

    tiemChung() {
      return (
        attr(this.hsba, "emrBenhAn.hoibenh.quatrinhsinhtruong.tiemchung") || {}
      );
    },

    //
    giamDocBenhVien() {
      return attr(this.hsba, "emrCoSoKhamBenh.giamdoc");
    },

    truongPhongKHTH() {
      return attr(this.hsba, "emrCoSoKhamBenh.truongphongth");
    },

    ngayKyBenhAn() {
      return this.hsba.ngaykybenhan || "";
    },

    bacSyLamBenhAn() {
      return this.hsba.bacsylambenhan || {};
    },

    ngayKyDieuTri() {
      return this.hsba.ngaybacsydieutriky || "";
    },

    bacSyDieuTri() {
      return this.hsba.bacsydieutri || {};
    },

    khoaDieuTri() {
      var khoa = {};
      if (this.hsba.emrVaoKhoas && this.hsba.emrVaoKhoas.length > 0) {
        var n = this.hsba.emrVaoKhoas.length;
        khoa = this.hsba.emrVaoKhoas[n - 1];
      }
      if (!khoa.tenkhoa) {
        khoa.tenkhoa = attr(khoa, "emrDmKhoaDieuTri.ten");
      }
      return khoa;
    },

    ngayKetThucDieuTri() {
      return this.khoaDieuTri.ngayketthucdieutri;
    },

    //So to dieu tri
    soToXQuang() {
      if (this.hsba.sotodieutri) {
        var soTo = this.hsba.sotodieutri.find(x => (x.ma = "01"));
        return soTo ? soTo.soluong : 0;
      }
      return 0;
    },

    soToCTScanner() {
      if (this.hsba.sotodieutri) {
        var soTo = this.hsba.sotodieutri.find(x => (x.ma = "02"));
        return soTo ? soTo.soluong : 0;
      }
      return 0;
    },

    soToSieuAm() {
      if (this.hsba.sotodieutri) {
        var soTo = this.hsba.sotodieutri.find(x => (x.ma = "03"));
        return soTo ? soTo.soluong : 0;
      }
      return 0;
    },

    soToXetNghiem() {
      if (this.hsba.sotodieutri) {
        var soTo = this.hsba.sotodieutri.find(x => (x.ma = "04"));
        return soTo ? soTo.soluong : 0;
      }
      return 0;
    },

    soToKhac() {
      var tong = 0;
      for (
        var i = 0;
        this.hsba.sotodieutri && i < this.hsba.sotodieutri.length;
        i++
      ) {
        if (parseInt(this.hsba.sotodieutri[i].ma) > 4)
          tong += this.hsba.sotodieutri[i].soluong;
      }
      return tong;
    },

    tongSoToDieuTri() {
      var tong = 0;
      for (
        var i = 0;
        this.hsba.sotodieutri && i < this.hsba.sotodieutri.length;
        i++
      ) {
        tong += this.hsba.sotodieutri[i].soluong;
      }
      return tong;
    }
  },

  methods: {
    getHsbaProp(prop) {
      return attr(this.hsba, prop);
    },

    getBenhNhanProp(prop) {
      return attr(this.hsba, "emrBenhNhan." + prop);
    },

    getChanDoanProp(prop) {
      return attr(this.hsba, "emrChanDoan." + prop);
    },

    getBenhAnProp(prop) {
      return attr(this.hsba, "emrBenhAn." + prop);
    },

    toCharArray(st) {
      return (st || "").replace(".", "").split("");
    },

    getTenKhoa(khoa) {
      return khoa.tenkhoa || attr(khoa, "emrDmKhoaDieuTri.ten");
    },

    formatNgayGio(ngaygio) {
      if (ngaygio && ngaygio.length >= 16) {
        var nam = ngaygio.substring(0, 4);
        var thang = ngaygio.substring(5, 7);
        var ngay = ngaygio.substring(8, 10);
        var gio = ngaygio.substring(11, 13);
        var phut = ngaygio.substring(14, 16);
        return `${gio} giờ ${phut} ph ngày ${ngay}/${thang}/${nam}`;
      }
      return "... giờ ... ph ngày .../.../......";
    },

    formatNgayGio2(ngaygio) {
      if (ngaygio && ngaygio.length >= 16) {
        var nam = ngaygio.substring(0, 4);
        var thang = ngaygio.substring(5, 7);
        var ngay = ngaygio.substring(8, 10);
        var gio = ngaygio.substring(11, 13);
        var phut = ngaygio.substring(14, 16);
        return `${gio} giờ ${phut} ngày ${ngay}/${thang}/${nam}`;
      }
      return "... giờ ... ngày .../.../......";
    },

    formatNgay(ngaygio) {
      if (ngaygio && ngaygio.length >= 10) {
        var nam = ngaygio.substring(0, 4);
        var thang = ngaygio.substring(5, 7);
        var ngay = ngaygio.substring(8, 10);
        return `${ngay} tháng ${thang} năm ${nam}`;
      }
      return "... tháng ... năm ......";
    },

    formatNgay2(ngaygio) {
      if (ngaygio && ngaygio.length >= 10) {
        var nam = ngaygio.substring(0, 4);
        var thang = ngaygio.substring(5, 7);
        var ngay = ngaygio.substring(8, 10);
        return `Ngày ${ngay} tháng ${thang} năm ${nam}`;
      }
      return "Ngày ... tháng ... năm ......";
    }
  }
};
</script>