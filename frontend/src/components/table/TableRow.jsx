/**
 * Takes in a list of string values that will be displayed in the row,
 * (one string value for each column in the row).
 * The order of the list should be the order the values should be displayed in
 * the row from left to right.
 * Each value will be displayed in its separate column.
 * If a value can be enterpreted as a number with a pluss or a minus
 * symbol in front, it will be given a styling based on if its positiv
 * or negative.
 * @param {*} values the values that is to be displayed in the row
 */
function TableRow({ values }) {
  const CHECKBOX = "checkbox";
  /* Regex for testing if a string is a number and starts with either
  a pluss or a minus */
  const reg = /^[+-](?:\d{1,3}(?:,\d{3})*|\d+)(?:\.\d+)?$/;

  function toggleCoinSubscription(event) {
    // TODO: ...
  }

  return (
    <tr className="table__row">
      {values.map((value, index) =>
        value === CHECKBOX ? (
          <td key={index}>
            <input type="checkbox" onClick={toggleCoinSubscription} />
          </td>
        ) : (
          <td
            key={index}
            className={
              "table__column" +
              " " +
              (index === 0 ? "" : "table__column--align-right ") +
              (reg.test(value)
                ? parseFloat(value) > 0
                  ? "success"
                  : "error"
                : "")
            }
          >
            {value}
          </td>
        )
      )}
    </tr>
  );
}

export default TableRow;
