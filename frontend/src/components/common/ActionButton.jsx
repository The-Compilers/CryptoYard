/**
 * An Action button component
 * @param {function} onClick Function to call on click
 * @param {string} text Text to show on the button
 * @param {boolean} disabled When true, the button is disabled
 * @return {JSX.Element}
 * @constructor
 */
export function ActionButton({ onClick, text, disabled }) {
  return (
    <button
      className="action-button"
      type="submit"
      onClick={onClick}
      disabled={disabled}
    >
      {text}
    </button>
  );
}
