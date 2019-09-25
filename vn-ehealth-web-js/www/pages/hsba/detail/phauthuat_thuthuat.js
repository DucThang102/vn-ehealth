var phauthuat_thuthuat_script = {
  data: function() {
    return {
      hsId: 0
    }
  },  

  computed: {
    pdfURL : function() {
      return "http://localhost:8000/api/hsba/view_pdf?loai_report=phauthuatthuthuat&idhsba=" + this.hsId;
    }
  },
  
  mounted: function () {
    this.hsId = getParam('hs_id');
  }
};