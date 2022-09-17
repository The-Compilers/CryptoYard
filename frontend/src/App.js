// Routing
import { Routes, Route, Navigate } from "react-router-dom";

// Global styles
import "./styles/global.css";

// Pages
import Dashboard from "./pages/dashboard/Dashboard";
import { SignInUpForm } from "./components/sign-inup-form/SignInUpForm";
import Market from "./pages/market/Market";

/**
 * The main application component
 * @returns {JSX.Element}
 * @constructor
 */
function App() {
  const tmpUser = {
    firstName: "Adam",
    lastName: "Jensen",
    username: "adam"
  };
  const loggedIn = false;

  return (
    <div>
      <Routes>
        <Route exact path="/" element={
          loggedIn
            ? <Navigate to={"/dashboard/" + tmpUser.username} />
            : <Navigate to="/signin" />
        } />
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
