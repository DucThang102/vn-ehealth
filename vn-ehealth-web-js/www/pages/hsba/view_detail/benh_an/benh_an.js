var benh_an_script = {
  data: function() {
    return {
      hsId: 0
    }
  },

  computed: {
    pdfURL : function() {
      return this.API_URL + "/api/hsba/view_pdf?idhsba=" + this.hsId;
    }
  },
  
  mounted: function () {
    this.hsId = getParam('hs_id');
  }
};

VueAsyncComponent('benh-an', '/pages/hsba/view_detail/benh_an/benh_an.html', benh_an_script);