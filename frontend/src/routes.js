import React, {  } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom'

import UserRegister from './pages/UserRegister';

export default function Routes(){
    return(
        <BrowserRouter>
            <Switch>
                <Route path="/register" component={UserRegister}/>
            </Switch>
        </BrowserRouter>
    )
}