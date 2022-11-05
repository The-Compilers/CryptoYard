import { Link } from "react-router-dom";
import { getAuthenticatedUser } from "../../services/authentication";
import "./notFound.css";

/**
 * Page to show for missing urls (404 Not Found)
 * @return {JSX.Element}
 * @constructor
 */
function NotFound() {
  return (
    <div className="not-found">
      <h1 className="not-found__title">Ooops!</h1>
      <h2 className="not-found__sub-title">404 - Page not found</h2>
      <p className="not-found__desc">
        The page you are looking for is not currently available. Maybe it's been
        moved, removed, renamed or it's never existed
      </p>
      {getAuthenticatedUser() == null ? (
        <Link className="not-found__redirect-btn" to="/signin">
          Go back to sign in page
        </Link>
      ) : (
        <Link className="not-found__redirect-btn" to="/dashboard">
          Go back to dashboard
        </Link>
      )}
    </div>
  );
}

export default NotFound;
