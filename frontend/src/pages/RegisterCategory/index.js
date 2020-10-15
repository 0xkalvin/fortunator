import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import walletGif from '../../assets/wallet.gif'
import Hamburguer from '../../components/Hamburguer'

export default function RegisterCategory() {
        const [title, setTitle] = useState('');

        async function categoryRegister(){
            try{
                const data = {title:title}
                const headers = {
                    "Content-Type": "application/json"
                }                    
                console.log(data);
                await api.post('/register-transaction', data, headers).then(function(response){
                    if(response.status === 200){ 
                        alert("Categoria cadastrada com sucesso!")                          
                    }
                })
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

        return (
        <div>    
            <Hamburguer/>  
            <div className="div-gif">
                <img className="wallet-gif" src={walletGif} alt="wallet-gif" height="170px" />
                <div className="user-register-description">
                    <h1 className="register-title-gif">Nova categoria</h1>
                    <p className="sub-title">Preencha os campos para adicionar uma nova categoria</p>
                </div>
            </div>
            <Link className="back-link" to="/register-transaction">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>
           <form>
               <input
                   placeholder="Título da Categoria"
                   value={title}
                   onChange={e => { setTitle(e.target.value) }}
               />          
               <button className="button-intern" type="button" onClick={categoryRegister}>Salvar</button>
           </form>
       </div>
        )
}