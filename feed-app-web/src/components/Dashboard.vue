<script lang="ts">
import PollCard from './poll-info-card.vue'
import PollEditor from './PollEditor.vue'
import { defineComponent } from 'vue'
import type { FAUser, Poll} from '@/assets/Entities';
import { SERVER_URL } from '@/assets/config';
import { MODULEDECLARATION_TYPES } from '@babel/types';
import {useAuthStore} from '../assets/auth'
import { storeToRefs } from 'pinia'
import jwt_decode from "jwt-decode";

    export default defineComponent ({
        setup() {
            const auth = useAuthStore();
            const {user, isAuthenticated, token} = storeToRefs(auth);
            const { authenticationCheck } = auth;
            return {
                user,
                isAuthenticated,
                token,
                authenticationCheck
            };
        },
        data() {
            return {
                userPolls: [] as Poll[],
                plannedPolls: [] as Poll[],
                activePolls: [] as Poll[],
                closedPolls: [] as Poll[],
                showPollEditor: false,
                detailsShown: false,
                selectedPoll: {} as Poll
            }
        },
        components: {
            PollCard,
            PollEditor
        },
        mounted() {
            this.initDashboard();
            this.auth();
        },
        methods: {
            async fetchPolls() {
                console.log("Token: " + localStorage.token);
                try{
                    await this.axios.get(SERVER_URL + '/users/' + (jwt_decode(localStorage.token) as any).userId + "/polls", {
                        headers: {
                            Authorization: `Bearer ${localStorage.token}`
                        }
                    }
                    ).then((data) => {
                        this.userPolls = data.data;
                        console.log(data);
                    });
                    
                    // Resetting poll lists
                    this.plannedPolls = []
                    this.activePolls = []
                    this.closedPolls = []

                    this.userPolls.forEach(poll =>{
                        let status = poll.status;
                        if(status == 0){
                            this.plannedPolls.push(poll);
                        }else if(status == 1){
                            this.activePolls.push(poll);
                        }else{
                            this.closedPolls.push(poll);
                        }
                    })
                }catch(err){
                    
                }
            },
            async initDashboard(){
                await this.fetchPolls();

            },
            showDetails(poll : Poll){
                this.selectedPoll = poll;
                this.detailsShown = true;
                this.showPollEditor = true;
            },
            closeDetails(){
                this.showPollEditor = false;
                this.detailsShown = false;
            },
            auth() {
                if (!this.authenticationCheck())
                    this.$router.push("/login")
            }
        }
    })
</script>

<template>
    <div>
        <button @click="showPollEditor = true">New Poll</button>
    </div>

    <div class="modal" v-if="showPollEditor">
        <PollEditor v-if="detailsShown" :userId="user.userId" :selectedPoll="selectedPoll" :newPoll="false" @close="closeDetails" @pollUpdated="fetchPolls" @pollDeleted="fetchPolls" />
        <PollEditor v-else :userId="user.userId" :newPoll="true" @close="showPollEditor = false" @pollCreated="fetchPolls"/>
    </div>

    <h2>Planned Polls</h2>
    <div class="grid-container">
        <PollCard v-for="poll in plannedPolls" :poll="poll" @showDetails="showDetails"/>
    </div>
    <h2>Active Polls</h2>
    <div class="grid-container">
        <PollCard v-for="poll in activePolls" :poll="poll" @showDetails="showDetails"/>
    </div>
    <h2>Closed Polls</h2>
    <div class="grid-container">
        <PollCard v-for="poll in closedPolls" :poll="poll" @showDetails="showDetails"/>
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
    .modal {
        display:block;
        position: fixed; 
        z-index: 1; 
        margin:auto;
        width: 60%; 
        height: 70%;
        overflow: auto; 
    }
</style>