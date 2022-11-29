<script lang="ts">
import { defineComponent } from 'vue'
import UserProfile from './UserProfile.vue'
import type {FAUser} from '../assets/Entities'
import {SERVER_URL} from '../assets/config'
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
            UserProfile
        },
        data() {
            return {
                user: {} as FAUser,
                feedback: "",
                loaded: false
            }
        },
        mounted() {
            this.auth();
            this.fetchData();
        },
        methods: {
            auth() {
                if (!this.authenticationCheck())
                    this.$router.push("/login")
            },
            fetchData() {
                try{
                    this.axios.get(SERVER_URL + '/users/' + (jwt_decode(localStorage.token) as any).userId, {
                        headers: {
                            Authorization: `Bearer ${localStorage.token}`
                        }
                    })
                    .then((response) => {
                        this.user = response.data})
                    this.loaded = true
                }catch(err){
                    this.loaded = false
                }
            },
            updateUser(name:string, pwd:string) {
                if(this.loaded){                    
                    try{
                        const token = (jwt_decode(localStorage.token) as any)
                        this.axios.put(SERVER_URL + '/users/' + token.userId, 
                    {
                        email: token.sub,
                        password: pwd,
                        name: name,
                        admin: token.admin
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: `Bearer ${localStorage.token}`
                        }
                    }
                    ).then((response) => response)
                        this.feedback = "Successfull update"
                    } catch(err){
                        this.feedback = ("Unsuccessfull update!")
                    }finally{
                        alert(this.feedback)
                    }

                }
            },
            deleteUser() {
                if(this.loaded){                    
                    try{
                        const token = (jwt_decode(localStorage.token) as any)
                        this.axios.delete(SERVER_URL + '/users/' + token.userId, 
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: `Bearer ${localStorage.token}`
                        }
                    }
                    ).then((response) => response)
                        this.feedback = "User deleted"
                        localStorage.removeItem("token");
                        this.$router.push("/");
                    } catch(err){
                        this.feedback = "Delete error!"
                    }finally{
                        alert(this.feedback)
                    }

                }
            }
        },
        computed: {
            getUser() {
                this.fetchData();
                return this.user;
            }
        }
    })

</script>

<template>
    <p>Name: {{user.name}}</p>
    <button class="deleteBtn" @click="deleteUser">Delete User</button>
    <div id="pollWrapper">
        <UserProfile :user = "getUser" @updateUser="updateUser" :feedback = "feedback"></UserProfile>
    </div>
</template>

<style scoped>
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
