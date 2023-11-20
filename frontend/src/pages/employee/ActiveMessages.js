import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Spinner from "../../components/Spinner";
import useMessages from "../../hooks/useMessages";

const ActiveMessages = () => {
  const { activeMessages, loading } = useMessages("active"); 
  const [messages, setMessages] = useState([]);
  const [searchInput, setSearchInput] = useState("");

  const mockMessages = [
    {
      "id": 19,
      "content": "hej",
      "dateTime": "2023-11-05T12:31:52.384",
      "sender": {
          "id": 1,
          "email": "yaman",
          "firstName": "Yaman",
          "lastName": "A",
          "role": "PATIENT"
      },
      "status": "ACTIVE"
  },
  {
      "id": 20,
      "content": "Godmorgon hej",
      "dateTime": "2023-11-10T22:19:11.525608",
      "sender": {
          "id": 6,
          "email": "Test",
          "firstName": "test",
          "lastName": "t",
          "role": "PATIENT"
      },
      "status": "ACTIVE"
  }
];

  useEffect(() => {
    setMessages(mockMessages); // Replace with actual data fetch logic
  }, []);

  
  const handleSearchSubmit = (event) => {
    event.preventDefault();

    const filteredMessages = messages.filter((message) =>
      message.content.toLowerCase().includes(searchInput.toLowerCase()) ||
      message.sender.firstName.toLowerCase().includes(searchInput.toLowerCase()) ||
      message.sender.lastName.toLowerCase().includes(searchInput.toLowerCase())
    );

    setMessages(filteredMessages);
  };

  return (
    <>
      <div>
        <h1>Active Messages</h1>
        <a href="/messages/active" className="btn btn-danger my-2">
          Reload Message List
        </a>
        <Link to="" className="btn btn-secondary my-2 mx-2"> {/*Not implemented yet*/}
          View Archived Messages
        </Link>

        <hr className="my-4" />
        {loading ? (
          <Spinner splash="Loading Messages..." />
        ) : (
          <>
            {messages.length === 0 ? (
              <h3>No active messages</h3>
            ) : (
              <>
                <form className="d-flex mb-3" onSubmit={handleSearchSubmit}>
                  <input
                    type="text"
                    name="searchInput"
                    id="searchInput"
                    className="form-control my-2"
                    placeholder="Search Message By Patient Name"
                    value={searchInput}
                    onChange={(e) => setSearchInput(e.target.value)}
                  />
                  <button type="submit" className="btn btn-info mx-2">
                    Search
                  </button>
                </form>

                <p>
                  Total Messages: <strong>{messages.length}</strong>
                </p>

                <table className="table table-hover">
                  <thead>
                    <tr className="table-dark">
                      <th scope="col">Patient Name</th>
                      <th scope="col">Content</th>
                      <th scope="col">Date & Time</th>
                      <th scope="col">Status</th>
                      <th scope="col">Replies</th>
                      <th scope="col"></th>
                    </tr>
                  </thead>
                  <tbody>
                    {messages.map((message) => (
                      <tr key={message.id}>
                        <td>{`${message.sender.firstName} ${message.sender.lastName}`}</td> 
                        <td>{message.content}</td>
                        <td>{new Date(message.dateTime).toLocaleString()}</td>
                        <td>{message.status}</td>
                        <td>
                          <Link to={`/messages/${message.id}`} className="btn btn-info">
                            Replies
                          </Link>
                        </td>
                        <td>
                        {/*Not implemented yet*/}
                          <button
                            className="btn btn-warning"
                            onClick={() => console.log("Archived")} 
                          > 
                            Archive
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </>
          )}
        </>
      )}
    </div>
    </>
  );
};

export default ActiveMessages;
