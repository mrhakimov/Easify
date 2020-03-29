<!--suppress HtmlUnknownAnchorTarget -->
<template>
    <header>
        <a href="/"><img src="../assets/img/logo.png" alt="Easify" title="Easify" width="300px"/></a>
        <div class="enter-or-register-box">
            <template v-if="user">
                {{user.login}}
                |
                <a href="#page=Logout" @click="logout">Logout</a>
            </template>
            <template v-else>
                <a href="#page=Enter" @click="changePage('Enter')">Enter</a>
                |
                <a href="#page=Register" @click="changePage('Register')">Register</a>
            </template>
        </div>
    </header>
</template>

<script>
    export default {
        props: ['user'],
        name: "Header",
        beforeCreate() {
            this.$root.$on("onEnterSuccess", () => {
                this.changePage('Index');
            });
        },
        methods: {
            changePage: function (page) {
                this.$root.$emit("onChangePage", page);
            }, logout: function() {
                this.$root.$emit("onLogout");
                this.changePage('Index');
            }
        }
    }
</script>

<style scoped>
    header .languages a {
        margin-left: 0.25rem;
    }
</style>
