VueAsyncComponent(
  "cdha",
  "/pages/hsba/view_detail/chandoan_hinhanh/cdha.html",
  {
    data: function() {
      return {
        cdha: null
      };
    },
    methods: {
      viewCdha: function(cdha) {
        this.cdha = cdha;
      },
      viewCdhaList: function() {
        this.cdha = null;
      }
    },
    props: ["hsba_id"]
  }
);

VueAsyncComponent(
  "cdha-list",
  "/pages/hsba/view_detail/chandoan_hinhanh/cdha_list.html",
  {
    data: function() {
      return {
        patientId: 0,
        cdha_list: null,
        cdha: null,
        hsba: null
      };
    },

    methods: {
      viewCdha: function(cdha) {
        this.cdha = cdha;
        $("#cdhaModal").modal();
      }
    },

    props: ["hsba_id"],

    created: async function() {
      if (this.hsba_id) {
        this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
          hsba_id: this.hsba_id
        });

        this.cdha_list = await this.get("/api/cdha/get_ds_cdha", {
          hsba_id: this.hsba_id
        });
        console.log(this.cdha_list);
      }
    }
  }
);

VueAsyncComponent(
  "cdha-view",
  "/pages/hsba/view_detail/chandoan_hinhanh/cdha_view.html",
  {
    data: function() {
      return {
        hsba: null
      };
    },
    props: ["hsba_id", "cdha"],

    methods: {
      viewCdhaList: function() {
        this.$emit("viewCdhaList");
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
    },

    created: async function() {
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
        hsba_id: this.hsba_id
      });
    }
  }
);
