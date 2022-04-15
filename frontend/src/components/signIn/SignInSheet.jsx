import './SignIn.css'
import {useNavigate} from "react-router";

function SignInSheet() {

    const navigate = useNavigate();

    return (

        <div className={"sign-in-container"}>

            <div className={"sign-in-form-wrapper box"}>
                <h2 className={"sign-in-header"}>
                    Sign In
                </h2>
                <form className={"sign-in-form"}>
                    <input className={"sign-in-input"} type={"text"} placeholder={"Username"}/>
                    <input className={"sign-in-input"} type={"password"} placeholder={"Password"}/>
                    <input id={"sign-in-button"} type={"submit"} value={"SIGN IN"}/>
                </form>
                <div className={"sign-in-footer"}>
                    <a onClick={() => (navigate("/signup"))} href={""}>Don't have an account? Sign up</a>
                </div>
            </div>
        </div>
    )
}

export default SignInSheet;
