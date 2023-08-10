<script setup>
    import 'bootstrap/dist/css/bootstrap.css'
    import Header from './Header.vue';
</script>

<template>

    <Header />

    <div class="container">
        <div class="my-5 shadow p-5">
            <h2 class="text-center">{{ editId ? 'Ödənişi Redaktə Et' : 'Ödənişi daxil edin' }}</h2>
            <form>
                <div class="row mt-5">
                    <div class="col-6">
                        <div class="mb-4">
                            <label class="my-2 fw-bold">
                                <h4>Məbləğ</h4>
                            </label>
                            <input class="form-control form-control-lg" type="number" v-model="amount"
                                placeholder="Dərsin ödənişi" />
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="mb-4">
                            <label class="my-2 fw-bold">
                                <h4>Çekin Tarixi</h4>
                            </label>
                            <input class="form-control form-control-lg" type="date" v-model="checkDate" />
                        </div>
                    </div>
                </div>
                <div class="row mt-5">
                    <div class="col-6">
                        <div class="mb-4">
                            <label class="my-2 fw-bold">
                                <h4>Kursun ayı</h4>
                            </label>
                            <input class="form-control form-control-lg" type="number" v-model="courseMonth"
                                placeholder="Ayı daxil edin" />
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="mb-4">
                            <label class="my-2 wf-bold">
                                <h4>Kursu</h4>
                            </label>
                            <select name="courseId" v-model="courseId" class="form-control form-control-lg">
                                <option value="">Kursu secin</option>
                                <option v-for="item in courses" :key="item.id" :value="item.id">{{ item.name }}</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row mt-5">
                    <div class="col-6">
                        <div class="mb-4">
                            <label class="my-2 wf-bold">
                                <h4>Tələbə</h4>
                            </label>
                            <select name="studentId" v-model="studentId" class="form-control form-control-lg">
                                <option value="">Tələbə seçin</option>
                                <option v-for="item in students" :key="item.id" :value="item.id">{{ item.studentName }}
                                    {{ item.surname }}</option>
                            </select>
                        </div>
                    </div>
                    <div class="row mt-5">
                        <div class="col-6">
                            <div class="mb-4">
                                <label class="my-2 fw-bold">
                                    <h4>Kartın sahibi</h4>
                                </label>
                                <input class="form-control form-control-lg" type="text" v-model="cardName"
                                    placeholder="Kartın sahibini daxil edin" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mb-5">
                    <button v-if="!editId" @click.prevent="create" class="btn btn-success mx-2 mb-5 float-end">Daxil
                        et</button>

                    <div v-else-if="editId">
                        <button @click.prevent="update" class="btn btn-success mx-2 mb-5 float-end">Yadda
                            saxla</button>

                        <button onclick="history.back()" class="btn btn-secondary mx-2 mb-5 float-end">Geri
                            qayıt</button>
                    </div>
                </div>
            </form>
        </div>

        <div class="row my-1 mb-5">
            <h4 class="fs-4 mb-3">Tələbələr</h4>
            <div class="col-4 col-lg-4">
                <form @submit.prevent="getTime">
                    <div class="input-group">
                        <input type="date" class="form-control" v-model="from">
                        <input type="date" class="form-control" v-model="to">
                        <button class="btn btn-dark" type="submit">Tarix (axtar)</button>
                    </div>
                </form>
            </div>

            <div class="col-4" style="margin-left: 70%;">
                <div class="input-group mb-3 px-5">
                    <input type="text" class="form-control" v-model="searchTerm"
                        placeholder="Telebenin elaqe nomresi...">
                    <button class="btn btn-dark" @click="searchstudent">Axtar</button>
                </div>
            </div>

            <div class="col-12">
                <div class="table-responsive shadow-lg">
                    <table class="table bg-white rounded table-hover text-center">
                        <thead class="table-dark">
                            <tr>
                                <th>#</th>
                                <th>Mebleg</th>
                                <th>Çekin tarixi</th>
                                <th>Ayliq</th>
                                <th>Kurs</th>
                                <th>Istifadeci</th>
                                <th>Kartın sahibi</th>
                                <th>Redakte</th>
                            </tr>
                        </thead>
                        <tbody>
    <tr v-for="(item, index) in payments" :key="index">
        <td>{{ index + 1 }}</td>
        <td>{{ item.amount }} AZN</td>
        <td>{{ item.checkDate}}</td>
        <td>{{ item.courseMonth }} ay</td>
        <td>{{ courseMap[item.courseId] }}</td>
        <td> {{ studentMap[item.studentId]  }}</td>
        <td>{{ item.cardName }}</td>
        <td>
            <button class="btn btn-warning" @click.prevent="edit(item)">Redakte</button>
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
                studentMap: [], // Map student IDs to student names
    courseMap: [],  // Map course IDs to course names
                receipt: '',
                amount: '',
                checkDate: '',
                courseMonth: '',
                courseId: '',
                studentId: '',
                cardName: '',
                students: [], //Telebeler
                courses: [], //Kurslar
                payments: [], //Odenisler
                editId: null,
                isModalShown: [],
                from: null,
                to: null,
                searchTerm:'',
                studentName:'',
            };
        },

        methods: {

            searchByStudentName() {
      if (this.studentName) {
        this.payments = this.payments.filter(payment =>
          this.studentMap[payment.studentId].toLowerCase().includes(this.studentName.toLowerCase())
        );
      } else {
        // If the search field is empty, reset the payments array
        this.getPayments(); // Assuming you have a method that retrieves all payments
      }
    },
  

            getTime() {
                const fromDate = this.from instanceof Date ? this.from : new Date(this.from);
    const toDate = this.to instanceof Date ? this.to : new Date(this.to);

    axios.get(`http://localhost:8084/payments/filter`, {
        params: {
            from: fromDate.toISOString().split('T')[0], // Convert Date to "yyyy-MM-dd" string
            to: toDate.toISOString().split('T')[0]
        }
    })
    .then((response) => {
        this.payments = response.data;
        this.cardName = response.data.cardName;
        this.amount = response.data.amount;
        this.studentId = response.data.studentId;
        this.courseId = response.data.courseId;
        //this.receipt = response.data.receipt;
        this.checkDate = response.data.checkDate;
        this.courseMonth = response.data.courseMonth;
    })
    .catch((error) => {
        console.error(error);
    });
},


            showModal(index) {
                this.isModalShown[index] = true;
            },
            hideModal(index) {
                this.isModalShown[index] = false;
            },

            searchstudent() {
                axios
                    .get(`http://localhost:8084/payments/payments/byCardName`, {
          params: {
            cardName: this.searchTerm,
          },
                    })
                    .then((response) => {
                        this.payments = response.data; // alınan veriyi items listesine ekle
                        this.cardName = response.data.cardName;
                        this.amount = response.data.amount;
                        this.studentId = response.data.studentId;
                        this.courseId = response.data.courseId;
                        //this.receipt = response.data.receipt;
                        this.checkDate = response.data.checkDate;
                        this.courseMonth = response.data.courseMonth;
                    })
                    .catch((error) => {
                        alert('Belə bir odenis mövcud deyil');
                        console.log(error);
                    });
            },


            update() {
                axios.put(`http://localhost:8084/payments/update`, {
                        cardName: this.cardName,
                        amount: this.amount,
                        studentId: this.studentId,
                        courseId: this.courseId,
                       // receipt: this.receipt,
                        checkDate: this.checkDate,
                        courseMonth: this.courseMonth,
                        id: this.editId
                    })
                    .then(response => {
                        const index = this.payments.findIndex(item => item.id === this.editId);
                        this.payments[index] = response.data;
                        this.cardName = '';
                        this.amount = '';
                        //this.receipt = '';
                        this.courseId = '';
                        this.studentId = '';
                        this.checkDate = '';
                        this.courseMonth = '';
                        this.editId = null;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            },
            edit(item) {
                this.editId = item.id;
                axios.get(`http://localhost:8084/payments/${this.editId}`)
                    .then(response => {
                        this.cardName = response.data.cardName;
                        this.amount = response.data.amount;
                        this.studentId = response.data.studentId;
                        this.courseId = response.data.courseId;
                       // this.receipt = response.data.receipt;
                        this.checkDate = response.data.checkDate;
                        this.courseMonth = response.data.courseMonth;
                    })
                    .catch(error => {
                        console.log(error);
                    });

            },


            //onFileChange(event) {
            //     const file = event.target.files[0];
            //     this.receipt = '';

            //     const reader = new FileReader();
            //     reader.readAsDataURL(file);

            //     reader.onload = () => {
            //         this.receipt = reader.result;
            //     };
            // },

            create() {

                this.errors = {}; // Reset errors
                let isValid = true;

                // Validation
                if (this.amount.toString().trim() === '') {
                    this.errors.amount = 'Odenis boş olmamalıdır';
                    isValid = false;
                }
                if (this.studentId.toString().trim() === '') {
                    this.errors.studentId = 'Istifadeci adı boş olmamalıdır';
                    isValid = false;
                }

                if (this.courseId.toString().trim() === '') {
                    this.errors.courseId = 'Kursun adı boş olmamalıdır';
                    isValid = false;
                }

                if (this.checkDate.trim() === '') {
                    this.errors.checkDate = 'Tarix boş olmamalıdır';
                    isValid = false;
                }

                if (this.cardName.trim() === '') {
                    this.errors.cardName = 'Kart melumatlari boş olmamalıdır';
                    isValid = false;
                }

                if (isValid) {
                    axios.put(`http://localhost:8084/payments`, {
                            //receipt: this.receipt,
                            amount: this.amount,
                            studentId: this.studentId,
                            courseId: this.courseId,
                            checkDate: this.checkDate,
                            courseMonth: this.courseMonth,
                            cardName: this.cardName,
                        })
                        .then(response => {
                            this.payments.push(response.data);
                            this.amount = '';
                            //this.receipt = '';
                            this.studentId = '';
                            this.courseId = '';
                            this.checkDate = '';
                            this.courseMonth = '';
                            this.cardName = '';
                            this.errors = {};
                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            },
        },

        mounted() {
           
            axios.get(`http://localhost:8081/students/all`)
                .then(response => {
                    this.students = response.data; //Telebeleri alıyoruz
                    this.studentMap = response.data.reduce((map, student) => {
        map[student.id] = student.name;
        return map;
      }, {});
    })
    .catch(error => {
      console.log(error);
    });
            axios.get(`http://localhost:8084/payments/all`)
                .then(response => {
                    this.payments = response.data; //Telebeleri alıyoruz
                })
                .catch(error => {
                    console.log(error);
                });

            axios.get(`http://localhost:8081/courses/active`)
                .then(response => {
                    this.courses = response.data; //Kursları alıyoruz
                    this.courseMap = response.data.reduce((map, course) => {
        map[course.id] = course.name;
        return map;
      }, {});
    })
    .catch(error => {
      console.log(error);
    });

        },

    };
</script>