<template>
  <div id="content" class="bg-white m-2 p-2">
    <div v-if="items">
      <div v-if="total == 0">
        <b>Không có dữ liệu</b>
      </div>

      <div>
        <form @submit.prevent="search()" class="mb-3">
          <div class="w">
            <div class="box-content">
              <div class="row mb-2">
                <div class="col-4">
                  <strong>Mã/tên danh mục dùng chung:</strong>
                </div>
              </div>
              <form @submit.prevent="search()" class="search-detail">
                <input v-model="keyword" class="form-control" placeholder="Search " />
              </form>
            </div>
          </div>
        </form>
        <table class="table table-bordered table-width-100 mt-4" v-if="total > 0">
          <thead>
            <tr>
              <th style="width:5%">STT</th>
              <th style="width:20%">Mã loại danh mục</th>
              <th style="width:40%">Tên danh mục</th>
              <th style="width:10%">Trạng thái</th>
              <th style="width:15%">Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(dm, index) in items" :key="dm.id">
              <td align="center">{{ index + (currentPage-1)*perPage + 1 }}</td>
              <td>{{ dm.ma }}</td>
              <td>{{ dm.ten }}</td>
              <td>{{ dm.trang_thai }}</td>
              <td align="center">
                <a class="mr-2" href="#" title="Xem chi tiết danh mục">
                  <i class="fa fa-eye"></i>
                </a>
                <a class="mr-2" href="#" title="Quản lý phiên bản">
                  <i class="fa fa-cog"></i>
                </a>
                <a class="mr-2" href="#" title="Tải xuống">
                  <i class="fa fa-download"></i>
                </a>

                <a href="#" v-on:click="openUploadModal(dm.ma)">
                  <i class="fas fa-fw fa-edit"></i>
                </a>
              </td>
            </tr>
          </tbody>
        </table>
        <b-pagination
          v-if="total > perPage"
          size="sm"
          v-model="currentPage"
          :total-rows="total"
          :per-page="perPage"
        ></b-pagination>
        <label class="label">Tổng số : {{ total }} bản ghi</label>
      </div>
      <div class="modal fade" id="csvUpload">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Cập nhật danh mục dung chung</h5>
              <button type="button" class="close" data-dismiss="modal">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <form id="fmt">
                <input type="hidden" v-model="dmType" name="dmType" />
                Chọn file :
                <input type="file" id="csv_file" name="csv_file" />
                <input type="button" v-on:click="upload()" value="Upload" />
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      keyword: "",
      perPage: 10,
      currentPage: 1,
      items: null,
      total: null,
      dmType: ""
    };
  },

  methods: {
    getTotal: async function() {
      this.total = await this.get("/api/nhom_danhmuc/count_nhom_dm_list", {
        keyword: this.keyword
      });
    },

    getItems: async function() {
      var start = (this.currentPage - 1) * this.perPage;
      this.items = await this.get("/api/nhom_danhmuc/get_nhom_dm_list", {
        keyword: this.keyword,
        start: start,
        count: this.perPage
      });
      console.log(this.items);
    },

    getData: async function() {
      await this.getTotal();
      await this.getItems();
    },

    search: async function() {
      if (this.currentPage != 1) {
        this.currentPage = 1;
      } else {
        await this.getData();
      }
    },

    openUploadModal: function(ma) {
      this.dmType = ma;
      $("#csvUpload").modal();
    },

    upload: async function() {
      if (this.dmType != "") {
        var formData = new FormData(document.getElementById("fmt"));
        var response = await this.post("/api/danhmuc/import_dm_list", formData);
        var result = await response.json();
        if (result.success) {
          $("#csv_file").val("");
          $("#csvUpload").modal("hide");
        } else {
          alert("Lỗi khi upload.");
        }
      }
    },
    getCurrentURL: function() {
      return this.createURL("/pages/danhmuc/dm_dungchung.html", {
        keyword: this.keyword,
        currentPage: this.currentPage
      });
    }
  },
  watch: {
    currentPage: async function() {
      await this.getData();
    }
  },

  created: async function() {
    var parameters = this.$route.query;
    this.currentPage = parameters.current_page || 1;
    this.keyword = parameters.keyword || "";
    await this.getData();
  }
};
</script>

<style scoped>
</style>
