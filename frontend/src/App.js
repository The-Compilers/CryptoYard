import "./styles/global.css";

import {Routes, Route} from "react-router-dom";
import SignUpSheet from "./components/signUp/signUpSheet";
import SignInSheet from "./components/signIn/SignInSheet";


function App() {
    return (
        <Routes>
            <Route path="/signin" element={<SignInSheet/>}/>
            <Route path="/signup" element={<SignUpSheet/>}/>
        </Routes>
    );
}

export default App;
