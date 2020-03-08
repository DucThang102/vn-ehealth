<template>
  <div class="modal fade" :id="id" v-if="dmList">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">{{ title }}</h5>
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <div class="container">
            <form method="POST" @submit.prevent="search()">
              <div class="row mb-3">
                <div class="col-12">
                  <input
                    v-model="keyword"
                    placeholder="Nhập tên/mã để tìm kiếm"
                    class="form-control"
                  />
                </div>
              </div>
            </form>
            <div class="row mt-1" v-for="(dm,i) in dmList" :key="dm.id">
              <div class="col-12" v-if="!multi">
                <input type="radio" :value="dm.ma" v-model="selectedItemCode" />
                <label>{{ dm.ten }}</label>
                <span v-if="showCode">({{ dm.ma }})</span>
              </div>
              <div class="col-12" v-if="multi">
                <input type="checkbox" :value="dm.ma" v-model="dmList[i].selected" />
                <label>{{ dm.ten }}</label>
                <span v-if="showCode">({{ dm.ma }})</span>
              </div>
            </div>
          </div>
          <div v-if="loading" style="position: absolute;left: 50%;top:50%" class="spinner-border"></div>
          <div class="row" v-if="total > perPage">
            <div class="col-12 pl-4 pt-3">
              <b-pagination size="sm" :total-rows="total" v-model="currentPage" :per-page="perPage"></b-pagination>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
          <button v-on:click="selectItem()" type="button" class="btn btn-primary">Chọn</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: function() {
    return {
      dmList: null,
      total: 0,
      selectedItemCode: "",
      selectedItems: [],
      hiddenItems: [],
      keyword: "",
      perPage: 10,
      currentPage: 1,
      loading: false
    };
  },

  props: [
    "id",
    "name",
    "dmType",
    "title",
    "value",
    "level",
    "parent",
    "multi",
    "showCode"
  ],

  watch: {
    currentPage: async function() {
      this.loading = true;
      this.getData();
      this.loading = false;
    },
    parent: function() {
      this.getData();
    }
  },

  methods: {
    getData: async function() {
      var params = {
        keyword: this.keyword,
        dm_type: this.dmType,
        start: (this.currentPage - 1) * this.perPage,
        count: this.perPage,
        level: this.level || 0,
        parentCode: this.parent ? this.parent.ma : ""
      };

      this.total = await this.get("/api/danhmuc/count_dm_list", params);
      this.dmList = await this.get("/api/danhmuc/get_dm_list", params);

      if (this.multi) {
        var selectedCodes = this.selectedItems.map(x => x.ma);
        this.dmList.forEach(x => (x.selected = selectedCodes.includes(x.ma)));
        var visibleItemCodes = this.dmList
          .filter(x => x.selected)
          .map(x => x.ma);
        this.hiddenItems = this.selectedItems.filter(
          x => !visibleItemCodes.includes(x.ma)
        );
      }
    },

    updateSelectedItems: function() {
      this.selectedItems = this.dmList.filter(x => x.selected);
      this.selectedItems = this.hiddenItems.concat(this.selectedItems);
    },

    search: async function() {
      this.updateSelectedItems();
      this.currentPage = 1;
      this.getData();
    },

    selectItem: function() {
      if (!this.multi) {
        var selectedItem = this.dmList.find(x => x.ma == this.selectedItemCode);
        this.$store.commit("setValue", { key: this.name, value: selectedItem });
      } else {
        this.updateSelectedItems();
        this.$store.commit("setValue", { key: this.name, value: this.selectedItems });
      }
      $("#" + this.id).modal("hide");
    }
  },

  created: async function() {
    this.getData();

    if (!this.value) return;

    if (!this.multi) {
      this.selectedItemCode = this.value.ma;
    } else {
      this.selectedItems = this.value;
    }
  }
};
</script>
