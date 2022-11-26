<script lang="ts">
import { defineComponent } from 'vue'
    export default defineComponent ({
        name: 'RedirectButton',
        props: {
            parameters: String,
            url: String
        },
        methods: {
            redirect() {
                this.$router.push(this.url + "/" + this.parameters);
            }
        }
    })
</script>

<template>
    <button @click="redirect"><slot></slot></button>
</template>

<style scoped>

button {
    background-color: rgb(255, 60, 30);
    border: none;
    color: white;
    margin: 15px 15px;
    padding: 10px 10px;
    border-radius: 4px;
    cursor: pointer;
    transition-duration: 0.3s;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
}

button:hover {
    background-color: rgb(218, 29, 0);
}
</style>