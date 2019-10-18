var don_thuoc_script = {
  data: function() {
    return {
      hsba: null,
      donthuoc: null
    }
  },  
  
  mounted: async function () {
    var idhsba = getParam('hs_id');
    var donthuoc_index = getParam('donthuoc_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && this.hsba.emrDonThuocs.length > donthuoc_index) {
      this.donthuoc = this.hsba.emrDonThuocs[donthuoc_index];
    }
  }
};