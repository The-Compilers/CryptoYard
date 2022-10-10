import "./table.css";

import Row from "./TableRow";

/**
 * Creates a table where the header for each column are the ones specified
 * by passing in an array of strings to tableHeaders. The rows displayed
 * are the ones specified by passing in an array of rows which again are an
 * array of values to be displayed in the rows.
 * @param {*} title, the title of the table
 * @param {*} tableHeaders an array containing string values for the headers in
 * the table. The order of the array should be the order the header should be
 * displayed from left to right.
 * @param {*} rows an array of rows. A row is another array with string values
 * for each column in that row. The rows are displayed from top down, meaning the
 * first row in the array is placed on the top of the table (under the headers).
 * @param {*} toggleable, a condition stating if the rows in the table should be togglabe
 * or not. A toggleable row gets a checkbox added at the end.
 * @param {*} toggleFunction, a function to be called when the row is toggled. Null if
 * row is not toggleable
 */
function CryptoTable({
  title,
  tableHeaders,
  rows,
  toggleable,
  toggleFunction,
}) {
  return (
    <div className="box">
      <h2 className="box__title">{title}</h2>
      <table>
        <thead>
          <tr className="table__row">
            {tableHeaders.map((header, index) => (
              <th
                key={index}
                className={
                  "table__column table__column--title " +
                  (index > 0 ? "table__column--align-right" : "")
                }
              >
                {header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {rows.map((row, index) => (
            <Row
              key={index}
              coinIndex={index}
              values={row}
              toggleable={toggleable}
              toggleFunction={toggleFunction}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default CryptoTable;
