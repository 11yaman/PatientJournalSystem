import React, { useState } from "react";
import { Link, useLocation, useParams } from 'react-router-dom';

const Patient = () => {
  const { id } = useParams();
  const location = useLocation();
  const patient = location.state;

  const handleButtonClick1 = () => {
    console.log('Show Notes');
  };

  const handleButtonClick2 = () => {
    console.log('Show Messages');
  };

  return (
    <div className="container mt-4">
      <table className="table">
        <tbody>
          <tr>
            <td>Name:</td>
            <td>{patient.firstName} {patient.lastName}</td>
          </tr>
          <tr>
            <td>Username:</td>
            <td>{patient.email}</td>
          </tr>
        </tbody>
      </table>
      <div className="mt-4">
          <Link to={`/patient/${id}/notes`} className="btn btn-info me-2">
              Notes
          </Link>
          <Link to={`/patient/${id}/messages`} className="btn btn-info me-2">
            Messages
          </Link>
      </div>
    </div>
  );
};

export default Patient;
