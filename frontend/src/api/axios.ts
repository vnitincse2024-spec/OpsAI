import axios from 'axios';

const api = axios.create({
  baseURL: '/api', // Vite proxy can be set later, for now relative
});

// Add a request interceptor to include JWT if present
api.interceptors.request.use((config: any) => {
  const token = localStorage.getItem('access_token');
  if (token) {
    // Ensure headers object exists and add Authorization header
    config.headers = config.headers || {};
    (config.headers as any).Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
