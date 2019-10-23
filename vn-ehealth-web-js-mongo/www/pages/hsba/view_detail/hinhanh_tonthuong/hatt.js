VueAsyncComponent('hatt', '/pages/hsba/view_detail/hinhanh_tonthuong/hatt.html', {
  data: function() {
    return {
      hatt: null
    }
  },

  methods: {
    xemHatt: function(hatt) {
      this.hatt = hatt;
    },
    xemDsHatt: function() {
      this.hatt = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('hatt-list', '/pages/hsba/view_detail/hinhanh_tonthuong/hatt_list.html', {
  data: function(){
    return {
      hatt_list : []
    }    
  },

  methods:  {
    xemHatt : function(hatt) {
      this.$emit('xemHatt', hatt);
    },
  },

  props: ["hsba"],

  mounted: function() {
    if(this.hsba) {
      this.hatt_list = this.hsba.emrHinhAnhTonThuongs;
    }
  }  
});

VueAsyncComponent('hatt-view', '/pages/hsba/view_detail/hinhanh_tonthuong/hatt_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "hatt"],
  
  methods: {
    xemDsHatt: function() {
      this.$emit('xemDsHatt');
    }
  },
});
