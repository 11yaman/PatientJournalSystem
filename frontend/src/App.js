import React from 'react';
import './App.css';
import PatientPage from './PatientPage';
import TestData from './TestData.json';
import Layout from './components/Layout';
import {Routes as Switch, Route} from 'react-router-dom';
import Register from './pages/Register';
import Login from './pages/Login';
import Home from './pages/Home';
import { AuthContextProvider } from './context/AuthContext';
import { ToastContextProvider } from './context/ToastContext';

function App() {
    const user = TestData.find(patient => patient.id === 1); //�ndra detta s� att den inloggade anv�ndarens ID anv�nds
  return (
    <ToastContextProvider>
      <AuthContextProvider>
        <Layout>
          <Switch>
            <Route path="/" element={<Home/>} />
            <Route path="/login" element={<Login/>} />
            <Route path="/register" element={<Register/>} />
          </Switch> 
        </Layout>
      
      </AuthContextProvider>
    </ToastContextProvider>
  );
}

export default App;
