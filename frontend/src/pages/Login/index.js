import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import finger from '../../assets/finger.gif'

export default function Login() {
        const [name, setName] = useState('');
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');

        async function userLogin(){
                try{
                    console.log("entroyu");
                    await api.post('/users')
                }catch(err){
                    alert(err);
                }  
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
                   type="password"
                   value={password}
                   onChange={e => { setPassword(e.target.value) }}
               />
               <button className="button-intern" type="button" onClick={userLogin}>Entrar</button>
           </form>
           <Link class="sign-up-link" to="/register">Ainda não é usuário? Inscrever-se</Link>
       </div>
        )

}