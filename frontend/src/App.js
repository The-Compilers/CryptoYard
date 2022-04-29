import "./styles/global.css";

import {Routes, Route} from "react-router-dom";
import {SignInUpForm} from "./components/sign-inup-form/SignInUpForm";


function App() {
    return (
        <Routes>
            <Route path="/signin" element={<SignInUpForm isSignIn={true}/>}/>
            <Route path="/signup" element={<SignInUpForm isSignIn={false}/>}/>
        </Routes>
    );
}

export default App;
