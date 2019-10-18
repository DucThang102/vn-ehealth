var giaiphau_benh_script = {
  data: function() {
    return {
      hsba: null,
      gpb: null
    }
  },  

  mounted: async function () {
    var idhsba = getParam('hs_id');
    var gpb_index = getParam('gpb_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && this.hsba.emrGiaiPhauBenhs.length > gpb_index) {
      this.gpb = this.hsba.emrGiaiPhauBenhs[gpb_index];
    }
  }
};