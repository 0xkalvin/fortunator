import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { AiOutlineEye, AiOutlineEyeInvisible} from 'react-icons/ai';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import Logo from '../../components/Logo'
import finger from '../../assets/finger.gif'

export default function Login() {
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');
        const [eye, setEye] = useState('close');

        async function userLogin(){
                try{
                    const data = {email: email, password: password}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    await api.post('/login', data, headers)
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
               <button className="button-intern" type="button" onClick={userLogin}>Entrar</button>
           </form>
           <Link className="sign-up-link" to="/register">Ainda não é usuário? Inscrever-se</Link>
       </div>
        )

}