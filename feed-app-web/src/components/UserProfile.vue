<script lang="ts">
import { defineComponent } from 'vue'
import type {FAUser} from '../assets/Entities'
    export default defineComponent ({
        data() {
            return {
                name: this.user?.name,
                password: this.user?.password,
                repeatedPassword: this.user?.password,
                nameError: "",
                passwordError: "",
                repeatedPasswordError: "",
            }
        },
        props: {
            user: {
                type: Object as () => FAUser
            }
        },
        methods: {
            saveChanges(){
                let validInput = true
                if(this.name == undefined || this.name.length < 1){
                    validInput = false;
                    this.nameError = "Invalid name"
                }else{
                    this.nameError = ""
                }

                if(this.password == undefined || this.password.length < 5){
                    validInput = false;
                    this.passwordError = "Invalid password: use atleast 5 characters!"
                }else{
                    this.passwordError = ""
                }

                if(this.repeatedPassword == undefined || this.repeatedPassword != this.password){
                    validInput = false;
                    this.repeatedPasswordError = "Repeated password doesn't match!"
                }else{
                    this.repeatedPasswordError = ""
                }

                if(validInput){
                    this.$emit('updateUser', this.name, this.password)
                }
            }
        }
    })
</script>

<template>
    <div id="userForm" v-if="user?.id">
        <h1>Change profile</h1>
        <label>Name</label>
        <input type="text" name="name" v-model="name" placeholder="Name"/>
        <p class="errorMsg">{{nameError}}</p>
        <label>Password</label>
        <input type="password" name="password" v-model="password" placeholder="Password" />
        <p class="errorMsg">{{passwordError}}</p>
        <label>Repeat password</label>
        <input type="password" name="repeatedPassword" v-model="repeatedPassword" placeholder="Repeat password" />
        <p class="errorMsg">{{repeatedPasswordError}}</p>

        <button @click="saveChanges">Save changes</button>
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
    text-align: left;
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
.errorMsg{
    color:red;
    font-size: small;
}
</style>