import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/students',
      name: 'account',
      component: () => import('../views/Account.vue')
    },
    {
      path: '/courses',
      name: 'course',
      component: () => import('../views/Course.vue')
    },
    {
      path: '/',
      name: 'payment',
      component: () => import('../views/Payment.vue')
    },
    {
      path: '/expenses',
      name: 'expense',
      component: () => import('../views/Expense.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/auth/Login.vue')
    }
  ]
});

router.beforeEach((to, from, next) => {
  const publicPages = ['/login'];
  const authRequired = !publicPages.includes(to.path);
  const token = localStorage.getItem('token');
 if (to.path === '/login' && token) {
    return next('/students');
  }
  
  if (authRequired && !token) {
    return next('/login');
  }

 

  next();
});

export default router;
