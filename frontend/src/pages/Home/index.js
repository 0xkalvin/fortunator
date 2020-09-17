import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { AiOutlineEye, AiOutlineEyeInvisible} from 'react-icons/ai';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import Logo from '../../components/Logo'
import finger from '../../assets/finger.gif'


export default function Home() {
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');
        const [eye, setEye] = useState('close');

        async function userLogin(){
                try{
                    const data = {email: email, password: password}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    await api.post('/login', data, headers)
                }catch(err){
                    alert(err);
                }  
        }
        
        function closeEye(){
            setEye("close");
            document.getElementById("senha").type = "password";
        }
        function openEye(){
            setEye("open");
            document.getElementById("senha").type = "text";
        }

        return (
        <div>
             <nav role="navigation" className="navigation">
            <div id="menuToggle">

                <input type="checkbox" />
                <span></span>
                <span></span>
                <span></span> 

                <ul id="menu">
                <a href="#"><li>Home</li></a>
                <a href="#"><li>About</li></a>
                <a href="#"><li>Info</li></a>
                <a href="#"><li>Contact</li></a>
                <a href="https://erikterwan.com/" target="_blank"><li>Show me more</li></a>
                </ul>
            </div>
            </nav>

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
                     <h1 className="title-gif">Home</h1>
                     <p className="sub-title">Bem-Vindo.</p>
                </div>     
            </div>

           
           
            </div>
        )

}