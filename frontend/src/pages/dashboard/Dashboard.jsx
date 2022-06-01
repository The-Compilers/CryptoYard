import "./dashboard.css";

import Nav from "../../components/nav/Nav";
import Footer from "../../components/footer/Footer";
import Table from "../../components/table/Table";

function Dashboard({ user }) {
  const tableHeaders = ["Coin", "Balance", "Profit", "Price", "24h%", "7h%"];
  const tmpCoins = [
    ["BitCoin", "$134,708.65", "+87,108.42", "$47,600.23", "-0.82", "+11.27"],
    ["Ethereum", "$4,149.48", "-952.34", "$3,401.21", "+0.08", "+13.13"],
    ["Tether", "$53.45", "+3.45", "$1.00", "-0.00", "-0.00"],
    ["USD Coin", "$154.81", "-83.05", "$0.9999", "-0.03", "+0.02"],
  ];

  return (
    <>
      <Nav user={user} />
      <main>
        <div className="box">
          <h2 className="box__title">Your coins</h2>
          <Table tableHeaders={tableHeaders} rows={tmpCoins} />
        </div>
      </main>
      <Footer />
    </>
  );
}

export default Dashboard;
