<script setup>
  import 'bootstrap/dist/css/bootstrap.css';
  import Header from './Header.vue';
</script>

<template>

  <Header />

  <div class="container">
    <div class="row">

      <!--Kursu daxil etme-->
      <div class="col-5">
        <div class="my-5 shadow p-5">
          <h3 class="text-center mb-4">{{ editId ? 'Kursu Redaktə Et' : 'Kursu Daxil Edin' }}</h3>
          <form>
            <div class="mb-4">
              <label class="my-2 fw-bold">
              </label>
              <input class="form-control form-control-lg" v-model="name" type="text" placeholder="Kursun adı" />
            </div>
            <div class="mb-5">
              <button v-if="!editId" @click.prevent="create" class="btn btn-success mx-2 mb-5 float-end">Daxil
                et</button>

              <div v-else-if="editId">
                <button @click.prevent="update" class="btn btn-success mx-2 mb-5 float-end">Yadda
                  saxla</button>
                <button @click="$router.back()" class="btn btn-secondary mx-2 mb-5 float-end">Geri qayıt</button>
              </div>
            </div>
          </form>
        </div>
      </div>
      <!--Kursu daxil etme-->


      <!--Tələbəni kursa əlavə edin-->
      <div class="col-7">
        <div class="my-5 shadow p-5">
          <h3 class="text-center mb-4">Tələbəni kursa əlavə edin</h3>
          <form>
            <div class="row">
              <div class="mb-4 col-6">
                <label class="my-2 fw-bold"></label>
                <input class="form-control form-control-lg" type="text" v-model="number"
                  placeholder="Tələbənin nömrəsi" />
              </div>
              <div class="mb-4 col-6">
                <label class="my-2 fw-bold">
                </label>
                <select class="form-control form-control-lg" v-model="courseId">
                  <option value="">Kursu secin</option>
                  <option v-for="info in items" :value="info.id">{{info.name}}</option>
                </select>
              </div>
            </div>
            <div class="mb-4">
              <button class="btn btn-success mx-1 mb-5 float-end" @click.prevent="yarat">Daxil
                et</button>
            </div>
          </form>
        </div>
      </div>
      <!--Tələbəni kursa əlavə edin-->
    </div>


    <!-- Table -->
    <div class="row mb-5">
      <h3 class="fs-3">Kurslar</h3>
      <div class="col-4 float-end" style="margin-left: 70%;">
        <div class="input-group mb-3 px-5">
          <input type="text" class="form-control" v-model="searchcourse" placeholder="Kursun adı...">
          <button class="btn btn-dark" @click="searchCourse">Axtar</button>
        </div>
      </div>
      <div class="col-12">
        <div class="table-responsive shadow-lg">
          <table class="table bg-white rounded table-hover text-center">
            <thead class="table-dark">
              <tr>
                <th>#</th>
                <th>Kurs</th>
                <th>Aç/Bağla</th>
                <th>Redakte</th>
                <th>Tələbələr</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in items" :key="index">
                <td>{{ index + 1 }}</td>
                <td>{{ item.name }}</td>
                <td>
                  <div class="form-switch">
                    <input class="form-check-input" type="checkbox" :id="`enable-switch-${index}`"
                      :checked="item.isActive == true"
                      @change="item.isActive ? toggleCourseDisabled(item.id) : toggleCourseEnabled(item.id)">
                  </div>
                </td>
                <td>
                  <button class="btn btn-warning" @click.prevent="edit(item);">Redakte</button>
                </td>
                <td>
                  <button class="btn btn-info" @click="showModal = true; selectedCourse = item;">Tələbələr</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <!-- Table -->
  </div>
  <div class="modal" v-if="showModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ selectedCourse.name }} kursunun tələbələri</h5>
            <button type="button" class="close" data-dismiss="modal" @click="showModal = false">              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <table class="table bg-white rounded table-hover text-center">
              <thead class="table-dark">
                <tr>
                  <th>#</th>
                  <th>Ad</th>
                  <th>Soyad</th>
                  <th>Nömrə</th>
                  <th>Sil</th>
                </tr>
              </thead>
              <tbody>
              <tr v-for="(student, index) in selectedCourse.students" :key="student.id">
                <td>{{ index + 1 }}</td>
                <td>{{ student.name }}</td>
                <td>{{ student.surname }}</td>
                <td>{{ student.number }}</td>
                <td>
                  <button class="btn btn-danger" @click="sil(student)">Sil</button>
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
        number: '',
        courseId: '',
        items: [],
        searchcourse: '',
        editId: null,
        showModal: false,
        selectedCourse: [],
      }; 
  },
    methods: {
      
      toggleCourseEnabled(courseId) {
        axios.post(`${config.apiBaseUrl}/courses/${courseId}/enable`)
          .then(response => {
            console.log(response.data);
          })
          .catch(error => {
            console.error(error);
          });
      },
    

      toggleCourseDisabled(courseId) {
        axios.post(`${config.apiBaseUrl}/courses/${courseId}/disable`)
          .then(response => {
            console.log(response.data);
          })
          .catch(error => {
            console.error(error);
          });
      },

      async sil(student) {
        const index = this.selectedCourse.students.indexOf(student);
        if (index !== -1) {
          try {
            await axios.post(
              `${config.apiBaseUrl}/courses/management?courseId=${this.selectedCourse.id}&studentId=${student.id}`
            );
            this.selectedCourse.students.splice(index, 1);
          } catch (error) {
            console.error(error);
          }
        }
      },

      yarat() {
        axios.post(`${config.apiBaseUrl}/students/enroll?number=${this.number}&courseId=${this.courseId}`,{
          headers: {
              'Authorization': `Bearer ${config.token}`
            },
        })
          .then(response => {
            this.items.push(response.data); // response'daki veriyi items'a ekliyoruz
            this.number = ''; // inputu temizliyoruz
            this.courseId = '';
          })
          .catch(error => {
            alert('Belə tələbə mövcud deyil.')
            console.log(error);
          });
      },

      create() {

        this.errors = {}; // Reset errors
        let isValid = true;

        // Validation
        if (this.name.trim() === '') {
          this.errors.name = 'Kursun adı boş olmamalıdır';
          isValid = false;
        }
        if (isValid) {

          axios.put(`${config.apiBaseUrl}/courses/create`, {
              name: this.name,
              isActive:this.isActive,
            })
            .then(response => {
              this.items.push(response.data); // response'daki veriyi items'a ekliyoruz
              this.name = ''; // inputu temizliyoruz
              this.isActive;
            })
            .catch(error => {
              console.log(error);
            });
        }
      },

      edit(item) {
        this.editId = item.id;
        axios.get(`${config.apiBaseUrl}/courses/get/${this.editId}`,{
          headers: {
              'Authorization': `Bearer ${config.token}`
            }
        })
          .then(response => {
            this.name = response.data.name;
          })
          .catch(error => {
            console.log(error);
          });
      },

      update() {
        axios.put(`${config.apiBaseUrl}/courses/update`, {
            id:this.editId,
            name: this.name,
            isactive:this.isActive,
          },
          {headers: {
              'Authorization': `Bearer ${config.token}`
            }
          })
          .then(response => {
            const index = this.items.findIndex(item => item.id === this.editId);
            this.items[index] = response.data;
            this.name = '';
          })
          .catch(error => {
            console.log(error);
          });
      },

      searchCourse() {
        axios
          .get(`${config.apiBaseUrl}/courses/search`, {
            headers: {
              'Authorization': `Bearer ${config.token}`
            },
            params: {
              name: this.searchcourse
            }
          })
          .then((response) => {
            this.items = [response.data]; // alınan veriyi items listesine ekle
            this.name = response.data.name;
          })
          .catch((error) => {
            alert('Belə bir kurs mövcud deyil');
            console.log(error);
          });
      },

    },

    mounted() {
      axios.get(`${config.apiBaseUrl}/courses/all`, {
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


<style>
  .modal {
    display: block;
    background-color: rgba(0, 0, 0, 0.4);
  }

  .modal-content {
    margin-top: 15%;
  }

  .close {
    color: #aaaaaa;
    float: right;
    font-size: 30x;
    font-weight: bold;
  }

  .close:hover,
  .close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
  }
</style>