<script setup>
  import 'bootstrap/dist/css/bootstrap.css';
  import Header from './Header.vue';
</script>

<template>

  <Header />

  <div class="container">
    <div class="my-5 shadow p-5">
      <h2 class="text-center">{{ editId ? 'Telebeni Redaktə Et' : 'Telebeni Daxil Edin' }}</h2>
      <form>
        <div class="row mt-5">
          <div class="class col-6">
            <div class="mb-4">
              <label class="my-2 fw-bold">
                <h4>Email</h4>
              </label>
              <input type="email" class="form-control form-control-lg" placeholder="Emaili daxil edin" v-model="email">
            </div>
          </div>
          <div class="class col-6">
            <div class="input-group mb-4 mt-5">
              <div class="input-group-prepend">
                <button class="btn btn-secondary btn-lg text-center">+994</button>
              </div>
              <input type="text" maxlength="9" class="form-control form-control-lg text-center"
                placeholder="Nömrəni daxil edin" v-model="number">
              <!-- <div class="input-group-append">
              <button class="btn btn-outline-secondary btn-lg" type="submit">Axtar</button>
            </div> -->
            </div>
          </div>
        </div>

        <div class="row mt-5">
          <div class="col-6">
            <div class="mb-4">
              <label class="my-2 fw-bold">
                <h4>Ad</h4>
              </label>
              <input class="form-control form-control-lg" v-model="name" type="text" placeholder="Tələbənin adı">
            </div>
          </div>
          <div class="col-6">
            <div class="mb-4">
              <label class="my-2 fw-bold">
                <h4>Soyad</h4>
              </label>
              <input class="form-control form-control-lg" v-model="surname" type="text" placeholder="Tələbənin soyadı">
            </div>
          </div>
        </div>


        <div class="mb-5">
          <button v-if="!editId" @click.prevent="create" class="btn btn-success mx-2 mb-5 float-end">Daxil
            et</button>

          <div v-else-if="editId">
            <button @click.prevent="update" class="btn btn-success mx-2 mb-5 float-end">Yadda
              saxla</button>

            <button onclick="history.back()" class="btn btn-secondary mx-2 mb-5 float-end">Geri qayıt</button>
          </div>
        </div>
      </form>
    </div>

    <div class="row mb-5">
      <h3 class="fs-4 mb-3">Tələbələr</h3>
      <!-- <div class="">
        <Link to="/create" class="btn btn-success mb-3 float-end">Yarat</Link>
      </div> -->
      <div class="col-4 float-end" style="margin-left: 70%;">
        <div class="input-group mb-3 px-5">
          <input type="text" class="form-control" v-model="searchTerm" placeholder="Telebenin elaqe nomresi...">
          <button class="btn btn-dark" @click="searchAccount">Axtar</button>
        </div>
      </div>
      <div class="col-12">
        <div class="table-responsive shadow-lg">
          <table class="table bg-white rounded table-hover text-center">
            <thead class="table-dark">
              <tr>
                <th>#</th>
                <th>Ad</th>
                <th>Soyad</th>
                <th>Email</th>
                <th>Nomre</th>
                <th>Redakte</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item,index ) in items" :key="item.id">
                <td>{{ index + 1 }}</td>
                <td>{{ item.name }}</td>
                <td>{{ item.surname }}</td>
                <td>{{ item.email }}</td>
                <td>{{ item.number }}</td>
                <td>
                  <button class="btn btn-warning" @click.prevent="edit(item);">Redakte</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>

</template>


<script>
  import axios from 'axios';

  import config from '../config/config'

  export default {

    data() {
      return {
        name: '',
        surname: '',
        number: '',
        email: '',
        items: [],
        editId: null,
        searchTerm: '',
        errors: {},
      };
    },

    methods: {

      searchAccount() {
        axios
          .get(`${config.apiBaseUrl}/students/search`, {
            headers: {
              'Authorization': `Bearer ${config.token}`
            },
            params: {
              number: this.searchTerm
            }
          })
          .then((response) => {
            this.items = [response.data]; // alınan veriyi items listesine ekle
            this.name = response.data.name;
            this.number = response.data.number;
            this.surname = response.data.surname;
            this.email = response.data.email;
          })
          .catch((error) => {
            alert('Belə bir tələbə mövcud deyil');
            console.log(error);
          });
      },


      edit(item) {
        this.editId = item.id;
        axios.get(`${config.apiBaseUrl}/students/${this.editId}`, {
            headers: {
              'Authorization': `Bearer ${config.token}`
            }
          })
          .then(response => {
            this.name = response.data.name;
            this.number = response.data.number;
            this.surname = response.data.surname;
            this.email = response.data.email;
          })
          .catch(error => {
            console.log(error);
          });
      },



      update() {
        axios.put(`${config.apiBaseUrl}/students/update`, {
            name: this.name,
            surname: this.surname,
            number: this.number,
            email: this.email,
          }, {

            headers: {
              'Authorization': `Bearer ${config.token}`,
            },
          })
          .then(response => {
            const index = this.items.findIndex(item => item.id === this.editId);
            this.items[index] = response.data;
            this.name = '';
            this.surname = '';
            this.number = '';
            this.email = '';
            this.editId = null;
          })
          .catch(error => {
            console.log(error);
          });
      },


      create() {
        this.errors = {}; // Reset errors
        let isValid = true;

        // Validation
        if (this.name.trim() === '') {
          this.errors.name = 'Ad boş olmamalıdır';
          isValid = false;
        }
        if (this.surname.trim() === '') {
          this.errors.surname = 'Soyad boş olmamalıdır';
          isValid = false;
        }
        if (this.number.trim() === '') {
          this.errors.number = 'Nomre adı boş olmamalıdır';
          isValid = false;
        }
        if (this.email.trim() === '') {
          this.errors.email = 'Email boş olmamalıdır';
          isValid = false;
        }

        if (isValid) {
          axios.put(`${config.apiBaseUrl}/students`, {
              name: this.name,
              surname: this.surname,
              email: this.email,
              number: `994${this.number}`,
            }, {

              headers: {
                'Authorization': `Bearer ${config.token}`,
              },
            })
            .then(response => {
              this.items.push(response.data);
              this.name = '';
              this.surname = '';
              this.number = '';
              this.email = '';
              this.errors = {};

              alert("Istifadeci ugurla yaradildi.");
            })
            .catch(error => {
              console.log(error);
            });
        }
      }
    },

    mounted() {

      axios.get(`${config.apiBaseUrl}/students/all`, {
          headers: {
            'Authorization': `Bearer ${config.token}`
          }
        })
        .then(response => {
          this.items = response.data;
        })
        .catch(error => {
          console.log(error);
        });

    },


  };
</script>