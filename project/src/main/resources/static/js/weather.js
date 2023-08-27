function showCurrentWeather(curr){
    let dt = new Date(curr.dt * 1000);
    return `<div class="row md-6" id="current-weather" xmlns="http://www.w3.org/1999/html">
                    <div class="col">
                        <div class="card text-bg-light mb-3">
                            <div class="card-header text-center">${dt.toDateString()} Summary: ${curr.weather[0].description}</div>
                            <div class="card-body text-start">
                                <div class="row">
                                    <div class="col-md-4">
                                        <img src="http://openweathermap.org/img/wn/${curr.weather[0].icon}@4x.png"
                                             class="img-fluid float-start" alt="Weather description" width="150"
                                             height="150">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p class="fs-4 fw-normal">${Math.round(curr.temp)}&deg;C</p>
                                                <small>Feels like ${Math.round(curr.feels_like)}&deg;C</small>
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
        window.addEventListener('load', app.fetchWeather)
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
        let curr = resp.current;
        currentWeatherElement.innerHTML = showCurrentWeather(curr);

        let forecast = document.getElementById('weather-forecast');
        //clear out the old weather and add the new
        forecast.innerHTML = '';

        forecast.innerHTML = resp.daily
            .map((day, idx) => {
                if (idx <= 3) {
                    let dt = new Date(day.dt * 1000); //timestamp * 1000
                    let dateStr = dt.toDateString();
                    return `<div class="col-md-3 col-sm-6">                        
                        <div class="card text-bg-light mb-3" style="width: 100%; height: 100%">
                             <img src="http://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png"
                              class="card-img-top" alt="${day.weather[0].description}"
                              style="height: 5rem;">
                             <div class="card-body">
                               <h5 class="card-title">${Math.round(day.temp.day)}&deg;C</h5>
                               <p class="card-text">${dateStr.slice(0, dateStr.length - 4)}</p>
                               <small class="fs-6">${day.summary}</small>
                             </div>
                        </div>
                    </div>`;
                }
            })
            .join(' ');
    }
};

app.init();


