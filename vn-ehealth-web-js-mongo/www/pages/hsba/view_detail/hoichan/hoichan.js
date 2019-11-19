VueAsyncComponent('hoichan', '/pages/hsba/view_detail/hoichan/hoichan.html', {
  data: function() {
    return {
      hoichan: null
    }
  },

  methods: {
    viewHoichan: function(hoichan) {
      this.hoichan = hoichan;
    },
    viewHoichanList: function() {
      this.hoichan = null;
    }
  },
  
  props: ["hsba_id"]
});

VueAsyncComponent('hoichan-list', '/pages/hsba/view_detail/hoichan/hoichan_list.html', {
  data: function(){
    return {
      hoichan_list : null
    }    
  },

  methods:  {
    viewHoichan : function(hoichan) {
      this.$emit('viewHoichan', hoichan);
    },
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || attr(khoadieutri, 'emrDmKhoaDieuTri.ten');
    }
  },

  props: ["hsba_id"],

  created: async function() {
    if(this.hsba_id) {
      this.hoichan_list = await this.get('/api/hoichan/get_ds_hoichan', { hsba_id: this.hsba_id });
    }
  }
});

VueAsyncComponent('hoichan-view', '/pages/hsba/view_detail/hoichan/hoichan_view.html', {
  data: function() {
    return {
      hsba: null
    }
  },
  props: ["hsba_id", "hoichan"],
  
  methods: {
    viewHoichanList: function() {
      this.$emit('viewHoichanList');
    },
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || attr(khoadieutri, 'emrDmKhoaDieuTri.ten');
    }
  },

  computed: {
    bacsichutoa: function(){
      var bacsi = this.hoichan.emrThanhVienHoiChans.find(x => attr(x, 'emrDmVaiTro.ma') == "1");
      if(bacsi){
        return bacsi.tenbacsi;
      }
      return "";
    },

    thuky: function() {
      var bacsi = this.hoichan.emrThanhVienHoiChans.find(x => attr(x, 'emrDmVaiTro.ma') == "2");
      if(bacsi){
        return bacsi.tenbacsi;
      }
      return "";
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
  }
});
