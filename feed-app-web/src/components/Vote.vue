<script lang="ts">
import { defineComponent } from 'vue'
import VotingPoll from './VotingPoll.vue'
import type {Poll} from '../assets/Entities'
import { SERVER_URL } from '@/assets/config'

    export default defineComponent ({
        components: {
            VotingPoll
        },
        data() {
            return {
                poll: {} as Poll
            }
        },
        props: ['accessCode']
        ,
        mounted() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                fetch(SERVER_URL + '/polls/access/' + this.accessCode)
                .then((response) => response.json())
                .then((data) => this.poll = data);
            },

            vote(str: string) {
                let ans : boolean;
                if(str == 'yes')
                    ans = true;
                else
                    ans = false;

                fetch(SERVER_URL + '/polls/' + this.poll.id, 
                {
                    method: 'POST',
                    headers: {
                    'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        answer: ans
                    })
                }).then((response) => response.json())

                this.fetchData();
            }
        },
        computed: {
            getPoll() {
                this.fetchData();
                return this.poll;
            }
        }
    })

</script>

<template>
    <div id="pollWrapper" v-if="poll.question">
        <VotingPoll :currentPoll = "getPoll" @vote="vote"></VotingPoll>
    </div>
    <p v-else>Invalid Access Code</p>
</template>