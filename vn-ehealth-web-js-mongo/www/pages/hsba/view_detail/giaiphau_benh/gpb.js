VueAsyncComponent("gpb", "/pages/hsba/view_detail/giaiphau_benh/gpb.html", {
  data: function() {
    return {
      gpb: null
    };
  },

  methods: {
    viewGpb: function(gpb) {
      this.gpb = gpb;
    },
    viewGpbList: function() {
      this.gpb = null;
    }
  },

  props: ["hsba_id"]
});

VueAsyncComponent(
  "gpb-list",
  "/pages/hsba/view_detail/giaiphau_benh/gpb_list.html",
  {
    data: function() {
      return {
        gpb_list: null,
        gpb: null,
        hsba: null
      };
    },

    methods: {
      viewGpb: function(gpb) {
        this.gpb = gpb;
        $("#gpbModal").modal();
      }
    },

    props: ["hsba_id"],

    created: async function() {
      if (this.hsba_id) {
        this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
          hsba_id: this.hsba_id
        });

        this.gpb_list = await this.get("/api/gpb/get_ds_gpb", {
          hsba_id: this.hsba_id
        });
        console.log(this.gpb_list);
      }
    }
  }
);

VueAsyncComponent(
  "gpb-view",
  "/pages/hsba/view_detail/giaiphau_benh/gpb_view.html",
  {
    data: function() {
      return {
        hsba: null
      };
    },
    props: ["hsba_id", "gpb"],

    methods: {
      viewGpbList: function() {
        this.$emit("viewGpbList");
      }
    },

    created: async function() {
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
        hsba_id: this.hsba_id
      });
    }
  }
);
