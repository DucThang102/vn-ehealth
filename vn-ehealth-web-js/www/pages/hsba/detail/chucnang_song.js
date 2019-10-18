var chucnang_song_script = {
  data: function() {
    return {
      hsba: null,
      khoadieutri: null,
      chucnangsong: null
    }
  },  
  
  mounted: async function () {
    var idhsba = getParam('hs_id');
    var vk_index = getParam('vk_index') || 0;
    var chucnangsong_index = getParam('chucnangsong_index') || 0;
    this.hsba = await this.get('/api/hsba/get_hs', {hoso_id : idhsba});
    if(this.hsba && vk_index < this.hsba.emrVaoKhoas.length) {
      this.khoadieutri = this.hsba.emrVaoKhoas[vk_index];
      if(chucnangsong_index < this.khoadieutri.emrChucNangSongs.length) {
        this.chucnangsong = this.khoadieutri.emrChucNangSongs[chucnangsong_index];
      }
    }
  }
};