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

VueAsyncComponent(
  "benh-an-nhi",
  "/pages/hsba/view_detail/benh_an/benh_an_nhi.html",
  { props: ["hsba"] }
);

VueAsyncComponent(
  "benh-an-so-sinh",
  "/pages/hsba/view_detail/benh_an/benh_an_so_sinh.html",
  { props: ["hsba"] }
);

VueAsyncComponent(
  "benh-an-noi-khoa",
  "/pages/hsba/view_detail/benh_an/benh_an_noi_khoa.html",
  { props: ["hsba"] }
);

VueAsyncComponent(
  "benh-an-ngoai-khoa",
  "/pages/hsba/view_detail/benh_an/benh_an_ngoai_khoa.html",
  { props: ["hsba"] }
);

VueAsyncComponent(
  "benh-an-ngoai-tru",
  "/pages/hsba/view_detail/benh_an/benh_an_ngoai_tru.html",
  { props: ["hsba"] }
);
VueAsyncComponent(
  "benh-an-san-khoa",
  "/pages/hsba/view_detail/benh_an/benh_an_san_khoa.html",
  { props: ["hsba"] }
);
VueAsyncComponent(
  "benh-an-truyen-nhiem",
  "/pages/hsba/view_detail/benh_an/benh_an_truyen_nhiem.html",
  { props: ["hsba"] }
);
VueAsyncComponent(
  "benh-an-phu-khoa",
  "/pages/hsba/view_detail/benh_an/benh_an_phu_khoa.html",
  { props: ["hsba"] }
);
VueAsyncComponent(
  "benh-an-tam-than",
  "/pages/hsba/view_detail/benh_an/benh_an_tam_than.html",
  { props: ["hsba"] }
);
VueAsyncComponent(
  "benh-an-da-lieu",
  "/pages/hsba/view_detail/benh_an/benh_an_da_lieu.html",
  { props: ["hsba"] }
);
VueAsyncComponent(
  "benh-an-dd",
  "/pages/hsba/view_detail/benh_an/benh_an_dd.html",
  { props: ["hsba"] }
);
VueAsyncComponent(
  "benh-an-hhtm",
  "/pages/hsba/view_detail/benh_an/benh_an_hhtm.html",
  { props: ["hsba"] }
);
