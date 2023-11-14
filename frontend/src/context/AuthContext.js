import { createContext, useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import ToastContext from "./ToastContext";

const AuthContext = createContext();
const BASE_URL = "http://localhost:8080/api/v1";

export const AuthContextProvider = ({ children }) => {
  const { toast } = useContext(ToastContext);
  const navigate = useNavigate();
  const location = useLocation();

  const [user, setUser] = useState(null);

  useEffect(() => {
    checkUserLoggedIn();
  }, []);

  const getErrorMessage = (status) => {
    switch (status) {
      case 401:
        return "Incorrect user ID or password";
      case 403:
        return "Access Denied";
      case 404:
        return "Not found";
      case 406:
        return "Invalid input data";
      case 409:
        return "Already exists";
      default:
        return "An error occurred";
    }
  };

  const checkUserLoggedIn = async () => {
    try {
      const storedUser = JSON.parse(localStorage.getItem('user'));
      console.log("storedUser: " +  JSON.stringify(storedUser));
      if (storedUser) {
        const res = await fetch(`${BASE_URL}/auth/info`, {
          method: "GET",
          headers: {
            Authorization: `Basic ${storedUser.token}`,
          },
        });
        if (res.ok) {
          const result = await res.json(); 
          if (
            location.pathname === "/login" ||
            location.pathname === "/register"
          ) {
            setTimeout(() => {
              navigate("/", { replace: true });
            }, 500);
          } else {
            navigate(location.pathname ? location.pathname : "/");
          }
          setUser(result);
        } else {
          navigate("/login", { replace: true });
        }
      }
    } catch (err) {
      console.log(err);
    }
  };

  const loginUser = async (userData) => {
    try {
      const res = await fetch(`${BASE_URL}/auth/authenticate`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ ...userData }),
      });
      if (res.ok) {
        const result = await res.json();
        result.token = window.btoa(userData.email + ':' + userData.password);
        localStorage.setItem("user", JSON.stringify(result));
        setUser(result);
        toast.success(`Logged in ${result.firstName}`);

        navigate("/", { replace: true });
      } else {
        const errorMessage = getErrorMessage(res.status);
        toast.error(errorMessage);
      }
    } catch (err) {
      console.log(err);
      toast.error("An error occurred");
    }
  };

  const registerPatient = async (userData) => {
    try {
      const res = await fetch(`${BASE_URL}/auth/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ ...userData }),
      });

      if (res.ok) {
        const result = await res.json();
        result.token = window.btoa(userData.email + ':' + userData.password);
        localStorage.setItem("user", JSON.stringify(result));
        setUser(result);
        toast.success("user registered successfully!");
        
        navigate("/", { replace: true });
      } else {
        const errorMessage = getErrorMessage(res.status);
        toast.error(errorMessage);
      }
    } catch (err) {
      console.log(err);
      toast.error("An error occurred");
    }
  };

  const logout = async () => {
    try {
      await fetch(`${BASE_URL}/auth/logout`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("user")}`,
        },
      });
      localStorage.removeItem("user");
      setUser(null);
      navigate("/login");
      toast.success("Logged out successfully");
    } catch (error) {
      console.error("Error logging out:", error);
      toast.error("An error occurred");
    }
  };

  return (
    <AuthContext.Provider value={{ loginUser, registerPatient, logout, user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;