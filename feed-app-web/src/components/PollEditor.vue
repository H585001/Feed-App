<script lang="ts">
import { defineComponent } from 'vue'
import type {Poll} from '../assets/Entities'
    export default defineComponent ({
        data() {
            return {
                question: "",
                startTime: null,
                endTime: null,
                status: 0,
                accessCode: "",
                public: true,
            }
        },
        methods: {
            createPoll(){

                this.$emit('close')
            }
        }
    })
</script>

<template>
    <div id="pollForm">
        <h1>Poll info</h1>
        <label>Question</label>
        <input name="question" v-model="question" placeholder="Name"/>
        <label>Start time</label>
        <input name="startTime" v-model="startTime"/>
        <label>End time</label>
        <input name="endTime" v-model="endTime"/>
        <label>Status</label>
        <input type="number" name="status" v-model="status" />
        <label>Access Code</label>
        <input name="accessCode" v-model="accessCode" />
        <label>Public</label>
        <input type="checkbox" name="public" />

        <button @click="createPoll">Create poll</button>
    </div>
    
</template>

<style scoped>
#pollForm {
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
</style>