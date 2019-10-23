VueAsyncComponent('dieutri', '/pages/hsba/view_detail/dieutri/dieutri.html', {
  data: function() {
    return {
      dieutri: null
    }
  },

  methods: {
    xemDieuTri: function(dieutri) {
      this.dieutri = dieutri;
    },
    xemDsDieuTri: function() {
      this.dieutri = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('dieutri-list', '/pages/hsba/view_detail/dieutri/dieutri_list.html', {
  data: function(){
    return {
      dieutri_list : []
    }    
  },

  methods:  {
    xemDieuTri : function(dieutri) {
      this.$emit('xemDieuTri', dieutri);
    },
    getNgayDieuTri : function(dieutri) {
      var ngayDieuTris = dieutri.emrQuaTrinhDieuTris.map(x => parseDate(x.ngaydieutri));
      ngayDieuTris = ngayDieuTris.sort(x => x.getTime());
      
      if(ngayDieuTris.length == 0) {
        return "";
      }
      ngayBatDau = ngayDieuTris[0];
      ngayBatDau = ngayBatDau? formatDate(ngayBatDau.toISOString()) : "";
      
      ngayKetThuc = ngayDieuTris[ngayDieuTris.length-1];
      ngayKetThuc = ngayKetThuc? formatDate(ngayKetThuc.toISOString()) : "";

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
      this.dieutri_list = this.hsba.emrVaoKhoas.flatMap(x => x.emrDieuTris);
      this.dieutri_list.forEach(x => {
        x.emrVaoKhoa = this.hsba.emrVaoKhoas.find(vk => vk.id = x.idvaokhoa);
        x.ngaydieutri = this.getNgayDieuTri(x);
      });
    }      
  }  
});

VueAsyncComponent('dieutri-view', '/pages/hsba/view_detail/dieutri/dieutri_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "dieutri"],
  
  methods: {
    xemDsDieuTri: function() {
      this.$emit('xemDsDieuTri');
    },
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    }
  },
});