VueAsyncComponent(
  "chucnangsong",
  "/pages/hsba/view_detail/chucnangsong/chucnangsong.html",
  {
    data: function() {
      return {
        chucnangsong: null
      };
    },

    methods: {
      viewChucnangsong: function(chucnangsong) {
        this.chucnangsong = chucnangsong;
      },
      viewChucnangsongList: function() {
        this.chucnangsong = null;
      }
    },

    props: ["hsba_id"]
  }
);

VueAsyncComponent(
  "chucnangsong-list",
  "/pages/hsba/view_detail/chucnangsong/chucnangsong_list.html",
  {
    data: function() {
      return {
        chucnangsong_list: null,
        chucnangsong: null,
        hsba: null
      };
    },

    methods: {
      viewChucnangsong: function(chucnangsong) {
        this.chucnangsong = chucnangsong;
        $("#chucnangsongModal").modal();
      },
      getNgayTheoDoi: function(chucnangsong) {
        var ngayTheoDois = chucnangsong.emrChucNangSongChiTiets.map(x =>
          parseDate(x.ngaytheodoi)
        );
        ngayTheoDois = ngayTheoDois.sort(x => x.getTime());

        if (ngayTheoDois.length == 0) {
          return "";
        }
        ngayBatDau = ngayTheoDois[0];
        ngayBatDau = ngayBatDau ? ngayBatDau.substring(10) : "";

        ngayKetThuc = ngayTheoDois[ngayTheoDois.length - 1];
        ngayKetThuc = ngayKetThuc ? ngayKetThuc.substring(10) : "";

        if (ngayBatDau == ngayKetThuc) {
          return ngayBatDau;
        } else {
          return "Từ " + ngayBatDau + " đến " + ngayKetThuc;
        }
      },
      getTenKhoa: function(khoadieutri) {
        return khoadieutri.tenkhoa || attr(khoadieutri, "emrDmKhoaDieuTri.ten");
      }
    },

    props: ["hsba_id"],

    created: async function() {
      if (this.hsba_id) {
        this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
          hsba_id: this.hsba_id
        });

        this.chucnangsong_list = await this.get(
          "/api/chucnangsong/get_ds_chucnangsong",
          { hsba_id: this.hsba_id }
        );
        this.chucnangsong_list.forEach(x => {
          x.ngaytheodoi = this.getNgayTheoDoi(x);
        });
        console.log(this.chucnangsong_list);
      }
    }
  }
);

VueAsyncComponent(
  "chucnangsong-view",
  "/pages/hsba/view_detail/chucnangsong/chucnangsong_view.html",
  {
    data: function() {
      return {
        hsba: null
      };
    },
    props: ["hsba_id", "chucnangsong"],

    methods: {
      viewChucnangsongList: function() {
        this.$emit("viewChucnangsongList");
      },
      getTenKhoa: function(khoadieutri) {
        return khoadieutri.tenkhoa || attr(khoadieutri, "emrDmKhoaDieuTri.ten");
      }
    },

    created: async function() {
      this.hsba = await this.get("/api/hsba/get_hsba_by_id", {
        hsba_id: this.hsba_id
      });
    }
  }
);
