import React, { useState } from 'react';
import { HOST_HOTEL } from '../hosts';
import HotelList from './HotelList';

function FindHotelsForm() {
    const [distance, setDistance] = useState(0);
    const [hotels, setHotels] = useState([]);

    // Function to find hotels based on coordinates
    function findHotels(latitude, longitude, distance) {
        fetch(`${HOST_HOTEL}/within?latitude=${latitude}&longitude=${longitude}&distance=${distance}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            if (response.ok) {
                //parse the JSON response body
                return response.json();
            } else {
                throw new Error('Request failed with status ' + response.status);
            }
        }).then(function (data) {
            setHotels(data);
        }).catch(function (error) {
            console.error('Error:', error.message);
        });
    }

    //function to get user's coordinates
    function getUserCoordinates(distance) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;

            findHotels(latitude, longitude, distance);
        }, function (error) {
            console.error('Error getting user coordinates:', error.message);
        });
    }

    const handleDistanceChange = (event) => {
        setDistance(parseFloat(event.target.value));
    };

    return (
        <div>
            <label htmlFor="distanceInput">Enter distance in kilometers</label>
            <input
                type="number"
                id="distanceInput"
                value={distance}
                onChange={handleDistanceChange}
            />
            <button variant="primary" onClick={() => getUserCoordinates(distance)}>Look for hotels</button>
            {hotels.length > 0 && <HotelList hotels={hotels} />} {/* Render HotelList if hotels are found */}
        </div>
    );
}

export default FindHotelsForm;