import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router'
import { AiOutlinePoweroff, AiOutlineInfoCircle } from 'react-icons/ai';
import { BsHouseDoor, BsAlarm } from 'react-icons/bs';
import { IoMdPaper } from 'react-icons/io';
import { RiCoinsLine } from 'react-icons/ri';
import { FaTasks, FaUserCircle } from 'react-icons/fa';
import { FiPhone } from 'react-icons/fi';
import { Link } from 'react-router-dom';
import api from '../service/api';

const Hamburguer=()=> {
    const [userLevel, setUserLevel] = useState('');
    const [userScore, setUserScore] = useState('');
    var userId = localStorage.getItem('userId');
    const [goLogout, setGoLogout] = useState(false);

    HandleUser()

    function logout(){
        localStorage.setItem('auth','false');
        setGoLogout(true);
    }

    function HandleUser(props){    
        useEffect(()=>{          
            try {                  
                api.get('/users/' + userId).then( res => {  
                    setUserLevel(res.data.level);
                    setUserScore(res.data.score);
                });
            } catch (err) {
                alert("Algo deu errado :(");
            }
        }, []) // <-- empty dependency array
        return <div></div>
    }
        return (         
        <div>
            {(function () {
                    if(goLogout === true){ return <Redirect to="/login"/> }
            })()}
            <div>
                <nav role="navigation" className="navigation">
                    <div id="menuToggle">

                        <input type="checkbox" />
                        <span></span>
                        <span></span>
                        <span></span> 
                        
                        <ul id="menu">
                            <li style={{color: "#FFE500"}}><FaUserCircle size={30} color="#FFE500" /><b style={{paddingLeft: "4%", fontSize: "28px"}}>{localStorage.getItem('userName')}</b><p style={{fontSize: "20px"}}>{userLevel}<br/>Score: {userScore}</p></li>
                            <Link to="/home"><li><BsHouseDoor size={22} color="#f0f0f5" /> Home</li></Link>
                            <Link to="/register-transaction"><li><RiCoinsLine size={22} color="#f0f0f5" /> Transações</li></Link>
                            <Link to="/extract"><li><IoMdPaper size={22} color="#f0f0f5" /> Extrato</li></Link>
                            <Link to="#"><li><BsAlarm size={22} color="#f0f0f5" /> Lembretes</li></Link>
                            <Link to="#"><li><FaTasks size={22} color="#f0f0f5" /> Metas</li></Link>
                            <Link to="#"><li><AiOutlineInfoCircle size={22} color="#f0f0f5" /> Sobre os devs</li></Link>
                            <Link to="#"><li><FiPhone size={22} color="#f0f0f5" /> Contato</li></Link>
                            <Link to="#" onClick={e => { logout()}}><li><AiOutlinePoweroff size={22} color="#f0f0f5" /> Sair</li></Link>
                            {/*<Link to="#" className="tooltip" data-title="Logout"><button type="button" className="invisible-button" onClick={e => { logout()}}><AiOutlinePoweroff size={22} color="#f0f0f5" /></button></Link>*/}
                        </ul>
                    </div>
                </nav>
            </div>
    </div>
    )
}

export default Hamburguer;