import Vue from 'vue'
import App from './App.vue'
import jquery from './jquery-3.4.1.min'
Vue.use(jquery);

import Notify from './notify'

Vue.use(Notify);

Vue.config.productionTip = false;

import data from './data';

new Vue({
    data: function() {
        return data;
    },
    render: h => h(App)
}).$mount('#app');
