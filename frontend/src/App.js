// Routing
import { Routes, Route } from "react-router-dom";

// Global styles
import "./styles/global.css";

// Pages
import Dashboard from "./pages/dashboard/Dashboard";
import { SignInUpForm } from "./components/sign-inup-form/SignInUpForm";

function App() {
  return (
    <div>
      <Routes>
        <Route path="dashboard/:username" element={<Dashboard />} />
        <Route path="/signin" element={<SignInUpForm isSignIn={true} />} />
        <Route path="/signup" element={<SignInUpForm isSignIn={false} />} />
      </Routes>
    </div>
  );
}

export default App;
