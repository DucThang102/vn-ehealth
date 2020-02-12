var benh_an_script = {
  data: function() {
    return { hsba: null };
  },

  props: ["hsba_id"],

  computed: {
    pdfURL: function() {
      return this.API_URL + "/api/hsba/view_pdf?hsba_id=" + this.hsba_id;
    }
  },

  methods: {
    printToPdf: function() {
      $("#benh_an input").each(function() {
        $(this).attr("value", $(this).val());
      });

      $("#benh_an input[type=checkbox]").each(function() {
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

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
      hsba_id: this.hsba_id
    });
    
    setTimeout(function() {
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
  data: function() {
    return { ptttList: [] };
  },

  created: async function() {
    this.ptttList = await this.get("/api/pttt/get_ds_pttt", {hsba_id: this.hsba.id});
  },

  props: ["hsba"],

  computed: {
    ngaySinhBenhNhan() {
      return attr(this.hsba, "emrBenhNhan.ngaysinh") || "";
    },
    tuoiBenhNhan() {
      var tuoi = (this.hsba.tuoiBenhNhan || "") + "";
      if (tuoi.length == 1) {
        return "0" + tuoi;
      }
      return tuoi;
    },

    maNgheNghiep() {
      return attr(this.hsba, "emrBenhNhan.emrDmNgheNghiep.ma") || "";
    },

    maDanToc() {
      return attr(this.hsba, "emrBenhNhan.emrDmDanToc.ma") || "";
    },

    maQuocTich() {
      return attr(this.hsba, "emrBenhNhan.emrDmQuocGia.ma") || "";
    },

    maQuanHuyen() {
      return attr(this.hsba, "emrBenhNhan.emrDmQuanHuyen.ma") || "";
    },

    maTinhThanh() {
      return attr(this.hsba, "emrBenhNhan.emrDmTinhThanh.ma") || "";
    },

    soTheBHYT() {
      return attr(this.hsba, "emrBenhNhan.sobhyt") || "";
    },

    ngayHetHanBHYT() {
      return attr(this.hsba, "emrBenhNhan.ngayhethanthebhyt") || "";
    },

    soDienThoai() {
      return attr(this.hsba, "emrBenhNhan.sodienthoainguoibaotin") || "";
    },

    diaChi() {
      return attr(this.hsba, "emrBenhNhan.diachi") || "";
    },

    ngayGioVaoVien() {
      return this.hsba.emrQuanLyNguoiBenh.ngaygiovaovien || ";";
    },

    ngayGioRaVien() {
      return this.hsba.emrQuanLyNguoiBenh.ngaygioravien || ";";
    },

    ngayKyBenhAn() {
      return this.hsba.emrBenhAn.ngaykybenhan || "";
    },

    ngayKyDieuTri() {
      return this.hsba.emrTongKetRaVien.ngaybacsydieutriky || ";";
    },

    ngayGioTuVong() {
      return this.hsba.emrTinhTrangRaVien.ngaygiotuvong || ";";
    },

    khoaDieuTri() {
      if (this.hsba.emrVaoKhoas.length > 0) {
        var n = this.hsba.emrVaoKhoas.length;
        return this.hsba.emrVaoKhoas[n - 1];
      }
      return {};
    },

    tenKhoaDieuTri() {
      var khoa = this.khoaDieuTri;
      return khoa.tenkhoa || attr(khoa, "emrDmKhoaDieuTri.ten");
    },

    ngayKetThucDieuTri() {
      return this.khoaDieuTri.ngayketthucdieutri;
    },

    chanDoanNoiDen() {
      return (
        attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoannoiden") || {
          ma: "",
          ten: ""
        }
      );
    },

    chanDoanKKB() {
      return (
        attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoankkb") || {
          ma: "",
          ten: ""
        }
      );
    },

    chandoanVaoKhoaDieuTri() {
      return (
        attr(this.hsba, "emrBenhAn.emrDmMaBenhChandoanbenhchinh") || {
          ma: "",
          ten: ""
        }
      );
    },

    chandoanVaoKhoaDieuTriPhanBiet() {
      return (
        attr(this.hsba, "emrBenhAn.emrDmMaBenhChandoanphanbiet") || {
          ma: "",
          ten: ""
        }
      );
    },

    chandoanVaoKhoaDieuTriKemTheo() {
      var chanDoanKemTheoList = attr(
        this.hsba,
        "emrBenhAn.emrDmMaBenhChandoankemtheos"
      );
      if (chanDoanKemTheoList && chanDoanKemTheoList.length > 0) {
        return chanDoanKemTheoList[0];
      }
      return { ma: "", ten: "" };
    },

    chanDoanRaVienChinh() {
      return (
        attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoanravienchinh") || {
          ma: "",
          ten: ""
        }
      );
    },

    chanDoanRaVienChinhYhct() {
      return (
        attr(this.hsba, "emrYhctChanDoan.emrDmYhctBenhdanhRavien") || {
          ma: "",
          ten: ""
        }
      );
    },

    chanDoanRaVienNguyenNhan() {
      return (
        attr(this.hsba, "emrChanDoan.emrDmMaBenhChandoanraviennguyennhan") || {
          ma: "",
          ten: ""
        }
      );
    },

    chanDoanTruocPt() {
      var chanDoanTruocPtList = attr(
        this.hsba,
        "emrChanDoan.emrDmMaBenhChandoantruocpts"
      );
      if (chanDoanTruocPtList && chanDoanTruocPtList.length > 0) {
        return chanDoanTruocPtList[0];
      }
      return { ma: "", ten: "" };
    },

    chanDoanSauPt() {
      var chanDoanSauPtList = attr(
        this.hsba,
        "emrChanDoan.emrDmMaBenhChandoansaupts"
      );
      if (chanDoanSauPtList && chanDoanSauPtList.length > 0) {
        return chanDoanSauPtList[0];
      }
      return { ma: "", ten: "" };
    },

    chanDoanRaVienKemTheo() {
      var chanDoanRaVienKemTheoList = attr(
        this.hsba,
        "emrChanDoan.emrDmMaBenhChandoanravienkemtheos"
      );
      if (chanDoanRaVienKemTheoList && chanDoanRaVienKemTheoList.length > 0) {
        return chanDoanRaVienKemTheoList[0];
      }
      return { ma: "", ten: "" };
    },

    nguyenNhanTuVong() {
      return (
        attr(this.hsba, "emrTinhTrangRaVien.emrDmNguyennhantuvong") || {
          ma: "",
          ten: ""
        }
      );
    },

    giaiPhauTuThi() {
      return (
        attr(this.hsba, "emrTinhTrangRaVien.emrDmGiaiphaututhi") || {
          ma: "",
          ten: ""
        }
      );
    }
  },

  methods: {
    
    toCharArray(st) {
      return (st || "").replace(".", "").split("");
    },

    getTenKhoa(khoa) {
      return khoa.tenkhoa || attr(khoa, "emrDmKhoaDieuTri.ten");
    },

    formatNgayGio(ngaygio) {
      if (ngaygio.length == 16) {
        var ngay = ngaygio.substring(0, 2);
        var thang = ngaygio.substring(3, 5);
        var nam = ngaygio.substring(6, 10);
        var gio = ngaygio.substring(11, 13);
        var phut = ngaygio.substring(14, 16);
        return `${gio} giờ ${phut} ph ngày ${ngay}/${thang}/${nam}`;
      }
      return "... giờ ... ph ngày .../.../......";
    },

    formatNgay(ngaygio) {
      if (ngaygio.length >= 10) {
        var ngay = ngaygio.substring(0, 2);
        var thang = ngaygio.substring(3, 5);
        var nam = ngaygio.substring(6, 10);
        return `${ngay} tháng ${thang} năm ${nam}`;
      }
      return "... tháng ... năm ......";
    },

    formatNgay2(ngaygio) {
      if (ngaygio.length >= 10) {
        var ngay = ngaygio.substring(0, 2);
        var thang = ngaygio.substring(3, 5);
        var nam = ngaygio.substring(6, 10);
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
