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
        const [userCurrentPassword, setUserCurrentPassword] = useState('');
        const [userNewPassword, setUserNewPassword] = useState('');

        HandleUser()

        async function trasactionRegister(){
            if(type==="" || type==="Nenhum"){
                alert("Selecione um tipo para a transação. ");
            }if(categoryOptions==="" || categoryOptions==="Nenhum"){
                alert("Selecione uma categoria para a transação. ");
            }if(date==="" || date==="Nenhum"){
                alert("Selecione a data da transação. ");
            }if(amount===""){
                alert("Insira o valor da transação. ");
            }if(description===""){
                alert("Insira o título da transação. ");
            }else{
                try{
                    const data = {description:description, date:date, amount:amount, type:type, transactionCategory:{id:parseInt(category)}, user:{id:parseInt(localStorage.getItem('userId'))}}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    const response = await api.post('/transactions', data, headers)
                        if(response.status === 201){
                           alert("Transação cadastrada com sucesso!");
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
    
        function HandleUser(props){    
            useEffect(()=>{          
                try {                  
                    api.get('/users/' + userId).then( res => { 
                        setUserName(res.data.name);
                        setUserEmail(res.data.email);
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }

        async function refreshPassword(){
            if(amount===""){
                alert("Insira o valor da meta. ");
            }else{
                try{
                    const data = {amount:amount, user:{id:parseInt(localStorage.getItem('userId'))}}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    const response = await api.post('/goals', data, headers)
                        if(response.status === 201){
                           alert("Transação cadastrada com sucesso!");
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
               <button className="button-intern" type="button" onClick={trasactionRegister}>Salvar</button>

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