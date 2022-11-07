<script lang="ts">
import { defineComponent } from 'vue'
    export default defineComponent ({
        data() {
            return {
            }
        }
    })
</script>

<template>
    <div class="grid-item">
        <h3>Question</h3>
        <table>
            <tr>
                <th>Yes:</th>
                <th class="count">-------------------------</th>
            </tr>
            <tr>
                <th>No:</th>
                <th class="count2">-----------------------------------------------------------------</th>
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