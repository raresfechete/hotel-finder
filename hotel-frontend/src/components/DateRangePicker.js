// womp womp
// import React, { useState, useEffect } from 'react';
// import { DateRange } from 'react-date-range';
// import 'react-date-range/dist/styles.css'; // main style file
// import 'react-date-range/dist/theme/default.css'; // theme css file
// import { HOST_RESERVATION } from '../hosts';

// function DateRangePicker({ roomId }) {
//     const [selection, setSelection] = useState({
//         startDate: new Date(),
//         endDate: new Date(),
//         key: 'selection',
//     });
//     const [reservedDates, setReservedDates] = useState([]);

//     useEffect(() => {
//         fetchReservedDates(roomId);
//     }, [roomId]);

//     function fetchReservedDates(roomId) {
//         fetch(`${HOST_RESERVATION}/room?id=${roomId}`)
//             .then(response => response.json())
//             .then(data => {
//                 const dates = data.map(reservation => ({
//                     startDate: new Date(reservation.startDate),
//                     endDate: new Date(reservation.endDate),
//                 }));
//                 setReservedDates(dates);
//             })
//             .catch(error => console.error('Error fetching reserved dates:', error));
//     }

//     const handleSelect = (ranges) => {
//         setSelection(ranges.selection);
//     };

//     const handleBook = () => {
//         // Adjust start and end times
//         const startDate = new Date(selection.startDate);
//         startDate.setHours(14, 0, 0, 0);

//         const endDate = new Date(selection.endDate);
//         endDate.setHours(10, 0, 0, 0);

//         // Make reservation request
//         const reservationData = {
//             roomId: roomId,
//             startDate: startDate.toISOString(),
//             endDate: endDate.toISOString(),
//         };

//         fetch(`${HOST_RESERVATION}/save`, {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify(reservationData),
//         })
//         .then(response => {
//             if (response.ok) {
//                 // Reservation successful
//                 // You can handle success or update UI as needed
//                 console.log('Reservation successful');
//             } else {
//                 // Handle reservation error
//                 console.error('Reservation failed');
//             }
//         })
//         .catch(error => {
//             console.error('Error making reservation:', error);
//         });
//     };

//     const disabledRanges = reservedDates.map(date => ({
//         startDate: date.startDate,
//         endDate: date.endDate,
//         key: 'reserved',
//     }));

//     const minDate = new Date();

//     return (
//         <div>
//             <DateRange
//                 editableDateInputs={true}
//                 onChange={handleSelect}
//                 moveRangeOnFirstSelection={false}
//                 ranges={[
//                     { ...selection, key: 'selection' }, // Selected date range
//                     ...disabledRanges // Reserved date ranges
//                 ]}
//                 minDate={minDate}
//             />
//             <button onClick={handleBook}>Book</button>
//         </div>
//     );
// }

// export default DateRangePicker;

