<script lang="ts">
import type {Poll} from '../assets/Poll'
import { defineComponent } from 'vue'
    export default defineComponent ({
    data() {
        return {
            noVotes: 0,
            yesVotes: 0,
            poll: {} as Poll
        };
    },
    props: ['id'],
    methods: {
        reset() {
            this.yesVotes = 0;
            this.noVotes = 0;
        },
        sendVotes() {
            console.log("Yes: " + this.yesVotes);
            console.log("No: " + this.noVotes);
            if (Object.keys(this.poll).length === 0){
              console.log("Device is not connected to a poll")
            }else{
              fetch('http://localhost:8080/iot/' + this.id + "/vote", 
            {
              method: 'POST',
              headers: {
              'Content-Type': 'application/json',
              },
              body: JSON.stringify({
                noCount: this.noVotes,
                yesCount: this.yesVotes
              })
            }).then((response) => response.json())

            this.reset();
            this.connect();
            }
        },
        connect() {
            console.log("Connecting to poll... ");
            fetch('http://localhost:8080/iot/' + this.id + "/poll")
                .then((response) => response.json())
                .then((data) => this.poll = data);
        },
        disconnect(){
            console.log("Disconnecting... ");
            this.poll = {} as Poll
        }
    },
})
</script>

<template>
  <div class="display">
    <button @click="yesVotes++;" style="background-color: green"> Yes </button>
    <button @click="noVotes++;" style="background-color: red"> No </button>
    <button @click="sendVotes()" style="background-color: orange"> Send votes </button>
    <button @click="reset()" style="background-color: black"> Reset votes </button>
    <button v-if="poll.question" @click="disconnect()" style="background-color: lightblue"> Disconnect </button>
    <button v-else @click="connect()" style="background-color: grey"> Connect </button>
    <button v-if="poll.question" @click="connect()" style="background-color: blue"> Refresh </button>

    <h1> Yes votes: {{yesVotes}}</h1>
    <h1> No votes: {{noVotes}}</h1>
  </div>

  <div class="display">
    <div v-if="poll.status && poll.status === 1">
      <h1> Result of poll: </h1>
      <h2> {{poll?.question}}</h2>
      <h2> No: {{poll?.noCount}}</h2>
      <h2> Yes: {{poll?.yesCount}}</h2>
    </div>
    <p v-else-if="poll.question">The connected poll is not open!</p>
    <p v-else>Device is not connected to a poll</p>
  </div>
</template>

<style>
  .display {
    margin: 50px auto;
    width: 700px;
    text-align: center;
    border: solid black 4px;
    background-color: #CCC;
  }
  button {
    color: white;
    margin: 15px 15px;
    padding: 10px 10px;
    border-radius: 4px;
    cursor: pointer;
    transition-duration: 0.3s;
    font-weight: 550;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
}
</style>
