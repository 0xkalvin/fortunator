import React, {useState, useEffect} from 'react';
import '../../global.css';
import './styles.css'
import Logo from '../../components/Logo'
import finger from '../../assets/finger.gif'
import Hamburguer from '../../components/Hamburguer'
import Chart from '../../components/Chart'
import axios from 'axios'

export default function Home() {  
        const [chartType, setChartType] = useState('Pie');   
        const [categoryNames, setCategoryNames] = useState([]);
        const [categorySpendAmount, setCategorySpendAmount] = useState([]);
        const [date, setDate] = useState('');
        const [years, setYears] = useState([]);
        var spendAmountArray = [];
        var nameArray = [];
        var randomColorsArray = ['rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)'];
        
        function GetAllYears(props){
            useEffect(()=>{ 
            var start = 1900;
            var end = new Date().getFullYear();
            var yearArray = [];

            for(var year = end ; year >=start; year--){
                yearArray.push(year);
            }
            console.log(yearArray);
            setYears(yearArray);  
            }, []) // <-- empty dependency array
            return <div></div>
        }

        GetAllYears()
        
        HandleCategory()

        function getRandomColor(colorQuantities) {
            var letters = '0123456789ABCDEF';      
            for(var j = 0; j<colorQuantities-3; j++){
                var color = '#';
                for (var i = 0; i < 6; i++) {
                    color += letters[Math.floor(Math.random() * 16)];          
                } 
                randomColorsArray.push(color);   
            }        
            return randomColorsArray;
          }

        function HandleCategory(props){
            useEffect(()=>{                
                try {                  
                     axios.get('https://api.github.com/users/KaiqueJuvencio/repos?page=1').then( res => {
                        for(var i = 0; i <= res.data.length-1; i++){
                            spendAmountArray.push(res.data[i].id);
                            nameArray.push(res.data[i].name);
                        }
                        setCategoryNames(nameArray);
                        setCategorySpendAmount(spendAmountArray);
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }               
        
        return (
        <div className="home-container"> 
            <Hamburguer/>
            <div className="div-logo" style={{paddingLeft: "16%"}}>
                <Logo />
                <div className="div-logo-description">
                    <h2>Fortunator</h2>
                    <h3>Controle Financeiro</h3>
                </div>
            </div>
            <div className="body-home">
                <div className="div-gif">
                    <img src={finger} height="55px" alt="finger-gif" />
                    <div>
                        <h1 className="title-gif">Olá</h1>
                        <p className="sub-title">Bem-Vindo ao Fortunator.</p>
                    </div>     
                </div>

                <select description="Transactionn" className="input-maior">                                                  
                    {years.map(categoryHook => (                                                      
                        <option key={categoryHook} value={categoryHook}>{categoryHook}</option>       
                    ))}
                </select>
  

                <Chart chartType='Bar' chartDataLabels={['Janeiro', 'Feveireiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']}  chartDataData={[617594,581045,453060,617594,581045,453060,617594,581045,453060,617594,581045,453060,617594,581045,453060]} chartDataColor={['rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)','rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)','rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)','rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)','rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)']} legendPosition="bottom" textTitle='Gasto Mensal em 2020' />
                <select description="Tipo do Gráfico" id="Transac" className="select-chart-type" onChange={e => {setChartType(e.target.value)}}>
                                <option value="Pie">Pizza</option>
                                <option value="Bar">Barra</option>
                                <option value="Line">Linha</option>
                </select>
                {(function () {
                    if(categorySpendAmount.length !== 0){
                        return(  
                            <div>
                               <input
                                    className="input-date"
                                    id="date"
                                    type="month"
                                    value={date}
                                    onChange={e => { setDate(e.target.value);}}
                                />
                                <Chart chartType={chartType} chartDataLabels={categoryNames}  chartDataData={categorySpendAmount} chartDataColor={getRandomColor(categorySpendAmount.length)} legendPosition="bottom" textTitle='Gasto Mensal Por Categoria' />         
                            </div>
                        )
                    }     
                })()}
            </div>
            </div>
        )

}