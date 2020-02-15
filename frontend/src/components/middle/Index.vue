<template>
    <div>
        <div class="request"><textarea autofocus placeholder="Enter your text..." @change.prevent="onChange" v-model="text"></textarea></div>
        <div class="response"><textarea readonly></textarea></div>
    </div>
</template>

<script>
    export default {
        data: function() {
            return {
                text: ""
            }
        },
        name: "Index",
        beforeMount() {
            this.text = "";
        },
        methods: {
            onChange: function () {
                this.$root.$emit("onEasify", this.text);
            }
        }
    }
</script>

<style scoped>

</style>
