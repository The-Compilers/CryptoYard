import { useParams } from "react-router-dom";

import "./dashboard.css";

import Nav from "../../components/nav/Nav";
import Footer from "../../components/footer/Footer";
import Table from "../../components/table/Table";

function Dashboard() {
  const { username } = useParams();
  const tableHeaders = ["Coin", "Balance", "Profit", "Price", "24h%", "7h%"];
  const tmpCoints = [
    ["BitCoin", "134,708.65", "+87,108.42", "47,600.23", "-0.82", "+11.27"],
    ["Ethereum", "4,149.48", "-952.34", "3,401.21", "+0.08", "+13.13"],
    ["Tether", "53.45", "+3.45", "1.00", "-0.00", "-0.00"],
    ["USD Coin", "154.81", "-83.05", "0.9999", "-0.03", "+0.02"],
  ];

  return (
    <>
      <Nav username={username} />
      <main>
        <Table tableHeaders={tableHeaders} rows={tmpCoints} />
      </main>
      <Footer />
    </>
  );
}

export default Dashboard;
