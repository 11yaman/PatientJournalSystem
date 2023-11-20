import { createContext, useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import ToastContext from "./ToastContext";
import useApi from "../hooks/useApi";  // Import the useApi hook

const AuthContext = createContext();

export const AuthContextProvider = ({ children }) => {
  const { toast } = useContext(ToastContext);
  const navigate = useNavigate();
  const location = useLocation();

  const [user, setUser] = useState(null);
  const { get, post, loading, error } = useApi();  // Initialize useApi hook

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
      const storedUser = JSON.parse(localStorage.getItem("user"));
      console.log("storedUser: " + JSON.stringify(storedUser));
      if (storedUser) {
        const result = await get("/auth/info", storedUser.token);

        if (result) {
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
      const result = await post("/auth/authenticate", userData);

      if (result) {
        result.token = window.btoa(userData.email + ":" + userData.password);
        localStorage.setItem("user", JSON.stringify(result));
        setUser(result);
        toast.success(`Logged in ${result.firstName}`);

        navigate(location.state?.from?.pathname || "/", { replace: true });
      } else {
        const errorMessage = getErrorMessage(error.status);
        toast.error(errorMessage);
      }
    } catch (err) {
      console.log(err);
      toast.error("An error occurred");
    }
  };

  const registerPatient = async (userData) => {
    try {
      const result = await post("/auth/register", userData);

      if (result) {
        result.token = window.btoa(userData.email + ":" + userData.password);
        localStorage.setItem("user", JSON.stringify(result));
        setUser(result);
        toast.success("User registered successfully!");

        navigate("/", { replace: true });
      } else {
        const errorMessage = getErrorMessage(error.status);
        toast.error(errorMessage);
      }
    } catch (err) {
      console.log(err);
      toast.error("An error occurred");
    }
  };

  const logout = async () => {
    try {
      const storedUser = JSON.parse(localStorage.getItem("user"));
      if (storedUser) {
        await post("/auth/logout", null, storedUser.token);
        localStorage.removeItem("user");
        setUser(null);
        navigate("/login");
        toast.success("Logged out successfully");
      }
    } catch (error) {
      console.error("Error logging out:", error);
      toast.error("An error occurred");
    }
  };

  return (
    <AuthContext.Provider
      value={{ loginUser, registerPatient, logout, user, setUser }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;