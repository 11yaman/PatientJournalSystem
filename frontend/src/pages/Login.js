import React, { useContext } from 'react'
import { Link } from 'react-router-dom'

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AuthContext from '../context/AuthContext';

const Login = () => {
  const {loginUser} = useContext(AuthContext);

  const [credentials, setCredentials] = React.useState({
    email: '',
    password: ''
  })

  const handleChange = (e) => {
    setCredentials({
    ...credentials,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    if(!credentials.email ||!credentials.password) {
      toast.error('Please fill in all fields')
      return;
    }

    loginUser(credentials);
  }
 
  return (
    <>
    <ToastContainer autoClose={2000}/>
      <form onSubmit={handleSubmit}>
          <h3>Login</h3>
          <div class="form-group">
              <label htmlFor="emailInput" class="form-label mt-4">Email address</label>
              <input 
                type="username" // edit later to email
                className="form-control" 
                id="emailInput"  
                name="email"
                value={credentials.email}
                onChange={handleChange}
                placeholder="Enter email" 
                required
              />
          </div>
          <div class="form-group">
              <label htmlFor="passwordInput" class="form-label mt-4">Password</label>
              <input 
                type="password" 
                className="form-control" 
                id="passwordInput" 
                name="password"
                value={credentials.password}
                onChange={handleChange}
                placeholder="Enter password"  
                required
              />
          </div>
          <input type="submit" value="Login" className="btn btn-primary my-3"/>
          <p>Don't have an account? <Link to="/register">Create one</Link></p>
      </form>
    </>
  )
}

export default Login;