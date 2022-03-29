import "./styles/global.css";
import SignUpSheet from "./components/SignUpSheet.jsx";
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <>
    <Routes>
      <Route exact path = "/" element = {<SignUpSheet/>}/>
    </Routes>
    </>
  );
}

export default App;
