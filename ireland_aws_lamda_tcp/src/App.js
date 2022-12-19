// import './App.css';
import axios from "axios";
import React, {useEffect} from "react";



function App() {
  // var temp;
  const callApi = async() => {
    var sensing = [];
    var indices = [];
    var indices2 = [];
    axios.get('/Mobius/ireland/sensor/latest',{
      headers: {
        'Accept': 'application/json',
        'X-M2M-RI': '12345',
        'X-M2M-Origin': 'Sireland'
      }
    })
    .then((res) => {
      // console.log(res.data);
      var str = JSON.stringify(res.data);
      var obj = JSON.parse(str);
      var con = obj['m2m:cin'].con;
      console.log(con)

      var idx = con.indexOf('/');
      while (idx !== -1) {
        indices.push(idx);
        idx = con.indexOf('/', idx + 1);
      }
      idx = con.indexOf(',');
      while (idx !== -1) {
        indices2.push(idx);
        idx = con.indexOf(',', idx + 1);
      }

      for(var i=0;i<indices.length;i++){
        sensing.push(con.substring(indices[i]+1,indices2[i]));
      }

      console.log(indices);
      console.log(indices2);
      console.log(sensing);

    });
  };

  useEffect(() => {
    callApi();
  },[]);

  return <div>callApi</div> 
  // return (
  //   <div className="App">
  //     <header className="App-header">
  //       <img src={logo} className="App-logo" alt="logo" />
  //       <p>
  //         Edit <code>src/App.js</code> and save to reload.
  //       </p>
  //       <a
  //         className="App-link"
  //         href="https://reactjs.org"
  //         target="_blank"
  //         rel="noopener noreferrer"
  //       >
  //         Learn React
  //       </a>
  //     </header>
  //   </div>
  // );
}

export default App;

