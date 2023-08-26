const boxImgA = document.getElementById('box-a-img');
const boxTempA = document.getElementById('box-a-temp');

fetch("https://api.openweathermap.org/data/2.5/weather?q=Sofia&appid=8485efed455456abfa98a49fe0c985f2")
    .then(data => data.json())
    .then(info => {
        // console.log(info)
        boxTempA.innerText = Math.round(info.main.temp - 273.15);
        boxImgA.src = 'https://openweathermap.org/img/wn/' + info.weather[0].icon + '@4x.png';
    })

//###########################

function showCurrentWeather(curr){
    let dt = new Date(curr.dt * 1000);

    return `<div class="row md-6" id="current-weather" xmlns="http://www.w3.org/1999/html">
                    <div class="col">
                        <div class="card mb-4">
                            <div class="card-header">${dt.toDateString()} Summary: ${curr.weather[0].description}</div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-4">
                                        <img src="http://openweathermap.org/img/wn/${curr.weather[0].icon}@4x.png"
                                             class="img-fluid float-start" alt="Weather description" width="150"
                                             height="150">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p>${curr.temp}&deg;C</p>
                                                <small>Feels like ${curr.feels_like}&deg;C</small>
                                            </div>
                                            <div class="col-md-6">
                                                <small>Humidity ${curr.humidity}%</small> </br>
                                                <small>Wind ${curr.wind_speed}m/s,</small>                                                
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <small>Pressure ${curr.pressure} hPa</small> </br>
                                                <small>UV index ${curr.uvi}</small>
                                            </div>
                                            <div class="col-md-6">
                                                <small>Sunset ${new Date(curr.sunset * 1000).toTimeString()}</small>
                                            </div>
                                        </div>
                                    </div>                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>`;
}

const app = {
    init: () => {
        document
            .getElementById('btnGet')
            .addEventListener('click', app.fetchWeather);
        document
            .getElementById('btnCurrent')
            .addEventListener('click', app.getLocation);
    },
    fetchWeather: (ev) => {
        //use the values from latitude and longitude to fetch the weather
        let lat = document.getElementById('latitude').value;
        let lon = document.getElementById('longitude').value;
        let key = '13be0b6c206437560575635d4143498b';
        let lang = 'en';
        let units = 'metric';
        let url = `https://api.openweathermap.org/data/3.0/onecall?lat=${lat}&lon=${lon}&exclude=minutely,hourly&appid=${key}&units=${units}&lang=${lang}`;
        //fetch the weather
        fetch(url)
            .then((resp) => {
                if (!resp.ok) throw new Error(resp.statusText);
                return resp.json();
            })
            .then((data) => {
                app.showWeather(data);
            })
            .catch(console.err);
    },
    getLocation: (ev) => {
        let opts = {
            enableHighAccuracy: true,
            timeout: 1000 * 10, //10 seconds
            maximumAge: 1000 * 60 * 5, //5 minutes
        };
        navigator.geolocation.getCurrentPosition(app.gotPosition, app.positionFail, opts);
    },
    gotPosition: (position) => {
        //got position
        document.getElementById('latitude').value =
            position.coords.latitude.toFixed(2);
        document.getElementById('longitude').value =
            position.coords.longitude.toFixed(2);
    },
    positionFail: (err) => {
        //geolocation failed
        // let errors = {
        //     1: 'No permission',
        //     2: 'Unable to determine',
        //     3: 'Took too long'
        // }
        console.error(err.message);
    },
    showWeather: (resp) => {
        console.log(resp);
        let currentWeatherElement = document.getElementById("current-weather")
        let row = document.querySelector('.weather.row');
        //clear out the old weather and add the new
        // row.innerHTML = '';
        let curr = resp.current;

         currentWeatherElement.innerHTML = showCurrentWeather(curr);

        // row.innerHTML = resp.daily
        //     .map((day, idx) => {
        //         if (idx <= 3) {
        //             let dt = new Date(day.dt * 1000); //timestamp * 1000
        //             let sr = new Date(day.sunrise * 1000).toTimeString();
        //             let ss = new Date(day.sunset * 1000).toTimeString();
        //             return `<div class="col">
        //       <div class="card">
        //       <h5 class="card-title p-2">${dt.toDateString()}</h5>
        //         <img
        //           src="http://openweathermap.org/img/wn/${
        //                 day.weather[0].icon
        //             }@4x.png"
        //           class="card-img-top"
        //           alt="${day.weather[0].description}"
        //         />
        //         <div class="card-body">
        //           <h3 class="card-title">${day.weather[0].main}</h3>
        //           <p class="card-text">High ${day.temp.max}&deg;C Low ${
        //                 day.temp.min
        //             }&deg;C</p>
        //           <p class="card-text">High Feels like ${day.feels_like.day}&deg;C</p>
        //           <p class="card-text">Pressure ${day.pressure}mb</p>
        //           <p class="card-text">Humidity ${day.humidity}%</p>
        //           <p class="card-text">UV Index ${day.uvi}</p>
        //           <p class="card-text">Precipitation ${Math.round(day.pop * 100)}%</p>
        //           <p class="card-text">Dewpoint ${day.dew_point}</p>
        //           <p class="card-text">Wind ${day.wind_speed}m/s, ${
        //                 day.wind_deg
        //             }&deg;</p>
        //           <p class="card-text">Sunrise ${sr}</p>
        //           <p class="card-text">Sunset ${ss}</p>
        //         </div>
        //       </div>
        //     </div>
        //   </div>`;
        //         }
        //     })
        //     .join(' ');
    }
};

app.init();


