import React, {  } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import UserRegister from './pages/UserRegister';
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import Login from './pages/Login'
import RegisterSuccess from './pages/RegisterSuccess'
=======
import Home from './pages/Home';
import Login from './pages/Login';
>>>>>>> front: Init home
=======
=======
>>>>>>> front: add Logo svg animation
import Home from './pages/Home';
import Login from './pages/Login';
import RegisterSuccess from './pages/RegisterSuccess'
<<<<<<< HEAD
>>>>>>> front: add registerSucces page
>>>>>>> front: add registerSucces page
=======

>>>>>>> front: add Logo svg animation

export default function Routes(){
    return(
        <BrowserRouter>
            <Switch>
                <Route path="/register" component={UserRegister}/>
                <Route path="/login" component={Login}/>
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
                <Route path="/register-success" component={RegisterSuccess}/>
                <Route path="/" component={Login}/>
=======
                <Route path="/home" component={Home}/>
>>>>>>> front: Init home
=======
=======
>>>>>>> front: add Logo svg animation
                <Route path="/home" component={Home}/>
                <Route path="/register-success" component={RegisterSuccess}/>
                <Route path="/" component={Login}/>
<<<<<<< HEAD
>>>>>>> front: add registerSucces page
>>>>>>> front: add registerSucces page
=======
>>>>>>> front: add Logo svg animation
            </Switch>
        </BrowserRouter>
    )
}