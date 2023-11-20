import { useState, useEffect } from 'react';
import useAuth from './useAuth';
import useApi from './useApi';
import { toast } from 'react-toastify';

const useMessages = (type, patientId) => {
  const { get, loading, error } = useApi();
  const [messages, setMessages] = useState([]);

  const {user} = useAuth()

  useEffect(() => {
    const fetchMessages = async () => {
      let url;

      if (type === 'active') {
        url = '/messages/active';
      } else if (type === 'archived') {
        url = ''; {/* not implemented yet */}
      } else if (type === 'patient' && patientId) {
        url = `/patients/${patientId}/messages/list`;
      } else {
        toast.error('An error occured')
        return;
      }
      try {
        if (user && user.token ) {
          const fetchedMessages = await get(url, user.token);

          if (fetchedMessages) {
            setMessages(fetchedMessages);
          } else {
            toast.error('Error fetching messages');
          }
        }
      } catch (apiError) {
        toast.error('Error fetching messages');
      }
    };

    fetchMessages();
  }, [type, patientId]);

  return { messages, loading };
};

export default useMessages;