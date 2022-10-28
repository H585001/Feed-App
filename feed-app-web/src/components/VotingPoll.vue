<script lang="ts">
import { defineComponent } from 'vue'
    export default defineComponent ({
        data() {
            return {
                poll: "hello"
            }
        },
        methods: {
            fetchData() {
                fetch('http://localhost:8080/polls/1')
                .then((response) => response.json())
                .then((data) => this.poll = data);
            }
        }
    })
</script>

<template>
    <p>{{poll}}</p>
    <button @click="fetchData">Fetch Data</button>
</template>