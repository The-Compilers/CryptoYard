import './SignIn.css'

function SignInForm() {
    return (

        <div className={"sign-in-container"}>

            <h2 className={"sign-in-header"}>
                Sign In
            </h2>

            <div className={"sign-in-form-wrapper"}>
                    <form className={"sign-in-form"}>
                        <input id={"sign-in-input"} type={"text"} placeholder={"Username"}/>
                        <input id={"sign-in-input"} type={"password"} placeholder={"Password"}/>

                        <div className={"sign-in-button-container"}>
                            <input type={"checkbox"} id={"sign-in-checkbox"} name={"check"}/>
                            <label for={"check"}>Keep med signed in</label>
                            <input id={"sign-in-button"} type={"submit"} value={"SIGN IN"} />
                        </div>
                    </form>
            </div>

            <div className={"sign-in-footer"}>
                <p>
                    <a href={""}>Forgot password?</a>
                    &nbsp;·&nbsp;
                    <a href={""}>Dont have an account?</a>
                </p>
            </div>
        </div>
    )
}


export default SignInForm;
