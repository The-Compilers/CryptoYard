// components
import Nav from "../../components/nav/Nav";
import Table from "../../components/table/Table";

/**
 * A page showing market data
 * @param user
 * @returns {JSX.Element}
 * @constructor
 */
export default function Market({ user }) {
  const tableHeaders = [
    "Name",
    "Price",
    "24h Change (%)",
    "24h Volume",
    "Market Cap",
  ];
  const tmpCoins = [
    ["BTC Bitcoin", "$29,628.42", "-6.72", "35,911.22M", "$564,910.68M"],
    ["ETH Ethereum", "$1,801.12", "-7.57", "18,424.01M", "$218,099.98M"],
  ];

  function toggleSubscription(coin) {
    console.log("Toggling subscription " + coin);
  }

  return (
    <>
      <Nav user={user} />
      <main>
        <Table
          title="All coins"
          tableHeaders={tableHeaders}
          rows={tmpCoins}
          toggleable={true}
          toggleFunction={toggleSubscription}
        />
      </main>
    </>
  );
}
