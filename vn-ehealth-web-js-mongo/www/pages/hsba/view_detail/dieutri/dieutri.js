VueAsyncComponent("dieutri", "/pages/hsba/view_detail/dieutri/dieutri.html", {
  data: function() {
    return {
      dieutri: null
    };
  },

  methods: {
    viewDieutri: function(dieutri) {
      this.dieutri = dieutri;
    },
    viewDieutriList: function() {
      this.dieutri = null;
    }
  },

  props: ["hsba_id"]
});

VueAsyncComponent(
  "dieutri-list",
  "/pages/hsba/view_detail/dieutri/dieutri_list.html",
  {
    data: function() {
      return {
        dieutri_list: null,
        dieutri: null,
        hsba: null
      };
    },

    methods: {
      viewDieutri: function(dieutri) {
        this.dieutri = dieutri;
        $("#dieutriModal").modal();
      },
      getNgayDieuTri: function(dieutri) {
		  var ngayDieuTris = dieutri.emrQuaTrinhDieuTris.map(x => x.ngaydieutri);
		  ngayDieuTris = ngayDieuTris.sort(x => parseDate(x).getTime());

		  if (ngayDieuTris.length == 0) {
			return "";
		  }
		  
		  let ngayBatDau = ngayDieuTris[0];
		  ngayBatDau = ngayBatDau ? this.formatDate(ngayBatDau) : "";

		  let ngayKetThuc = ngayDieuTris[ngayDieuTris.length - 1];
		  ngayKetThuc = ngayKetThuc ? this.formatDate(ngayKetThuc) : "";

		  if (ngayBatDau == ngayKetThuc) {
			return ngayBatDau;
		  } else {
			return "Từ " + ngayBatDau + " đến " + ngayKetThuc;
      },
      getTenKhoa: function(khoadieutri) {
        return khoadieutri.tenkhoa || attr(khoadieutri, "emrDmKhoaDieuTri.ten");
      }     
    },

    props: ["hsba_id"],

    created: async function() {
      if (this.hsba_id) {
        this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
          hsba_id: this.hsba_id
        });

        this.dieutri_list = await this.get("/api/dieutri/get_ds_dieutri", {
          hsba_id: this.hsba_id
        });
        console.log(this.dieutri_list);
        this.dieutri_list.forEach(x => {
          x.ngaydieutri = this.getNgayDieuTri(x);
        });
      }
    }
  }
);

VueAsyncComponent(
  "dieutri-view",
  "/pages/hsba/view_detail/dieutri/dieutri_view.html",
  {
    data: function() {
      return {
        hsba: null
      };
    },
    props: ["hsba_id", "dieutri"],

    methods: {
      viewDieutriList: function() {
        this.$emit("viewDieutriList");
      },
      getTenKhoa: function(khoadieutri) {
        return khoadieutri.tenkhoa || attr(khoadieutri, "emrDmKhoaDieuTri.ten");
      }
    },

    created: async function() {
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
        hsba_id: this.hsba_id
      });
    }
  }
);
