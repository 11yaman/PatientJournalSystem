import { useState, useEffect } from 'react';
import { fetchAllPatients } from '../apis/PatientApi';

const usePatients = () => {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    const user = JSON.parse(localStorage.getItem('user'));

    if (user !== null && user.token !== null) {
      fetchAllPatients(user.token)
        .then((fetchedPatients) => {
          setPatients(fetchedPatients);
          setLoading(false);
        })
        .catch((err) => {
          console.log(err);
          setLoading(false);
        });
    } else {
      setLoading(false);
    }
  }, []);

  return { patients, loading };
};

export default usePatients;
