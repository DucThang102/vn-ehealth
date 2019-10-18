var hinhanh_tonthuong_script = {
  data: function() {
    return {
      hsba : null,
      hatt: null
    }
  },
  
  mounted: async function () {
    var idhsba = getParam('hs_id');
    var hatt_index = getParam('hatt_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && this.hsba.emrHinhAnhTonThuongs.length > hatt_index) {
      this.hatt = this.hsba.emrHinhAnhTonThuongs[hatt_index];
    }
  }
};