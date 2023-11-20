import { useEffect, useState } from 'react';
import useApi from './useApi';
import useAuth from './useAuth';
import { toast } from 'react-toastify';

const useNotes = (patientId) => {
  const { get, loading, error } = useApi();
  const [messages, setMessages] = useState([]);

  const {user} = useAuth()

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (user && user.token ) {
          const fetchedNotes = await get(`/patients/${patientId}/notes/list`, user.token);

          if (fetchedNotes) {
            setMessages(fetchedNotes);
          } else {
            toast.error('Error fetching patient notes');
          }
        }
      } catch (apiError) {
        toast.error('Error fetching patient notes');
      }
    };

    fetchData();
  }, [patientId]);

  return { messages, loading, error };
};

export default useNotes;
