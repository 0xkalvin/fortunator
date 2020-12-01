import React, { useState, useEffect } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';
import '../../global.css';
import './styles.css'
import api from '../../service/api';
import goalGif from '../../assets/goal.gif'
import Hamburguer from '../../components/Hamburguer'
import ProgressBar from 'react-bootstrap/ProgressBar'
import { BsPlusSquare } from 'react-icons/bs';
import { mask, unMask } from 'remask'
import { Button, Progress } from 'semantic-ui-react'

export default function Goals() {
        const [goal, setGoal] = useState([]);
        const [increaseAmount, setIncreaseAmount] = useState('');
        const [goalName, setGoalName] = useState('');
        const [goalId, setGoalId] = useState('');
        const [amountMasked, setAmountMasked] = useState('');
        const [amount, setAmount] = useState('');
        const [goalProgressAmount, setGoalProgressAmount] = useState('');
        const [goalProgressPercentage, setGoalProgressPercentage] = useState('');
        const [goalCurrentAmount, setGoalCurrentAmount] = useState('');

        function HandleExtractFirstTime(props){
            useEffect(()=>{
                try {
                    api.get('/goals', { params: { user_id: localStorage.getItem('userId')} }).then(res => {
                        setGoal(res.data);                        
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }   
        
        const ExtractComponent = (note) => {
            return (                  
                <div className="timeline">                 
                    {goal.map(goal => { 
                            return(
                            <div className="container-incoming-goal left"  key={goal.id}>
                                <div className="content">
                                    <div className="div-date">
                                        <h3>{goal.description}</h3>
                                        <p>{goal.status}</p>
                                        <p><b>META:</b> R$ {goal.amount}</p>                                                       
                                    </div>      
                                    <p style={{paddingBottom:"3%"}}><b>Progresso:</b> R$ {goal.progressAmount} ( {goal.progressPercentage}% )</p>
                                    <p style={{color:"rgb(0, 187, 31)", paddingBottom:"3%"}}>+ {goal.score} xp</p>
                                    <button className="button-intern-add-progress"  onClick={e=>setParameters(`${goal.description}`,`${goal.amount}`,`${goal.id}`,`${goal.progressAmount}`, `${goal.progressPercentage}`)}>Progresso</button>
                                </div>            
                            </div>   
                        )                                                                               
                    })}                                       
                </div>
            )              
        }

        function setParameters(goalNameParam, goalCurrentAmountParam, goalIdParam, goalProgressAmountParam, goalProgressPercentageParam) {
            window.location.href = "#abrirModal"
            setGoalName(goalNameParam);
            setGoalCurrentAmount(goalCurrentAmountParam);
            setGoalId(goalIdParam);
            setGoalProgressAmount(goalProgressAmountParam);
            setGoalProgressPercentage(goalProgressPercentageParam)
        }
        
        const onChangeRealMask = (ev) => {
            const amountValue = unMask(ev.target.value);
        
            const brazilianPatterns = [
              "9,99",
              "99,99",
              "999,99",
              "9.999,99",
              "99.999,99",
              "999.999,99",
            ];
        
            const maskedAmount = mask(amountValue, brazilianPatterns);
        
            setAmount(maskedAmount);
          };

        async function refreshGoalAmount(){
            if(amount===""){
                alert("Insira o valor para adicionar progresso. ");
            }else{
                try{
                    const numberPatterns = [
                        "9.99",
                        "99.99",
                        "999.99",
                        "9,999.99",
                        "99,999.99",
                        "999,999.99",
                      ];
              
                    const amountAsNumber = mask(unMask(amount), numberPatterns);
                    const data = {goalId:goalId, progressAmount:amountAsNumber}
                    const headers = {
                        "Content-Type": "application/json"
                    }
                    console.log(amount);
                    const response = await api.put('/goals', data, headers)
                        if(response.status === 200){
                           alert("Progresso adicionado com sucesso!");
                           window.location.href = "#fechar"
                           window.location.reload(false);
                        }
                }catch(err){
                    if(err.response === undefined){
                        alert("Algo deu errado :(");
                    }else{
                        if(err.response.status >= 500){
                            alert("Serviço indisponível.");
                        }
                    }                                            
                }  
            }
        }

        return ( 
        <div>  
            <div id="abrirModal" class="modal">
                <div>
                    <a href="#fechar" title="Fechar" class="fechar">x</a>
                    <h2 style={{paddingBottom:"3%"}}>{goalName}</h2>
                    <p style={{paddingBottom:"3%"}}>Digite o valor que você juntou para adicionar progresso.</p> 
                    <p style={{paddingBottom:"3%"}}><b>Meta:</b> R$ {goalCurrentAmount}</p>  
                    <p style={{paddingBottom:"3%"}}><b>Progresso:</b> R$ {goalProgressAmount} ( {goalProgressPercentage}% )</p>  

                    <input
                        type="text"                           
                        className="input-maior"
                        placeholder="R$ 0,00"
                        onChange={onChangeRealMask}
                        value={amount}
                    />
                    <button className="button-intern" type="button" style={{marginLeft:"23%"}} onClick={refreshGoalAmount}>Salvar</button>
                </div>
            </div>   
            
            {HandleExtractFirstTime()}
            <Hamburguer/>  
            <div className="div-gif">
                <img className="extract-gif" src={goalGif} alt="wallet-gif" height="170px" />
                <div className="goal-description">
                    <h1 className="register-title-gif">Metas</h1>
                    <p className="sub-title">Lista de metas</p>
                </div>
            </div>
            <Link className="back-link" to="/home">
                    <FiArrowLeft size={22} color="#00a8a0" />
                    Voltar
            </Link>
            <Link to="/register-goal" className="tooltip" data-title="Nova meta" style={{marginLeft:"43.7%"}}><BsPlusSquare size={30} color="#00A0A0"  /></Link>
            {(function () {
                if(goal.length > 0){
                    return(<ExtractComponent/>)
                }else{
                    return <div><h2>Nenhuma meta cadastrada</h2></div>
                }       
            })()}
       </div>   
        )
}