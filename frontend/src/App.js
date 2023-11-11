import React from 'react';
import './App.css';
import PatientPage from './PatientPage';
import TestData from './TestData.json';

function App() {
    const user = TestData.find(patient => patient.id === 1); //Ändra detta så att den inloggade användarens ID används
  return (
      <div className="App">
          <PatientPage patient={user} />
    </div>
  );
}

export default App;
