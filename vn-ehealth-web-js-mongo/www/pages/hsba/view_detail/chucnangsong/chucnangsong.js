VueAsyncComponent('chucnangsong', '/pages/hsba/view_detail/chucnangsong/chucnangsong.html', {
  data: function() {
    return {
      chucnangsong: null
    }
  },

  methods: {
    xemChucNangSong: function(chucnangsong) {
      this.chucnangsong = chucnangsong;
    },
    xemDsChucNangSong: function() {
      this.chucnangsong = null;
    }
  },
  
  props: ["hsba"]
});

VueAsyncComponent('chucnangsong-list', '/pages/hsba/view_detail/chucnangsong/chucnangsong_list.html', {
  data: function(){
    return {
      chucnangsong_list : []
    }    
  },

  methods:  {
    xemChucNangSong : function(chucnangsong) {
      this.$emit('xemChucNangSong', chucnangsong);
    },
    getNgayTheoDoi : function(chucnangsong) {
      var ngayTheoDois = chucnangsong.emrChucNangSongChiTiets.map(x => parseDate(x.ngaytheodoi));
      ngayTheoDois = ngayTheoDois.sort(x => x.getTime());
      
      if(ngayTheoDois.length == 0) {
        return "";
      }
      ngayBatDau = ngayTheoDois[0];
      ngayBatDau = ngayBatDau? ngayBatDau.substring(10) : "";
      
      ngayKetThuc = ngayTheoDois[ngayTheoDois.length-1];
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
      this.chucnangsong_list = this.hsba.emrVaoKhoas.flatMap(x => x.emrChucNangSongs);
      this.chucnangsong_list.forEach(x => {
        x.emrVaoKhoa = this.hsba.emrVaoKhoas.find(vk => vk.id = x.emrVaoKhoaId);
        x.ngaytheodoi = this.getNgayTheoDoi(x);
      });
    }      
  }  
});

VueAsyncComponent('chucnangsong-view', '/pages/hsba/view_detail/chucnangsong/chucnangsong_view.html', {
  data: function() {
    return {
    }
  },
  props: ["hsba", "chucnangsong"],
  
  methods: {
    xemDsChucNangSong: function() {
      this.$emit('xemDsChucNangSong');
    },
    getTenKhoa: function(khoadieutri){
      return khoadieutri.tenkhoa || khoadieutri.emrDmKhoaDieuTri.ten;
    }
  },
});