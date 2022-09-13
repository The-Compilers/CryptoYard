// components
import MenuItem from "./MenuItem";

// styles
import "./menu.css";

// Main menu with the section links
export default function Menu({ menuItems }) {
  return (
    <ul className="menu">
      {menuItems.map((item, index) => (
        <MenuItem key={index} item={item} />
      ))}
    </ul>
  );
}
