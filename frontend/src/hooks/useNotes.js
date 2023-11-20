import { useState, useEffect } from 'react';

const useNotes = (patientId) => {
    const [messages, setMessages] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        // get token from localstorage and send it
        // fetchPatientNotes(patientId, token)


    }, [patientId]);

    return { messages, loading };
};

export default useNotes;