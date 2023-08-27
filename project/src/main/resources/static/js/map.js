let map = L.map('map').setView([42.76, 25.23], 7);

let layer = new L.TileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
});
map.addLayer(layer);


let marker = null;
map.on('click', (event)=> {

    if(marker !== null){
        map.removeLayer(marker);
    }

    marker = L.marker([event.latlng.lat , event.latlng.lng]).addTo(map);

    document.getElementById('latitude').value = event.latlng.lat.toPrecision(7);
    document.getElementById('longitude').value = event.latlng.lng.toPrecision(7);

})