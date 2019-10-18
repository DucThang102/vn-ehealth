var giay_to_script = {
  data: function() {
    return {
      hsba: null,
      giayto: null
    }
  },  

  mounted: async function () {
    var idhsba = getParam('hs_id');
    var giayto_index = getParam('giayto_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && this.hsba.emrQuanLyFileDinhKemBenhAn.length > giayto_index) {
      this.giayto = this.hsba.emrQuanLyFileDinhKemBenhAn[giayto_index];
    }
  }
};