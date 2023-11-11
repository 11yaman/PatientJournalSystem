import React from 'react';
import './PatientPage.css';

const PatientPage = ({ patient }) => {
    return (
        <div>
            <h1>Patient Information</h1>
            <table>
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

            <button onClick={() => handleButtonClick1()}>Notes</button>
            <button onClick={() => handleButtonClick2()}>Messages</button>
        </div>
    );
};

const handleButtonClick1 = () => {
    console.log('Show Notes');
};

const handleButtonClick2 = () => {
    console.log('Show Messages');
};

export default PatientPage;
