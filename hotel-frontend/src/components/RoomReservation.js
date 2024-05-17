import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { HOST_RESERVATION } from '../hosts';

function RoomReservation({ roomId, onUpdateRooms }) {
    const [reservedDates, setReservedDates] = useState([]);
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());

    useEffect(() => {
        fetchReservedDates(roomId);
    }, [roomId]);

    function fetchReservedDates(roomId) {
        fetch(`${HOST_RESERVATION}/room?id=${roomId}`)
            .then(response => response.json())
            .then(data => {
                setReservedDates(data);
            })
            .catch(error => console.error('Error fetching reserved dates:', error));
    }

    const handleBook = () => {
        // Adjust start and end times
        const startDateTime = new Date(startDate);
        startDateTime.setHours(14, 0, 0, 0);

        const endDateTime = new Date(endDate);
        endDateTime.setHours(10, 0, 0, 0);


        if (endDateTime <= startDateTime) {
            console.error('End date must be at least one day after the start date');
            return;
        }

        // Check if the new reservation overlaps with existing reservations
        const isOverlapping = reservedDates.some(reservation => {
            const existingStart = new Date(reservation.startDate);
            const existingEnd = new Date(reservation.endDate);

            // Check if the new reservation overlaps with any existing reservation
            return (
                (startDateTime >= existingStart && startDateTime < existingEnd) ||
                (endDateTime > existingStart && endDateTime <= existingEnd) ||
                (startDateTime <= existingStart && endDateTime >= existingEnd)
            );
        });

        if (isOverlapping) {
            console.error('Reservation overlaps with existing reservation');
            return;
        }

        // Make reservation request
        const reservationData = {
            roomId: roomId,
            startDate: startDateTime.toISOString(),
            endDate: endDateTime.toISOString(),
        };

        fetch(`${HOST_RESERVATION}/save`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(reservationData),
        })
            .then(response => {
                if (response.ok) {

                    fetchReservedDates(roomId);
                    onUpdateRooms();
                    console.log('Reservation successful');
                } else {
                    // Handle reservation error
                    console.error('Reservation failed');
                }
            })
            .catch(error => {
                console.error('Error making reservation:', error);
            });
    };

    const handleCancelReservation = (id, startTime) => {
        const currentTime = new Date();
        const twoHoursLater = new Date(startTime);
        twoHoursLater.setHours(twoHoursLater.getHours() + 2);

        if (currentTime < twoHoursLater || currentTime > startTime) {
            console.error('Cannot delete reservation less than 2 hours before start time or after checkin');
            return;
        }


        console.log('Cancel reservation:', id);
        fetch(`${HOST_RESERVATION}/id?id=${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    console.log('Reservation deleted successfully');
                    fetchReservedDates(roomId);
                    onUpdateRooms();
                } else {
                    console.error('Failed to delete reservation');
                }
            })
            .catch(error => {
                console.error('Error deleting reservation:', error);
            });
    };

    const nextDay = new Date(startDate);
    nextDay.setDate(nextDay.getDate() + 1);

    const handleStartDateChange = (date) => {
        setStartDate(date);

        // Calculate the next day after the new start date
        const nextDay = new Date(date);
        nextDay.setDate(nextDay.getDate() + 1);

        // if the current end date is before the next day, update it to the next day
        if (endDate < nextDay) {
            setEndDate(nextDay);
        }
    };

    return (
        <div>
            <h2>Reserved Dates</h2>
            <ul>
                {reservedDates.map((reservation, index) => (
                    <li key={index}>
                        {new Date(reservation.startDate).toLocaleDateString('en-GB')} - {new Date(reservation.endDate).toLocaleDateString('en-GB')}
                        <button onClick={() => handleCancelReservation(reservation.id, reservation.startDate)}>Cancel</button>
                    </li>
                ))}
            </ul>
            <h2>Select Dates for New Reservation</h2>
            <div>
                <DatePicker
                    selected={startDate}
                    onChange={handleStartDateChange}
                    minDate={new Date()}
                    dateFormat="dd-MM-yyyy"
                />
                <DatePicker
                    selected={endDate}
                    onChange={date => setEndDate(date)}
                    minDate={nextDay}
                    dateFormat="dd-MM-yyyy"
                />
                <button onClick={handleBook}>Book</button>
            </div>
        </div>
    );
}

export default RoomReservation;