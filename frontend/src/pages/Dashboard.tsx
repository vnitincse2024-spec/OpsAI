import React from 'react';

const Dashboard: React.FC = () => {
  return (
    <section className="p-8">
      <h1 className="text-4xl font-bold text-emerald-300 mb-4">OpsAI Dashboard</h1>
      <p className="text-gray-200 max-w-2xl">
        Welcome to OpsAI – an AI‑powered incident management platform. Use the sidebar to explore incidents, view analytics, or adjust settings.
      </p>
    </section>
  );
};

export default Dashboard;
