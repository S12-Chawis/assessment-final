import React, { useState } from 'react';
import api from '../api/axiosConfig';
import { useNavigate } from 'react-router-dom'; // Importa el hook

const Login = () => {
    const [form, setForm] = useState({ username: '', password: '' });
    const navigate = useNavigate(); // Inicializa la navegación

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const { data } = await api.post('/auth/login', form);
            localStorage.setItem('token', data.token);
            alert("Login successful!");
            
            // Redirige al dashboard después del éxito
            navigate('/dashboard'); 
        } catch (err) {
            console.error(err);
            alert("Unauthorized: Check your credentials");
        }
    };

    return (
        <div className="login-container">
            <form onSubmit={handleSubmit}>
                <h1>Project Manager</h1>
                <input type="text" placeholder="Username" 
                    onChange={e => setForm({...form, username: e.target.value})} required />
                <input type="password" placeholder="Password" 
                    onChange={e => setForm({...form, password: e.target.value})} required />
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;