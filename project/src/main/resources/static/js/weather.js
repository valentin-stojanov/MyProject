const boxImgA = document.getElementById('box-a-img');
const boxTempA = document.getElementById('box-a-temp');

fetch("https://api.openweathermap.org/data/2.5/weather?q=Sofia&appid=8485efed455456abfa98a49fe0c985f2")
.then(data => data.json())
.then(info =>{
    // console.log(info)
    boxTempA.innerText = Math.round(info.main.temp - 273.15);
    boxImgA.src = 'https://openweathermap.org/img/wn/' + info.weather[0].icon + '@4x.png';
})
