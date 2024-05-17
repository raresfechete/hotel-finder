import React, { useState, useEffect } from 'react';
import Modal from 'react-modal';
import { HOST_ROOM } from '../hosts';
import RoomReservation from './RoomReservation';

// Decode room types
function decodeRoomType(type) {
    switch (type) {
        case 1:
            return 'Single';
        case 2:
            return 'Double';
        case 3:
            return 'Suite';
        case 4:
            return 'Matrimonial';
        default:
            return 'Unknown';
    }
}

function RoomModal({ hotelId, isOpen, closeModal }) {
    const [rooms, setRooms] = useState([]);
    const [datePickerVisibility, setDatePickerVisibility] = useState({});

    useEffect(() => {
        if (isOpen && hotelId) {
            fetchRoomsByHotel(hotelId)
                .then(data => {
                    setRooms(data);

                    // Initialize date picker visibility state for each room
                    const visibilityState = {};
                    data.forEach(room => {
                        visibilityState[room.id] = false;
                    });
                    setDatePickerVisibility(visibilityState);
                });
        }
    }, [hotelId, isOpen]);

    // Function to fetch rooms by hotel ID
    function fetchRoomsByHotel(hotelId) {
        return fetch(`${HOST_ROOM}/hotel?id=${hotelId}`)
            .then(response => response.json())
            .catch(error => console.error('Error fetching rooms:', error));
    }

    // Toggle visibility of DateRangePicker for a specific room
    function toggleDatePickerVisibility(roomId) {
        setDatePickerVisibility(prevState => ({
            ...prevState,
            [roomId]: !prevState[roomId]
        }));
    }

    // Function to update rooms after adding or deleting reservations
    const onUpdateRooms = () => {
        fetchRoomsByHotel(hotelId)
            .then(data => {
                setRooms(data);
            })
            .catch(error => console.error('Error fetching updated rooms:', error));
    };

    return (
        <Modal
            isOpen={isOpen}
            onRequestClose={closeModal}
            contentLabel="Rooms Modal"
        >
            <h2>Room List</h2>
            {rooms.length === 0 ? (
                <p>No rooms available for this hotel.</p>
            ) : (
                <div>
                    <ul>
                        {rooms.map(room => (
                            <li key={room.id}>
                                Room {room.roomNumber}: {decodeRoomType(room.type)}, ${room.price}/night, {room.isAvailable ? 'Available' : 'Not Available Today'}
                                <button onClick={() => toggleDatePickerVisibility(room.id)}>Book this room</button>
                                {datePickerVisibility[room.id] && <RoomReservation roomId={room.id} onUpdateRooms={onUpdateRooms} />}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
            <button onClick={closeModal}>Close</button>
        </Modal>
    );
}

export default RoomModal;
