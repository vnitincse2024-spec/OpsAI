import React from 'react';
import { Link } from 'react-router-dom';

const Header: React.FC = () => {
  return (
    <header className="flex items-center justify-between bg-emerald-900/30 backdrop-blur-xs p-4 border-b border-emerald-500/30">
      <h1 className="text-2xl font-bold text-emerald-200">
        OpsAI Dashboard
      </h1>
      <nav>
        <Link to="/login" className="text-emerald-300 hover:text-emerald-100">
          Login
        </Link>
      </nav>
    </header>
  );
};

export default Header;
