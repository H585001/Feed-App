<script setup lang="ts">
import NavBar from "./components/NavBar.vue";
</script>

<template>
  <div id="wrapper">
    <NavBar/>
    <router-view></router-view>
  </div>
</template>