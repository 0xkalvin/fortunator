import React, { useState } from 'react';
import '../../global.css';
import api from '../../service/api';

export default function UserRegister() {
        const [name, setName] = useState('');
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');

        async function userRegister(){
            try{
                await api.post('/users')
            }catch(err){
                alert(err);
            }      
        }

        return (
        <div>
           <h1>Cadastro de Usuário</h1>
           <p>Preencha os campos para se cadastrar.</p>

           <form onSubmit>
               <input
                   placeholder="Nome"
                   value={name}
                   onChange={e => { setName(e.target.value) }}
               />
               <input
                   placeholder="E-mail"
                   value={email}
                   onChange={e => { setEmail(e.target.value) }}
               />
               <input
                   placeholder="Senha"
                   value={password}
                   onChange={e => { setPassword(e.target.value) }}
               />
               <button className="button-intern" type="submit">Enviar</button>
           </form>
       </div>
        )

}