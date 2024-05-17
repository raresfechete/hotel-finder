import React, { useState, useEffect } from 'react';
import RoomModal from './RoomModal';
import ReviewModal from './ReviewModal';
import { HOST_REVIEW } from '../hosts';

function HotelList({ hotels }) {
    const [selectedHotelId, setSelectedHotelId] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isReviewModalOpen, setIsReviewModalOpen] = useState(false);
    const [hotelScores, setHotelScores] = useState({});

    useEffect(() => {
        // Fetch scores for each hotel
        hotels.forEach(hotel => {
            fetchHotelScore(hotel.id)
                .then(score => {
                    setHotelScores(prevScores => ({
                        ...prevScores,
                        [hotel.id]: score
                    }));
                });
        });
    }, [hotels]);

    const fetchHotelScore = async (hotelId) => {
        const response = await fetch(`${HOST_REVIEW}/stars?id=${hotelId}`);
        const data = await response.json();
        return data;
    };

    const handleSeeRooms = (hotelId) => {
        setSelectedHotelId(hotelId);
        setIsModalOpen(true);
    };

    const handleLeaveReview = (hotelId) => {
        setSelectedHotelId(hotelId);
        setIsReviewModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setIsReviewModalOpen(false);
    };

    const updateScore = (hotelId, newScore) => {
        setHotelScores(prevScores => ({
            ...prevScores,
            [hotelId]: newScore
        }));
    };

    return (
        <div>
            <h2>Found Hotels</h2>
            <ul>
                {hotels.map(hotel => (
                    <li key={hotel.id}>
                        {hotel.name} - {hotelScores[hotel.id] ? `${parseFloat(hotelScores[hotel.id]).toFixed(2)}/5⭐` : '0/5⭐'}
                        <button onClick={() => handleSeeRooms(hotel.id)}>See Rooms</button>
                        <button onClick={() => handleLeaveReview(hotel.id)}>Leave a Review</button>
                    </li>
                ))}
            </ul>
            <RoomModal hotelId={selectedHotelId} isOpen={isModalOpen} closeModal={closeModal} />
            <ReviewModal hotelId={selectedHotelId} isOpen={isReviewModalOpen} closeModal={closeModal} updateScore={updateScore} />
        </div>
    );
}

export default HotelList;