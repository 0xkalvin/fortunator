import axios from 'axios';

const api = axios.create({
<<<<<<< HEAD
<<<<<<< HEAD
    baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080'
=======

    baseURL: 'http://localhost:8080'
    //baseURL: process.env.REACT_APP_VAR_TESTE
>>>>>>> front: Init home

=======
    baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080'
>>>>>>> front: pass api url as env variable
})

export default api;