import { useState, useEffect } from 'react';
import { fetchMessageWithReplies } from '../apis/MessageApi';

const useMessageWithReplies = (messageId) => {
  const [message, setMessage] = useState();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // get token from localstorage and send it
    // fetchMessageWithReplies(messageId, token)

  }, [messageId]);

  return { message, loading };
};

export default useMessageWithReplies;
