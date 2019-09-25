var chucnang_song_script = {
  data: function() {
    return {
      hsId: 0
    }
  },  
  
  computed: {
    pdfURL : function() {
      return this.API_URL + "/api/hsba/view_pdf?loai_report=chucnangsong&idhsba=" + this.hsId;
    }
  },

  mounted: function () {
    this.hsId = getParam('hs_id');
  }
};