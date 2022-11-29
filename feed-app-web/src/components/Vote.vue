<script lang="ts">
import { defineComponent } from 'vue'
import VotingPoll from './VotingPoll.vue'
import type {Poll} from '../assets/Entities'
import { SERVER_URL } from '@/assets/config'
import jwt_decode from 'jwt-decode'
import { useAuthStore } from '@/assets/auth'


    export default defineComponent ({
        setup() {
            const auth = useAuthStore();
            const { authenticationCheck } = auth;
            return {
                authenticationCheck
            };
        },
        components: {
            VotingPoll
        },
        data() {
            return {
                poll: {} as Poll,
                text: ""
            }
        },
        props: ['accessCode']
        ,
        mounted() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                this.axios.get(SERVER_URL + '/polls/access/' + this.accessCode)
                .then((response) => {
                    this.poll = response.data;
                })
            },

            vote(str: string) {
                let ans : boolean;
                if(str == 'yes')
                    ans = true;
                else
                    ans = false;

                let url = SERVER_URL + '/polls/' + this.poll.id;

                if(!this.poll.public) {
                this.axios.post(url += "/" + (jwt_decode(localStorage.token) as any).userId,
                {
                    answer: ans
                }, 
                {
                    headers: {
                    Authorization: `Bearer ${localStorage.token}`,
                    'Content-Type': 'application/json',
                    }

                }).then((response) => response).catch((e) => {
                    console.log(e);
                    if (e.response.status == 405)
                        this.text = "You have already voted for this poll";
                })
            } else {
                this.axios.post(url,
                {
                    answer: ans
                }, 
                {
                    headers: {
                    'Content-Type': 'application/json',
                    }

                }).then((response) => response).catch((e) => {
                    console.log(e);
                    if (e.response.status == 405)
                        this.text = "You have already voted for this poll";
                })
            }

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
        <p v-if="text != ''">{{text}}</p>
    </div>
    <p v-else>Invalid Access Code</p>
</template>

<style scoped>
    p {
        width: fit-content;
        padding: 10px;
        margin:auto;
        margin-top: 30px;
        border-radius: 5px;
        background-color: rgb(255, 164, 160);
        border-style:none;
        border-color: rgb(203, 30, 30);
        color: white;
        font-size: 14px;
    }
</style>