VueAsyncComponent('danhmuc-popup', '/pages/hsba/edit/danhmuc_popup/danhmuc.html', {
  data: function () {
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
    }
  },

  props: ["id", "name", "dm_type", "title", "value", "level", "parent", "multi"],

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
        name: this.keyword,
        dm_type: this.dm_type,
        start: (this.currentPage-1) * this.perPage,
        count: this.perPage,
        level: this.level||0,
        parentCode: this.parent? this.parent.ma : ''

      };
      this.total = await this.get('/api/danhmuc/count_dm_list', params);      
      this.dmList = await this.get('/api/danhmuc/get_dm_list', params);

      if(this.multi){
        var selectedCodes = this.selectedItems.map(x => x.ma);
        this.dmList.forEach(x => x.selected = selectedCodes.includes(x.ma));
        var visibleItemCodes = this.dmList.filter(x => x.selected).map(x => x.ma);
        this.hiddenItems = this.selectedItems.filter(x => !visibleItemCodes.includes(x.ma));
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
      if(!this.multi){
        var selectedItem = this.dmList.find(x => x.ma == this.selectedItemCode);
        store.commit('setValue', {key: this.name, value: selectedItem});
      }else{
        this.updateSelectedItems();
        store.commit('setValue', {key: this.name, value: this.selectedItems});
      }
      $('#' + this.id).modal('hide');
    },    
  },

  created: async function() {
    this.getData();

    if(!this.value) return;

    if(!this.multi){
      this.selectedItemCode = this.value.ma;
    }else{
      this.selectedItems = this.value;
    }
  }
});