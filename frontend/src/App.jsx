// Routing
import { Routes, Route, useNavigate, Navigate } from "react-router-dom";

// Global styles
import "./styles/global.css";

// Pages
import Dashboard from "./pages/dashboard/Dashboard";
import SignInUpForm from "./pages/signin-signup/SignInUpForm";
import Market from "./pages/market/Market";
import Settings from "./pages/settings/Settings";

// Services
import { UserContext } from "./state/UserContext";
import {
  deleteAuthorizationCookies,
  getAuthenticatedUser,
} from "./services/authentication";
import { useState } from "react";
import Nav from "./components/nav/Nav";
import NotFound from "./pages/404/NotFound";

import { theme } from "./styles/theme";
import { ThemeProvider } from "@mui/material";
import { QueryClient } from "@tanstack/react-query";
import { QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

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
    navigate("/signin");
  }

  return (
    <ThemeProvider theme={theme}>
      <QueryClientProvider client={queryClient}>
        <UserContext.Provider value={user}>
          {user ? <Nav onLogOut={handleLogOut} /> : <></>}
          <Routes>
            {user ? (
              <>
                <Route path="/" element={<Navigate to="/dashboard/" />} />
                <Route path="/dashboard/" element={<Dashboard />} />
                <Route path="/markets" element={<Market />} />
                <Route
                  path="/settings"
                  element={<Settings doLogout={handleLogOut} />}
                />
              </>
            ) : (
              <>
                <Route path="/" element={<Navigate to="/signin" />} />
                <Route
                  path="/signin"
                  element={
                    <SignInUpForm isSignIn={true} onSuccess={onSignInSuccess} />
                  }
                />
                <Route
                  path="/signup"
                  element={
                    <SignInUpForm
                      isSignIn={false}
                      onSuccess={onSignUpSuccess}
                    />
                  }
                />
              </>
            )}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </UserContext.Provider>
      </QueryClientProvider>
    </ThemeProvider>
  );
}

export default App;
