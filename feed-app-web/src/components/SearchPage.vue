<script lang="ts">
import RedirectButton from './ui-elements/RedirectButton.vue'
import { defineComponent } from 'vue'
    export default defineComponent ({
        data() {
            return {
                text: "",
                url: "/poll"
            }
        },
        components: {RedirectButton}
    })
</script>

<template>
    <form id="accessCodeInputForm">
        <label for="accessCode" >Access Code</label>
        <input name="accessCode" v-model="text" placeholder="Access code"/> 
        <RedirectButton :parameters="text" :url="url">Join Poll!</RedirectButton>
    </form>
</template>

<style>

#accessCodeInputForm {
  margin: 50px auto;
  width: 500px;
  text-align: center;
}

#accessCodeInputForm input {
  text-align: center;
  width: 100%;
  height: 40px;
  margin-top: 10px;
}
</style>
