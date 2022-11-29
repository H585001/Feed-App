import { createApp } from "vue";
import App from "./App.vue";
import {createRouter, createWebHistory} from 'vue-router'

import * as Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

import {createPinia} from 'pinia'

import "./assets/main.css";
import SearchPage from "./components/SearchPage.vue";
import Vote from "./components/Vote.vue";
import Dashboard from "./components/Dashboard.vue";
import Login from "./components/Login.vue";
import Register from "./components/Register.vue";
import Profile from "./components/Profile.vue";

const routes = [
    {path: '/', component: SearchPage},
    {path: '/poll/:accessCode', component: Vote, props: true},
    {path: '/login', component: Login},
    {path: '/dashboard', component: Dashboard},
    {path: '/register', component: Register},
    {path: '/profile', component: Profile}
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

const pinia = createPinia();

const axiosInstance = axios.create();

axiosInstance.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    if(error.response.status == 401)
        return router.push("/login");
    else return Promise.reject(error);
});

createApp(App).use(router).use(VueAxios, axiosInstance).use(pinia).mount("#app");
