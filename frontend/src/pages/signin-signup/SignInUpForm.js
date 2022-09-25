import "../../styles/fullscreen-forms.css"
import {useState} from "react";
import {FormErrorMessage} from "../../components/sign-inup-form/FormErrorMessage";
import {sendAuthenticationRequest} from "../../services/authentication";

/**
 * A form that is used for both sign-in and sign-up
 * @param isSignIn - when true, show Sign-In form, otherwise - Sign-Up form
 * @param onSuccess - callback function called when the submission button is clicked
 * @constructor
 */
export function SignInUpForm({isSignIn, onSuccess}) {
  let formTitle;
  let emailInput = null;
  let repeatPasswordInput = null;
  let submitButtonTitle;
  let alternativeDescription;
  let alternativeLinkText;
  let alternativeUrl;
  let usernamePlaceholder;
  let [error, setError] = useState("");
  let [buttonDisabled, setButtonDisabled] = useState(false);

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
    emailInput = <input className="fullscreen-form__input" type="text" placeholder="Email" id="signinup_email"/>;
    repeatPasswordInput = <input className="fullscreen-form__input" type="text" placeholder="Repeat Password"
                                 id="signinup_repeated_password"/>;
    submitButtonTitle = "Create Account";
    alternativeDescription = "Already have an account?";
    alternativeLinkText = "Sign in";
    alternativeUrl = "/signin";
  }

  /**
   * Handle form submission
   * @param event The click-event
   */
  function handleSubmit(event) {
    event.preventDefault();
    setButtonDisabled(true);
    const username = document.getElementById("signinup_username").value;
    const password = document.getElementById("signinup_password").value;
    if (isSignIn) {
      sendAuthenticationRequest(username, password, onSuccess,
        (errorCode, errorMessage) => {
          setError(errorMessage);
          setButtonDisabled(false);
        }
      );
    } else {
      // TODO apiSignUp(username, email, password, repeatPassword);
    }
  }

  return <main className="fullscreen-form__background">
    <form className="fullscreen-form box">
      <h1 className="fullscreen-form__header">{formTitle}</h1>
      <input className="fullscreen-form__input" type="text" placeholder={usernamePlaceholder} id="signinup_username"/>
      {emailInput}
      <input className="fullscreen-form__input" type="password" placeholder="Password" id="signinup_password"/>
      {repeatPasswordInput}
      {error ? <FormErrorMessage error={error}/> : <></>}
      <button className="fullscreen-form__action-button" type="submit" disabled={buttonDisabled}
              onClick={handleSubmit}>{submitButtonTitle}</button>
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
