import { Link } from "react-router-dom";

// Components
import Menu from "./components/Menu";

// styles
import "./nav.css";
import { UserMenu } from "./components/UserMenu";

/**
 * Top navigation bar
 * @param onSignOut - a function to call when the "Log out" link has been pressed
 * @return {JSX.Element}
 */
function Nav({ onLogOut }) {
  const home_url = "/dashboard";
  const menuItems = [
    {
      link: home_url,
      label: "Home",
    },
    {
      link: "/markets",
      label: "Market",
      subItems: [
        {
          link: "/markets",
          label: "Coin list",
        },
      ],
    },
  ];

  return (
    <nav className="nav">
      <div className="menu--wrapper">
        <Link to={home_url} className="logo--link">
          <h1 className="logo">CryptoYard</h1>
        </Link>
        <Menu menuItems={menuItems} />
      </div>
      <UserMenu onLogOut={onLogOut} />
    </nav>
  );
}

export default Nav;
