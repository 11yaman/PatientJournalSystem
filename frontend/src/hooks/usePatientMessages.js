import { useState, useEffect } from 'react';

const usePatientMessages = (patientId) => {
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // get token from localstorage and send it
    // fetchPatientMessages(patientId, token)


  }, [patientId]);

  return { messages, loading };
};

export default usePatientMessages;
