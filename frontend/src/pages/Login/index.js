import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { AiOutlineEye, AiOutlineEyeInvisible} from 'react-icons/ai';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
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
                    if(err.response.status >= 500){
                        alert("Serviço indisponível.");
                    }else{
                        alert(err);
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
            <div class="div-gif">
                <img src={finger} height="55px" />
                <div>
                     <h1 class="title-gif">Login</h1>
                     <p class="sub-title">Preencha os campos com suas credenciais para entrar.</p>
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
                            <button type="button" class="invisible-button" onClick={closeEye}><AiOutlineEye size={22} color="#00a8a0" /></button>   
                        )
                    }if(eye === "close"){
                        return(
                            <button type="button" class="invisible-button" onClick={openEye}><AiOutlineEyeInvisible size={22} color="#00a8a0" /></button>
                        )
                    } 
               })()}
               <button className="button-intern" type="button" onClick={userLogin}>Entrar</button>
           </form>
           <Link class="sign-up-link" to="/register">Ainda não é usuário? Inscrever-se</Link>
       </div>
        )

}