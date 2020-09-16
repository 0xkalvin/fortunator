import React, { useState, state, count } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import { AiOutlineEye, AiOutlineEyeInvisible} from 'react-icons/ai';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import Logo from '../../components/Logo'
import registerGif from '../../assets/register.gif'


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
                    await api.post('/users')
                }catch(err){
                    alert(err);
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

       
        return (
        <div>
            <div class="div-logo">
                <Logo />
                <div class="div-logo-description">
                    <h2>Fortunator</h2>
                    <h3>Controle Financeiro</h3>
                </div>
            </div>
            
            
            <div class="div-gif">
                <img className="pen-gif" src={registerGif} height="120px" />
                <div className="user-register-description">
                    <h1 class="register-title-gif">Cadastro de Usuário</h1>
                    <p class="sub-title">Preencha os campos para se cadastrar.</p>
                </div>
            </div>
            <Link className="back-link" to="/login">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>
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