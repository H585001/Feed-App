<script lang="ts">
import { useAuthStore } from '@/assets/auth';
import { defineComponent } from 'vue'
    export default defineComponent ({
      setup() {
            const auth = useAuthStore();
            const { authenticationCheck } = auth;
            return {
                authenticationCheck
            };
        },
        computed:{
          getAuthenticationCheck(){
            return this.authenticationCheck();
          }
        },
        data() {
            return {

            }
        },
        methods: {
          logout(){
            localStorage.removeItem("token");
            this.$router.push("/");
          },
        }
    })
</script>

<template>
    <ul class="navBar">
        <li id="logoHeader">Feed App</li>
        <li><router-link to="/">Search</router-link></li>
        <li><router-link to="/dashboard">Dashboard</router-link></li>
        <li><router-link to="/profile">Profile</router-link></li>
        <li v-if="!getAuthenticationCheck" id="loginRef"><router-link to="/login">Login</router-link></li>
        <li v-else id="loginRef"><router-link to="/" @click="logout">Log out</router-link></li>
    </ul>    
</template>

<style>
.navBar {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: rgb(255, 60, 30);
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
}


.navBar li {
  float: left;
}

.navBar #logoHeader {
  text-align: center;
  padding: 15px;
  background-color: white;
  color: rgb(255, 60, 30);
  font-size: 20px;
  font-weight: 800;
}

.navBar li a {
  padding: 17px 16px;
  font-weight: 550;
  display: block;
  color: white;
  text-align: center;
  height: 100%;
  text-decoration: none;
}

.navBar li a:hover {
  background-color: rgb(218, 29, 0);
}

#loginRef {
    float: right;
}

#loginRef a {
    background-color:white;
    color: rgb(255, 60, 30);
}

#loginRef a:hover {
    background-color:rgb(243, 243, 243); 
}

</style>
