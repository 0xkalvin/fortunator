import React, {  } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom'

import UserRegister from './pages/UserRegister';
import Login from './pages/Login'
import RegisterSuccess from './pages/RegisterSuccess'

export default function Routes(){
    return(
        <BrowserRouter>
            <Switch>
                <Route path="/register" component={UserRegister}/>
                <Route path="/login" component={Login}/>
                <Route path="/register-success" component={RegisterSuccess}/>
                <Route path="/" component={Login}/>
            </Switch>
        </BrowserRouter>
    )
}