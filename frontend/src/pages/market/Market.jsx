// components
import Nav from "../../components/nav/Nav";
import Table from "../../components/table/Table";

export default function Market({ user }) {
  const tableHeaders = [
    "Name",
    "Price",
    "24h Change (%)",
    "24h Volume",
    "Market Cap",
  ];
  const tmpCoins = [
    [
      "BTC Bitcoin",
      "$29,628.42",
      "-6.72",
      "35,911.22M",
      "$564,910.68M",
      "checkbox",
    ],
    [
      "ETH Ethereum",
      "$1,801.12",
      "-7.57",
      "18,424.01M",
      "$218,099.98M",
      "checkbox",
    ],
  ];

  return (
    <>
      <Nav user={user} />
      <main>
        <div className="box">
          <h2 className="box__title">All coins</h2>
          <Table tableHeaders={tableHeaders} rows={tmpCoins} />
        </div>
      </main>
    </>
  );
}
