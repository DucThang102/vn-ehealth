VueAsyncComponent('chamsoc', '/pages/hsba/view_detail/chamsoc/chamsoc.html', {
  data: function() {
    return {
      chamsoc: null
    }
  },

  methods: {
    xemChamSoc: function(chamsoc) {
      this.chamsoc = chamsoc;
    },
    xemDsChamSoc: function() {
      this.chamsoc = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('chamsoc-list', '/pages/hsba/view_detail/chamsoc/chamsoc_list.html', {
  data: function(){
    return {
      chamsoc_list : []
    }    
  },

  methods:  {
    xemChamSoc : function(chamsoc) {
      this.$emit('xemChamSoc', chamsoc);
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

  props: ["hsba"],

  mounted: function() {
    if(this.hsba) {
      this.chamsoc_list = this.hsba.emrVaoKhoas.flatMap(x => x.emrChamSocs);
      this.chamsoc_list.forEach(x => {
        x.emrVaoKhoa = this.hsba.emrVaoKhoas.find(vk => vk.id = x.emrVaoKhoaId);
        x.ngaychamsoc = this.getNgayChamSoc(x);
      });
    }      
  }  
});

VueAsyncComponent('chamsoc-view', '/pages/hsba/view_detail/chamsoc/chamsoc_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "chamsoc"],
  
  methods: {
    xemDsChamSoc: function() {
      this.$emit('xemDsChamSoc');
    },
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    }
  },
});