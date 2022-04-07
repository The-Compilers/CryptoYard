import Chart from "./Chart";
import "./chart.css";

function ChartBox() {
  const navItems = document.querySelectorAll("[data-chart-nav-item]");

  /**
   * Change the active nav item to the one that is clicked
   *
   * @param {*} event The event that is triggered when the nav
   * item is clicked
   */
  function changeActive(event) {
    navItems.forEach((item) => {
      item.classList.remove("chart-nav__item--active");
    });
    event.target.classList.add("chart-nav__item--active");
    // TODO: Update graph labels and data
  }

  return (
    <div className="chart-box box">
      <nav className="chart-nav">
        <ul className="chart-nav__list">
          <li
            className="chart-nav__item"
            data-chart-nav-item
            onClick={changeActive}
          >
            Year
          </li>
          <li
            className="chart-nav__item chart-nav__item--active"
            data-chart-nav-item
            onClick={changeActive}
          >
            Month
          </li>
          <li
            className="chart-nav__item"
            data-chart-nav-item
            onClick={changeActive}
          >
            Day
          </li>
        </ul>
      </nav>
      <Chart />
    </div>
  );
}

export default ChartBox;
