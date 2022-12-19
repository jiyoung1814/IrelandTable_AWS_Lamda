/*  rate slider */
// const container = document.querySelectorAll('.slider__box');
// const btn_red = document.querySelector('.slider_red_btn');
// const color_red= document.querySelector('.slider_red_color');
// const tooltip_red = document.querySelector('.slider_red_tooltip');
// const btn_blue = document.querySelector('.slider_blue_btn');
// const color_blue = document.querySelector('.slider_blue_color');
// const tooltip_blue = document.querySelector('.slider_blue_tooltip');
// const btn_fan = document.querySelector('.slider_fan_btn');
// const color_fan = document.querySelector('.slider_fan_color');
// const tooltip_fan = document.querySelector('.slider_fan_tooltip');

// const btn_arr = [btn_red, btn_blue,btn_fan];
// const color_arr = [color_red,color_blue,color_fan];
// const tooltip_arr = [tooltip_red,tooltip_blue,tooltip_fan];

/*  clock */
const hours = document.querySelector('.hours');
const minutes = document.querySelector('.minutes');
const seconds = document.querySelector('.seconds');

clock = () => {
  let today = new Date();
  let h = (today.getHours() % 12) + today.getMinutes() / 59; // 22 % 12 = 10pm
  let m = today.getMinutes(); // 0 - 59
  let s = today.getSeconds(); // 0 - 59

  h *= 30; // 12 * 30 = 360deg
  m *= 6;
  s *= 6; // 60 * 6 = 360deg

  rotation(hours, h);
  rotation(minutes, m);
  rotation(seconds, s);

  // call every second
  setTimeout(clock, 500);
}

rotation = (target, val) => {
  target.style.transform =  `rotate(${val}deg)`;
}

window.onload = clock();

// dragElement = (target, btn, color, tooltip) => {
//   target.addEventListener('mousedown', (e) => {
//       onMouseMove(e);
//       window.addEventListener('mousemove', onMouseMove);
//       window.addEventListener('mouseup', onMouseUp);
//   });

//   onMouseMove = (e) => {
//       e.preventDefault();
//       // console.log(target);
//       let targetRect = target.getBoundingClientRect();
//       let x = e.pageX - targetRect.left + 10;
//       if (x > targetRect.width) { x = targetRect.width};
//       if (x < 0){ x = 0};
//       btn.x = x - 10;
//       btn.style.left = btn.x + 'px';

//       // get the position of the button inside the container (%)
//       let percentPosition = (btn.x + 10) / targetRect.width * 100;
      
//       // color width = position of button (%)
//       color.style.width = percentPosition + "%";

//       // move the tooltip when button moves, and show the tooltip
//       tooltip.style.left = btn.x - 5 + 'px';
//       tooltip.style.opacity = 1;

//       // show the percentage in the tooltip
//       tooltip.textContent = Math.round(percentPosition) + '%';
//   };

//   onMouseUp  = (e) => {
//       window.removeEventListener('mousemove', onMouseMove);
//       tooltip.style.opacity = 0;

//       btn.addEventListener('mouseover', function() {
//         tooltip.style.opacity = 1;
//       });
      
//       btn.addEventListener('mouseout', function() {
//         tooltip.style.opacity = 0;
//       });
//   };
// };

// dragElement2 = (target, btn, color, tooltip) => {
//   target.addEventListener('mousedown', (e) => {
//       onMouseMove(e);
//       window.addEventListener('mousemove', onMouseMove);
//       window.addEventListener('mouseup', onMouseUp);
//   });

//   onMouseMove = (e) => {
//       e.preventDefault();
//       // console.log(target);
//       let targetRect = target.getBoundingClientRect();
//       let x = e.pageX - targetRect.left + 10;
//       if (x > targetRect.width) { x = targetRect.width};
//       if (x < 0){ x = 0};
//       btn.x = x - 10;
//       btn.style.left = btn.x + 'px';

//       // get the position of the button inside the container (%)
//       let percentPosition = (btn.x + 10) / targetRect.width * 100;
      
//       // color width = position of button (%)
//       color.style.width = percentPosition + "%";

//       // move the tooltip when button moves, and show the tooltip
//       tooltip.style.left = btn.x - 5 + 'px';
//       tooltip.style.opacity = 1;

//       // show the percentage in the tooltip
//       tooltip.textContent = Math.round(percentPosition) + '%';
//   };

//   onMouseUp  = (e) => {
//       window.removeEventListener('mousemove', onMouseMove);
//       tooltip.style.opacity = 0;

//       btn.addEventListener('mouseover', function() {
//         tooltip.style.opacity = 1;
//       });
      
//       btn.addEventListener('mouseout', function() {
//         tooltip.style.opacity = 0;
//       });
//   };
// };

// dragElement3 = (target, btn, color, tooltip) => {
//   target.addEventListener('mousedown', (e) => {
//       onMouseMove(e);
//       window.addEventListener('mousemove', onMouseMove);
//       window.addEventListener('mouseup', onMouseUp);
//   });

//   onMouseMove = (e) => {
//       e.preventDefault();
//       // console.log(target);
//       let targetRect = target.getBoundingClientRect();
//       let x = e.pageX - targetRect.left + 10;
//       if (x > targetRect.width) { x = targetRect.width};
//       if (x < 0){ x = 0};
//       btn.x = x - 10;
//       btn.style.left = btn.x + 'px';

//       // get the position of the button inside the container (%)
//       let percentPosition = (btn.x + 10) / targetRect.width * 100;
      
//       // color width = position of button (%)
//       color.style.width = percentPosition + "%";

//       // move the tooltip when button moves, and show the tooltip
//       tooltip.style.left = btn.x - 5 + 'px';
//       tooltip.style.opacity = 1;

//       // show the percentage in the tooltip
//       tooltip.textContent = Math.round(percentPosition) + '%';
//   };

//   onMouseUp  = (e) => {
//       window.removeEventListener('mousemove', onMouseMove);
//       tooltip.style.opacity = 0;

//       btn.addEventListener('mouseover', function() {
//         tooltip.style.opacity = 1;
//       });
      
//       btn.addEventListener('mouseout', function() {
//         tooltip.style.opacity = 0;
//       });
//   };
// };

// // dragElement(container, btn);


// // dragElement(container);
// // dragElement(container[0], btn_red, color_red, tooltip_red);
// // dragElement(container[1], btn_blue,color_blue, tooltip_blue);
// // dragElement(container[2], btn_fan,color_fan, tooltip_fan);
// dragElement(container[0], btn_arr[0], color_arr[0], tooltip_arr[0]);
// // dragElement2(container[1], btn_arr[1], color_arr[1], tooltip_arr[1]);
// // dragElement3(container[2], btn_arr[2], color_arr[2], tooltip_arr[2]);
