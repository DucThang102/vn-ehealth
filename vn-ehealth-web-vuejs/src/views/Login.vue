<template>
  <div>
    <div>
      <div class="bg1">
        <ul class="text-menu">
          <li>
            <a href>Giới thiệu</a>
          </li>
          <li>
            <a class="linenone" href>Liên hệ</a>
          </li>
        </ul>
      </div>
      <div>
        <p class="text-center mt-30">
          <img class="logo" src="@/assets/images/logo_VEIG.svg" alt />
        </p>
        <h3 class="text-center text1">
          <!--Bệnh viện Trung ương-->
        </h3>

        <h1 class="text-center text2 MT0">Hệ thống Bệnh án điện tử</h1>
      </div>
      <div class="login-form">
        <form @submit.prevent="logIn()" class="form-signin">
          <div class="form-group mb-3">
            <input v-model="username" class="form-control" placeholder="Tên tài khoản" />
          </div>
          <div class="form-group mb-3">
            <input v-model="password" type="password" class="form-control" placeholder="Mật khẩu" />
          </div>
          <div class="form-group">
            <span style="color:red">{{error}}</span>
          </div>
          <div class="form-group mb-3">
            <button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>
          </div>
          <div class="clearfix">
            <label class="pull-left checkbox-inline text-blue">
              <input type="checkbox" />Nhớ tài khoản
            </label>
            <a href="#" class="float-right">Quên mật khẩu?</a>
          </div>
        </form>
        <p class="text-center">
          <a href="/registration">Đăng ký tài khoản</a>
        </p>
      </div>
    </div>
    <footer class="container-fluid fixF">
      <div class="container">
        <div class="row mt-3">
          <div class="col-4">
            <p>
              <i class="LineH fa fa-phone pull-left" aria-hidden="true"></i> Hỗ trợ: 097 667 61 29
            </p>
            <p>
              <i class="LineH fa fa-envelope pull-left" aria-hidden="true"></i> support@veig.vn
            </p>
          </div>
          <div class="col-4 text-center">
            <p class="MT10">Copyright © 2019 Công ty CP VEIG</p>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  data: function() {
    return {
      username: "",
      password: "",
      error: ""
    };
  },
  methods: {
    logIn: async function() {
      var data = {username: this.username, password: this.password};
      var result = await this.post("/api/auth/signin", data);
      var token = result.accessToken;
      if(token) {
        localStorage.setItem("token", token);
        location.href = "/";
        this.$router.push({name: 'home'});
      }else {
        this.error = "Sai mật khẩu hoặc tên đăng nhập";
      }
    }
  },
  created: function() {
    document.body.className = "bg-login";
  }
};
</script>