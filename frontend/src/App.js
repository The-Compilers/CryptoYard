// Routing
import { Routes, Route } from "react-router-dom";

// Global styles
import "./styles/global.css";

// Pages
import Dashboard from "./pages/dashboard/Dashboard";
import { SignInUpForm } from "./components/sign-inup-form/SignInUpForm";
import Market from "./pages/market/Market";

function App() {
  const tmpUser = {
    firstName: "Adam",
    lastName: "Jensen",
  };

  return (
    <div>
      <Routes>
        <Route
          path="/dashboard/:username"
          element={<Dashboard user={tmpUser} />}
        />
        <Route path="/signin" element={<SignInUpForm isSignIn={true} />} />
        <Route path="/signup" element={<SignInUpForm isSignIn={false} />} />
        <Route path="/markets" element={<Market user={tmpUser} />} />
      </Routes>
    </div>
  );
}

export default App;
