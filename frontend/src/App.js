// Routing
import { Routes, Route, Navigate, useNavigate } from "react-router-dom";

// Global styles
import "./styles/global.css";

// Pages
import Dashboard from "./pages/dashboard/Dashboard";
import { SignInUpForm } from "./pages/signin-signup/SignInUpForm";
import Market from "./pages/market/Market";

// Services
import { UserContext } from "./state/UserContext";
import {
  deleteAuthorizationCookies,
  getAuthenticatedUser,
} from "./services/authentication";
import { useState } from "react";
import Nav from "./components/nav/Nav";
import NotFound from "./pages/404/NotFound";

/**
 * The main application component
 * @returns {JSX.Element}
 * @constructor
 */
function App() {
  let [user, setUser] = useState(getAuthenticatedUser());
  const navigate = useNavigate();

  function onSignInSuccess(userData) {
    console.log("User authenticated!");
    setUser(userData);
    navigate("/dashboard");
  }

  function onSignUpSuccess(userData) {
    console.log("Signed up!");
    return onSignInSuccess(userData);
  }

  /**
   * Perform user logout
   */
  function handleLogOut() {
    console.log("Signed out");
    deleteAuthorizationCookies();
    setUser(null);
  }

  return (
    <UserContext.Provider value={user}>
      {user ? <Nav onLogOut={handleLogOut} /> : <></>}
      <Routes>
        {user ? (
          <>
            <Route path="/" element={<Dashboard />} />
            <Route path="/dashboard/" element={<Dashboard />} />
            <Route path="/markets" element={<Market />} />
          </>
        ) : (
          <>
            <Route
              path="/"
              element={
                <SignInUpForm isSignIn={true} onSuccess={onSignInSuccess} />
              }
            />
            <Route
              path="/signin"
              element={
                <SignInUpForm isSignIn={true} onSuccess={onSignInSuccess} />
              }
            />
            <Route
              path="/signup"
              element={
                <SignInUpForm isSignIn={false} onSuccess={onSignUpSuccess} />
              }
            />
          </>
        )}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </UserContext.Provider>
  );
}

export default App;
