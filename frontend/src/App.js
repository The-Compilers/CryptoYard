import "./styles/global.css";

import { Routes, Route } from "react-router-dom";
import SignUpSheet from "./components/SignUpSheet.jsx";
import SignIn from "./components/SignIn/SignIn";
import Dashboard from "./pages/dashboard/Dashboard";

function App() {
  return (
    <div>
      <Routes>
        <Route path="dashboard/:username" element={<Dashboard />} />
        <Route path="/signin" element={<SignIn />} />
        <Route exact path="/signup" element={<SignUpSheet />} />
      </Routes>
    </div>
  );
}

export default App;
