import React, { useEffect, useState } from 'react';
import api from '../api/axiosConfig';

const Dashboard = () => {
    const [projects, setProjects] = useState([]);
    const [newProjectName, setNewProjectName] = useState("");
    const [newTaskTitle, setNewTaskTitle] = useState({}); // Maneja títulos por proyecto

    const loadData = async () => {
        try {
            const res = await api.get('/projects');
            setProjects(res.data);
        } catch (err) { console.error("Error loading data", err); }
    };

    const handleCreateProject = async (e) => {
        e.preventDefault();
        await api.post('/projects', { name: newProjectName });
        setNewProjectName("");
        loadData();
    };

    const handleCreateTask = async (projectId) => {
        await api.post(`/projects/${projectId}/tasks`, { title: newTaskTitle[projectId] });
        setNewTaskTitle({ ...newTaskTitle, [projectId]: "" });
        loadData();
    };

    const handleActivate = async (id) => {
        try {
            await api.patch(`/projects/${id}/activate`);
            loadData();
            alert("Project Activated!");
        } catch (err) { alert("Error: A project needs at least 1 active task to be activated."); }
    };

    useEffect(() => { loadData(); }, []);

    return (
        <div style={{ padding: '20px', fontFamily: 'Arial' }}>
            <h1>My Projects Dashboard</h1>
            <form onSubmit={handleCreateProject}>
                <input value={newProjectName} onChange={e => setNewProjectName(e.target.value)} placeholder="New Project Name" required />
                <button type="submit">Create Project</button>
            </form>

            <div style={{ marginTop: '20px' }}>
                {projects.map(p => (
                    <div key={p.id} style={{ border: '1px solid #444', padding: '15px', marginBottom: '10px', borderRadius: '8px' }}>
                        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                            <h2>{p.name} <span style={{ fontSize: '12px', color: p.status === 'ACTIVE' ? 'green' : 'orange' }}>[{p.status}]</span></h2>
                            <button onClick={() => handleActivate(p.id)} disabled={p.status === 'ACTIVE'}>Activate Project</button>
                        </div>

                        <div style={{ background: '#f9f9f9', padding: '10px' }}>
                            <h4>Add Task:</h4>
                            <input 
                                value={newTaskTitle[p.id] || ""} 
                                onChange={e => setNewTaskTitle({...newTaskTitle, [p.id]: e.target.value})} 
                                placeholder="Task title..." 
                            />
                            <button onClick={() => handleCreateTask(p.id)}>Add</button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Dashboard;