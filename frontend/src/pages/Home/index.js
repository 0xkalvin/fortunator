import React, {useState, useEffect} from 'react';
import { AiOutlineEye, AiOutlineEyeInvisible} from 'react-icons/ai';
import '../../global.css';
import './styles.css'
import Logo from '../../components/Logo'
import finger from '../../assets/finger.gif'
import Hamburguer from '../../components/Hamburguer'
import Chart from '../../components/Chart'
import api from '../../service/api';

export default function Home() {  
        var currentMonth = new Date().getMonth() + 1;
        var currentYear = new Date().getFullYear();
        var spendAmountArray = [];
        var categoryNameArray = [];
        var spendAmountByMonthArray = [];
        var userId = localStorage.getItem('userId');
        var randomColorsArray = ['rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)'];
        const [chartType, setChartType] = useState('Pie');   
        const [categoryNames, setCategoryNames] = useState([]);
        const [categorySpendAmount, setCategorySpendAmount] = useState([]);
        const [categorySpendAmountByMonth, setCategorySpendAmountByMonth] = useState([]);
        const [date, setDate] = useState(currentYear.toString() + "-" + currentMonth.toString());
        const [years, setYears] = useState([]);
        const [yearSelected, setYearSelected] = useState(new Date().getFullYear());
        const [eye, setEye] = useState('close');
        const [userBalance, setUserBalance] = useState('');

        GetAllYears()     
        HandleCategoryByYearFirstTime()
        HandleCategoryByMonthFirstTime()
        HandleUser()
        
        function HandleUser(props){    
            useEffect(()=>{          
                try {                  
                    api.get('/users/' + userId).then( res => { 
                        setUserBalance(res.data.balance.amount); 
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }

        function GetAllYears(props){
            useEffect(()=>{ 
            var start = 1900;
            var end = new Date().getFullYear();
            var yearArray = [];
            for(var year = end ; year >=start; year--){
                yearArray.push(year);
            }
            setYears(yearArray);  
            }, []) // <-- empty dependency array
            return <div></div>
        }

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

        function HandleCategoryByYearFirstTime(props){
            useEffect(()=>{                
                try {                  
                    api.get('/financial_movements', { params: { user_id: localStorage.getItem('userId'), year: currentYear} }).then( res => {
                        for(var i = 0; i < res.data.expenses.length; i++){
                            spendAmountByMonthArray.push(res.data.expenses[i].total);    
                        }
                        setCategorySpendAmountByMonth(spendAmountByMonthArray);
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        } 

        function HandleCategoryByYear(props){               
                try {                  
                     api.get('/financial_movements', { params: { user_id: localStorage.getItem('userId'), year: yearSelected} }).then( res => {
                        for(var i = 0; i < res.data.expenses.length; i++){
                            spendAmountByMonthArray.push(res.data.expenses[i].total);    
                        }
                        setCategorySpendAmountByMonth(spendAmountByMonthArray);
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            return <div></div>
        } 

        function HandleCategoryByMonthFirstTime(props){
            useEffect(()=>{                
                try {             
                     api.get('/financial_movements/category', { params: { user_id: localStorage.getItem('userId'), year_month: date } }).then( res => {
                        for(var i = 0; i < res.data.length; i++){
                            categoryNameArray.push(res.data[i].category.name);
                            spendAmountArray.push(res.data[i].movementsPercentage);
                        }
                        setCategoryNames(categoryNameArray);
                        setCategorySpendAmount(spendAmountArray);
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            }, []) // <-- empty dependency array
            return <div></div>
        }

        function HandleCategoryByMonth(props){              
                try {             
                     api.get('/financial_movements/category', { params: { user_id: localStorage.getItem('userId'), year_month: date } }).then( res => {
                        for(var i = 0; i < res.data.length; i++){
                            categoryNameArray.push(res.data[i].category.name);
                            spendAmountArray.push(res.data[i].movementsPercentage);
                        }
                        setCategoryNames(categoryNameArray);
                        setCategorySpendAmount(spendAmountArray);
                    });
                } catch (err) {
                    alert("Algo deu errado :(");
                }
            return <div></div>
        }
        
        function closeEye(){
            setEye("close");
        }

        function openEye(){
            setEye("open");
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
                    <div className="div-gif" style={{paddingBottom: "3%"}}>
                        <img src={finger} height="55px" alt="finger-gif" />
                        <div>
                            <h1 className="title-gif">Olá, {localStorage.getItem('userName')}</h1>
                            <p className="sub-title">Bem-Vindo ao Fortunator.</p>
                        </div>
                         
                        {(function () {
                                if(eye === "open"){
                                    return(
                                        <div className="div-patrimonio">
                                            <h2 style={{paddingLeft: "max(0px, 43%)", fontSize: "25px"}}>Patrimônio: R$ {userBalance}</h2> 
                                            <button type="button" className="invisible-button" style={{paddingLeft:"8%"}} onClick={closeEye}><AiOutlineEye size={22} color="#00a8a0" /></button>   
                                        </div>
                                    )
                                }if(eye === "close"){
                                    return(
                                        <div className="div-patrimonio">
                                            <h2 style={{paddingLeft: "max(0px, 43%)", fontSize: "25px"}}>Patrimônio: R$ ********</h2> 
                                            <button type="button" className="invisible-button" style={{paddingLeft:"8%"}} onClick={openEye}><AiOutlineEyeInvisible size={22} color="#00a8a0" /></button>
                                        </div>
                                    )
                                } 
                        })()}  
                    </div>
                 
                <select description="YearFilter" className="input-maior" style={{width:"10%",  transform: "translateY(80%)"}} onChange={e => {setYearSelected(e.target.value)}}>                                                  
                    {years.map(yearHook => (                                                      
                        <option key={yearHook} value={yearHook}>{yearHook}</option>       
                    ))}
                </select>
                <button className="button-filter" style={{width:"10%",  transform: "translateY(70%)"}} onClick={e => HandleCategoryByYear()}>Filtrar</button>
                <Chart chartType='Bar' chartDataLabels={['Janeiro', 'Feveireiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']}  chartDataData={categorySpendAmountByMonth} chartDataColor={['rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)','rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)','rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)','rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)','rgba(255, 99, 132, 0.6)','rgba(54, 162, 235, 0.6)', 'rgba(255, 206, 86, 0.6)']} textTitle={'Gasto Mensal em ' + yearSelected} />   
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
                                    style={{width: "25%", transform: "translateY(80%)"}}
                                />
                                <button className="button-filter" style={{width:"10%",  transform: "translateY(84%)"}} onClick={e => HandleCategoryByMonth()}>Filtrar</button>
                                <Chart chartType={chartType} chartDataLabels={categoryNames} chartDataData={categorySpendAmount} chartDataColor={getRandomColor(categorySpendAmount.length)} legendPosition="bottom" textTitle='Gasto Mensal Por Categoria' />         
                            </div>
                        )
                    }     
                })()}
            </div>
        </div>
        )

}