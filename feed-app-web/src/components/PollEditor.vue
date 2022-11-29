<script lang="ts">
import { SERVER_URL } from '@/assets/config'
import { defineComponent } from 'vue'
import type {Poll} from '../assets/Entities'
import {useAuthStore} from '../assets/auth'
import { storeToRefs } from 'pinia'
import jwt_decode from "jwt-decode";

    export default defineComponent ({
        setup() {
            const auth = useAuthStore();
            const {token} = storeToRefs(auth);
            return {
                token
            };
        },
        data() {
            return {
                question: "",
                startTime: null,
                endTime: null,
                accessCode: "",
                public: false,
                editPoll: true,
                errorMsg: ""
            }
        },
        emits: ['close', 'pollCreated', 'pollUpdated', 'pollDeleted'],
        props: ['userId', 'newPoll', 'selectedPoll'],
        mounted() {
            if(this.newPoll){
               this.editPoll = false
            }else{
                this.question = this.selectedPoll?.question,
                this.startTime = this.selectedPoll?.startTime,
                this.endTime = this.selectedPoll?.endTime,
                this.accessCode = this.selectedPoll?.accessCode,
                this.public = this.selectedPoll?.public
            }
        },
        methods: {
            async createPoll(){
                let status = 0;
                if(this.startTime != null && new Date(this.startTime) < new Date()){
                    status = 1
                }

                if(await this.validInput()){
                    console.log("Valid input")
                    try{
                        const token = localStorage.token;
                        let response = this.axios.post(SERVER_URL + '/users/' + (jwt_decode(token) as any).userId + '/polls', {
                            question: this.question,
                            noCount: 0,
                            yesCount: 0,
                            startTime: this.startTime,
                            endTime: this.endTime,
                            status: status,
                            accessCode: this.accessCode,
                            public: this.public
                        },
                        {
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: `Bearer ${localStorage.getItem("token")}`
                        },
                        })
                        if((await response).status == 200){
                            alert("Poll created!")
                            this.$emit('close')
                            this.$emit('pollCreated')
                        }else{
                            alert("Registration error!!")
                        } 

                    } catch(err){
                        alert("Registration error!")
                    }
                }else{
                    alert("Invalid input: " + this.errorMsg)
                }    
            },
            validInput(){
                let valid = true;
                if(this.question == ""){
                    valid = false;
                    this.errorMsg = "Question can not be empty!"
                }

                if(valid){
                    this.errorMsg = "";
                }

                return valid
            },
            async updatePoll(){
                let status = 0
                if(this.endTime != null && new Date(this.endTime) < new Date()){
                    status = 2
                }else if(this.startTime != null && new Date(this.startTime) < new Date()){
                    status = 1
                }

                let json = JSON.stringify({
                            question: this.question,
                            noCount: this.selectedPoll.noCount,
                            yesCount: this.selectedPoll.yesCount,
                            startTime: this.startTime,
                            endTime: this.endTime,
                            status: status,
                            accessCode: this.accessCode,
                            public: this.public
                            })

                if(await this.validInput()){
                    console.log("Valid input")
                    try{
                        this.axios.put(SERVER_URL + '/polls/' + this.selectedPoll?.id, 
                    {
                        question: this.question,
                        noCount: this.selectedPoll.noCount,
                        yesCount: this.selectedPoll.yesCount,
                        startTime: this.startTime,
                        endTime: this.endTime,
                        status: status,
                        accessCode: this.accessCode,
                        public: this.public
                    }, {
                        headers: {
                            authorization: `Bearer ${localStorage.token}`
                        }
                    }).then((response) => response)
                        alert("Poll updated!")
                        this.$emit('close')
                        this.$emit('pollUpdated')

                    } catch(err){
                        alert("Update error!")
                    }
                }else{
                    console.log("Invalid input")
                }
            },
            async deletePoll(){
                try{
                        this.axios.delete(SERVER_URL + '/polls/' + this.selectedPoll?.id, 
                    {
                        headers: {
                        'Content-Type': 'application/json',
                        authorization: `Bearer ${localStorage.token}`
                        }
                    }).then((response) => response)
                        alert("Poll deleted!")
                        this.$emit('close')
                        this.$emit('pollDeleted')
                    } catch(err){
                        alert("Delete error!")
                    }
            }
        }
    })
</script>

<template>
    <div v-if="editPoll" class="pollForm">
        <h2>Poll Results</h2> <button class="closeBtn" @click="$emit('close')">X</button>
        <p>{{question}}</p>
        <p>Yes: {{selectedPoll?.yesCount}}</p>
        <p>No: {{selectedPoll?.noCount}}</p>

        <h2>Poll Details</h2>
        <p><label>Status: {{selectedPoll?.status}}</label></p>
        <p><label>Start time: {{startTime}}</label></p>
        <input v-if="selectedPoll?.status == 0" type="datetime-local" name="startTime" v-model="startTime"/>
        <p><label>End time: {{endTime}}</label></p>
        <input v-if="selectedPoll?.status != 2" type="datetime-local" name="endTime" v-model="endTime"/>
        <p><label>Access Code: {{accessCode}}</label></p>
        <label>Public</label>
        <input type="checkbox" name="public" v-model="public" @click="public = !public"/>

        <button @click="updatePoll">Save changes</button>
        <button v-if="selectedPoll?.status == 1" @click="$router.push('/poll/' + accessCode)">Go to voting!</button>
        <button class="deleteBtn" @click="deletePoll">DELETE</button>
    </div>
    
    <div v-else class="pollForm">
        <h1>Create poll</h1> <button class="closeBtn" @click="$emit('close')">X</button>
        <label>Question</label>
        <input name="question" v-model="question" placeholder="Yes or No question"/>
        <label>Start time</label>
        <input type="datetime-local" name="startTime" v-model="startTime"/>
        <label>End time</label>
        <input type="datetime-local" name="endTime" v-model="endTime"/>
        <label>Public</label>
        <input type="checkbox" name="public" @click="public = !public"/>

        <button @click="createPoll">Create poll</button>
    </div>
</template>

<style scoped>
.pollForm {
    text-align: left;
    display: block;
    margin: 50px auto;
    width: fit-content;
    padding: 16px 16px;
    background-color: white;
    box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.2);
    border-radius: 5px;
    border: 2px solid black;
    position: relative;
}
input {
    display: block;
    padding: 10px 2px;
    margin: 16px auto;
    width: 300px;
    border-radius: 4px;
}
input[type="checkbox"] {
    margin: 2px;
    width: 20px;
    border-radius: 4px;
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
.closeBtn {
    position:absolute;
    top: 0;
    right:0;
    font-weight: bold;
    margin: 2px;
    padding: 5px 5px;
}
.deleteBtn {
    font-weight: bold;
}
</style>