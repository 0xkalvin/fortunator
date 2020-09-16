import React, { useState, state, count } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import { AiOutlineEye, AiOutlineEyeInvisible} from 'react-icons/ai';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import registerGif from '../../assets/register.gif'
import Logo from '../../components/Logo'


export default function UserRegister() {
        const [name, setName] = useState('');
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');
        const [confirmPassword, setConfirmPassword] = useState('');
        const [passwordMisMatched, setPasswordMisMatched] = useState('');
        const [eye, setEye] = useState('close');

        async function userRegister(){
            if (password === confirmPassword){
                try{
                    const data = {name: name, email: email, password: password}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    await api.post('/users', data, headers)
                }catch(err){
                    if(err.response.status === 409){
                        alert("Usuário já cadastrado.");
                    }if(err.response.status >= 500){
                        alert("Serviço indisponível.");
                    }else{
                        alert(err);
                    }                                                          
                }  
            }else{
                setPasswordMisMatched(true);
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
        console.log(process.env.REACT_APP_API_URL);
        return (
        <div>
            <Link className="back-link" to="/login">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>
            
            <div class="div-logo">
                <Logo />
                <div class="div-logo-description">
                    <h2>Fortunator</h2>
                    <h3>Controle Financeiro</h3>
                </div>
            </div>

            <div class="div-gif">
                <img src={registerGif} height="120px" />
                <div>
                    <h1 class="register-title-gif">Cadastro de Usuário</h1>
                    <p class="sub-title">Preencha os campos para se cadastrar.</p>
                </div>
            </div>

           <form>
               <input
                   placeholder="Nome"
                   value={name}
                   onChange={e => { setName(e.target.value) }}
               />
               <input
                   placeholder="E-mail"
                   value={email}
                   onChange={e => { setEmail(e.target.value) }}
               />
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
                            <button type="button" class="invisible-button" onClick={closeEye}><AiOutlineEye size={22} color="#00a8a0" /></button>   
                        )
                    }if(eye === "close"){
                        return(
                            <button type="button" class="invisible-button" onClick={openEye}><AiOutlineEyeInvisible size={22} color="#00a8a0" /></button>
                        )
                    } 
               })()}

               {(function () {
                   if(passwordMisMatched === true){
                    return(<p class="password-mismatched">Senha não correspondente</p>)
                   }
               })()}
               
               <input
                   placeholder="Confirmar senha"
                   type="password"
                   value={confirmPassword}
                   onChange={e => { setConfirmPassword(e.target.value) }}
               />
               <button className="button-intern" type="button" onClick={userRegister}>Enviar</button>
           </form>
       </div>
        )
}