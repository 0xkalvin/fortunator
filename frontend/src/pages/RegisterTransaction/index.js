import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import walletGif from '../../assets/wallet.gif'
import Hamburguer from '../../components/Hamburguer'
import { mask, unMask } from 'remask'
import { BsPlusSquare } from 'react-icons/bs';
import axios from 'axios';

export default function RegisterTransaction() {
        const [description, setDescription] = useState('');
        const [date, setDate] = useState('');
        const [amount, setAmount] = useState('');
        const [amountMasked, setAmountMasked] = useState('');
        const [type, setType] = useState('');
        const [category, setCategory] = useState('');
        const [categoryOptions, setCategoryOptions] = useState([]);

        async function trasactionRegister(){
            if(type==="" || type==="Nenhum"){
                alert("Selecione um tipo para a transação. ");
            }if(categoryOptions==="" || categoryOptions==="Nenhum"){
                alert("Selecione uma categoria para a transação. ");
            }else{
                try{
                    const data = {description:description, date:date, amount:amount, type:type, transactionCategory:{id:parseInt(category)}, user:{id:parseInt(localStorage.getItem('userId'))}}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    
                    console.log(data);
                    await api.post('/transactions', data, headers).then(function(response){
                        if(response.status === 200){
                           alert("Transação cadastrada com sucesso!");
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

        function HandleCategory(props){
            useEffect(()=>{
                try {
                    api.get('/transactions/categories/' + localStorage.getItem('userId')).then(res => {
                        setCategoryOptions(res.data);
                    });
                } catch (err) {
                    alert(err);
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }      
        
        const CategoryComponent = (note) => {
            if(categoryOptions.length != 0){
            return (                  
                <select description="Transaction" id="Transac" className="input-maior"  onChange={e => {setCategory(e.target.value)}}>                                                  
                    {categoryOptions.map(category => (                                                      
                        <option value={category.id}>{category.description}</option>       
                    ))}
                </select>
            )
            }else{
                return (                  
                    <select description="Transaction" id="Transac" className="input-maior"  onChange={e => {setCategory(e.target.value)}}>                                                                                                                            
                        <option value="Alimentacao">Alimentação</option>
                        <option value="Educacao">Educação</option>
                        <option value="Lazer">Lazer</option>              
                        <option value="Saude">Saúde</option>              
                    </select>
                )
            } 
            }   

        const onChangeRealMask = ev => {         
            setAmountMasked(mask(ev.target.value, [ "9,99","99,99","999,99","9.999,99","99.999,99", "999.999,99"]));
            setAmount(unMask(amountMasked));
        } 

        return (
        <div>  
            {HandleCategory()}
            <Hamburguer/>  
            <div className="div-gif">
                <img className="wallet-gif" src={walletGif} alt="wallet-gif" height="170px" />
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
           <div className="div-trasaction"> 
                    <div className="div-input-transacao-esquerda">
                        <label for="DateTransaction"><h2 className="h2-label">Valor</h2></label>
                        <input
                            type="text"                           
                            className="input-maior"
                            placeholder="R$ 0,00"
                            onChange={onChangeRealMask}
                            value={amountMasked}
                        />
                    </div>
                    <div className="div-input-transacao-direita">
                        <label for="DateTransaction"><h2 className="h2-label">Data da Transação</h2></label>
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
                   placeholder="Descrição da Transação"
                   value={description}
                   onChange={e => { setDescription(e.target.value) }}
               />
                <div className="div-trasaction">
                    <div className="div-input-transacao-esquerda">
                        <label for="TypeTransaction"><h2 className="h2-label">Categoria</h2></label>
                        <CategoryComponent/>
                        <a href="register-category" className="tooltip" data-title="Criar Categoria"><BsPlusSquare size={22} color="#00A0A0"  /></a>
                    </div>
                    <div className="div-input-transacao-direita">
                        <label for="TypeTransaction"><h2 className="h2-label">Tipo</h2></label>
                        <select description="Transaction" id="Transac" className="input-maior" onChange={e => {setType(e.target.value)}}>
                            <option value="Nenhum">Nenhum</option>
                            <option value="INCOMING">Receita</option>
                            <option value="EXPENSE">Despesa</option>
                        </select>
                    </div>       
                </div>               

               <button className="button-intern" type="button" onClick={trasactionRegister}>Salvar</button>
           </form>
       </div>
        )
}