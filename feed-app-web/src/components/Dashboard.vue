<script lang="ts">
import PollCard from './poll-info-card.vue'
import PollEditor from './PollEditor.vue'
import { defineComponent } from 'vue'
import type { FAUser, Poll} from '@/assets/Entities';
import { SERVER_URL } from '@/assets/config';
import { MODULEDECLARATION_TYPES } from '@babel/types';
    export default defineComponent ({
        data() {
            return {
                user: {} as FAUser,
                userPolls: [] as Poll[],
                plannedPolls: [] as Poll[],
                activePolls: [] as Poll[],
                closedPolls: [] as Poll[],
                showPollEditor: false,
                detailsShown: false,
                selectedPoll: {} as Poll
            }
        },
        props: ['userId'],
        components: {
            PollCard,
            PollEditor
        },
        mounted() {
            this.initDashboard();
        },
        methods: {
            async fetchUser() {
                try{
                    await fetch(SERVER_URL + '/users/' + this.userId)
                    .then((response) => response.json())
                    .then((data) => this.user = data);
                }catch(err){
                    alert("Dashboard is only available for logged in users!")
                    this.$router.push("/login")
                }
            },
            async fetchPolls() {
                try{
                    await fetch(SERVER_URL + '/users/' + this.userId + "/polls")
                    .then((response) => response.json())
                    .then((data) => this.userPolls = data);
                    
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
                await this.fetchUser();
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
            }
        }
    })
</script>

<template>
    <div>
        <button @click="showPollEditor = true">New Poll</button>
    </div>

    <div class="modal" v-if="showPollEditor">
        <PollEditor v-if="detailsShown" :userId="userId" :selectedPoll="selectedPoll" :newPoll="false" @close="closeDetails" @pollUpdated="fetchPolls" />
        <PollEditor v-else :userId="userId" :newPoll="true" @close="showPollEditor = false" @pollCreated="fetchPolls"/>
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