/**
 * ResultChart.jsx — Bar chart showing pass/fail/skipped count per feature module.
 *
 * Uses the Recharts library (a popular React charting library).
 *
 * DATA TRANSFORMATION:
 *   Input:  flat list of scenarios [{ module: "Login", status: "passed" }, ...]
 *   Output: grouped data [{ module: "Login", passed: 3, failed: 1, skipped: 0 }]
 *
 *   useMemo caches this transformation so it only runs when scenarios change.
 */
import React, { useMemo } from "react";
import { Bar, BarChart, CartesianGrid, Legend, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";

export default function ResultChart({ scenarios }) {
  // Group scenarios by module and count pass/fail/skipped per module
  const chartData = useMemo(() => {
    const grouped = {};
    scenarios.forEach((scenario) => {
      if (!grouped[scenario.module]) {
        grouped[scenario.module] = { module: scenario.module, passed: 0, failed: 0, skipped: 0 };
      }
      grouped[scenario.module][scenario.status] += 1;
    });
    return Object.values(grouped);
  }, [scenarios]);

  return (
    <div className="card shadow-sm mb-4">
      <div className="card-body">
        <h2 className="h5">Module-wise Pass/Fail Breakdown</h2>
        <div style={{ width: "100%", height: 300 }}>
          {/* ResponsiveContainer makes the chart resize with the browser window */}
          <ResponsiveContainer>
            <BarChart data={chartData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="module" />
              <YAxis allowDecimals={false} />
              <Tooltip />
              <Legend />
              {/* Each Bar component represents one data series with a color */}
              <Bar dataKey="passed" fill="#198754" />
              <Bar dataKey="failed" fill="#dc3545" />
              <Bar dataKey="skipped" fill="#6c757d" />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
}
