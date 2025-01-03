import { useAuth } from '../contexts/AuthContext';
import { Navigate } from 'react-router-dom';
import accounts from './Accounts'

export default function Home() {
  const { user, isAuthenticated, logout } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" />;
  }

  return (
    <div className="p-4">
      <h1 className="text-2xl mb-4">Welcome, {user?.profile.name}!</h1>
      <button
        onClick={logout}
        className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
      >
        Sign Out
      </button>
    </div>
  );
}