import { useState, useEffect } from 'react';
import { useApi } from '../hooks/useApi';
import { TokenService } from '../services/TokenService';

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
      const email = await TokenService.getEmail();
      const data = await fetchData<UserData>("/users/"+email+"/accounts");
      if (data) {
        setUserData(data);
      }
    }
    loadUserData();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;
  if (!userData) return <div>No user data found</div>;

  const listAccounts = userData.map((account) => (
          <tr key={account.accountName} class="hover:bg-slate-100 cursor-pointer odd:bg-white even:bg-slate-50">
              <td class="px-4 py-2">{account.accountName}</td>
              <td class="px-4 py-2">{account.currentBalance}</td>
          </tr>
      ));
  return (
    <div class="w-2/3 border border-slate-200 rounded-xl overflow-x-auto">
      <table class="w-full">
      <thead class="bg-slate-100 text-slate-800">
      <tr>
        <th class="px-4 py-2 text-start">Account</th>
        <th class="px-4 py-2 text-start">Balance</th>
      </tr>
      </thead>
       <tbody class="divide-y divide-slate-200 bg-white text-slate-800">
      {listAccounts}
      </tbody>
      </table>
    </div>
  );
}