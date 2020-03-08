<template>
  <div>
    <div id="content" class="bg-white mt-2">
      <form class="mt-3" @submit.prevent="searchPatient()">
        <div class="row">
          <div class="col-10">
            <input
              class="form-control"
              v-model="keyword"
              placeholder="Tìm kiếm bệnh nhân"
              type="text"
            />
          </div>
          <div class="col-2">
            <button type="submit" class="btn btn btn-primary">
              <i class="fa fa-search faR10" aria-hidden="true"></i>Tìm kiếm
            </button>
          </div>
        </div>
      </form>

      <div v-if="patients" class="portlet-body">
        <table class="table mt-3 table-bordered">
          <tr class="table-active text-center">
            <th style="width: 5%">STT</th>
            <th style="width: 20%">Họ tên</th>
            <th style="width: 10%">Ngày sinh</th>
            <th style="width: 10%">Giới tính</th>
            <th style="width: 20%">Định danh y tế</th>
            <th style="width: 35%">Địa chỉ</th>
          </tr>

          <tr v-for="(patient, index) in currentItems" :key="patient.id">
            <td align="center">{{ index + (currentPage-1)*perPage + 1}}</td>
            <td>
              <router-link :to="{name: 'ttBenhNhanTongQuan', params: {patientId: patient.id}}">
                <span class="family-name">{{ patient.tendaydu }}</span>
              </router-link>
            </td>
            <td class="text-center">
              <span class="dob">{{ patient.ngaysinh }}</span>
            </td>
            <td class="text-center">
              <span class="gender">{{ patient.emrDmGioiTinh.ten }}</span>
            </td>
            <td class="text-center">
              <span class="identifier-system">{{ patient.iddinhdanhchinh }}</span>
            </td>
            <td>
              <span class="contact">{{ patient.diachi }}</span>
            </td>
          </tr>
        </table>

        <b-pagination v-if="rows>perPage" v-model="currentPage" :total-rows="rows" :per-page="perPage"></b-pagination>
        <p class="mt-3">Tổng số kết quả tìm kiếm: {{ rows }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .family-name {
    font-weight: bold;
    font-size: 90%;
  }

  .given-name {
    font-size: 90%;
  }

  .gender {
    font-weight: bold;
  }

  .dob {
    font-weight: bold;
  }

  .identifier-system {
    font-style: italic;
    font-size: 90%;
  }

  .identifier-number {
    font-weight: bold;
    font-size: 90%;
  }

  .contact-type {
    font-style: italic;
    font-size: 90%;
  }

  .contact {
    font-weight: bold;
    font-size: 90%;
  }
</style>

<script>
export default {
  data() {
    return {
      keyword: "",
      perPage: 10,
      currentPage: 1,
      patients: null
    }
  },

  methods: {
    searchPatient: async function() {      
      this.patients = await this.get("/api/benh_nhan/search_benhnhan", {
        keyword: this.keyword.toUpperCase()
      });
      this.currentPage = 1;
      this.$router.push(this.currentURL);
    }    
  },
  computed: {
    rows() {
      return this.patients.length;
    },
    currentItems() {
      return this.patients.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      );
    },
    currentURL() {
      return "/benhnhan/timkiem?" + this.serialize({keyword: this.keyword, current_page: this.currentPage});
    }
  },
  watch: {
    currentPage() {
      this.$router.push(this.currentURL);
    }
  },
  created: async function() {
    var parameters = this.$route.query;
    this.keyword = parameters.keyword;
    this.currentPage = parameters.current_page;
    if(this.currentPage) {
      this.searchPatient();
    }
  }
};
</script>