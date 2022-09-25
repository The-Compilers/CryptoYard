/**
 * Displays an error message in a form
 * @param {string}e rror Error message to show
 * @return {JSX.Element|null}
 * @constructor
 */
export function FormErrorMessage({error}) {
  if (error !== "") {
    return <p className="fullscreen-form__error">{error}</p>;
  } else {
    return null;
  }
}
