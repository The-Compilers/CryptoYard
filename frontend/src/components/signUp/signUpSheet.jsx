import "../signUp/signUp.css"
import {useNavigate} from "react-router";


function SignUpSheet() {

    const navigate = useNavigate();

    return (
        <form className="sign-up-form box" action="">

            <h3 className="sign-up-form__header">Create your account</h3>
            <input className="sign-up-form__input" type="text" placeholder="Username"/>
            <input className="sign-up-form__input" type="text" placeholder="Email"/>
            <input className="sign-up-form__input" type="text" placeholder="Password"/>
            <input className="sign-up-form__input" type="text" placeholder="Repeat Password"/>
            <button className="sign-up-form__create-button" type="submit">Create Account</button>
            <div className="sign-up-form__footer">
                <p>Already have an account?</p>
                <a onClick={() => (navigate("/signin"))} className="sign-up-form__login" href="#">Log in</a>
            </div>

        </form>
    );
}

export default SignUpSheet;
