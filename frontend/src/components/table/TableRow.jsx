/**
 * Takes in a list of string values that will be displayed in the row,
 * (one string value for each column in the row).
 * The order of the list should be the order the values should be displayed in
 * the row from left to right.
 * Each value will be displayed in its separate column.
 * If a value can be enterpreted as a number with a pluss or a minus
 * symbol in front, it will be given a styling based on if its positiv
 * or negative.
 * @param {*} rowIndex, the index of the row
 * @param {*} values, the values that is to be displayed in the row
 * @param {*} togglable, true if the row should be togglable, false otherwise. When
 * a row is togglable, a checkbox is added at the end of the row
 * @param {*} toggleFunction, the function to be called when the checkbox is toggled.
 * The function take in a param with the index of the row.
 */
function TableRow({ rowIndex, values, toggleable, toggleFunction }) {
  /* Regex for testing if a string is a number and starts with either
  a pluss or a minus */
  const reg = /^[+-](?:\d{1,3}(?:,\d{3})*|\d+)(?:\.\d+)?$/;

  function handleToggle(event) {
    toggleFunction(event.target.value);
  }

  return toggleable ? (
    <tr className="table__row">
      {values.map((value, index) => (
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
      ))}
      <td>
        <input type="checkbox" onChange={handleToggle} value={rowIndex} />
      </td>
    </tr>
  ) : (
    <tr className="table__row">
      {values.map((value, index) => (
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
      ))}
    </tr>
  );
}

export default TableRow;
