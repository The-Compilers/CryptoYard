function CryptoTableRow({
  coin,
  balance,
  profit,
  price,
  lastDay,
  lastSevenHours,
}) {
  return (
    <tr className="table__row">
      <td className="table__column">{coin}</td>
      <td className="table__column table__column--align-right">${balance}</td>
      <td
        className={
          "table__column table__column--align-right " +
          (parseFloat(profit) > 0 ? "success" : "error")
        }
      >
        {profit.charAt(0) + "$" + profit.substring(1)}
      </td>
      <td className="table__column table__column--align-right">${price}</td>
      <td
        className={
          "table__column table__column--align-right " +
          (parseFloat(lastDay) > 0 ? "success" : "error")
        }
      >
        {lastDay}%
      </td>
      <td
        className={
          "table__column table__column--align-right " +
          (parseFloat(lastSevenHours) > 0 ? "success" : "error")
        }
      >
        {lastSevenHours}%
      </td>
    </tr>
  );
}

export default CryptoTableRow;
