import React, { useState } from 'react';
import '../../global.css';
import './styles.css'
import Logo from '../../components/Logo'
import finger from '../../assets/finger.gif'
import Hamburguer from '../../components/Hamburguer'

export default function Home() {
    
        return (
        <div>
             <Hamburguer/>
            <div className="div-logo">
                <Logo />
                <div className="div-logo-description">
                    <h2>Fortunator</h2>
                    <h3>Controle Financeiro</h3>
                </div>
            </div>
            <div className="div-gif">
                <img src={finger} height="55px" alt="finger-gif" />
                <div>
                     <h1 className="title-gif">Olá, Kaique</h1>
                     <p className="sub-title">Bem-Vindo ao Fortunator.</p>
                </div>     
            </div>
            </div>
        )

}