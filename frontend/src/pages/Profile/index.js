import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import walletGif from '../../assets/wallet.gif'
import Hamburguer from '../../components/Hamburguer'

export default function Profile() {
        const [description, setDescription] = useState('');
        const [date, setDate] = useState('');
        const [amount, setAmount] = useState('');
        const [amountMasked, setAmountMasked] = useState('');
        const [type, setType] = useState('INCOMING');
        const [category, setCategory] = useState('1');
        const [categoryOptions, setCategoryOptions] = useState([]);
        var userId = localStorage.getItem('userId');
        const [userName, setUserName] = useState('');
        const [userEmail, setUserEmail] = useState('');
        const [userNameFirstTime, setUserNameFirstTime] = useState('');
        const [userEmailFirstTime, setUserEmailFirstTime] = useState('');
        const [userCurrentPassword, setUserCurrentPassword] = useState('');
        const [userNewPassword, setUserNewPassword] = useState('');

        HandleUser()

        function HandleUser(props){    
            useEffect(()=>{          
                try {                  
                    api.get('/users/' + userId).then( res => { 
                        setUserName(res.data.name);
                        setUserEmail(res.data.email);
                        setUserNameFirstTime(res.data.name);
                        setUserEmailFirstTime(res.data.email);
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }

        async function refreshPassword(){
                try{
                    var userNameData = null;
                    var userEmailData = null;
                    var userCurrentPasswordData = null;
                    var userNewPasswordData = null;
                    if(userName != userNameFirstTime){
                        userNameData = userName;
                    }if(userEmail != userEmailFirstTime){
                        userEmailData = userEmail;
                    }if(userCurrentPassword != ""){
                        userCurrentPasswordData = userCurrentPassword;
                    }if(userNewPassword != ""){
                        userNewPasswordData = userNewPassword;
                    }
                    const data = {name:userNameData, email:userEmailData, newPassword:userNewPasswordData, oldPassword:userCurrentPasswordData, balance:0, userId:parseInt(localStorage.getItem('userId'))}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    const response = await api.put('/users', data, headers)
                        if(response.status === 200){
                           alert("Perfil atualizado com sucesso!");
                        }
                }catch(err){
                    if(err.response === undefined){
                        alert("Algo deu errado :(");
                    }if(err.response.status === 401){
                        alert("Senha incorreta.");
                     }else{
                        if(err.response.status >= 500){
                            alert("Serviço indisponível.");
                        }
                    }                                            
                }  
        }

        return (
        <div>  
            <Hamburguer/>  
            <div className="div-gif">
                <img className="wallet-gif" src={walletGif} alt="wallet-gif" height="170px" />
                <div className="user-register-description">
                    <h1 className="register-title-gif">Profile</h1>
                    <p className="sub-title">Preencha os campos para adicionar uma transação</p>
                </div>
            </div>
            <Link className="back-link" to="/login">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>

                <label htmlFor="DateTransaction"><h2 className="h2-label">Nome</h2></label>
                <input
                    type="text"                           
                    onChange={e => { setUserName(e.target.value) }}
                    value={userName}
                />

                <div className="div-refreshPass">
                    <label htmlFor="DateTransaction"><h2 className="h2-label">E-mail</h2></label>
                    <input
                        type="text"                           
                        onChange={e => { setUserEmail(e.target.value) }}
                        value={userEmail}
                    />
                    <a className="refresh-password-link" href="#abrirModal">Atualizar senha.</a>
                </div>
               <button className="button-intern" type="button" onClick={refreshPassword}>Salvar</button>

               <div id="abrirModal" class="modal">
                <div>
                    <a href="#fechar" title="Fechar" class="fechar">x</a>
                    <h2 style={{paddingBottom:"3%"}}>Atualizar Senha</h2>
                    <p style={{paddingBottom:"5%"}}>Digite sua senha atual e uma nova para atualizar.</p>  

                    <input
                        style={{width:"100%"}}
                        type="password" 
                        placeholder="Senha atual"                          
                        onChange={e => { setUserCurrentPassword(e.target.value) }}
                        value={userCurrentPassword}
                    />
                    <input
                    style={{width:"100%"}}
                        type="password" 
                        placeholder="Senha nova"                             
                        onChange={e => { setUserNewPassword(e.target.value) }}
                        value={userNewPassword}
                    />

                    <button className="button-intern" type="button" onClick={refreshPassword}>Atualizar</button>
                </div>
            </div> 
       </div>
        )
}