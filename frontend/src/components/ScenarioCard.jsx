/**
 * ScenarioCard.jsx — Displays a card for one test scenario.
 *
 * REACT CONCEPT — Conditional Styling:
 *   The card dynamically changes its badge color based on the test status:
 *     passed  → green (Bootstrap "success")
 *     failed  → red   (Bootstrap "danger")
 *     skipped → grey  (Bootstrap "secondary")
 *
 *   Template literals (`backtick strings`) let you embed variables
 *   inside strings: `text-bg-${color}` → "text-bg-success"
 */
import React from "react";

// Maps status strings to Bootstrap color classes
const statusColor = {
  passed: "success",
  failed: "danger",
  skipped: "secondary",
};

export default function ScenarioCard({ scenario }) {
  // Look up the color, default to "secondary" (grey) for unknown statuses
  const color = statusColor[scenario.status] || "secondary";

  return (
    <div className="card h-100 shadow-sm">
      <div className="card-body">
        <h5 className="card-title">{scenario.name}</h5>
        <p className="card-text text-muted mb-2">{scenario.module}</p>
        <span className={`badge text-bg-${color} text-uppercase`}>{scenario.status}</span>
        <p className="mt-2 mb-0 small">Duration: {scenario.durationMs} ms</p>
      </div>
    </div>
  );
}
