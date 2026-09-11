import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';


const LoginPage: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    // Mock login – in a real app you'd call the backend auth endpoint.
    // Here we just store a dummy token and redirect.
    const dummyToken = 'mock-token-' + Date.now();
    localStorage.setItem('access_token', dummyToken);
    navigate('/');
  };

  return (
    <section className="flex items-center justify-center min-h-screen bg-darkbg text-gray-100">
      <div className="bg-emerald-900/30 backdrop-blur-xs p-8 rounded-lg shadow-glass border border-emerald-500/30 w-full max-w-md">
        <h1 className="text-3xl font-bold text-emerald-400 mb-6 text-center">OpsAI Login</h1>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="w-full bg-emerald-900/30 backdrop-blur-xs px-4 py-2 rounded-lg border border-emerald-500/30 text-gray-100 focus:outline-none"
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="w-full bg-emerald-900/30 backdrop-blur-xs px-4 py-2 rounded-lg border border-emerald-500/30 text-gray-100 focus:outline-none"
          />
          <button
            type="submit"
            className="w-full bg-emerald-500 hover:bg-emerald-600 text-white font-semibold py-2 px-4 rounded-lg transition-colors"
          >
            Sign In
          </button>
        </form>
      </div>
    </section>
  );
};

export default LoginPage;
