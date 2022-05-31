import { Link } from "react-router-dom";

export default function MenuItem({ item }) {
  return (
    <li className="menu__item">
      {item.subItems ? (
        <Link to={item.link} className="menu__link">
          {item.label} <i class="material-icons">arrow_drop_down</i>
        </Link>
      ) : (
        <Link to={item.link} className="menu__link">
          {item.label}
        </Link>
      )}
      {item.subItems ? (
        <ul className="sub-menu box box--square-top">
          {item.subItems.map((subItem, index) => (
            <li key={index} className="sub-menu__item">
              <Link to={subItem.link} className="sub-menu__link">
                {subItem.label}
              </Link>
            </li>
          ))}
        </ul>
      ) : (
        ""
      )}
    </li>
  );
}
