import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface Incident {
  id: string;
  title: string;
  severity: 'Critical' | 'High' | 'Medium' | 'Low';
  service: string;
  status: 'Investigating' | 'Mitigating' | 'Resolved' | 'Open';
}

const sampleIncidents: Incident[] = [
  { id: 'INC-2048', title: 'Payment API Failure', severity: 'Critical', service: 'Backend', status: 'Investigating' },
  { id: 'INC-2047', title: 'Database Connection Timeout', severity: 'High', service: 'Database', status: 'Mitigating' },
  { id: 'INC-2046', title: 'Authentication Latency', severity: 'Medium', service: 'Auth', status: 'Investigating' },
  { id: 'INC-2045', title: 'Redis Connection Failure', severity: 'High', service: 'Infrastructure', status: 'Open' },
  { id: 'INC-2044', title: 'Deployment Failure', severity: 'Low', service: 'DevOps', status: 'Open' },
];

const IncidentsPage: React.FC = () => {
  const [search, setSearch] = useState('');
  const [severityFilter, setSeverityFilter] = useState('');
  const [statusFilter, setStatusFilter] = useState('');
  const [teamFilter, setTeamFilter] = useState('');
  const navigate = useNavigate();

  const filtered = sampleIncidents.filter((inc) => {
    const matchesSearch = inc.title.toLowerCase().includes(search.toLowerCase()) || inc.id.toLowerCase().includes(search.toLowerCase());
    const matchesSeverity = severityFilter ? inc.severity === severityFilter : true;
    const matchesStatus = statusFilter ? inc.status === statusFilter : true;
    const matchesTeam = teamFilter ? inc.service === teamFilter : true;
    return matchesSearch && matchesSeverity && matchesStatus && matchesTeam;
  });

  return (
    <section className="p-8 min-h-screen bg-darkbg text-gray-100">
      <h1 className="text-5xl font-bold text-emerald-400 mb-2">Incidents</h1>
      <p className="text-lg mb-8 text-gray-300">Monitor, investigate and resolve active IT incidents.</p>
      {/* Top metrics */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
        <div className="bg-emerald-900/30 backdrop-blur-xs p-4 rounded-lg shadow-glass border border-emerald-500/30">
          <div className="text-2xl font-semibold text-emerald-300">{sampleIncidents.length}</div>
          <div className="text-gray-300">Active Incidents</div>
        </div>
        <div className="bg-emerald-900/30 backdrop-blur-xs p-4 rounded-lg shadow-glass border border-emerald-500/30">
          <div className="text-2xl font-semibold text-emerald-300">{sampleIncidents.filter(i => i.severity === 'Critical').length}</div>
          <div className="text-gray-300">Critical</div>
        </div>
        <div className="bg-emerald-900/30 backdrop-blur-xs p-4 rounded-lg shadow-glass border border-emerald-500/30">
          <div className="text-2xl font-semibold text-emerald-300">{sampleIncidents.filter(i => i.status === 'Open').length}</div>
          <div className="text-gray-300">SLA At Risk</div>
        </div>
        <div className="bg-emerald-900/30 backdrop-blur-xs p-4 rounded-lg shadow-glass border border-emerald-500/30">
          <div className="text-2xl font-semibold text-emerald-300">{sampleIncidents.filter(i => i.status === 'Resolved').length}</div>
          <div className="text-gray-300">Resolved Today</div>
        </div>
      </div>

      {/* Filters */}
      <div className="flex flex-wrap gap-4 mb-4">
        <input
          type="text"
          placeholder="Search…"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="bg-emerald-900/30 backdrop-blur-xs px-4 py-2 rounded-lg border border-emerald-500/30 text-gray-100 focus:outline-none"
        />
        <select
          value={severityFilter}
          onChange={(e) => setSeverityFilter(e.target.value)}
          className="bg-emerald-900/30 backdrop-blur-xs px-4 py-2 rounded-lg border border-emerald-500/30 text-gray-100"
        >
          <option value="">All Severities</option>
          <option value="Critical">Critical</option>
          <option value="High">High</option>
          <option value="Medium">Medium</option>
          <option value="Low">Low</option>
        </select>
        <select
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
          className="bg-emerald-900/30 backdrop-blur-xs px-4 py-2 rounded-lg border border-emerald-500/30 text-gray-100"
        >
          <option value="">All Statuses</option>
          <option value="Investigating">Investigating</option>
          <option value="Mitigating">Mitigating</option>
          <option value="Resolved">Resolved</option>
          <option value="Open">Open</option>
        </select>
        <select
          value={teamFilter}
          onChange={(e) => setTeamFilter(e.target.value)}
          className="bg-emerald-900/30 backdrop-blur-xs px-4 py-2 rounded-lg border border-emerald-500/30 text-gray-100"
        >
          <option value="">All Teams</option>
          <option value="Backend">Backend</option>
          <option value="Database">Database</option>
          <option value="Auth">Auth</option>
          <option value="Infrastructure">Infrastructure</option>
          <option value="DevOps">DevOps</option>
        </select>
      </div>

      {/* Incident Table */}
      <div className="overflow-x-auto rounded-lg shadow-glass border border-emerald-500/30 bg-emerald-900/20 backdrop-blur-xs">
        <table className="w-full text-left table-auto">
          <thead className="bg-emerald-800/30">
            <tr>
              <th className="px-4 py-2 cursor-pointer" onClick={() => {}}
              >ID</th>
              <th className="px-4 py-2 cursor-pointer" onClick={() => {}}
              >Title</th>
              <th className="px-4 py-2 cursor-pointer" onClick={() => {}}
              >Severity</th>
              <th className="px-4 py-2 cursor-pointer" onClick={() => {}}
              >Service</th>
              <th className="px-4 py-2 cursor-pointer" onClick={() => {}}
              >Status</th>
            </tr>
          </thead>
          <tbody>
            {filtered.map((inc) => (
              <tr
                key={inc.id}
                className="hover:bg-emerald-800/30 cursor-pointer"
                onClick={() => navigate(`/incidents/${inc.id}`)}
              >
                <td className="px-4 py-2">{inc.id}</td>
                <td className="px-4 py-2">{inc.title}</td>
                <td className="px-4 py-2 text-{inc.severity === 'Critical' ? 'red' : inc.severity === 'High' ? 'orange' : inc.severity === 'Medium' ? 'yellow' : 'green'}-500">
                  {inc.severity}
                </td>
                <td className="px-4 py-2">{inc.service}</td>
                <td className="px-4 py-2">{inc.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  );
};

export default IncidentsPage;
