import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import extractGif from '../../assets/extract.gif'
import Hamburguer from '../../components/Hamburguer'
import axios from 'axios';

export default function Extract() {
        const [extract, setExtract] = useState([]);
        const [date, setDate] = useState('');

        function HandleExtract(props){
            useEffect(()=>{
                try {
                    api.get('/transactions', { params: { user_id: localStorage.getItem('userId'), year_month: '2020-10' } }).then(res => {
                        setExtract(res.data);
                    });
                } catch (err) {
                    alert(err);
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }      

        const ExtractComponent = (note) => {
            return (                  
                <div className="timeline">  
                    {extract.map(extract => (                           
                        <div className="container right">
                            <div className="content">
                                <div className="div-date">
                                    <h3>{extract.description}</h3>
                                    <p>{extract.date}</p>
                                </div>
                                <p></p>
                                <p>R${extract.amount}</p>
                            </div>
                        </div>                          
                    ))}
                </div>
            )              
        }

        return ( 
        <div>  
            {HandleExtract()}
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
            
            
            <ExtractComponent/>
       </div>   
        )
}