import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import goalGif from '../../assets/goal.gif'
import Hamburguer from '../../components/Hamburguer'
import ProgressBar from 'react-bootstrap/ProgressBar'
import { BsPlusSquare } from 'react-icons/bs';
import { Button, Progress } from 'semantic-ui-react'

export default function Goals() {
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
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }   
        
        async function HandleExtract(props){        
                try {
                    const res = await api.get('/transactions', { params: { user_id: localStorage.getItem('userId'), year_month: date } })
                        setExtract(res.data);     
                } catch (err) {
                    alert("Algo deu errado :(");
                }
        }  

        const ExtractComponent = (note) => {
            return (                  
                <div className="timeline">                 
                    {extract.map(extract => { 
                            return(
                            <div className="container-incoming-goal left"  key={extract.id}>
                                <div className="content">
                                    <div className="div-date">
                                        <h3>{extract.description}</h3>
                                        <p>{extract.transactionCategory.description}</p>
                                        <p>{extract.date}</p>
                                    </div>      
                                    <p className="extract-amount-incoming">+ R$ {extract.amount}</p>
                                </div>
                            </div>   
                            )                                                                               
                    })}                           
                </div>
            )              
        }
        const now = 60;

        const progressInstance = <ProgressBar now={now} label={`${now}%`} />;

        return ( 
        <div>  
            {HandleExtractFirstTime()}
            <Hamburguer/>  
            <div className="div-gif">
                <img className="extract-gif" src={goalGif} alt="wallet-gif" height="170px" />
                <div className="user-register-description">
                    <h1 className="register-title-gif">Metas</h1>
                    <p className="sub-title">Lista de metas</p>
                </div>
            </div>
            <Link className="back-link" to="/home">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>
            <Link to="/register-goal" className="tooltip" data-title="Nova meta" style={{marginLeft:"43.5%"}}><BsPlusSquare size={30} color="#00A0A0"  /></Link>
            {(function () {
                if(extract.length > 0){
                    return(<ExtractComponent/>)
                }else{
                    return <div><h2>Nenhuma meta cadastrada</h2></div>
                }       
            })()}
       </div>   
        )
}