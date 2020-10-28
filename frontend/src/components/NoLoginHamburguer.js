import React from 'react';
import { AiOutlineInfoCircle } from 'react-icons/ai';
import { MdAttachMoney } from 'react-icons/md';
import { FiPhone } from 'react-icons/fi';
import { Link } from 'react-router-dom';

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
                            <Link to="#"><li><MdAttachMoney size={22} color="#f0f0f5" /> Fortunator</li></Link>
                            <Link to="#"><li><AiOutlineInfoCircle size={22} color="#f0f0f5" /> Sobre os devs</li></Link>
                            <Link to="#"><li><FiPhone size={22} color="#f0f0f5" /> Contato</li></Link>
                        </ul>
                    </div>
                </nav>
            </div>
    </div>
    )
}

export default NoLoginHamburguer;