var benh_an_script = {
  data: function () {
    return {
      hsba: null
    };
  },

  props: ["hsba_id"],

  computed: {
    pdfURL: function () {
      return this.API_URL + "/api/hsba/view_pdf?hsba_id=" + this.hsba_id;
    }
  },

  methods: {
    printToPdf: function () {
      $("#benh_an input").each(function () {
        $(this).attr("value", $(this).val());
      });

      $("#benh_an input[type=checkbox]").each(function () {
        if ($(this).prop("checked")) {
          $(this).attr("checked", "checked");
        }
      });

      var body = $("#benh_an").html();
      var wnd = window.open("", ""); //window.open('', '', 'width=1024');
      wnd.document.write(`<html><head>
      <title>Tờ bệnh án</title>
      <style>
        @media print  
        {
            div{
                page-break-inside: avoid;
            }
        }
      </style>
      <link href="/css/fontawesome/all.css" rel="stylesheet" type="text/css" />
      <link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,300i,400,400i,700,700i&subset=latin,vietnamese" rel="stylesheet" type="text/css">
      <link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
      <link href="/css/bootstrap.css" rel="stylesheet" />
      <link href="http://unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.min.css"  rel="stylesheet"/>
      <link href="/css/style.css" rel="stylesheet" />
      <link href="/css/benhan.css" rel="stylesheet" />
      <script src="/js/jquery.min.js"></script>
      <script>
      $(document).ready(function(){ window.print() });
      </script>
      
      `);

      wnd.document.write("</head><body >");
      wnd.document.write(body);
      wnd.document.write("</body></html>");
      wnd.document.close();
    }
  },

  created: async function () {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
      hsba_id: this.hsba_id
    });

    setTimeout(function () {
      $("#printButton").removeClass("d-none");
    }, 2000);
  }
};

VueAsyncComponent(
  "benh-an",
  "/pages/hsba/view_detail/benh_an/benh_an_new2.html",
  benh_an_script
);

var benh_an_chi_tiet_script = {
  data: function () {
    return {
      ptttList: []
    };
  },

  created: async function () {
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
      var chanDoan = attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoannoiden") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp('motachandoannoiden')
      };
    },

    chanDoanKKB() {
      var chanDoan = attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoankkb") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp('motachandoankkb')
      };
    },

    chanDoanDieuTri() {
      var chanDoan = attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoandieutrichinh") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp('motachandoandieutrichinh')
      };
    },

    chanDoanDieuTriKemTheo() {
      var chanDoanKemTheoList = attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoandieutrikemtheos")
      var chanDoan = {};
      if (chanDoanKemTheoList && chanDoanKemTheoList.length > 0) {
        chanDoan = chanDoanKemTheoList[0];
      }
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp('motachandoandieutrikemtheo')
      };
    },

    chandoanDieuTriPhanBiet() {
      var chanDoan = attr(this.hsba, "emrBenhAn.emrDmMaBenhChandoandieutriphanbiet") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp('motachandoandieutriphanbiet')
      };
    },

    chanDoanCoPhauThuat() {
      return attr(this.hsba, "emrChanDoan.dieutriphauthuat");
    },

    chanDoanCoThuThuat() {
      return attr(this.hsba, "emrChanDoan.dieutrithuthuat");
    },

    chanDoanRaVienChinh() {
      var chanDoan = attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoanravienchinh") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp('motachandoanravienchinh')
      };
    },

    chanDoanRaVienKemTheo() {
      var chanDoanKemTheoList = attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoanravienkemtheos")
      var chanDoan = {};
      if (chanDoanKemTheoList && chanDoanKemTheoList.length > 0) {
        chanDoan = chanDoanKemTheoList[0];
      }
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp('motachandoanravienkemtheo')
      };
    },

    chanDoanRaVienNguyenNhan() {
      var chanDoan = attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoanraviennguyennhan") || {};
      return {
        ma: chanDoan.ma,
        ten: chanDoan.ten || this.getChanDoanProp('motachandoanraviennguyennhan')
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
      return attr(this.hsba, "emrTinhTrangRaVien.emrDmKetQuaGiaiPhauBenh") || {};
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
      return attr(this.hsba, "emrTinhTrangRaVien.emrDmMaBenhNguyennhantuvong") || {};
    },

    khamNghiemTuThi() {
      return attr(this.hsba, "emrTinhTrangRaVien.khamnghiemtuthi");
    },

    giaiPhauTuThi() {
      return attr(this.hsba, "emrTinhTrangRaVien.emrDmMaBenhGiaiphaututhi") || {};
    },

    // Benh an
    tienThai() {
      return attr(this.hsba, "emrBenhAn.hoibenh.quatrinhsinhtruong.tienthai") || {};
    },

    tinhTrangKhiSinh() {
      return attr(this.hsba, "emrBenhAn.hoibenh.quatrinhsinhtruong.emrDmTinhTrangKhiSinh") || {};
    },

    tiemChung() {
      return attr(this.hsba, "emrBenhAn.hoibenh.quatrinhsinhtruong.tiemchung") || {};
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
      return this.hsba.bacsylambenhan;
    },

    ngayKyDieuTri() {
      return this.hsba.ngaybacsydieutriky || "";
    },

    bacSyDieuTri() {
      return this.hsba.bacsydieutri;
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
      var soTo = this.hsba.sotodieutri.find(x => x.ma = "01");
      return soTo ? soTo.soluong : 0;
    },

    soToCTScanner() {
      var soTo = this.hsba.sotodieutri.find(x => x.ma = "02");
      return soTo ? soTo.soluong : 0;
    },

    soToSieuAm() {
      var soTo = this.hsba.sotodieutri.find(x => x.ma = "03");
      return soTo ? soTo.soluong : 0;
    },

    soToXetNghiem() {
      var soTo = this.hsba.sotodieutri.find(x => x.ma = "04");
      return soTo ? soTo.soluong : 0;
    },

    soToKhac() {
      var tong = 0;
      for (var i = 0; i < this.hsba.sotodieutri.length; i++) {
        if (parseInt(this.hsba.sotodieutri[i].ma) > 4)
          tong += this.hsba.sotodieutri[i].soluong;
      }
      return tong;
    },

    tongSoToDieuTri() {
      var tong = 0;
      for (var i = 0; i < this.hsba.sotodieutri.length; i++) {
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

    chanDoanTruocPt(pttt) {
      var chanDoanTruocPtList = attr(
        pttt,
        "emrDmMaBenhChandoantruocs"
      );
      if (chanDoanTruocPtList && chanDoanTruocPtList.length > 0) {
        return chanDoanTruocPtList[0];
      }
      return {
        ma: "",
        ten: ""
      };
    },

    chanDoanSauPt(pttt) {
      var chanDoanSauPtList = attr(
        pttt,
        "emrDmMaBenhChandoansaus"
      );
      if (chanDoanSauPtList && chanDoanSauPtList.length > 0) {
        return chanDoanSauPtList[0];
      }
      return {
        ma: "",
        ten: ""
      };
    },

    chanDoanRaVienKemTheo(pttt) {
      var chanDoanRaVienKemTheoList = attr(
        pttt,
        "emrDmMaBenhChandoanravienkemtheos"
      );
      if (chanDoanRaVienKemTheoList && chanDoanRaVienKemTheoList.length > 0) {
        return chanDoanRaVienKemTheoList[0];
      }
      return {
        ma: "",
        ten: ""
      };
    },

    toCharArray(st) {
      return (st || "").replace(".", "").split("");
    },

    getTenKhoa(khoa) {
      return khoa.tenkhoa || attr(khoa, "emrDmKhoaDieuTri.ten");
    },

    formatNgayGio(ngaygio) {
      if (ngaygio.length >= 16) {
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
      if (ngaygio.length >= 16) {
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
      if (ngaygio.length >= 10) {
        var nam = ngaygio.substring(0, 4);
        var thang = ngaygio.substring(5, 7);
        var ngay = ngaygio.substring(8, 10);
        return `${ngay} tháng ${thang} năm ${nam}`;
      }
      return "... tháng ... năm ......";
    },

    formatNgay2(ngaygio) {
      if (ngaygio.length >= 10) {
        var nam = ngaygio.substring(0, 4);
        var thang = ngaygio.substring(5, 7);
        var ngay = ngaygio.substring(8, 10);
        return `Ngày ${ngay} tháng ${thang} năm ${nam}`;
      }
      return "Ngày ... tháng ... năm ......";
    }
  }
};

VueAsyncComponent(
  "benh-an-nhi",
  "/pages/hsba/view_detail/benh_an/benh_an_nhi.html",
  benh_an_chi_tiet_script
);

VueAsyncComponent(
  "benh-an-so-sinh",
  "/pages/hsba/view_detail/benh_an/benh_an_so_sinh.html",
  benh_an_chi_tiet_script
);

VueAsyncComponent(
  "benh-an-noi-khoa",
  "/pages/hsba/view_detail/benh_an/benh_an_noi_khoa.html",
  benh_an_chi_tiet_script
);

VueAsyncComponent(
  "benh-an-ngoai-khoa",
  "/pages/hsba/view_detail/benh_an/benh_an_ngoai_khoa.html",
  benh_an_chi_tiet_script
);

VueAsyncComponent(
  "benh-an-ngoai-tru",
  "/pages/hsba/view_detail/benh_an/benh_an_ngoai_tru.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-san-khoa",
  "/pages/hsba/view_detail/benh_an/benh_an_san_khoa.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-truyen-nhiem",
  "/pages/hsba/view_detail/benh_an/benh_an_truyen_nhiem.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-phu-khoa",
  "/pages/hsba/view_detail/benh_an/benh_an_phu_khoa.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-tam-than",
  "/pages/hsba/view_detail/benh_an/benh_an_tam_than.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-da-lieu",
  "/pages/hsba/view_detail/benh_an/benh_an_da_lieu.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-dd",
  "/pages/hsba/view_detail/benh_an/benh_an_dd.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-hhtm",
  "/pages/hsba/view_detail/benh_an/benh_an_hhtm.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-rhm",
  "/pages/hsba/view_detail/benh_an/benh_an_rhm.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-tmh",
  "/pages/hsba/view_detail/benh_an/benh_an_tmh.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-noi-tru-yhct",
  "/pages/hsba/view_detail/benh_an/benh_an_noi_tru_yhct.html",
  benh_an_chi_tiet_script
);
VueAsyncComponent(
  "benh-an-ngoai-tru-yhct",
  "/pages/hsba/view_detail/benh_an/benh_an_ngoai_tru_yhct.html",
  benh_an_chi_tiet_script
);