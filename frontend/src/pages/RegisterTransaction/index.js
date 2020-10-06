import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import { Redirect } from 'react-router'
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import registerGif from '../../assets/register.gif'
import Logo from '../../components/Logo'

export default function RegisterTransaction() {
        const [name, setName] = useState('');
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');
        const [confirmPassword, setConfirmPassword] = useState('');
        const [passwordMisMatched, setPasswordMisMatched] = useState('');
        const [eye, setEye] = useState('close');
        const [goToLogin, setGoToLogin] = useState(false);


        async function userRegister(){
            if (password === confirmPassword){
                try{
                    const data = {name: name, email: email, password: password}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    await api.post('/users', data, headers).then(function(response){
                        if(response.status === 200){
                            setGoToLogin(true);
                        }
                    })
                }catch(err){
                    if(err.response === undefined){
                        alert("Algo deu errado :(");
                    }else{
                        if(err.response.status === 409){
                            alert("Usuário já cadastrado.");
                        }if(err.response.status >= 500){
                            alert("Serviço indisponível.");
                        }
                    }                                            
                }  
            }else{
                setPasswordMisMatched(true);
            }
        }
        

        return (
        <div>
            {(function () {
                    if(goToLogin === true){ return <Redirect to="/register-success"/> }
               })()}

            <div className="div-logo">
                <Logo />
                <div className="div-logo-description">
                    <h2>Fortunator</h2>
                    <h3>Controle Financeiro</h3>
                </div>
            </div>
            
            <div className="div-gif">
                <img className="pen-gif" src={registerGif} alt="pen-gif" height="120px" />
                <div className="user-register-description">
                    <h1 className="register-title-gif">Cadastro de Transação</h1>
                    <p className="sub-title">Preencha os campos para adicionar uma transação</p>
                </div>
            </div>
            <Link className="back-link" to="/login">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>
           <form>
               <input
                   placeholder="Descrição da Transação"
                   value={name}
                   onChange={e => { setName(e.target.value) }}
               />
                <br></br>
                <label for="TypeTransaction">Escolha o Tipo de Transação</label>
                <br></br>
                <select name="Transaction" id="Transac">
                <option value="Comida">Comida</option>
                <option value="Roupa">Roupa</option>
                <option value="Despesas">Despesas</option>
                <option value="Serviços">Serviços</option>
                </select>
                <br></br>
                <label for="DateTransaction">Escolha a data da Transação</label>
                <br></br>
               <input
                   placeholder="Data da Transação"
                   id="senha"
                   type="date"
                   value={password}
                   onChange={e => { setPassword(e.target.value) }}
               />
                

               <button className="button-intern" type="button" onClick={userRegister}>Salvar</button>
           </form>
       </div>
        )
}