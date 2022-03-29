import "./styles/global.css";
import { Routes, Route } from "react-router-dom";
import SignIn from "./components/SignIn/SignIn";



function App() {
  return (

      <div>
          <Routes>
              <Route path ="/signin" element = {<SignIn/>}/>
          </Routes>
      </div>

  );
}

export default App;
