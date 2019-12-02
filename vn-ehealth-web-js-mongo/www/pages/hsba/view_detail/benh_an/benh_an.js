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

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
      hsba_id: this.hsba_id
    });
    console.log(this.hsba);
  }
};

VueAsyncComponent(
  "benh-an",
  "/pages/hsba/view_detail/benh_an/benh_an.html",
  benh_an_script
);
