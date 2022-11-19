/**
 * An Action button component
 * @param {function} onClick Function to call on click
 * @param {string} text Text to show on the button
 * @return {JSX.Element}
 * @constructor
 */
export function ActionButton({ onClick, text }) {
  return (
    <button className="action-button" type="submit" onClick={onClick}>
      {text}
    </button>
  );
}
