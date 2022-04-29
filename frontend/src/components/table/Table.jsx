import "./table.css";

import Row from "./TableRow";

function CryptoTable() {
  return (
    <table className="box">
      <thead>
        <tr className="table__row">
          <th className="table__column table__column--title">Coin</th>
          <th className="table__column table__column--title table__colum--big table__column--align-right">
            Balance
          </th>
          <th className="table__column table__column--title table__column--align-right">
            Profit
          </th>
          <th className="table__column table__column--title table__column--align-right">
            Price
          </th>
          <th className="table__column table__column--title table__column--align-right">
            24h%
          </th>
          <th className="table__column table__column--title table__column--align-right">
            7h%
          </th>
        </tr>
      </thead>
      <tbody>
        <Row
          coin="BitCoin"
          balance="134,708.65"
          profit="+87,108.42"
          price="47,600.23"
          lastDay="-0.82"
          lastSevenHours="+11.27"
        />
        <Row
          coin="Ethereum"
          balance="4,149.48"
          profit="-952.34"
          price="3,401.21"
          lastDay="+0.08"
          lastSevenHours="+13.13"
        />
        <Row
          coin="Tether"
          balance="53.45"
          profit="+3.45"
          price="1.00"
          lastDay="0.00"
          lastSevenHours="0.00"
        />
        <Row
          coin="USD Coin"
          balance="154.81"
          profit="-83.05"
          price="0.9999"
          lastDay="-0.03"
          lastSevenHours="+0.02"
        />
      </tbody>
    </table>
  );
}

export default CryptoTable;
