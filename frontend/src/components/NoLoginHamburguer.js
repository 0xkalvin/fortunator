import React from 'react';
import { AiOutlineInfoCircle } from 'react-icons/ai';
import { MdAttachMoney } from 'react-icons/md';
import { FiPhone } from 'react-icons/fi';

const NoLoginHamburguer=()=> {
        return (         
        <div>
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