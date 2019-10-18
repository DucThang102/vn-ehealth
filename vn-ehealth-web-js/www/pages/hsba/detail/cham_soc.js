var cham_soc_script = {
  data: function() {
    return {
      hsba: null,
      khoadieutri: null,
      chamsoc: null
    }
  },  

  mounted: async function () {
    var idhsba = getParam('hs_id');
    var vk_index = getParam('vk_index') || 0;
    var chamsoc_index = getParam('chamsoc_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && vk_index < this.hsba.emrVaoKhoas.length) {
      this.khoadieutri = this.hsba.emrVaoKhoas[vk_index];
      if(chamsoc_index < this.khoadieutri.emrChamSocs.length) {
        this.chamsoc = this.khoadieutri.emrChamSocs[chamsoc_index];
      }
    }
  }
};