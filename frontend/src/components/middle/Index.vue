<template>
    <div>
        <div class="request"><textarea autofocus placeholder="Enter your text..." @change.prevent="onChange"
                                       v-model="text" required></textarea></div>
        <div class="response"><textarea id="copyValue" v-model="result" readonly></textarea></div>
        <button class="copyButton" @click.prevent="copyToClipboard()">Copy text</button>
    </div>
</template>

<script>
    export default {
        data: function () {
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
                alert("chaaange...");
                this.$root.$emit("onEasify", this.text);
            }, changePage: function (page) {
                this.$root.$emit("onChangePage", page);
            }, copyToClipboard: function () {
                let copyText = document.getElementById("copyValue");

                copyText.select();
                copyText.setSelectionRange(0, 99999); /*For mobile devices*/

                alert("Copied!");
                document.execCommand("copy");
            }
        }
    }
</script>

<style scoped>

</style>
