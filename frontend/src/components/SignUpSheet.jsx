import "../styles/loginForms.css";

function SignUpSheet() {
  return (
    <>
      <div className="sign-up-form--wrapper">
        <form className="sign-up-form box" action="">
          <h3 className="sign-up-card__header">Create your account</h3>
          <input
            className="sign-up-card__input"
            type="text"
            placeholder="Username"
          />
          <input
            className="sign-up-card__input"
            type="text"
            placeholder="Email"
          />
          <input
            className="sign-up-card__input"
            type="text"
            placeholder="Password"
          />
          <input
            className="sign-up-card__input"
            type="text"
            placeholder="Repeat Password"
          />
          <button className="sign-up-card__create-button" type="submit">
            Create Account
          </button>
          <div className="sign-up-card__footer">
            <p>Already have an account?</p>
            <a className="sign-up-card__login" href="#">
              Log in
            </a>
          </div>
        </form>
      </div>
    </>
  );
}

export default SignUpSheet;
