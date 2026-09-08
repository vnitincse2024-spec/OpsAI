import React from 'react';
import { Link } from 'react-router-dom';

const Sidebar: React.FC = () => {
  return (
    <nav className="w-64 bg-emerald-900/20 backdrop-blur-xs p-4 border-r border-emerald-500/30">
      <ul className="space-y-4">
        <li>
          <Link to="/" className="block text-emerald-300 hover:text-emerald-100">
            Dashboard
          </Link>
        </li>
        <li>
          <Link to="/incidents" className="block text-emerald-300 hover:text-emerald-100">
            Incidents
          </Link>
        </li>
        <li>
          <Link to="/settings" className="block text-emerald-300 hover:text-emerald-100">
            Settings
          </Link>
        </li>
      </ul>
    </nav>
  );
};

export default Sidebar;
