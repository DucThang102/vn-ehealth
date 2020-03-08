<template>
  <div>
    <Login v-if="$route.name=='login'"/>
    <Registration v-else-if="$route.name=='registration'"/>
    <Home v-else-if="logged_in"/>
  </div> 
</template>

<style>
@import "assets/css/bootstrap.css";
@import "assets/css/style.css";
@import "assets/css/fontawesome/all.css";
</style>

<script>
import Home from './views/Home.vue';
import Login from './views/Login.vue';
import Registration from './views/Registration.vue';

export default {
  components: {
    Home,
    Login,
    Registration
  },
  computed: {
    logged_in() {
      return localStorage.getItem("token");
    }
  },
  created: function() {
    if(!this.logged_in && this.$route.name != "login") {
      this.$router.push({name: 'login'});
    }
  } 
};
</script>