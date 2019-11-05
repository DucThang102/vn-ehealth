VueAsyncComponent('xetnghiem', '/pages/hsba/view_detail/xetnghiem/xetnghiem.html', {
  data: function() {
    return {
      xetnghiem: null
    }
  },

  methods: {
    viewXetnghiem: function(xetnghiem) {
      this.xetnghiem = xetnghiem;
    },
    viewXetnghiemList: function() {
      this.xetnghiem = null;
    }
  },
  
  props: ["hsba_id"]
});

VueAsyncComponent('xetnghiem-list', '/pages/hsba/view_detail/xetnghiem/xetnghiem_list.html', {
  data: function(){
    return {
      xetnghiem_list : null
    }    
  },

  methods:  {
    viewXetnghiem : function(xetnghiem) {
      this.$emit('viewXetnghiem', xetnghiem);
    },
  },

  props: ["hsba_id"],

  created: async function() {
    if(this.hsba_id) {
      this.xetnghiem_list = await this.get('/api/hsba/get_ds_xetnghiem', { hsba_id: this.hsba_id });
    }
  }  
});

VueAsyncComponent('xetnghiem-view', '/pages/hsba/view_detail/xetnghiem/xetnghiem_view.html', {
  data: function() {
    return {
      hsba: null
    }
  },
  props: ["hsba_id", "xetnghiem"],
  
  methods: {
    viewXetnghiemList: function() {
      this.$emit('viewXetnghiemList');
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
  }
});