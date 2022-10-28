<script lang="ts">
import { defineComponent } from 'vue'
    export default defineComponent ({
        data() {
            return {

            }
        }
    })
</script>

<template>
    <ul class="navBar">
        <li id="logoHeader">Feed App</li>
        <li><router-link to="/">Search</router-link></li>
        <li><router-link to="/dashboard">Dashboard</router-link></li>
        <li><router-link to="/profile">Profile</router-link></li>
    </ul>    
</template>
