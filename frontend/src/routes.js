import React, {  } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import UserRegister from './pages/UserRegister';
<<<<<<< HEAD
<<<<<<< HEAD
import Login from './pages/Login'
import RegisterSuccess from './pages/RegisterSuccess'
=======
import Home from './pages/Home';
import Login from './pages/Login';
>>>>>>> front: Init home
=======
import Home from './pages/Home';
import Login from './pages/Login';
=======
import Login from './pages/Login'
import RegisterSuccess from './pages/RegisterSuccess'
>>>>>>> front: add registerSucces page
>>>>>>> front: add registerSucces page

export default function Routes(){
    return(
        <BrowserRouter>
            <Switch>
                <Route path="/register" component={UserRegister}/>
                <Route path="/login" component={Login}/>
<<<<<<< HEAD
<<<<<<< HEAD
                <Route path="/register-success" component={RegisterSuccess}/>
                <Route path="/" component={Login}/>
=======
                <Route path="/home" component={Home}/>
>>>>>>> front: Init home
=======
                <Route path="/home" component={Home}/>
=======
                <Route path="/register-success" component={RegisterSuccess}/>
                <Route path="/" component={Login}/>
>>>>>>> front: add registerSucces page
>>>>>>> front: add registerSucces page
            </Switch>
        </BrowserRouter>
    )
}