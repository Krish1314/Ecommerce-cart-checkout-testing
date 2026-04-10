/**
 * App.jsx — Main component of the React dashboard.
 *
 * WHAT THIS DOES:
 *   Reads the Cucumber JSON test report and displays it as a visual dashboard
 *   with summary cards, a bar chart, and individual scenario cards.
 *
 * KEY REACT CONCEPTS USED:
 *   useState  → stores data that can change (the report data)
 *   useEffect → runs code when the component first loads (fetches the report)
 *   useMemo   → caches computed values to avoid recalculating on every render
 */
import React, { useEffect, useMemo, useState } from "react";
import TestSummary from "./components/TestSummary";
import ScenarioCard from "./components/ScenarioCard";
import ResultChart from "./components/ResultChart";

/**
 * Determines the overall status of a scenario by checking all its steps.
 * If ANY step failed → scenario failed
 * If ALL steps were skipped → scenario skipped
 * Otherwise → scenario passed
 */
function extractScenarioStatus(scenario) {
  const statuses = (scenario.steps || []).map((s) => s.result?.status || "skipped");
  if (statuses.includes("failed")) return "failed";
  if (statuses.every((s) => s === "skipped")) return "skipped";
  return "passed";
}

/**
 * Calculates total scenario duration by summing all step durations.
 * Cucumber reports time in nanoseconds, so we convert to milliseconds.
 */
function scenarioDurationMs(scenario) {
  const ns = (scenario.steps || []).reduce((sum, step) => sum + (step.result?.duration || 0), 0);
  return Math.round(ns / 1_000_000);
}

export default function App() {
  // State to hold the parsed Cucumber report data
  const [report, setReport] = useState([]);

  // useEffect with [] runs ONCE when the component mounts (page loads)
  // Fetches the JSON report file from the public/ folder
  useEffect(() => {
    fetch("/cucumber-report.json")
      .then((res) => res.json())
      .then((json) => setReport(Array.isArray(json) ? json : []))
      .catch(() => setReport([])); // gracefully handle missing/invalid report
  }, []);

  // useMemo transforms the raw report into a flat list of scenario objects
  // Only recalculates when 'report' changes (not on every render)
  const scenarios = useMemo(() => {
    return report.flatMap((feature) =>
      (feature.elements || []).map((scenario) => ({
        module: feature.name,          // feature name (e.g. "User Login on SauceDemo")
        name: scenario.name,           // scenario name (e.g. "Successful login")
        status: extractScenarioStatus(scenario),
        durationMs: scenarioDurationMs(scenario),
      }))
    );
  }, [report]);

  // Compute summary counts: total, passed, failed, skipped
  const summary = useMemo(() => {
    return scenarios.reduce(
      (acc, s) => {
        acc.total += 1;
        acc[s.status] += 1;
        return acc;
      },
      { total: 0, passed: 0, failed: 0, skipped: 0 }
    );
  }, [scenarios]);

  return (
    <div className="container py-4">
      <h1 className="mb-3">E-Commerce BDD Test Dashboard</h1>
      <p className="text-muted">
        Visual status view for Cucumber JSON output from SauceDemo checkout flow tests.
      </p>

      {/* Summary badges showing total/passed/failed/skipped counts */}
      <TestSummary summary={summary} />

      {/* Bar chart showing pass/fail breakdown by feature module */}
      <ResultChart scenarios={scenarios} />

      {/* Individual cards for each test scenario */}
      <h2 className="h4 mt-4 mb-3">Scenario Status</h2>
      <div className="row g-3">
        {scenarios.map((scenario) => (
          <div className="col-md-6 col-lg-4" key={`${scenario.module}-${scenario.name}`}>
            <ScenarioCard scenario={scenario} />
          </div>
        ))}
      </div>
    </div>
  );
}
