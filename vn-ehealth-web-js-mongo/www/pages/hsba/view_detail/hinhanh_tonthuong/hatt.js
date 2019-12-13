VueAsyncComponent(
  "hatt",
  "/pages/hsba/view_detail/hinhanh_tonthuong/hatt.html",
  {
    data: function() {
      return {
        hatt: null
      };
    },

    methods: {
      viewHatt: function(hatt) {
        this.hatt = hatt;
      },
      viewHattList: function() {
        this.hatt = null;
      }
    },

    props: ["hsba_id"]
  }
);

VueAsyncComponent(
  "hatt-list",
  "/pages/hsba/view_detail/hinhanh_tonthuong/hatt_list.html",
  {
    data: function() {
      return {
        hatt_list: null,
        hatt: null,
        hsba: null
      };
    },

    methods: {
      viewHatt: function(hatt) {
        this.hatt = hatt;
        $("#hattModal").modal();
      }
    },

    props: ["hsba_id"],

    created: async function() {
      if (this.hsba_id) {
        this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
          hsba_id: this.hsba_id
        });

        this.hatt_list = await this.get("/api/hatt/get_ds_hatt", {
          hsba_id: this.hsba_id
        });
        console.log(this.hatt_list);
      }
    }
  }
);

VueAsyncComponent(
  "hatt-view",
  "/pages/hsba/view_detail/hinhanh_tonthuong/hatt_view.html",
  {
    data: function() {
      return {
        hsba: null
      };
    },
    props: ["hatt", "hsba_id"],

    methods: {
      viewHattList: function() {
        this.$emit("viewHattList");
      }
    },

    created: async function() {
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
        hsba_id: this.hsba_id
      });
    }
  }
);
