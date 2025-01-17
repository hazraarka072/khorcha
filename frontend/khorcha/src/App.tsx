import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import { Callback } from './components/Callback';
import Home from './components/Home';
import Login from './components/Login';
import Accounts from './components/Accounts';

import './App.css'
import './index.css'

function App() {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route path="/callback" element={<Callback />} />
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Accounts />} />
        </Routes>
      </AuthProvider>
    </Router>
  );
}

export default App;