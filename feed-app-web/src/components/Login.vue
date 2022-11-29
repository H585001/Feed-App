<script lang="ts">
import { SERVER_URL } from '@/assets/config';
import { storeToRefs } from 'pinia';
import { defineComponent } from 'vue'
import {useAuthStore} from '../assets/auth'
    export default defineComponent ({
        setup() {
            const auth = useAuthStore();
            const {user, isAuthenticated, token} = storeToRefs(auth);
            const { getUserDetails } = auth;
            return {
                user,
                isAuthenticated,
                token,
                getUserDetails,
            };
        },
        data() {
            return {
                email: "",
                password: ""
            }
        },
        methods: {
            async login() {
                await this.axios.post(SERVER_URL + '/token', {}, {
                    auth: {
                        username: this.email,
                        password: this.password
                    }
                }
                ).then((response) => {
                    this.token = response.data;
                    this.getUserDetails();
                    this.$router.push("/dashboard");
                    localStorage.setItem("token", response.data);
                }).catch((error) => {
                    console.log(error);
                    alert("Invalid email or password");
                });
            },
        }
    })
</script>

<template>
    <div id="signin">
        <h1>Sign In</h1>
        <input name="email" v-model="email" placeholder="E-mail"/>
        <input type="password" name="password" v-model="password" placeholder="Password" />
        <button @click="login">Sign in</button>
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