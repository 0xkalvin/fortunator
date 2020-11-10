import React, { Component } from 'react'
import {Bar, Line, Pie} from 'react-chartjs-2';

class Chart extends Component{

    static defaultProps = {
        displayTitle:true,
        textTitle: 'No title',
        displayLegend: true,
        legendPosition: 'right',
        chartType: 'Pie'
    }

    render(){
        this.state = {
            chartData:{
                labels: this.props.chartDataLabels,
                datasets:[
                    {
                        label: 'Gastos',
                        data: this.props.chartDataData,
                        backgroundColor: this.props.chartDataColor
                    }
                ]
            }
        }

        if(this.props.chartType === 'Bar'){    
        return(
            <div className="chart">
                <Bar
                    width={150}
                    height={50}
                    data={this.state.chartData}
                    options={{ 
                        title:{
                            display:this.props.displayTitle,
                            text: this.props.textTitle,
                            fontSize: 25,
                        },
                        legend:{
                            display:this.props.displayLegend,
                            position: this.props.legendPosition
                        }
                    }}       
                />
            </div>
        )
        }if(this.props.chartType === 'Pie'){
            return (
                <Pie
                    width={150}
                    height={50}
                    data={this.state.chartData}
                    options={{ 
                        title:{
                            display:this.props.displayTitle,
                            text: this.props.textTitle,
                            fontSize: 25
                        },
                        legend:{
                            display:this.props.displayLegend,
                            position: this.props.legendPosition
                        }
                    }}
                />
            )
        }if(this.props.chartType === 'Line'){
            return (
                <Line
                    width={150}
                    height={50}
                    data={this.state.chartData}
                    options={{ 
                        title:{
                            display:this.props.displayTitle,
                            text: this.props.textTitle,
                            fontSize: 25
                        },
                        legend:{
                            display:this.props.displayLegend,
                            position: this.props.legendPosition
                        }
                    }}
                />
            )
        }
    }
}

export default Chart;