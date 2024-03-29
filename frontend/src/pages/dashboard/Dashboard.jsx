import Footer from "../../components/footer/Footer";
import Table from "../../components/table/Table";
import { UserContext } from "../../state/UserContext";
import { useContext } from "react";

/**
 * Dashboard page for a logged-in user.
 * @returns {JSX.Element}
 * @constructor
 */
function Dashboard() {
  const user = useContext(UserContext);
  const tableHeaders = ["Coin", "Balance", "Profit", "Price", "24h%", "7h%"];
  const tmpCoins = [
    ["BitCoin", "$134,708.65", "+87,108.42", "$47,600.23", "-0.82", "+11.27"],
    ["Ethereum", "$4,149.48", "-952.34", "$3,401.21", "+0.08", "+13.13"],
    ["Tether", "$53.45", "+3.45", "$1.00", "-0.00", "-0.00"],
    ["USD Coin", "$154.81", "-83.05", "$0.9999", "-0.03", "+0.02"],
  ];

  return (
    <>
      <main>
        <Table
          title={`Coins for ${user.username}`}
          tableHeaders={tableHeaders}
          rows={tmpCoins}
          toggleable={false}
        />
      </main>
      <Footer />
    </>
  );
}

export default Dashboard;
