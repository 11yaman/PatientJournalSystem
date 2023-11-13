import {React, useEffect, useRef, useState, userEffect} from'react';

const USER_REGEX = /^[a-zA-Z][a-zA-Z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[ !@#$%]).{8,24}$/;

const Register = () => {
    const useRef = useRef();
    const errRef = useRef(); 

    const [username, setUsername] = useState('');
    const [validUsername, setValidUsername] = useState(false);

    const [password, setPassword] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    
    const [err, setErr] = useState('');
    const [sucess, setSucces] = useState(false);

    useEffect(() => {
        useRef.current.focus();
    }, [])
    
    useEffect(() => {
      const result = USER_REGEX.test(username) && PWD_REGEX.test(password);
      console.log(result);
      setValidUsername(result);
    }, [username])
    
    
    useEffect(() => {
        const result = PWD_REGEX.test(password);
        console.log(result);
        setValidPassword(result);
        

    }, [password, matchPassword])

    useEffect(() => {
      setErr('');
    
    }, [username, password, matchPassword])
    

    return (
        <div>Register</div>
    )
}

export default Register