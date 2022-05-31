import { Link } from "react-router-dom";

// components
import MenuItem from "./MenuItem";

// styles
import "./menu.css";

export default function Menu({ menuItems }) {
  return (
    <ul className="menu">
      {menuItems.map((item, index) => (
        <MenuItem key={index} item={item} />
      ))}
    </ul>
  );
}
