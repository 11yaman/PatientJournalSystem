import {Link} from 'react-router-dom';
import AuthContext from '../context/AuthContext';
import { useContext } from 'react';

const Navbar = ({title = "Patient Journal System"}) => {
  const {user, logout} = useContext(AuthContext);

  const handleLogout = () => {
    logout();
  };

  return (
    <nav className="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
      <div className="container-fluid">
        <Link to="/">
          <a className="navbar-brand" href="#">
            {title}
          </a>
        </Link>
        <button 
          className="navbar-toggler" 
          type="button" 
          data-bs-toggle="collapse" 
          data-bs-target="#navbarColor01" 
          aria-controls="navbarColor01" 
          aria-expanded="false" 
          aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarColor01">
          <ul className="navbar-nav ms-auto">
            {user ? <>       
              <li className="nav-item">
                <Link to="/allpatients">
                  <a className="nav-link">
                    All patients
                  </a>
                </Link>
              </li>
              <li className="nav-item" onClick={handleLogout}>
                <button className="btn btn-danger">
                  Log out
                </button>   
              </li>               
            </> : <>
              <li className="nav-item">
                <Link to="/login">
                  <a className="nav-link">
                    Login
                  </a>
                </Link>
              </li>
              <li className="nav-item">
                <Link to="/register">
                  <a className="nav-link">
                    Register
                  </a>
                </Link>
              </li>
            </>}
            </ul>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;