import "../styles/loginForms.css"

function SignUpSheet()
{
    return (
        <>
            <body>
                <section class="sign-up-form__wrapper">
                    <form class="sign-up-form" action="">
                        <div class="sign-up-card">
                            <h3 class="sign-up-card__header">Create your account</h3>
                            <input class="sign-up-card__element" type="text" placeholder="Username"/>
                            <input class="sign-up-card__element" type="text" placeholder="Email" />
                            <input class="sign-up-card__element" type="text" placeholder="Password"/>
                            <input class="sign-up-card__element" type="text" placeholder="Repeat Password"/>
                            <button class="sign-up-card__create-button" type="submit">Create Account</button>
                            <div class="sign-up-card__footer">
                                <p>Already have an account?</p>
                                <a class="sign-up-card__login" href="#">Log in</a>
                            </div>
                        </div>
                    </form>
                </section>
            </body>
        </>
    );
}

export default SignUpSheet;