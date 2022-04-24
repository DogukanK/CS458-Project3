/**
 * Create google maps Map instance.
 * @param {number} lat
 * @param {number} lng
 * @return {Object}
 */
const createMap = ({ lat, lng }) => {
  return new google.maps.Map(document.getElementById('map'), {
    center: { lat, lng },
    zoom: 15
  });
};

/**
 * Create google maps Marker instance.
 * @param {Object} map
 * @param {Object} position
 * @return {Object}
 */
const createMarker = ({ map, position }) => {
  return new google.maps.Marker({ map, position });
};


/**
 * Track the user location.
 * @param {Object} onSuccess
 * @param {Object} [onError]
 * @return {number}
 */
const trackLocation = ({ onSuccess, onError = () => { } }) => {
  if ('geolocation' in navigator === false) {
    return onError(new Error('Geolocation is not supported by your browser.'));
  }

  return navigator.geolocation.watchPosition(onSuccess, onError, {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0
  });
};

/**
 * Get position error message from the given error code.
 * @param {number} code
 * @return {String}
 */
const getPositionErrorMessage = code => {
  switch (code) {
    case 1:
      return 'Permission denied.';
    case 2:
      return 'Position unavailable.';
    case 3:
      return 'Timeout reached.';
  }
}


//calculate the coordinates country
function findCountry(cor) {
  //if cor has no comma, error
  if (cor.indexOf(",") == -1) {
    alert("Error: Please enter valid coordinates (latitude, longitude)");
    return;
  }
  //console.log(cor);
  //split cor to lat and lng
  var lat = cor.split(",")[0];
  var lon = cor.split(",")[1];
  //google's reverse geocode api is paid so i used different one
  fetch(`https://api.geoapify.com/v1/geocode/reverse?lat=${lat}&lon=${lon}&apiKey=9a14581af46544eb9f289e74d579ce10`)
  .then(response => response.json())
  .then(result => {
    if (result.features.length) {
      console.log(result.features[0].properties.country);
      //insert the country name to the result div
      document.getElementById("result_country").textContent = result.features[0].properties.country;
    } else {
      console.log("No address found");
    }
  });

}

//find distance to the north pole
function findDistanceNorthPole() {
  //first get the device coordinates
  //console.log(globLat, globLng);
  
  //then calculate the distance to the north pole
  var R = 6371; // Radius of the earth in km
  var dLat = (globLat - 90) * (Math.PI / 180);  // deg2rad below
  var dLon = (globLng - 0) * (Math.PI / 180);
  var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(globLat * (Math.PI / 180)) * Math.cos(90 * (Math.PI / 180)) *
    Math.sin(dLon / 2) * Math.sin(dLon / 2);
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  var d = R * c; // Distance in km
  console.log(d);
  //insert the distance to the north pole to the result div
  document.getElementById("result_northpole").textContent = d.toFixed(2);

}


let globLat = 0;
let globLng = 0;
/**
 * Initialize the application.
 * Automatically called by the google maps API once it's loaded.
*/
function init() {
  //Default location set to Bilkent University.
  const initialPosition = { lat: 39.87, lng: 32.74 };
  const map = createMap(initialPosition);
  const marker = createMarker({ map, position: initialPosition });
  const $info = document.getElementById('info');

  let watchId = trackLocation({
    onSuccess: ({ coords: { latitude: lat, longitude: lng } }) => {
      marker.setPosition({ lat, lng });
      map.panTo({ lat, lng });
      globLat = lat;
      globLng = lng;
      $info.textContent = `Latitude: ${lat.toFixed(5)}, Longitude: ${lng.toFixed(5)}`;
      $info.classList.remove('error');
    },
    onError: err => {
      console.log($info);
      $info.textContent = `Error: ${err.message || getPositionErrorMessage(err.code)}`;
      $info.classList.add('error');
    }
  });
}
