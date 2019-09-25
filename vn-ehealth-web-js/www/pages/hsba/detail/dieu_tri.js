var dieu_tri_script = {
  data: function() {
    return {
      hsId: 0
    }
  },  
  
  computed: {
    pdfURL : function() {
      return this.API_URL + "/api/hsba/view_pdf?loai_report=dieutri&idhsba=" + this.hsId;
    }
  },

  mounted: function () {
    this.hsId = getParam('hs_id');
  }
};