<script lang="ts">
import PollCard from './poll-info-card.vue'
import { defineComponent } from 'vue'
    export default defineComponent ({
        data() {
            return {
            }
        },
        components: {
            PollCard
        }
    })
</script>
<template>
    <h2>Planned Polls</h2>
    <div class="grid-container">
        <PollCard/>
        <PollCard/>
        <PollCard/>
    </div>
    <hr>
    <h2>Active Polls</h2>
    <div class="grid-container">
        <PollCard/>
        <PollCard/>
        <PollCard/>
    </div>
    <hr>
    <h2>Closed Polls</h2>
    <div class="grid-container">
        <PollCard/>
        <PollCard/>
        <PollCard/>
    </div>
</template>
<style scoped>
    .grid-container {
        display: grid;
        grid-template-columns: auto auto auto;
    }

    h2 {
        padding-left: 20px;
        font-weight: bold;
    }
</style>