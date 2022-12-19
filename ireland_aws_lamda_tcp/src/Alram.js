import React from "react";
// import axios from 'axios';
import './Alram.css';

function Alram(){
    

    // var selectede = document.getElementsByName('radio2');
    // let data = '';
    // let time = [];

    // const setAlram = e =>{
    //   var start_hour = document.getElementById('input_start_hour');
    //   var start_mimute = document.getElementById('input_start_minute');
    //   var end_hour = document.getElementById('input_end_hour');
    //   var end_minute =document.getElementById('input_end_minute');
    //   for(let i=0;i<selectede.length;i++){
    //       if(selectede[i].checked === true){
    //           console.log(selectede[i].value);
    //           time[0] = parseInt(start_hour.value);
    //           time[1] = parseInt(start_mimute.value);
    //           time[2] = parseInt(end_hour.value);
    //           time[3] = parseInt(end_minute.value);
    //           // console.log('start_hour: '+ time[0]);
    //           // console.log('start_mimute: '+ time[1]);
    //           // console.log('end_hour: '+ time[2]);
    //           // console.log('end_minute: '+ time[3]);
    //           data = selectede[i].value;
    //         }
    //   }
    
    //   console.log('set Alram: '+data+'=> '+time[0]+':'+time[1]+' ~ ' + time[2] +':' +time[3]);
    //   data += ',' + time[0]+ ',' + time[1]  + ',' + time[2] +',' + time[3]
    //   console.log(data);


    //   let body = {"m2m:cin": {"con": data}};

    //   axios.post(
    //     '/Mobius/ireland/alram', JSON.stringify(body), {
    //         headers: {
    //             'Accept': 'application/json',
    //             'x-m2m-ri': '12345',
    //             'x-m2m-origin': 'Sireland',
    //             'content-type': 'application/json; ty=4'
    //         }
          
    //     }
    // );
        
    // }

    return(
        <div class="box">

            <div class="alram_blank"> 
        
                <div class="toggle">
                    <input type="radio" name="radio2" value="red" id="tab-1" checked/>
                    <label for="tab-1" class= "toggle__1">
                    <p>RED</p></label>
                    
                    <input type="radio" name="radio2" value="blue" id="tab-2" />
                    <label for="tab-2" class= "toggle__2">
                    <p>BLUE</p></label>
                    
                    <input type="radio" name="radio2" value="fan" id="tab-3" />
                    <label for="tab-3" class= "toggle__3">
                    <p>FAN</p></label>
                    
                    <div class="toggle__color"></div>
                </div>

                <div class="set_btn"><p>SET</p></div>
            </div>

          <div class="alram_blank2">
            <div class="time_blank">
              <div class="time_title">Start</div>  
              <div class="form">
                <input type="text" id="input_start_hour" placeholder="Hour"/>
              </div>
              <div class="form">
                <input type="text" id="input_start_minute" placeholder="Minute"/>
              </div>

            </div>
            <div class="time_blank">
              <div class="time_title">END</div>
              <div class="form">
                <input type="text" id="input_end_hour" placeholder="Hour"/>
              </div>
              <div class="form">
                <input type="text" id="input_end_minute" placeholder="Minute"/>
              </div>
            </div>

          </div>

          
        </div>

    );

}

export default Alram
    