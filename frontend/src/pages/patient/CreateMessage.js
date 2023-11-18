import React, { useState } from "react";

const CreateMessage = () => {
  const [content, setContent] = useState("");

  const handleContentChange = (e) => {
    setContent(e.target.value);
  };

  const handleSendMessage = () => {
    // logic to send the message

    console.log("Sending message:", content);
    setContent("");
  };

  return (
    <div>
      <h1>New Message</h1>
      <div className="mb-3">
        <label htmlFor="content" className="form-label">
          Message Content:
        </label>
        <textarea
          id="content"
          className="form-control"
          rows="5"
          value={content}
          onChange={handleContentChange}
        ></textarea>
      </div>
      <button className="btn btn-primary" onClick={handleSendMessage}>
        Send
      </button>
    </div>
  );
};

export default CreateMessage;
