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

      if (type === 'all') {
        url = '/messages/all';
      } else if (type === 'active') {
        url = '/messages/active';
      } else if (type === 'archived') {
        url = '/messages/archived';
      } else if (type === 'patient' && patientId) {
        url = `/patient/${patientId}/messages`;
      } else {
        console.error('Invalid arguments for useMessages');
        return;
      }
      try {
        if (user && user.token ) {
          const fetchedNotes = await get(`/patients/${patientId}/notes/list`, user.token);

          if (fetchedNotes) {
            setMessages(fetchedNotes);
          } else {
            toast.error('Error fetching patient messages');
          }
        }
      } catch (apiError) {
        toast.error('Error fetching patient messages');
      }
    };

    fetchMessages();
  }, [type, patientId]);

  return { messages, loading };
};

export default useMessages;