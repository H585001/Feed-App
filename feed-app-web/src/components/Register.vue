<script lang="ts">
import type {FAUser} from '../assets/Entities'
import { defineComponent } from 'vue'
import { validate } from '@babel/types'
import { routerKey, useRouter } from 'vue-router'
import { SERVER_URL } from '@/assets/config'

    export default defineComponent ({
        data() {
            return {
                name: "",
                password: "",
                email: "",
                repeatedPassword: "",
                nameError:"",
                emailError:"",
                passwordError:"",
                repeatedPasswordError:"",
                emailInUse: false, 
                validInput: false
            }
        },
        methods: {
            async getUserByEmail(email:String) {
                try{
                    const response = await fetch(SERVER_URL + '/users/email/' + email)
                    const data = await response.json();
                    this.emailInUse = true
                }catch(err){
                    this.emailInUse = false
                }
            },
            async validateInput(){
                let valid = true

                if(this.name == ""){
                    this.nameError = "Not a valid name!"
                    valid = false
                }else{
                    this.nameError = ""
                }

                if(this.email == ""){
                    this.emailError = "Not a valid e-mail!"
                    valid = false
                }else{
                    this.emailError = ""
                }

                await this.getUserByEmail(this.email)
                if(this.emailInUse){
                    this.emailError = "E-mail is already in use!"
                    valid = false
                }else{
                    this.emailError = ""
                }

                if(this.password.length < 5){
                    this.passwordError = "Invalid password: use atleast 5 characters!"
                    valid = false
                }else{
                    this.passwordError = ""
                }

                if(this.password != this.repeatedPassword){
                    this.repeatedPasswordError = "Repeated password doesn't match!"
                    valid = false
                }else{
                    this.repeatedPasswordError = ""
                }
                
               this.validInput = valid
            },
            async createUser() {
                await this.validateInput()
                if(this.validInput){
                    console.log("Valid input")
                    try{
                        fetch(SERVER_URL + '/users', 
                    {
                        method: 'POST',
                        headers: {
                        'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                                email: this.email,
                                password: this.password,
                                name: this.name,
                                admin: false
                            })
                    }).then((response) => response.json())
                        alert("User created!")
                        this.$router.push("/login")
                    } catch(err){
                        console.log("Not registered!")
                    }
                }else{
                    console.log("Invalid input")
                }                   
            }
        }
    })
</script>

<template>
    <div id="userForm">
        <h1>Register</h1>
        <label>Name</label>
        <input name="name" v-model="name" placeholder="Name"/>
        <p class="errorMsg">{{nameError}}</p>
        <label>E-mail</label>
        <input name="email" v-model="email" placeholder="name@mail.com"/>
        <p class="errorMsg">{{emailError}}</p>
        <label>Password</label>
        <input type="password" name="password" v-model="password" placeholder="Password" />
        <p class="errorMsg">{{passwordError}}</p>
        <label>Repeat password</label>
        <input type="password" name="repeatedPassword" v-model="repeatedPassword" placeholder="Repeat password" />
        <p class="errorMsg">{{repeatedPasswordError}}</p>

        <button @click="createUser">Create user</button>
        <p id="info">Already have a user? <router-link id="loginLink" to="/login">Log in here!</router-link></p>
    </div>
</template>

<style scoped>
#userForm {
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
h1 {
    text-align: center;
}
#info{
    text-align: center;
    font-size: 10px;
}
#loginLink{
    text-decoration: none;
    color: red;
}
.errorMsg{
    color:red;
    font-size: small;
}
</style>