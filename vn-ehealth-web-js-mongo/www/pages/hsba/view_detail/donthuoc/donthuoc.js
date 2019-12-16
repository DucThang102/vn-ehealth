VueAsyncComponent(
  "donthuoc",
  "/pages/hsba/view_detail/donthuoc/donthuoc.html",
  {
    data: function() {
      return {
        donthuoc: null
      };
    },

    methods: {
      viewDonthuoc: function(donthuoc) {
        this.donthuoc = donthuoc;
      },
      viewDonthuocList: function() {
        this.donthuoc = null;
      }
    },

    props: ["hsba_id"]
  }
);

VueAsyncComponent(
  "donthuoc-list",
  "/pages/hsba/view_detail/donthuoc/donthuoc_list.html",
  {
    data: function() {
      return {
        donthuoc_list: null,
        donthuoc: null,
        hsba: null
      };
    },

    methods: {
      viewDonthuoc: function(donthuoc) {
        this.donthuoc = donthuoc;
        $("#donthuocModal").modal();
      }
    },

    props: ["hsba_id"],

    created: async function() {
      if (this.hsba_id) {
        this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
          hsba_id: this.hsba_id
        });

        this.donthuoc_list = await this.get("/api/donthuoc/get_ds_donthuoc", {
          hsba_id: this.hsba_id
        });
        console.log(this.donthuoc_list);
      }
    }
  }
);

VueAsyncComponent(
  "donthuoc-view",
  "/pages/hsba/view_detail/donthuoc/donthuoc_view.html",
  {
    data: function() {
      return {
        hsba: null
      };
    },
    props: ["hsba_id", "donthuoc"],

    methods: {
      viewDonthuocList: function() {
        this.$emit("viewDonthuocList");
      }
    },

    created: async function() {
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
        hsba_id: this.hsba_id
      });
    }
  }
);
