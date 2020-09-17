import React, {  } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom'

import UserRegister from './pages/UserRegister';
import Login from './pages/Login'

export default function Routes(){
    return(
        <BrowserRouter>
            <Switch>
                <Route path="/register" component={UserRegister}/>
                <Route path="/login" component={Login}/>
            </Switch>
        </BrowserRouter>
    )
}