import './App.css';
import { Routes, Route } from "react-router-dom";
import SignUpSheet from "./components/SignUpSheet.jsx";

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
