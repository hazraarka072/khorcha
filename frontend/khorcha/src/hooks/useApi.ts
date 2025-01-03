import { useState } from 'react';
import { ApiClient } from '../services/ApiClient';

const api = new ApiClient({
  baseURL: import.meta.env.VITE_API_BASE_URL,
});

export function useApi() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  async function fetchData<T>(
    endpoint: string,
    method: 'get' | 'post' = 'get',
    data?: any
  ): Promise<T | null> {
    setLoading(true);
    setError(null);

    try {
      const result = method === 'get'
        ? await api.get<T>(endpoint)
        : await api.post<T>(endpoint, data);
      return result;
    } catch (err) {
      setError(err instanceof Error ? err : new Error('An error occurred'));
      return null;
    } finally {
      setLoading(false);
    }
  }

  return { fetchData, loading, error };
}
