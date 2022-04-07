import Chart from "./Chart";
import "./chart.css";

function ChartBox() {
  return (
    <div className="chart-box box">
      <nav>
        <ul>
          <li>Year</li>
          <li>Month</li>
          <li>Day</li>
        </ul>
      </nav>
      <Chart />
    </div>
  );
}

export default ChartBox;
