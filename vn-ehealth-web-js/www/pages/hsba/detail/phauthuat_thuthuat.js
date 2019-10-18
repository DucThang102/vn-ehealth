var phauthuat_thuthuat_script = {
  data: function() {
    return {
      hsba : null,
      pttt : null,
    }
  },  

  mounted: async function () {
    var idhsba = getParam('hs_id');
    var pttt_index = getParam('pttt_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && this.hsba.emrPhauThuatThuThuats.length > pttt_index) {
      this.pttt = this.hsba.emrPhauThuatThuThuats[pttt_index];
    }
  }
};