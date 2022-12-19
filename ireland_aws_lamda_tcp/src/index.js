import React from 'react';
import ReactDOM from 'react-dom/client';
import Control from './Control';
import Sensing from './Sensing';
import Alram from './Alram';

const sensing = ReactDOM.createRoot(document.querySelector('.sensing'));
const control = ReactDOM.createRoot(document.querySelector('.control'));
const alram = ReactDOM.createRoot(document.querySelector('.alram'));


sensing.render(
  <React.StrictMode>
    <Sensing />
  </React.StrictMode>
)

control.render(
  <React.StrictMode>
    <Control />
  </React.StrictMode>
)

alram.render(
  <React.StrictMode>
    <Alram />
  </React.StrictMode>
)

