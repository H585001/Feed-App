<script lang="ts">
import type { Poll } from '@/assets/Entities';
import { defineComponent } from 'vue'
    export default defineComponent ({
        data() {
            return {
            }
        },
        props: {
            poll: {
                type: Object as () => Poll
            }
        }
    })
</script>

<template>
    <div class="grid-item">
        <h2>{{poll?.question}}</h2>
        <table>
            <tr>
                <th>Yes:</th>
                <th>{{poll?.yesCount}}</th>
            </tr>
            <tr>
                <th>No:</th>
                <th>{{poll?.noCount}}</th>
            </tr>
        </table>
    </div>
</template>

<style scoped>
    .grid-item {
        background-color: rgb(244, 244, 244);
        padding-left: 10px;
        margin: 10px;
        box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
        padding-bottom: 25px;
    }
    .count {
        display: inline;
        color: transparent;
        white-space: nowrap;
        background-color: rgb(255, 170, 0);
    }

    .count2 {
        display: inline;
        white-space: nowrap;
        color: transparent;
        background-color: rgb(251, 49, 120);
    }

</style>