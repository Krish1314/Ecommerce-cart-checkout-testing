/**
 * TestSummary.jsx — Displays summary badges for test results.
 *
 * REACT CONCEPT — Props:
 *   This component receives a 'summary' prop from App.jsx containing:
 *   { total: 10, passed: 8, failed: 1, skipped: 1 }
 *
 *   Props are read-only data passed from a parent component to a child.
 *   The { summary } syntax is called "destructuring" — it extracts the
 *   'summary' property from the props object.
 */
import React from "react";

export default function TestSummary({ summary }) {
  return (
    <div className="card shadow-sm mb-4">
      {/* Bootstrap badge classes: text-bg-primary (blue), text-bg-success (green), etc. */}
      <div className="card-body d-flex flex-wrap gap-3">
        <span className="badge text-bg-primary p-2">Total: {summary.total}</span>
        <span className="badge text-bg-success p-2">Passed: {summary.passed}</span>
        <span className="badge text-bg-danger p-2">Failed: {summary.failed}</span>
        <span className="badge text-bg-secondary p-2">Skipped: {summary.skipped}</span>
      </div>
    </div>
  );
}
