import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Control.css';
var target, btn, color, tooltip;
var percentPosition;
var control_value = [0,0,0];
var control_state = [false,false,false];
let targetRect;
let filter = false;


function Control(){
    const [control, setControl] = useState(0);
    const [control2, setControl2] = useState(false);
    
    

    const StateControl = async (e) =>{
        let sensing_target = '';
        let control_state_send = [];

        let control_led_id = ['switch-1','switch-2','switch-3'];
        let control_led_name = ['red_state','blue_state','fan_state'];

        for(let i=0;i<3;i++){
            if(e.target.id === control_led_id[i]){
                if(control_state[i]){control_state[i] = false;}
                else{control_state[i]= true;}

                sensing_target = control_led_name[i].split('_')[0]+'/'+control_state[i];
            }
            if(control_state[i]){
                control_state_send[i] = 1;
            }
            else{
                control_state_send[i] = 0;
            }
        }
        
        console.log('sensing_target: ' + sensing_target);
        console.log('control_state_send: '+control_state_send);

        setControl2(control_state_send);

        let date = new Date();
        // eslint-disable-next-line
        let datetime = date.getFullYear()+"-"+(('0' + (date.getMonth() + 1)).slice(-2))+"-"+(('0' + date.getDate()).slice(-2))+" "+('0' + date.getHours()).slice(-2)+":"+('0' + date.getMinutes()).slice(-2)+":"+('0' + date.getSeconds()).slice(-2);
        // eslint-disable-next-line
        let body = "{\"datetime\": "+datetime+","+"\"redState\": "+control_state_send[0]+","+"\"redRange\": "+control_value[0]+","+"\"blueState\": "+control_state_send[1]+","+"\"blueRange\": "+control_value[1] +","+"\"fanState\": "+ control_state_send[2]+","+"\"fanRange\": "+ control_value[2]+"}"// eslint-disable-next-line

        console.log(body)

        const response = await axios.post(
            '/2022-09-29/control', 
            {
                datetime: datetime,
                redState: control_state_send[0],
                redRange: control_value[0],
                blueState: control_state_send[1],
                blueRange: control_value[1],
                fanState: control_state_send[2],
                fanRange: control_value[2]
            }
        );
    
        console.log(response);
    }
    
    const onMouseDown = (e) =>{
        filter = true;
        target  = e.target;
    
        if(target.id === 'slider_red_box' || target.id ==='slider_blue_box' || target.id ==='slider_fan_box'){
            btn = e.target.children[0];
            color = e.target.children[1];
            tooltip =  e.target.children[2];
        }
        else{
            target = e.target.parentElement;
            btn = target.children[0];
            color = target.children[1];
            tooltip =  target.children[2];
        }
        
        onMouseMove(e);
        window.addEventListener('mousemove', onMouseMove);
        window.addEventListener('mouseup', onMouseUp);
        
        
    }
    
    const onMouseMove = (e) => {
        e.preventDefault();
        // console.log(target);
        targetRect = target.getBoundingClientRect();
        let x = e.pageX - targetRect.left + 10;
        if (x > targetRect.width) { x = targetRect.width};
        if (x < 0){ x = 0};
        // let btnRect = btn.getBoundingClientRect();
        btn.x = x - 10;
        btn.style.left = btn.x + 'px';
    
        // get the position of the button inside the container (%)
        percentPosition = (btn.x + 10) / targetRect.width * 100;
        
        // color width = position of button (%)
        color.style.width = percentPosition + "%";
    
        // move the tooltip when button moves, and show the tooltip
        tooltip.style.left = btn.x - 5 + 'px';
        tooltip.style.opacity = 1;
    
        // show the percentage in the tooltip
        tooltip.textContent = Math.round(percentPosition) + '%';

        // console.log(percentPosition)
        // console.log(Math.round(percentPosition/100*targetRect.width-10));
        // console.log(percentPosition+10/672*100)
        // console.log(btn.x);
        // targetRect.width == 672
    
    };
    

    const onMouseUp  = async(e) => {
        if(filter){
            // eslint-disable-next-line
            let target_control ='';
            window.removeEventListener('mousemove', onMouseMove);
            tooltip.style.opacity = 0;
        
            btn.addEventListener('mouseover', function() {
              tooltip.style.opacity = 1;
            });
            
            btn.addEventListener('mouseout', function() {
              tooltip.style.opacity = 0;
            });
            
    
            if(target.id === 'slider_red_box'){
                control_value[0] = Math.round(percentPosition);
                target_control = 'red/'+control_value[0]
            }
            else if (target.id === 'slider_blue_box'){
                control_value[1] = Math.round(percentPosition);
                target_control = 'blue/'+control_value[1]
            }
            else if(target.id === 'slider_fan_box'){
                control_value[2] = Math.round(percentPosition);
                target_control = 'fan/'+control_value[2]
            }
                
            console.log('control red value: red-'+control_value[0]+ ' blue-'+control_value[1]+' fan-'+control_value[2]);
    
            let control_state_send = [];
            for(let i=0;i<3;i++){
                if(control_state[i]){
                    control_state_send[i] = 1;
                }
                else{
                    control_state_send[i] = 0;
                }
            }
    
            setControl(control_value);
    
            let date = new Date();
            let datetime = date.getFullYear()+"-"+(('0' + (date.getMonth() + 1)).slice(-2))+"-"+(('0' + date.getDate()).slice(-2))+" "+('0' + date.getHours()).slice(-2)+":"+('0' + date.getMinutes()).slice(-2)+":"+('0' + date.getSeconds()).slice(-2);
            // eslint-disable-next-line
            let body = "{\"datetime\": "+datetime+","+"\"redState\": "+control_state_send[0]+","+"\"redRange\": "+control_value[0]+","+"\"blueState\": "+control_state_send[1]+","+"\"blueRange\": "+control_value[1] +","+"\"fanState\": "+ control_state_send[2]+","+"\"fanRange\": "+ control_value[2]+"}"
    
            console.log(body)
    
            const response = await axios.post(
                '/2022-09-29/control', 
                {
                    datetime: datetime,
                    redState: control_state_send[0],
                    redRange: control_value[0],
                    blueState: control_state_send[1],
                    blueRange: control_value[1],
                    fanState: control_state_send[2],
                    fanRange: control_value[2]
                }
            );
        
            console.log(response);
            filter = false;
        }
       
    };


    const getControl = async () => {

        try {
        const response = await axios.get( //led fan on/off 확인
            '/2022-09-29/control',{
            headers: {
                'Access-Control-Allow-Origin':'*'
              }
            }
        );

        console.log(response.data);

        control_state[0] = response.data.redState;
        control_state[1] = response.data.blueState;
        control_state[2] = response.data.fanState;

        control_value[0] = response.data.redRange;
        control_value[1] = response.data.blueRange;
        control_value[2] = response.data.fanRange;


        console.log("control_state: "+control_state);
        console.log("control_value: "+control_value);

        setControl(control_value);
        setControl2(control_state)

        }catch (e) {
            // console.log("Control.js error: "+e.getMessae());
        }

    };
    
    useEffect(() => {
        getControl();
    },[]);

    return(
        <div class = "box_control">
    
        <div class="slider">
            <div class="slider_blank">
                <div class="slider_title">RED LED</div>
                <div class="switch">
                <div class="switch__1">
                  <input id="switch-1" type="checkbox" onClick={StateControl} checked={control2[0] || ''}/>
                  <label for="switch-1" ></label>
                </div>
              </div>
            </div>

            <div class="slider__box" id="slider_red_box" >
                <div class="slider__btn" id="red_btn" onClick={onMouseDown} style = {{left: (control[0]+10/672*100)-3 + '%'}}></div>
                <span class="slider__color" id='red_color' style = {{width: (control[0]+10/672*100) + '%'}}></span>
                <span class="slider__tooltip" id='red_toolip'>{control[0]}%</span>
            </div>
        </div>

        <div class="slider">
        <div class="slider_blank">
            <div class="slider_title">BLUE LED</div>
            <div class="switch">
            <div class="switch__2">
                <input  id="switch-2" type="checkbox" onClick={StateControl} checked={control2[1] || ''}></input>
                <label for="switch-2"></label>
            </div>
            </div>
        </div>

        <div class="slider__box" id="slider_blue_box">
            <div class="slider__btn" id="blue_btn"  onClick={onMouseDown} style = {{left: (control[1]+10/672*100)-3 + '%'}}></div>
            <span class="slider__color" id='blue_color' style = {{width: (control[1]+10/672*100) + '%'}}></span>
            <span class="slider__tooltip" id='blue_toolip'>{control[1]}%</span>
        </div>
        </div>

        <div class="slider">
        <div class="slider_blank">
            <div class="slider_title">FAN</div>
            <div class="switch">
            <div class="switch__3">
                <input id="switch-3" type="checkbox" onClick={StateControl} checked={control2[2] || ''} ></input>
                <label for="switch-3"></label>
            </div>
            </div>
        </div>


        <div class="slider__box" id="slider_fan_box">
            <div class="slider__btn" id="fan_btn" onClick={onMouseDown} style = {{left: (control[2]+10/672*100)-3 + '%'}}></div>
            <span class="slider__color" id='fan_color' style = {{width: (control[2]+10/672*100) + '%'}}></span>
            <span class="slider__tooltip" id='fan_toolip'>{control[2]}%</span>
        </div>
        </div>


    </div>
    );

}

export default Control;