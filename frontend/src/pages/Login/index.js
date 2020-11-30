import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { AiOutlineEye, AiOutlineEyeInvisible} from 'react-icons/ai';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import Logo from '../../components/Logo'
import finger from '../../assets/finger.gif'
import NoLoginHamburguer from '../../components/NoLoginHamburguer'


export default function Login() {
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');
        const [eye, setEye] = useState('close');
        const [unauthorized, setUnauthorized] = useState('');
        const [username, setUsername] = useState('');

        async function userLogin(){
                try{
                    const data = {email: email, password: password}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    const response = await api.post('users/login', data, headers)
                        if(response.status === 200){
                            localStorage.setItem('auth', 'true');
                            localStorage.setItem('userId', response.data.id);
                            localStorage.setItem('userName', response.data.name);
                            localStorage.setItem('userBalance', response.data.balance.amount);
                            window.location.reload(false);
                        }
                }catch(err){
                    if(err.response === undefined){
                        alert("Algo deu errado :(");
                    }else{
                        if(err.response.status === 401 || err.response.status === 404){
                            setUnauthorized(true);
                        }if(err.response.status === 400 || 403){
                            alert("Verifique se os campos foram preenchidos corretamente e se o usuário existe.");
                        }if(err.response.status >= 500){
                            alert("Serviço indisponível.");
                        }else{
                            alert("Algo deu errado :(");
                        }
                    }  
                }  
        }

        function closeEye(){
            setEye("close");
            document.getElementById("senha").type = "password";
        }
        function openEye(){
            setEye("open");
            document.getElementById("senha").type = "text";
        }

        async function sendNewPassword(){
            if(username===""){
                alert("Insira um nome de usuário. ");
            }else{
                try{
                    const data = {email:username}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    const response = await api.post('/users/password', data, headers)
                        if(response.status === 200){
                           alert("E-mail enviado com sucesso!");
                        }
                }catch(err){
                    if(err.response === undefined){
                        alert("Algo deu errado :(");
                    }else{
                        if(err.response.status >= 500){
                            alert("Serviço indisponível.");
                        }
                    }                                            
                }  
            }
        }
        
        return (         
        <div>    
            <div id="abrirModal" class="modal">
                <div>
                    <a href="#fechar" title="Fechar" class="fechar">x</a>
                    <h2 style={{paddingBottom:"3%"}}>Recuperação de senha.</h2>
                    <p style={{paddingBottom:"4%"}}>Digite seu e-mail cadastrado para que uma nova senha seja enviada.</p>  

                    <input
                        type="text"                           
                        className="input-maior"
                        placeholder="E-mail"
                        onChange={e => { setUsername(e.target.value) }}
                        value={username}
                    />
                    <button className="button-intern" type="button" style={{marginLeft:"23%"}} onClick={sendNewPassword}>Enviar</button>
                </div>
            </div> 
                
            <NoLoginHamburguer/>
            <div className="div-logo">
                <Logo />
                <div className="div-logo-description">
                    <h2>Fortunator</h2>
                    <h3>Controle Financeiro</h3>
                </div>
            </div>

            <div className="div-gif">
                <img src={finger} height="55px" alt="finger-gif" />
                <div>
                     <h1 className="title-gif">Login</h1>
                     <p className="sub-title">Preencha os campos com suas credenciais para entrar.</p>
                </div>     
            </div>

           <form>
               <input
                   placeholder="E-mail"
                   value={email}
                   onChange={e => { setEmail(e.target.value) }}
               />
               {(function () {
                   if(unauthorized === true){
                    return(<p className="password-mismatched">Usuário e senha não correspondem</p>)
                   }
               })()}
               <input
                   placeholder="Senha"
                   id="senha"
                   type="password"
                   value={password}
                   onChange={e => { setPassword(e.target.value) }}
               />
               
                {(function () {
                    if(eye === "open"){
                        return(
                            <button type="button" className="invisible-button" onClick={closeEye}><AiOutlineEye size={22} color="#00a8a0" /></button>   
                        )
                    }if(eye === "close"){
                        return(
                            <button type="button" className="invisible-button" onClick={openEye}><AiOutlineEyeInvisible size={22} color="#00a8a0" /></button>
                        )
                    } 
               })()}
               <button className="button-intern" type="button" onClick={userLogin} style={{marginBottom:"3%"}}>Entrar</button>
           </form>
           <div className="div-links-login">
                <a className="refresh-password-link" href="#abrirModal" style={{paddingBottom:"1%"}}>Esqueceu a senha?</a>
                <Link className="sign-up-link" to="/register">Ainda não é usuário? Inscrever-se</Link>
           </div> 
       </div>
        )

}