let map = L.map('map').setView([42.76, 25.23], 7);


let openTopoMap = L.tileLayer('https://{s}.tile.opentopomap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: 'Map data: &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, <a href="http://viewfinderpanoramas.org">SRTM</a> | Map style: &copy; <a href="https://opentopomap.org">OpenTopoMap</a> (<a href="https://creativecommons.org/licenses/by-sa/3.0/">CC-BY-SA</a>)'
});

let bgMountains = L.tileLayer('https://bgmtile.kade.si/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: 'Map data: &copy; <a href="http://kade.si">BG Mountains</a> contributors'
});

let osm = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: 'Map data: &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
});
osm.addTo(map);

let baseMaps = {
    "OpenStreetMap": osm,
    "BgMountains": bgMountains,
    "OpenTopoMap": openTopoMap
};
let layerControl = L.control.layers(baseMaps).addTo(map);


let marker = null;
map.on('click', (event) => {

    if (marker !== null) {
        map.removeLayer(marker);
    }

    marker = L.marker([event.latlng.lat, event.latlng.lng]).addTo(map);

    document.getElementById('latitude').value = event.latlng.lat.toPrecision(7);
    document.getElementById('longitude').value = event.latlng.lng.toPrecision(7);

})


// ############################################################### Tracking

const trackBtn = document.getElementById("btnTrack");
const stopTrackBtn = document.getElementById("stopTrackBtn");

trackBtn.addEventListener("click", startTracking);
stopTrackBtn.addEventListener("click", stopTracking);

let positions = [];
const HIGH_ACCURACY = true;
const MAX_CACHE_AGE_MILLISECOND = 0;
const MAX_NEW_POSITION_MILLISECOND = 500;

const options = {
    enableHighAccuracy: HIGH_ACCURACY,
    maximumAge: MAX_CACHE_AGE_MILLISECOND,
    timeout: MAX_NEW_POSITION_MILLISECOND,
};

let watchPositionHandlerId;

function startTracking() {
    console.log("track track ...")
    watchPositionHandlerId = navigator.geolocation.watchPosition(positionSuccess, positionFailure, options);
    console.log(watchPositionHandlerId)
}

function positionSuccess(geolocationPosition) {
    console.log(geolocationPosition)
    let latlngs = [geolocationPosition.coords.latitude, geolocationPosition.coords.longitude];

    positions.push(latlngs);

    console.log(positions);

    let polyline = L
        .polyline(positions, {color: 'red'})
        .addTo(map);

    map.fitBounds(polyline.getBounds());
}

function positionFailure(err) {
    console.error(`ERROR(${err.code}): ${err.message}`)
}

function stopTracking() {
    navigator.geolocation.clearWatch(watchPositionHandlerId);
}
