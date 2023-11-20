import { useParams } from "react-router-dom";
import Spinner from "../../components/Spinner";
import { useEffect, useState } from "react";
import useAuth from '../../hooks/useAuth';
import useMessageWithReplies from "../../hooks/useMessageWithReplies";
import { toast } from "react-toastify";
import useApi from "../../hooks/useApi";

const MyMessageDetails = () => {
  const { messageId } = useParams(); 
  const {message, loading} = useMessageWithReplies(messageId);
  const {user} = useAuth();
  
  const [messageDetails, setMessageDetails] = useState();

  useEffect(() => {
    setMessageDetails(message); 
  }, [messageId, message, user]); 
  
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
      <h1>Message Details</h1>
      {loading ? (
        <Spinner splash="Loading Message Details..." />
      ) : (
        <div>
          {messageDetails && messageDetails.replies && renderMessage(messageDetails)}
          <div className="mt-3">
            <input type="text" placeholder="Type your reply" className="form-control" />
            <button className="btn btn-primary my-3">Reply</button>
          </div>
        </div>
      )}
    </div>
  );
};
export default MyMessageDetails;