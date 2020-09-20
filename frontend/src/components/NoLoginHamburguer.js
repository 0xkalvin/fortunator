import React, { useState } from 'react';
import { Redirect } from 'react-router'
import { AiOutlinePoweroff, AiOutlineInfoCircle } from 'react-icons/ai';
import { BsHouseDoor, BsAlarm } from 'react-icons/bs';
import { IoMdPaper } from 'react-icons/io';
import { MdAttachMoney } from 'react-icons/md';
import { FaTasks } from 'react-icons/fa';
import { FiPhone } from 'react-icons/fi';

const NoLoginHamburguer=()=> {
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
                    <a href="#"><li><MdAttachMoney size={22} color="#f0f0f5" /> Fortunator</li></a>
                    <a href="#"><li><AiOutlineInfoCircle size={22} color="#f0f0f5" /> Sobre os devs</li></a>
                    <a href="#"><li><FiPhone size={22} color="#f0f0f5" /> Contato</li></a>
                    </ul>
                </div>
                </nav>
                </div>
    </div>
    )
}

export default NoLoginHamburguer;