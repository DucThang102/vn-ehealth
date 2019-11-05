var benh_an_script = {
  data: function() {
    return {
      hsId: 0
    }
  },

  props: ["hsba_id"],

  computed: {
    pdfURL : function() {
      return this.API_URL + "/api/hsba/view_pdf?hsba_id=" + this.hsba_id;
    }
  }  
};

VueAsyncComponent('benh-an', '/pages/hsba/view_detail/benh_an/benh_an.html', benh_an_script);