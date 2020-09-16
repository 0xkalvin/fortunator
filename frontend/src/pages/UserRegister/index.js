import React, { useState } from 'react';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import finger from '../../assets/finger.gif'
import Logo from '../../components/Logo'

export default function UserRegister() {
        const [name, setName] = useState('');
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');
        const [confirmPassword, setConfirmPassword] = useState('');
        const [passwordMisMatched, setPasswordMisMatched] = useState('');

        async function userRegister(){
            if (password === confirmPassword){

                try{
                    console.log("entroyu");
                    await api.post('/users')
                }catch(err){
                    alert(err);
                }  
            }else{
                setPasswordMisMatched(true);
            }
                
        }
        console.log(process.env.REACT_APP_API_URL);
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
                <img src={finger} height="55px" />
                <h1 class="title-gif">Cadastro de Usuário</h1>
            </div>
           
           <p class="sub-title">Preencha os campos para se cadastrar.</p>

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
                   type="password"
                   value={password}
                   onChange={e => { setPassword(e.target.value) }}
               />
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