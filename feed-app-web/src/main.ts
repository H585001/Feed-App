import { createApp } from "vue";
import App from "./App.vue";
import {createRouter, createWebHistory} from 'vue-router'

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
    {path: '/dashboard/:userId', component: Dashboard, props:true},
    {path: '/register', component: Register},
    {path: '/profile/:userId', component: Profile, props: true}
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

createApp(App).use(router).mount("#app");
