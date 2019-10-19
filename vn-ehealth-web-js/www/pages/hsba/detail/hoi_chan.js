var hoi_chan_script = {
  data: function() {
    return {
      hsba: null,
      khoadieutri: null,
      hoichan: null
    }
  },  

  computed : {
    bacsichutoa : function() {
      var bs = this.hoichan.emrHoiDongHoiChans.find(x => x.idvaitro == 1);
      if(bs) {
        return bs.bacsihoichan;
      }else{
        return "";
      }
    },
    thuky : function() {
      var bs = this.hoichan.emrHoiDongHoiChans.find(x => x.idvaitro == 2);
      if(bs) {
        return bs.bacsihoichan;
      }else{
        return "";
      }
    }
  },

  mounted: async function () {
    var idhsba = getParam('hs_id');
    var vk_index = getParam('vk_index') || 0;
    var hoichan_index = getParam('hoichan_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && vk_index < this.hsba.emrVaoKhoas.length) {
      this.khoadieutri = this.hsba.emrVaoKhoas[vk_index];
      if(hoichan_index < this.khoadieutri.emrHoiChans.length) {
        this.hoichan = this.khoadieutri.emrHoiChans[hoichan_index];
      }
    }
  }
};