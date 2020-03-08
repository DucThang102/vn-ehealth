<template>
  <div v-if="hattList">
    <div class="row mt-3">
      <div class="col-12">
        <label class="label-title label-bold">Danh sách hình ảnh tổn thương</label>
        <router-link class="mt-1" title="Thêm" 
          :to="{name: 'chinhSuaTTHinhAnTonThuongItem',
                params: {hsbaId: hsbaId, hattId: '_'}}">
          <i class="fas fa-fw fa-lg fa-plus-circle"></i>
        </router-link>
      </div>
    </div>
    <table class="table table-bordered">
      <tr>
        <th style="width: 5%;" class="text-center">STT</th>
        <th style="width: 15%;" class="text-center">Thao tác</th>
        <th style="width: 30%;" class="text-center">Ảnh tổn thương</th>
        <th style="width: 50%;" class="text-center">Mô tả tổn thương</th>
      </tr>
      <tr v-for="(hatt, i) in hattList" v-bind:key="hatt.id">
        <td class="text-center">{{ i + 1 }}</td>
        <td class="text-center">
          <router-link title="Chỉnh sửa" 
            :to="{name: 'chinhSuaTTHinhAnTonThuongItem',
                  params: {hsbaId: hsbaId, hattId: hatt.id}}">
            <i class="fas fa-fw fa-lg fa-edit"></i>
          </router-link>

          <a href="#" v-on:click="editHatt(hatt)" title="Chỉnh sửa">
            
          </a>

          <a href="#" v-on:click="editFiles(hatt)" title="Quản lý file đính kèm">
            <i class="fas fa-fw fa-lg fa-file"></i>
          </a>

          <a href="#" v-on:click="deleteHatt(hatt.id)" title="Xóa">
            <i class="fas fa-fw fa-lg fa-trash" style="color:red"></i>
          </a>
        </td>
        <td class="text-center">{{ hatt.anhtonthuong }}</td>
        <td class="text-center">{{ hatt.motatonthuong }}</td>
      </tr>
      <tr v-if="hattList.length == 0">
        <td colspan="4">Không có dữ liệu</td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  props: ["hsbaId"],

  data: function(){
    return {
      hattList : null,
    }    
  },

  methods:  {
    getHattList: async function(){
      this.hattList = await this.get('/api/hatt/get_ds_hatt', { hsba_id: this.hsbaId });
    },

    deleteHatt: async function(id) {
      if(confirm('Bạn có muốn xóa ảnh tổn thương này không?')){
        var result = await this.get("/api/hatt/delete_hatt", {hatt_id: id});
        if(result.success) {
          this.getHattList();
        }else {
          alert('Lỗi xảy ra quá trình xóa');
        }
      }
    },
  },

  created: async function() {
    this.getHattList();
  }
};
</script>