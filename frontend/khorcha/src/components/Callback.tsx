import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/AuthService';

export const Callback = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const handleCallback = async () => {
      try {
        await authService.handleLoginCallback();
        navigate('/');
      } catch (error) {
        console.error('Error handling callback:', error);
        navigate('/login');
      }
    };
    handleCallback();
  }, [navigate]);

  return <div>Loading...</div>;
};