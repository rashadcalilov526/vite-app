<script setup>
    import 'bootstrap/dist/css/bootstrap.css'
    import Header from './Header.vue';
    
</script>

<template>

    <Header />

    <div class="container">
        <div class="my-5 shadow p-5">
            <h2 class="text-center">{{ editId ? 'Xərci Redaktə Et' : 'Xərci daxil edin' }}</h2>
            <form>
                <div class="row mt-5">
                    <div class="col-6">
                        <div class="mb-4">
                            <label class="my-2 fw-bold">
                                <h4>Xərc dəyəri</h4>
                            </label>
                            <input class="form-control form-control-lg" type="number" v-model="costValue"
                                placeholder="Xərcin ödənişi" />
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="mb-4">
                            <label class="my-2 fw-bold">
                                <h4>Tarix</h4>
                            </label>
                            <input class="form-control form-control-lg" type="date" v-model="costDate" />
                        </div>
                    </div>
                </div>
                <div class="row mt-5">
                    <div class="col-6">
                        <div class="mb-4">
                            <label class="my-2 fw-bold">
                                <h4>Xercin adı</h4>
                            </label>
                            <input class="form-control form-control-lg" type="text" v-model="costName"
                                placeholder="Xərcin adı..." />
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="mb-4">
                            <label class="my-2 fw-bold">
                                <h4>Metn</h4>
                            </label>
                            <input class="form-control form-control-lg" type="text" v-model="description"
                                placeholder="Kartın sahibini daxil edin" />
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
            <h3 class="fs-4 mb-3">Xərclər</h3>
            <div class="col-12">
                <div class="table-responsive shadow-lg">
                    <table class="table bg-white rounded table-hover text-center">
                        <thead class="table-dark">
                            <tr>
                                <th>#</th>
                                <th>Mebleg</th>
                                <th>Xercin adı</th>
                                <td>Metn</td>
                                <th>Xercin tarixi</th>
                                <th>Redakte</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(item, index) in cost" :key="index">
                                <td>{{ index + 1 }}</td> 
                                <td>{{ item.costValue }} AZN</td>
                                <td>{{ item.costName }}</td>
                                <td>{{ item.description }}</td>
                                <td>{{ item.costDate }}</td>
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
                // receipt: null,
                costValue: '',
                costDate: '',
                costName: '',
                costId: '',
                cost: [], //Xercler
                description: '',
                editId: null,
                /* receipt: {
                    base64EncodedPhoto: null
                }, */
            };
        },
        

        methods: {

            update() {
                axios.put(`http://localhost:8085/costs`, {
                        costName: this.costName,
                        costValue: this.costValue,
                        description: this.description,
                        // receipt: this.receipt,
                        costDate: this.costDate,
                        id: this.editId
                    })
                    .then(response => {
                        const index = this.cost.findIndex(item => item.id === this.editId);
                        this.cost[index] = response.data;
                        this.costValue = '';
                        // this.receipt = '';
                        this.costDate = '';
                        this.costName = '';
                        this.description = '';
                        this.editId = null;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            },

            edit(item) {
                this.editId = item.id;
                axios.get(`http://localhost:8085/costs/${this.editId}`)
                    .then(response => {
                        this.costName = response.data.costName;
                        this.costValue = response.data.costValue;
                        // this.receipt = response.data.receipt;
                        this.costDate = response.data.costDate;
                        this.description = response.data.description;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            },
       

            // onFileChange(event) {
            //     const file = event.target.files[0];
            //     // this.receipt = null;

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
                if (this.costValue.toString().trim() === '') {
                    this.errors.costValue = 'Ödəniş boş olmamalıdır';
                    isValid = false;
                }
                if (this.description.toString().trim() === '') {
                    this.errors.description = 'Mətn adı boş olmamalıdır';
                    isValid = false;
                }
                if (this.costDate.trim() === '') {
                    this.errors.costDate = 'Tarix boş olmamalıdır';
                    isValid = false;
                }
                if (this.costName.trim() === '') {
                    this.errors.costName = 'Xərcin məlumatı boş olmamalıdır';
                    isValid = false;
                }
               
                if (isValid) {
                    axios.put(`http://localhost:8085/costs/create`, {
                            // receipt: this.receipt,
                            costValue: this.costValue,
                            description: this.description,
                            costDate: this.costDate,
                            costName: this.costName,
                        })
                        .then(response => {
                            this.cost.push(response.data);
                            this.costValue = '';
                            // this.receipt = '';
                            this.description = '';
                            this.costDate = '';
                            this.costName = '';
                            this.errors = {};
                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            },
        },

        mounted() {
            axios.get(`http://localhost:8085/costs/all`)
                .then(response => {
                    this.cost = response.data; //Xərcleri alıriq
                })
                .catch(error => {
                    console.log(error);
                });
        },

    };
</script>