<<<<<<< HEAD
<<<<<<< HEAD
import React from 'react';
=======
import React, { useState } from 'react';
>>>>>>> front: add registerSucces page
=======
import React from 'react';
>>>>>>> front: Remove test env vars
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import fireworks from '../../assets/fireworks.gif'

export default function RegisterSuccess() {
        return (
        <div>
            <div className="div-gif">
                <img src={fireworks} height="200px" alt="finger-gif" />
                <div className="success-div">
                     <h1 className="title-gif">Cadastro realizado com sucesso</h1>
                     <p className="sub-title">Agora você já pode realizar o login.</p>
                </div>     
            </div>
               
            <Link className="back-link" to="/login">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Entrar
            </Link>
       </div>
        )

}