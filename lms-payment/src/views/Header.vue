<script setup>
  import {
    RouterLink,
    RouterView
  } from 'vue-router';
  
</script>

<template>

  <header class="p-3 text-bg-dark">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

        <div class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
          <img src="/ingress.jpg" class="object-fit-fill" style="width: 100px; height: 80px;">
        </div>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li>
            <RouterLink outerLink to="/" class="nav-link px-2 text-white">
              <h4>Ödənişlər</h4>
            </RouterLink>
          </li>
          <li>
            <RouterLink to="/students" class="nav-link px-2 text-white">
              <h4>Tələbələr</h4>
            </RouterLink>
          </li>
          <li>
            <RouterLink to="/courses" class="nav-link px-2 text-white">
              <h4>Kurslar</h4>
            </RouterLink>
          </li>

          <li>
            <RouterLink to="/expenses" class="nav-link px-2 text-white">
              <h4>Xərclər</h4>
            </RouterLink>
          </li>
        </ul>

        <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
          <input type="search" class="form-control form-control-dark text-bg-light" placeholder="Axtar..."
            aria-label="Search" />
        </form>

        <div class="text-end">
          <button @click="logout()" class="btn btn-warning me-2">Cix</button>
        </div>

      </div>
    </div>
  </header>

</template>


<script>

export default{


methods: {

  logout() 
  {
    localStorage.removeItem('token');
    this.$router.push('/login');
  }

}


}

</script>