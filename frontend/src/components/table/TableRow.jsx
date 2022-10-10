/**
 * Takes in a list of string values that will be displayed in the row,
 * (one string value for each column in the row).
 * The order of the list should be the order the values should be displayed in
 * the row from left to right.
 * Each value will be displayed in its separate column.
 * If a value can be interpreted as a number with a plus or a minus
 * symbol in front, it will be given a styling based on if its positive
 * or negative.
 * @param {*} rowIndex, the index of the row
 * @param {*} values, the values that is to be displayed in the row
 * @param {*} toggleable, true if the row should be toggleable, false otherwise. When
 * a row is toggleable, a checkbox is added at the end of the row
 * @param {*} toggleFunction, the function to be called when the checkbox is toggled.
 * The function take in a param with the index of the row.
 */
function TableRow({ rowIndex, values, toggleable, toggleFunction }) {
  /* Regex for testing if a string is a number and starts with either
  a plus or a minus */
  const numberRegex = /^[+-](?:\d{1,3}(?:,\d{3})*|\d+)(?:\.\d+)?$/;

  function handleToggle(event) {
    toggleFunction(event.target.value);
  }

  /**
   * Create the cells showing values in the columns
   * @returns {Array[JSX.Element]}
   */
  function createValueCells() {
    return values.map((value, index) => (
      <td
        key={index}
        className={
          "table__column" +
          " " +
          (index === 0 ? "" : "table__column--align-right ") +
          (numberRegex.test(value)
              ? parseFloat(value) > 0 ? "success" : "error"
              : ""
          )
        }
      >
        {value}
      </td>
    ));
  }

  /**
   * Create a cell showing the toggling-checkbox
   * @returns {JSX.Element}
   */
  function createToggleCell() {
    return <td>
      <input type="checkbox" onChange={handleToggle} value={rowIndex} />
    </td>;
  }

  return <tr className="table__row">
    {createValueCells()}
    {toggleable ? createToggleCell() : <></>}
  </tr>;
}

export default TableRow;
