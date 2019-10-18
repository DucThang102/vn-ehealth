var chandoan_hinhanh_script = {
  data: function() {
    return {
      hsba: null,
      cdha: null
    }
  },  

  mounted: async function () {
    var idhsba = getParam('hs_id');
    var cdha_index = getParam('cdha_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && this.hsba.emrChanDoanHinhAnhs.length > cdha_index) {
      this.cdha = this.hsba.emrChanDoanHinhAnhs[cdha_index];
    }
  }
};