var xet_nghiem_script = {
  data: function() {
    return {
      hsId: 0
    }
  },  

  computed: {
    pdfURL : function() {
      return "http://34.87.51.9:8000/api/hsba/view_pdf?loai_report=xetnghiem&idhsba=" + this.hsId;
    }
  },
  
  mounted: function () {
    this.hsId = getParam('hs_id');
  }
};