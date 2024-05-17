import React, { useState, useEffect } from 'react';
import { HOST_REVIEW } from '../hosts';
import Modal from 'react-modal';

function ReviewModal({ hotelId, isOpen, closeModal, updateScore }) {
    const [review, setReview] = useState('');
    const [rating, setRating] = useState(0);
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        if (isOpen && hotelId) {
            fetchReviewsByHotel(hotelId)
                .then(data => {
                    setReviews(data);
                });
        }
    }, [hotelId, isOpen]);

    const fetchReviewsByHotel = async (hotelId) => {
        const response = await fetch(`${HOST_REVIEW}/hotel?id=${hotelId}`);
        const data = await response.json();
        return data;
    };

    const handleSubmit = (event) => {
        event.preventDefault(); // Prevent default form submission behavior

        const newReview = {
            hotelId: hotelId,
            stars: rating,
            message: review
        };

        fetch(`${HOST_REVIEW}/save`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newReview)
        }).then(() => {
            //refresh reviews after submission
            fetchReviewsByHotel(hotelId)
                .then(data => {
                    setReviews(data);
                });

            
            const newScore = calculateNewScore(reviews.concat(newReview));
            // Update score in parent component
            updateScore(hotelId, newScore);
        });
        setReview('');
        setRating(0);
    };

    const calculateNewScore = (reviews) => {
        if (reviews.length === 0) return 0;

        const totalStars = reviews.reduce((acc, review) => acc + review.stars, 0);
        return totalStars / reviews.length;
    };

    return (
        <Modal
            isOpen={isOpen}
            onRequestClose={closeModal}
            contentLabel="Review Modal"
        >
            <div>
                <h2>Leave a Review</h2>
                <form onSubmit={handleSubmit}>
                    <label>
                        Rating:
                        <input type="number" value={rating} min={1} max={5} onChange={(e) => setRating(parseInt(e.target.value))} />
                    </label>
                    <br />
                    <label>
                        Review:
                        <textarea value={review} onChange={(e) => setReview(e.target.value)} />
                    </label>
                    <br />
                    <button type="submit">Post Review</button>
                </form>
                <h3>Existing Reviews</h3>
                <ul>
                    {reviews.map((review, index) => (
                        <li key={index}>
                            {review.message} - {review.stars}/5⭐
                        </li>
                    ))}
                </ul>
                <button onClick={closeModal}>Close</button>
            </div>
        </Modal>
    );
}

export default ReviewModal;