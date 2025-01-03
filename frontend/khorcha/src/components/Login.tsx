import { useAuth } from '../contexts/AuthContext';

export default function Login() {
  const { login } = useAuth();

  return (
    <div className="flex items-center justify-center min-h-screen">
      <button
        onClick={login}
        className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
      >
        Sign In
      </button>
    </div>
  );
}