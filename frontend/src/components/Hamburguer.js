import React, { useState } from 'react';
import { Redirect } from 'react-router'
import { AiOutlinePoweroff, AiOutlineInfoCircle } from 'react-icons/ai';
import { BsHouseDoor, BsAlarm } from 'react-icons/bs';
import { IoMdPaper } from 'react-icons/io';
import { RiCoinsLine } from 'react-icons/ri';
import { FaTasks } from 'react-icons/fa';
import { FiPhone } from 'react-icons/fi';

const Hamburguer=()=> {
    const [goLogout, setGoLogout] = useState(false);

    function logout(){
        localStorage.setItem('auth','false');
        setGoLogout(true);
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
                    <a href="#" class="tooltip" data-title="Logout"><button type="button" className="invisible-button" onClick={e => { logout()}}><AiOutlinePoweroff size={22} color="#f0f0f5" /></button></a>
                    <a href="#"><li><BsHouseDoor size={22} color="#f0f0f5" /> Home</li></a>
                    <a href="#"><li><RiCoinsLine size={22} color="#f0f0f5" /> Transações</li></a>
                    <a href="#"><li><IoMdPaper size={22} color="#f0f0f5" /> Extrato</li></a>
                    <a href="#"><li><BsAlarm size={22} color="#f0f0f5" /> Lembretes</li></a>
                    <a href="#"><li><FaTasks size={22} color="#f0f0f5" /> Metas</li></a>
                    <a href="#"><li><AiOutlineInfoCircle size={22} color="#f0f0f5" /> Sobre os devs</li></a>
                    <a href="#"><li><FiPhone size={22} color="#f0f0f5" /> Contato</li></a>
                    </ul>
                </div>
                </nav>
                </div>
    </div>
    )
}

export default Hamburguer;