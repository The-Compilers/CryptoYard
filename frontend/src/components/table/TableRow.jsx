import { useState, useEffect } from "react";

function CryptoTableRow({
  coin,
  balance,
  profit,
  price,
  lastDay,
  lastSevenHours,
}) {
  /**
   * UseStates describing the state of the current value, wether it
   * positiv or negative. If positiv, state is "success", if negative
   * state if "error". States are appliied as css classes to display
   * correct color
   */
  const [profitState, setProfitState] = useState("");
  const [lastDayState, setLastDayState] = useState("");
  const [lastSevenHoursState, setLastSevenHoursState] = useState("");

  /**
   * Checks the values if they are positiv or negative.
   * If positiv set color of text to green, if negative
   * set color of text to red.
   * @param {*} value the value to check
   * @param {*} updater the useState needed to be updated to
   * apply class to correct row with correct color
   */
  function checkValue(value, updater) {
    const firstLetter = value.charAt(0);
    if (firstLetter == "+") {
      updater("success");
    } else if (firstLetter == "-") {
      updater("error");
    } else {
      updater("");
    }
  }

  useEffect(() => {
    checkValue(profit, setProfitState);
    checkValue(lastDay, setLastDayState);
    checkValue(lastSevenHours, setLastSevenHoursState);
  });

  return (
    <tr className="table__row">
      <td className="table__column">{coin}</td>
      <td className="table__column table__column--align-right">${balance}</td>
      <td className={"table__column table__column--align-right " + profitState}>
        {profit.charAt(0) + "$" + profit.substring(1)}
      </td>
      <td className="table__column table__column--align-right">${price}</td>
      <td
        className={"table__column table__column--align-right " + lastDayState}
      >
        {lastDay}%
      </td>
      <td
        className={
          "table__column table__column--align-right " + lastSevenHoursState
        }
      >
        {lastSevenHours}%
      </td>
    </tr>
  );
}

export default CryptoTableRow;
