var chandoan_hinhanh_script = {
  data: function() {
    return {
      hsId: 0
    }
  },  
  
  mounted: function () {
    this.hsId = getParam('hs_id');
  }
};