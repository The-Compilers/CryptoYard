import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

// Components
import Menu from "./components/Menu";

// styles
import "./nav.css";
import { UserMenu } from "./components/UserMenu";

// Top navigation bar
function Nav({ user }) {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const home_url = "/dashboard/" + firstName + "-" + lastName;
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

  useEffect(() => {
    if (user) {
      setFirstName(user.firstName);
      setLastName(user.lastName);
    }
  }, [user]);

  function toggleSettings(event) {
    document
      .querySelector("[data-dropdown-list]")
      .classList.toggle("user__dropdown__list--hidden");
  }

  return (
    <nav className="nav">
      <div className="menu--wrapper">
        <Link to={home_url} className="logo--link">
          <h1 className="logo">CryptoYard</h1>
        </Link>
        <Menu menuItems={menuItems} />
      </div>
      <UserMenu onClick={toggleSettings} firstName={firstName} lastName={lastName} />
    </nav>
  );
}

export default Nav;
