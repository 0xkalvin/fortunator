import React, {  } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import UserRegister from './pages/UserRegister';
<<<<<<< HEAD
import Login from './pages/Login'
import RegisterSuccess from './pages/RegisterSuccess'
=======
import Home from './pages/Home';
import Login from './pages/Login';
>>>>>>> front: Init home

export default function Routes(){
    return(
        <BrowserRouter>
            <Switch>
                <Route path="/register" component={UserRegister}/>
                <Route path="/login" component={Login}/>
<<<<<<< HEAD
                <Route path="/register-success" component={RegisterSuccess}/>
                <Route path="/" component={Login}/>
=======
                <Route path="/home" component={Home}/>
>>>>>>> front: Init home
            </Switch>
        </BrowserRouter>
    )
}