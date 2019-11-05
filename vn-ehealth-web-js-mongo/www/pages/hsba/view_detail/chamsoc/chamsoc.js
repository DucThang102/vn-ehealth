VueAsyncComponent('chamsoc', '/pages/hsba/view_detail/chamsoc/chamsoc.html', {
  data: function() {
    return {
      chamsoc: null
    }
  },

  methods: {
    viewChamsoc: function(chamsoc) {
      this.chamsoc = chamsoc;
    },
    viewChamsocList: function() {
      this.chamsoc = null;
    }
  },
  
  props: ["hsba_id"]
});

VueAsyncComponent('chamsoc-list', '/pages/hsba/view_detail/chamsoc/chamsoc_list.html', {
  data: function(){
    return {
      chamsoc_list : null
    }    
  },

  methods:  {
    viewChamsoc : function(chamsoc) {
      this.$emit('viewChamsoc', chamsoc);
    },
    getNgayChamSoc : function(chamsoc) {
      var ngayChamSocs = chamsoc.emrQuaTrinhChamSocs.map(x => parseDate(x.ngaychamsoc));
      ngayChamSocs = ngayChamSocs.sort(x => x.getTime());
      
      if(ngayChamSocs.length == 0) {
        return "";
      }
      ngayBatDau = ngayChamSocs[0];
      ngayBatDau = ngayBatDau? ngayBatDau.substring(10) : "";
      
      ngayKetThuc = ngayChamSocs[ngayChamSocs.length-1];
      ngayKetThuc = ngayKetThuc? ngayKetThuc.substring(10) : "";

      if(ngayBatDau == ngayKetThuc){
        return ngayBatDau
      }else{
        return "Từ " + ngayBatDau + " đến " + ngayKetThuc;
      }
    },
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    }
  },

  props: ["hsba_id"],

  created: async function() {
    if(this.hsba_id) {
      var emrVaoKhoas = await this.get('/api/hsba/get_ds_vaokhoa', { hsba_id: this.hsba_id });
      this.chamsoc_list = emrVaoKhoas.flatMap(x => x.emrChamSocs);
      this.chamsoc_list.forEach(x => {
        x.emrVaoKhoa = emrVaoKhoas.find(vk => vk.id = x.emrVaoKhoaId);
        x.ngaychamsoc = this.getNgayChamSoc(x);
      });
    }
  } 
});

VueAsyncComponent('chamsoc-view', '/pages/hsba/view_detail/chamsoc/chamsoc_view.html', {
  data: function() {
    return {
      hsba: null
    }
  },
  props: ["hsba_id", "chamsoc"],
  
  methods: {
    viewChamsocList: function() {
      this.$emit('viewChamsocList');
    },
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    }
  },

  created: async function() {
    this.hsba = await this.get("/api/hsba/get_hsba_by_id", {"hsba_id": this.hsba_id});
  }
});