/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import {createRouter, createWebHistory, Router} from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    redirect: 'ui',
  },
  {
    path: '/ui',
    name: 'ui',
    component: () => import('@/pages/index.vue'),
    props: true,
  },
];

const router: Router = createRouter({
  history: createWebHistory(process.env.BASE_URL),

  routes
});

export default router;
