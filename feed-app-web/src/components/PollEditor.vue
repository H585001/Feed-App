<script lang="ts">
import { SERVER_URL } from '@/assets/config'
import { defineComponent } from 'vue'
import type {Poll} from '../assets/Entities'
    export default defineComponent ({
        data() {
            return {
                question: "",
                startTime: null,
                endTime: null,
                accessCode: "",
                public: false,
                editPoll: true,
            }
        },
        emits: ['close', 'pollCreated', 'pollUpdated'],
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
                        let response = fetch(SERVER_URL + '/users/' + this.userId + '/polls', 
                    {
                        method: 'POST',
                        headers: {
                        'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            question: this.question,
                            noCount: 0,
                            yesCount: 0,
                            startTime: this.startTime,
                            endTime: this.endTime,
                            status: status,
                            accessCode: this.accessCode,
                            public: this.public
                            })
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
                    console.log("Invalid input")
                }    
            },
            validInput(){
                return this.question != "" && this.accessCode != ""
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
                console.log(json)

                if(await this.validInput()){
                    console.log("Valid input")
                    try{
                        fetch(SERVER_URL + '/polls/' + this.selectedPoll?.id, 
                    {
                        method: 'PUT',
                        headers: {
                        'Content-Type': 'application/json',
                        },
                        body: json
                    }).then((response) => response.json())
                        alert("Poll updated!")
                        this.$emit('close')
                        this.$emit('pollUpdated')

                    } catch(err){
                        alert("Update error!")
                    }
                }else{
                    console.log("Invalid input")
                }
            }
        }
    })
</script>

<template>
    <div v-if="editPoll" class="pollForm">
        <h2>Poll Results</h2>
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
        <button @click="$emit('close')">Close editor</button>
        <button v-if="selectedPoll?.status == 1" @click="$router.push('/poll/' + accessCode)">Go to voting!</button>
    </div>
    
    <div v-else class="pollForm">
        <h1>Create poll</h1>
        <label>Question</label>
        <input name="question" v-model="question" placeholder="Yes or No question"/>
        <label>Start time</label>
        <input type="datetime-local" name="startTime" v-model="startTime"/>
        <label>End time</label>
        <input type="datetime-local" name="endTime" v-model="endTime"/>
        <label>Access Code</label>
        <input name="accessCode" v-model="accessCode" placeholder="Access code"/>
        <label>Public</label>
        <input type="checkbox" name="public" @click="public = !public"/>

        <button @click="createPoll">Create poll</button>
        <button @click="$emit('close')">Close editor</button>
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
}
input {
    display: block;
    padding: 10px 2px;
    margin: 16px auto;
    width: 300px;
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
</style>