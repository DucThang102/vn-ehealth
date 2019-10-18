var xet_nghiem_script = {
  data: function() {
    return {
      hsba: null,
      xetnghiem: null
    }
  },  
  
  mounted: async function () {
    var idhsba = getParam('hs_id');
    var xetnghiem_index = getParam('xetnghiem_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && this.hsba.emrXetNghiems.length > xetnghiem_index) {
      this.xetnghiem = this.hsba.emrXetNghiems[xetnghiem_index];
    }
  }
};