import { defineStore } from "pinia";
import type {FAUser} from '@/assets/Entities'
import jwt_decode from "jwt-decode";

export const useAuthStore = defineStore("auth", {
    state: () => {
        return {
            user: {} as FAUser,
            isAuthenticated: false,
            token: ""
        }
    },
    actions: {
        getUserDetails() {
            this.user.email = (jwt_decode(this.token) as any).sub;
            this.user.name = (jwt_decode(this.token) as any).name;
            this.user.userId = (jwt_decode(this.token) as any).userId;
        },
        authenticationCheck() {
            if(localStorage.token != undefined) {
                const endTime = (jwt_decode(localStorage.token) as any).exp as number;
                console.log(new Date().getTime() / 1000 > endTime);
                new Date().getTime() / 1000 > endTime;
                return this.isAuthenticated = true;
            } else {
                return false;
            }
        }
    }

});