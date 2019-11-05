VueAsyncComponent('qlybn', '/pages/hsba/edit/qly_benhnhan/qlybn.html', {
    data: function() {
      return {
        tab: 0,
      }
    },

    props: ["hsba_id"],
  
    methods: {
      changeTab(tab){
        if(tab != this.tab && sessionStorage.getItem('dataChange')) {
          if(!confirm('Bạn sẽ mất dữ liệu đang sửa nếu chuyển tab, tiếp tục?')){
            return;
          }          
        }
        this.tab = tab;
      }
    },
});

var mixin = {
  data: function() {
    return {
      hsba: null
    }
  },

  props: ["hsba_id"],

  watch: {
    hsba: {
      handler: function (val, oldVal) {
        if (oldVal) {
          sessionStorage.setItem('dataChange', true);
        }
      },
      deep: true
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
    sessionStorage.removeItem('dataChange');
  }
};

VueAsyncComponent('tthc', '/pages/hsba/edit/qly_benhnhan/thongtin_hanhchinh.html', {
  mixins: [mixin],

  data: function() {
    return {
    }
  },

  methods: {
  },  
});

VueAsyncComponent('ttnb', '/pages/hsba/edit/qly_benhnhan/thongtin_nguoibenh.html', {
  mixins: [mixin],

  data: function() {
    return {
      khoadieutri: null
    }
  },
  methods: {
  },
});

VueAsyncComponent('chandoan', '/pages/hsba/edit/qly_benhnhan/chandoan.html', {
  mixins: [mixin],

  data: function() {
    return {
    }
  },
  methods: {
  },
});


VueAsyncComponent('ttrv', '/pages/hsba/edit/qly_benhnhan/tinhtrang_ravien.html', {
  mixins: [mixin],

  data: function() {
    return {
    }
  },

  methods: {
  },
});