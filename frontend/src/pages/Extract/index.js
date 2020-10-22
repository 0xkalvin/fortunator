import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import extractGif from '../../assets/extract.gif'
import Hamburguer from '../../components/Hamburguer'

export default function Extract() {
        const [extract, setExtract] = useState([]);
        const [date, setDate] = useState('');

        function HandleExtractFirstTime(props){
            useEffect(()=>{
                try {
                    var today = new Date();
                    var currentDate;
                    let currentMonth = today.getMonth()+1;
                    let currentYear = today.getFullYear();

                    currentDate = currentYear + '-' + currentMonth;
   
                    api.get('/transactions', { params: { user_id: localStorage.getItem('userId'), year_month: currentDate} }).then(res => {
                        setExtract(res.data);                        
                    });
                } catch (err) {
                    alert(err);
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }   
        
        function HandleExtract(props){        
                try {
                    api.get('/transactions', { params: { user_id: localStorage.getItem('userId'), year_month: date } }).then(res => {
                        setExtract(res.data);
                    });
                } catch (err) {
                    alert(err);
                }
        }  

        const ExtractComponent = (note) => {
            return (                  
                <div className="timeline">                 
                    {extract.map(extract => { 
                        if(extract.type === "EXPENSE"){
                            return(
                            <div className="container-expense right">
                                <div className="content">
                                    <div className="div-date">
                                        <h3>{extract.description}</h3>
                                        <p>{extract.transactionCategory.description}</p>
                                        <p>{extract.date}</p>
                                    </div>              
                                    <p className="extract-amount-expense">- R$ {extract.amount}</p>
                                </div>
                            </div>     
                            )
                        }else{
                            return(
                            <div className="container-incoming left">
                                <div className="content">
                                    <div className="div-date">
                                        <h3>{extract.description}</h3>
                                        <p>{extract.transactionCategory.description}</p>
                                        <p>{extract.date}</p>
                                    </div>      
                                    <p className="extract-amount-incoming">+ R$ {extract.amount}</p>
                                </div>
                            </div>     
                        )}                                                                             
                    })}                           
                </div>
            )              
        }

        return ( 
        <div>  
            {HandleExtractFirstTime()}
            <Hamburguer/>  
            <div className="div-gif">
                <img className="extract-gif" src={extractGif} alt="wallet-gif" height="170px" />
                <div className="user-register-description">
                    <h1 className="register-title-gif">Extrato</h1>
                    <p className="sub-title">Lista dos seus gastos mais recentes</p>
                </div>
            </div>
            <Link className="back-link" to="/home">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>
            <input
                className="input-date"
                id="date"
                type="month"
                value={date}
                onChange={e => { setDate(e.target.value);}}
            />
            <button className="button-filter" onClick={e => HandleExtract()}>Filtrar</button>
            {(function () {
                if(extract.length > 0){
                    return(<ExtractComponent/>)
                }else{
                    return <div><h2>Nenhuma transação nesse mês</h2></div>
                }       
            })()}
       </div>   
        )
}