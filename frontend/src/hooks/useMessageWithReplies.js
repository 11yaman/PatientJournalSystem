import { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import useAuth from './useAuth';
import useApi from './useApi';

const useMessageWithReplies = (messageId) => {
  const { get, loading, error } = useApi();
  const [message, setMessage] = useState([]);

  const {user} = useAuth();

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (user && user.token) {
          const fetchedMessage = await get(`/messages/${messageId}`, user.token);
          console.log("id "+ fetchedMessage.id);
          if (fetchedMessage) {
            setMessage(fetchedMessage);
          } else {
            toast.error('Error fetching message with replies');
          }
        }
      } catch (apiError) {
        toast.error('Error fetching message with replies');
      } 
    };

    fetchData();
    
  }, [user, messageId]);
  return { message, loading };
};

export default useMessageWithReplies;
