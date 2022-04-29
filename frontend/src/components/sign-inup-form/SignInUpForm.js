import "../../styles/fullscreen-forms.css"

/**
 * A form that is used for both sign-in and sign-up
 * @param props Contains isSignIn property - when true, show Sign-In form, otherwise - Sign-Up form
 * @constructor
 */
export function SignInUpForm(props) {
    let formTitle;
    let emailInput = null;
    let repeatPasswordInput = null;
    let submitButtonTitle;
    let alternativeDescription;
    let alternativeLinkText;
    let alternativeUrl;
    let usernamePlaceholder;
    if (props.isSignIn) {
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

    return <main className="fullscreen-form__background">
        <form className="fullscreen-form box">
            <h1 className="fullscreen-form__header">{formTitle}</h1>
            <input className="fullscreen-form__input" type="text" placeholder={usernamePlaceholder}/>
            {emailInput}
            <input className="fullscreen-form__input" type="password" placeholder="Password"/>
            {repeatPasswordInput}
            <button className="fullscreen-form__action-button" type="submit">{submitButtonTitle}</button>
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
