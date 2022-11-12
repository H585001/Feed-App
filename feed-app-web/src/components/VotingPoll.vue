<script lang="ts">
import { defineComponent } from 'vue'
import type {Poll} from '../assets/Entities'
    export default defineComponent ({
        data() {
            return {
                
            }
        },
        props: {
            currentPoll: {
                type: Object as () => Poll
            }
        }
    })
</script>

<template>
    <h2>{{currentPoll?.question}}</h2>
    <div id="pollClosed" v-if="!currentPoll?.public">
        <p>This poll is not public. Private polls are only available for logged in users</p>
    </div>
    <div id="pollClosed" v-else-if="currentPoll.status != 1">
        <p>This poll is not open </p>
    </div>
    <div id="pollOpen" v-else>
        <p>Yes Votes: {{currentPoll?.yesCount}}</p>
        <p>No Votes: {{currentPoll?.noCount}}</p>
        <button @click="$emit('vote', 'yes')">Yes</button>
        <button @click="$emit('vote', 'no')">No</button>
    </div>
</template>

<style scoped>
button {
    background-color: rgb(255, 60, 30);
    border: none;
    color: white;
    margin: 10px;
    padding: 10px 10px;
    border-radius: 4px;
    cursor: pointer;
    transition-duration: 0.3s;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
}
</style>