import React, { useState } from 'react';
import api from '../api/axiosConfig';

const Login = () => {
    const [form, setForm] = useState({ username: '', password: '' });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const { data } = await api.post('/auth/login', form);
            localStorage.setItem('token', data.token); // Almacenamos el token [cite: 40]
            alert("Login successful!");
            // Aquí podrías usar useNavigate() de react-router-dom para ir al dashboard
        } catch (err) {
            console.error(err);
            alert("Unauthorized: Check your credentials"); // [cite: 48]
        }
    };

    return (
        <div className="login-container">
            <form onSubmit={handleSubmit}>
                <h1>Project Manager</h1>
                <input type="text" placeholder="Username" 
                    onChange={e => setForm({...form, username: e.target.value})} />
                <input type="password" placeholder="Password" 
                    onChange={e => setForm({...form, password: e.target.value})} />
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;