VueAsyncComponent('donthuoc', '/pages/hsba/view_detail/donthuoc/donthuoc.html', {
  data: function() {
    return {
      donthuoc: null
    }
  },

  methods: {
    viewDonthuoc: function(donthuoc) {
      this.donthuoc = donthuoc;
    },
    viewDonthuocList: function() {
      this.donthuoc = null;
    }
  },
  
  props: ["hsba_id"]
});

VueAsyncComponent('donthuoc-list', '/pages/hsba/view_detail/donthuoc/donthuoc_list.html', {
  data: function(){
    return {
      donthuoc_list : null
    }    
  },

  methods:  {
    viewDonthuoc : function(donthuoc) {
      this.$emit('viewDonthuoc', donthuoc);
    },
  },

  props: ["hsba_id"],

  created: async function() {
    if(this.hsba_id) {
      this.donthuoc_list = await this.get('/api/hsba/get_ds_donthuoc', { hsba_id: this.hsba_id });
    }
  }  
});

VueAsyncComponent('donthuoc-view', '/pages/hsba/view_detail/donthuoc/donthuoc_view.html', {
  data: function() {
    return {
      hsba: null
    }
  },
  props: ["hsba_id", "donthuoc"],
  
  methods: {
    viewDonthuocList: function() {
      this.$emit('viewDonthuocList');
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
  }
});