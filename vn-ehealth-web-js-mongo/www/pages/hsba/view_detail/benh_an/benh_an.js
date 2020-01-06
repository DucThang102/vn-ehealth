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
      var body = $("#benhan_new").html();
      var wnd = window.open('', '');//window.open('', '', 'width=1024');
      wnd.document.write(`<html><head>
      <title>Tờ bệnh án</title>
      <link href="/css/fontawesome/all.css" rel="stylesheet" type="text/css" />
      <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet" />      
      <link href="/css/bootstrap.css" rel="stylesheet" />
      <link href="http://unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.min.css"  rel="stylesheet"/>
      <link href="/css/style.css" rel="stylesheet" />
      <link href="/css/benhan.css" rel="stylesheet" />
      <script src="/js/jquery.min.js"></script>
      <script>
      $(document).ready(function(){ window.print() });
      </script>
      
      `);

      wnd.document.write('</head><body >');
      wnd.document.write(body);
      wnd.document.write('</body></html>');
      wnd.document.close();
      
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
      hsba_id: this.hsba_id
    });
    console.log(this.hsba);
  }
};

VueAsyncComponent(
  "benh-an",
  "/pages/hsba/view_detail/benh_an/benh_an_new.html",
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
