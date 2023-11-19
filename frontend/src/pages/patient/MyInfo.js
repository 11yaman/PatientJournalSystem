import { useEffect, useState } from "react";
import Spinner from "../../components/Spinner";
import useAuth from "../../hooks/useAuth";
import { Link } from "react-router-dom";

const MyInfo = () => {
  const {user} = useAuth();

  const { patientInfo } = {}; //usePatientInfo(user.id)
  const [messages, setMessages] = useState([]);
  const [patient, setPatient] = useState([]);

  const mockPatientInfo = {
    id: 1,
    name: "John Doe",
    username: "Johndoe@kth.se"
  }

  useEffect(() => {
    setPatient(mockPatientInfo); // messages 
  }, []); //patient


  return (
    <div className="container mt-4">
      <h1>My Information</h1>
      <table className="table">
        <tbody>
          <tr>
            <td>Name:</td>
            <td>{patient.name}</td>
          </tr>
          <tr>
            <td>Username:</td>
            <td>{patient.username}</td>
          </tr>
        </tbody>
      </table>
      <div className="mt-4">
          <Link to={`/mynotes`} className="btn btn-info me-2">
              Notes
          </Link>
          <Link to={`/mymessages`} className="btn btn-info me-2">
            Messages
          </Link>
      </div>
    </div>
  );
};
  
  export default MyInfo;