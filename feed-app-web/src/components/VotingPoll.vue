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