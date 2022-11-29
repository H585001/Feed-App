<script lang="ts">
import { defineComponent } from 'vue'
import UserProfile from './UserProfile.vue'
import type {FAUser} from '../assets/Entities'
import {SERVER_URL} from '../assets/config'
import jwt_decode from 'jwt-decode'

    export default defineComponent ({
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
            this.fetchData();
        },
        methods: {
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
    <div id="pollWrapper">
        <UserProfile :user = "getUser" @updateUser="updateUser" :feedback = "feedback"></UserProfile>
    </div>
</template>