VueAsyncComponent("tdcn", "/pages/hsba/view_detail/thamdo_chucnang/tdcn.html", {
  data: function() {
    return {
      tdcn: null
    };
  },

  methods: {
    viewTdcn: function(tdcn) {
      this.tdcn = tdcn;
    },
    viewTdcnList: function() {
      this.tdcn = null;
    }
  },

  props: ["hsba_id"]
});

VueAsyncComponent(
  "tdcn-list",
  "/pages/hsba/view_detail/thamdo_chucnang/tdcn_list.html",
  {
    data: function() {
      return {
        tdcn_list: null,
        tdcn: null,
        hsba: null
      };
    },

    methods: {
      viewTdcn: function(tdcn) {
        this.tdcn = tdcn;
        $("#tdcnModal").modal();
      }
    },

    props: ["hsba_id"],

    created: async function() {
      if (this.hsba_id) {
        this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
          hsba_id: this.hsba_id
        });

        this.tdcn_list = await this.get("/api/tdcn/get_ds_tdcn", {
          hsba_id: this.hsba_id
        });
        console.log(this.tdcn_list);
      }
    }
  }
);

VueAsyncComponent(
  "tdcn-view",
  "/pages/hsba/view_detail/thamdo_chucnang/tdcn_view.html",
  {
    data: function() {
      return {
        hsba: null
      };
    },
    props: ["hsba_id", "tdcn"],

    methods: {
      viewTdcnList: function() {
        this.$emit("viewTdcnList");
      }
    },

    created: async function() {
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
        hsba_id: this.hsba_id
      });
    }
  }
);
