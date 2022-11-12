<script lang="ts">
import { SERVER_URL } from '@/assets/config';
import type { FAUser } from '@/assets/Entities';
import { defineComponent } from 'vue'
    export default defineComponent ({
        data() {
            return {
                email: "",
                password: "",
                user: {} as FAUser
            }
        },
        methods: {
            async signin() {
                console.log("E-mail: " + this.email + "\nPassword: " + this.password);
                await this.getUserByEmail(this.email)
            },
            async getUserByEmail(email:String) {
                try{
                    const response = await fetch(SERVER_URL + '/users/email/' + email)
                    const data = await response.json();
                    this.user = data
                    console.log(this.user)
                    this.$router.push('/dashboard/' + this.user.id)
                }catch(err){
                    alert("E-mail and password doesn't match!")
                }
            },
        }
    })
</script>

<template>
    <div id="signin">
        <h1>Sign In</h1>
        <input name="email" v-model="email" placeholder="E-mail"/>
        <input type="password" name="password" v-model="password" placeholder="Password" />
        <button @click="signin">Sign in</button>
        <p id="info">Don't have an account? <router-link id="registerLink" to="/register">Sign up here!</router-link></p>
    </div>
</template>

<style scoped>
#signin {
    text-align: center;
    display: block;
    margin: 50px auto;
    width: fit-content;
    padding: 16px 16px;
    background-color: white;
    box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.2);
    border-radius: 5px;
}
input {
    display: block;
    padding: 10px 2px;
    margin: 16px auto;
    width: 300px;
    border-radius: 4px;
}
button {
    display: block;
    background-color: rgb(255, 60, 30);
    border: none;
    color: white;
    margin: 15px auto;
    padding: 10px 10px;
    border-radius: 4px;
    cursor: pointer;
    transition-duration: 0.3s;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
}

button:hover {
    background-color: rgb(218, 29, 0);
}
#info{
    text-align: center;
    font-size: 10px;
}
#registerLink{
    text-decoration: none;
    color: red;
}
</style>