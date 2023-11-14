import React, { useState } from "react";
import { Link, useParams } from 'react-router-dom';

const Patient = () => {
    const { id } = useParams();
    // Fetch patient data using 'id'
    const patient = {
        id: 1,
        name: "John Doe",
        username: "Johndoe@kth.se",
        age: 30,
        diagnosis: "Fever"
    }

    const handleButtonClick1 = () => {
        console.log('Show Notes');
    };

    const handleButtonClick2 = () => {
        console.log('Show Messages');
    };

    return (
        <div className="container mt-4">
            <h1>Patient Information</h1>
            <table className="table">
                <tbody>
                    <tr>
                        <td>ID:</td>
                        <td>{patient.id}</td>
                    </tr>
                    <tr>
                        <td>Name:</td>
                        <td>{patient.name}</td>
                    </tr>
                    <tr>
                        <td>Username:</td>
                        <td>{patient.username}</td>
                    </tr>
                    <tr>
                        <td>Age:</td>
                        <td>{patient.age}</td>
                    </tr>
                    <tr>
                        <td>Diagnosis:</td>
                        <td>{patient.diagnosis}</td>
                    </tr>
                </tbody>
            </table>

            <div className="mt-4">
                <button className="btn btn-primary me-2" onClick={handleButtonClick1}>Notes</button>
                <Link to={`/patient/${id}/messages`} className="btn btn-info me-2">
                    Messages
                </Link>
            </div>
        </div>
    );
};

export default Patient;
