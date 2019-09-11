var giaiphau_benh_script = {
  data: function() {
    return {
      hsId: 0
    }
  },  
  
  mounted: function () {
    this.hsId = getParam('hs_id');
  }
};