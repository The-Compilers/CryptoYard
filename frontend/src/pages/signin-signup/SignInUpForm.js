import "../../styles/fullscreen-forms.css"
import {useState} from "react";

/**
 * A form that is used for both sign-in and sign-up
 * @param isSignIn - when true, show Sign-In form, otherwise - Sign-Up form
 * @param onSubmit - callback function called when the submission button is clicked
 * @constructor
 */
export function SignInUpForm({isSignIn, onSubmit}) {
  let formTitle;
  let emailInput = null;
  let repeatPasswordInput = null;
  let submitButtonTitle;
  let alternativeDescription;
  let alternativeLinkText;
  let alternativeUrl;
  let usernamePlaceholder;
  let [email, setEmail] = useState("");
  let [password, setPassword] = useState("");

  if (isSignIn) {
    formTitle = "Sign in";
    usernamePlaceholder = "Username or email";
    submitButtonTitle = "Sign in";
    alternativeDescription = "Don't have an account?";
    alternativeLinkText = "Sign up";
    alternativeUrl = "/signup";
  } else {
    formTitle = "Sign up";
    usernamePlaceholder = "Username";
    emailInput = <input className="fullscreen-form__input" type="text" placeholder="Email"/>;
    repeatPasswordInput = <input className="fullscreen-form__input" type="text" placeholder="Repeat Password"/>;
    submitButtonTitle = "Create Account";
    alternativeDescription = "Already have an account?";
    alternativeLinkText = "Sign in";
    alternativeUrl = "/signin";
  }

  const ALPHA_NUMERIC_REGEX = /^[a-zA-Z0-9_]+$/;

  /**
   * Return true if the username/email field is valid (is not empty and does not contain non-allowed characters)
   * @return {boolean}
   */
  function isUsernameValid() {
    return "" !== email && email.match(ALPHA_NUMERIC_REGEX);
  }

  /**
   * Returns true if current password value meets the minimums security requirements
   * @return {boolean}
   */
  function isPasswordValid() {
    // Regex from https://www.w3resource.com/javascript/form/password-validation.php
    const LOWERCASE_UPPERCASE_DIGITS__6_TO_20_CHARS = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
    return !!password.match(LOWERCASE_UPPERCASE_DIGITS__6_TO_20_CHARS);
  }

  let submitButtonEnabled = !isSignIn || (isUsernameValid() && isPasswordValid());

  function handleSubmit(event) {
    event.preventDefault();
    if (onSubmit) {
      onSubmit();
    }
  }

  return <main className="fullscreen-form__background">
    <form className="fullscreen-form box">
      <h1 className="fullscreen-form__header">{formTitle}</h1>
      <input className="fullscreen-form__input" type="text" placeholder={usernamePlaceholder} value={email}
             onChange={(e) => setEmail(e.target.value)}/>
      {emailInput}
      <input className="fullscreen-form__input" type="password" placeholder="Password" value={password}
             onChange={(e) => setPassword(e.target.value)}/>
      {repeatPasswordInput}
      <button className="fullscreen-form__action-button" type="submit" disabled={!submitButtonEnabled}
              onClick={handleSubmit}>
        {submitButtonTitle}</button>
      <div className="fullscreen-form__footer">
        <p>{alternativeDescription}</p>
        <a href={alternativeUrl} className="fullscreen-form__anchor">
          {alternativeLinkText}
        </a>
      </div>
    </form>
    <div className={"sign-in-footer"}>
    </div>
  </main>;
}
