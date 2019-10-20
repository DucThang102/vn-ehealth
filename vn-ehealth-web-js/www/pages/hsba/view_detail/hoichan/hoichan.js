VueAsyncComponent('hoichan', '/pages/hsba/view_detail/hoichan/hoichan.html', {
  data: function() {
    return {
      hoichan: null
    }
  },

  methods: {
    xemHoiChan: function(hoichan) {
      this.hoichan = hoichan;
    },
    xemDsHoiChan: function() {
      this.hoichan = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('hoichan-list', '/pages/hsba/view_detail/hoichan/hoichan_list.html', {
  data: function(){
    return {
      hoichan_list : []
    }    
  },

  methods:  {
    xemHoiChan : function(hoichan) {
      this.$emit('xemHoiChan', hoichan);
    }
  },

  props: ["hsba"],

  mounted: function() {
    if(this.hsba) {
      this.hoichan_list = this.hsba.emrVaoKhoas.flatMap(x => x.emrHoiChans);
      this.hoichan_list.forEach(x => {
        x.emrVaoKhoa = this.hsba.emrVaoKhoas.find(vk => vk.id = x.idvaokhoa);
      });
    }      
  }  
});

VueAsyncComponent('hoichan-view', '/pages/hsba/view_detail/hoichan/hoichan_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "hoichan"],
  
  methods: {
    xemDsHoiChan: function() {
      this.$emit('xemDsHoiChan');
    }
  },
});
