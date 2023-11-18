import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useMessageWithReplies from "../../hooks/useMessageWithReplies";
import Spinner from '../../components/Spinner';

const MessageDetails = () => {
  const { messageId } = useParams(); 

  const { messageWithReplies, loading } = {}; //useMessagesWithReplies
  const [messageDetails, setMessageDetails] = useState([]);

  const mockMessageDetails = [
    {
      id: 1,
      content: 'hej',
      dateTime: '2023-11-05T17:31:52.384',
      sender: {
        id: 1,
        email: 'yaman',
        firstName: 'Yaman',
        lastName: 'A',
        role: 'PATIENT',
      },
      status: 'ACTIVE',
      replies: [
        {
          id: 6,
          content: 'reply1',
          dateTime: '2023-11-10T22:59:01.052195',
          sender: {
            id: 1,
            email: 'yaman',
            firstName: 'Yaman',
            lastName: 'A',
            role: 'PATIENT',
          },
        },
        {
          id: 7,
          content: 'reply2',
          dateTime: '2023-11-10T23:01:29.942987',
          sender: {
            id: 2,
            email: 'noor',
            firstName: 'Noor',
            lastName: 'A',
            role: 'EMPLOYEE',
          },
        },
        {
          id: 7,
          content: 'reply2',
          dateTime: '2023-11-10T23:01:29.942987',
          sender: {
            id: 2,
            email: 'noor',
            firstName: 'Noor',
            lastName: 'A',
            role: 'EMPLOYEE',
          },
        },
        {
          id: 7,
          content: 'reply2',
          dateTime: '2023-11-10T23:01:29.942987',
          sender: {
            id: 2,
            email: 'noor',
            firstName: 'Noor',
            lastName: 'A',
            role: 'EMPLOYEE',
          },
        },
        {
          id: 7,
          content: 'reply2',
          dateTime: '2023-11-10T23:01:29.942987',
          sender: {
            id: 2,
            email: 'noor',
            firstName: 'Noor',
            lastName: 'A',
            role: 'EMPLOYEE',
          },
        },
      ],
    },
  ];

  useEffect(() => {
    setMessageDetails(mockMessageDetails); //messageWithReplies
  }, [messageId]);

  const renderMessage = (message) => (
    <div key={message.id} className='ml-3 bg-light p-3 rounded'>
      <p>
        Patient: {message.sender.firstName} {message.sender.lastName} ({message.sender.email})
      </p>
      <p>{message.content}</p>
      <p>Sent at: {new Date(message.dateTime).toLocaleString()}</p>
      {message.replies && message.replies.length > 0 && (
        <div>
          {message.replies.map((reply, index) => (
            <div key={reply.id} className="border rounded p-2 my-2">
              <p>{reply.content}</p>
              <p>Sent by: {reply.sender && `${reply.sender.firstName} ${reply.sender.lastName} (${reply.sender.email})`} 
                  , at {new Date(reply.dateTime).toLocaleString()}</p>  
            </div>
          ))}
        </div>
      )}
    </div>
  );

  return (
    <div>
      <h1>Message details</h1>
      {loading ? (
        <Spinner splash="Loading Message Details..." />
      ) : (
        <div>
          {messageDetails.map((message) => renderMessage(message))}
          <div className="mt-3">
            <input type="text" placeholder="Type your reply" className="form-control" />
            <button className="btn btn-primary my-3">Reply</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default MessageDetails;