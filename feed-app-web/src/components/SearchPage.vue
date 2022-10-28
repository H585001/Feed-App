<script lang="ts">
import { defineComponent } from 'vue'
    export default defineComponent ({
        data() {
            return {
                text: ""
            }
        },
        methods: {
            redirect() {
                this.$router.push('/poll/'+ this.text);
            }
        }
    })
</script>

<template>
    <form id="accessCodeInputForm">
        <label for="accessCode" >Access Code</label>
        <input name="accessCode" v-model="text" placeholder="Access code"/> 
        <button @click="redirect">Join Poll</button>
    </form>
</template>

