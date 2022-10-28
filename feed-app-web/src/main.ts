import { createApp } from "vue";
import App from "./App.vue";
import {createRouter, createWebHistory} from 'vue-router'

import "./assets/main.css";
import SearchPage from "./components/SearchPage.vue";
import VotingPoll from "./components/VotingPoll.vue";

const routes = [
    {path: '/', component: SearchPage},
    {path: '/vote', component: VotingPoll}
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

createApp(App).use(router).mount("#app");
