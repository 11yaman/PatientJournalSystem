import { useState, useEffect } from 'react';

const useMessages = (type, patientId) => {
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchMessages = async () => {
      setLoading(true);

      try {
        let url;

        // Determine the URL based on the type of messages
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

        // Fetch messages based on the determined URL
        const response = await fetch(url);
        const data = await response.json();

        // Sort messages from newest to oldest based on the dateTime property
        const sortedMessages = data.sort((a, b) => new Date(b.dateTime) - new Date(a.dateTime));

        setMessages(sortedMessages);
      } catch (error) {
        console.error('Error fetching messages', error);
      } finally {
        setLoading(false);
      }
    };

    fetchMessages();
  }, [type, patientId]);

  return { messages, loading };
};

export default useMessages;