import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import goalGif from '../../assets/goal.gif'
import Hamburguer from '../../components/Hamburguer'
import { mask, unMask } from 'remask'
import { BsPlusSquare } from 'react-icons/bs';

export default function RegisterGoal() {
        const [description, setDescription] = useState('');
        const [date, setDate] = useState('');
        const [amount, setAmount] = useState("");
        const [amountMasked, setAmountMasked] = useState('');
        const [type, setType] = useState('BUDGET');

        async function goalRegister(){
            if(type==="" || type==="Nenhum"){
                alert("Selecione um tipo para a meta. ");
            }if(date==="" || date==="Nenhum"){
                alert("Selecione a data da meta. ");
            }if(amount===""){
                alert("Insira o valor da meta. ");
            }if(description===""){
                alert("Insira o título da meta. ");
            }else{
                try{
                    const numberPatterns = [
                        "9.99",
                        "99.99",
                        "999.99",
                        "9,999.99",
                        "99,999.99",
                        "999,999.99",
                      ];
              
                    const amountAsNumber = parseInt(mask(unMask(amount), numberPatterns));
                    const data = {description:description, date:date, amount:amountAsNumber, type:type, user:{id:parseInt(localStorage.getItem('userId'))}}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    const response = await api.post('/goals', data, headers)
                        if(response.status === 201){
                           alert("Meta cadastrada com sucesso!");
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
  
        const onChangeRealMask = (ev) => {
            const amountValue = unMask(ev.target.value);
        
            const brazilianPatterns = [
              "9,99",
              "99,99",
              "999,99",
              "9.999,99",
              "99.999,99",
              "999.999,99",
            ];
        
            const maskedAmount = mask(amountValue, brazilianPatterns);
        
            setAmount(maskedAmount);
          };

        return (
        <div>  
            <Hamburguer/>  
            <div className="div-gif">
                <img className="goal-gif" src={goalGif} alt="goal-gif" height="170px" />
                <div className="user-register-description">
                    <h1 className="register-title-gif">Cadastro de Meta</h1>
                    <p className="sub-title">Preencha os campos para adicionar uma meta.</p>
                </div>
            </div>
            <Link className="back-link" to="/goals">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>
           <div className="div-trasaction"> 
                    <div className="div-input-transacao-esquerda">
                        <label htmlFor="DateTransaction"><h2 className="h2-label">Valor</h2></label>
                        <input
                            type="text"                           
                            className="input-maior"
                            placeholder="R$ 0,00"
                            onChange={onChangeRealMask}
                            value={amount}
                        />
                    </div>
                    <div className="div-input-transacao-direita">
                        <label htmlFor="DateTransaction"><h2 className="h2-label">Data</h2></label>
                        <input
                            className="input-maior"
                            id="date"
                            type="date"
                            value={date}
                            onChange={e => { setDate(e.target.value) }}
                        />
                    </div>   
                </div>

               <input
                   placeholder="Descrição da Meta"
                   value={description}
                   onChange={e => { setDescription(e.target.value) }}
               />
                             
               <button className="button-intern" type="button" onClick={goalRegister}>Salvar</button>
       </div>
        )
}