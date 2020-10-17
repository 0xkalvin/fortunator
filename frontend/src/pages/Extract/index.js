import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import walletGif from '../../assets/wallet.gif'
import Hamburguer from '../../components/Hamburguer'
import { mask, unMask } from 'remask'
import { BsPlusSquare } from 'react-icons/bs';
import { Timeline } from 'rsuite';

export default function Extract() {
        const [description, setDescription] = useState('');
        const [date, setDate] = useState('');
        const [amount, setAmount] = useState('');
        const [amountMasked, setAmountMasked] = useState('');
        const [type, setType] = useState('');
        const [category, setCategory] = useState('');

        async function trasactionRegister(){
            if(type==="" || type==="Nenhum"){
                alert("Selecione um tipo para a transação. ");
            }else{
                try{
                    const data = {description:description, date:date, amount:amount, type:type, category:category}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    
                    console.log(data);
                    await api.post('/register-transaction', data, headers).then(function(response){
                        if(response.status === 200){
                           
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
        }

        return (
        <div>    
            <Hamburguer/>  
            <div className="div-gif">
                <img className="wallet-gif" src={walletGif} alt="wallet-gif" height="170px" />
                <div className="user-register-description">
                    <h1 className="register-title-gif">Extrato</h1>
                    <p className="sub-title">Preencha os campos para adicionar uma transação</p>
                </div>
            </div>
            <Link className="back-link" to="/login">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>
            <div class="timeline">
            <div class="container right">
                <div class="content">
                <div className="div-date">
                    <h3>Mercado</h3>
                    <p>18/10/2020</p>
                </div>
                <p>Compra do mês</p>
                <p>R$ 700,00</p>
                </div>
            </div>
            <div class="container right">
                <div class="content">
                <div className="div-date">
                    <h3>Saúde</h3>
                    <p>16/10/2020</p>
                </div>
                <p>Whey</p>
                <p>R$ 120,00</p>
                </div>
            </div>
            </div>
       </div>
        )
}