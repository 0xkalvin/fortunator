import React, {  } from 'react';
import { BrowserRouter, Route, Switch, Redirect} from 'react-router-dom'
import { isAuthenticated } from './auth';
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
    const PrivateRoute = ({ component: Component, ...rest }) => (
        <Route {...rest} render={props => (
            isAuthenticated() == 'true'? (
                <Component {...props}/>
            ):(
                <Redirect to={{ pathname: '/login', state: { from: props.location }}}/>
            )
        )}/>
    )
    const LoginRoute = ({ component: Component, ...rest }) => (
        <Route {...rest} render={props => (
            isAuthenticated() == 'false'? (
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
                <PrivateRoute path="/home" component={Home}/>
                <Route path="/register" component={UserRegister}/>
<<<<<<< HEAD
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
=======
                <Route path="/register-success" component={RegisterSuccess}/>
>>>>>>> front: add simplpe authorization
            </Switch>
        </BrowserRouter>
    )
}