VueAsyncComponent(
  "xetnghiem",
  "/pages/hsba/view_detail/xetnghiem/xetnghiem.html",
  {
    data: function() {
      return {
        xetnghiem: null
      };
    },

    methods: {
      viewXetnghiem: function(xetnghiem) {
        this.xetnghiem = xetnghiem;
      },
      viewXetnghiemList: function() {
        this.xetnghiem = null;
      }
    },

    props: ["hsba_id"]
  }
);

VueAsyncComponent(
  "xetnghiem-list",
  "/pages/hsba/view_detail/xetnghiem/xetnghiem_list.html",
  {
    data: function() {
      return {
        xetnghiem_list: null,
        xetnghiem: null,
        hsba: null
      };
    },

    methods: {
      viewXetnghiem: function(xetnghiem) {
        this.xetnghiem = xetnghiem;
        $("#xetnghiemModal").modal();
      }
    },

    props: ["hsba_id"],

    created: async function() {
      if (this.hsba_id) {
        this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
          hsba_id: this.hsba_id
        });

        this.xetnghiem_list = await this.get(
          "/api/xetnghiem/get_ds_xetnghiem",
          {
            hsba_id: this.hsba_id
          }
        );
        this.xetnghiem_list.forEach(xn => {
          xn.emrXetNghiemDichVus = xn.emrXetNghiemDichVus.filter(xndv => 
              xndv.emrXetNghiemKetQuas.length > 0);
        });
      }
    }
  }
);

VueAsyncComponent(
  "xetnghiem-view",
  "/pages/hsba/view_detail/xetnghiem/xetnghiem_view.html",
  {
    data: function() {
      return {
        hsba: null
      };
    },
    props: ["hsba_id", "xetnghiem"],

    methods: {
      viewXetnghiemList: function() {
        this.$emit("viewXetnghiemList");
      }
    },

    created: async function() {
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
        hsba_id: this.hsba_id
      });
    }
  }
);
