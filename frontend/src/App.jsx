import { Routes, Route, Navigate } from 'react-router-dom'

import Login from './pages/Login'
import Register from './pages/Register'
import Dashboard from './pages/Dashboard'
import Logs from './pages/Logs'
import Incidents from './pages/Incidents'
import IncidentDetails from './pages/IncidentDetails'
import Layout from './components/Layout'
import ProtectedRoute from './components/ProtectedRoute'
import Profile from './pages/Profile'

function App() {
    return (
        <Routes>
            {/* Public routes */}
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            {/* Protected application routes */}
            <Route
                element={
                    <ProtectedRoute>
                        <Layout />
                    </ProtectedRoute>
                }
            >
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/logs" element={<Logs />} />
                <Route path="/incidents" element={<Incidents />} />
                <Route
                    path="/incidents/:id"
                    element={<IncidentDetails />}
                />
                <Route path="/profile" element={<Profile />} />
            </Route>

            {/* Unknown URL */}
            <Route
                path="*"
                element={<Navigate to="/login" replace />}
            />
        </Routes>
    )
}

export default App