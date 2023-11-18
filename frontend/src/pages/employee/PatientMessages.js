import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import Spinner from "../../components/Spinner";
import usePatientMessages from "../../hooks/usePatientMessages";

const Messages = () => {
  const { patientId } = useParams();

  const { patientMessages, loading } = usePatientMessages(patientId); 
  const [messages, setMessages] = useState([]);

  const mockMessages = [
    {
      id: 1,
      content: 'I need an appointment',
      dateTime: '2023-11-05T17:31:52.384',
      sender: {
        id: 1,
        email: 'employee@example.com',
        firstName: 'John',
        lastName: 'Doe',
        role: 'EMPLOYEE',
      },
      status: 'ACTIVE',
    },
    {
      id: 2,
      content: 'I am feeling bad',
      dateTime: '2023-11-06T09:15:00.000',
      sender: {
        id: 1,
        email: 'employee@example.com',
        firstName: 'John',
        lastName: 'Doe',
        role: 'EMPLOYEE',
      },
      status: 'ACTIVE',
    },
  ];

  useEffect(() => {
    setMessages(mockMessages); //messageWithReplies
  }, [patientId]);
    
  return (
    <div>
      <h1>Messages for Patient</h1>
      <Link to="/allpatients" className="btn btn-danger my-2">
        Back to All Patients
      </Link>
      <hr className="my-4" />
      {loading ? (
        <Spinner splash="Loading Messages..." />
      ) : (
        <div>
          {messages.length === 0 ? (
            <h3>No messages for this patient</h3>
          ) : (
            <div>
              <table className="table table-hover">
                <thead>
                  <tr className="table-dark">
                    <th scope="col">Content</th>
                    <th scope="col">Date & Time</th>
                    <th scope="col">Status</th>
                    <th scope="col">Replies</th>
                    <th scope="col"></th>

                  </tr>
                </thead>
                <tbody>
                  {messages.map((message) => (
                    <tr key={message.id}>
                      <td>{message.content}</td>
                      <td>{new Date(message.dateTime).toLocaleString()}</td>
                      <td>{message.status}</td>
                      <td>
                        <Link to={`/messages/${message.id}`} className="btn btn-info">
                          Replies
                        </Link>
                      </td>
                      <td>
                        <button
                          className="btn btn-warning"
                          onClick={console.log("Archived")}
                        >
                          Archive
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default Messages;