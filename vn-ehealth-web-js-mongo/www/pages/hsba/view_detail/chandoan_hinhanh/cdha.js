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
        cdha_list: null
      };
    },

    methods: {
      viewCdha: function(cdha) {
        this.$emit("viewCdha", cdha);
      }
    },

    props: ["hsba_id"],

    created: async function() {
      if (this.hsba_id) {
        this.cdha_list = await this.get("/api/cdha/get_ds_cdha", {
          hsba_id: this.hsba_id
        });
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
      }
    },

    created: async function() {
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
        hsba_id: this.hsba_id
      });
    }
  }
);
