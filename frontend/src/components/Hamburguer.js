import React, { useState } from 'react';
import { Redirect } from 'react-router'
import { AiOutlinePoweroff, AiOutlineInfoCircle } from 'react-icons/ai';
import { BsHouseDoor, BsAlarm } from 'react-icons/bs';
import { IoMdPaper } from 'react-icons/io';
import { RiCoinsLine } from 'react-icons/ri';
import { FaTasks } from 'react-icons/fa';
import { FiPhone } from 'react-icons/fi';
import { Link } from 'react-router-dom';

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
                            <Link to="#" className="tooltip" data-title="Logout"><button type="button" className="invisible-button" onClick={e => { logout()}}><AiOutlinePoweroff size={22} color="#f0f0f5" /></button></Link>
                            <Link to="/home"><li><BsHouseDoor size={22} color="#f0f0f5" /> Home</li></Link>
                            <Link to="/register-transaction"><li><RiCoinsLine size={22} color="#f0f0f5" /> Transações</li></Link>
                            <Link to="/extract"><li><IoMdPaper size={22} color="#f0f0f5" /> Extrato</li></Link>
                            <Link to="#"><li><BsAlarm size={22} color="#f0f0f5" /> Lembretes</li></Link>
                            <Link to="#"><li><FaTasks size={22} color="#f0f0f5" /> Metas</li></Link>
                            <Link to="#"><li><AiOutlineInfoCircle size={22} color="#f0f0f5" /> Sobre os devs</li></Link>
                            <Link to="#"><li><FiPhone size={22} color="#f0f0f5" /> Contato</li></Link>
                        </ul>
                    </div>
                </nav>
            </div>
    </div>
    )
}

export default Hamburguer;