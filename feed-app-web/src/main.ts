import { createApp } from "vue";
import App from "./App.vue";
import {createRouter, createWebHistory} from 'vue-router'
import Login from "./components/Login.vue";

import "./assets/main.css";
import SearchPage from "./components/SearchPage.vue";
import Vote from "./components/Vote.vue";

const routes = [
    {path: '/', component: SearchPage},
    {path: '/poll/:accessCode', component: Vote, props: true},
    {path: '/login', component: Login}
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

createApp(App).use(router).mount("#app");
