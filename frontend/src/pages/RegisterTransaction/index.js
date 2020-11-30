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
import { FiTrash2 } from 'react-icons/fi';

export default function RegisterTransaction() {
        const [description, setDescription] = useState('');
        const [date, setDate] = useState('');
        const [amount, setAmount] = useState('');
        const [amountMasked, setAmountMasked] = useState('');
        const [type, setType] = useState('INCOMING');
        const [category, setCategory] = useState('1');
        const [categoryOptions, setCategoryOptions] = useState([])
        const [categoryOptionsCreated, setCategoryOptionsCreated] = useState([]); //Only categories created by the user

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

        function HandleCategory(props){
            useEffect(()=>{
                try {
                    api.get('/transactions/categories/' + localStorage.getItem('userId')).then(response => {
                        setCategoryOptions(response.data);
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }   
        
        function HandleCategoryCreated(props){
            useEffect(()=>{
                try {
                    api.get('/transactions/categories/created/' + localStorage.getItem('userId')).then(response => {
                        setCategoryOptionsCreated(response.data);
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }  

        const onChangeRealMask = ev => {         
            setAmountMasked(mask(ev.target.value, [ "9,99","99,99","999,99","9.999,99","99.999,99", "999.999,99"]));
            setAmount(unMask(amountMasked));
        } 

        async function deleteCategory(){
            if(category===""){
                alert("Selecione uma categoria. ");
            }else{
                try{
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    const response = await api.delete('/transactions/categories/' + category, headers)
                        if(response.status === 200){
                           alert("Categoria excluída com sucesso!");
                           window.location.reload(false);
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
            <div id="abrirModal" class="modal">
                <div>
                    <a href="#fechar" title="Fechar" class="fechar">x</a>
                    <h2 style={{paddingBottom:"3%"}}>Exclusão de categoria</h2>
                    <p style={{paddingBottom:"3%"}}>Escolha a categoria que deseja excluir.</p> 

                    {(function () {
                            if(categoryOptionsCreated.length !== 0){
                                return(
                                <select description="Transactionn" className="input-maior" onChange={e => {setCategory(e.target.value)}}>                                                  
                                    {categoryOptionsCreated.map(categoryOptionsCreated => (                                                      
                                        <option key={categoryOptionsCreated.id} value={categoryOptionsCreated.id}>{categoryOptionsCreated.description}</option>       
                                    ))}
                                </select>
                                )
                            }else{
                                return(
                                <select description="Transactionn" className="input-maior">                                                                                                       
                                        <option>Nenhuma Opção Disponível</option>       
                                </select>
                                )
                            }
                        })()}
                    <button className="button-intern" type="button" style={{marginLeft:"23%"}} onClick={deleteCategory}>Excluir</button>
                </div>
            </div>   

            {HandleCategory()}
            {HandleCategoryCreated()}
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
           <div className="div-trasaction"> 
                    <div className="div-input-transacao-esquerda">
                        <label htmlFor="DateTransaction"><h2 className="h2-label">Valor</h2></label>
                        <input
                            type="text"                           
                            className="input-maior"
                            placeholder="R$ 0,00"
                            onChange={onChangeRealMask}
                            value={amountMasked}
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
                   placeholder="Descrição da Transação"
                   value={description}
                   onChange={e => { setDescription(e.target.value) }}
               />
                <div className="div-trasaction">
                    <div className="div-input-transacao-esquerda">
                        <label htmlFor="CategoryTransaction"><h2 className="h2-label">Categoria</h2></label>
                        {(function () {
                            if(categoryOptions.length !== 0){
                                return(
                                <select description="Transactionn" className="input-maior" onChange={e => {setCategory(e.target.value)}}>                                                  
                                    {categoryOptions.map(categoryOption => (                                                      
                                        <option key={categoryOption.id} value={categoryOption.id}>{categoryOption.description}</option>       
                                    ))}
                                </select>
                                )
                            }else{
                                return(
                                <select description="Transactionn" className="input-maior">                                                                                                       
                                        <option>Nenhuma Opção Disponível</option>       
                                </select>
                                )
                            }
                        })()}
                        <Link to="/register-category" className="tooltip" data-title="Criar Categoria"><BsPlusSquare size={22} color="#00A0A0"  /></Link>
                        <a href="#abrirModal" className="tooltip" data-title="Excluir Categoria"><FiTrash2 size={22} color="#00A0A0"  /></a>
                        
                    </div>
                    <div className="div-input-transacao-direita">
                        <label htmlFor="TypeTransaction"><h2 className="h2-label">Tipo</h2></label>
                        <select description="Transaction" id="Transac" className="input-maior" onChange={e => {setType(e.target.value)}}>
                            <option value="INCOMING">Receita</option>
                            <option value="EXPENSE">Despesa</option>
                        </select>
                    </div>       
                </div>               

               <button className="button-intern" type="button" onClick={trasactionRegister}>Salvar</button>
       </div>
        )
}