<template>
    <div>
        <div class="request"><textarea autofocus placeholder="Enter your text..." @change.prevent="onChange" v-model="text"></textarea></div>
        <div class="response"><textarea>{{result}}</textarea></div>
    </div>
</template>

<script>
    export default {
        data: function() {
            return {
                text: "",
                result: ""
            }
        },
        name: "Index",
        beforeCreate() {
            this.$root.$on("onEasifyResult", (result) => {
                this.result = result;
            });
        },
        beforeMount() {
            this.text = "";
            this.result = "";
        },
        methods: {
            onChange: function () {
                this.$root.$emit("onEasify", this.text);
            }, changePage: function (page) {
                this.$root.$emit("onChangePage", page);
            }
        }
    }
</script>

<style scoped>

</style>
