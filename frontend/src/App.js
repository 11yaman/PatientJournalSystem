import React from 'react';
import './App.css';
import Patient from './pages/Patient';
import TestData from './TestData.json';
import Layout from './components/Layout';
import {Routes as Switch, Route} from 'react-router-dom';
import Register from './pages/Register';
import Login from './pages/Login';
import Home from './pages/Home';
import { AuthContextProvider } from './context/AuthContext';
import { ToastContextProvider } from './context/ToastContext';
import AllPatients from './pages/employee/AllPatients';
import PatientMessages from './pages/employee/PatientMessages';
import MessageDetails from './pages/employee/MessageDetails';

function App() {
    const user = TestData.find(patient => patient.id === 1);
  return (
    <ToastContextProvider>
      <AuthContextProvider>
        <Layout>
          <Switch>
            <Route path="/" element={<Home/>} />
            <Route path="/login" element={<Login/>} />
            <Route path="/register" element={<Register/>} />

            //admin
            <Route path="/allpatients" element={<AllPatients />} />
            <Route path="/patient/:id" element={<Patient />} />
            <Route path="/patient/:id/messages" element={<PatientMessages />} />
            <Route path="/messages/:id" element={<MessageDetails />} />
          </Switch> 
        </Layout>
      </AuthContextProvider>
    </ToastContextProvider>
  );
}

export default App;
