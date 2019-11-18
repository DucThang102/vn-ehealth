VueAsyncComponent('yhhd', '/pages/hsba/edit/yhoc_hiendai/yhhd.html', {
  data: function() {
    return {
      tab: 0,
    }
  },  

  props: ["hsba_id"],

  methods: {
    changeTab(tab){
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

  methods : {
    saveHsba : async function() {
      var result = await this.post("/api/hsba/update_hsba", this.hsba);
      if(result.success) {
        console.log(result.emrHoSoBenhAn);
        alert('Cập nhật thông tin thành công');
      }else {
        alert('Lỗi xảy ra quá trình lưu thông tin');
      }
    }
  },

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
  }
};


VueAsyncComponent('hoibenh', '/pages/hsba/edit/yhoc_hiendai/hoibenh.html', {
  mixins: [mixin],  
});

VueAsyncComponent('khambenh', '/pages/hsba/edit/yhoc_hiendai/khambenh.html', {
  mixins: [mixin],
});

VueAsyncComponent('huongdieutri', '/pages/hsba/edit/yhoc_hiendai/huongdieutri.html', {  
  mixins: [mixin],  
});

  