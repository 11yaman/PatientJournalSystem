import { useEffect, useState } from 'react';
import useApi from './useApi';
import { toast } from 'react-toastify';
import useAuth from './useAuth';

const usePatients = () => {
  const { get, loading, error } = useApi();
  const [patients, setPatients] = useState([]);
  
  const {user} = useAuth();

  useEffect(() => {
    const fetchData = async () => {

      try{
        if (user && user.token) {
          const fetchedPatients = await get('/patients/list', user.token);

          if (fetchedPatients) {
            setPatients(fetchedPatients);
          } else {
            toast.error("Error fetching patients")
          }
        }
      } catch (error) {
        toast.error('Error fetching patients');
      };
    };

    fetchData();
  }, []);

  return { patients, loading, error };
};

export default usePatients;
