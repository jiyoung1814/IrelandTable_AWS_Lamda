import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Sensing.css';


function Sensing(){
    const [sensing, setSensing] = useState(0);


    const getSensing = async () => {
        var sensing = [];
    
        
        try {
            setSensing(0);// 요청이 시작 할 때에는 sensing을 초기화
            const response = await axios.get(
                '/2022-09-29/board'
            );
            var str = JSON.stringify(response.data);
            var obj = JSON.parse(str);
           
            console.log("sensing Sensor board:"+str);

            let datetime = obj.datetime.replace('T',' ');
            datetime = datetime.replace('.000Z','')

            console.log(datetime);

            sensing.push(datetime);
            sensing.push(obj.temperature);
            sensing.push(obj.humidity);
            sensing.push(obj.illumination);
            sensing.push(obj.co2);
            sensing.push(obj.gas);

            setSensing(sensing);
          } catch (e) {
        }
    };

        useEffect(() => {
            getSensing();
            setInterval(() => {
                getSensing();
            }, 300000);
        },[]);
        return (
            <div class ="sensing_box">
                <div class="sensing_tem1">
                    <div class= "sensing_temp">
                    <div class="temp_title"><p>Temperature</p></div>
                    <div class="temp_value"><p class="temp">{sensing[1]}℃</p></div>
                    </div>
        
                    <div class="sensing_blank">
                    <div class="sensing_humi">
                        <div class="humi_title"><p>Humidity</p></div>
                        <div class="humi_value"><p class="humi">{sensing[2]}%</p></div>
                    </div>
        
                    </div>
                </div>

                <div class="sensing_tem2">
                    <div class="sensing_illum">
                    <div class="title"><p>Illuminance</p></div>
                    <div class="value"><p class="illum">{sensing[3]}[lux]</p></div>
                    </div>
                    <div class="sensing_co2">
                    <div class="title"><p>Co2</p></div>
                    <div class="value"><p class="co2">{sensing[4]}ppm</p></div>
                    </div>
                    <div class="sensing_gas">
                    <div class="title"><p>Gas</p></div>
                    <div class="value"><p class="gas">{sensing[5]}</p></div>
                    </div>
                </div>

            </div>
            
        );
}


export default Sensing;