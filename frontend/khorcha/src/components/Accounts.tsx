import { useState, useEffect } from 'react';
import { useApi } from '../hooks/useApi';

interface UserData {
  id: string;
  name: string;
  email: string;
}

export default function Accounts() {
  const { fetchData, loading, error } = useApi();
  const [userData, setUserData] = useState<UserData | null>(null);

  useEffect(() => {
    async function loadUserData() {
      const data = await fetchData<UserData>('/users/test@test.com/accounts');
      if (data) {
        setUserData(data);
      }
    }
    loadUserData();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;
  if (!userData) return <div>No user data found</div>;

  return (
    <div>
      <h1>User Profile</h1>
      <p>Name: {userData}</p>
    </div>
  );
}