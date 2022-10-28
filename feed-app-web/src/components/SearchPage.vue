<script lang="ts">
import { defineComponent } from 'vue'
    export default defineComponent ({
        data() {
            return {
                text: ""
            }
        }
    })
</script>

<template>
    <form id="accessCodeInputForm">
        <label for="accessCode" >Access Code</label>
        <input name="accessCode" v-model="text" placeholder="Access code"/>    
    </form>
</template>

