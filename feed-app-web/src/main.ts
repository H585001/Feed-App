import { createApp } from "vue";
import App from "./App.vue";
import {createRouter, createWebHistory} from 'vue-router'
import Login from "./components/Login.vue";

import "./assets/main.css";
import SearchPage from "./components/SearchPage.vue";
import Vote from "./components/Vote.vue";
import Dashboard from "./components/Dashboard.vue";

const routes = [
    {path: '/', component: SearchPage},
    {path: '/poll/:accessCode', component: Vote, props: true},
    {path: '/login', component: Login},
    {path: '/dashboard', component: Dashboard}
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

createApp(App).use(router).mount("#app");
