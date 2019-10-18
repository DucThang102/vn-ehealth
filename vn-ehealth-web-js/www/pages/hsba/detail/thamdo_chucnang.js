var thamdo_chucnang_script = {
  data: function() {
    return {
      hsba: null,
      tdcn: null
    }
  },  

  mounted: async function () {
    var idhsba = getParam('hs_id');
    var tdcn_index = getParam('tdcn_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && this.hsba.emrThamDoChucNangs.length > tdcn_index) {
      this.tdcn = this.hsba.emrThamDoChucNangs[tdcn_index];
    }
  }
};