import {createApp} from 'vue'
import App from './01-views/app.vue'
import {createRouter, createWebHistory} from "vue-router";

const routes = [
    {path: '/dashboard', component: () => import('./01-views/01-overview.vue')},
    {path: '', component: () => import('./01-views/01-overview.vue')},
    {path: '/create_index', component: () => import('./01-views/02-create-index.vue')},
    {path: '/upload_file', component: () => import('./01-views/03-upload-file.vue')},
    {path: '/search', component: () => import('./01-views/04-search.vue')},
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

createApp(App).use(router).mount('#app')
