<script lang="ts">
import { defineComponent } from 'vue'
import UserProfile from './UserProfile.vue'
import type {FAUser} from '../assets/Entities'
import {SERVER_URL} from '../assets/config'

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
        props: ['userId']
        ,
        mounted() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                try{
                    fetch(SERVER_URL + '/users/' + this.userId)
                    .then((response) => response.json())
                    .then((data) => this.user = data);
                    this.loaded = true
                }catch(err){
                    this.loaded = false
                }
            },
            updateUser(name:string, pwd:string) {
                if(this.loaded){                    
                    try{
                        fetch(SERVER_URL + '/users/' + this.user.id, 
                    {
                        method: 'PUT',
                        headers: {
                        'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                                email: this.user.email,
                                password: pwd,
                                name: name,
                                admin: this.user.isAdmin
                            })
                    }).then((response) => response.json())
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
    <div id="pollWrapper" v-if="user.id">
        <UserProfile :user = "getUser" @updateUser="updateUser" :feedback = "feedback"></UserProfile>
    </div>
    <p v-else>Invalid User Id</p>
</template>