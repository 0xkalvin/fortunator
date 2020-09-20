import React, {  } from 'react';
import { BrowserRouter, Route, Switch, Redirect} from 'react-router-dom'
import { isAuthenticated } from './auth';
import UserRegister from './pages/UserRegister';
import Home from './pages/Home';
import Login from './pages/Login';
import RegisterSuccess from './pages/RegisterSuccess'


export default function Routes(){
    const PrivateRoute = ({ component: Component, ...rest }) => (
        <Route {...rest} render={props => (
            isAuthenticated() === 'true'? (
                <Component {...props}/>
            ):(
                <Redirect to={{ pathname: '/login', state: { from: props.location }}}/>
            )
        )}/>
    )
    const LoginRoute = ({ component: Component, ...rest }) => (
        <Route {...rest} render={props => (
            isAuthenticated() === 'false'? (
                <Component {...props}/>
            ):(
                <Redirect to={{ pathname: '/home', state: { from: props.location }}}/>
            )
        )}/>
    )

    return(
        <BrowserRouter>
            <Switch>
                <LoginRoute path="/login" exact component={Login}/>
                <LoginRoute path="/" exact component={Login}/>
                <PrivateRoute path="/home" component={Home}/>
                <Route path="/register" component={UserRegister}/>
                <Route path="/register-success" component={RegisterSuccess}/>       
            </Switch>
        </BrowserRouter>
    )
}