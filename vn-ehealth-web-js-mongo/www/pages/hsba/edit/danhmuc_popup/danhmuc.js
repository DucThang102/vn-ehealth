VueAsyncComponent('danhmuc-popup', '/pages/hsba/edit/danhmuc_popup/danhmuc.html', {
  data: function () {
    return {
      dmList: null,
      total: 0,
      selectedItemCode: "",
      keyword: "",
      perPage: 10,
      currentPage: 1,
      loading: false
    }
  },

  props: ["id", "name", "dm_type", "title", "value", "level", "parent"],

  watch: {
    currentPage: async function() {
      this.loading = true;
      this.getData();
      this.loading = false;
    }
  },

  methods: {
    getData: async function() {
      var params = {
        name: this.keyword,
        dm_type: this.dm_type,
        start: (this.currentPage-1) * this.perPage,
        count: this.perPage,
        level: this.level||0,
        parentCode: this.parent? this.parent.ma : ''

      };
      this.total = await this.get('/api/danhmuc/count_dm_list', params);      
      this.dmList = await this.get('/api/danhmuc/get_dm_list', params);
    },

    search: async function() {
      this.currentPage = 1;
      this.getData();
    },

    selectItem: function() {
      var selectedItem = this.dmList.find(x => x.ma == this.selectedItemCode);
      if(selectedItem){        
        store.commit('setValue', {key: this.name, value: selectedItem});
      }
      this.selectedItemCode = "";
      $('#' + this.id).modal('hide');
    },    
  },

  created: async function() {
    if(this.value){
      this.selectedItemCode = this.value.ma;
    }
    this.getData();
  }
});