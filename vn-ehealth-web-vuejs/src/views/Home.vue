<template>
  <div>
    <nav id="topbar" class="navbar navbar-expand bg-xanh">
      <div class="navbar-header1">
        <a class="navbar-brand1" href="/">
          <img src="@/assets/images/logo_VEIG2.svg" height="50" alt />
        </a>
        <h5 class="text3">Hệ thống</h5>
        <h4 class="text4 MT0 MB0">Bệnh án điện tử</h4>
      </div>

      <ul class="navbar-nav top-bar ml-auto">
        <li class="nav-item dropdown no-arrow">
          <a class="nav-link" href="#">
            <img class="img-profile rounded-circle" src="@/assets/images/MediaObj_Placeholder.png" />
            <span class="ml-2 d-none d-lg-inline text-white">admin</span>
          </a>
        </li>

        <li class="nav-item dropdown no-arrow">
          <a class="nav-link" href="javascript:void(0)" v-on:click="logOut()">
            <i class="fas fa-fw fa-sign-out-alt"></i>
          </a>
        </li>
      </ul>
    </nav>

    <nav id="menubar" class="navbar navbar-expand-lg navbar-light bg-white p-0">
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link class="nav-link" to="/dashboard">
            <i class="fas fa-lg fa-home black"></i>
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/benhnhan/timkiem">
            QUẢN LÝ BỆNH NHÂN
          </router-link>
        </li>
        <li class="nav-item dropdown">
          <a
            class="nav-link dropdown-toggle text-uppercase"
            href="#"
            id="hsbaDropdown"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >HỒ SƠ BỆNH ÁN</a>
          <div class="dropdown-menu" aria-labelledby="hsbaDropdown">
            <router-link class="dropdown-item" to="/">
              <i class="fas fa-angle-right black"></i> Chưa xử lý
            </router-link>

            <router-link class="dropdown-item" to="/hsba/daluu">
              <i class="fas fa-angle-right black"></i> Đã lưu
            </router-link>
          </div>
        </li>
        <li class="nav-item active dropdown">
          <a
            class="nav-link dropdown-toggle text-uppercase"
            href="#"
            id="hsbaDropdown"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >Danh mục</a>
          <div class="dropdown-menu" aria-labelledby="hsbaDropdown">
            <router-link class="dropdown-item" to="/danhmuc/dungchung">
              <i class="fas fa-angle-right black"></i> Dùng chung
            </router-link>
            <router-link class="dropdown-item" to="/danhmuc/chuanhoa">
              <i class="fas fa-angle-right black"></i> Chuẩn hóa
            </router-link>
          </div>
        </li>
      </ul>
    </nav>    
    <div id="content-wrapper" class="bg-light">
      <router-view />   
    </div>

    <footer class="sticky-footer bg-light">
      <div class="row">
        <div class="col-8">
          <h4 class="text-H">Bệnh viện Đa khoa Trung ương Cần Thơ</h4>
          <ul class="list-inline">
            <li class="list-inline-item">
              <i class="LineH fa fa-phone pull-left" aria-hidden="true"></i> Hỗ trợ: 097 667 61 29
            </li>
            <li class="list-inline-item">
              <i class="LineH fa fa-envelope pull-left" aria-hidden="true"></i> support@veig.vn
            </li>
          </ul>
        </div>
        <div class="col-4">
          <p class="mt-3 mr-3 text-right">Copyright © 2019 Công ty CP VEIG</p>
        </div>
      </div>
    </footer>    
  </div>
</template>

<script>
export default {
  methods: {
    logOut: function() {
      localStorage.removeItem("token");
      this.$router.push({name: 'login'});
    }
  }
}
</script>