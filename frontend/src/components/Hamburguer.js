import React from 'react';

const Hamburguer=()=> {
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
                    <a href="#"><li>Home</li></a>
                    <a href="#"><li>Informações do Fortunator</li></a>
                    <a href="#"><li>Sobre os devs</li></a>
                    <a href="#"><li>Contato</li></a>
                    <a href="https://erikterwan.com/" target="_blank"><li>Show me more</li></a>
                    </ul>
                </div>
                </nav>
                </div>
    </div>
    )
}

export default Hamburguer;