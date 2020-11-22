import React, {  } from 'react';
import { BrowserRouter, Route, Switch, Redirect} from 'react-router-dom'
import { isAuthenticated } from './auth';
import UserRegister from './pages/UserRegister';
import Home from './pages/Home';
import Login from './pages/Login';
import RegisterSuccess from './pages/RegisterSuccess'
import RegisterTransaction from './pages/RegisterTransaction'
import RegisterCategory from './pages/RegisterCategory'
import RegisterGoal from './pages/RegisterGoal'
import Goals from './pages/Goals'
import Extract from './pages/Extract'


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
        <Route {...rest} render={props => {
            
            const authenticated = isAuthenticated()

            if( authenticated == null || authenticated === 'false' ) {
                return <Component {...props}/>
            }
            else{
                return <Redirect to={{ pathname: '/home', state: { from: props.location }}}/>
            }
    }}/>
    )

    return(
        <BrowserRouter>
            <Switch>
                <LoginRoute path="/login" exact component={Login}/>
                <LoginRoute path="/" exact component={Login}/>
                <PrivateRoute path="/home" component={Home}/>
                <LoginRoute path="/register" component={UserRegister}/>
                <LoginRoute path="/register-success" component={RegisterSuccess}/>
                <PrivateRoute path="/register-transaction" component={RegisterTransaction}/>      
                <PrivateRoute path="/register-category" component={RegisterCategory}/> 
                <PrivateRoute path="/extract" component={Extract}/>    
                <PrivateRoute path="/register-goal" component={RegisterGoal}/>   
                <PrivateRoute path="/goals" component={Goals}/>           
            </Switch>
        </BrowserRouter>
    )
}